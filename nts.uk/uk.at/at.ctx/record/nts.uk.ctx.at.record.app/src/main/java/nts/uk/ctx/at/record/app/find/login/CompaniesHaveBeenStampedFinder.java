/**
 * 
 */
package nts.uk.ctx.at.record.app.find.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.login.dto.StampCompany;
import nts.uk.ctx.at.record.dom.adapter.company.CompanyImportForKDP003;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv 
 * 打刻入力の会社一覧を取得する
 */
@Stateless
public class CompaniesHaveBeenStampedFinder {
	
	@Inject
	private SyCompanyRecordAdapter companyAdapter;
	
	public List<StampCompany> getListOfCompaniesHaveBeenStamped(Optional<String> cid) {
		
		// 1.get 
		
		String contractCd = AppContexts.user().contractCode();
		Boolean isAbolition = false;
		List<CompanyImportForKDP003> listCompany = companyAdapter.get(contractCd, cid, isAbolition);
		if (listCompany.isEmpty()) {
			return new ArrayList<StampCompany>();
		}
		return listCompany.stream().map(item -> new StampCompany(item.getCompanyCode(),item.getCompanyName(), item.getCompanyId(), 
										item.getContractCd(), true, true, true)).collect(Collectors.toList());
		
	}

}
