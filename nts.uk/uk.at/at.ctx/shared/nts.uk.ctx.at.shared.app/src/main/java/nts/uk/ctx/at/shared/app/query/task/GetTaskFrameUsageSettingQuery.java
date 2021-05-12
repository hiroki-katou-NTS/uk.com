package nts.uk.ctx.at.shared.app.query.task;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Query: 作業枠利用設定を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業枠.App.作業枠利用設定を取得する
 *
 * @author chinh.hm
 */
@Stateless
public class GetTaskFrameUsageSettingQuery {
    @Inject
    private TaskFrameUsageSettingRepository repository;

    public TaskFrameUsageSetting getWorkFrameUsageSetting(String cid) {
        return repository.getWorkFrameUsageSetting(cid);
    }
}
