package nts.uk.ctx.at.shared.infra.repository.specialholidaynew;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHolidayRepository;

/**
 * Jpa Special Holiday Repository
 * 
 * @author tanlv
 *
 */
@Stateless
public class JpaSpecialHolidayRepositoryNew extends JpaRepository implements SpecialHolidayRepository {
	private final static String SELECT_SPHD_BY_COMPANY_ID_QUERY = String.join(" ","SELECT ca.ppemtPerInfoCtgPK.perInfoCtgId,",
			 "ca.categoryCd, ca.categoryName, ca.abolitionAtr,",
			 "co.categoryParentCd, co.categoryType, co.personEmployeeType, co.fixedAtr, po.disporder, co.addItemObjCls, co.initValMasterObjCls, co.canAbolition, co.salaryUseAtr, co.personnelUseAtr, co.employmentUseAtr",
			 "FROM PpemtPerInfoCtg ca INNER JOIN PpemtPerInfoCtgCm co",
			 "ON ca.categoryCd = co.ppemtPerInfoCtgCmPK.categoryCd",
			 "INNER JOIN PpemtPerInfoCtgOrder po ON ca.cid = po.cid AND ca.ppemtPerInfoCtgPK.perInfoCtgId = po.ppemtPerInfoCtgPK.perInfoCtgId",
			 "WHERE co.ppemtPerInfoCtgCmPK.contractCd = :contractCd AND ca.cid = :cid",
			 "AND ((co.salaryUseAtr = 1 AND :salaryUseAtr = 1) OR (co.personnelUseAtr = 1 AND :personnelUseAtr = 1) OR (co.employmentUseAtr = 1 AND :employmentUseAtr = 1))",
			 "OR (:salaryUseAtr =  0 AND :personnelUseAtr = 0 AND :employmentUseAtr = 0)",
			 "ORDER BY po.disporder");
	
	@Override
	public List<SpecialHoliday> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_SPHD_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("cid", companyId)
				.getList(c -> {
					return createSphdDomainFromEntity(c);
				});
	}

	private SpecialHoliday createSphdDomainFromEntity(Object[] c) {
		String personInfoCategoryId = String.valueOf(c[0]);
		String categoryCode = String.valueOf(c[1]);
		String categoryName = String.valueOf(c[2]);
		int abolitionAtr = Integer.parseInt(String.valueOf(c[3]));
		String categoryParentCd = (c[4] != null) ? String.valueOf(c[4]) : null;
		int categoryType = Integer.parseInt(String.valueOf(c[5]));
		int personEmployeeType = Integer.parseInt(String.valueOf(c[6]));
		int fixedAtr = Integer.parseInt(String.valueOf(c[7]));
		int addObj = (c[9] != null) ? Integer.parseInt(String.valueOf(c[9])) : 1;
		int initObj = (c[10] != null) ? Integer.parseInt(String.valueOf(c[10])) : 1;
		int canAbolition = Integer.parseInt(String.valueOf(c[11]));
		int salaryUseAtr = Integer.parseInt(String.valueOf(c[12]));
		int personnelUseAtr = Integer.parseInt(String.valueOf(c[13]));
		int employmentUseAtr = Integer.parseInt(String.valueOf(c[14]));
//		return SpecialHoliday.createFromEntity(personInfoCategoryId, null, categoryCode, categoryParentCd,
//				categoryName, personEmployeeType, abolitionAtr, categoryType, fixedAtr, addObj, initObj, salaryUseAtr,
//				employmentUseAtr, personnelUseAtr, canAbolition);
		
		return null;
	}

	@Override
	public Optional<SpecialHoliday> findByCode(String companyId, int specialHolidayCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkExists(String companyId, int specialHolidayCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(SpecialHoliday specialHoliday) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SpecialHoliday specialHoliday) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId, int specialHolidayCode) {
		// TODO Auto-generated method stub
		
	}
}
