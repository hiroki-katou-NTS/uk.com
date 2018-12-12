package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - entity 要素範囲設定
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_ELEM_RANGE_SET")
public class QpbmtElementRangeSetting extends UkJpaEntity {

	@Id
	@Column(name = "HIST_ID")
	public String historyId;

	/**
	 * 第一要素範囲
	 */
	@Column(name = "STEP_INCREMENT_1")
	@Basic(optional = true)
	public Integer stepIncrement1;

	@Column(name = "UPPER_LIMIT_1")
	@Basic(optional = true)
	public Integer upperLimit1;

	@Column(name = "LOWER_LIMIT_1")
	@Basic(optional = true)
	public Integer lowerLimit1;

	/**
	 * 第二要素範囲
	 */
	@Column(name = "STEP_INCREMENT_2")
	@Basic(optional = true)
	public Integer stepIncrement2;

	@Column(name = "UPPER_LIMIT_2")
	@Basic(optional = true)
	public Integer upperLimit2;

	@Column(name = "LOWER_LIMIT_2")
	@Basic(optional = true)
	public Integer lowerLimit2;

	/**
	 * 第三要素範囲
	 */
	@Column(name = "STEP_INCREMENT_3")
	@Basic(optional = true)
	public Integer stepIncrement3;

	@Column(name = "UPPER_LIMIT_3")
	@Basic(optional = true)
	public Integer upperLimit3;

	@Column(name = "LOWER_LIMIT_3")
	@Basic(optional = true)
	public Integer lowerLimit3;

	@Override
	protected Object getKey() {
		return this.historyId;
	}

	public ElementRangeSetting toDomain() {
		return new ElementRangeSetting(this.stepIncrement1, this.lowerLimit1, this.upperLimit1, this.stepIncrement2,
				this.lowerLimit2, this.upperLimit2, this.stepIncrement3, this.lowerLimit3, this.upperLimit3,
				this.historyId);
	}

	public static QpbmtElementRangeSetting fromDomain(ElementRangeSetting domain) {
		return new QpbmtElementRangeSetting(domain.getHistoryID(),
				domain.getFirstElementRange().getNumericElementRange().isPresent()
						? domain.getFirstElementRange().getNumericElementRange().get().getStepIncrement().v() : null,
				domain.getFirstElementRange().getNumericElementRange().isPresent()
						? domain.getFirstElementRange().getNumericElementRange().get().getRangeUpperLimit().v() : null,
				domain.getFirstElementRange().getNumericElementRange().isPresent()
						? domain.getFirstElementRange().getNumericElementRange().get().getRangeLowerLimit().v() : null,
				domain.getSecondElementRange().isPresent()
						? (domain.getSecondElementRange().get().getNumericElementRange().isPresent() ? domain
								.getSecondElementRange().get().getNumericElementRange().get().getStepIncrement().v()
								: null)
						: null,
				domain.getSecondElementRange().isPresent()
						? (domain.getSecondElementRange().get().getNumericElementRange().isPresent() ? domain
								.getSecondElementRange().get().getNumericElementRange().get().getRangeUpperLimit().v()
								: null)
						: null,
				domain.getSecondElementRange().isPresent()
						? (domain.getSecondElementRange().get().getNumericElementRange().isPresent() ? domain
								.getSecondElementRange().get().getNumericElementRange().get().getRangeLowerLimit().v()
								: null)
						: null,
				domain.getThirdElementRange().isPresent()
						? (domain.getThirdElementRange().get().getNumericElementRange().isPresent() ? domain
								.getThirdElementRange().get().getNumericElementRange().get().getStepIncrement().v()
								: null)
						: null,
				domain.getThirdElementRange().isPresent()
						? (domain.getThirdElementRange().get().getNumericElementRange().isPresent() ? domain
								.getThirdElementRange().get().getNumericElementRange().get().getRangeUpperLimit().v()
								: null)
						: null,
				domain.getThirdElementRange().isPresent()
						? (domain.getThirdElementRange().get().getNumericElementRange().isPresent() ? domain
								.getThirdElementRange().get().getNumericElementRange().get().getRangeLowerLimit().v()
								: null)
						: null);
	}

}
