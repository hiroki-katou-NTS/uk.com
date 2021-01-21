package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTripInfoOutputCommand {

    // 出張申請設定
    private BusinessTripSetCommand setting;

    // 申請表示情報
    private AppDispInfoStartupDto appDispInfoStartup;

    // 休日勤務種類リスト
    private List<WorkTypeDto> holidays;

    // 出勤日勤務種類リスト
    private List<WorkTypeDto> workdays;

    // 出張の実績内容
    private List<BusinessTripActualContentCommand> businessTripActualContent;

    // 変更前勤務種類
    private List<BusinessTripWorkTypesCommand> infoBeforeChange;

    // 変更後勤務種類
    private List<BusinessTripWorkTypesCommand> infoAfterChange;

    public BusinessTripInfoOutput toDomain() {
        BusinessTripInfoOutput result = new BusinessTripInfoOutput();
        result.setSetting(this.getSetting() == null ? null : this.getSetting().toDomain());
        // Set empty list do liên quan đến phần khác
        result.setActualContentDisplay(Optional.ofNullable(Collections.emptyList()));
        result.setAppDispInfoStartup(this.getAppDispInfoStartup().toDomain());
        result.setHolidayCds(Optional.ofNullable(this.getHolidays().stream().map(i -> i.toDomain()).collect(Collectors.toList())));
        result.setWorkDayCds(Optional.ofNullable(this.getWorkdays().stream().map(i -> i.toDomain()).collect(Collectors.toList())));
        result.setWorkTypeBeforeChange(Optional.ofNullable(this.getInfoBeforeChange().stream().map(i -> i.toDomain()).collect(Collectors.toList())));
        result.setWorkTypeAfterChange(Optional.ofNullable(this.getInfoAfterChange().stream().map(i -> i.toDomain()).collect(Collectors.toList())));
        return result;
    }

}
