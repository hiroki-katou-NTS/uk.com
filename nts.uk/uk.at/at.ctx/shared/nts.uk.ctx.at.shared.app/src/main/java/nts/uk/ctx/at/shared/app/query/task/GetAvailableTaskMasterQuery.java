package nts.uk.ctx.at.shared.app.query.task;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.shr.com.context.AppContexts;


import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.List;

/**
 * Query: 使用できる作業マスタを取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.App.使用できる作業マスタを取得する
 *
 * @author viet.tx
 */
@Stateless
public class GetAvailableTaskMasterQuery {
    @Inject
    private TaskingRepository taskingRepository;

    public List<Task> getListTask(String baseDate, List<TaskFrameNo> taskFrameNos) {
        val date = GeneralDate.fromString(baseDate,"yyyy/MM/dd");
        String companyId = AppContexts.user().companyId();
        return taskingRepository.getListTask(companyId, date, taskFrameNos);
    }
}
