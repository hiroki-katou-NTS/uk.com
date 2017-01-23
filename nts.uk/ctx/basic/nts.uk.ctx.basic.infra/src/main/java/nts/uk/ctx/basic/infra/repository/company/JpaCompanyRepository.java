package nts.uk.ctx.basic.infra.repository.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyRepository;
/**
 * 
 * @author lanlt
 *
 */
@Stateless
public class JpaCompanyRepository extends JpaRepository implements CompanyRepository {
	private static final String ADD_COMPANY;
	private static final String UPDATE_COMPANY;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtDep e");
		builderString.append(" WHERE e.cmnmtDepPK.companyCode = :companyCode");
		ADD_COMPANY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtDep e");
		builderString.append(" WHERE e.cmnmtDepPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtDepPK.departmentCode = :departmentCode");
		UPDATE_COMPANY = builderString.toString();
	}
	
	

	@Override
	public Optional<Company> getCompanyDetail(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> getAllCompanys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> getHistoryBefore(String companyCode) {
		// TODO Auto-generated method stub
		//this.commandProxy().insert();
		return null;
	}

	@Override
	public void add(Company company) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Company company) {
		// TODO Auto-generated method stub
		
	}

}
