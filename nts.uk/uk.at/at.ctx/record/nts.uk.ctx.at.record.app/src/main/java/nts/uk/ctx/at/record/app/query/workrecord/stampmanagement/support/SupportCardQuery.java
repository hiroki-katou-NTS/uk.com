package nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEditRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.shr.com.context.AppContexts;

/**
 * Query: 初期起動を行う
 *
 * @author : NWS_namnv
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SupportCardQuery {
	
	@Inject
	private SupportCardRepository supportCardRepository;
	
	@Inject
	private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private SupportCardEditRepository supportCardEditRepository;
	
	/**
	 * Initial startup support card.
	 *
	 * @return the support card dto
	 */
	public InitialStartupDto initialStartupSupportCard() {
		String companyId = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		
		// get 応援カード
		List<SupportCardDto> listSupportCard = this.supportCardRepository.getAll().stream()
				.map(SupportCardDto::toDto)
				.collect(Collectors.toList());
		
		// [RQ622]会社IDから会社情報を取得する
		List<CompanyInfo> companyInfos = listSupportCard.stream()
				.map(t -> this.companyAdapter.getCompanyInfoById(t.getCompanyId()))
				.collect(Collectors.toList());
		companyInfos.add(this.companyAdapter.getCompanyInfoById(companyId));

		// [No.560]職場IDから職場の情報をすべて取得する
		List<WorkplaceInfor> workplaceInfors = new ArrayList<WorkplaceInfor>();
		listSupportCard.stream()
				.map(t -> this.workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(t.getCompanyId(), Arrays.asList(t.getWorkplaceId()), baseDate))
				.forEach(t -> workplaceInfors.addAll(t));
		
		// get 応援カード編集設定
		SupportCardSettingDto supportCardEdit = this.supportCardEditRepository.get(companyId)
				.map(t -> new SupportCardSettingDto(t.getEditMethod().value))
				.orElse(null);
		
		return new InitialStartupDto(listSupportCard, companyInfos, workplaceInfors, supportCardEdit);
	}
}
