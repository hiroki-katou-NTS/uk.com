package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDetailDto {

    /**
     * 1勤務種類コード
     */
    private String workTypeCD;

    /**
     * 2勤務種類名称
     */
    private String opWorkTypeName;

    /**
     * 3就業時間帯コード
     */
    private String workTimeCD;

    /**
     * 4就業時間帯名称
     */
    private String opWorkTimeName;

    /**
     * 5出勤時刻
     */
    private Integer opWorkTime;

    /**
     * 6退勤時刻
     */
    private Integer opLeaveTime;

    public static AchievementDetailDto fromDomain(AchievementDetail domain) {
        return new AchievementDetailDto(
                domain.getWorkTypeCD(),
                domain.getOpWorkTypeName().isPresent() ? domain.getOpWorkTypeName().get() : null,
                domain.getWorkTimeCD(),
                domain.getOpWorkTimeName().isPresent() ? domain.getOpWorkTimeName().get() : null,
                Optional.of(domain.getOpWorkTime().get()).orElse(null),
                Optional.of(domain.getOpLeaveTime().get()).orElse(null)
        );
    }

}
