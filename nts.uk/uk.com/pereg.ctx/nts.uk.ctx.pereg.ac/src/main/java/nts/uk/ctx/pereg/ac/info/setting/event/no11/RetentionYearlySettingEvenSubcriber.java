package nts.uk.ctx.pereg.ac.info.setting.event.no11;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingDomainEvent;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

/**
 * Event:積立年休カテゴリ、項目の廃止区分の切り替え
 * 
 * @author lanlt
 *
 */
@Stateless
public class RetentionYearlySettingEvenSubcriber implements DomainEventSubscriber<RetentionYearlySettingDomainEvent> {
	@Inject
	private PerInfoCategoryRepositoty ctgRepo;
	
	@Inject
	private PerInfoItemDefRepositoty itemRepo;
	
	private static final List<String> ctgCodeLst = Arrays.asList(new String[] { "CS00038", "CS00024" });

	private static final List<String> itemCdLst = Arrays.asList(new String[] { "IS00294" });

	@Override
	public Class<RetentionYearlySettingDomainEvent> subscribedToEventType() {
		return RetentionYearlySettingDomainEvent.class;
	}

	@Override
	public void handle(RetentionYearlySettingDomainEvent domainEvent) {
		boolean manaDivision = domainEvent.isParameter();
		String companyId = AppContexts.user().companyId();
		List<PersonInfoCategory> ctgLst = this.ctgRepo.getPerCtgByListCtgCd(ctgCodeLst, companyId);
		updateCS00038(companyId, ctgLst, manaDivision);
		updateCS00024(ctgLst, manaDivision);
	
	}
	
	private void updateCS00038(String companyId,List<PersonInfoCategory> ctgLst, boolean manaDivision ) {
		Optional<PersonInfoCategory> CS00038 = ctgLst.stream().filter(c -> c.getCategoryCode().v().equals(ctgCodeLst.get(0))).findFirst();
		if(CS00038.isPresent()) {
			PersonInfoCategory ctg = CS00038.get();
			ctg.setAbolish(manaDivision == true? IsAbolition.NOT_ABOLITION : IsAbolition.ABOLITION);
			this.ctgRepo.updateAbolition(Arrays.asList(new PersonInfoCategory[] {ctg}));
		}
	}
	
	private void updateCS00024(List<PersonInfoCategory> ctgLst, boolean manaDivision) {
		Optional<PersonInfoCategory> CS00024 = ctgLst.stream().filter(c -> c.getCategoryCode().v().equals(ctgCodeLst.get(1))).findFirst();
		if(CS00024.isPresent()) {
			List<String> ctgId = Arrays.asList(new String[] {CS00024.get().getPersonInfoCategoryId()});
			List<PersonInfoItemDefinition> itemLst = this.itemRepo.getAllItemId(ctgId, itemCdLst).stream().map(c->{
					c.setIsAbolition(manaDivision == true? IsAbolition.NOT_ABOLITION : IsAbolition.ABOLITION);
				return c;
			}).collect(Collectors.toList());
			
			if(itemLst.size() > 0)  this.itemRepo.updateAbolitionItem(itemLst);
			
		}
		
		
	}

}
