package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.DesiredCourseApplicationAge;
import nts.uk.ctx.hr.shared.dom.enumeration.DateSelectItem;
import nts.uk.ctx.hr.shared.dom.enumeration.MonthSelectItem;

/**
 * @author thanhpv
 * 希望コース申請条件
 */
@AllArgsConstructor
@Getter
public class PlanCourseApplyTerm extends DomainObject{

	/** 申請開始年齢  */
	private DesiredCourseApplicationAge applicationEnableStartAge;
	
	/** 申請終了年齢 */
	private DesiredCourseApplicationAge applicationEnableEndAge;
	
	/** 申請終了月 */
	private MonthSelectItem endMonth;
	
	/** 申請終了日 */
	private DateSelectItem endDate;
	
	public static PlanCourseApplyTerm createFromJavaType(int applicationEnableStartAge, int applicationEnableEndAge, int endMonth, int endDate) {
		return new PlanCourseApplyTerm(
				new DesiredCourseApplicationAge(applicationEnableStartAge),
				new DesiredCourseApplicationAge(applicationEnableEndAge),
				EnumAdaptor.valueOf(endMonth, MonthSelectItem.class),
				EnumAdaptor.valueOf(endDate, DateSelectItem.class)
				);
	}
}
