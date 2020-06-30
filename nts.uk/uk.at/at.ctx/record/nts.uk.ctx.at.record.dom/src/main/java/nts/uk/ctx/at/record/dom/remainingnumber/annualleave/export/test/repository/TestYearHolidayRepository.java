package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test.repository;

import java.util.List;
import java.util.Optional;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.CalculationMethod;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantCondition;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.StandardCalculation;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.UseSimultaneousGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayName;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayNote;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
//import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstGrantCondition;
//import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstGrantConditionPK;
//import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstGrantHdTblSet;
//import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstGrantHdTblSetPK;

/**
 * 年休付与テーブル設定
 * @author masaaki_jinno
 *
 */
public class TestYearHolidayRepository implements YearHolidayRepository{

//	private static final String FIND_ALL_BY_CID = "SELECT a FROM KshstGrantHdTblSet a "
//			+ "WHERE a.kshstGrantHdTblSetPK.companyId = :companyId ORDER BY a.kshstGrantHdTblSetPK.yearHolidayCode ASC";
//	private static final String DELETE_CONDITION = "DELETE FROM KshstGrantCondition c "
//			+ "WHERE c.kshstGrantConditionPK.companyId =:companyId "
//			+ "AND c.kshstGrantConditionPK.yearHolidayCode =:yearHolidayCode ";
//	private static final String DELETE_GRANT_DATES = "DELETE FROM KshstGrantHdTbl g "
//			+ "WHERE g.kshstGrantHdTblPK.companyId =:companyId "
//			+ "AND g.kshstGrantHdTblPK.yearHolidayCode =:yearHolidayCode ";
	
	@Override
	public List<GrantHdTblSet> findAll(String companyId) {
//		return this.queryProxy().query(FIND_ALL_BY_CID, KshstGrantHdTblSet.class)
//				.setParameter("companyId", companyId)
//				.getList(x -> convertToDomainYearHoliday(x));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}	

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<GrantHdTblSet> findByCode(String companyId, String s_yearHolidayCode) {
//		return this.queryProxy().find(new KshstGrantHdTblSetPK(companyId, yearHolidayCode), KshstGrantHdTblSet.class)
//				.map(x -> convertToDomainYearHoliday(x));
		
//		/* 会社ID */
//		String companyId;

		/* コード */
		YearHolidayCode yearHolidayCode = new YearHolidayCode(s_yearHolidayCode);

		/* 名称 */
		YearHolidayName yearHolidayName = new YearHolidayName("年休設定");

		/* 計算方法 */
		CalculationMethod calculationMethod = CalculationMethod.ATTENDENCE_RATE; // 出勤率

		/* 計算基準 */
		StandardCalculation standardCalculation = StandardCalculation.WORK_CLOSURE_DATE; // 就業締め日

		/* 一斉付与を利用する */
		UseSimultaneousGrant useSimultaneousGrant = UseSimultaneousGrant.NOT_USE;

		/* 一斉付与日 */
		Integer simultaneousGrandMonthDays = 601;

		/* 備考 */
		YearHolidayNote yearHolidayNote = new YearHolidayNote("備考");
		
		List<GrantCondition> grantConditions = null;

		// 年休付与テーブル設定
		GrantHdTblSet aGrantHdTblSet 
			= new GrantHdTblSet(
					companyId, yearHolidayCode, yearHolidayName,
					calculationMethod, standardCalculation, 
					useSimultaneousGrant, simultaneousGrandMonthDays, 
					yearHolidayNote, grantConditions);
		
		return Optional.of(aGrantHdTblSet);
	}

	@Override
	public void add(GrantHdTblSet yearHoliday) {
//		this.commandProxy().insert(toEntity(yearHoliday));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}

	@Override
	public void update(GrantHdTblSet yearHoliday) {
//		KshstGrantHdTblSetPK key = new KshstGrantHdTblSetPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v());
//		Optional<KshstGrantHdTblSet> entity = this.queryProxy().find(key, KshstGrantHdTblSet.class);
//		KshstGrantHdTblSet kshstYearHoliday = entity.get();
//		kshstYearHoliday.yearHolidayName = yearHoliday.getYearHolidayName().v();
//		kshstYearHoliday.calculationMethod = yearHoliday.getCalculationMethod().value;
//		kshstYearHoliday.simultaneousGrandMonthDays = yearHoliday.getSimultaneousGrandMonthDays();
//		kshstYearHoliday.standardCalculation = yearHoliday.getStandardCalculation().value;
//		kshstYearHoliday.useSimultaneousGrant = yearHoliday.getUseSimultaneousGrant().value;
//		kshstYearHoliday.yearHolidayNote = yearHoliday.getYearHolidayNote().v();
//		
//		List<KshstGrantCondition> grantCoditionList = yearHoliday.getGrantConditions().stream()
//				.map(x -> {
//					KshstGrantConditionPK conditionKey = new KshstGrantConditionPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v(), x.getConditionNo());
//					return new KshstGrantCondition(conditionKey, x.getConditionValue() != null ? x.getConditionValue().v() : null, x.getUseConditionAtr().value);
//				}).collect(Collectors.toList());
//		
//		kshstYearHoliday.grantConditions = grantCoditionList;
//		
//		this.commandProxy().update(kshstYearHoliday);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}

	@Override
	public void remove(String companyId, String yearHolidayCode) {
//		this.commandProxy().remove(KshstGrantHdTblSet.class, new KshstGrantHdTblSetPK(companyId, yearHolidayCode));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	
	@Override
	public void removeCondition(String companyId,  String yearHolidayCode) {
//		this.getEntityManager().createQuery(DELETE_CONDITION)
//			.setParameter("companyId", companyId)
//			.setParameter("yearHolidayCode", yearHolidayCode)
//			.executeUpdate();
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	
	@Override
	public void removeGrantDates(String companyId, String yearHolidayCode) {
//		this.getEntityManager().createQuery(DELETE_GRANT_DATES)
//		.setParameter("companyId", companyId)
//		.setParameter("yearHolidayCode", yearHolidayCode)
//		.executeUpdate();
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	
//	/**
//	 * Convert to domain
//	 * 
//	 * @param KshstGrantHdTblSet
//	 * @return
//	 */
//	private GrantHdTblSet convertToDomainYearHoliday(KshstGrantHdTblSet x) {
//		List<GrantCondition> grantConditions = x.grantConditions.stream().map(t -> {
//			return new 	GrantCondition(t.kshstGrantConditionPK.companyId, 
//					new YearHolidayCode(t.kshstGrantConditionPK.yearHolidayCode), 
//					t.kshstGrantConditionPK.conditionNo, 
//					t.conditionValue != null ? new ConditionValue(t.conditionValue) : null, 
//					EnumAdaptor.valueOf(t.useConditionAtr, UseConditionAtr.class),
//					!CollectionUtil.isEmpty(t.yearHolidayGrants));
//		}).collect(Collectors.toList());
//		
//		return GrantHdTblSet.createFromJavaType(x.kshstGrantHdTblSetPK.companyId, 
//				x.kshstGrantHdTblSetPK.yearHolidayCode, 
//				x.yearHolidayName, 
//				x.calculationMethod, 
//				x.standardCalculation, 
//				x.useSimultaneousGrant,	
//				x.simultaneousGrandMonthDays, 
//				x.yearHolidayNote, grantConditions);
//	}

//	/**
//	 * Convert to entity
//	 * 
//	 * @param yearHoliday
//	 * @return
//	 */
//	private KshstGrantHdTblSet toEntity(GrantHdTblSet yearHoliday) {
//		List<KshstGrantCondition> grantCoditionList = yearHoliday.getGrantConditions().stream()
//				.map(x -> {
//					KshstGrantConditionPK key = new KshstGrantConditionPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v(), x.getConditionNo());
//					return new KshstGrantCondition(key, x.getConditionValue() != null ? x.getConditionValue().v() : null, x.getUseConditionAtr().value);
//				}).collect(Collectors.toList());
//		
//		return new KshstGrantHdTblSet(
//				new KshstGrantHdTblSetPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v()),
//				yearHoliday.getYearHolidayName().v(),
//				yearHoliday.getCalculationMethod().value,
//				yearHoliday.getStandardCalculation().value,
//				yearHoliday.getUseSimultaneousGrant().value,
//				yearHoliday.getSimultaneousGrandMonthDays(),
//				yearHoliday.getYearHolidayNote().v(), grantCoditionList);
//	}
}