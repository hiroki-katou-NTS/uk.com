package nts.uk.ctx.at.shared.dom.workmanagement.repo.operationsettings;

import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.operationsettings.WorkOperationSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameUsageSetting;

import java.util.Optional;

/**
 * 作業運用設定Repository
 */
public interface WorkOperationSettingRepository {
    /**
     * insert
     *
     * @param workFrameUsageSetting
     */
    void insert(WorkOperationSetting workFrameUsageSetting);

    /**
     * update
     *
     * @param workFrameUsageSetting
     */
    void update(WorkOperationSetting workFrameUsageSetting);

    /**
     * 作業運用設定を取得する
     *
     * @param cid
     */
    Optional<WorkOperationSetting> getWorkOperationSetting(String cid);
}
