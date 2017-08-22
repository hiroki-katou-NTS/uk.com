package nts.uk.ctx.bs.company.app.find.share;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.company.app.find.company.CompanyDto;
import nts.uk.ctx.bs.company.app.find.company.CompanyFinder;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;

@Stateless
public class CompanyAdapterImpl implements CompanyAdapter{
	
	@Inject
	private CompanyFinder companyFinder;

	@Override
	public Optional<CompanyInfor> getCurrentCompany() {
		Optional<CompanyInfor> result = Optional.empty();
		
		CompanyDto dto = companyFinder.getCompany();
		if (dto != null) {
			result = Optional.of(new CompanyInfor(dto.getCompanyCode(), dto.getCompanyName()));
		}
		
		return result;
	}

}
