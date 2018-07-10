package nts.uk.ctx.at.shared.repository.vacation.setting.temp.caredata;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimeHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata.TempCareDataGetMemento;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.temp.caredata.KrcdtTempCareData;

public class JpaTempCareDataGetMemento implements TempCareDataGetMemento {
	
	/** The entity. */
	private KrcdtTempCareData entity;
	
	
	/**
	 * Instantiates a new jpa temp care data get memento.
	 */
	public JpaTempCareDataGetMemento() {
		super();
	}

	/**
	 * Instantiates a new jpa temp care data get memento.
	 *
	 * @param entity the entity
	 */
	public JpaTempCareDataGetMemento(KrcdtTempCareData entity) {
		super();
		this.entity = entity;
	}

	@Override
	public String getEmployeeId() {
		return entity.getId().getSid();
	}

	@Override
	public ScheduleRecordAtr getScheduleRecordAtr() {
		String valueString = String.valueOf(entity.getScheRecdAtr());
		return ScheduleRecordAtr.valueOf(valueString);
	}

	@Override
	public ManagementDays getAnnualLeaveUse() {
		return new ManagementDays(entity.getAnnleaUse());
	}

	@Override
	public TimeHoliday getTimeAnnualLeaveUsePrivateGoOut() {
		return new TimeHoliday(entity.getTimeealUsePrivGoout());
	}

	@Override
	public WorkTypeCode getWorkTypeCode() {
		return new WorkTypeCode(this.entity.getWorkTypeCode());
	}

	@Override
	public GeneralDate getYmd() {
		return this.entity.getId().getYmd();
	}

}
