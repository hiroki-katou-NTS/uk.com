package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KscmtVerticalCntAggPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**会社ID**/
	@Column(name = "CID")
	public String companyId;
	
	/** 付与基準日 **/
	@Column(name = "FIXED_ITEM_ATR")
	public int fixedItemAtr;
	
	/****/
	@Column(name = "VERTICAL_CNT_NO")
	public int verticalCountNo;
	

}
