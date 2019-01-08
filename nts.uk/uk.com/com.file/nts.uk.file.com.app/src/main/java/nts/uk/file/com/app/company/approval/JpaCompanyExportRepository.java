package nts.uk.file.com.app.company.approval;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
@Stateless
public class JpaCompanyExportRepository extends JpaRepository implements CompanyExportRepository {
	
	private static final String SELECT_NO_WHERE = "SELECT NEW " + CompanyData.class.getName() + "(c.bcmmtCompanyInforPK.companyId, c.companyCode, c.companyName, c.isAbolition) FROM BcmmtCompanyInfor c ";
	
	@Override
	public List<CompanyData> findAll() {
		return this.queryProxy().query(SELECT_NO_WHERE, CompanyData.class)
				.getList();
	}


}
