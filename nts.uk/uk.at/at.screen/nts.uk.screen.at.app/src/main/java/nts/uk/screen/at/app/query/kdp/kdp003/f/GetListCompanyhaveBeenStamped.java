/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.bs.company.pub.company.CompanyExportForKDP003;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.screen.at.app.query.kdp.kdp003.f.dto.GetListCompanyHasStampedDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv 
 * 打刻入力の会社一覧を取得する
 * Screen 2
 */
@Stateless
public class GetListCompanyhaveBeenStamped {
	
	@Inject
	private ICompanyPub companyPub;
	
	@Inject
	private SettingsUsingEmbossingRepository embossingRepo;

	/**
	 * 【input】 ・会社ID(Optional) 
	 * 【output】 ・打刻会社一覧
	 */
	public List<GetListCompanyHasStampedDto> getListOfCompaniesHaveBeenStamped(Optional<String> cid) {
		// 1.get
		//fix contractCode request UI ,AO  ※2020/4　の説明ではASP認証について内容が存在しないため、契約コードは「000000000000」で実施してください
		
		String contractCd = "000000000000" ;
		Boolean isAbolition = false;
		List<GetListCompanyHasStampedDto> resultList = new ArrayList<>();
		
		List<CompanyExportForKDP003> listCompany = companyPub.get(contractCd, cid, isAbolition);

		List<String> companyIds = listCompany.stream().map(m -> m.getCompanyId()).collect(Collectors.toList());

		List<SettingsUsingEmbossing> listEmbossing = embossingRepo.getSettingEmbossingByComIds(companyIds);

		if (listCompany.isEmpty()) {
			return new ArrayList<GetListCompanyHasStampedDto>();
		}

		// 2020/06/12 EA3789
		// 未作成のため、利用区分はすべて「利用する」で返してください。
		resultList = listCompany.stream().map(item -> {
			Optional<SettingsUsingEmbossing> domain = listEmbossing.stream()
					.filter(f -> f.getCompanyId().equals(item.getCompanyId())).findFirst();

			GetListCompanyHasStampedDto dto = new GetListCompanyHasStampedDto(item.getCompanyCode(),
					item.getCompanyName(), item.getCompanyId(), item.getContractCd(), true, true, true);
			/**
			 * 2020/07/27 EA3811
			 * 1: 氏名選択 ⇒ 打刻会社一覧.氏名選択利用
			 * 2: 指認証打刻 ⇒ 打刻会社一覧.指認証打刻
			 * 3:ICカード打刻 ⇒ 打刻会社一覧.ICカード打刻
			 */
			domain.ifPresent(c -> {
				dto.setIcCardStamp(c.isIc_card());
				dto.setFingerAuthStamp(c.isFinger_authc());
				dto.setSelectUseOfName(c.isName_selection());
			});

			return dto;
		}).collect(Collectors.toList());
		
		resultList.sort(Comparator.comparing(GetListCompanyHasStampedDto::getCompanyCode));
		
		return resultList;
	}

}
