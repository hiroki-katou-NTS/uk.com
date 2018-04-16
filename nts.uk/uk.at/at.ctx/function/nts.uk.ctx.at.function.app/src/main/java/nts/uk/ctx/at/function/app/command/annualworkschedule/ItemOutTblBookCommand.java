package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;

@Value
public class ItemOutTblBookCommand {
	/**
	* 会社ID
	*/
	private String cid;
	/**
	* コード
	*/
	private int code;
	/**
	* 並び順
	*/
	private int sortBy;
	/**
	* 使用区分
	*/
	private int useClass;
	/**
	* 値の出力形式
	*/
	private int valOutFormat;
	/**
	* 見出し名称
	*/
	private String headingName;

	private Long version;
}
