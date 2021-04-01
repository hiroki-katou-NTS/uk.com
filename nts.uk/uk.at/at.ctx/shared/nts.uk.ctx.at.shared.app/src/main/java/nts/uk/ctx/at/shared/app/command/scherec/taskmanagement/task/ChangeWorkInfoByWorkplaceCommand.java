package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class ChangeWorkInfoByWorkplaceCommand {
    // 職場ID:
    private String workPlaceId;
    // Map<作業枠NO,List<作業コード>>
    private Map<Integer, List<String>> mapFrameAndCode;
}
