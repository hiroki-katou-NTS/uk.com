package nts.uk.screen.at.app.kmt009;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.screen.at.app.kmt009.TaskDto;

import java.util.List;


@AllArgsConstructor
@Getter
public class TaskDtos {
    //・作業リスト：List<作業>
    private List<TaskDto> listTask;
    //・子作業リスト：List<作業>
    private List<TaskDto> listChildTask;
}
