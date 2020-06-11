package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantReferenceDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantSimultaneity;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
//import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstLengthServiceTbl;
//import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstLengthServiceTblPK;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.Month;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;


/**
 * 勤続年数テーブル
 * @author masaaki_jinno
 *
 */
public class TestLengthServiceRepository_1 implements LengthServiceRepository{
//	private static final String SELECT_BY_CODE = "SELECT c FROM KshstLengthServiceTbl c "
//			+ "WHERE c.kshstLengthServiceTblPK.companyId = :companyId "
//			+ "AND c.kshstLengthServiceTblPK.yearHolidayCode = :yearHolidayCode ORDER BY c.kshstLengthServiceTblPK.grantNum ";
//
//	private static final String DELETE_ALL = "DELETE FROM KshstLengthServiceTbl c "
//								+ "WHERE c.kshstLengthServiceTblPK.companyId = :companyId ";
//	
//	private static final String DELETE_BY_CD = DELETE_ALL + "AND c.kshstLengthServiceTblPK.yearHolidayCode = :yearHolidayCode ";
//	
//	private static final String DELETE_BY_KEY = DELETE_BY_CD + "AND c.kshstLengthServiceTblPK.grantNum IN :grantNums ";
//	/**
//	* convert from KshstLengthServiceTbl entity to LengthServiceTbl domain
//	* @param entity
//	* @return
//	*/
//	private LengthServiceTbl convertToDomain(KshstLengthServiceTbl entity){
//	return LengthServiceTbl.createFromJavaType(entity.kshstLengthServiceTblPK.companyId, 
//												entity.kshstLengthServiceTblPK.yearHolidayCode, entity.kshstLengthServiceTblPK.grantNum, 
//												entity.allowStatus, entity.standGrantDay, entity.year, entity.month);
//	}
	
//	private KshstLengthServiceTbl toEntity(LengthServiceTbl domain){
//	val entity = new KshstLengthServiceTbl();
//	entity.kshstLengthServiceTblPK = new KshstLengthServiceTblPK(domain.getCompanyId(), domain.getYearHolidayCode().v(), domain.getGrantNum().v());
//	entity.allowStatus = domain.getAllowStatus().value;
//	entity.month = domain.getMonth() != null ? domain.getMonth().v() : 0;
//	entity.year = domain.getYear()!= null ? domain.getYear().v() : 0;
//	entity.standGrantDay = domain.getStandGrantDay().value;
//	return entity;
//	}
	/**
	* find by key
	* @author yennth
	*/
	@Override
	public Optional<LengthServiceTbl> find(String companyId, String yearHolidayCode, int grantNum) {
//	return this.queryProxy().find(new KshstLengthServiceTblPK(companyId, yearHolidayCode, grantNum), KshstLengthServiceTbl.class)
//							.map(c -> convertToDomain(c));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return Optional.empty();
	}
	/**
	* find by code 
	* @author yennth
	*/
	@Override
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<LengthServiceTbl> findByCode(String companyId, String s_yearHolidayCode) {
//	return this.queryProxy().query(SELECT_BY_CODE, KshstLengthServiceTbl.class)
//								.setParameter("companyId", companyId)
//								.setParameter("yearHolidayCode", yearHolidayCode).getList(c -> convertToDomain(c));
//		System.out.print("要実装");
//		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
//	    System.out.println(className);
//	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//        System.out.println(methodName);
//        return null;
		
		List<LengthServiceTbl> list = new ArrayList<LengthServiceTbl>();
		
//		/* 会社ID */
//		String companyId;

		/* 年休付与テーブル設定コード */
		YearHolidayCode yearHolidayCode = new YearHolidayCode(s_yearHolidayCode);
		
		/* 付与回数 */
		GrantNum grantNum = new GrantNum(10);
		
		/* 一斉付与する */
		GrantSimultaneity allowStatus = GrantSimultaneity.NOT_USE;
		
		/* 付与基準日 */ // ooooo
		GrantReferenceDate standGrantDay = GrantReferenceDate.YEAR_HD_REFERENCE_DATE;

		/* 年数 */
		LimitedTimeHdDays year = new LimitedTimeHdDays(2);
		
		/* 月数 */
		Month month = new Month(24);
		
		GeneralDate grantDate = GeneralDate.ymd(2019, 4, 1);
		
		// 勤続年数テーブル
		LengthServiceTbl lengthServiceTbl 
			= new LengthServiceTbl(
					companyId, yearHolidayCode, grantNum, allowStatus, 
					standGrantDay, year, month, grantDate);
        
		list.add(lengthServiceTbl);
		
		return list;        
	}
	
	/**
	* insert a length service
	* @author yennth
	*/
	@Override
	public void add(LengthServiceTbl holidayGrant) {
//	this.commandProxy().insert(toEntity(holidayGrant));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	/**
	* update a length service
	*/
	@Override
	public void update(LengthServiceTbl holidayGrant) {
	//this.commandProxy().update(toEntity(holidayGrant));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	/**
	* remove LengthServiceTbl by code 
	* @author yennth
	*/
	@Override
	public void remove(String companyId, String yearHolidayCode) {
//	this.getEntityManager().createQuery(DELETE_BY_CD)
//							.setParameter("companyId", companyId)
//							.setParameter("yearHolidayCode", yearHolidayCode)
//							.executeUpdate();
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	/**
	* delete by companyId
	* @author yennth
	*/
	@Override
	public void remove(String companyId) {
//	this.getEntityManager().createQuery(DELETE_ALL)
//							.setParameter("companyId", companyId)
//							.executeUpdate();
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	/**
	* remove LengthServiceTbl by list grantNum
	* @author yennth
	*/
	@Override
	public void remove(String companyId, String yearHolidayCode, List<Integer> grantNums) {
//	CollectionUtil.split(grantNums, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
//		this.getEntityManager().createQuery(DELETE_BY_KEY)
//			.setParameter("companyId", companyId)
//			.setParameter("yearHolidayCode", yearHolidayCode)
//			.setParameter("grantNums", subList)
//			.executeUpdate();			
//	});
//	this.getEntityManager().flush();
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	
	@Override
	public Map<String, List<LengthServiceTbl>> findByCode(String companyId, List<String> yearHolidayCode) {
//	if (yearHolidayCode.isEmpty())
//		return Collections.emptyMap();
//	String query = "SELECT c FROM KshstLengthServiceTbl c "
//			+ "WHERE c.kshstLengthServiceTblPK.companyId = :companyId "
//			+ "AND c.kshstLengthServiceTblPK.yearHolidayCode IN :yearHolidayCode ORDER BY c.kshstLengthServiceTblPK.grantNum ";
//	return this.queryProxy().query(query, KshstLengthServiceTbl.class).setParameter("companyId", companyId)
//			.setParameter("yearHolidayCode", yearHolidayCode).getList(c -> convertToDomain(c)).stream()
//			.collect(Collectors.groupingBy(i -> i.getYearHolidayCode().v()));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}
}

