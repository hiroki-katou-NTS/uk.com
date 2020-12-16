package nts.uk.file.at.app.export.attendancerecord;

import lombok.Data;

// UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.出勤簿.アルゴリズム.出勤簿の出力項目設定.(UK2)出勤簿を出力する.月別実績の月初と月末の雇用コードをチェックする
/**
 * The Class MonthlyResultCheck.
 */
@Data
public class MonthlyResultCheck {
	//	雇用取得結果
	// false(NG)(取得できなかった)、true(OK)(取得できた)
	private boolean employeeResult;
	
	//	チェック結果
	// false(NG)(不一致)、true(OK)(一致)
	private boolean checkResult;
}
