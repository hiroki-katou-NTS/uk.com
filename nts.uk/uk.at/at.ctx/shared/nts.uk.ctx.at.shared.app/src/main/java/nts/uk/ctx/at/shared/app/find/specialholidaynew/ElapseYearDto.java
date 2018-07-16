package nts.uk.ctx.at.shared.app.find.specialholidaynew;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.ElapseYear;

@Value
public class ElapseYearDto {
	/** 付与テーブルコード */
	private String grantDateCode;
	
	private int elapseNo;
	
	/** 付与テーブルコード */
	private BigDecimal grantedDays;
	
	/** 付与テーブルコード */
	private int months;
	
	/** 付与テーブルコード */
	private int years;
	
	public static ElapseYearDto fromDomain(ElapseYear elapseYear) {
		return new ElapseYearDto(
				elapseYear.getGrantDateCode(),
				elapseYear.getElapseNo(),
				elapseYear.getGrantedDays().v(),
				elapseYear.getMonths().v(),
				elapseYear.getYears().v()
		);
	}
}
