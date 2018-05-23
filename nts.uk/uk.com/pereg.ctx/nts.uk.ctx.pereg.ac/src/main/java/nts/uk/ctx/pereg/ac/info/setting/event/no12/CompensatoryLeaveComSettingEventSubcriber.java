package nts.uk.ctx.pereg.ac.info.setting.event.no12;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSettingDomainEvent;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

/**
 * Event:代休項目の廃止区分の切り替え
 * 
 * @author lanlt
 *
 */
@Stateless
public class CompensatoryLeaveComSettingEventSubcriber
		implements DomainEventSubscriber<CompensatoryLeaveComSettingDomainEvent> {
	@Inject
	private PerInfoCategoryRepositoty ctgRepo;

	@Inject
	private PerInfoItemDefRepositoty itemRepo;

	private static final String ctgCode = "CS00035";
	private static final List<String> itemCodeLst = Arrays.asList(new String[] {"IS00366" });

	@Override
	public Class<CompensatoryLeaveComSettingDomainEvent> subscribedToEventType() {
		return CompensatoryLeaveComSettingDomainEvent.class;
	}

	@Override
	public void handle(CompensatoryLeaveComSettingDomainEvent domainEvent) {
		String companyId = AppContexts.user().companyId();
		Optional<PersonInfoCategory> ctg35 = this.ctgRepo.getPerInfoCategoryByCtgCD(ctgCode, companyId);
		if (ctg35.isPresent()) {
			PersonInfoCategory ctg = ctg35.get();
			List<PersonInfoItemDefinition> itemLst = this.itemRepo
					.getAllItemId(Arrays.asList(new String[] { ctg.getPersonInfoCategoryId() }), itemCodeLst).stream()
					.map(c -> {
						c.setIsAbolition(
								domainEvent.isParameter() == true ? IsAbolition.NOT_ABOLITION : IsAbolition.ABOLITION);
						return c;
					}).collect(Collectors.toList());
			if (itemLst.size() > 0)
				this.itemRepo.updateAbolitionItem(itemLst);

		}
	}

}
