package nts.uk.screen.at.app.kmt001;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.app.query.task.GetTaskFrameUsageSettingQuery;
import nts.uk.ctx.at.shared.app.query.task.GetTaskOperationSettingQuery;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.screen.at.app.query.kmt.kmt005.TaskFrameSettingDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * ScreenQuery: 起動時の情報取得処理
 */
@Stateless
public class InforAcquisitionProcessAtStartup {
    @Inject
    private GetTaskOperationSettingQuery operationSettingQuery;

    @Inject
    private GetTaskFrameUsageSettingQuery frameUsageSettingQuery;

    public Kmt001InitDto getInforAcquisition() {
        val cid = AppContexts.user().companyId();
        //1. 取得する(会社ID)
        val optOperationSetting = operationSettingQuery.getTasksOperationSetting(cid);
        //2. not 作業運用設定.isPresent or 作業運用設定.作業運用方法 == 利用しない
        if (!optOperationSetting.isPresent() || optOperationSetting.get().getTaskOperationMethod() == TaskOperationMethod.DO_NOT_USE)
            throw new BusinessException("Msg_2122");
        //5.取得(会社ID)
        val usageSetting = frameUsageSettingQuery.getWorkFrameUsageSetting(cid);
        //6.not 作業枠設定.isPresent
        if (usageSetting == null) {
            throw new BusinessException("Msg_2109", "KMT011_1");
        }
        return new Kmt001InitDto(
                optOperationSetting.get().getTaskOperationMethod(),
                usageSetting.getFrameSettingList()
                        .stream()
                        .map(s -> new TaskFrameSettingDto(
                                s.getTaskFrameNo().v(),
                                s.getTaskFrameName().v(),
                                s.getUseAtr().value
                        )).collect(Collectors.toList())
        );
    }
}