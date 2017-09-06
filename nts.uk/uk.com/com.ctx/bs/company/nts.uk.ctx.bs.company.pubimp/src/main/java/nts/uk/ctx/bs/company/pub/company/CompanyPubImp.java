package nts.uk.ctx.bs.company.pub.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.bs.company.dom.company.CompanyRepository;

public class CompanyPubImp implements ICompanyPub {

	@Inject
	private CompanyRepository repo;

	@Override
	public List<CompanyExport> getAllCompany() {
		
		return repo.getAllCompany().stream().map(item -> new CompanyExport(item.getCompanyCode().v(),
						item.getCompanyName().v(), item.getCompanyId().v(), item.getIsAbolition().value))
				.collect(Collectors.toList());

	}

}
