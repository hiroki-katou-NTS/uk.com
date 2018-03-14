package nts.uk.ctx.at.record.infra.repository.divergence.time.setting;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcstComDrtHist;
import nts.uk.shr.com.context.AppContexts;

public class JpaDivergenceReasonInputMethodRepositoryGetMemento implements DivergenceReasonInputMethodGetMemento{
	
	/** The entities. */
	private KrcstDvgcTime entities ;
	
	public JpaDivergenceReasonInputMethodRepositoryGetMemento(){
		
	}

	public JpaDivergenceReasonInputMethodRepositoryGetMemento(KrcstDvgcTime entities) {
		
		this.entities = entities;
	}

	@Override
	public int getDivergenceTimeNo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	@Override
	public boolean getDivergenceReasonInputed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getDivergenceReasonSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DivergenceReasonSelect> getReasons() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
