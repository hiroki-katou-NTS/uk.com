package nts.uk.screen.at.app.kmt001;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.app.query.task.GetTaskFrameUsageSettingQuery;
import nts.uk.ctx.at.shared.app.query.task.GetTaskListOfSpecifiedWorkFrameNoQuery;
import nts.uk.ctx.at.shared.app.query.task.GetTaskOperationSettingQuery;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * ScreenQuery: 起動時の情報取得処理
 */

@Stateless
public class InforAcquisitionProcessAtStartup {
    @Inject
    private GetTaskOperationSettingQuery operationSettingQuery;

    @Inject
    private GetTaskFrameUsageSettingQuery frameUsageSettingQuery;

    @Inject
    private AcquireWorkListAndWorkDetailsDisplayed acquireWorkListAndWorkDetailsDisplayed;

    private static final Integer FRAME_NO = 1;

    public KmtDto GetInforAcquisition() {
        val cid = AppContexts.user().companyId();
        //1. 取得する(会社ID)
        val optOperationSetting = operationSettingQuery.getTasksOperationSetting(cid);
        //2. not 作業運用設定.isPresent or 作業運用設定.作業運用方法 == 利用しない
        if (!optOperationSetting.isPresent() || optOperationSetting.get().getTaskOperationMethod() == TaskOperationMethod.DO_NOT_USE)
            throw new BusinessException("Msg_2122");
        val operationSetting = optOperationSetting.get();
        //3. 作業運用設定．作業運用法 == 実績で利用
        val usageSetting = frameUsageSettingQuery.getWorkFrameUsageSetting(cid);
        if (usageSetting == null) {
            throw new BusinessException("Msg_2109");
        }
        return acquireWorkListAndWorkDetailsDisplayed.getData(FRAME_NO, null);
    }
}

