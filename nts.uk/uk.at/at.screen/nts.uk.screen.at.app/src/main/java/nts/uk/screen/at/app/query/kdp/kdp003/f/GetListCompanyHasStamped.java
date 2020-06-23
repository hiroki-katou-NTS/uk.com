/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.bs.company.pub.company.CompanyExportForKDP003;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv 打刻入力の会社一覧を取得する
 */
@Stateless
public class GetListCompanyHasStamped {

	@Inject
	private SyCompanyRecordAdapter companyAdapter;
	
	@Inject
	private ICompanyPub companyPub;

	public List<GetListCompanyHasStampedDto> getListOfCompaniesHaveBeenStamped(Optional<String> cid) {

		// 1.get
		String contractCd = AppContexts.user().contractCode();
		Boolean isAbolition = false;
		List<CompanyExportForKDP003> listCompany = companyPub.get(contractCd, cid, isAbolition);
		if (listCompany.isEmpty()) {
			return new ArrayList<GetListCompanyHasStampedDto>();
		}
		return listCompany.stream().map(item -> new GetListCompanyHasStampedDto(item.getCompanyCode(), item.getCompanyName(),
				item.getCompanyId(), item.getContractCd(), true, true, true)).collect(Collectors.toList());

	}

}
