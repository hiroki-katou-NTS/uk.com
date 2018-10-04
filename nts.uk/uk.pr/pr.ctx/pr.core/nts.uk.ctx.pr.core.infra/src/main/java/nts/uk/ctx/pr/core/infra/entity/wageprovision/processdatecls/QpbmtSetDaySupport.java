package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 給与支払日設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SET_DAY_SUPPORT")
public class QpbmtSetDaySupport extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtSetDaySupportPk setDaySupportPk;

	/**
	 * 勤怠締め日
	 */
	@Basic(optional = false)
	@Column(name = "CLOSE_DATE_TIME")
	public GeneralDate closeDateTime;

	/**
	 * 雇用保険基準日
	 */
	@Basic(optional = false)
	@Column(name = "EMP_INSURD_STAN_DATE")
	public GeneralDate empInsurdStanDate;

	/**
	 * 経理締め年月日
	 */
	@Basic(optional = false)
	@Column(name = "CLOSURE_DATE_ACCOUNTING")
	public GeneralDate closureDateAccounting;

	/**
	 * 支払年月日
	 */
	@Basic(optional = false)
	@Column(name = "PAYMENT_DATE")
	public GeneralDate paymentDate;

	/**
	 * 社員抽出基準日
	 */
	@Basic(optional = false)
	@Column(name = "EMP_EXTRA_REFE_DATE")
	public GeneralDate empExtraRefeDate;

	/**
	 * 社会保険基準日
	 */
	@Basic(optional = false)
	@Column(name = "SOCIAL_INSURD_STAN_DATE")
	public GeneralDate socialInsurdStanDate;

	/**
	 * 社会保険徴収年月
	 */
	@Basic(optional = false)
	@Column(name = "SOCIAL_INSURD_COLLEC_MONTH")
	public int socialInsurdCollecMonth;

	/**
	 * 処理年月
	 */

	/**
	 * 所得税基準日
	 */
	@Basic(optional = false)
	@Column(name = "INCOME_TAX_DATE")
	public GeneralDate incomeTaxDate;

	/**
	 * 要勤務日数
	 */
	@Basic(optional = false)
	@Column(name = "NUMBER_WORK_DAY")
	public BigDecimal numberWorkDay;

	@Override
	protected Object getKey() {
		return setDaySupportPk;
	}

	public SetDaySupport toDomain() {
		return new SetDaySupport(
				this.setDaySupportPk.cid,
				this.setDaySupportPk.processCateNo,
				this.setDaySupportPk.processDate,
				this.closeDateTime,
				this.empInsurdStanDate,
				this.closureDateAccounting,
				this.paymentDate,
				this.empExtraRefeDate,
				this.socialInsurdStanDate,
				this.socialInsurdCollecMonth,
				this.incomeTaxDate,
				this.numberWorkDay
		);
	}

	public static QpbmtSetDaySupport toEntity(SetDaySupport domain) {
		return new QpbmtSetDaySupport(
				new QpbmtSetDaySupportPk(domain.getCid(), domain.getProcessCateNo(),
						domain.getProcessDate().v().intValue()),
				domain.getCloseDateTime(),
				domain.getEmpInsurdStanDate(),
				domain.getClosureDateAccounting(),
				domain.getPaymentDate(),
				domain.getEmpExtraRefeDate(),
				domain.getSocialInsurdStanDate(),
				domain.getSocialInsurdCollecMonth(),
				domain.getIncomeTaxDate(),
				domain.getNumberWorkDay().v());
	}

}
