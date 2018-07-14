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

	/** 特別休暇コード */
	private int specialHolidayCode;
	
	/** 付与テーブルコード */
	private String grantDateCode;
	
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
}
