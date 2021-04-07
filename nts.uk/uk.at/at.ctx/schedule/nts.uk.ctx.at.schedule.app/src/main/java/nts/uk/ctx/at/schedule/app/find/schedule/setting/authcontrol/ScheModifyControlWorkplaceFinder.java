package nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol;

import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyAuthCtrlByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheModifyFuncByWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * <<Query>>スケジュール修正職場別の設定を取得する
 * @author viet.tx
 */
@Stateless
public class ScheModifyControlWorkplaceFinder {
    @Inject
    private ScheModifyAuthCtrlByWorkPlaceRepository scheModifyAuthCtrlByWkpRepo;

    @Inject
    private ScheModifyFuncByWorkplaceRepository scheModifyFuncByWkpRepo;

    public ScheModifyByWorkplaceDto get(String roleId){
        String companyID = AppContexts.user().companyId();
        List<ScheModifyAuthCtrlByWorkplace> scheModifyAuthCtrlByWkp = scheModifyAuthCtrlByWkpRepo.getAllByRoleId(companyID, roleId);

        List<ScheModifyFuncByWorkplace> scheModifyFuncByWorkplaces = scheModifyFuncByWkpRepo.getAll();

        return new ScheModifyByWorkplaceDto(scheModifyAuthCtrlByWkp, scheModifyFuncByWorkplaces);
    }
}
