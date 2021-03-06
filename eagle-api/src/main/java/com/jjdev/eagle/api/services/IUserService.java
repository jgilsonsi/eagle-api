package com.jjdev.eagle.api.services;

import com.jjdev.eagle.api.entities.JUser;
import java.util.Optional;

/**
 *
 * @author JGilson
 */
public interface IUserService {

    /**
     * Create user.
     *
     * @param user
     * @return JUser
     */
    JUser create(JUser user);

    /**
     * Return user by email.
     *
     * @param email
     * @return Optional<JUser>
     */
    Optional<JUser> readByEmail(String email);

    /**
     * Return user by id.
     *
     * @param id
     * @return Optional<JUser>
     */
    Optional<JUser> readById(Long id);

    /**
     * Update user.
     *
     * @param user
     * @return return true if the action is ok
     */
    boolean update(JUser user);

    /**
     * Remove user.
     *
     * @param id
     */
    void delete(Long id);

}
