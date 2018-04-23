package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class ItemOutTblBookCommand {
	/**
	* 会社ID
	*/
	private String cid;

	/**
	* コード
	*/
	private int cd;

	/**
	* コード
	*/
	private int setOutCd;

	/**
	* コード
	*/
	private int itemOutCd;

	/**
	* 使用区分
	*/
	private int useClass;

	/**
	* 値の出力形式
	*/
	private int valOutFormat;

	private Long version;
}
