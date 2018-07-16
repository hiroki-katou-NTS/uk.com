package nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class ElapseYearCommand {
	/** 会社ID */
	private String companyId;
	
	/** 付与テーブルコード */
	private String grantDateCode;
	
	private int elapseNo;
	
	/** 付与テーブルコード */
	private BigDecimal grantedDays;
	
	/** 付与テーブルコード */
	private Integer months;
	
	/** 付与テーブルコード */
	private Integer years;
}
