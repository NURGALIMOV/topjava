package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, User> storage = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return storage.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            storage.put(user.getId(), user);
            log.info("save new user {}", user);
            return user;
        }
        User result = storage.computeIfPresent(user.getId(), (id, oldMeal) -> user);
        log.info("update user {}", user);
        return result;
    }

    @Override
    public User get(int id) {
        final User user = storage.get(id);
        log.info("get by id {} user {}", id, user);
        return user;
    }

    @Override
    public List<User> getAll() {
        final List<User> collect = storage.values()
                .stream()
                .sorted(Comparator.comparing(AbstractNamedEntity::getName))
                .collect(Collectors.toList());
        log.info("get all {}", collect);
        return collect;
    }

    @Override
    public User getByEmail(String email) {
        final User user = storage.values()
                .stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
        log.info("get by email {} user {}", email, user);
        return user;
    }

}
