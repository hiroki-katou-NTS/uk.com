package nts.uk.ctx.sys.portal.app.find.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.adapter.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class CompanyFinder {
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	public List<ShortCompanyDto> findAll() {
		LoginUserContext context = AppContexts.user();
		return companyAdapter.getCompanyIdList(context.userId(), context.personId())
				.stream().map(id -> companyAdapter.getCompany(id)).filter(c -> c.isPresent())
				.map(c -> new ShortCompanyDto(c.get().getCompanyId(), c.get().getCompanyName()))
				.collect(Collectors.toList());
	}
}
