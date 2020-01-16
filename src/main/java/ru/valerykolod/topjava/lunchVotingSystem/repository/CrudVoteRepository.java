package ru.valerykolod.topjava.lunchVotingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.valerykolod.topjava.lunchVotingSystem.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Transactional
    @Modifying
    @Query(name = Vote.DELETE_FOR_DATE)
    int deleteForDate(@Param("date") LocalDate date);

    @Query(name = Vote.GET_BY_USER)
    Optional<List<Vote>> findByUserId(@Param("userId") Integer userId);

    @Query(name = Vote.GET_BY_USER_AND_DATE)
    Optional<Vote> findByUserIdAndDate(@Param("userId") Integer userId, @Param("date") LocalDate date);

    List<Vote> findAll();

    @Override
    void delete(Vote entity);

}
