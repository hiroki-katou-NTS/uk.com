package nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * アラームリスト抽出従業員エラー
 */
@Getter
@AllArgsConstructor
public class ExtractEmployeeErAlData {

	/** 実行ID */
	private String executeId;
	
	/** 従業員ID */
	private String employeeId;
	
	/** レコードID */
	private String recordId;

	/** アラーム値日付 */
	private String alarmTime;		
	
	/** カテゴリーコード */
	private int categoryCode;	
	
	/** カテゴリー名 */
	private String categoryName;
	
	/** アラーム項目 */
	private String alarmItem;	
	
	/** アラーム値メッセージ */
	private String alarmMes;	
	
	/** コメント */
	private String comment;	
	
	/** チェック対象値 */
	private String checkedValue;
	
	/** 終了日 */
	private String endDate;
	
	public List<Object> createToList(){
		return Arrays.asList(employeeId, recordId, alarmTime, categoryName, 
								alarmItem, alarmMes, comment,checkedValue,categoryCode);
	}
}
