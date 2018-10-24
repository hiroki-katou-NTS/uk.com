package nts.uk.ctx.at.shared.dom.specialholiday;

import java.util.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantPeriodic;
import nts.uk.shr.com.primitive.Memo;

/**
 * 特別休暇
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialHoliday extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/** 特別休暇名称 */
	private SpecialHolidayName specialHolidayName;
	
	/** 付与情報 */
	private GrantRegular grantRegular;
	
	/** 期限情報 */
	private GrantPeriodic grantPeriodic;
	
	/** 特別休暇利用条件 */
	private SpecialLeaveRestriction specialLeaveRestriction;

	/** 対象項目 */
	private TargetItem targetItem;
	
	/** メモ */
	private Memo memo;

	@Override
	public void validate() {
		super.validate();
	}

	public SpecialHoliday(String companyId, SpecialHolidayCode specialHolidayCode,
			SpecialHolidayName specialHolidayName, Memo memo) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.specialHolidayName = specialHolidayName;
		this.memo = memo;
	}

	public SpecialHoliday(String companyId, SpecialHolidayCode specialHolidayCode,
			SpecialHolidayName specialHolidayName, GrantRegular grantRegular, GrantPeriodic grantPeriodic,
			SpecialLeaveRestriction specialLeaveRestriction, Memo memo) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.specialHolidayName = specialHolidayName;
		this.grantRegular = grantRegular;
		this.grantPeriodic = grantPeriodic;
		this.specialLeaveRestriction = specialLeaveRestriction;
		this.memo = memo;
	}
	
	public static SpecialHoliday createFromJavaType(String companyId, int specialHolidayCode, String specialHolidayName, GrantRegular grantRegular, 
			GrantPeriodic grantPeriodic, SpecialLeaveRestriction specialLeaveRestriction, TargetItem targetItem, String memo) {
		return new SpecialHoliday(companyId, 
				new SpecialHolidayCode(specialHolidayCode),
				new SpecialHolidayName(specialHolidayName),
				grantRegular,
				grantPeriodic,
				specialLeaveRestriction,
				targetItem,
				new Memo(memo));
	}

	public static SpecialHoliday createFromJavaType(String companyId, int specialHolidayCode, String specialHolidayName, String memo) {
		return new SpecialHoliday(companyId, 
				new SpecialHolidayCode(specialHolidayCode),
				new SpecialHolidayName(specialHolidayName),
				new Memo(memo));
	}
	
	public static SpecialHoliday createFromJavaType(String companyId, int specialHolidayCode, String specialHolidayName, GrantRegular grantRegular, 
			GrantPeriodic grantPeriodic, SpecialLeaveRestriction specialLeaveRestriction, String memo) {
		return new SpecialHoliday(companyId, 
				new SpecialHolidayCode(specialHolidayCode),
				new SpecialHolidayName(specialHolidayName),
				grantRegular,
				grantPeriodic,
				specialLeaveRestriction,
				new TargetItem(),
				new Memo(memo));
	}
	
	public TargetItem getTargetItem() {
		return targetItem != null ? targetItem : new TargetItem(Collections.emptyList(), Collections.emptyList());
	}
	
}
