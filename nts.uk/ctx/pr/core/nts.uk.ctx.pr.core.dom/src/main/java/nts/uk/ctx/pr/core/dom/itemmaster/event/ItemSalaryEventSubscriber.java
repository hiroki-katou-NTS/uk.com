package nts.uk.ctx.pr.core.dom.itemmaster.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.core.dom.itemmaster.AvePayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;

@Stateless
public class ItemSalaryEventSubscriber implements DomainEventSubscriber<ItemSalaryEvent> {
	@Inject
	private ItemSalaryRespository repository;
	
	@Override
	public Class<ItemSalaryEvent> subscribedToEventType() {
		return ItemSalaryEvent.class;
	}

	@Override
	public void handle(ItemSalaryEvent domainEvent) {
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
