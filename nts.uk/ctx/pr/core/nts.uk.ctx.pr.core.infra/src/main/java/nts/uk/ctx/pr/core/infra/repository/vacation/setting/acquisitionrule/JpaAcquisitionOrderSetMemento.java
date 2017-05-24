package nts.uk.ctx.pr.core.infra.repository.vacation.setting.acquisitionrule;


import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.Priority;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionOrderSetMemento;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionType;
import nts.uk.ctx.pr.core.infra.entity.vacation.setting.acquisitionrule.KmfstAcquisitionRule;

public class JpaAcquisitionOrderSetMemento implements AcquisitionOrderSetMemento {
	
	private KmfstAcquisitionRule typeValue;
	
	private AcquisitionType vaType;
	
	private Priority priority;

	
	public JpaAcquisitionOrderSetMemento(AcquisitionType vaType, Priority priority){
		super();
		this.vaType = vaType;
		this.priority = priority;
	}
	@Override
	public void setVacationType(AcquisitionType vacationType) {
		this.vaType = vacationType;
	}

	@Override
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}
