package nts.uk.screen.com.infra.query.company;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.screen.com.app.repository.company.CompanyQueryDto;
import nts.uk.screen.com.app.repository.company.CompanyQueryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Company Query Repository implement
 * @author yennth
 *
 */
@Stateless
public class JpaCompanyQueryRepository extends JpaRepository implements CompanyQueryRepository {
	
	private static final String SELECT_NO_WHERE = "SELECT NEW " + CompanyQueryDto.class.getName() + "(c.bcmmtCompanyInforPK.companyId, c.companyCode, c.companyName, c.isAbolition) FROM BcmmtCompanyInfor c ";
	
	private static final String SELECT_BY_CONTRACT = SELECT_NO_WHERE
			+ "WHERE c.contractCd=:contractCd";

	@Override
	public List<CompanyQueryDto> findAll() {
		return this.queryProxy().query(SELECT_BY_CONTRACT, CompanyQueryDto.class)
				.setParameter("contractCd", AppContexts.user().contractCode())
				.getList();
	}

}
