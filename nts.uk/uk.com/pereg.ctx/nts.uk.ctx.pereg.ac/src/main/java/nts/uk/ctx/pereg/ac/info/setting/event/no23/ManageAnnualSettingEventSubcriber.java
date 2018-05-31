package nts.uk.ctx.pereg.ac.info.setting.event.no23;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingDomainEvent;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

/**
 * Event:半休項目の廃止区分の切り替え
 * 
 * @author lanlt
 *
 */
@Stateless
public class ManageAnnualSettingEventSubcriber implements DomainEventSubscriber<ManageAnnualSettingDomainEvent> {

	@Inject
	private PerInfoCategoryRepositoty ctgRepo;

	@Inject
	private PerInfoItemDefRepositoty itemRepo;

	private static final String ctgCode = "CS00024";

	private static final List<String> itemCdLst = Arrays
			.asList(new String[] { "IS00290", "IS00291", "IS00292", "IS00293" });

	@Override
	public Class<ManageAnnualSettingDomainEvent> subscribedToEventType() {
		return ManageAnnualSettingDomainEvent.class;
	}

	@Override
	public void handle(ManageAnnualSettingDomainEvent domainEvent) {
		String companyId = AppContexts.user().companyId();
		Optional<PersonInfoCategory> CS00024 = this.ctgRepo.getPerInfoCategoryByCtgCD(ctgCode, companyId);
		if (CS00024.isPresent()) {
			PersonInfoCategory ctg = CS00024.get();
			List<String> ctgId = Arrays.asList(new String[] { ctg.getPersonInfoCategoryId() });
			List<PersonInfoItemDefinition> itemLst = this.itemRepo.getAllItemId(ctgId, itemCdLst).stream().map(c -> {
				c.setIsAbolition(domainEvent.isParameter() == true ? IsAbolition.NOT_ABOLITION : IsAbolition.ABOLITION);
				return c;
			}).collect(Collectors.toList());
			if (itemLst.size() > 0)
				this.itemRepo.updateAbolitionItem(itemLst);
		}

	}

}
