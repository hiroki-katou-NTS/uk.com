package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppAcceptLimitDay;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.BeforehandRestriction;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class BeforehandRestrictionDto {
	/**
	 * 日数
	 */
	private int dateBeforehandRestrictions;
	
	/**
	 * 利用する
	 */
	private boolean toUse;
	
	public static BeforehandRestrictionDto fromDomain(BeforehandRestriction beforehandRestriction) {
		return new BeforehandRestrictionDto(
				beforehandRestriction.getDateBeforehandRestrictions().value, 
				beforehandRestriction.isToUse());
	}
	
	public BeforehandRestriction toDomain() {
		return new BeforehandRestriction(
				EnumAdaptor.valueOf(dateBeforehandRestrictions, AppAcceptLimitDay.class), 
				toUse);
	}
}
