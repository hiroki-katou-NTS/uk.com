package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;

/**
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@Getter
public class GrantDatePer extends DomainObject {

	/*会社ID*/
	private String companyId;

	/*付与日のID*/
	private int specialHolidayCode;

	/*特別休暇コード*/
	private PersonalGrantDateCode personalGrantDateCode;

	/*特別休暇名称*/
	private PersonalGrantDateName personalGrantDateName;
	
	private int provision;

	/*一律基準日*/
	private GeneralDate grantDate;
 
	/*付与基準日*/
	private GrantDateAtr grantDateAtr;
	
	private List<GrantDatePerSet> grantDatePerSet;

	/* 
	 * Create from java type
	 */
	public static GrantDatePer createSimpleFromJavaType(String companyId, int specialHolidayCode, String personalGrantDateCode, String personalGrantDateName,
			int provision, GeneralDate grantDate, int grantDateAtr, List<GrantDatePerSet> grantDatePerSet) {
		return new GrantDatePer(companyId,
				specialHolidayCode,
				new PersonalGrantDateCode(personalGrantDateCode),
				new PersonalGrantDateName(personalGrantDateName),
				provision,
				grantDate,
				EnumAdaptor.valueOf(grantDateAtr, GrantDateAtr.class),
				grantDatePerSet);

	}

	/* 
	 * Validate function
	 */
	public List<String> validateInput() {
		List<String> errors = new ArrayList<>();
		
		// 経過年数は必ず１件以上必要
		if(CollectionUtil.isEmpty(this.grantDatePerSet)){
			errors.add("Msg_144");
		}
					
		Set<Integer> grantDateYears = new HashSet<>();
		for (int i = 0; i < this.grantDatePerSet.size(); i++) {
			GrantDatePerSet currentSet = this.grantDatePerSet.get(i);
			
			// 0年0ヶ月は登録不可
			if(currentSet.getGrantDateYear().v() == 0 && currentSet.getGrantDateMonth().v() == 0){
				errors.add("Msg_95");
			}
			
			// 同じ経過年数の場合は登録不可
			if(!grantDateYears.add(currentSet.getGrantDateYear().v())){
				errors.add("Msg_96");
			}
		}
		
		return errors;
	}

}
