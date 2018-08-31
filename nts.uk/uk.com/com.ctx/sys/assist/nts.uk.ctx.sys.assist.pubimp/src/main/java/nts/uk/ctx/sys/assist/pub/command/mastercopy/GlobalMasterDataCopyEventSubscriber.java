/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.pub.command.mastercopy;

import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterDataCopyEvent;

/**
 * The Class MasterDataCopyEventSubscriber.
 */
@Stateless
public class GlobalMasterDataCopyEventSubscriber implements DomainEventSubscriber<MasterDataCopyEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.dom.event.DomainEventSubscriber#subscribedToEventType()
	 */
	@Override
	public Class<MasterDataCopyEvent> subscribedToEventType() {
		return MasterDataCopyEvent.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.dom.event.DomainEventSubscriber#handle(nts.arc.layer.dom.
	 * event.DomainEvent)
	 */
	@Override
	public void handle(MasterDataCopyEvent event) {
		GlobalMasterDataCopyEvent eventPub = new GlobalMasterDataCopyEvent(event.getCompanyId(),
				event.getCopyTargetList().stream()
						.map(item -> new GlobalCopyTargetItem(item.getMasterCopyId(),
								item.getMasterCopyTarget(),
								this.convertToGlobalCopyMethod(item.getProcessMethod())))
						.collect(Collectors.toList()),
				event.getTaskId());

		eventPub.toBePublished();
	}

	/**
	 * Convert to global copy method.
	 *
	 * @param copyMethod
	 *            the copy method
	 * @return the global copy method
	 */
	private GlobalCopyMethod convertToGlobalCopyMethod(CopyMethod copyMethod) {
		switch (copyMethod) {
		case ADD_NEW:
			return GlobalCopyMethod.ADD_NEW;
		case DO_NOTHING:
			return GlobalCopyMethod.DO_NOTHING;
		case REPLACE_ALL:
			return GlobalCopyMethod.REPLACE_ALL;
		default:
			throw new RuntimeException("Not support this value!");
		}
	}
}
