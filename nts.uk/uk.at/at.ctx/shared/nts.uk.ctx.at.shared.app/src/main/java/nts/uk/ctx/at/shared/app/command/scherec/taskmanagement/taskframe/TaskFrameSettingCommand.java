package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskframe;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskFrameSettingCommand {
    private int frameNo;
    private String frameName;
    private boolean useAtr;
}
