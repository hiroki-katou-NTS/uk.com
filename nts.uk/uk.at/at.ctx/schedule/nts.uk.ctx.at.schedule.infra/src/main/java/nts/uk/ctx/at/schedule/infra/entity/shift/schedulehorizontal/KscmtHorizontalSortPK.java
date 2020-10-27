package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtHorizontalSortPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID **/
	@Column(name = "CID")
	public String companyId;
	/** カテゴリコード */
	@Column(name = "CATEGORY_CD")
	public String categoryCode;
	/** 並び順 */
	@Column(name = "TOTAL_ITEM_NO")
	public Integer totalItemNo;
}
