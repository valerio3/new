package ru.valerykolod.topjava.lunchVotingSystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
public class VoteRepository {

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public VoteRepository(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository,
                          CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Vote save(Vote vote, Integer userId, Integer restaurantId) {
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudVoteRepository.save(vote);
    }

    public List<Vote> getByUser(Integer userId) {
        return crudVoteRepository.findByUserId(userId).orElse(Collections.emptyList());
    }

    public Vote getByUserAndDate(Integer userId, LocalDate date) {
        return crudVoteRepository.findByUserIdAndDate(userId, date).orElse(null);
    }

    public boolean deleteOldVotesFromDate(LocalDate date) {
        return crudVoteRepository.deleteForDate(date) != 0;
    }

    public List<Vote> getAllVotes() {
        return crudVoteRepository.findAll();
    }

    public void deleteVote(Vote vote) {
        crudVoteRepository.delete(vote);
    }

}
