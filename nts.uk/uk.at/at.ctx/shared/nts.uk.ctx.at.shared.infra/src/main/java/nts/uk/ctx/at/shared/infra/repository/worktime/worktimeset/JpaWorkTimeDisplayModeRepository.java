/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayModeRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeDispMode;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeDispModePK;

/**
 * The Class JpaWorkTimeDisplayModeRepository.
 */
@Stateless
public class JpaWorkTimeDisplayModeRepository extends JpaRepository implements WorkTimeDisplayModeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeRepository#add(nts.uk.ctx.at.shared.dom.worktime.
	 * worktimedisplay.WorkTimeDisplayMode)
	 */
	@Override
	public void add(WorkTimeDisplayMode domain) {
		KshmtWorktimeDispMode entity = new KshmtWorktimeDispMode();
		domain.saveToMemento(new JpaWorkTimeDisplayModeSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeRepository#update(nts.uk.ctx.at.shared.dom.worktime.
	 * worktimedisplay.WorkTimeDisplayMode)
	 */
	@Override
	public void update(WorkTimeDisplayMode domain) {
		KshmtWorktimeDispModePK pk = new KshmtWorktimeDispModePK(domain.getCompanyId(), domain.getWorktimeCode().v());

		Optional<KshmtWorktimeDispMode> op = this.queryProxy().find(pk, KshmtWorktimeDispMode.class);
		if (op.isPresent()) {
			KshmtWorktimeDispMode entity = op.get();
			domain.saveToMemento(new JpaWorkTimeDisplayModeSetMemento(entity));
			this.commandProxy().update(entity);
		} else {
			this.add(domain);
		}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtWorktimeDispMode.class, new KshmtWorktimeDispModePK(companyId, workTimeCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeRepository#findByKey(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<WorkTimeDisplayMode> findByKey(String companyId, String workTimeCode) {
		KshmtWorktimeDispModePK pk = new KshmtWorktimeDispModePK(companyId, workTimeCode);
		Optional<KshmtWorktimeDispMode> entity = this.queryProxy().find(pk, KshmtWorktimeDispMode.class);
		return entity.isPresent()
				? Optional.of(new WorkTimeDisplayMode(new JpaWorkTimeDisplayModeGetMemento(entity.get())))
				: Optional.empty();
	}

}
