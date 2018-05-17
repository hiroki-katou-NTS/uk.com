package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;

@Value
public class CalcFormulaItemCommand {
	/**
	 * 年間勤務表(36チェックリスト)の出力条件
	 */
	private String setOutCd;

	/**
	 * 帳表に出力する項目.コード
	 */
	private String itemOutCd;

	/**
	* 勤怠項目ID
	*/
	private int attendanceItemId;

	/**
	* オペレーション
	*/
	private int operation;
}
