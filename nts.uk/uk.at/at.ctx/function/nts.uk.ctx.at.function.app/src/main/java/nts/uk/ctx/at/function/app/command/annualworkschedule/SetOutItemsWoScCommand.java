package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SetOutItemsWoScCommand {
	/**
	* 会社ID
	*/
	private String cid;
	/**
	* コード
	*/
	private int cd;
	/**
	* 36協定の表示設定
	*/
	private int dispSettAgr36;
	/**
	* 名称
	*/
	private String name;
	/**
	* 
	*/
	private int outNumExceedTime36Agr;
	/**
	* 
	*/
	private int displayFormat;
	
	private Long version;

}
