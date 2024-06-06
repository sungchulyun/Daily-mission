package dailymissionproject.demo.domain.mission.repository;

import dailymissionproject.demo.domain.mission.repository.Mission;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class MissionRepository {

    private final EntityManager em;


    public void save(Mission mission){
        em.persist(mission);
    }

    /*
    public List<Mission> findAll(){
    }

     */
}