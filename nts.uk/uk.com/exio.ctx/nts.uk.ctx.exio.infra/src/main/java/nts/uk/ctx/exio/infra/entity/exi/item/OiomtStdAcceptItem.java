package nts.uk.ctx.exio.infra.entity.exi.item;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtAcScreenCondSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 受入項目（定型）
 */

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_ACCEPT_ITEM")
public class OiomtStdAcceptItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtStdAcceptItemPk stdAcceptItemPk;

	/**
	 * カテゴリ項目NO
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_ITEM_NO")
	public int categoryItemNo;

	/**
	 * CSV項目番号
	 */
	@Basic(optional = false)
	@Column(name = "CSV_ITEM_NUMBER")
	public int csvItemNumber;

	/**
	 * CSV項目名
	 */
	@Basic(optional = false)
	@Column(name = "CSV_ITEM_NAME")
	public String csvItemName;

	/**
	 * 項目型
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_TYPE")
	public int itemType;

	/**
	 * 受入選別条件設定
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtAcScreenCondSet acceptScreenCondSet;

	@Override
	protected Object getKey() {
		return stdAcceptItemPk;
	}

	public OiomtStdAcceptItem(String cid, int sysType, String conditionCode, String categoryId, int acceptItemNum,
			int categoryItemNo, int csvItemNumber, String csvItemName, int itemType,
			OiomtAcScreenCondSet acceptScreenCondSet) {
		super();
		this.stdAcceptItemPk = new OiomtStdAcceptItemPk(cid, sysType, conditionCode, categoryId, acceptItemNum);
		this.categoryItemNo = categoryItemNo;
		this.csvItemNumber = csvItemNumber;
		this.csvItemName = csvItemName;
		this.itemType = itemType;
		this.acceptScreenCondSet = acceptScreenCondSet;
	}

	public static OiomtStdAcceptItem fromDomain(StdAcceptItem domain) {
		return new OiomtStdAcceptItem(domain.getCid(), domain.getSystemType(), domain.getConditionSetCd().v(), "",
				domain.getAcceptItemNumber(), domain.getCategoryItemNo(), domain.getCsvItemNumber(),
				domain.getCsvItemName(), domain.getItemType().value,
				OiomtAcScreenCondSet.fromDomain(domain.getAcceptScreenConditionSetting()));
	}

	public static StdAcceptItem toDomain(OiomtStdAcceptItem entity) {
		return new StdAcceptItem(entity.stdAcceptItemPk.cid, entity.stdAcceptItemPk.systemType,
				entity.stdAcceptItemPk.conditionSetCd, entity.stdAcceptItemPk.acceptItemNumber, entity.categoryItemNo,
				entity.csvItemNumber, entity.csvItemName, entity.itemType,
				OiomtAcScreenCondSet.toDomain(entity.acceptScreenCondSet));
	}

}
