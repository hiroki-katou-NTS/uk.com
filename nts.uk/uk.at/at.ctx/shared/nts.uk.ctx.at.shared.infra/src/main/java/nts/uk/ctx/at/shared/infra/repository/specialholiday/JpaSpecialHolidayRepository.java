package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SphdLimit;
import nts.uk.ctx.at.shared.dom.specialholiday.SubCondition;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantSingle;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHoliday;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHolidayPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdWorkType;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdWorkTypePK;

@Stateless
public class JpaSpecialHolidayRepository extends JpaRepository implements SpecialHolidayRepository {

	private static final String SELECT_BY_CID;

	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstSpecialHoliday e");
		builderString.append(" WHERE e.kshstSpecialHolidayPK.companyId = :companyId");
		builderString.append(" ORDER BY e.kshstSpecialHolidayPK.specialHolidayCode ASC");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Special Holiday
	 * 
	 * @param kshstSpecialHoliday
	 * @return
	 */
	private SpecialHoliday convertToDomain(KshstSpecialHoliday kshstSpecialHoliday) {
		List<String> workTypeList = kshstSpecialHoliday.sphdWorkTypes.stream().map(x -> x.kshstSphdWorkTypePK.workTypeCode)
				.collect(Collectors.toList());
		
		GrantRegular grantRegular = new GrantRegular();
		GrantPeriodic grantPeriodic = new GrantPeriodic();
		SphdLimit sphdLimit = new SphdLimit();
		SubCondition subCondition = new SubCondition();
		GrantSingle grantSingle = new GrantSingle();
		
		SpecialHoliday specialHoliday = SpecialHoliday.createFromJavaType(
				kshstSpecialHoliday.kshstSpecialHolidayPK.companyId,
				kshstSpecialHoliday.kshstSpecialHolidayPK.specialHolidayCode, 
				kshstSpecialHoliday.specialHolidayName,
				kshstSpecialHoliday.grantMethod, 
				kshstSpecialHoliday.memo,
				workTypeList,
				grantRegular,
				grantPeriodic,
				sphdLimit,
				subCondition,
				grantSingle);
		return specialHoliday;
	}

	/**
	 * 
	 * Convert to Database Type Special Holiday
	 * 
	 * @param specialHoliday
	 * @return
	 */
	private KshstSpecialHoliday convertToDbType(SpecialHoliday specialHoliday) {
		KshstSpecialHoliday kshstSpecialHoliday = new KshstSpecialHoliday();
		KshstSpecialHolidayPK kshstSpecialHolidayPK = new KshstSpecialHolidayPK(specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v());
		kshstSpecialHoliday.specialHolidayName = specialHoliday.getSpecialHolidayName().v();
		kshstSpecialHoliday.grantMethod = specialHoliday.getGrantPeriodicCls().value;
		kshstSpecialHoliday.memo = specialHoliday.getMemo().v();
		// Add list KDL 002
		List<KshstSphdWorkType> workTypes = specialHoliday.getWorkTypeList().stream().map(x -> {
			KshstSphdWorkTypePK key = new KshstSphdWorkTypePK(specialHoliday.getCompanyId(), specialHoliday.getSpecialHolidayCode().v(), x);
			return new KshstSphdWorkType(key);
		}).collect(Collectors.toList());
		
		kshstSpecialHoliday.sphdWorkTypes = workTypes;
		kshstSpecialHoliday.kshstSpecialHolidayPK = kshstSpecialHolidayPK;
		return kshstSpecialHoliday;
	}

	/**
	 * Find by Company Id
	 */
	@Override
	public List<SpecialHoliday> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstSpecialHoliday.class).setParameter("companyId", companyId)
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
		KshstSpecialHolidayPK primaryKey = new KshstSpecialHolidayPK(specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v());
		KshstSpecialHoliday entity = this.queryProxy().find(primaryKey, KshstSpecialHoliday.class).get();
		entity.specialHolidayName = specialHoliday.getSpecialHolidayName().v();
		entity.grantMethod = specialHoliday.getGrantPeriodicCls().value;
		entity.memo = specialHoliday.getMemo().v();
		List<KshstSphdWorkType> workTypes = specialHoliday.getWorkTypeList().stream().map(x -> {
			KshstSphdWorkTypePK key = new KshstSphdWorkTypePK(specialHoliday.getCompanyId(), specialHoliday.getSpecialHolidayCode().v(), x);
			return new KshstSphdWorkType(key);
		}).collect(Collectors.toList());
		
		entity.sphdWorkTypes = workTypes;
		entity.kshstSpecialHolidayPK = primaryKey;
		this.commandProxy().update(entity);
	}

	/**
	 * Delete Special Holiday
	 */
	@Override
	public void delete(String companyId, int specialHolidayCode) {
		KshstSpecialHolidayPK kshstSpecialHolidayPK = new KshstSpecialHolidayPK(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstSpecialHoliday.class, kshstSpecialHolidayPK);
	}

}
