package dailymissionproject.demo.domain.mission.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class MissionResponseDto {

    private String title;
    private String content;
    private String imgUrl;
    private String userName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder
    public MissionResponseDto(String title, String content, String imgUrl, String userName, LocalDate startDate, LocalDate endDate){
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}