package com.noble.developers.services;

import com.noble.developers.models.UserStat;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserStat getUserByMaxWatchedFilm();
}
