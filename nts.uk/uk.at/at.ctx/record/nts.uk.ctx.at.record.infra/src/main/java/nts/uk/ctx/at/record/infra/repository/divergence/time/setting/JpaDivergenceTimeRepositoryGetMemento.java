package nts.uk.ctx.at.record.infra.repository.divergence.time.setting;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeName;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeUseSet;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;

public class JpaDivergenceTimeRepositoryGetMemento implements DivergenceTimeGetMemento{

	/** The entities. */
	private KrcstDvgcTime entities ;

	public JpaDivergenceTimeRepositoryGetMemento(KrcstDvgcTime entities) {
		
		this.entities = entities;
	}

	@Override
	public int getDivergenceTimeNo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DivergenceTimeUseSet getDivTimeUseSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DivergenceTimeName getDivTimeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DivergenceType getDivType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DivergenceTimeErrorCancelMethod getErrorCancelMedthod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Double> getTargetItems() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
