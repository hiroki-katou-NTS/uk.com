/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * laitv
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaborContractHistoryDto   {

	private String cid;

	private String sid;
	
	String hisId;
	
	GeneralDate start;
	
	GeneralDate end;

}
