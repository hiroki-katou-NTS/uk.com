package nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol;

import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalConditionRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * <<Query>> 個人条件の表示制御を取得する
 *
 * @author viet.tx
 */
@Stateless
public class DisplayCtrlPersonalConditionFinder {
    @Inject
    private DisplayControlPersonalConditionRepo displayControlPersonalConditionRepo;

    public Optional<DisplayControlPersonalCondition> getDisplayCtrlPersonalCond() {
        return displayControlPersonalConditionRepo.get(AppContexts.user().companyId());
    }
}
