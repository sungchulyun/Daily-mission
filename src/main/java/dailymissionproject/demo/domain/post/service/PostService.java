package dailymissionproject.demo.domain.post.service;

import dailymissionproject.demo.domain.mission.repository.Mission;
import dailymissionproject.demo.domain.mission.repository.MissionRepository;
import dailymissionproject.demo.domain.participant.repository.Participant;
import dailymissionproject.demo.domain.post.dto.request.PostSaveRequestDto;
import dailymissionproject.demo.domain.post.repository.Post;
import dailymissionproject.demo.domain.post.repository.PostRepository;
import dailymissionproject.demo.domain.user.repository.User;
import dailymissionproject.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long save(Long id, String userName, PostSaveRequestDto requestDto){

        Mission mission = missionRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new NoSuchElementException("해당 미션이 존재하지 않습니다."));


        User findUser = userRepository.findOneByName(userName);
        if(Objects.isNull(findUser)){
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
        }
        //미션 참여자인지 검증
        validIsParticipating(findUser, mission);

        Post post = requestDto.toEntity(findUser, mission);
        return postRepository.save(post).getId();

    }

    private boolean validIsParticipating(User user, Mission mission){
        for(Participant p : mission.getParticipants()){
            if(p.getId() == user.getId())
                return true;
        }
        throw new RuntimeException("참여중이지 않은 미션에 인증 글을 작성할 수 없습니다.");
    }
}
