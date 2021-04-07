package nts.uk.screen.at.app.kmt001;

import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.shared.app.query.task.GetTaskListOfSpecifiedWorkFrameNoQuery;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.screen.at.app.kmt009.ExternalCooperationInfoDto;
import nts.uk.screen.at.app.kmt009.TaskDisplayInfoDto;
import nts.uk.screen.at.app.kmt009.TaskDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * ScreenQuery: 指定された作業枠NOの作業一覧及び表示する作業明細を取得する
 */
@Stateless
public class AcquireWorkListAndWorkDetailsDisplayed {
    @Inject
    TaskingRepository getTaskOptional;

    @Inject
    GetTaskListOfSpecifiedWorkFrameNoQuery getTaskListOfSpecifiedWork;

    public TaskResultDto getData(String cid, Integer frameNo) {
        val listTask = getTaskListOfSpecifiedWork.getListTask(cid, frameNo);
        val taskFrameNo = new TaskFrameNo(frameNo);
        if (!listTask.isEmpty()) {
            val listCode = listTask.stream().flatMap(e -> e.getChildTaskList().stream()).collect(Collectors.toList());
            if (listCode.size() > 0) {
                val firstCode = listCode.get(0);
                val optionalTask = getTaskOptional.getOptionalTask(cid, taskFrameNo, firstCode);
                if (optionalTask.isPresent())
                    return new TaskResultDto(
                            getKmtDto(listTask),
                            Optional.of(optionalTask(optionalTask.get()))
                    );
            }

        }
        return null;

    }

    private List<KmtDto> getKmtDto(List<Task> taskList) {
        return taskList.stream().map(this::optionalTask).collect(Collectors.toList());

    }

    private KmtDto optionalTask(Task e) {
        val rs = new KmtDto(
                e.getCode().v(),
                e.getTaskFrameNo().v(),
                new ExternalCooperationInfoDto(
                        e.getCooperationInfo().getExternalCode1().isPresent() ? e.getCooperationInfo()
                                .getExternalCode1().get().v() : null,
                        e.getCooperationInfo().getExternalCode2().isPresent() ? e.getCooperationInfo()
                                .getExternalCode2().get().v() : null,
                        e.getCooperationInfo().getExternalCode3().isPresent() ? e.getCooperationInfo()
                                .getExternalCode3().get().v() : null,
                        e.getCooperationInfo().getExternalCode4().isPresent() ? e.getCooperationInfo()
                                .getExternalCode4().get().v() : null,
                        e.getCooperationInfo().getExternalCode5().isPresent() ? e.getCooperationInfo()
                                .getExternalCode5().get().v() : null
                ),
                e.getChildTaskList().stream().map(PrimitiveValueBase::v).collect(Collectors.toList()),
                e.getExpirationDate().start(),
                e.getExpirationDate().end(),
                new TaskDisplayInfoDto(
                        e.getDisplayInfo().getTaskName().v(),
                        e.getDisplayInfo().getTaskAbName().v(),
                        e.getDisplayInfo().getColor().isPresent() ? e.getDisplayInfo().getColor()
                                .get().v() : null,
                        e.getDisplayInfo().getTaskNote().isPresent() ? e.getDisplayInfo().getTaskNote()
                                .get().v() : null
                )
        );
        return rs;
    }
}


