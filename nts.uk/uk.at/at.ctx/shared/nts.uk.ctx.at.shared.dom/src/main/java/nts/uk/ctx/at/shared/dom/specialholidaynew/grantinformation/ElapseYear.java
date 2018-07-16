package nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.specialholidaynew.periodinformation.SpecialVacationMonths;

/**
 * 経過年数に対する付与日数
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElapseYear extends DomainObject {
	/** 会社ID */
	private String companyId;
	
	/** 付与テーブルコード */
	private String grantDateCode;
	
	private int elapseNo;
	
	/** 付与テーブルコード */
	private GrantedDays grantedDays;
	
	/** 付与テーブルコード */
	private SpecialVacationMonths months;
	
	/** 付与テーブルコード */
	private GrantedYears years;
	
	@Override
	public void validate() {
		super.validate();
	}

	public static ElapseYear createFromJavaType(String grantDateCode, int elapseNo, int grantedDays, int months, int years) {
		return new ElapseYear(grantDateCode, elapseNo, 
				new GrantedDays(grantedDays),
				new SpecialVacationMonths(months),
				new GrantedYears(years));
	}

	public ElapseYear(String grantDateCode, int elapseNo, GrantedDays grantedDays, SpecialVacationMonths months, GrantedYears years) {
		this.grantDateCode = grantDateCode;
		this.elapseNo = elapseNo;
		this.grantedDays = grantedDays;
		this.months = months;
		this.years = years;
	}
}
