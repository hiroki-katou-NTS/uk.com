package nts.uk.ctx.basic.infra.repository.organization.employment;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.employment.Employment;
import nts.uk.ctx.basic.dom.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.dom.organization.employment.EmploymentRepository;

public class JpaEmploymentRepository extends JpaRepository implements EmploymentRepository{

	@Override
	public void add(Employment employment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Employment employment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String companyCode, EmploymentCode employmentCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Employment> findEmployment(String companyCode, EmploymentCode employmentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employment> findAllEmployment(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
