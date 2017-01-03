package nts.uk.ctx.basic.dom.organization.employment;

import java.util.List;
import java.util.Optional;

public interface EmploymentRespository {
	
	void add(Employment employment);
	
	void update(Employment employment);
	
	void remove(String companyCode, EmploymentCode employmentCode);
	
	Optional<Employment> findEmployment(String companyCode, EmploymentCode employmentCode);
	
	List<Employment> findAllEmployment(String companyCode);
}
