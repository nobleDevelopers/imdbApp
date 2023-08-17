package com.noble.developers.services;

import com.noble.developers.models.DirectorStat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DirectorService {
    List<DirectorStat> listDirectorsOrderBy();
}
