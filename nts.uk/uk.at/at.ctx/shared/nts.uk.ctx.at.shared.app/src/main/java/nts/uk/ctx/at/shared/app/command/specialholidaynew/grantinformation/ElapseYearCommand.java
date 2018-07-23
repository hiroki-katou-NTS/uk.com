package nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation;

import lombok.Value;

@Value
public class ElapseYearCommand {
	/** 付与テーブルコード */
	private String grantDateCode;
	
	private int elapseNo;
	
	/** 付与テーブルコード */
	private Integer grantedDays;
	
	/** 付与テーブルコード */
	private Integer months;
	
	/** 付与テーブルコード */
	private Integer years;
}
