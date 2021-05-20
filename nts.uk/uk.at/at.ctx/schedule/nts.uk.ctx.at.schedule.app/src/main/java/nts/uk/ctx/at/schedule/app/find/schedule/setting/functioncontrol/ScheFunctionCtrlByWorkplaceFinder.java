package nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol;

import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionCtrlByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionCtrlByWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * <<Query>>スケジュール修正職場別の機能制御を取得する
 *
 * @author viet.tx
 */
@Stateless
public class ScheFunctionCtrlByWorkplaceFinder {
    @Inject
    private ScheFunctionCtrlByWorkplaceRepository scheFuncCtrlByWorkplaceRepo;

    public Optional<ScheFunctionCtrlByWorkplace> getScheFuncCtrlByWorkplace() {
        return scheFuncCtrlByWorkplaceRepo.get(AppContexts.user().companyId());
    }
}
