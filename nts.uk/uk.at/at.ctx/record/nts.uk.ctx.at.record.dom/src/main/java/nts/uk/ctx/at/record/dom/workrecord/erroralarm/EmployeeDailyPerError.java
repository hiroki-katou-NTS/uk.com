package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;

/**
 * 
 * @author nampt
 * 社員の日別実績エラー一覧
 */
@Getter
public class EmployeeDailyPerError extends AggregateRoot {
	
	private String companyID;
	
	private String employeeID;
	
	private GeneralDate date;
	
	// 勤務実績のエラーアラームコード
	private ErrorAlarmWorkRecordCode errorAlarmWorkRecordCode;
	
	// 勤怠項目ID
	private List<Integer> attendanceItemList;
	
	// エラー解除する
	private int errorCancelAble;

	public EmployeeDailyPerError(String companyID, String employeeID, GeneralDate date,
			ErrorAlarmWorkRecordCode errorAlarmWorkRecordCode, List<Integer> attendanceItemList, int errorCancelAble) {
		super();
		this.companyID = companyID;
		this.employeeID = employeeID;
		this.date = date;
		this.errorAlarmWorkRecordCode = errorAlarmWorkRecordCode;
		this.attendanceItemList = attendanceItemList;
		this.errorCancelAble = errorCancelAble;
	}

}
