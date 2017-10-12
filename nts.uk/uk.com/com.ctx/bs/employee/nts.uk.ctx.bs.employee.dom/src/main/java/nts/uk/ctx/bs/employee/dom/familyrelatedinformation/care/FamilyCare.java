package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class FamilyCare extends AggregateRoot {
	/**
	 * domain : 家族介護
	 */
	/** 家族介護ID*/
	private String familyCareId;
	/** 家族ID */
	private String familyId;
	/** 社員ID */
	private String sid;
	/** 期間 */
	private Period period;
	/** 支援介護区分*/
	private SupportedCareClassifi careClassifi;

	public static FamilyCare createFromJavaType(String familyCareId, String familyId, String sid, GeneralDate startDate,
			GeneralDate endDate, int careClassifi) {
		return new FamilyCare(familyCareId, familyId, sid, new Period(startDate, endDate),
				EnumAdaptor.valueOf(careClassifi, SupportedCareClassifi.class));
	}
}
