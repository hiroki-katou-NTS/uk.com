package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstElapseYears;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstElapseYearsPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantDateTbl;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantDateTblPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */
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
	
	private final static String SELECT_ELAPSE_BY_GDCD_QUERY = "SELECT e.pk.specialHolidayCode, e.pk.grantDateCd, e.pk.elapseNo, e.grantedDays, e.months, e.years "
			+ "FROM KshstElapseYears e "
			+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode AND e.pk.grantDateCd = :grantDateCd "
			+ "ORDER BY e.pk.elapseNo ASC";
	
	private final static String DELETE_All_ELAPSE = "DELETE FROM KshstElapseYears e "
			+ "WHERE e.pk.companyId =:companyId "
			+ "AND e.pk.grantDateCd =:grantDateCd ";
	
	private final static String CHANGE_ALL_PROVISION = "UPDATE KshstGrantDateTbl e SET e.isSpecified = 0 "
			+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode";
	private String SELECT_CODE_ISSPECIAL = "SELECT e FROM KshstGrantDateTbl e"
			+ " WHERE e.pk.companyId = :companyId "
			+ " AND e.pk.specialHolidayCode = :specialHolidayCode "
			+ " AND e.isSpecified = 1";
	/**
	 * Create Grant Date Domain From Entity
	 * @param c
	 * @return
	 */
	private GrantDateTbl createGdDomainFromEntity(Object[] c) {
		String grantDateCd = String.valueOf(c[0]);
		String grantName = String.valueOf(c[1]);
		boolean isSpecified = Integer.parseInt(String.valueOf(c[2])) == 1 ? true : false;
		boolean fixedAssign = Integer.parseInt(String.valueOf(c[3])) == 1 ? true : false;
		int numberOfDays = c[4] != null ? Integer.parseInt(String.valueOf(c[4])) : 0;
		
		return GrantDateTbl.createFromJavaType(grantDateCd, grantName, isSpecified, fixedAssign, numberOfDays);
	}
	
	/**
	 * Create Elapse Year Domain From Entity
	 * @param c
	 * @return
	 */
	private ElapseYear createDomainFromEntity(Object[] c) {
		String companyId = AppContexts.user().companyId();
		int specialHolidayCode = Integer.parseInt(String.valueOf(c[0]));
		String grantDateCd = String.valueOf(c[1]);
		int elapseNo = Integer.parseInt(String.valueOf(c[2]));
		int grantedDays = Integer.parseInt(String.valueOf(c[3]));
		int months = Integer.parseInt(String.valueOf(c[4]));
		int years = Integer.parseInt(String.valueOf(c[5]));
		
		return ElapseYear.createFromJavaType(companyId, specialHolidayCode, grantDateCd, elapseNo, grantedDays, months, years);
	}

	/**
	 * Create Grant Date from Domain
	 * @param domain
	 * @return
	 */
	private KshstGrantDateTbl createGrantDateTblFromDomain(GrantDateTbl domain) {
		KshstGrantDateTblPK pk = new KshstGrantDateTblPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v(), domain.getGrantDateCode().v());
		return new KshstGrantDateTbl(pk, domain.getGrantDateName().v(), domain.isSpecified() ? 1 : 0, domain.isFixedAssign() ? 1 : 0, domain.getNumberOfDays());
	}

	/**
	 * Convert domain to entity
	 * @param domain
	 * @return
	 */
	private KshstElapseYears toElapseEntity(ElapseYear domain) {
		KshstElapseYearsPK pk = new KshstElapseYearsPK(domain.getCompanyId(), domain.getSpecialHolidayCode(), domain.getGrantDateCode(), domain.getElapseNo());
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
	public List<ElapseYear> findElapseByGrantDateCd(String companyId, int specialHolidayCode, String grantDateCode) {
		return this.queryProxy().query(SELECT_ELAPSE_BY_GDCD_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode)
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
		KshstGrantDateTblPK pk = new KshstGrantDateTblPK(grantDateTbl.getCompanyId(), grantDateTbl.getSpecialHolidayCode().v(), grantDateTbl.getGrantDateCode().v());
		KshstGrantDateTbl old = this.queryProxy().find(pk, KshstGrantDateTbl.class).orElse(null);
		old.grantName = grantDateTbl.getGrantDateName().v();
		old.isSpecified = grantDateTbl.isSpecified() ? 1 : 0;
		old.fixedAssign = grantDateTbl.isFixedAssign() ? 1 : 0;
		old.numberOfDays = grantDateTbl.getNumberOfDays();
		this.commandProxy().update(old);
		updateElapseYears(grantDateTbl);
	}

	/**
	 * Re-update Elapse Year when update a Grant Date
	 * @param grantDateTbl
	 */
	private void updateElapseYears(GrantDateTbl grantDateTbl) {
		this.getEntityManager().createQuery(DELETE_All_ELAPSE)
				.setParameter("companyId", grantDateTbl.getCompanyId())
				.setParameter("grantDateCd", grantDateTbl.getGrantDateCode().v())
				.executeUpdate();
		
		List<KshstElapseYears> lstEntity = grantDateTbl.getElapseYear().stream().map(e -> this.toElapseEntity(e)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstEntity);
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

	@Override
	public void changeAllProvision(int specialHolidayCode) {
		String companyId = AppContexts.user().companyId();
		
		this.getEntityManager().createQuery(CHANGE_ALL_PROVISION)
					.setParameter("companyId", companyId)
					.setParameter("specialHolidayCode", specialHolidayCode)
					.executeUpdate();
	}

	@Override
	public Optional<GrantDateTbl> findByCodeAndIsSpecified(String companyId, int specialHolidayCode) {
		return this.queryProxy().query(SELECT_CODE_ISSPECIAL, KshstGrantDateTbl.class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode)
				.getSingle(c -> {
					String grantDateCd = String.valueOf(c.pk.grantDateCd);
					String grantName = String.valueOf(c.grantName);
					boolean isSpecified = Integer.parseInt(String.valueOf(c.isSpecified)) == 1 ? true : false;
					boolean fixedAssign = Integer.parseInt(String.valueOf(c.fixedAssign)) == 1 ? true : false;
					int numberOfDays = c.numberOfDays != null ? Integer.parseInt(String.valueOf(c.numberOfDays)) : 0;
					
					return GrantDateTbl.createFromJavaType(grantDateCd, grantName, isSpecified, fixedAssign, numberOfDays);
				});
	}
}
