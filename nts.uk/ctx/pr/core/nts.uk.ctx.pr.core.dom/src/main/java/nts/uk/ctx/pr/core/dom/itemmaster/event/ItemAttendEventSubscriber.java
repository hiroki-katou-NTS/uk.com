package nts.uk.ctx.pr.core.dom.itemmaster.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.core.dom.itemmaster.AvePayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;

@Stateless
public class ItemAttendEventSubscriber implements DomainEventSubscriber<ItemAttendEvent> {
	@Inject
	private ItemAttendRespository repository;
	
	@Override
	public Class<ItemAttendEvent> subscribedToEventType() {
		return ItemAttendEvent.class;
	}

	@Override
	public void handle(ItemAttendEvent domainEvent) {
		String companyCode = domainEvent.getCompanyCode();
		List<String> itemCodeList = domainEvent.getItemCodes();
		AvePayAtr avePayAtr = domainEvent.getAvePayAtr();
		
		if (CollectionUtil.isEmpty(itemCodeList)) {
			return;
		}
		
		/* do something */
		repository.updateItems(companyCode, itemCodeList, avePayAtr);
	}

}
