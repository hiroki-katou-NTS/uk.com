package nts.uk.screen.at.app.query.kmt.kmt005;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskFrameSettingDto {
    private int frameNo;
    private String frameName;
    private int useAtr;
}
