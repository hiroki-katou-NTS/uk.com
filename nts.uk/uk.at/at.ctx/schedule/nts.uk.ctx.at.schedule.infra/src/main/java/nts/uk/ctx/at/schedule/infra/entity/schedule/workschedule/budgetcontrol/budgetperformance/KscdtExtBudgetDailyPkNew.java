package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.budgetcontrol.budgetperformance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author HieuLt
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscdtExtBudgetDailyPkNew implements Serializable {


	private static final long serialVersionUID = 1L;

	/** CID **/
	@Column(name = "CID")
	public String companyId;
	
	/*"対象組織の単位
	0:職場
	1:職場グループ"									
	 */
	@Column(name = "TARGET_UNIT")
	public int targetUnit;
	
	/*"対象組織の単位に応じたID
	0:職場ID
	1:職場グループID"									
	 */
	@Column(name = "TARGET_ID")
	public String targetID;
	
	/**年月日**/
	@Column(name = "YMD")
	public GeneralDate ymd;
	
	/** 項目コード **/
	@Column(name = "CD")
	public String itemCd;
}
