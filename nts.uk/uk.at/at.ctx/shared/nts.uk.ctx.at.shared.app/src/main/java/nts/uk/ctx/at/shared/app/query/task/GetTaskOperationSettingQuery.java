package nts.uk.ctx.at.shared.app.query.task;


import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings.TaskOperationSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Query: 作業運用設定を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.運用設定.App.作業運用設定を取得する.作業運用設定を取得して起動判定する
 *
 * @author chinh.hm
 */
@Stateless
public class GetTaskOperationSettingQuery {

    @Inject
    private TaskOperationSettingRepository repository;

    public Optional<TaskOperationSetting> getTasksOperationSetting(String cid) {
        return repository.getTasksOperationSetting(cid);
    }
}
