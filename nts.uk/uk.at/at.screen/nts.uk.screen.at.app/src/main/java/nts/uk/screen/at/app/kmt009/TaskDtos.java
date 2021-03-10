package nts.uk.screen.at.app.kmt.kmt009;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public class TaskDtos {
    //・作業リスト：List<作業>
    private List<TaskDto> listTask;
    //・子作業リスト：List<作業>
    private List<TaskDto> listChildTask;
}
