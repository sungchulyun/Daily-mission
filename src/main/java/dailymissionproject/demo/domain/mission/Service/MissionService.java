package dailymissionproject.demo.domain.mission.Service;

import dailymissionproject.demo.domain.mission.dto.request.MissionSaveRequestDto;
import dailymissionproject.demo.domain.mission.dto.response.MissionHotListResponseDto;
import dailymissionproject.demo.domain.mission.dto.response.MissionResponseDto;
import dailymissionproject.demo.domain.mission.dto.response.MissionSaveResponseDto;
import dailymissionproject.demo.domain.mission.repository.Mission;
import dailymissionproject.demo.domain.mission.repository.MissionRepository;
import dailymissionproject.demo.domain.user.repository.User;
import dailymissionproject.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class MissionService {
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;

    //==미션 상세 조회==//
    public MissionResponseDto findById(Long id){
        Mission mission = missionRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 미션이 존재하지 않습니다."));

        MissionResponseDto responseDto = MissionResponseDto.builder()
                .title(mission.getTitle())
                .content(mission.getContent())
                .imgUrl(mission.getImageUrl())
                .userName(mission.getUser().getName())
                .startDate(mission.getStartDate())
                .endDate(mission.getEndDate())
                .build();
        return responseDto;
    }


    //== 미션 생성 ==//
    public MissionSaveResponseDto save(String userName, MissionSaveRequestDto missionReqDto){

        User findUser = userRepository.findOneByName(userName);
        if(Objects.isNull(findUser)){
            throw new RuntimeException("존재하지 않는 사용자 닉네임입니다.");
        }

        Mission mission = Mission.builder()
                        .title(missionReqDto.getTitle())
                        .content(missionReqDto.getContent())
                        .imageUrl(missionReqDto.getImgUrl())
                        .build();
        mission.setUser(findUser);
        missionRepository.save(mission);
        String credential = String.valueOf(UUID.randomUUID());

        MissionSaveResponseDto responseDto = MissionSaveResponseDto.builder()
                .credential(credential).build();
        return responseDto;

        //미션 생성자를 바로 참여하는 로직 추가 필요
    }

    //Hot 미션 불러오기 ==//
    @Transactional(readOnly = true)
    public List<MissionHotListResponseDto> findHotList(){

        List<MissionHotListResponseDto> res = new ArrayList<>();

        List<Mission> hotLists = missionRepository.findAllByParticipantSize();
        for(Mission mission : hotLists){
            MissionHotListResponseDto hotMission = MissionHotListResponseDto.builder()
                    .title(mission.getTitle())
                    .content(mission.getContent())
                    .userName(mission.getUser().getName())
                    .startDate(mission.getStartDate())
                    .endDate(mission.getEndDate())
                    .build();

            res.add(hotMission);
        }
        return res;
    }

    //New 미션 불러오기 ==//

    //모든 미션 불러오기 ==//


}