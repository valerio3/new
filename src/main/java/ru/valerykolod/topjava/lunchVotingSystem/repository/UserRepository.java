package ru.valerykolod.topjava.lunchVotingSystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.valerykolod.topjava.lunchVotingSystem.model.User;

import java.util.List;

@Repository
public class UserRepository {

    private final CrudUserRepository crudUserRepository;

    @Autowired
    public UserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    public User save(User user) {
        return crudUserRepository.save(user);
    }

    public boolean delete(int id) {
        return crudUserRepository.deleteById(id) != 0;
    }

    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return crudUserRepository.getAll();
    }

}
