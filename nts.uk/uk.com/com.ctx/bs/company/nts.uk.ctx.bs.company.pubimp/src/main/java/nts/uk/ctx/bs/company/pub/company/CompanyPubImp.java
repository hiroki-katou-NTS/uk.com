package nts.uk.ctx.bs.company.pub.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.company.dom.company.AbolitionAtr;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CompanyPubImp implements ICompanyPub {

	@Inject
	private CompanyRepository repo;

	@Override
	public List<CompanyExport> getAllCompany() {

		return repo
				.getAllCompany().stream().map(item -> new CompanyExport(item.getCompanyCode().v(),
						item.getCompanyName().v(), item.getCompanyId(), item.getIsAbolition().value))
				.collect(Collectors.toList());

	}

	@Override
	public BeginOfMonthExport getBeginOfMonth(String cid) {

		BeginOfMonthExport result = new BeginOfMonthExport();
		Optional<Company> comOpt = repo.getComanyInfoByCid(cid);
		if (comOpt.isPresent()) {
			Company company = comOpt.get();
			result.setCid(company.getCompanyId());
			result.setStartMonth(company.getStartMonth().value);
		}
		return result;
	}

	/**
	 * for request list No.125
	 * @return Company Info
	 */
	@Override
	public CompanyExport getCompanyByCid(String cid) {
		Optional<Company> companyOpt = repo.getComanyByCid(cid);
		CompanyExport result = new CompanyExport();
		if (companyOpt.isPresent()) {
			Company company = companyOpt.get();
			result.setCompanyCode(company.getCompanyCode() == null ? "" : company.getCompanyCode().v());
			result.setCompanyId(company.getCompanyId());
			result.setCompanyName(company.getCompanyName() == null ? "" : company.getCompanyName().v());
			result.setIsAbolition(company.getIsAbolition().value);
		}
		return result;

	}

	/**
	 * For request list No.289
	 */
	@Override
	public List<String> acquireAllCompany() {
		String contractCd = AppContexts.user().contractCode();
		
		// ドメインモデル「会社情報」を取得する
		List<Company> comps = repo.getAllCompanyByContractCd(contractCd);
		
		if(comps.size() >= 1) {
			List<String> cIds = new ArrayList<>();
			
			for (Company item : comps) {
				cIds.add(item.getCompanyId());
			}
			
			return cIds;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<CompanyExport> getAllCompanyInfor() {
		String contractCd = AppContexts.user().contractCode();
		List<Company> listCompanyInfor = repo.getAllCompanyByContractCdandAboAtr(contractCd,
				AbolitionAtr.NOT_ABOLITION.value);
		if (listCompanyInfor.isEmpty())
			return new ArrayList<>();
		else {
			return listCompanyInfor.stream().map(item -> new CompanyExport(item.getCompanyCode().v(),
					item.getCompanyName().v(), item.getCompanyId(), item.getIsAbolition().value))
					.collect(Collectors.toList());
		}
	}

	@Override
	public CompanyExport getCompany(String cid) {
		Optional<Company> companyOpt = repo.getCompany(cid);
		CompanyExport result = new CompanyExport();
		if (companyOpt.isPresent()) {
			Company company = companyOpt.get();
			result.setCompanyCode(company.getCompanyCode() == null ? "" : company.getCompanyCode().v());
			result.setCompanyId(company.getCompanyId());
			result.setCompanyName(company.getCompanyName() == null ? "" : company.getCompanyName().v());
			result.setIsAbolition(company.getIsAbolition().value);
		}
		return result;
	}
}
