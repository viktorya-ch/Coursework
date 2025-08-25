package org.skypro.Star.Bank.controller;

import org.skypro.Star.Bank.repository.CachedUserRepository;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {


    private final CacheManager cacheManager;
    private final CachedUserRepository cachedUserRepository;

    public ManagementController(CacheManager cacheManager, CachedUserRepository cachedUserRepository) {
        this.cacheManager = cacheManager;
        this.cachedUserRepository = cachedUserRepository;
    }

    @PostMapping("/clear-caches")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCaches() {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
        cachedUserRepository.clearAllCaches();
    }
}