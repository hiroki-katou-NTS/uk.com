package nts.uk.screen.at.app.kmt001;

import lombok.val;
import nts.uk.ctx.at.shared.app.query.task.GetTaskListOfSpecifiedWorkFrameNoQuery;
import nts.uk.ctx.at.shared.app.query.task.GetsTheChildTaskOfTheSpecifiedTask;
package nts.uk.screen.at.app.kmt001.TaskResultDto;
import nts.uk.screen.at.app.kmt009.TaskDtos;
import nts.uk.shr.com.context.AppContexts;


import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 * ScreenQuery: 指定された作業枠NOの作業一覧及び表示する作業明細を取得する
 */
@Stateless
public class AcquireWorkListAndWorkDetailsDisplayed {
    @Inject
    GetsTheChildTaskOfTheSpecifiedTask getsTheChildTask;

    @Inject
    GetTaskListOfSpecifiedWorkFrameNoQuery getTaskListOfSpecifiedWork;

    public TaskResultDto getData(Integer frameNo, String code) {
        val cid = AppContexts.user().companyId();
        val taskList = getTaskListOfSpecifiedWork.getListTask(cid, frameNo);
        val optionalTask = getsTheChildTask.getAllChildTask(cid, frameNo, code);
        return new TaskResultDto(
                getKmtDto(taskList),
                getKmtDto(optionalTask)
        );
    }
}

