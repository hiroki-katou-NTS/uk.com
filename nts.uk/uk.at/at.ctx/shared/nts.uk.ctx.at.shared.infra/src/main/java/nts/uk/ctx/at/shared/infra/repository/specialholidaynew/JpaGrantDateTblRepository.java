package nts.uk.ctx.at.shared.infra.repository.specialholidaynew;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantDateTblRepository;

@Stateless
public class JpaGrantDateTblRepository extends JpaRepository implements GrantDateTblRepository {
	private final static String SELECT_GD_BY_SPHDCD_QUERY = "SELECT e.pk.grantDateCd, e.grantName, e.isSpecified, e.fixedAssign, e.numberOfDays "
			+ "FROM KshstGrantDateTbl e "
			+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode "
			+ "ORDER BY e.pk.grantDateCd ASC";
	
	private final static String SELECT_ELAPSE_BY_GDCD_QUERY = "SELECT e.pk.grantDateCd, e.grantName "
			+ "FROM KshstGrantDateTbl e "
			+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode "
			+ "ORDER BY e.pk.grantDateCd ASC";
	
	private GrantDateTbl createGdDomainFromEntity(Object[] c) {
		String grantDateCd = String.valueOf(c[0]);
		String grantName = String.valueOf(c[1]);
		boolean isSpecified = Integer.parseInt(String.valueOf(c[2])) == 1 ? true : false;
		boolean fixedAssign = Integer.parseInt(String.valueOf(c[3])) == 1 ? true : false;
		int numberOfDays = Integer.parseInt(String.valueOf(c[4]));
		
		return GrantDateTbl.createFromJavaType(grantDateCd, grantName, isSpecified, fixedAssign, numberOfDays);
	}
	
	private ElapseYear createDomainFromEntity(Object[] c) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<GrantDateTbl> findBySphdCd(String companyId, int specialHolidayCode) {
		return this.queryProxy().query(SELECT_GD_BY_SPHDCD_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode)
				.getList(c -> {
					return createGdDomainFromEntity(c);
				});
	}

	@Override
	public List<ElapseYear> findElapseByGrantDateCd(String companyId, int specialHolidayCode, int grantDateCode) {
		return this.queryProxy().query(SELECT_ELAPSE_BY_GDCD_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode)
				.setParameter("grantDateCode", grantDateCode)
				.getList(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public void add(GrantDateTbl specialHoliday) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GrantDateTbl specialHoliday) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId, int specialHolidayCode, int grantDateCode) {
		// TODO Auto-generated method stub
		
	}
}
