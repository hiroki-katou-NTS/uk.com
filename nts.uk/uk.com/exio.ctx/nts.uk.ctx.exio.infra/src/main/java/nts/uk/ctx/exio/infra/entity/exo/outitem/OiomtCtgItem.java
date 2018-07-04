package nts.uk.ctx.exio.infra.entity.exo.outitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.outitem.CondSetCd;
import nts.uk.ctx.exio.dom.exo.outitem.CtgId;
import nts.uk.ctx.exio.dom.exo.outitem.CtgItem;
import nts.uk.ctx.exio.dom.exo.outitem.CtgItemNo;
import nts.uk.ctx.exio.dom.exo.outitem.OperationSymbol;
import nts.uk.ctx.exio.dom.exo.outitem.OutItemCd;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * カテゴリ項目
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_CTG_ITEM")
public class OiomtCtgItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtCtgItemPk ctgItemPk;

	/**
	 * カテゴリID
	 */
	@Basic(optional = false)
	@Column(name = "CTG_ID")
	public int ctgId;

	/**
	 * 演算符号
	 */
	@Basic(optional = false)
	@Column(name = "OPERATION_SYMBOL")
	public int operationSymbol;

	/**
	 * 順序
	 */
	@Basic(optional = false)
	@Column(name = "ORDER")
	public int order;

	@Override
	protected Object getKey() {
		return ctgItemPk;
	}

	public CtgItem toDomain() {
		return new CtgItem(new CtgItemNo(this.ctgItemPk.ctgItemNo), this.ctgItemPk.cid,
				new OutItemCd(this.ctgItemPk.outItemCd), new CondSetCd(this.ctgItemPk.condSetCd), new CtgId(this.ctgId),
				EnumAdaptor.valueOf(this.operationSymbol, OperationSymbol.class), this.order);
	}

	public static OiomtCtgItem toEntity(CtgItem domain) {
		return new OiomtCtgItem(
				new OiomtCtgItemPk(domain.getCtgItemNo().v(), domain.getCid(), domain.getOutItemCd().v(),
						domain.getCondSetCd().v()),
				domain.getCtgId().v(), domain.getOperationSymbol().value, domain.getOrder());
	}

}
