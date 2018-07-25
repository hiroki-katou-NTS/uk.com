package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;

@Value
public class ElapseYearDto {
	/** 付与テーブルコード */
	private String grantDateCode;
	
	private int elapseNo;
	
	/** 付与テーブルコード */
	private Integer grantedDays;
	
	/** 付与テーブルコード */
	private Integer months;
	
	/** 付与テーブルコード */
	private Integer years;

	public static ElapseYearDto fromDomain(ElapseYear domain) {
		if(domain == null) {
			return null;
		}
		
		return new ElapseYearDto( 
				domain.getGrantDateCode(),
				domain.getElapseNo(),
				domain.getGrantedDays().v(),
				domain.getMonths().v(),
				domain.getYears().v()
		);
	}
}
