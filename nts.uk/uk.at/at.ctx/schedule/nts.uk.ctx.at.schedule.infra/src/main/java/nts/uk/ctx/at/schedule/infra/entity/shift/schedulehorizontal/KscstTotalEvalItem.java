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
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_TOTAL_EVAL_ITEM")
public class KscstTotalEvalItem extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscstTotalEvalItemPK kscstTotalEvalItemPK;
	/* 名称 */
	@Column(name = "TOTAL_ITEM_NAME")
	public String totalItemName;
	
	@Override
	protected Object getKey() {
		return kscstTotalEvalItemPK;
	}
}
