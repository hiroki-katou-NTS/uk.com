package nts.uk.ctx.at.schedule.app.find.schedule.setting.authcontrol;

import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheAuthModifyDeadline;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheAuthModifyDeadlineRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * <Query>スケジュール修正の修正期限を取得する
 *
 * @author viet.tx
 */
@Stateless
public class ScheduleAuthModifyDeadlineFinder {
    @Inject
    private ScheAuthModifyDeadlineRepository scheAuthModifyDeadlineRepo;

    public Optional<ScheAuthModifyDeadline> get(String roleId) {
        return scheAuthModifyDeadlineRepo.get(
                AppContexts.user().companyId()
                , roleId);
    }
}
