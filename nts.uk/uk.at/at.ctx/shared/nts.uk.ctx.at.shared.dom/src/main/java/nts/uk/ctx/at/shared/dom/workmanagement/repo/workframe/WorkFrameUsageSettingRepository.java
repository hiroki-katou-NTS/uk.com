package nts.uk.ctx.at.shared.dom.workmanagement.repo.workframe;

import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameUsageSetting;

/**
 *	作業枠利用設定 Repository
 */
public interface WorkFrameUsageSettingRepository {
    /**
     * insert
     *
     * @param workFrameUsageSetting
     */
    void insert(WorkFrameUsageSetting workFrameUsageSetting);

    /**
     * update
     *
     * @param workFrameUsageSetting
     */
    void update(WorkFrameUsageSetting workFrameUsageSetting);

    /**
     * 	作業枠利用設定を取得する
     *
     * @param cid
     */
    WorkFrameUsageSetting getWorkFrameUsageSetting(String cid);

}
