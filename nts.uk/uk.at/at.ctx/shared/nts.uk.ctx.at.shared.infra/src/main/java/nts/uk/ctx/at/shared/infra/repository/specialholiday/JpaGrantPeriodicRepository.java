package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHoliday;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHolidayPK;

public class JpaGrantPeriodicRepository extends JpaRepository implements GrantPeriodicRepository  {
	
	private static final String SELECT_BY_CID;
	
	static{
		
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstSpecialHoliday e");
		builderString.append(" WHERE e.kshstSpecialHolidayPK.companyId = :companyId");
		builderString.append(" ORDER BY e.kshstSpecialHolidayPK.specialHolidayCode ASC");
		SELECT_BY_CID = builderString.toString();
	}
	
	/**
	 * Convert to Domain Special Holiday
	 * @param kshstSpecialHoliday
	 * @return
	 */
	private SpecialHoliday convertToDomain(KshstSpecialHoliday kshstSpecialHoliday){
		SpecialHoliday specialHoliday = SpecialHoliday.createFromJavaType(
				kshstSpecialHoliday.kshstSpecialHolidayPK.companyId,
				kshstSpecialHoliday.kshstSpecialHolidayPK.specialHolidayCode,
				kshstSpecialHoliday.specialHolidayName,
				kshstSpecialHoliday.grantPeriodicCls,
				kshstSpecialHoliday.memo);
		return specialHoliday;
	}
	
	/**
	 * Convert to Database Type Special Holiday
	 * @param specialHoliday
	 * @return
	 */
	private KshstSpecialHoliday convertToDbType(SpecialHoliday specialHoliday) {
		KshstSpecialHoliday kshstSpecialHoliday = new KshstSpecialHoliday();
		KshstSpecialHolidayPK kshstSpecialHolidayPK = new KshstSpecialHolidayPK(
				specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().toString());
		kshstSpecialHoliday.specialHolidayName = specialHoliday.getSpecialHolidayName().toString();
		kshstSpecialHoliday.grantPeriodicCls = specialHoliday.getGrantPeriodicCls().value;
		kshstSpecialHoliday.memo = specialHoliday.getMemo().toString();
		kshstSpecialHoliday.kshstSpecialHolidayPK = kshstSpecialHolidayPK;
		return kshstSpecialHoliday;
	}
	
	/**
	 * Find by Company Id
	 */
	@Override
	public List<SpecialHoliday> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstSpecialHoliday.class)
				.setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Special Holiday
	 */
	@Override
	public void add(SpecialHoliday specialHoliday) {
		this.commandProxy().insert(convertToDbType(specialHoliday));		
	}
	
	/**
	 * Update Special Holiday
	 */
	@Override
	public void update(SpecialHoliday specialHoliday) {
		KshstSpecialHolidayPK primaryKey = new KshstSpecialHolidayPK(specialHoliday.getCompanyId(), specialHoliday.getSpecialHolidayCode().v());
		KshstSpecialHoliday entity = this.queryProxy().find(primaryKey, KshstSpecialHoliday.class).get();
		entity.specialHolidayName = specialHoliday.getSpecialHolidayName().toString();
		entity.grantPeriodicCls = specialHoliday.getGrantPeriodicCls().value;
		entity.memo = specialHoliday.getMemo().toString();
		entity.kshstSpecialHolidayPK = primaryKey;
		this.commandProxy().update(entity);	
	}
	
	/**
	 * Delete Special Holiday
	 */
	@Override
	public void delete(String companyId, String specialHolidayCode) {
		KshstSpecialHolidayPK kshstSpecialHolidayPK = new KshstSpecialHolidayPK(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstSpecialHoliday.class, kshstSpecialHolidayPK);
	}
	
}
