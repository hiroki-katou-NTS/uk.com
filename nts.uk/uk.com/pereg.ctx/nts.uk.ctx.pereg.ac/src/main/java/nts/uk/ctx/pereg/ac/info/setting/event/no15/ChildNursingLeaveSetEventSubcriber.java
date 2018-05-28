package nts.uk.ctx.pereg.ac.info.setting.event.no15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildNursingLeaveSettingDomainEvent;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

/**
 * Event:子の看護休暇項目の廃止区分の切り替え
 * @author laitv
 *
 */
@Stateless
public class ChildNursingLeaveSetEventSubcriber implements DomainEventSubscriber<ChildNursingLeaveSettingDomainEvent>{

	@Inject
	private PerInfoCategoryRepositoty ctgRepo;

	@Inject
	private PerInfoItemDefRepositoty itemRepo;

	private static final List<String> ctgCodeLst = Arrays.asList(new String[] { "CS00036" });

	private static final List<String> itemCdLst = Arrays.asList(new String[] { "IS00380", "IS00381","IS00382","IS00383","IS00384" });


	@Override
	public Class<ChildNursingLeaveSettingDomainEvent> subscribedToEventType() {
		return ChildNursingLeaveSettingDomainEvent.class;
	}

	@Override
	public void handle(ChildNursingLeaveSettingDomainEvent domainEvent) {
		String companyId = AppContexts.user().companyId();
		boolean parameter = domainEvent.isParameter();
		Optional<PersonInfoCategory> ctg35Opt = ctgRepo.getPerInfoCategoryByCtgCD("CS00036", companyId);
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
