package nts.uk.ctx.at.shared.dom.taskmanagement.repo.taskframe;

import nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.taskframe.TaskFrameUsageSetting;

/**
 *	作業枠利用設定 Repository
 */
public interface TaskFrameUsageSettingRepository {
    /**
     * insert
     *
     * @param taskFrameUsageSetting
     */
    void insert(TaskFrameUsageSetting taskFrameUsageSetting);

    /**
     * update
     *
     * @param taskFrameUsageSetting
     */
    void update(TaskFrameUsageSetting taskFrameUsageSetting);

    /**
     * 	作業枠利用設定を取得する
     *
     * @param cid
     */
    TaskFrameUsageSetting getWorkFrameUsageSetting(String cid);

}
