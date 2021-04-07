package nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol;

import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByPerson;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByPersonRepository;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncByPerson;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncByPersonRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * <Query>スケジュール修正個人別の設定を取得する
 *
 * @author viet.tx
 */
@Stateless
public class ScheModifyIndividualSettingFinder {
    @Inject
    private ScheModifyAuthCtrlByPersonRepository scheModifyAuthCtrlByPersonRepo;

    @Inject
    private ScheModifyFuncByPersonRepository scheModifyFuncByPersonRepo;

    public ScheduleModifyByPersonDto get(String roleId) {
        String companyID = AppContexts.user().companyId();

        List<ScheModifyAuthCtrlByPerson> scheModifyAuthCtrlByPersons = scheModifyAuthCtrlByPersonRepo.getAllByRoleId(
                companyID, roleId);

//        List<Integer> functionNoList = new ArrayList<>();
//        if (!CollectionUtil.isEmpty(scheModifyAuthCtrlByPersons)) {
//            functionNoList = scheModifyAuthCtrlByPersons.stream().map(AvailabilityPermissionBase::getFunctionNo).collect(Collectors.toList());
//        }

        List<ScheModifyFuncByPerson> scheModifyFuncByPersons = scheModifyFuncByPersonRepo.getAll();

        return new ScheduleModifyByPersonDto(scheModifyAuthCtrlByPersons, scheModifyFuncByPersons);
    }
}
