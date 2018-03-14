package nts.uk.ctx.at.record.infra.repository.divergence.time.setting;

import java.math.BigDecimal;
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
		return entities.getId().getNo();
	}

	@Override
	public String getCompanyId() {
		return entities.getId().getCid();
	}

	@Override
	public boolean getDivergenceReasonInputed() {
		if (entities.getDvgcReasonInputed().compareTo(BigDecimal.ZERO)==1)return false;
		return true;
	}

	@Override
	public boolean getDivergenceReasonSelected() {
		if (entities.getDvgcReasonSelected().compareTo(BigDecimal.ZERO)==1)return false;
		return true;
	}

	@Override
	public List<DivergenceReasonSelect> getReasons() {
		return null;
	}
	
	

}
