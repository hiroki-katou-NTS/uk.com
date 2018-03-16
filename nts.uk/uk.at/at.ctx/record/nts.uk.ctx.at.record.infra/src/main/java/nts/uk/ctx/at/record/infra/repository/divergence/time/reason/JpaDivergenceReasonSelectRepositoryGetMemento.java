package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason;

public class JpaDivergenceReasonSelectRepositoryGetMemento implements DivergenceReasonSelectGetMemento{
	
	private KrcstDvgcReason entity;
	
	JpaDivergenceReasonSelectRepositoryGetMemento (){
		
	}
	
	JpaDivergenceReasonSelectRepositoryGetMemento(KrcstDvgcReason entity){
		this.entity=entity;
	}

	@Override
	public DivergenceReasonCode getDivergenceReasonCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DivergenceReason getReason() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DivergenceInputRequired getReasonRequired() {
		// TODO Auto-generated method stub
		return null;
	}

}
