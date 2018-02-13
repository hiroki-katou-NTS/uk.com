/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkType;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkTypePK;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkTypeSet;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkTypeSetPK;

@Stateless
public class JpaWorkTypeRepository extends JpaRepository implements WorkTypeRepository {

	private static final String SELECT_FROM_WORKTYPE = "SELECT c FROM KshmtWorkType c";

	private static final String SELECT_ALL_WORKTYPE = SELECT_FROM_WORKTYPE
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId";
	
	private static final String SELECT_WORKTYPE_BY_LEAVE_ABSENCE = SELECT_FROM_WORKTYPE
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId AND c.oneDayAtr=12"
			+" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC";
	
	private static final String SELECT_ALL_NOT_DEPRECATED_WORKTYPE = "SELECT c FROM KshmtWorkType c" 
			+ " LEFT JOIN KshmtWorkTypeOrder o ON c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId AND c.deprecateAtr = 0"
			+ " ORDER BY CASE WHEN o.dispOrder IS NULL THEN 1 ELSE 0 END, o.dispOrder ASC, c.kshmtWorkTypePK.workTypeCode ASC";
	
	private static final String SELECT_ALL_CODE_AND_NAME_OF_WORKTYPE = "SELECT c.kshmtWorkTypePK.workTypeCode, c.name FROM KshmtWorkType c" 
			+ " LEFT JOIN KshmtWorkTypeOrder o ON c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId AND c.deprecateAtr = 0"
			+ " ORDER BY CASE WHEN o.dispOrder IS NULL THEN 1 ELSE 0 END, o.dispOrder ASC, c.kshmtWorkTypePK.workTypeCode ASC";


	private static final String SELECT_FROM_WORKTYPESET = "SELECT a FROM KshmtWorkTypeSet a WHERE a.kshmtWorkTypeSetPK.companyId = :companyId"
			+ " AND a.kshmtWorkTypeSetPK.workTypeCode = :workTypeCode";

	private static final String SELECT_FROM_WORKTYPESET_CLOSE_ATR = "SELECT a FROM KshmtWorkTypeSet a WHERE a.kshmtWorkTypeSetPK.companyId = :companyId"
			+ " AND a.closeAtr = :closeAtr"
			+" ORDER BY a.kshmtWorkTypeSetPK.workTypeCode";

	private final String SELECT_WORKTYPE = SELECT_FROM_WORKTYPE + " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.kshmtWorkTypePK.workTypeCode IN :lstPossible";

	private static final String FIND_NOT_DEPRECATED_BY_LIST_CODE = SELECT_FROM_WORKTYPE
			+ " LEFT JOIN KshmtWorkTypeOrder o"
			+ " ON c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.kshmtWorkTypePK.workTypeCode IN :codes AND c.deprecateAtr = 0" + " ORDER BY c.kshmtWorkTypePK.workTypeCode ASC";

	private static final String FIND_NOT_DEPRECATED = SELECT_FROM_WORKTYPE + " LEFT JOIN KshmtWorkTypeOrder o"
			+ " ON c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId" + " AND c.deprecateAtr = 0"
			+ " ORDER BY c.kshmtWorkTypePK.workTypeCode ASC";

	private static final String DELETE_WORKTYPE_SET = "DELETE FROM KshmtWorkTypeSet c "
			+ "WHERE c.kshmtWorkTypeSetPK.companyId =:companyId "
			+ "AND c.kshmtWorkTypeSetPK.workTypeCode =:workTypeCode ";
	
	private static final String FIND_ATTENDANCE_WORKTYPE = SELECT_FROM_WORKTYPE + " LEFT JOIN KshmtWorkTypeOrder o"
			+ " ON c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.deprecateAtr = 0"
			+ " AND ((c.worktypeAtr = 0 AND c.oneDayAtr = 0)"
			+ " OR (c.worktypeAtr = 0 AND c.oneDayAtr = 7)"
			+ " OR (c.worktypeAtr = 0 AND c.oneDayAtr = 10))"
			+ " ORDER BY o.dispOrder ASC";
	
	private static final String FIND_HOLIDAY_WORKTYPE = SELECT_FROM_WORKTYPE + " LEFT JOIN KshmtWorkTypeOrder o"
			+ " ON c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.deprecateAtr = 0"
			+ " AND (c.worktypeAtr = 0 AND c.oneDayAtr = 1)"
			+ " ORDER BY o.dispOrder ASC";
	
	private static final String FIND_LEAVE_SYSTEM_WORKTYPE = SELECT_FROM_WORKTYPE + " LEFT JOIN KshmtWorkTypeOrder o"
			+ " ON c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode"
			+ " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.deprecateAtr = 0"
			+ " AND ((c.worktypeAtr = 0 AND c.oneDayAtr = 0)"
			+ " OR (c.worktypeAtr = 0 AND c.oneDayAtr = 7)"
			+ " OR (c.worktypeAtr = 0 AND c.oneDayAtr = 11))"
			+ " ORDER BY o.dispOrder ASC";
	
	private static final String FIND_WORKTYPE_BY_DEPRECATE = SELECT_FROM_WORKTYPE + " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.kshmtWorkTypePK.workTypeCode = :workTypeCd"
			+ " AND c.deprecateAtr = 0";

	// findWorkType(java.lang.String, java.lang.Integer, java.util.List,
	// java.util.List)
	private static String FIND_WORKTYPE_ALLDAY_AND_HALFDAY;
	private static String FIND_WORKTYPE_ONEDAY;
	static {
		StringBuilder builder = new StringBuilder();
		builder.append(SELECT_FROM_WORKTYPE);
		builder.append(" WHERE c.kshmtWorkTypePK.companyId = :companyId");
		builder.append(" AND c.deprecateAtr = :abolishAtr");
		builder.append(" AND c.oneDayAtr IN :oneDayAtrs");
		builder.append(" AND (c.morningAtr IN :morningAtrs");
		builder.append(" OR c.afternoonAtr IN :afternoonAtrs)");
		builder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");
		FIND_WORKTYPE_ALLDAY_AND_HALFDAY = builder.toString();
	}
	static {
		StringBuilder builder = new StringBuilder();
		builder.append(SELECT_FROM_WORKTYPE);
		builder.append(" WHERE c.kshmtWorkTypePK.companyId = :companyId");
		builder.append(" AND c.deprecateAtr = :abolishAtr");
		builder.append(" AND c.worktypeAtr = :worktypeAtr");
		builder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");
		FIND_WORKTYPE_ONEDAY = builder.toString();
	}

	private static WorkType toDomain(KshmtWorkType entity) {
		val domain = WorkType.createSimpleFromJavaType(entity.kshmtWorkTypePK.companyId,
				entity.kshmtWorkTypePK.workTypeCode, entity.symbolicName, entity.name, entity.abbreviationName,
				entity.memo, entity.worktypeAtr, entity.oneDayAtr, entity.morningAtr, entity.afternoonAtr,
				entity.deprecateAtr, entity.calculatorMethod);
		if (entity.kshmtWorkTypeOrder != null) {
			domain.setDisplayOrder(entity.kshmtWorkTypeOrder.dispOrder);
		}

		if (entity.worktypeSetList != null) {
			domain.setWorkTypeSet(
					entity.worktypeSetList.stream().map(x -> toDomainWorkTypeSet(x)).collect(Collectors.toList()));
		}
		return domain;
	}

	private static WorkTypeSet toDomainWorkTypeSet(KshmtWorkTypeSet entity) {
		val domain = WorkTypeSet.createSimpleFromJavaType(entity.kshmtWorkTypeSetPK.companyId,
				entity.kshmtWorkTypeSetPK.workTypeCode, entity.kshmtWorkTypeSetPK.workAtr, entity.digestPublicHd,
				entity.hodidayAtr, entity.countHoliday, entity.closeAtr, entity.sumAbsenseNo, entity.sumSpHolidayNo,
				entity.timeLeaveWork, entity.attendanceTime, entity.genSubHoliday, entity.dayNightTimeAsk);
		return domain;
	}

	private static KshmtWorkType toEntity(WorkType domain) {
		val entity = new KshmtWorkType();

		entity.kshmtWorkTypePK = new KshmtWorkTypePK(domain.getCompanyId(), domain.getWorkTypeCode().v());
		entity.abbreviationName = domain.getAbbreviationName().v();
		entity.afternoonAtr = domain.getDailyWork().getAfternoon().value;
		entity.calculatorMethod = domain.getCalculateMethod().value;
		entity.deprecateAtr = domain.getDeprecate().value;
		entity.memo = domain.getMemo().v();
		entity.morningAtr = domain.getDailyWork().getMorning().value;
		entity.name = domain.getName().v();
		entity.oneDayAtr = domain.getDailyWork().getOneDay().value;
		entity.symbolicName = domain.getSymbolicName().v();
		entity.worktypeAtr = domain.getDailyWork().getWorkTypeUnit().value;

		return entity;
	}

	private static KshmtWorkTypeSet toEntityWorkTypeSet(WorkTypeSet domain) {
		val entity = new KshmtWorkTypeSet(
				new KshmtWorkTypeSetPK(domain.getCompanyId(), domain.getWorkTypeCd().v(), domain.getWorkAtr().value),
				domain.getDigestPublicHd().value, domain.getHolidayAtr().value, domain.getCountHodiday().value,
				domain.getCloseAtr() != null ? domain.getCloseAtr().value : null, 
				domain.getSumAbsenseNo(), domain.getSumSpHodidayNo(),
				domain.getTimeLeaveWork().value, domain.getAttendanceTime().value, domain.getGenSubHodiday().value,
				domain.getDayNightTimeAsk().value);
		return entity;
	}
	
	private List<String> toListCodeAndName(Object[] obj){
		List<String> lstCodeAndName = new ArrayList<>();
		lstCodeAndName.add(obj[0].toString());
		lstCodeAndName.add(obj[1].toString());
		return lstCodeAndName;
	}

	@Override
	public List<WorkType> getPossibleWorkType(String companyId, List<String> lstPossible) {
		return this.queryProxy().query(SELECT_WORKTYPE, KshmtWorkType.class).setParameter("companyId", companyId)
				.setParameter("lstPossible", lstPossible).getList(c -> toDomain(c));
	}

	@Override
	public List<WorkType> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_ALL_WORKTYPE, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}
	
	@Override
	public List<WorkType> findByCompanyIdAndLeaveAbsence(String companyId) {
		return this.queryProxy().query(SELECT_WORKTYPE_BY_LEAVE_ABSENCE, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}
	
	@Override
	public List<WorkType> findNotDeprecateByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_ALL_NOT_DEPRECATED_WORKTYPE, KshmtWorkType.class)
				.setParameter("companyId", companyId).getList(c -> toDomain(c));
	}

	@Override
	public List<WorkTypeSet> findWorkTypeSet(String companyId, String workTypeCode) {
		return this.queryProxy().query(SELECT_FROM_WORKTYPESET, KshmtWorkTypeSet.class)
				.setParameter("companyId", companyId).setParameter("workTypeCode", workTypeCode)
				.getList(x -> toDomainWorkTypeSet(x));
	}
	
	@Override
	public List<WorkTypeSet> findWorkTypeSetCloseAtr(String companyId, int closeAtr) {
		return this.queryProxy().query(SELECT_FROM_WORKTYPESET_CLOSE_ATR, KshmtWorkTypeSet.class)
				.setParameter("companyId", companyId).setParameter("closeAtr", closeAtr)
				.getList(x -> toDomainWorkTypeSet(x));
	}
	@Override
	public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
		return this.queryProxy().find(new KshmtWorkTypePK(companyId, workTypeCd), KshmtWorkType.class)
				.map(x -> toDomain(x));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository#findNotDeprecated(
	 * java.lang.String)
	 */
	@Override
	public List<WorkType> findNotDeprecated(String companyId) {
		return this.queryProxy().query(FIND_NOT_DEPRECATED, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository#
	 * findNotDeprecatedByListCode(java.lang.String, java.util.List)
	 */
	@Override
	public List<WorkType> findNotDeprecatedByListCode(String companyId, List<String> codes) {
		return this.queryProxy().query(FIND_NOT_DEPRECATED_BY_LIST_CODE, KshmtWorkType.class)
				.setParameter("companyId", companyId).setParameter("codes", codes).getList(c -> toDomain(c));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository#
	 * findWorkType(java.lang.String, java.lang.Integer, java.util.List, java.util.List)
	 */
	@Override
	public List<WorkType> findWorkType(String companyId, int abolishAtr, List<Integer> allDayAtrs, List<Integer> halfAtrs) {
		return this.queryProxy().query(FIND_WORKTYPE_ALLDAY_AND_HALFDAY, KshmtWorkType.class)
				.setParameter("companyId", companyId)
				.setParameter("abolishAtr", abolishAtr)
				.setParameter("oneDayAtrs", allDayAtrs)
				.setParameter("morningAtrs", halfAtrs)
				.setParameter("afternoonAtrs", halfAtrs)
				.getList(c -> toDomain(c));
	}

	/**
	 * Insert workType to KSHMT_WORKTYPE
	 */
	@Override
	public void add(WorkType workType) {
		this.commandProxy().insert(toEntity(workType));
	}

	@Override
	public void update(WorkType workType) {
		KshmtWorkTypePK key = new KshmtWorkTypePK(workType.getCompanyId(), workType.getWorkTypeCode().v());
		KshmtWorkType entity = this.queryProxy().find(key, KshmtWorkType.class).get();
		entity.kshmtWorkTypePK = key;
		entity.symbolicName = workType.getSymbolicName().v();
		entity.name = workType.getName().v();
		entity.abbreviationName = workType.getAbbreviationName().v();
		entity.memo = workType.getMemo().v();
		entity.deprecateAtr = workType.getDeprecate().value;
		entity.worktypeAtr = workType.getDailyWork().getWorkTypeUnit().value;
		entity.oneDayAtr = workType.getDailyWork().getOneDay().value;
		entity.morningAtr = workType.getDailyWork().getMorning().value;
		entity.afternoonAtr = workType.getDailyWork().getAfternoon().value;
		entity.calculatorMethod = workType.getCalculateMethod().value;
		this.commandProxy().update(entity);
	}

	@Override
	public void remove(String companyId, String workTypeCd) {
		KshmtWorkTypePK key = new KshmtWorkTypePK(companyId, workTypeCd);
		this.commandProxy().remove(KshmtWorkType.class, key);
	}

	@Override
	public void addWorkTypeSet(WorkTypeSet workTypeSet) {
		this.commandProxy().insert(toEntityWorkTypeSet(workTypeSet));
	}

	@Override
	public void removeWorkTypeSet(String companyId, String workTypeCd) {
		this.getEntityManager().createQuery(DELETE_WORKTYPE_SET).setParameter("companyId", companyId)
				.setParameter("workTypeCode", workTypeCd).executeUpdate();
	}

	@Override
	public List<WorkType> findWorkOneDay(String companyId, int abolishAtr, int worktypeAtr) {
		return this.queryProxy().query(FIND_WORKTYPE_ONEDAY, KshmtWorkType.class).setParameter("companyId", companyId)
				.setParameter("abolishAtr", abolishAtr).setParameter("worktypeAtr", worktypeAtr)
				.getList(x -> toDomain(x));
	}

	@Override
	public List<List<String>> findCodeAndNameOfWorkTypeByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_ALL_CODE_AND_NAME_OF_WORKTYPE, Object[].class)
				.setParameter("companyId", companyId).getList(c -> toListCodeAndName(c));
	}

	public List<WorkType> getAcquiredAttendanceWorkTypes(String companyId) {
		return this.queryProxy().query(FIND_ATTENDANCE_WORKTYPE, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(x -> toDomain(x));
	}
	
	@Override
	public List<WorkType> getAcquiredHolidayWorkTypes(String companyId) {
		return this.queryProxy().query(FIND_HOLIDAY_WORKTYPE, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(x -> toDomain(x));
	}

	@Override
	public List<WorkType> getAcquiredLeaveSystemWorkTypes(String companyId) {
		return this.queryProxy().query(FIND_LEAVE_SYSTEM_WORKTYPE, KshmtWorkType.class).setParameter("companyId", companyId)
				.getList(x -> toDomain(x));
	}

	@Override
	public Optional<WorkType> findByDeprecated(String companyId, String workTypeCd) {
		return this.queryProxy().query(FIND_WORKTYPE_BY_DEPRECATE, KshmtWorkType.class).setParameter("companyId", companyId)
				.setParameter("workTypeCd", workTypeCd)
				.getSingle(x -> toDomain(x));
	}
}
