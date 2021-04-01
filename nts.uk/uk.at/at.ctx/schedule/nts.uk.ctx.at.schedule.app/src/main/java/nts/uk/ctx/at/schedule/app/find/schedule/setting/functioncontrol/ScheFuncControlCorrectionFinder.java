package nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol;

import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControlRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * スケジュール修正の機能制御を取得する
 *
 * @author viet.tx
 */
@Stateless
public class ScheFuncControlCorrectionFinder {
    @Inject
    private ScheFunctionControlRepository repository;

    public Optional<ScheFunctionControl> getData(String companyId) {
        return this.repository.get(companyId);
    }
}
