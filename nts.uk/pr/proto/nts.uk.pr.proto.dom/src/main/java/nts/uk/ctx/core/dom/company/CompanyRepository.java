package nts.uk.ctx.core.dom.company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

	public Optional<Company> find(String companyCode);
	
	public List<Company> findAll();
	
	public void add(Company company);
	
	public void update(Company company);
	
	public void remove(CompanyCode companyCode);
}
