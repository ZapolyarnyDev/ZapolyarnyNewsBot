package io.github.zapolyarnydev.service.news;

import io.github.zapolyarnydev.entity.SubscriptionEntity;
import io.github.zapolyarnydev.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NewsSubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    public void subscribe(Long chatId){
        Optional<SubscriptionEntity> entity = repository.findById(chatId);
        if(entity.isPresent()){
            entity.get().setSubscribed(true);
            repository.save(entity.get());
        } else {
            var newEntity = new SubscriptionEntity(chatId, true);
            newEntity.setLastNewsSendDateTime(LocalDateTime.now());
            repository.save(newEntity);
        }
    }

    public void unsubscribe(Long chatId){
        Optional<SubscriptionEntity> entity = repository.findById(chatId);
        if(entity.isPresent()){
            entity.get().setSubscribed(false);
            repository.save(entity.get());
        } else {
            var newEntity = new SubscriptionEntity(chatId, false);
            newEntity.setLastNewsSendDateTime(LocalDateTime.now());
            repository.save(newEntity);
        }
    }

    public boolean isSubscribed(Long chatId){
        return repository.findById(chatId)
                .map(SubscriptionEntity::isSubscribed)
                .orElse(false);
    }


    public boolean chatExists(Long chatId){
        return repository.existsById(chatId);
    }
}
