package nts.uk.ctx.at.shared.app.query.scherec.taskmanagement.taskframe;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskFrameSettingDto {
    private int frameNo;
    private String frameName;
    private int useAtr;
}
