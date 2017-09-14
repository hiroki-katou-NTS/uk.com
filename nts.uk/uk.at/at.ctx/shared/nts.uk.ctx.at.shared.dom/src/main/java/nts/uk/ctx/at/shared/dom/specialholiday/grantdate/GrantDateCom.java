	package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
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
	private GrantDate grantDate;
	
	private List<GrantDateSet> grantDateSets;
	
	/* 
	 * Create from java type
	 */
	public static GrantDateCom createFromJavaType(String companyId, String specialHolidayCode, int grantDateAtr,
			int grantDate ,List<GrantDateSet> grantDateSets) {
		return new GrantDateCom(companyId,
				new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(grantDateAtr, GrantDateAtr.class),
				new GrantDate(grantDate),
				grantDateSets);
	}

	/* 
	 * Validate function
	 */
	public void validateInput() {
		for (int i = 0; i < this.grantDateSets.size(); i++) {
			GrantDateSet currentSet = this.grantDateSets.get(i);
			
			// 0年0ヶ月は登録不可
			if(currentSet.getGrantDateYear().v() == 0 && currentSet.getGrantDateMonth().v() == 0){
				throw new BusinessException("Msg_95");
			}
						
			// 経過年数は必ず１件以上必要
			if(currentSet.getGrantDateYear().v() < 1){
				throw new BusinessException("Msg_144");
			}
			
			// 同じ経過年数の場合は登録不可
			List<Integer> grantDateYears = this.grantDateSets.stream().map(x -> { return x.getGrantDateYear().v(); }).collect(Collectors.toList());
			int countDuplicate = Collections.frequency(grantDateYears, currentSet.getGrantDateYear().v());
			if(countDuplicate > 1){
				throw new BusinessException("Msg_96");
			}
		}
	}
}
