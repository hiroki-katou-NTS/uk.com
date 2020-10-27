package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtHorizontalItemPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID **/
	@Column(name = "CID")
	public String companyId;
	/** 集計項目NO */
	@Column(name = "TOTAL_ITEM_NO")
	public Integer totalItemNo;
}
