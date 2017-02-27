package nts.uk.ctx.pr.core.infra.entity.rule.employement.processing.yearmonth;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.TableEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPDMT_SYSTEM_DAY")
public class QpdmtSystemDay extends TableEntity implements Serializable {

	public static final long serialVersionUID = 1L;

	@EmbeddedId
	public QpdmtSystemDayPK qpdmtSystemDayPk;

	@Basic(optional = false)
	@Column(name = "PAY_STD_DAY")
	public Number payStdDay;

	@Basic(optional = false)
	@Column(name = "RESITAX_BEGIN_MON")
	public Number resitaxBeginMon;

	@Basic(optional = false)
	@Column(name = "RESITAX_STD_MON")
	public Number resitaxStdMon;

	@Basic(optional = false)
	@Column(name = "RESITAX_STD_DAY")
	public Number resitaxStdDay;

	@Basic(optional = false)
	@Column(name = "PICKUP_STD_MON_ATR")
	public Number pickupStdMonAtr;

	@Basic(optional = false)
	@Column(name = "PICKUP_STD_DAY")
	public Number pickupStdDay;

	@Basic(optional = false)
	@Column(name = "SOCIAL_INSU_LEVY_MON_ATR")
	public Number socialInsuLevyMonAtr;

	@Basic(optional = false)
	@Column(name = "ACCOUNT_DUE_MON_ATR")
	public Number accountDueMonAtr;

	@Basic(optional = false)
	@Column(name = "ACCOUNT_DUE_DAY")
	public Number accountDueDay;
}
