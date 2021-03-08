package nts.uk.screen.at.app.kmt009;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.shared.app.query.task.GetTaskListOfSpecifiedWorkFrameNoQuery;
import nts.uk.ctx.at.shared.app.query.task.GetsTheChildTaskOfTheSpecifiedTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ScreenQuery: 指定された作業情報、下位作業絞込情報を取得する
 * UKDesign.UniversalK.就業.KMT_作業.KMT009_下位作業の絞り込み.Ａ：下位作業の絞り込み.Ａ：メニュー別OCD.指定された作業情報、下位作業絞込情報を取得する
 *
 * @author chinh.hm
 */
@Stateless
public class AcquiresSpecWorkAndSubNarrowInfoScreenQuery {
    @Inject
    GetsTheChildTaskOfTheSpecifiedTask getsTheChildTask;

    @Inject
    GetTaskListOfSpecifiedWorkFrameNoQuery getTaskListOfSpecifiedWork;

    public TaskDtos getTask(Integer frameNo, String code) {
        val cid = AppContexts.user().companyId();
        val listTask = getTaskListOfSpecifiedWork.getListTask(cid, frameNo);
        val listChild = getsTheChildTask.getAllChildTask(cid, frameNo, code);
        return new TaskDtos(
                getTaskDto(listTask),
                getTaskDto(listChild)


        );
    }

    @AllArgsConstructor
    @Getter
    class TaskDtos {
        //・作業リスト：List<作業>
        private List<TaskDto> listTask;
        //・子作業リスト：List<作業>             
        private List<TaskDto> listChildTask;
    }

    private List<TaskDto> getTaskDto(List<Task> taskList) {
        return taskList.stream().map(e ->
                new TaskDto(
                        e.getCode().v(),
                        e.getTaskFrameNo().v(),
                        new ExternalCooperationInfoDto(
                                e.getCooperationInfo().getExternalCode1().isPresent() ? e.getCooperationInfo().getExternalCode1().get().v() : null,
                                e.getCooperationInfo().getExternalCode2().isPresent() ? e.getCooperationInfo().getExternalCode2().get().v() : null,
                                e.getCooperationInfo().getExternalCode3().isPresent() ? e.getCooperationInfo().getExternalCode3().get().v() : null,
                                e.getCooperationInfo().getExternalCode4().isPresent() ? e.getCooperationInfo().getExternalCode4().get().v() : null,
                                e.getCooperationInfo().getExternalCode5().isPresent() ? e.getCooperationInfo().getExternalCode5().get().v() : null
                        ),
                        e.getChildTaskList().stream().map(PrimitiveValueBase::v).collect(Collectors.toList()),
                        e.getExpirationDate().start(),
                        e.getExpirationDate().end(),
                        new TaskDisplayInfoDto(
                                e.getDisplayInfo().getTaskName().v(),
                                e.getDisplayInfo().getTaskAbName().v(),
                                e.getDisplayInfo().getColor().isPresent() ? e.getDisplayInfo().getColor().get().v() : null,
                                e.getDisplayInfo().getTaskNote().isPresent() ? e.getDisplayInfo().getTaskNote().get().v() : null
                        )
                )
        ).collect(Collectors.toList());
    }
}
