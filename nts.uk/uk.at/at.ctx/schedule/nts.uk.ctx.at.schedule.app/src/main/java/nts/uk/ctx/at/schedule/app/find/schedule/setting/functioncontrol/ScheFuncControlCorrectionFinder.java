package nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol;

import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControlRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * スケジュール修正の機能制御を取得する
 */
@Stateless
public class ScheFuncControlCorrectionFinder {
    @Inject
    private ScheFunctionControlRepository repository;

    public Optional<ScheFunctionControl> get(){
        return this.repository.get(AppContexts.user().companyId());
    }
}
