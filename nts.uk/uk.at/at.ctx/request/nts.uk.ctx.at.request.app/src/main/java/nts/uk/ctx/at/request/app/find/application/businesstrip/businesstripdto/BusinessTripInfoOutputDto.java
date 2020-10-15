package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.businesstrip.BusinessTripSetDto;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusinessTripInfoOutputDto {
    // 出張申請設定
    private BusinessTripSetDto setting;

    // 申請表示情報
    private AppDispInfoStartupDto appDispInfoStartup;

    // 休日勤務種類リスト
    private List<WorkTypeDto> holidays;

    // 出勤日勤務種類リスト
    private List<WorkTypeDto> workdays;

    // 出張の実績内容
    private List<BusinessTripActualContentDto> businessTripActualContent;

    // 変更前勤務種類
    private List<BusinessTripWorkTypesDto> infoBeforeChange;

    // 変更後勤務種類
    private List<BusinessTripWorkTypesDto> infoAfterChange;

    public static BusinessTripInfoOutputDto convertToDto(BusinessTripInfoOutput domain) {
        List<WorkTypeDto> holidayCds = domain.getHolidayCds().isPresent() ? domain.getHolidayCds().get().stream().map(i -> WorkTypeDto.fromDomain(i)).collect(Collectors.toList()) : Collections.emptyList();
        List<WorkTypeDto> wkDayCds = domain.getWorkDayCds().isPresent() ? domain.getWorkDayCds().get().stream().map(i -> WorkTypeDto.fromDomain(i)).collect(Collectors.toList()) : Collections.emptyList();
        List<BusinessTripWorkTypesDto> infoBefore = domain.getWorkTypeBeforeChange().isPresent() ? domain.getWorkTypeBeforeChange().get().stream().map(i -> BusinessTripWorkTypesDto.fromDomain(i)).collect(Collectors.toList()) : Collections.emptyList();
        List<BusinessTripWorkTypesDto> infoAfter = domain.getWorkTypeAfterChange().isPresent() ? domain.getWorkTypeAfterChange().get().stream().map(i -> BusinessTripWorkTypesDto.fromDomain(i)).collect(Collectors.toList()) : Collections.emptyList();
        List<BusinessTripActualContentDto> actualContents = domain.getActualContentDisplay().isPresent() ?  domain.getActualContentDisplay().get().stream().map(i -> BusinessTripActualContentDto.fromDomain(i))
                .collect(Collectors.toList()) : Collections.emptyList();
        BusinessTripSetDto setting = domain.getSetting() == null ? null : BusinessTripSetDto.fromDomain(domain.getSetting());
        return new BusinessTripInfoOutputDto(
                setting,
                AppDispInfoStartupDto.fromDomain(domain.getAppDispInfoStartup()),
                holidayCds,
                wkDayCds,
                actualContents,
                infoBefore,
                infoAfter
        );
    }

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
