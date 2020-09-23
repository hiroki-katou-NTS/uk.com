package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.AchievementDetailDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;

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
                domain.getOpAchievementDetail().map(i -> AchievementDetailDto.fromDomain(i)).orElse(null)
        );
    }
}
