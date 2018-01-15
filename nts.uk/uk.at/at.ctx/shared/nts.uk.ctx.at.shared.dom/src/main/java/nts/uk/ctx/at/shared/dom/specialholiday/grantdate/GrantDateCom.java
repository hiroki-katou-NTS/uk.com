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
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@Getter	
public class GrantDateCom extends DomainObject {

	/*会社ID*/
	private String companyId;

	/*付与日のID*/
	private SpecialHolidayCode specialHolidayCode;

	/*付与基準日*/
	private GrantDateAtr grantDateAtr;

	/*一律基準日*/
	private GeneralDate grantDate;
	
	private List<GrantDateSet> grantDateSets;
	
	/* 
	 * Create from java type
	 */
	public static GrantDateCom createFromJavaType(String companyId, String specialHolidayCode, int grantDateAtr,
			GeneralDate grantDate ,List<GrantDateSet> grantDateSets) {
		return new GrantDateCom(companyId,
				new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(grantDateAtr, GrantDateAtr.class),
				grantDate,
				grantDateSets);
	}

	/* 
	 * Validate function
	 */
	public List<String> validateInput() {
		List<String> errors = new ArrayList<>();
		// 経過年数は必ず１件以上必要
		if(CollectionUtil.isEmpty(this.grantDateSets)){
			errors.add("Msg_144");
		}
					
		Set<Integer> grantDateYears = new HashSet<>();
		for (int i = 0; i < this.grantDateSets.size(); i++) {
			GrantDateSet currentSet = this.grantDateSets.get(i);
			
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
