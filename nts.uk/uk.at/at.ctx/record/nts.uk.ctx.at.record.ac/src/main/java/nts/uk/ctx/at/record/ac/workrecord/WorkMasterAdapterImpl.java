package nts.uk.ctx.at.record.ac.workrecord;

import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.TaskImport;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.WorkMasterAdapter;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.ctx.at.shared.pub.workmanagement.workmaster.TaskPubExport;
import nts.uk.ctx.at.shared.pub.workmanagement.workmaster.WorkMasterPub;
import nts.uk.shr.com.color.ColorCode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class WorkMasterAdapterImpl implements WorkMasterAdapter {
    @Inject
    private WorkMasterPub workMasterPub;

    @Override
    public List<TaskImport> getTaskList(String cid, Integer taskFrameNo, List<String> codes) {
        List<TaskPubExport> taskExportList = workMasterPub.getListTask(cid, taskFrameNo, codes);

        return taskExportList.stream().map(t -> new TaskImport(
                t.getCode(),
                t.getTaskFrameNo(),
                t.getDisplayInfo().getTaskName()
        )).collect(Collectors.toList());
    }

    private List<Task> convertToTask(List<TaskPubExport> data) {
        return data.stream().map(x -> new Task(
                new TaskCode(x.getCode()),
                new TaskFrameNo(x.getTaskFrameNo()),
                new ExternalCooperationInfo(
                        x.getCorporationInfo().getExternalCode1() != null ? Optional.of(new TaskExternalCode(x.getCorporationInfo().getExternalCode1())) : Optional.empty(),
                        x.getCorporationInfo().getExternalCode2() != null ? Optional.of(new TaskExternalCode(x.getCorporationInfo().getExternalCode2())) : Optional.empty(),
                        x.getCorporationInfo().getExternalCode3() != null ? Optional.of(new TaskExternalCode(x.getCorporationInfo().getExternalCode3())) : Optional.empty(),
                        x.getCorporationInfo().getExternalCode4() != null ? Optional.of(new TaskExternalCode(x.getCorporationInfo().getExternalCode4())) : Optional.empty(),
                        x.getCorporationInfo().getExternalCode5() != null ? Optional.of(new TaskExternalCode(x.getCorporationInfo().getExternalCode5())) : Optional.empty()
                ),
                x.getChildTaskList().stream().map(TaskCode::new).collect(Collectors.toList()),
                x.getExpirationDate(),
                new TaskDisplayInfo(
                        new TaskName(x.getDisplayInfo().getTaskName()),
                        new TaskAbName(x.getDisplayInfo().getTaskAbName()),
                        x.getDisplayInfo().getColor() != null ? Optional.of(new ColorCode(x.getDisplayInfo().getColor())) : Optional.empty(),
                        x.getDisplayInfo().getTaskNote() != null ? Optional.of(new TaskNote(x.getDisplayInfo().getTaskNote())) : Optional.empty()

                )
        )).collect(Collectors.toList());
    }
}
