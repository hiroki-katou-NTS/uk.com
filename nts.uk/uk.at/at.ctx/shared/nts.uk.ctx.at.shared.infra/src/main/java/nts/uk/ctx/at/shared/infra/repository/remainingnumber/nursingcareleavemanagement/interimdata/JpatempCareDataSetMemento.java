package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.interimdata;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimeHoliday;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareDataSetMemento;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.interimdata.KrcdtTempCareData;

@NoArgsConstructor
public class JpatempCareDataSetMemento implements TempCareDataSetMemento {
	
	/** The entity. */
	private KrcdtTempCareData entity;
	

	/**
	 * Instantiates a new jpatemp care data set memento.
	 *
	 * @param entity the entity
	 */
	public JpatempCareDataSetMemento(KrcdtTempCareData entity) {
		super();
		this.entity = entity;
	}

	@Override
	public void setEmployeeId(String employeeId) {
		this.entity.getId().setSid(employeeId);
	}

	@Override
	public void setScheduleRecordAtr(ScheduleRecordAtr scheduleRecordAtr) {
		this.entity.setScheRecdAtr(scheduleRecordAtr.value);
	}

	@Override
	public void setAnnualLeaveUse(ManagementDays annualLeaveUse) {
		this.entity.setAnnleaUse(annualLeaveUse.v());
	}

	@Override
	public void setTimeAnnualLeaveUsePrivateGoOut(TimeHoliday timeAnnualLeaveUsePrivateGoOut) {
		this.entity.setTimeealUsePrivGoout(timeAnnualLeaveUsePrivateGoOut.v());
	}

	@Override
	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		this.entity.setWorkTypeCode(workTypeCode.v());
	}

	@Override
	public void setYmd(GeneralDate ymd) {
		this.entity.getId().setYmd(ymd);
	}


}
