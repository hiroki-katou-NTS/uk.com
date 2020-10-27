/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrule.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KshmtClosure;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KshmtClosureHist;

/**
 * The Class JpaClosureGetMemento.
 */
@Getter
@Setter
public class JpaClosureGetMemento implements ClosureGetMemento{
	
	/** The Kclmt closure. */
	private KshmtClosure kshmtClosure;
	
	/** The kclmt closure history. */
	private List<KshmtClosureHist> kshmtClosureHistorys;
	
	/**
	 * Instantiates a new jpa closure get memento.
	 *
	 * @param KshmtClosure the kclmt closure
	 */
	public JpaClosureGetMemento(KshmtClosure kshmtClosure, List<KshmtClosureHist> kshmtClosureHistorys) {
		this.kshmtClosure = kshmtClosure;
		this.kshmtClosureHistorys = kshmtClosureHistorys;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.kshmtClosure.getKshmtClosurePK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getClosureId()
	 */
	@Override
	public ClosureId getClosureId() {
		return ClosureId.valueOf(this.kshmtClosure.getKshmtClosurePK().getClosureId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getUseClassification()
	 */
	@Override
	public UseClassification getUseClassification() {
		return UseClassification.valueOf(this.kshmtClosure.getUseClass());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getMonth()
	 */
	@Override
	public CurrentMonth getClosureMonth() {
		return new CurrentMonth(this.kshmtClosure.getClosureMonth());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getClosureHistories()
	 */
	@Override
	public List<ClosureHistory> getClosureHistories() {
		if(CollectionUtil.isEmpty(this.kshmtClosureHistorys)){
			return new ArrayList<>();
		}
		return this.kshmtClosureHistorys.stream()
				.map(entity -> new ClosureHistory(new JpaClosureHistoryGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
