package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author anhdt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_DAY_FORM_S_MON_ITEM")
public class KfnmtAuthorityMonthlySItem extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAuthorityMonthlySItemPK kfnmtAuthorityMonthlySItemPK;
	
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;

	@Override
	protected Object getKey() {
		return this.kfnmtAuthorityMonthlySItemPK;
	}

}
