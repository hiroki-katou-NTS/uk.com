package nts.uk.ctx.pr.proto.infra.entity.paymentdata;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name = "QPDMT_PAYDAY")
public class QpdmtPayday extends AggregateTableEntity {
	@EmbeddedId
	public QpdmtPaydayPK qpdmtPaydayPK;

	@Basic(optional = false)
	@Column(name = "PAY_MON")
	public BigDecimal paymentMonth;

	@Basic(optional = false)
	@Column(name = "PAY_DAY")
	public BigDecimal paymentDay;

	@Basic(optional = false)
	@Column(name = "STD_MON")
	public BigDecimal standardMonth;

	@Basic(optional = false)
	@Column(name = "STD_DAY")
	public BigDecimal standardDay;

	@Basic(optional = false)
	@Column(name = "PAY_DATE")
	@Temporal(TemporalType.DATE)
	public Date payDate;

	@Basic(optional = false)
	@Column(name = "STD_DATE")
	@Temporal(TemporalType.DATE)
	public Date standardDate;

	@Basic(optional = false)
	@Column(name = "SOCIAL_INS_STD_DATE")
	@Temporal(TemporalType.DATE)
	public Date socialInsuranceStandardDate;

	@Basic(optional = false)
	@Column(name = "EMP_INS_STD_DATE")
	@Temporal(TemporalType.DATE)
	public Date employmentInsuranceStandardDate;

	@Basic(optional = false)
	@Column(name = "INCOME_TAX_STD_DATE")
	@Temporal(TemporalType.DATE)
	public Date incomeTaxReferenceDate;

	@Basic(optional = false)
	@Column(name = "STMT_OUTPUT_MON")
	public BigDecimal stmtOutputMonth;

	@Basic(optional = false)
	@Column(name = "NEEDED_WORK_DAY")
	public BigDecimal neededWorkDay;

}
