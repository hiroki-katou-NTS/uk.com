package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.AchievementDetailDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTripActualContentCommand {

    /**
     * 年月日
     */
    private String date;

    /**
     * 実績詳細
     */
    private AchievementDetailDto opAchievementDetail;


}
