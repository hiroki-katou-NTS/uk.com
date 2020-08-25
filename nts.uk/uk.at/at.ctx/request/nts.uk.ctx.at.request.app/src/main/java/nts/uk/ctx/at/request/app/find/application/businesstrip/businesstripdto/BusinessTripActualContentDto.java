package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTripActualContentDto {

    /**
     * 年月日
     */
    private String date;

    /**
     * 実績詳細
     */
    private AchievementDetailDto opAchievementDetail;

    public static BusinessTripActualContentDto fromDomain(ActualContentDisplay domain) {
        return new BusinessTripActualContentDto(
                domain.getDate().toString(),
                domain.getOpAchievementDetail().isPresent() ? AchievementDetailDto.fromDomain(domain.getOpAchievementDetail().get()) : null
        );
    }
}
