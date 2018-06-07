package nts.uk.ctx.pereg.ac.info.setting.event.no9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingDomainEvent;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.shr.com.context.AppContexts;

/**
 * Event:年休関連カテゴリの廃止区分の切り替え
 * 
 * @author lanlt
 *
 */
@Stateless
public class AnnualPaidLeaveSettingEvenSubcriber implements DomainEventSubscriber<AnnualPaidLeaveSettingDomainEvent> {

	@Inject
	private PerInfoCategoryRepositoty ctgRepo;
	
	private static final List<String> ctgCodeLst = Arrays.asList(new String[] { "CS00024", "CS00037", "CS00038"});

	@Override
	public Class<AnnualPaidLeaveSettingDomainEvent> subscribedToEventType() {
		return AnnualPaidLeaveSettingDomainEvent.class;
	}

	@Override
	public void handle(AnnualPaidLeaveSettingDomainEvent domainEvent) {
		String companyId = AppContexts.user().companyId();
		boolean manageAnnualSetting = domainEvent.isParameter();
		updateCtgAbolish(manageAnnualSetting, companyId);
	}

	private void updateCtgAbolish(boolean params, String companyId) {
		List<PersonInfoCategory> ctgLst = new ArrayList<>();
		if (params) {
			ctgLst.addAll(ctgRepo.getPerCtgByListCtgCd(Arrays.asList("CS00024","CS00037"), companyId).stream()
					.filter(c -> c.getIsAbolition() == IsAbolition.ABOLITION).map(c -> {
						c.setAbolish(IsAbolition.NOT_ABOLITION);
						return c;
					}).collect(Collectors.toList()));

		} else {
			ctgLst.addAll(ctgRepo.getPerCtgByListCtgCd(ctgCodeLst, companyId)
					.stream().map(c -> {
						c.setAbolish(IsAbolition.ABOLITION);
						return c;
					}).collect(Collectors.toList()));
		}
		if (ctgLst.size() > 0) {
			this.ctgRepo.updateAbolition(ctgLst);
		}
	}

}
