package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationMonths;

/**
 * 特別休暇付与経過年数テーブル
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ElapseYear extends AggregateRoot {
	
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;
	
	/** 経過年数テーブル */
	private List<ElapseYearMonthTbl> elapseYearMonthTblList;
	
	/** テーブル以降の固定付与をおこなう */
	private boolean fixedAssign;
	
	/** テーブル以降の付与周期 */
	private Optional<GrantCycleAfterTbl> grantCycleAfterTbl;
	
	
//	/** 会社ID */
//	private String companyId;
//	
//	/** 特別休暇コード */
//	private int specialHolidayCode;
//	
//	/** 付与テーブルコード */
//	private String grantDateCode;
//	
//	/** 付与回数 */
//	private int elapseNo;
//	
//	/** 特別休暇付与日数 */
//	private GrantedDays grantedDays;
//	
//	/**  */
//	private SpecialVacationMonths months;
//	
//	/**  */
//	private GrantedYears years;
	
	@Override
	public void validate() {
		super.validate();
	}

//	public static ElapseYear createFromJavaType(String companyId, int specialHolidayCode, String grantDateCode, int elapseNo, int grantedDays, int months, int years) {
//		return new ElapseYear(companyId, specialHolidayCode, grantDateCode, elapseNo, 
//				new GrantedDays(grantedDays),
//				new SpecialVacationMonths(months),
//				new GrantedYears(years));
//	}
	
}
