package nts.uk.screen.at.app.kmt010;

import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.shared.app.query.task.SpecifyWplAndNarrowDownOfTaskByWplQuery;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.screen.at.app.kmt009.ExternalCooperationInfoDto;
import nts.uk.screen.at.app.kmt009.TaskDisplayInfoDto;
import nts.uk.screen.at.app.kmt009.TaskDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ScreenQuery: 選択されている対象職場に登録されている作業情報を取得する
 */

@Stateless
public class GetTaskInfoRegistSelectedTargetWplScreenQuery {
    @Inject
    private SpecifyWplAndNarrowDownOfTaskByWplQuery taskByWplQuery;

    @Inject
    private TaskingRepository taskingRepository;

    public Map<Integer, List<TaskDto>> getTaskSelected(String workPlaceId) {
        Map<Integer, List<TaskDto>> rs = new HashMap<>();
        val listNarrow = taskByWplQuery.getListWorkByWpl(workPlaceId);
        val cid = AppContexts.user().companyId();
        listNarrow.forEach(e -> {
            List<Task> listTask = taskingRepository.getListTask(cid,
                    e.getTaskFrameNo(), e.getTaskCodeList());
            rs.put(e.getTaskFrameNo().v(), getTaskDto(listTask));
        });
        return rs;
    }
    private List<TaskDto> getTaskDto(List<Task> taskList) {
        return taskList.stream().map(e ->
                new TaskDto(
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
                )
        ).collect(Collectors.toList());
    }
}
