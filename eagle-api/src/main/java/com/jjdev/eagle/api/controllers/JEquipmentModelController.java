package com.jjdev.eagle.api.controllers;

import com.jjdev.eagle.api.dtos.JEquipmentModelDto;
import com.jjdev.eagle.api.dtos.JEquipmentTypeDto;
import com.jjdev.eagle.api.entities.JEquipmentModel;
import com.jjdev.eagle.api.entities.JEquipmentType;
import com.jjdev.eagle.api.response.JResponse;
import com.jjdev.eagle.api.services.IEquipmentModelService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JGilson
 */
@RestController
@RequestMapping("/api/v1/equipment-model")
@CrossOrigin(origins = "*")
public class JEquipmentModelController {

    private static final Logger log = LoggerFactory.getLogger(JEquipmentModelController.class);

    @Autowired
    private IEquipmentModelService equipmentModelService;

    public JEquipmentModelController() {
    }

    /**
     * Create equipment model.
     *
     * @param equipmentModel
     * @param result
     * @return ResponseEntity<JResponse<JEquipmentModelDto>>
     */
    @PostMapping
    public ResponseEntity<JResponse<JEquipmentModelDto>> create(
            @Valid @RequestBody JEquipmentModelDto equipmentModelDto, BindingResult result) {

        log.info("Creating equipment model: {}", equipmentModelDto.getName());

        JResponse<JEquipmentModelDto> response = new JResponse<>();
        if (result.hasErrors()) {
            log.info("Validation erros: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors()
                    .add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        JEquipmentModel equipmentModel = this.dtoToEquipmentModel(equipmentModelDto);
        equipmentModel = this.equipmentModelService.create(equipmentModel);

        response.setData(this.equipmentModelToDto(equipmentModel));
        return ResponseEntity.ok(response);
    }

    /**
     * Return a list of equipment models.
     *
     * @return ResponseEntity<Response<List<JEquipmentModelDto>>>
     */
    @GetMapping(value = "")
    public ResponseEntity<JResponse<List<JEquipmentModelDto>>> readAll() {

        log.info("Searching all equipment models.");

        JResponse<List<JEquipmentModelDto>> response = new JResponse<>();

        List<JEquipmentModel> equipmentModels = this.equipmentModelService.readAll();
        List<JEquipmentModelDto> equipmentModelsDto = equipmentModels.stream()
                .map(equipmentModel -> this.equipmentModelToDto(equipmentModel))
                .collect(Collectors.toList());

        response.setData(equipmentModelsDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Update equipment model.
     *
     * @param id
     * @param equipmentModelDto
     * @return ResponseEntity<JResponse<JEquipmentModelDto>>
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<JResponse<JEquipmentModelDto>> update(@PathVariable("id") Long id,
            @Valid @RequestBody JEquipmentModelDto equipmentModelDto, BindingResult result) {

        log.info("Updating equipment model: {}", equipmentModelDto.getName());

        JResponse<JEquipmentModelDto> response = new JResponse<>();

        if (result.hasErrors()) {
            log.info("Validation errors: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        equipmentModelDto.setId(id);
        JEquipmentModel equipmentModel = this.dtoToEquipmentModel(equipmentModelDto);
        equipmentModel = this.equipmentModelService.update(equipmentModel);

        response.setData(this.equipmentModelToDto(equipmentModel));
        return ResponseEntity.ok(response);
    }

    /**
     * Delete equipment model by id.
     *
     * @param id
     * @return ResponseEntity<JResponse<String>>
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<JResponse<String>> delete(@PathVariable("id") Long id) {

        log.info("Removing equipment model by id: {}", id);

        JResponse<String> response = new JResponse<>();
        Optional<JEquipmentModel> equipmentModel = this.equipmentModelService.readById(id);

        if (!equipmentModel.isPresent()) {
            log.info("Invalid equipment model id: {}", id);
            response.getErrors().add("Invalid equipment model id: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.equipmentModelService.delete(id);

        return ResponseEntity.ok(new JResponse<>());
    }

    //--------------------------------------------------------------------------
    /**
     * Convert DTO to JEquipmentModel.
     *
     * @param equipmentModelDto
     * @return JEquipmentModel
     */
    private JEquipmentModel dtoToEquipmentModel(JEquipmentModelDto equipmentModelDto) {
        JEquipmentModel equipmentModel = new JEquipmentModel();
        equipmentModel.setId(equipmentModelDto.getId());
        equipmentModel.setName(equipmentModelDto.getName());
        equipmentModel.setDescription(equipmentModelDto.getDescription());

        JEquipmentType equipmentType = new JEquipmentType();
        equipmentType.setId(equipmentModelDto.getEquipmentTypeDto().getId());
        equipmentModel.setEquipmentType(equipmentType);

        return equipmentModel;
    }

    /**
     * Convert JEquipmentModel to DTO.
     *
     * @param equipmentModel
     * @return JEquipmentModelDto
     */
    private JEquipmentModelDto equipmentModelToDto(JEquipmentModel equipmentModel) {
        JEquipmentModelDto equipmentModelDto = new JEquipmentModelDto();
        equipmentModelDto.setId(equipmentModel.getId());
        equipmentModelDto.setName(equipmentModel.getName());
        equipmentModelDto.setDescription(equipmentModel.getDescription());

        JEquipmentTypeDto equipmentTypeDto = new JEquipmentTypeDto();
        equipmentTypeDto.setId(equipmentModel.getEquipmentType().getId());
        equipmentTypeDto.setName(equipmentModel.getEquipmentType().getName());

        equipmentModelDto.setEquipmentTypeDto(equipmentTypeDto);

        return equipmentModelDto;
    }

}