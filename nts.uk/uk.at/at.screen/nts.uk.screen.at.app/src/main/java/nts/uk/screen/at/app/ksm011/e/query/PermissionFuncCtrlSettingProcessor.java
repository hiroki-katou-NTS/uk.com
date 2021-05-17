package nts.uk.screen.at.app.ksm011.e.query;

import lombok.val;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol.*;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheAuthModifyDeadline;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * <<ScreenQuery>> 権限機能制御の設定を取得する
 *
 * @author viet.tx
 */
@Stateless
public class PermissionFuncCtrlSettingProcessor {
    @Inject
    private ScheduleModifyCtrlCommonFinder commonFinder;

    @Inject
    private ScheduleAuthModifyDeadlineFinder deadlineFinder;

    @Inject
    private ScheModifyControlWorkplaceFinder workPlaceFinder;

    @Inject
    private ScheModifyIndividualSettingFinder personFinder;

    public PermissionFuncCtrlSettingDto getPermissionFunctionSetting(String roleId) {
        val scheModifyCommon = commonFinder.getScheModifyCommon(roleId);
        Optional<ScheAuthModifyDeadline> deadline = deadlineFinder.get(roleId);
        val scheModifyByWorkplace = workPlaceFinder.get(roleId);
        val scheduleModifyByPerson = personFinder.get(roleId);

        return new PermissionFuncCtrlSettingDto(
                scheModifyCommon,
                deadline.isPresent() ? deadline.get().getUseAtr().value : null,
                deadline.isPresent() ? deadline.get().getDeadLine().v() : null,
                scheModifyByWorkplace,
                scheduleModifyByPerson
        );
    }
}
