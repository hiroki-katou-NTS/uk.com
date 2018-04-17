package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.ArrayList;
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
	
	/** 会社ID: 会社ID */
	private String companyID;
	
	/** エラー発生社員: 社員ID*/
	private String employeeID;
	
	/** 処理年月日: 年月日*/
	private GeneralDate date;
	
	/** エラー: 勤務実績のエラーアラームコード*/
	private ErrorAlarmWorkRecordCode errorAlarmWorkRecordCode;
	
	/** 項目一覧: 勤怠項目ID */
	private List<Integer> attendanceItemList;
//	
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

	public EmployeeDailyPerError(String companyID, String employeeID, GeneralDate date,
			ErrorAlarmWorkRecordCode errorAlarmWorkRecordCode, List<Integer> attendanceItemList) {
		super();
		this.companyID = companyID;
		this.employeeID = employeeID;
		this.date = date;
		this.errorAlarmWorkRecordCode = errorAlarmWorkRecordCode;
		this.attendanceItemList = attendanceItemList;
		this.errorCancelAble = 0;
	}
	
	public EmployeeDailyPerError(String companyID, String employeeID, GeneralDate date,
			ErrorAlarmWorkRecordCode errorAlarmWorkRecordCode, Integer attendanceItem) {
		super();
		this.companyID = companyID;
		this.employeeID = employeeID;
		this.date = date;
		this.errorAlarmWorkRecordCode = errorAlarmWorkRecordCode;
		List<Integer> attendanceItemList = new ArrayList<>();
		attendanceItemList.add(attendanceItem);
		this.attendanceItemList = attendanceItemList;
		this.errorCancelAble = 0;
	}
}
