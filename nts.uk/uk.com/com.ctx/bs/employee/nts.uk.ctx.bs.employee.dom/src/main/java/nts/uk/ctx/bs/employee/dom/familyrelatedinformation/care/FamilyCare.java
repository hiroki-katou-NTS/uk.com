package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class FamilyCare extends AggregateRoot {

	private String familyCareId;

	private String familyId;

	private String sid;

	private Period period;

	private SupportedCareClassifi careClassifi;

	public static FamilyCare createFromJavaType(String familyCareId, String familyId, String sid, GeneralDate startDate,
			GeneralDate endDate, int careClassifi) {
		return new FamilyCare(familyCareId, familyId, sid, new Period(startDate, endDate),
				EnumAdaptor.valueOf(careClassifi, SupportedCareClassifi.class));
	}
}
