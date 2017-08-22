	package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@AllArgsConstructor
@Getter	
public class GrantDateCom {

	/*会社ID*/
	private String companyId;

	/*付与日のID*/
	private SpecialHolidayCode specialHolidayCode;

	/*付与基準日*/
	private GrantDateAtr grantDateAtr;

	/*一律基準日*/
	private GrantDate grantDate;
	
	private List<GrantDateSet> grantDateSets;
	
	public static GrantDateCom createFromJavaType(String companyId, String specialHolidayCode, int grantDateAtr,
			int grantDate) {
		return new GrantDateCom(companyId,
				new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(grantDateAtr, GrantDateAtr.class),
				new GrantDate(grantDate));
	}
}
