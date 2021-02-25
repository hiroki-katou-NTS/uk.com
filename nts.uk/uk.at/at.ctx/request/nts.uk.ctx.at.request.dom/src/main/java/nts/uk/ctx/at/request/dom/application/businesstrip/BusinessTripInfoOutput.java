package nts.uk.ctx.at.request.dom.application.businesstrip;

import lombok.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripWorkTypes;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 出張申請の表示情報
public class BusinessTripInfoOutput {

    // 出張申請設定
    private AppTripRequestSet setting;

    // 申請表示情報
    private AppDispInfoStartupOutput appDispInfoStartup;

    // 休日勤務種類リスト
    private Optional<List<WorkType>> holidayCds;

    // 出勤日勤務種類リスト
    private Optional<List<WorkType>> workDayCds;

    // 出張の実績内容
    private Optional<List<ActualContentDisplay>> actualContentDisplay;

    // 変更前勤務種類
    private Optional<List<BusinessTripWorkTypes>> workTypeBeforeChange;

    // 変更後勤務種類
    private Optional<List<BusinessTripWorkTypes>> workTypeAfterChange;

}
