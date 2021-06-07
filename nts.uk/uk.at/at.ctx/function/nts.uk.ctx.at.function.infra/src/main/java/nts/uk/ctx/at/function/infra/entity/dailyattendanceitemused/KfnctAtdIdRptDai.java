package nts.uk.ctx.at.function.infra.entity.dailyattendanceitemused;

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
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItemUsed;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.FormCanUsedForTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Data
@Entity
@Table(name = "KFNCT_ATD_ID_RPT_DAI")
@EqualsAndHashCode(callSuper = true)
public class KfnctAtdIdRptDai extends UkJpaEntity
		implements DailyAttendanceItemUsed.MementoGetter, DailyAttendanceItemUsed.MementoSetter, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KfnctAtdIdRptDaiPK kfnctAtdIdRptDaiPK;
	
	@Column(name = "WORK_DAILY")
	private BigDecimal workDaily;
	
	@Column(name = "WORK_ATTENDANCE")
	private BigDecimal workAttendance;
	
	@Column(name = "ATD_WORK_DAILY")
	private BigDecimal atdWorkDaily;
	
	@Column(name = "ATD_WORK_ATTENDANCE")
	private BigDecimal atdWorkAttendance;
	
	@Column(name = "Reserve01")
	private BigDecimal reserve01;
	
	@Column(name = "Reserve02")
	private BigDecimal reserve02;
	
	@Column(name = "Reserve03")
	private BigDecimal reserve03;
	
	@Column(name = "Reserve04")
	private BigDecimal reserve04;
	
	@Column(name = "Reserve05")
	private BigDecimal reserve05;
	
	@Column(name = "Reserve06")
	private BigDecimal reserve06;
	
	@Column(name = "Reserve07")
	private BigDecimal reserve07;
	
	@Column(name = "Reserve08")
	private BigDecimal reserve08;
	
	@Column(name = "Reserve09")
	private BigDecimal reserve09;
	
	@Column(name = "Reserve10")
	private BigDecimal reserve10;
	
	@Column(name = "Reserve11")
	private BigDecimal reserve11;
	
	@Column(name = "Reserve12")
	private BigDecimal reserve12;
	
	@Column(name = "Reserve13")
	private BigDecimal reserve13;
	
	@Column(name = "Reserve14")
	private BigDecimal reserve14;
	
	@Column(name = "Reserve15")
	private BigDecimal reserve15;
	
	@Column(name = "Reserve16")
	private BigDecimal reserve16;

	@Override
	protected Object getKey() {
		return this.kfnctAtdIdRptDaiPK;
	}

	@Override
	public void setCompanyId(String companyId) {
		if (this.kfnctAtdIdRptDaiPK == null) {
			this.kfnctAtdIdRptDaiPK = new KfnctAtdIdRptDaiPK();
		}
		this.kfnctAtdIdRptDaiPK.setCompanyId(companyId);
		
	}

	@Override
	public void setItemDailyId(BigDecimal itemDailyId) {
		if (this.kfnctAtdIdRptDaiPK == null) {
			this.kfnctAtdIdRptDaiPK = new KfnctAtdIdRptDaiPK();
		}
		this.kfnctAtdIdRptDaiPK.setAttendanceItemId(itemDailyId);
	}

	@Override
	public void setFormCanUsedForTimes(List<FormCanUsedForTime> form) {
		for (FormCanUsedForTime formCanUsedForTime : form) {
			switch (formCanUsedForTime) {
				case DAILY_WORK_SCHEDULE:
					this.setWorkDaily(BigDecimal.valueOf(1));
					break;
				case ATTENDANCE_BOOK:
					this.setWorkAttendance(BigDecimal.valueOf(1));
					break;
				case WORK_SITUATION_TABLE:
					this.setAtdWorkDaily(BigDecimal.valueOf(1));
					break;
				case ANNUAL_WORK_LEDGER:
					this.setAtdWorkAttendance(BigDecimal.valueOf(1));
					break;
				default:
					break;
			}
		}
	}

	@Override
	public String getCompanyId() {
		return this.kfnctAtdIdRptDaiPK.getCompanyId();
	}

	@Override
	public BigDecimal getItemDailyId() {
		return this.kfnctAtdIdRptDaiPK.getAttendanceItemId();
	}

	@Override
	public List<FormCanUsedForTime> getFormCanUsedForTimes() {
		return Arrays.asList(
				FormCanUsedForTime.valueOf(this.workDaily.intValue()),
				FormCanUsedForTime.valueOf(this.workAttendance.intValue()),
				FormCanUsedForTime.valueOf(this.atdWorkAttendance.intValue()),
				FormCanUsedForTime.valueOf(this.atdWorkDaily.intValue()));
	}

}
