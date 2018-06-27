/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ac.employment.event;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.bs.employee.pub.employment.event.EmploymentDeleteEventPub;

/**
 * The Class EmploymentDeleteEventPubSubscriber.
 * @author NWS_HoangDD
 */
@Stateless
public class EmploymentDeleteEventPubSubscriber implements DomainEventSubscriber<EmploymentDeleteEventPub>{

	/** The closure employment repository. */
	@Inject 
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.event.DomainEventSubscriber#subscribedToEventType()
	 */
	@Override
	public Class<EmploymentDeleteEventPub> subscribedToEventType() {
		return EmploymentDeleteEventPub.class;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.event.DomainEventSubscriber#handle(nts.arc.layer.dom.event.DomainEvent)
	 */
	@Override
	public void handle(EmploymentDeleteEventPub domainEvent) {
		closureEmploymentRepository.removeClousureEmp(domainEvent.getCompanyId(), domainEvent.getEmploymentcode());
	}

}

