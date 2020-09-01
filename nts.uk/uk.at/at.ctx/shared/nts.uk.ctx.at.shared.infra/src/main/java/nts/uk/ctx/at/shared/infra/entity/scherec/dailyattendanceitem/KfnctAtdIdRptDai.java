package nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemUsed;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.FormCanUsedForTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Data
@Entity
@Table(name = "KFNCT_ATD_ID_RPT_DAI")
@EqualsAndHashCode(callSuper = true)
public class KfnctAtdIdRptDai extends UkJpaEntity implements DailyAttendanceItemUsed.MementoGetter, DailyAttendanceItemUsed.MementoSetter, Serializable {
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
		
	}

	@Override
	public void setFormCanUsedForTimes(List<FormCanUsedForTime> form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCompanyId() {
		return this.kfnctAtdIdRptDaiPK.getCompanyId();
	}

	@Override
	public BigDecimal getItemDailyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FormCanUsedForTime> getFormCanUsedForTimes() {
		// TODO Auto-generated method stub
		return null;
	}

}
