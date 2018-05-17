package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.List;

import lombok.Value;

/**
 * 帳表に出力する項目
 */
@Value
public class ItemOutTblBookCommand {
	/**
	* 年間勤務表(36チェックリスト)の出力条件.コード
	*/
	private String setOutCd;

	/**
	* コード
	*/
	private String cd;

	/**
	* 並び順
	*/
	private int sortBy;

	/**
	* 見出し名称
	*/
	private String headingName;

	/**
	* 使用区分
	*/
	private boolean useClass;

	/**
	* 値の出力形式
	*/
	private int valOutFormat;

	/**
	 * 36協定時間
	 */
	private boolean item36AgreementTime;

	List<CalcFormulaItemCommand> listOperationSetting;
}
