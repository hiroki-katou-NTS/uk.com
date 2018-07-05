package nts.uk.ctx.at.shared.dom.specialholidaynew;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantRegular;
import nts.uk.shr.com.primitive.Memo;

/**
 * 特別休暇
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@Getter
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
	private Optional<Memo> memo;

	@Override
	public void validate() {
		super.validate();
	}
}
