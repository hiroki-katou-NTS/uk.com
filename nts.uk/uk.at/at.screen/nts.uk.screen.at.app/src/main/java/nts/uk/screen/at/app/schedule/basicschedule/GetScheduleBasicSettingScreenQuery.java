package nts.uk.screen.at.app.schedule.basicschedule;

import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.ScheFuncControlCorrectionFinder;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * スケジュール基本の設定を取得する
 * @author viet.tx
 */
@Stateless
public class GetScheduleBasicSettingScreenQuery {
    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private ScheFuncControlCorrectionFinder scheFuncCtrlFinder;

    // <<Public>> 廃止されていない勤務種類をすべて取得する
    String companyId = AppContexts.user().companyId();
    List<WorkType> lstWorkType = basicScheduleService.getAllWorkTypeNotAbolished(companyId);


}
