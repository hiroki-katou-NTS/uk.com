package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_TOTAL_EVAL_ITEM")
public class KscmtTotalEvalItem extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscmtTotalEvalItemPK kscmtTotalEvalItemPK;
	/* 名称 */
	@Column(name = "TOTAL_ITEM_NAME")
	public String totalItemName;
	
	@Override
	protected Object getKey() {
		return kscmtTotalEvalItemPK;
	}
}
