package nts.uk.ctx.at.shared.infra.entity.monthlyattendanceitemused;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import nts.uk.ctx.at.shared.dom.monthlyattditem.FormCanUsedForTime;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemUsed;

@Data
@Entity
@Table(name = "KFNCT_ATD_ID_RPT_MON")
@EqualsAndHashCode(callSuper = true)
public class KfnctAtdIdRptMon extends UkJpaEntity implements MonthlyAttendanceItemUsed.MementoGetter, MonthlyAttendanceItemUsed.MementoSetter, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private KfnctAtdIdRptMonPK kfnctAtdIdRptMonPK;
	
	@Column(name = "EXCLUS_VER")
	private String exclusVer;
	
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	@Column(name = "WORK_ATTENDANCE")
	private BigDecimal workAttendance;
	
	@Column(name = "WORK_MONTHLY")
	private BigDecimal workMonthly;
	
	@Column(name = "WORK_YEARLY")
	private BigDecimal workYearly;
	
	@Column(name = "WORK_PERIOD")
	private BigDecimal workPeriod;
	
	@Column(name = "ATD_WORK_ATTENDANCE")
	private BigDecimal atdWorkAttendance;
	
	@Column(name = "ATD_WORK_YEARLY")
	private BigDecimal atdWorkYearly;
	
	@Override
	protected Object getKey() {
		return this.kfnctAtdIdRptMonPK;
	}
	
	@Override
	public void setCompanyId(String companyId) {
		if (this.kfnctAtdIdRptMonPK == null) {
			this.kfnctAtdIdRptMonPK = new KfnctAtdIdRptMonPK();
		}
		this.kfnctAtdIdRptMonPK.setCompanyId(companyId);
	}

	@Override
	public void setAttendanceItemId(int attendanceItemId) {
		if (this.kfnctAtdIdRptMonPK == null) {
			this.kfnctAtdIdRptMonPK = new KfnctAtdIdRptMonPK();
		}
		this.kfnctAtdIdRptMonPK.setAttendanceItemId(attendanceItemId);
	}

	@Override
	public void setFormCanUsedForTimes(List<FormCanUsedForTime> forms) {
		for (FormCanUsedForTime form : forms) {
			switch (form) {
				case ATTENDANCE_BOOK:
					this.setAtdWorkAttendance(BigDecimal.valueOf(form.value));
					break;
				case MONTHLY_WORK_SCHEDULE:
					this.setWorkMonthly(BigDecimal.valueOf(form.value));
					break;
				case ANNUAL_WORK_SCHEDULE:
					this.setWorkYearly(BigDecimal.valueOf(form.value));
					break;
				case OPTIONAL_PERIOD_SCHEDULE:
					this.setWorkPeriod(BigDecimal.valueOf(form.value));
					break;
				case ANNUAL_WORK_LEDGER:
					this.setAtdWorkAttendance(BigDecimal.valueOf(form.value));
					break;
				case WORKBOOK:
					this.setAtdWorkYearly(BigDecimal.valueOf(form.value));
					break;
				default:
					break;
			}
		}
	}

	@Override
	public String getCompanyId() {
		return this.kfnctAtdIdRptMonPK.getCompanyId();
	}

	@Override
	public int getAttendanceItemId() {
		return this.kfnctAtdIdRptMonPK.getAttendanceItemId();
	}

	@Override
	public List<FormCanUsedForTime> getFormCanUsedForTimes() {
		List<FormCanUsedForTime> canUsedForTimes = Arrays.asList(
				FormCanUsedForTime.valueOf(this.atdWorkAttendance.intValue()),
				FormCanUsedForTime.valueOf(this.workMonthly.intValue()),
				FormCanUsedForTime.valueOf(this.workYearly.intValue()),
				FormCanUsedForTime.valueOf(this.workPeriod.intValue()),
				FormCanUsedForTime.valueOf(this.atdWorkAttendance.intValue()),
				FormCanUsedForTime.valueOf(this.atdWorkYearly.intValue())
		);
		return canUsedForTimes;
	}
	
	

}
