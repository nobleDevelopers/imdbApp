package com.noble.developers.services.impl;

import com.noble.developers.exceptions.NoRankException;
import com.noble.developers.models.UserStat;
import com.noble.developers.repository.RankRepository;
import com.noble.developers.services.UserService;

public class UserServiceImpl implements UserService {
    private final RankRepository rankRepository;

    public UserServiceImpl(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Override
    public UserStat getUserByMaxWatchedFilm() {
        UserStat stat =  rankRepository.getUserByMaxWatchedFilm();
        if( stat == null)
            throw new NoRankException("No Rank Existed");
        return stat;
    }
}
