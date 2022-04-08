package nts.uk.screen.at.app.kha002;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemDto;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.FormCanUsedForTime;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSettingRepository;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.ClosureAdapter;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class Kha002ScreenQuery {
    @Inject
    private SupportOperationSettingRepository sosRepository;

    @Inject
    private SupportWorkAggregationSettingRepository swasRepository;

    @Inject
    private ClosureAdapter closureAdapter;

    @Inject
    private AttendanceItemNameService attendanceItemNameService;

    @Inject
    private CompanyDailyItemService dailyItemService;

    public Kha002InitDto initScreenA() {
        String companyId = AppContexts.user().companyId();
        SupportOperationSetting domain = sosRepository.get(companyId);
        if (!domain.isUsed()) throw new BusinessException("Msg_3240");
        Optional<SupportWorkAggregationSetting> setting = swasRepository.get(companyId);
        if (!setting.isPresent()) {
            if (!AppContexts.user().roles().isInChargeAttendance())
                throw new BusinessException("Msg_3268");
        }
        Optional<PresentClosingPeriodImport> presentClosingPeriodOpt = closureAdapter.findByClosureId(companyId, 1);
        return presentClosingPeriodOpt.map(i -> new Kha002InitDto(i.getClosureStartDate(), i.getClosureEndDate())).orElse(null);
    }

    public List<AttendanceItemDto> getAttendanceItems() {
        String companyId = AppContexts.user().companyId();
        List<Integer> attendanceIdList = attendanceItemNameService.getDailyAttendanceItemsAvaiable(companyId, FormCanUsedForTime.WORK_SUPPORT, TypeOfItem.Daily);
        return dailyItemService.getDailyItems(companyId, Optional.empty(), attendanceIdList, null)
                .stream().map(i -> AttendanceItemDto.builder()
                        .attendanceItemId(i.getAttendanceItemId())
                        .attendanceItemName(i.getAttendanceItemName())
                        .attendanceAtr(i.getAttendanceAtr())
                        .displayNumbers(i.getAttendanceItemDisplayNumber())
                        .build()
                ).collect(Collectors.toList());
    }
}
