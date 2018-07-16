package nts.uk.ctx.at.shared.infra.repository.specialholidaynew;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation.KshstElapseYears;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation.KshstElapseYearsPK;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation.KshstGrantDateTbl;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation.KshstGrantDateTblPK;

@Stateless
public class JpaGrantDateTblRepository extends JpaRepository implements GrantDateTblRepository {
	private final static String SELECT_GD_BY_SPHDCD_QUERY = "SELECT e.pk.grantDateCd, e.grantName, e.isSpecified, e.fixedAssign, e.numberOfDays "
			+ "FROM KshstGrantDateTbl e "
			+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode "
			+ "ORDER BY e.pk.grantDateCd ASC";
	
	private final static String SELECT_GRANDATE_BY_CODE_QUERY = "SELECT e.pk.grantDateCd, e.grantName, e.isSpecified, e.fixedAssign, e.numberOfDays "
			+ "FROM KshstGrantDateTbl e "
			+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode AND e.pk.grantDateCd = :grantDateCd "
			+ "ORDER BY e.pk.grantDateCd ASC";
	
	private final static String SELECT_ELAPSE_BY_GDCD_QUERY = "SELECT e.pk.grantDateCd, e.pk.elapseNo, e.grantedDays, e.months, e.years "
			+ "FROM KshstElapseYears e "
			+ "WHERE e.pk.companyId = :companyId AND e.pk.grantDateCd = :grantDateCd "
			+ "ORDER BY e.pk.elapseNo ASC";
	
	private final static String DELETE_All_ELAPSE = "DELETE FROM KshstElapseYears e "
			+ "WHERE e.pk.companyId =:companyId "
			+ "AND e.pk.grantDateCd =:grantDateCd ";
	
	private GrantDateTbl createGdDomainFromEntity(Object[] c) {
		String grantDateCd = String.valueOf(c[0]);
		String grantName = String.valueOf(c[1]);
		boolean isSpecified = Integer.parseInt(String.valueOf(c[2])) == 1 ? true : false;
		boolean fixedAssign = Integer.parseInt(String.valueOf(c[3])) == 1 ? true : false;
		int numberOfDays = c[4] != null ? Integer.parseInt(String.valueOf(c[4])) : 0;
		
		return GrantDateTbl.createFromJavaType(grantDateCd, grantName, isSpecified, fixedAssign, numberOfDays);
	}
	
	private ElapseYear createDomainFromEntity(Object[] c) {
		String grantDateCd = String.valueOf(c[0]);
		int elapseNo = Integer.parseInt(String.valueOf(c[1]));
		int grantedDays = Integer.parseInt(String.valueOf(c[2]));
		int months = Integer.parseInt(String.valueOf(c[3]));
		int years = Integer.parseInt(String.valueOf(c[4]));
		
		return ElapseYear.createFromJavaType(grantDateCd, elapseNo, grantedDays, months, years);
	}

	private KshstGrantDateTbl createGrantDateTblFromDomain(GrantDateTbl domain) {
		KshstGrantDateTblPK pk = new KshstGrantDateTblPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v(), domain.getGrantDateCode().v());
		return new KshstGrantDateTbl(pk, domain.getGrantDateName().v(), domain.isSpecified() ? 1 : 0, domain.isFixedAssign() ? 1 : 0, domain.getNumberOfDays());
	}

	private KshstElapseYears toElapseEntity(ElapseYear domain) {
		KshstElapseYearsPK pk = new KshstElapseYearsPK(domain.getCompanyId(), domain.getGrantDateCode(), domain.getElapseNo());
		return new KshstElapseYears(pk, domain.getGrantedDays().v(), domain.getMonths().v(), domain.getYears().v());
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
	public Optional<GrantDateTbl> findByCode(String companyId, int specialHolidayCode, String grantDateCode) {
		return this.queryProxy().query(SELECT_GRANDATE_BY_CODE_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode)
				.setParameter("grantDateCd", grantDateCode)
				.getSingle(c -> {
					return createGdDomainFromEntity(c);
				});
	}

	@Override
	public List<ElapseYear> findElapseByGrantDateCd(String companyId, String grantDateCode) {
		return this.queryProxy().query(SELECT_ELAPSE_BY_GDCD_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("grantDateCd", grantDateCode)
				.getList(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public void add(GrantDateTbl grantDateTbl) {
		this.commandProxy().insert(createGrantDateTblFromDomain(grantDateTbl));
		
		List<KshstElapseYears> lstEntity = grantDateTbl.getElapseYear().stream().map(e -> this.toElapseEntity(e)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstEntity);
	}

	@Override
	public void update(GrantDateTbl grantDateTbl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId, int specialHolidayCode, String grantDateCode) {
		this.getEntityManager().createQuery(DELETE_All_ELAPSE)
			.setParameter("companyId", companyId)
			.setParameter("grantDateCd", grantDateCode)
			.executeUpdate();
		
		KshstGrantDateTblPK gPk = new KshstGrantDateTblPK(companyId, specialHolidayCode, grantDateCode);
		this.commandProxy().remove(KshstGrantDateTbl.class, gPk);
	}
}
