package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.List;

import lombok.Value;

/**
 * 年間勤務表(36チェックリスト)の出力条件
 */
@Value
public class SetOutItemsWoScCommand {
	/**
	 * 
	 */
	private String cid;

	/**
	* コード
	*/
	private String cd;

	/**
	* 名称
	*/
	private String name;

	/**
	* 36協定時間を超過した月数を出力する
	*/
	private boolean outNumExceedTime36Agr;

	/**
	* 表示形式
	*/
	private int displayFormat;

	private List<ItemOutTblBookCommand> listItemOutput;
}
