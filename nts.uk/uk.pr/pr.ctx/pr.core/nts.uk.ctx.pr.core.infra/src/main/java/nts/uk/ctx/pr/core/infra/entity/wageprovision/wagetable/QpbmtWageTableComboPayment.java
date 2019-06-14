package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 賃金テーブル内容.支払金額
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WAGE_TBL_COMBO_PAY")
public class QpbmtWageTableComboPayment extends UkJpaEntity {

	@EmbeddedId
	public QpbmtWageTableComboPaymentPk pk;

	@Column(name = "PAY_AMOUNT")
	public long paymentAmount;

	/**
	 * 第一要素項目
	 */
	@Column(name = "MASTER_CD_1")
	@Basic(optional = true)
	public String masterCode1;

	@Column(name = "FRAME_UPPER_1")
	@Basic(optional = true)
	public BigDecimal frameUpperLimit1;

	@Column(name = "FRAME_LOWER_1")
	@Basic(optional = true)
	public BigDecimal frameLowerLimit1;

	@Column(name = "FRAME_NUMBER_1")
	@Basic(optional = true)
	public Long frameNumber1;

	/**
	 * 第二要素項目
	 */
	@Column(name = "MASTER_CD_2")
	@Basic(optional = true)
	public String masterCode2;

	@Column(name = "FRAME_UPPER_2")
	@Basic(optional = true)
	public BigDecimal frameUpperLimit2;

	@Column(name = "FRAME_LOWER_2")
	@Basic(optional = true)
	public BigDecimal frameLowerLimit2;

	@Column(name = "FRAME_NUMBER_2")
	@Basic(optional = true)
	public Long frameNumber2;

	/**
	 * 第三要素項目
	 */
	@Column(name = "MASTER_CD_3")
	@Basic(optional = true)
	public String masterCode3;

	@Column(name = "FRAME_UPPER_3")
	@Basic(optional = true)
	public BigDecimal frameUpperLimit3;

	@Column(name = "FRAME_LOWER_3")
	@Basic(optional = true)
	public BigDecimal frameLowerLimit3;

	@Column(name = "FRAME_NUMBER_3")
	@Basic(optional = true)
	public Long frameNumber3;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public ElementsCombinationPaymentAmount toDomain() {
		return new ElementsCombinationPaymentAmount(pk.id, paymentAmount, masterCode1, frameNumber1, frameLowerLimit1,
				frameUpperLimit1, masterCode2, frameNumber2, frameLowerLimit2, frameUpperLimit2, masterCode3,
				frameNumber3, frameLowerLimit3, frameUpperLimit3);
	}

	public QpbmtWageTableComboPayment(ElementsCombinationPaymentAmount domain, String historyId, String companyId,
			String wageTableCode) {
		this.pk = new QpbmtWageTableComboPaymentPk(companyId, wageTableCode, historyId, domain.getId());
		this.paymentAmount = domain.getWageTablePaymentAmount().v();
		this.masterCode1 = domain.getElementAttribute().getFirstElementItem().getMasterElementItem().isPresent()
				? domain.getElementAttribute().getFirstElementItem().getMasterElementItem().get().getMasterCode()
				: null;
		if (domain.getElementAttribute().getFirstElementItem().getNumericElementItem().isPresent()) {
			this.frameUpperLimit1 = domain.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
					.getFrameUpperLimit();
			this.frameLowerLimit1 = domain.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
					.getFrameLowerLimit();
			this.frameNumber1 = domain.getElementAttribute().getFirstElementItem().getNumericElementItem().get()
					.getFrameNumber();
		}
		if (domain.getElementAttribute().getSecondElementItem().isPresent()) {
			this.masterCode2 = domain.getElementAttribute().getSecondElementItem().get().getMasterElementItem()
					.isPresent()
							? domain.getElementAttribute().getSecondElementItem().get().getMasterElementItem().get()
									.getMasterCode()
							: null;
			if (domain.getElementAttribute().getSecondElementItem().get().getNumericElementItem().isPresent()) {
				this.frameUpperLimit2 = domain.getElementAttribute().getSecondElementItem().get()
						.getNumericElementItem().get().getFrameUpperLimit();
				this.frameLowerLimit2 = domain.getElementAttribute().getSecondElementItem().get()
						.getNumericElementItem().get().getFrameLowerLimit();
				this.frameNumber2 = domain.getElementAttribute().getSecondElementItem().get().getNumericElementItem()
						.get().getFrameNumber();
			}
		}
		if (domain.getElementAttribute().getThirdElementItem().isPresent()) {
			this.masterCode3 = domain.getElementAttribute().getThirdElementItem().get().getMasterElementItem()
					.isPresent()
							? domain.getElementAttribute().getThirdElementItem().get().getMasterElementItem().get()
									.getMasterCode()
							: null;
			if (domain.getElementAttribute().getThirdElementItem().get().getNumericElementItem().isPresent()) {
				this.frameUpperLimit3 = domain.getElementAttribute().getThirdElementItem().get().getNumericElementItem()
						.get().getFrameUpperLimit();
				this.frameLowerLimit3 = domain.getElementAttribute().getThirdElementItem().get().getNumericElementItem()
						.get().getFrameLowerLimit();
				this.frameNumber3 = domain.getElementAttribute().getThirdElementItem().get().getNumericElementItem()
						.get().getFrameNumber();
			}
		}
	}

}
