package nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

/**
 * Query: 選択したカードNOの登録情報を取得する
 *
 * @author : NWS_namnv
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegistrationInfoSupportCardQuery {
	
	@Inject
	private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	/**
	 * Gets the registration info support card.
	 *
	 * @param param the param
	 * @return the registration info support card
	 */
	public RegistrationInfoDto getRegistrationInfoSupportCard(RegistrationInfoParam param) {
		RegistrationInfoDto registrationInfoDto = new RegistrationInfoDto();
		
		// [RQ622]会社IDから会社情報を取得する
		CompanyInfo companyInfo = this.companyAdapter.getCompanyInfoById(param.getCompanyId());
		if (companyInfo != null) {
			registrationInfoDto.setCompanyName(companyInfo.getCompanyName());
		}

		// [No.560]職場IDから職場の情報をすべて取得する
		GeneralDate baseDate = GeneralDate.fromString(param.getBaseDate(), "yyyy/MM/dd");
		List<WorkplaceInfor> workplaceInfors = this.workplaceConfigInfoAdapter
				.getWorkplaceInforByWkpIds(param.getCompanyId(), Arrays.asList(param.getWorkplaceId()), baseDate);
		if (workplaceInfors.size() > 0) {
			registrationInfoDto.setWorkplaceName(workplaceInfors.get(0).getWorkplaceName());
		}
		
		return registrationInfoDto;
	}

}
