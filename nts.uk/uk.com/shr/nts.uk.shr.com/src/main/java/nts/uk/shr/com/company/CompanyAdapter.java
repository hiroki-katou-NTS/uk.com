package nts.uk.shr.com.company;

import java.util.Optional;

public interface CompanyAdapter {

	public Optional<CompanyInfor> getCompanyByCode(String companyCode);
}
