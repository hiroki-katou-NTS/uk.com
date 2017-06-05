package nts.uk.ctx.basic.infra.repository.company.organization.employment;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.basic.dom.company.organization.employment.Employment;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentRepository;

@Stateless
public class JpaEmploymentRepository implements EmploymentRepository {

	@Override
	public List<Employment> findAll(String CompanyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
