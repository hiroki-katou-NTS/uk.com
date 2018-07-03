package nts.uk.ctx.exio.infra.entity.exo.outitemsortorder;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.outitemsortorder.CondSetCd;
import nts.uk.ctx.exio.dom.exo.outitemsortorder.OutItemCd;
import nts.uk.ctx.exio.dom.exo.outitemsortorder.StdOutItemOrder;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 出力項目並び順(定型)
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_OUT_ITEM_ORDER")
public class OiomtStdOutItemOrder extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtStdOutItemOrderPk stdOutItemOrderPk;

	/**
	 * 順序
	 */
	@Basic(optional = false)
	@Column(name = "ORDER")
	public int order;

	@Override
	protected Object getKey() {
		return stdOutItemOrderPk;
	}

	public StdOutItemOrder toDomain() {
		return new StdOutItemOrder(this.stdOutItemOrderPk.cid, new OutItemCd(this.stdOutItemOrderPk.outItemCd),
				new CondSetCd(this.stdOutItemOrderPk.condSetCd), this.order);
	}

	public static OiomtStdOutItemOrder toEntity(StdOutItemOrder domain) {
		return new OiomtStdOutItemOrder(
				new OiomtStdOutItemOrderPk(domain.getCid(), domain.getOutItemCd().v(), domain.getCondSetCd().v()),
				domain.getOrder());
	}

}
