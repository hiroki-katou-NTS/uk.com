package nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol;

import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * <<Query>> スケジュール修正職場別の表示設定を取得する
 *
 * @author viet.tx
 */
@Stateless
public class DisplaySettingByWkpFinder {

    @Inject
    private DisplaySettingByWorkplaceRepository displaySettingByWkpRepo;

    public Optional<DisplaySettingByWorkplace> getDisplaySettingByWkp() {
        return displaySettingByWkpRepo.get(AppContexts.user().companyId());
    }
}
