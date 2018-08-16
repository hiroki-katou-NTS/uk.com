package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationMonths;

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
	
	/** 特別休暇コード */
	private int specialHolidayCode;
	
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

	public static ElapseYear createFromJavaType(String companyId, int specialHolidayCode, String grantDateCode, int elapseNo, int grantedDays, int months, int years) {
		return new ElapseYear(companyId, specialHolidayCode, grantDateCode, elapseNo, 
				new GrantedDays(grantedDays),
				new SpecialVacationMonths(months),
				new GrantedYears(years));
	}
}
