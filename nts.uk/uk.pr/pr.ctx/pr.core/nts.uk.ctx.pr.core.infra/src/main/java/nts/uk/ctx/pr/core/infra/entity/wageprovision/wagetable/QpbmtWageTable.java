package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - entity 賃金テーブル
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WAGE_TABLE")
public class QpbmtWageTable extends UkJpaEntity {

	@EmbeddedId
	public QpbmtWageTablePk pk;

	@Column(name = "WAGE_TABLE_NAME")
	public String name;

	@Column(name = "ELEMENT_SET")
	public int elementSetting;

	@Column(name = "MEMO")
	@Basic(optional = true)
	public String memo;

	/**
	 * 要素情報.一次元要素
	 */
	@Column(name = "MASTER_NUM_ATR_1")
	@Basic(optional = true)
	public Integer masterNumericAtr1;

	@Column(name = "OPT_ADD_ELEMENT_1")
	@Basic(optional = true)
	public String optionalAdditionalElement1;

	@Column(name = "FIXED_ELEMENT_1")
	@Basic(optional = true)
	public String fixedElement1;

	/**
	 * 要素情報.二次元要素
	 */
	@Column(name = "MASTER_NUM_ATR_2")
	@Basic(optional = true)
	public Integer masterNumericAtr2;

	@Column(name = "OPT_ADD_ELEMENT_2")
	@Basic(optional = true)
	public String optionalAdditionalElement2;

	@Column(name = "FIXED_ELEMENT_2")
	@Basic(optional = true)
	public String fixedElement2;

	/**
	 * 要素情報.三次元要素
	 */
	@Column(name = "MASTER_NUM_ATR_3")
	@Basic(optional = true)
	public Integer masterNumericAtr3;

	@Column(name = "OPT_ADD_ELEMENT_3")
	@Basic(optional = true)
	public String optionalAdditionalElement3;

	@Column(name = "FIXED_ELEMENT_3")
	@Basic(optional = true)
	public String fixedElement3;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public WageTable toDomain() {
		return new WageTable(this.pk.companyId, this.pk.code, this.name, this.masterNumericAtr1, this.fixedElement1,
				this.optionalAdditionalElement1, this.masterNumericAtr2, this.fixedElement2,
				this.optionalAdditionalElement2, this.masterNumericAtr3, this.fixedElement3,
				this.optionalAdditionalElement3, this.elementSetting, this.memo);
	}

	public static QpbmtWageTable fromDomain(WageTable domain) {
		ElementInformation elementInfor = domain.getElementInformation();
		QpbmtWageTable entity = new QpbmtWageTable();
		entity.pk = new QpbmtWageTablePk(domain.getCid(), domain.getWageTableCode().v());
		entity.name = domain.getWageTableName().v();
		entity.elementSetting = domain.getElementSetting().value;
		if (domain.getRemarkInformation().isPresent())
			entity.memo = domain.getRemarkInformation().get().v();
		if (elementInfor.getOneDimensionalElement().getMasterNumericAtr().isPresent())
			entity.masterNumericAtr1 = elementInfor.getOneDimensionalElement().getMasterNumericAtr().get().value;
		else
			entity.masterNumericAtr1 = 0; // default value
		if (elementInfor.getOneDimensionalElement().getOptionalAdditionalElement().isPresent())
			entity.optionalAdditionalElement1 = elementInfor.getOneDimensionalElement().getOptionalAdditionalElement().get().v();
		if (elementInfor.getOneDimensionalElement().getFixedElement().isPresent())
			entity.fixedElement1 = elementInfor.getOneDimensionalElement().getFixedElement().get().value;
		if (elementInfor.getTwoDimensionalElement().isPresent()) {
			if (elementInfor.getTwoDimensionalElement().get().getMasterNumericAtr().isPresent())
				entity.masterNumericAtr2 = elementInfor.getTwoDimensionalElement().get().getMasterNumericAtr().get().value;
			else
				entity.masterNumericAtr2 = 0; // default value
			if (elementInfor.getTwoDimensionalElement().get().getOptionalAdditionalElement().isPresent())
				entity.optionalAdditionalElement2 = elementInfor.getTwoDimensionalElement().get().getOptionalAdditionalElement().get().v();
			if (elementInfor.getTwoDimensionalElement().get().getFixedElement().isPresent())
				entity.fixedElement2 = elementInfor.getTwoDimensionalElement().get().getFixedElement().get().value;
		} else
			entity.masterNumericAtr2 = 0; // default value
		if (elementInfor.getThreeDimensionalElement().isPresent()) {
			if (elementInfor.getThreeDimensionalElement().get().getMasterNumericAtr().isPresent())
				entity.masterNumericAtr3 = elementInfor.getThreeDimensionalElement().get().getMasterNumericAtr().get().value;
			else
				entity.masterNumericAtr3 = 0; // default value
			if (elementInfor.getThreeDimensionalElement().get().getOptionalAdditionalElement().isPresent())
				entity.optionalAdditionalElement3 = elementInfor.getThreeDimensionalElement().get().getOptionalAdditionalElement().get().v();
			if (elementInfor.getThreeDimensionalElement().get().getFixedElement().isPresent())
				entity.fixedElement3 = elementInfor.getThreeDimensionalElement().get().getFixedElement().get().value;
		} else
			entity.masterNumericAtr3 = 0; // default value
		return entity;
	}

}
