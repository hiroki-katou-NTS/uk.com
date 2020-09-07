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

	/**
	 * 【input】 ・会社ID(Optional) 
	 * 【output】 ・打刻会社一覧
	 */
	public List<GetListCompanyHasStampedDto> getListOfCompaniesHaveBeenStamped(Optional<String> cid) {

		// 1.get
		String contractCd = AppContexts.user().contractCode();
		Boolean isAbolition = false;
		List<GetListCompanyHasStampedDto> resultList = new ArrayList<>();
		List<CompanyExportForKDP003> listCompany = companyPub.get(contractCd, cid, isAbolition);
		if (listCompany.isEmpty()) {
			return new ArrayList<GetListCompanyHasStampedDto>();
		}
		resultList = listCompany.stream().map(item -> new GetListCompanyHasStampedDto(item.getCompanyCode(), item.getCompanyName(),
				item.getCompanyId(), item.getContractCd(), true, true, true)).collect(Collectors.toList());
		resultList.sort(Comparator.comparing(GetListCompanyHasStampedDto::getCompanyCode));
		return resultList;

	}

}
