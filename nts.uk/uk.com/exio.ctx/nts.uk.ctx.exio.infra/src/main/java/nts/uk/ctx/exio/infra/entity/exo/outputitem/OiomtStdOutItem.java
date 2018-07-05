package nts.uk.ctx.exio.infra.entity.exo.outputitem;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 出力項目(定型)
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_OUT_ITEM")
public class OiomtStdOutItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtStdOutItemPk stdOutItemPk;

	/**
	 * 出力項目名
	 */
	@Basic(optional = false)
	@Column(name = "OUT_ITEM_NAME")
	public String outItemName;

	/**
	 * 項目型
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_TYPE")
	public int itemType;

	@Override
	protected Object getKey() {
		return stdOutItemPk;
	}

	public StandardOutputItem toDomain(List<OiomtCtgItem> categoryItems) {
		return new StandardOutputItem(this.stdOutItemPk.cid, this.stdOutItemPk.outItemCd, this.stdOutItemPk.condSetCd,
				this.outItemName, this.itemType, categoryItems.stream().map(x -> {
					return new CategoryItem(x.getCtgItemPk().ctgItemNo, x.getCtgId(), x.getOperationSymbol(),
							x.getOrder());
				}).collect(Collectors.toList()));
	}

	public static OiomtStdOutItem toEntity(StandardOutputItem domain) {
		return new OiomtStdOutItem(
				new OiomtStdOutItemPk(domain.getCid(), domain.getOutItemCd().v(), domain.getCondSetCd().v()),
				domain.getOutItemName().v(), domain.getItemType().value);
	}
}
