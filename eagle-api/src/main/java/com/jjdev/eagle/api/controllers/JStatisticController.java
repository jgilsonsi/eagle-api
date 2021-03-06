package com.jjdev.eagle.api.controllers;

import com.jjdev.eagle.api.dtos.JStatisticDto;
import com.jjdev.eagle.api.dtos.JStatisticItemDto;
import com.jjdev.eagle.api.entities.JStatistic;
import com.jjdev.eagle.api.response.JResponse;
import com.jjdev.eagle.api.services.IStatisticService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JGilson
 */
@RestController
@RequestMapping("/api/v1/statistics")
@CrossOrigin(origins = "*")
public class JStatisticController {

    @Autowired
    private IStatisticService statisticService;

    private static final Logger log = LoggerFactory.getLogger(JStatisticController.class);

    public JStatisticController() {
    }

    /**
     * Return system statistics.
     *
     * @return ResponseEntity<Response<JStatisticDto>>
     */
    @GetMapping()
    public ResponseEntity<JResponse<JStatisticDto>> readStatistics() {

        log.info("Searching system statistics.");

        JResponse<JStatisticDto> response = new JResponse<>();
        JStatisticDto statisticDto = new JStatisticDto();

        List<JStatistic> numberOfClientsByMonthItems = this.statisticService.readNumberOfClientsByMonth();
        List<JStatisticItemDto> numberOfClientsByMonthItemsDto = numberOfClientsByMonthItems.stream()
                .map(statistic -> this.statisticToDto(statistic))
                .collect(Collectors.toList());
        statisticDto.setNumberOfClientsByMonth(numberOfClientsByMonthItemsDto);

        List<JStatistic> numberOfVisitsByMonthItems = this.statisticService.readNumberOfVisitsByMonth();
        List<JStatisticItemDto> numberOfVisitsByMonthItemsDto = numberOfVisitsByMonthItems.stream()
                .map(statistic -> this.statisticToDto(statistic))
                .collect(Collectors.toList());
        statisticDto.setNumberOfVisitsByMonth(numberOfVisitsByMonthItemsDto);

        List<JStatistic> numberOfOrdersByMonthItems = this.statisticService.readNumberOfOrdersByMonth();
        List<JStatisticItemDto> numberOfOrdersByMonthItemsDto = numberOfOrdersByMonthItems.stream()
                .map(statistic -> this.statisticToDto(statistic))
                .collect(Collectors.toList());
        statisticDto.setNumberOfOrdersByMonth(numberOfOrdersByMonthItemsDto);

        List<JStatistic> numberOfOrdersByEquipmentTypeItems = this.statisticService.readNumberOfOrdersByEquipmentType();
        List<JStatisticItemDto> numberOfOrdersByEquipmentTypeItemsDto = numberOfOrdersByEquipmentTypeItems.stream()
                .map(statistic -> this.statisticToDto(statistic))
                .collect(Collectors.toList());
        statisticDto.setNumberOfOrdersByEquipmentType(numberOfOrdersByEquipmentTypeItemsDto);

        statisticDto.setNumberOfVisits(this.statisticToDto(this.statisticService.readNumberOfVisits()));

        statisticDto.setNumberOfClients(this.statisticToDto(this.statisticService.readNumberOfClients()));

        statisticDto.setNumberOfOrders(this.statisticToDto(this.statisticService.readNumberOfOrders()));

        response.setData(statisticDto);
        return ResponseEntity.ok(response);
    }

    //--------------------------------------------------------------------------
    /**
     * Convert JStatistic to DTO.
     *
     * @param order
     * @return JStatisticItemDto
     */
    private JStatisticItemDto statisticToDto(JStatistic statistic) {

        JStatisticItemDto statisticDto = new JStatisticItemDto();
        statisticDto.setName(statistic.getName());
        statisticDto.setValue(statistic.getValue());

        return statisticDto;
    }

}
