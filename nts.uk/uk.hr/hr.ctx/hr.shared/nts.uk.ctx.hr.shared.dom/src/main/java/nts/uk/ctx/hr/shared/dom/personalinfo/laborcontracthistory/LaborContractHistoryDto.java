/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * laitv
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LaborContractHistoryDto   {

	private String cid; 
	
	private String hisId; 

	private String sid; // 社員ID
	 
	private GeneralDate startDate; // 労働契約履歴.開始日
	 
	private GeneralDate endDate;  // 労働契約履歴.終了日
	
	private Integer contractStatus;  // 契約状況

	public LaborContractHistoryDto(String cid, String sid, String hisId, GeneralDate startDate, GeneralDate endDate) {
		super();
		this.cid = cid;
		this.sid = sid;
		this.hisId = hisId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
