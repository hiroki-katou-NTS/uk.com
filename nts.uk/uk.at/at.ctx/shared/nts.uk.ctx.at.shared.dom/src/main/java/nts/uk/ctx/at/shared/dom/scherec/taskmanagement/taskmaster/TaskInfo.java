package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskInfo {
    private String code;

    // 作業枠NO
    private Integer taskFrameNo;

    private String name;
}
