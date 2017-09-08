package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;

@AllArgsConstructor
@Getter
public class GrantDatePer {

	/*会社ID*/
	private String companyId;

	/*付与日のID*/
	private String specialHolidayCode;

	/*特別休暇コード*/
	private PersonalGrantDateCode personalGrantDateCode;

	/*特別休暇名称*/
	private PersonalGrantDateName personalGrantDateName;

	/*一律基準日*/
	private GrantDate grantDate;
 
	/*付与基準日*/
	private GrantDateAtr grantDateAtr;
	
	private List<GrantDatePerSet> grantDatePerSet;

	public static GrantDatePer createSimpleFromJavaType(String companyId, String specialHolidayCode, String personalGrantDateCode, String personalGrantDateName,
			int grantDate, int grantDateAtr, List<GrantDatePerSet> grantDatePerSet) {
		return new GrantDatePer(companyId,
				specialHolidayCode,
				new PersonalGrantDateCode(personalGrantDateCode),
				new PersonalGrantDateName(personalGrantDateName),
				new GrantDate(grantDate),
				EnumAdaptor.valueOf(grantDateAtr, GrantDateAtr.class),
				grantDatePerSet);

	}

	public void validateInput() {
		for (int i = 0; i < this.grantDatePerSet.size(); i++) {
			GrantDatePerSet currentSet = this.grantDatePerSet.get(i);
			
			if(currentSet.getGrantDateYear().v() < 1){
				throw new BusinessException("Msg_144");
			}
			
			if(currentSet.getGrantDateYear().v() == 0 && currentSet.getGrantDateMonth().v() == 0){
				throw new BusinessException("Msg_95");
			}
		}
	}

}
