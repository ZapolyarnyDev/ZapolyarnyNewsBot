package io.github.zapolyarnydev.service.news;

import io.github.zapolyarnydev.entity.SubscriptionEntity;
import io.github.zapolyarnydev.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NewsCategoryService {

    @Autowired
    private SubscriptionRepository repository;

    public boolean hasSubscribeOnCategory(Long chatId, String categoryId){
        return repository.findById(chatId)
                .map(subscriptionEntity -> subscriptionEntity.getSubscribedCategories().contains(categoryId))
                .orElse(false);
    }

    public void addCategory(Long chatId, String categoryId){
        Optional<SubscriptionEntity> subscriptionEntity = repository.findById(chatId);
        if(subscriptionEntity.isPresent()){
            subscriptionEntity.get().getSubscribedCategories().add(categoryId);
            repository.save(subscriptionEntity.get());
        } else {
            var entity = new SubscriptionEntity(chatId, false);
            entity.getSubscribedCategories().add(categoryId);
            entity.setLastNewsSendDateTime(LocalDateTime.now());
            repository.save(entity);
        }
    }

    public void removeCategory(Long chatId, String categoryId){
        repository.findById(chatId).ifPresent(subscriptionEntity ->{
            subscriptionEntity.getSubscribedCategories().remove(categoryId);
            repository.save(subscriptionEntity);
        });

    }
}
