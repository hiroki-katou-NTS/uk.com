package nts.uk.screen.at.app.kmt010;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.app.query.task.GetAllTaskRefinementsByWorkplaceQuery;
import nts.uk.ctx.at.shared.app.query.task.GetTaskFrameUsageSettingQuery;
import nts.uk.ctx.at.shared.app.query.task.GetTaskOperationSettingQuery;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.screen.at.app.query.kmt.kmt005.TaskFrameSettingDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 起動時の情報取得処理
 * UKDesign.UniversalK.就業.KMT_作業.KMT010_職場別作業の登録.Ａ：職場別作業の登録.Ａ：メニュー別OCD.起動時の情報取得処理
 *
 * @author :chinh.hm
 */
@Stateless
public class TaskByWorkPlaceProcessStartupScreenQuery {
    @Inject
    private GetTaskOperationSettingQuery getTaskOperationSettingQuery;

    @Inject
    private GetTaskFrameUsageSettingQuery frameUsageSettingQuery;

    public List<TaskFrameSettingDto> getData() {
        val cid = AppContexts.user().companyId();
        val optSetting = getTaskOperationSettingQuery.getTasksOperationSetting(cid);

        if (!optSetting.isPresent() || optSetting.get().getTaskOperationMethod().value == TaskOperationMethod.DO_NOT_USE.value)
            throw new BusinessException("Msg_2122");
        if (optSetting.get().getTaskOperationMethod().value != TaskOperationMethod.USED_IN_ACHIEVENTS.value)
            throw new BusinessException("Msg_2114");
        val frameSetting = frameUsageSettingQuery.getWorkFrameUsageSetting(cid);
        if (frameSetting == null)
            throw new BusinessException("Msg_2109");
        return frameSetting.getFrameSettingList()
                .stream()
                .map(s -> new TaskFrameSettingDto(
                        s.getTaskFrameNo().v(),
                        s.getTaskFrameName().v(),
                        s.getUseAtr().value
                )).collect(Collectors.toList());
    }
}
