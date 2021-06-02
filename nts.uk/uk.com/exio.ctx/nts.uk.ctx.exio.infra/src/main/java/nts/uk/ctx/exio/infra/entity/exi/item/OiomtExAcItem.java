package nts.uk.ctx.exio.infra.entity.exi.item;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceConditionCode;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.DataFormatSetting;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.TimeDataFormatSet;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtExAcScreenCond;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtExAcFmChac;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtExAcFmDate;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtExAcFmNum;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtExAcFmTime;
import nts.uk.ctx.exio.infra.entity.exi.dataformat.OiomtTimeDataFmSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 受入項目（定型）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "OIOMT_EX_AC_ITEM")
public class OiomtExAcItem extends ContractUkJpaEntity implements Serializable {
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
	public OiomtExAcScreenCond acceptScreenCondSet;
	/**数値型データ形式設定	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtExAcFmNum numDataFormatSet;
	/**文字型データ形式設定	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtExAcFmChac charDataFormatSet;
	/**日付型データ形式設定	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtExAcFmDate dateDataFormatSet;
	/**時刻型データ形式設定	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtExAcFmTime insTimeDataFormatSet;
	/**時間型データ形式設定 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acceptItem", orphanRemoval = true)
	public OiomtTimeDataFmSet timeDataFormatSet;

	@Override
	protected Object getKey() {
		return stdAcceptItemPk;
	}

	public static OiomtExAcItem toEntity(StdAcceptItem domain) {
		OiomtExAcScreenCond screenConSet = domain.getAcceptScreenConditionSetting().isPresent()
				? OiomtExAcScreenCond.fromDomain(domain, domain.getAcceptScreenConditionSetting().get())
				: null;
		OiomtExAcFmNum numDataFormatSet = domain.getItemType() == ItemType.NUMERIC && domain.getDataFormatSetting().isPresent()
				? OiomtExAcFmNum.fromDomain(domain,
						(NumDataFormatSet) domain.getDataFormatSetting().get())
				: null;
		OiomtExAcFmChac chrDataFormatSet = domain.getItemType() == ItemType.CHARACTER && domain.getDataFormatSetting().isPresent()
				? OiomtExAcFmChac.fromDomain(domain,
						(ChrDataFormatSet) domain.getDataFormatSetting().get())
				: null;
		OiomtExAcFmDate dateDataFormatSet = domain.getItemType() == ItemType.DATE && domain.getDataFormatSetting().isPresent()
				? OiomtExAcFmDate.fromDomain(domain, (DateDataFormSet) domain.getDataFormatSetting().get())
				: null;
		OiomtExAcFmTime insTimeFormatSet =  domain.getItemType() == ItemType.INS_TIME && domain.getDataFormatSetting().isPresent()
				? OiomtExAcFmTime.fromDomain(domain, (InsTimeDatFmSet) domain.getDataFormatSetting().get())
				: null;
		OiomtTimeDataFmSet timeDateFormatSet = domain.getItemType() == ItemType.TIME && domain.getDataFormatSetting().isPresent()
				? OiomtTimeDataFmSet.fromDomain(domain, (TimeDataFormatSet) domain.getDataFormatSetting().get())
				: null;
		OiomtStdAcceptItemPk pk = new OiomtStdAcceptItemPk(domain.getCid(), domain.getConditionSetCd().v(), domain.getAcceptItemNumber());
		OiomtExAcItem entity = new OiomtExAcItem(pk,
				domain.getCategoryItemNo(),
				domain.getCsvItemNumber().isPresent() ? domain.getCsvItemNumber().get() : null,
				domain.getCsvItemName().isPresent() ? domain.getCsvItemName().get() : null,
				domain.getItemType().value,
				screenConSet,
				numDataFormatSet,
				chrDataFormatSet,
				dateDataFormatSet,
				insTimeFormatSet,
				timeDateFormatSet);
		return entity;
	}

	public static StdAcceptItem toDomain(OiomtExAcItem entity) {
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
		
		return new StdAcceptItem(entity.getStdAcceptItemPk().cid,
				new AcceptanceConditionCode(entity.getStdAcceptItemPk().conditionSetCd),
				entity.getStdAcceptItemPk().acceptItemNumber,
				Optional.ofNullable(entity.getCsvItemNumber()),
				Optional.ofNullable(entity.getCsvItemName()),
				EnumAdaptor.valueOf(entity.getItemType(), ItemType.class),
				entity.getCategoryItemNo(),
				Optional.ofNullable(entity.getAcceptScreenCondSet() == null ? null : entity.getAcceptScreenCondSet().toDomain()),
				Optional.ofNullable(dataFormatSet));
		
	}

}
