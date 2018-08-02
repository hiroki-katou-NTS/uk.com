/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ac.employment.event;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.bs.employee.pub.employment.event.EmploymentCreateEventPub;

/**
 * The Class EmploymentCreateEventPubSubscriber.
 * @author NWS_HoangDD
 */
@Stateless
public class EmploymentCreateEventPubSubscriber implements DomainEventSubscriber<EmploymentCreateEventPub>{
	
	/** The closure employment repository. */
	@Inject 
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.event.DomainEventSubscriber#subscribedToEventType()
	 */
	@Override
	public Class<EmploymentCreateEventPub> subscribedToEventType() {
		return EmploymentCreateEventPub.class;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.event.DomainEventSubscriber#handle(nts.arc.layer.dom.event.DomainEvent)
	 */
	@Override
	public void handle(EmploymentCreateEventPub domainEvent) {
		List<ClosureEmployment> lstClosureEmployment = new ArrayList<>();
		lstClosureEmployment.add(new ClosureEmployment(domainEvent.getCompanyId(), domainEvent.getEmploymentcode(), ClosureId.RegularEmployee.value));
		closureEmploymentRepository.addListClousureEmp(domainEvent.getCompanyId(), lstClosureEmployment);
	}



}

