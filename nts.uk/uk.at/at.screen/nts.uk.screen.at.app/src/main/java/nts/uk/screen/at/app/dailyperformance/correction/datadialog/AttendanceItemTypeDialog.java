package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceItemTypeDialog {
 
	private String attendanceItemId;
	
	/*DUTY(1, "勤務種類"),
	WORK_HOURS(2, "就業時間帯"),
	SERVICE_PLACE(3, "勤務場所"),
	REASON(4, "乖離理由"),
    WORKPLACE(5, "職場"),
    CLASSIFICATION(6, "分類")
    POSSITION(7, "職位")
    EMPLOYMENT(8, "雇用区分")*/
	private Integer typeDialog;
}
