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
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.DataFormatSetting;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.TimeDataFormatSet;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtAcScreenCondSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtChrDataFormatSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtDateDataFormSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtInsTimeDatFmSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtNumDataFormatSet;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtTimeDataFmSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 受入項目（定型）
 */

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_ACCEPT_ITEM")
public class OiomtStdAcceptItem extends ContractUkJpaEntity implements Serializable {
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
	@Basic(optional = true)
	@Column(name = "CSV_ITEM_NUMBER")
	public Integer csvItemNumber;

	/**
	 * CSV項目名
	 */
	@Basic(optional = true)
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

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtNumDataFormatSet numDataFormatSet;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtChrDataFormatSet charDataFormatSet;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtDateDataFormSet dateDataFormatSet;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtInsTimeDatFmSet insTimeDataFormatSet;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtTimeDataFmSet timeDataFormatSet;

	@Override
	protected Object getKey() {
		return stdAcceptItemPk;
	}

	public OiomtStdAcceptItem(String cid, int sysType, String conditionCode, int acceptItemNum, int categoryItemNo,
			Integer csvItemNumber, String csvItemName, int itemType, OiomtAcScreenCondSet acceptScreenCondSet,
			OiomtNumDataFormatSet numDataFormatSet, OiomtChrDataFormatSet charDataFormatSet,
			OiomtDateDataFormSet dateDataFormatSet, OiomtInsTimeDatFmSet insTimeDataFormatSet,
			OiomtTimeDataFmSet timeDataFormatSet) {
		super();
		this.stdAcceptItemPk = new OiomtStdAcceptItemPk(cid, sysType, conditionCode, acceptItemNum);
		this.categoryItemNo = categoryItemNo;
		this.csvItemNumber = csvItemNumber;
		this.csvItemName = csvItemName;
		this.itemType = itemType;
		this.acceptScreenCondSet = acceptScreenCondSet;
		this.numDataFormatSet = numDataFormatSet;
		this.charDataFormatSet = charDataFormatSet;
		this.dateDataFormatSet = dateDataFormatSet;
		this.insTimeDataFormatSet = insTimeDataFormatSet;
		this.timeDataFormatSet = timeDataFormatSet;
	}

	public static OiomtStdAcceptItem fromDomain(StdAcceptItem domain) {
		return new OiomtStdAcceptItem(domain.getCid(), domain.getSystemType().value, domain.getConditionSetCd().v(),
				domain.getAcceptItemNumber(), domain.getCategoryItemNo(),
				domain.getCsvItemNumber().isPresent() ? domain.getCsvItemNumber().get() : null,
				domain.getCsvItemName().isPresent() ? domain.getCsvItemName().get() : null, domain.getItemType().value,
				domain.getAcceptScreenConditionSetting().isPresent()
						? OiomtAcScreenCondSet.fromDomain(domain, domain.getAcceptScreenConditionSetting().get())
						: null,
				domain.getItemType() == ItemType.NUMERIC && domain.getDataFormatSetting().isPresent()
						? OiomtNumDataFormatSet.fromDomain(domain,
								(NumDataFormatSet) domain.getDataFormatSetting().get())
						: null,
				domain.getItemType() == ItemType.CHARACTER && domain.getDataFormatSetting().isPresent()
						? OiomtChrDataFormatSet.fromDomain(domain,
								(ChrDataFormatSet) domain.getDataFormatSetting().get())
						: null,
				domain.getItemType() == ItemType.DATE && domain.getDataFormatSetting().isPresent()
						? OiomtDateDataFormSet.fromDomain(domain, (DateDataFormSet) domain.getDataFormatSetting().get())
						: null,
				domain.getItemType() == ItemType.INS_TIME && domain.getDataFormatSetting().isPresent()
						? OiomtInsTimeDatFmSet.fromDomain(domain, (InsTimeDatFmSet) domain.getDataFormatSetting().get())
						: null,
				domain.getItemType() == ItemType.TIME && domain.getDataFormatSetting().isPresent()
						? OiomtTimeDataFmSet.fromDomain(domain, (TimeDataFormatSet) domain.getDataFormatSetting().get())
						: null);
	}

	public static StdAcceptItem toDomain(OiomtStdAcceptItem entity) {
		DataFormatSetting dataFormatSet = null;
		ItemType itemType = ItemType.values()[entity.itemType];
		switch (itemType) {
		case NUMERIC:
			dataFormatSet = entity.numDataFormatSet == null ? null : entity.numDataFormatSet.toDomain();
			break;
		case CHARACTER:
			dataFormatSet = entity.charDataFormatSet == null ? null : entity.charDataFormatSet.toDomain();
			break;
		case DATE:
			dataFormatSet = entity.dateDataFormatSet == null ? null : entity.dateDataFormatSet.toDomain();
			break;
		case INS_TIME:
			dataFormatSet = entity.insTimeDataFormatSet == null ? null : entity.insTimeDataFormatSet.toDomain();
			break;
		case TIME:
			dataFormatSet = entity.timeDataFormatSet == null ? null : entity.timeDataFormatSet.toDomain();
			break;
		}
		return new StdAcceptItem(entity.stdAcceptItemPk.cid, entity.stdAcceptItemPk.systemType,
				entity.stdAcceptItemPk.conditionSetCd, entity.stdAcceptItemPk.acceptItemNumber, entity.categoryItemNo,
				entity.csvItemNumber, entity.csvItemName, entity.itemType,
				entity.acceptScreenCondSet == null ? null : entity.acceptScreenCondSet.toDomain(), dataFormatSet);
	}

}
