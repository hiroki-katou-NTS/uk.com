package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;

import java.util.Optional;

/**
 * 作業運用設定Repository
 */
public interface TaskOperationSettingRepository {
    /**
     * insert
     *
     * @param workFrameUsageSetting
     */
    void insert(TaskOperationSetting workFrameUsageSetting);

    /**
     * update
     *
     * @param workFrameUsageSetting
     */
    void update(TaskOperationSetting workFrameUsageSetting);

    /**
     * 作業運用設定を取得する
     *
     * @param cid
     */
    Optional<TaskOperationSetting> getTasksOperationSetting(String cid);
}
