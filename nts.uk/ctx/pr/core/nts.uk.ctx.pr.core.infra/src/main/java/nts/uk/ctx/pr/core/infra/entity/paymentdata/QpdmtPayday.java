package nts.uk.ctx.pr.core.infra.entity.paymentdata;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

@Entity
@Table(name = "QPDMT_PAYDAY")
public class QpdmtPayday {
	@EmbeddedId
	public QpdmtPaydayPK qpdmtPaydayPK;

	@Basic(optional = false)
	@Column(name = "PAY_MON")
	public int paymentMonth;

	@Basic(optional = false)
	@Column(name = "PAY_DAY")
	public int paymentDay;

	@Basic(optional = false)
	@Column(name = "STD_MON")
	public int standardMonth;

	@Basic(optional = false)
	@Column(name = "STD_DAY")
	public int standardDay;

	@Basic(optional = false)
	@Column(name = "PAY_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate payDate;

	@Basic(optional = false)
	@Column(name = "STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate standardDate;

	@Basic(optional = false)
	@Column(name = "SOCIAL_INS_STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate socialInsuranceStandardDate;

	@Basic(optional = false)
	@Column(name = "EMP_INS_STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate employmentInsuranceStandardDate;

	@Basic(optional = false)
	@Column(name = "INCOME_TAX_STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate incomeTaxReferenceDate;

	@Basic(optional = false)
	@Column(name = "STMT_OUTPUT_MON")
	public BigDecimal stmtOutputMonth;

	@Basic(optional = false)
	@Column(name = "NEEDED_WORK_DAY")
	public BigDecimal neededWorkDay;

}
