package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkTimeSettingDto {

    /** The worktime code. */
    // コード
    private String worktimeCode;

    private String workTimeName;

    /** The abolish atr. */
    // 廃止区分
    private int abolishAtr;


    public static WorkTimeSettingDto setData(WorkTimeSetting data) {
        return new WorkTimeSettingDto(
            data.getWorktimeCode().v(),
            data.getWorkTimeDisplayName().getWorkTimeName().v(),
            data.getAbolishAtr().value
        );
    }
}
