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
@Table(name = "QPBMT_WAGE_TABLE_copy")
public class QpbmtWageTable extends UkJpaEntity {

	@EmbeddedId
	public QpbmtWageTablePk pk;

	@Column(name = "NAME")
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
		return new QpbmtWageTable(new QpbmtWageTablePk(domain.getCid(), domain.getWageTableCode().v()),
				domain.getWageTableName().v(), domain.getElementSetting().value,
				domain.getRemarkInformation().isPresent() ? domain.getRemarkInformation().get().v() : null,
				elementInfor.getOneDimensionalElement().getMasterNumericAtr().isPresent()
						? elementInfor.getOneDimensionalElement().getMasterNumericAtr().get().value : null,
				elementInfor.getOneDimensionalElement().getOptionalAdditionalElement().isPresent()
						? elementInfor.getOneDimensionalElement().getOptionalAdditionalElement().get().v() : null,
				elementInfor.getOneDimensionalElement().getFixedElement().isPresent()
						? elementInfor.getOneDimensionalElement().getFixedElement().get().value : null,

				elementInfor.getTwoDimensionalElement().isPresent()
						? (elementInfor.getTwoDimensionalElement().get().getMasterNumericAtr().isPresent()
								? elementInfor.getTwoDimensionalElement().get().getMasterNumericAtr()
										.get().value
								: null)
						: null,
				elementInfor.getTwoDimensionalElement().isPresent()
						? (elementInfor.getTwoDimensionalElement().get().getOptionalAdditionalElement().isPresent()
								? elementInfor.getTwoDimensionalElement().get().getOptionalAdditionalElement().get().v()
								: null)
						: null,
				elementInfor.getTwoDimensionalElement().isPresent()
						? (elementInfor.getTwoDimensionalElement().get().getFixedElement().isPresent()
								? elementInfor.getTwoDimensionalElement().get().getFixedElement().get().value : null)
						: null,

				elementInfor.getThreeDimensionalElement().isPresent()
						? (elementInfor.getThreeDimensionalElement().get().getMasterNumericAtr().isPresent()
								? elementInfor.getThreeDimensionalElement().get().getMasterNumericAtr()
										.get().value
								: null)
						: null,
				elementInfor.getThreeDimensionalElement().isPresent()
						? (elementInfor.getThreeDimensionalElement().get().getOptionalAdditionalElement().isPresent()
								? elementInfor.getThreeDimensionalElement().get().getOptionalAdditionalElement().get()
										.v()
								: null)
						: null,
				elementInfor.getThreeDimensionalElement().isPresent()
						? (elementInfor.getThreeDimensionalElement().get().getFixedElement().isPresent()
								? elementInfor.getThreeDimensionalElement().get().getFixedElement().get().value : null)
						: null);
	}

}
