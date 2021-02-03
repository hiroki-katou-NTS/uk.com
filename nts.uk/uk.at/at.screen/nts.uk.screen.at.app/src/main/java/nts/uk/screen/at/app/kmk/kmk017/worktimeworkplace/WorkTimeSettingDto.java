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
    private String workTimeCode;

    private String workTimeName;

    /** The note. */
    // 備考
    private String note;


    public static WorkTimeSettingDto setData(WorkTimeSetting data) {
        return new WorkTimeSettingDto(
            data.getWorktimeCode().v(),
            data.getWorkTimeDisplayName().getWorkTimeName().v(),
            data.getNote().v()
        );
    }
}
