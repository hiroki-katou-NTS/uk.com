package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@Getter
public class GrantDatePer {

	/*会社ID*/
	private String companyId;

	/*付与基準日ID*/
	private String grantDateId;

	/*特別休暇コード*/
	private PersonalGrantDateCode personalGrantDateCode;

	/*特別休暇名称*/
	private PersonalGrantDateName personalGrantDateName;

	/*一律基準日*/
	private GrantDate grantDate;
 
	/*付与基準日*/
	private GrantDateAtr grantDateAtr;

	public static GrantDatePer createSimpleFromJavaType(String companyId, String grantDateId, String personalGrantDateCode, String personalGrantDateName,
			int grantDate, int grantDateAtr) {
		return new GrantDatePer(companyId,
				grantDateId,
				new PersonalGrantDateCode(personalGrantDateCode),
				new PersonalGrantDateName(personalGrantDateName),
				new GrantDate(grantDate),
				EnumAdaptor.valueOf(grantDateAtr, GrantDateAtr.class));

	}

}
