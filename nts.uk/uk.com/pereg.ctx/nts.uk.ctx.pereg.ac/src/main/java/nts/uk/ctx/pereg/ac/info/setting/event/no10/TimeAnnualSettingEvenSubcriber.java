package nts.uk.ctx.pereg.ac.info.setting.event.no10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualSettingDomainEvent;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

/**
 * Event 時間年休項目の廃止区分の切り替え
 * 
 * @author lanlt
 *
 */
@Stateless
public class TimeAnnualSettingEvenSubcriber implements DomainEventSubscriber<TimeAnnualSettingDomainEvent> {

	@Inject
	private PerInfoCategoryRepositoty ctgRepo;

	@Inject
	private PerInfoItemDefRepositoty itemRepo;

	private static final List<String> ctgCodeLst = Arrays.asList(new String[] { "CS00024", "CS00037" });

	private static final List<String> itemCdLst = Arrays
			.asList(new String[] { "IS00286", "IS00287", "IS00288", "IS00289", "IS00391", "IS00394", "IS00397" });

	@Override
	public Class<TimeAnnualSettingDomainEvent> subscribedToEventType() {
		return TimeAnnualSettingDomainEvent.class;
	}

	@Override
	public void handle(TimeAnnualSettingDomainEvent domainEvent) {
		boolean timeVacationSetting = domainEvent.isParameter();
		updateItem(timeVacationSetting);

	}
	

	private void updateItem(boolean params) {
		List<PersonInfoItemDefinition> itemUpdateLst = new ArrayList<>();
		itemUpdateLst.addAll(setAbolition( getItemLst() , params));
		if (itemUpdateLst.size() > 0)
			this.itemRepo.updateAbolitionItem(itemUpdateLst);
	}
	
	private List<PersonInfoItemDefinition> getItemLst(){
		String companyLogin = AppContexts.user().companyId();
		List<String> ctgLst = ctgRepo.getAllCtgId(ctgCodeLst, companyLogin);
		if (ctgLst.size() > 0) {
			return this.itemRepo.getAllItemId(ctgLst, itemCdLst);
		
		}else {
			return new ArrayList<>();
		}
	}
	
	private  List<PersonInfoItemDefinition> setAbolition(List<PersonInfoItemDefinition> itemLst, boolean params ){
		List<PersonInfoItemDefinition> itemUpdateLst = new ArrayList<>();
		if (itemLst.size() > 0) {
			itemUpdateLst.addAll(itemLst.stream().map(c -> {
				c.setIsAbolition(params == true? IsAbolition.NOT_ABOLITION: IsAbolition.ABOLITION);
				return c;
			}).collect(Collectors.toList()));
		}
		return itemUpdateLst;
	}

}
