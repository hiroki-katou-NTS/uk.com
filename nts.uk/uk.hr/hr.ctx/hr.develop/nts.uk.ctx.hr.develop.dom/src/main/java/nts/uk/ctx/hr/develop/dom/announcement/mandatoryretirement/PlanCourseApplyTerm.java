package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.DateSelectItem;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.MonthSelectItem;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_50_59;

/**
 * @author thanhpv
 * 希望コース申請条件
 */
@AllArgsConstructor
@Getter
public class PlanCourseApplyTerm extends DomainObject{

	/** 申請開始年齢  */
	private Integer_50_59 applicationEnableStartAge;
	
	/** 申請終了年齢 */
	private Integer_50_59 applicationEnableEndAge;
	
	/** 申請終了月 */
	private MonthSelectItem endMonth;
	
	/** 申請終了日 */
	private DateSelectItem endDate;
	
	public static PlanCourseApplyTerm createFromJavaType(int applicationEnableStartAge, int applicationEnableEndAge, int endMonth, int endDate) {
		return new PlanCourseApplyTerm(
				new Integer_50_59(applicationEnableStartAge),
				new Integer_50_59(applicationEnableEndAge),
				EnumAdaptor.valueOf(endMonth, MonthSelectItem.class),
				EnumAdaptor.valueOf(endDate, DateSelectItem.class)
				);
	}
}
