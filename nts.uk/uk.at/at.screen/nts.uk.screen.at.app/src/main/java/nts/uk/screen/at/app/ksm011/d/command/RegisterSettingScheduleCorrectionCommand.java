package nts.uk.screen.at.app.ksm011.d.command;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.uk.ctx.at.schedule.app.command.shift.table.ClosureDateType;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByWorkplaceRepository;
import nts.uk.ctx.at.schedule.dom.displaysetting.InitDispMonth;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionCtrlByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionCtrlByWorkplaceRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.ConditionATRWorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalConditionRepo;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.PersonInforDisplayControl;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <<Command>> スケジュール修正（職場別）の設定を登録する
 *
 * @author viet.tx
 */
@Stateless
public class RegisterSettingScheduleCorrectionCommand {
    @Inject
    private ScheFunctionCtrlByWorkplaceRepository scheFuncCtrlByWkpRepo;

    @Inject
    private DisplaySettingByWorkplaceRepository displaySettingByWkpRepo;

    @Inject
    private DisplayControlPersonalConditionRepo displayCtrlPersonalConditionRepo;

    public void registerSettingScheModifyByWork(RegisterSettingScheduleDto request) {
        String companyID = AppContexts.user().companyId();

        /** 1 - 2 - 3 - 4 - 5 **/
        // Create domain ScheFunctionCtrlByWorkplace
        ScheFunctionCtrlByWorkplace scheFuncCtrlByWorkplace = request.toScheFuncCtrlByWkpDomain();

        // get Optional<ScheFunctionCtrlByWorkplace>
        Optional<ScheFunctionCtrlByWorkplace> scheFuncCtrlWkp = scheFuncCtrlByWkpRepo.get(companyID);

        if (scheFuncCtrlWkp.isPresent()) {
            scheFuncCtrlByWkpRepo.update(companyID, scheFuncCtrlByWorkplace);
        } else {
            // persist
            scheFuncCtrlByWkpRepo.insert(companyID, scheFuncCtrlByWorkplace);
        }

        /** 6 - 7 - 8 **/
        // create DisplaySettingByWorkplace
        DisplaySettingByWorkplace displaySettingByWorkplace = new DisplaySettingByWorkplace(
                companyID
                , EnumAdaptor.valueOf(request.getInitDispMonth(), InitDispMonth.class)
                , new OneMonth(new DateInMonth(request.getEndDay(), EnumAdaptor.valueOf(request.getEndDay(), ClosureDateType.class) == ClosureDateType.LASTDAY)));

        // get Optional<DisplaySettingByWorkplace>
        Optional<DisplaySettingByWorkplace> displaySettingByWkp = displaySettingByWkpRepo.get(companyID);
        if (displaySettingByWkp.isPresent()) {
            displaySettingByWkpRepo.update(displaySettingByWorkplace);
        } else {
            displaySettingByWkpRepo.insert(displaySettingByWorkplace);
        }

        /** 9 - 10 - 11 - 12 **/
        List<PersonInforDisplayControl> listConditionDisplayControl = request.getConditionDisplayControls().stream()
                .map(x -> new PersonInforDisplayControl(EnumAdaptor.valueOf(x, ConditionATRWorkSchedule.class), NotUseAtr.USE)).collect(Collectors.toList());

        DisplayControlPersonalCondition displayCtrlPersonalCondition = DisplayControlPersonalCondition.get(
                companyID
                , listConditionDisplayControl
                , Optional.empty()
        );

        Optional<DisplayControlPersonalCondition> displayCtrlPersonal = displayCtrlPersonalConditionRepo.get(companyID);
        if (displayCtrlPersonal.isPresent()) {
            displayCtrlPersonalConditionRepo.update(displayCtrlPersonalCondition);
        } else {
            displayCtrlPersonalConditionRepo.insert(displayCtrlPersonalCondition);
        }
    }
}
