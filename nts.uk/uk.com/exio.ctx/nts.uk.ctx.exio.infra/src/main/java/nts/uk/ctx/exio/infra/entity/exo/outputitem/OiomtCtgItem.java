package nts.uk.ctx.exio.infra.entity.exo.outputitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * カテゴリ項目
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_CTG_ITEM")
@Getter
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

	public CategoryItem toDomain() {
		return new CategoryItem(this.ctgItemPk.ctgItemNo, this.ctgId, this.operationSymbol, this.order);
	}

	public static OiomtCtgItem toEntity(StandardOutputItem stdOutItem, CategoryItem domain) {
		return new OiomtCtgItem(
				new OiomtCtgItemPk(domain.getCategoryItemNo().v(), stdOutItem.getCid(),
						stdOutItem.getOutputItemCode().v(), stdOutItem.getConditionSettingCode().v()),
				domain.getCategoryId().v(), domain.getOperationSymbol().value, domain.getOrder());
	}

}
