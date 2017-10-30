/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.workfixed;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixedPK;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaWorkfixedRepository extends JpaRepository implements WorkfixedRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#remove(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void remove(String workPlaceId, Integer closureId) {
		this.commandProxy().remove(KrcstWorkFixed.class,
				new KrcstWorkFixedPK(workPlaceId, closureId));
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#add(nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed)
	 */
	@Override
	public void add(WorkFixed workFixed) {
		KrcstWorkFixed entity = new KrcstWorkFixed();
		workFixed.saveToMemento(new JpaWorkFixedSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#findByWorkPlaceIdAndClosureId(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Optional<WorkFixed> findByWorkPlaceIdAndClosureId(String workPlaceId, Integer closureId) {
		Optional<KrcstWorkFixed> optional = this.queryProxy().find(new KrcstWorkFixedPK(workPlaceId, closureId), KrcstWorkFixed.class);
		
		if (optional.isPresent()) {
			return Optional.ofNullable(new WorkFixed(new JpaWorkfixedGetMemento(optional.get())));	
		} 
		
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#update(nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed)
	 */
	@Override
	public void update(WorkFixed workFixed) {	
		Optional<KrcstWorkFixed> optional = this.queryProxy().find(
				new KrcstWorkFixedPK(workFixed.getWkpId(), workFixed.getClosureId()),
				KrcstWorkFixed.class);
		KrcstWorkFixed entity = null;
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KrcstWorkFixed();
		}
		workFixed.saveToMemento(new JpaWorkFixedSetMemento(entity));
		this.commandProxy().update(entity);	
	}
}
