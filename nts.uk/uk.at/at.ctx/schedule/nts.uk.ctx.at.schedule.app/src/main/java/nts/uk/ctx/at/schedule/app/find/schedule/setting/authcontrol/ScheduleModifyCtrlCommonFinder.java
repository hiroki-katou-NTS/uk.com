package nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol;

import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlCommon;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlCommonRepository;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncCommon;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncCommonRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * <<Query>>スケジュール修正の共通の設定を取得する
 *
 * @author viet.tx
 */
@Stateless
public class ScheduleModifyCtrlCommonFinder {
    @Inject
    private ScheModifyAuthCtrlCommonRepository scheModifyAuthCtrlCommonRepo;

    @Inject
    private ScheModifyFuncCommonRepository scheModifyFuncCommonRepo;

    public ScheModifyCommonDto getScheModifyCommon(String roleId) {
        String companyID = AppContexts.user().companyId();

        // get(会社ID、ロールID) : List<スケジュール修正の共通の権限>
        List<ScheModifyAuthCtrlCommon> scheModifyAuthCtrlCommons = scheModifyAuthCtrlCommonRepo.getAllByRoleId(companyID, roleId);

//        List<Integer> functionNoList = new ArrayList<>();
//        if (!CollectionUtil.isEmpty(scheModifyAuthCtrlCommons)) {
//            functionNoList = scheModifyAuthCtrlCommons.stream().map(AvailabilityPermissionBase::getFunctionNo).collect(Collectors.toList());
//        }

        // get(List<NO>) : List<スケジュール修正の共通の機能>
        List<ScheModifyFuncCommon> scheModifyFuncCommons = scheModifyFuncCommonRepo.getAll();

        return new ScheModifyCommonDto(scheModifyAuthCtrlCommons, scheModifyFuncCommons);
    }
}
