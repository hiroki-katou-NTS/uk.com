package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import java.math.BigDecimal;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason;

public class JpaDivergenceReasonSelectRepositorySetMemento implements DivergenceReasonSelectSetMemento{
	
	private KrcstDvgcReason entity;
	
	public JpaDivergenceReasonSelectRepositorySetMemento(){
		
	}
	

	public JpaDivergenceReasonSelectRepositorySetMemento(KrcstDvgcReason entity){
		this.entity=entity;
		
	}


	@Override
	public void setDivergenceReasonCode(DivergenceReasonCode divergenceReasonCode) {
		this.entity.getId().setReasonCode(divergenceReasonCode.toString());
		
	}


	@Override
	public void setReason(DivergenceReason reason) {
		this.entity.setReason(reason.toString());
		
	}


	@Override
	public void setReasonRequired(DivergenceInputRequired reasonRequired) {
		this.entity.setReasonRequired(new BigDecimal(reasonRequired.value));
	}
}
