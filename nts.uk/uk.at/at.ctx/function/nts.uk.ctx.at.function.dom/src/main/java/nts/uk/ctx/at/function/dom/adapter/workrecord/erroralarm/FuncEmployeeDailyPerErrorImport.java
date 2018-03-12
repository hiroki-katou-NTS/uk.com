package nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class FuncEmployeeDailyPerErrorImport {
	/** 会社ID: 会社ID */
	private String companyID;
	
	/** エラー発生社員: 社員ID*/
	private String employeeID;
	
	/** 処理年月日: 年月日*/
	private GeneralDate date;
	
	/** エラー: 勤務実績のエラーアラームコード*/
	private String errorAlarmWorkRecordCode;
	
	/** 項目一覧: 勤怠項目ID */
	private List<Integer> attendanceItemList;
//	
	// エラー解除する
	private int errorCancelAble;
}
