package nts.uk.ctx.pereg.ac.info.setting.event.no14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationDomainEvent;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

/**
 * Event:60H超休項目の廃止区分の切り替え
 * @author laitv
 *
 */
@Stateless
public class Com60HourVacationEventSubcriber implements DomainEventSubscriber<Com60HourVacationDomainEvent>{
	
	@Inject
	private PerInfoCategoryRepositoty ctgRepo;

	@Inject
	private PerInfoItemDefRepositoty itemRepo;

	private static final List<String> ctgCodeLst = Arrays.asList(new String[] { "CS00035" });

	private static final List<String> itemCdLst = Arrays.asList(new String[] { "IS00370", "IS00371","IS00372","IS00374" });

	@Override
	public Class<Com60HourVacationDomainEvent> subscribedToEventType() {
		return Com60HourVacationDomainEvent.class;
	}

	@Override
	public void handle(Com60HourVacationDomainEvent domainEvent) {
		String companyId = AppContexts.user().companyId();
		boolean parameter = domainEvent.isParameter();
		Optional<PersonInfoCategory> ctg35Opt = ctgRepo.getPerInfoCategoryByCtgCD("CS00035", companyId);
		if (ctg35Opt.isPresent()) {
			updateAbolition(parameter);
		}
	}
	
	private void updateAbolition(boolean parameter) {
		List<PersonInfoItemDefinition> lstItemDf = this.getItemLst();
		if (parameter) {
			lstItemDf.stream().map(c -> {
				c.setIsAbolition(IsAbolition.NOT_ABOLITION);
				return c;
			}).collect(Collectors.toList());
		} else {
			lstItemDf.stream().map(c -> {
				c.setIsAbolition(IsAbolition.ABOLITION);
				return c;
			}).collect(Collectors.toList());
		}

		if (lstItemDf.size() > 0)
			this.itemRepo.updateAbolitionItem(lstItemDf);
	}
	

	private List<PersonInfoItemDefinition> getItemLst() {
		String companyLogin = AppContexts.user().companyId();
		List<String> ctgLst = ctgRepo.getAllCtgId(ctgCodeLst, companyLogin);
		if (ctgLst.size() > 0) {
			return this.itemRepo.getAllItemId(ctgLst, itemCdLst);

		} else {
			return new ArrayList<>();
		}
	}

}
