package nts.uk.screen.at.app.kmt001;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class TaskResultDto {

    private List<Task> taskList;
    private Optional<Task> optionalTask;
}
