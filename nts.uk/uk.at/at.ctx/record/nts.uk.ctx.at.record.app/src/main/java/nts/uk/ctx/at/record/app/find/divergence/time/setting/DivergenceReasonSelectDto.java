package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento;

public class DivergenceReasonSelectDto implements DivergenceReasonSelectSetMemento{
	
	private String divergenceReasonCode;
	
	private String reason;
	
	int reasonRequired;
	

	@Override
	public void setDivergenceReasonCode(DivergenceReasonCode divergenceReasonCode) {
		this.divergenceReasonCode=divergenceReasonCode.toString();
		
	}

	@Override
	public void setReason(DivergenceReason reason) {
		// TODO Auto-generated method stub
		this.divergenceReasonCode=reason.toString();
		
	}

	@Override
	public void setReasonRequired(DivergenceInputRequired reasonRequired) {
		// TODO Auto-generated method stub
		this.reasonRequired=reasonRequired.value;
	}

}
