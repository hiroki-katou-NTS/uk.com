package nts.uk.ctx.at.shared.app.query.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;

import java.util.List;

@AllArgsConstructor
@Getter
public class TaskDto {

    private String baseDate;

    private Integer taskFrameNo;
}

