package nts.uk.screen.at.app.schedule.basicschedule;

import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.ScheFuncControlCorrectionFinder;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <ScreenQuery> スケジュール基本の設定を取得する
 *
 * @author viet.tx
 */
@Stateless
public class ScheduleBasicSettingScreenQuery {
    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private ScheFuncControlCorrectionFinder scheFuncCtrlFinder;

    public ScheduleBasicSettingDto getDataInit() {
        String companyId = AppContexts.user().companyId();
        // <<Public>> 廃止されていない勤務種類をすべて取得する
        List<WorkType> lstWorkType = basicScheduleService.getAllWorkTypeNotAbolished(companyId);

        Optional<ScheFunctionControl> scheduleFuncCtrl = scheFuncCtrlFinder.getData(companyId);
        if (!scheduleFuncCtrl.isPresent()) {
            return new ScheduleBasicSettingDto();
        }

        List<WorkTypeNameDto> workTypeNameList = lstWorkType.stream().map(x -> new WorkTypeNameDto(
                x.getWorkTypeCode().v()
                , x.getName().v()
                , x.getAbbreviationName() != null ? x.getAbbreviationName().v() : ""
        )).collect(Collectors.toList());

        return ScheduleBasicSettingDto.fromDomain(scheduleFuncCtrl.get(), workTypeNameList);
    }
}
