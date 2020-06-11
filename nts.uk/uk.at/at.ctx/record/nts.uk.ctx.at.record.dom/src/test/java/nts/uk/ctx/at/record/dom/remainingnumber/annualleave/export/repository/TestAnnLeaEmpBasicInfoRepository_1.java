package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveGrantRule;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.WorkingDayBeforeIntro;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.WorkingDayPerYear;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.PerServiceLengthTableCD;

/**
 * 年休社員基本情報
 * @author masaaki_jinno
 *
 */
public class TestAnnLeaEmpBasicInfoRepository_1 implements AnnLeaEmpBasicInfoRepository{

////	private static final String SELECT_ALL = "SELECT si FROM KrcmtAnnLeaBasicInfo si WHERE si.sid IN :listEmployeeId ";
//	@Override
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<AnnualLeaveEmpBasicInfo> get(String employeeId) {
//		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaBasicInfo.class);
//		if (entityOpt.isPresent()) {
//			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
//			return Optional.of(AnnualLeaveEmpBasicInfo.createFromJavaType(ent.sid, ent.workDaysPerYear,
//					ent.workDaysBeforeIntro, ent.grantTableCode, ent.grantStandardDate));
//		}
//		return Optional.empty();
		
//		AnnualLeaveEmpBasicInfo a 
//			= AnnualLeaveEmpBasicInfo.createFromJavaType( // ←この中でAppContextを利用しているため、エラー。
//				employeeId, // 社員ID
//				150, // 年間所定労働日数
//				140, // 導入前労働日数
//				"", // 付与ルール
//				GeneralDate.ymd(2018, 4, 1)); // grantStandardDate
		
		// 会社ID
		String companyId = "1";

		// 年間所定労働日数
		WorkingDayPerYear workingDayPerYear = new WorkingDayPerYear(150);

		// 導入前労働日数
		WorkingDayBeforeIntro workingDayBeforeIntro = new WorkingDayBeforeIntro(140);

		// 付与ルール
		AnnualLeaveGrantRule grantRule = new AnnualLeaveGrantRule();
		PerServiceLengthTableCD aPerServiceLengthTableCD = new PerServiceLengthTableCD("1");
		grantRule.setGrantTableCode(aPerServiceLengthTableCD);
		
		AnnualLeaveEmpBasicInfo a 
			= new AnnualLeaveEmpBasicInfo(
					employeeId
					, companyId
					, Optional.of(workingDayPerYear)
					, Optional.of(workingDayBeforeIntro)
					, grantRule);
		
		return Optional.of(a);
	}
	
	// chuyển sang jdbc, tăng tốc độ
	public List<AnnualLeaveEmpBasicInfo> getAll(String cid, List<String> sids) {
//		List<AnnualLeaveEmpBasicInfo> result = new ArrayList<>();
//		
//		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
//			String sql = "SELECT * FROM KRCMT_ANNLEA_INFO WHERE CID = ? AND SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
//			
//			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
//				stmt.setString( 1, cid);
//				for (int i = 0; i < subList.size(); i++) {
//					stmt.setString( 2 + i, subList.get(i));
//				}
//				
//				List<AnnualLeaveEmpBasicInfo> annualLeavelst = new NtsResultSet(stmt.executeQuery()).getList(r -> {
//					KrcmtAnnLeaBasicInfo entity= new KrcmtAnnLeaBasicInfo();
//					entity.sid = r.getString("SID");
//					entity.cid = r.getString("CID");
//					entity.workDaysPerYear = r.getInt("WORK_DAYS_PER_YEAR");
//					entity.workDaysBeforeIntro = r.getInt("WORK_DAYS_BEFORE_INTRO");
//					entity.grantTableCode = r.getString("GRANT_TABLE_CODE");
//					entity.grantStandardDate = r.getGeneralDate("GRANT_STANDARD_DATE");
//					return entity.toDomain();
//				});
//				result.addAll(annualLeavelst);
//				
//			}catch (SQLException e) {
//				throw new RuntimeException(e);
//			}
//		});
//		
//		return result;
		
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}

	@Override
	public void add(AnnualLeaveEmpBasicInfo basicInfo) {
//		KrcmtAnnLeaBasicInfo entity = new KrcmtAnnLeaBasicInfo();
//		entity.sid = basicInfo.getEmployeeId();
//		entity.cid = basicInfo.getCompanyId();
//		entity.workDaysPerYear = basicInfo.getWorkingDaysPerYear().isPresent()
//				? basicInfo.getWorkingDaysPerYear().get().v() : null;
//		entity.workDaysBeforeIntro = basicInfo.getWorkingDayBeforeIntroduction().isPresent()
//				? basicInfo.getWorkingDayBeforeIntroduction().get().v() : null; 
//		entity.grantTableCode = basicInfo.getGrantRule().getGrantTableCode().v();
//		entity.grantStandardDate = basicInfo.getGrantRule().getGrantStandardDate();
//		this.commandProxy().insert(entity);
		
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}

	@Override
	public void update(AnnualLeaveEmpBasicInfo basicInfo) {
//		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(basicInfo.getEmployeeId(),
//				KrcmtAnnLeaBasicInfo.class);
//		if (entityOpt.isPresent()) {
//			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
//			ent.workDaysPerYear = basicInfo.getWorkingDaysPerYear().isPresent()
//					? basicInfo.getWorkingDaysPerYear().get().v() : null;
//			ent.workDaysBeforeIntro = basicInfo.getWorkingDayBeforeIntroduction().isPresent()
//					? basicInfo.getWorkingDayBeforeIntroduction().get().v() : null;
//			ent.grantTableCode = basicInfo.getGrantRule().getGrantTableCode().v();
//			ent.grantStandardDate = basicInfo.getGrantRule().getGrantStandardDate();
//			this.commandProxy().update(ent);
//		} else {
//			add(basicInfo);
//		}
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}

	@Override
	public void delete(String employeeId) {
//		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaBasicInfo.class);
//		if (entityOpt.isPresent()) {
//			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
//			this.commandProxy().remove(ent);
//		}
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}

	@Override
	public void addAll(List<AnnualLeaveEmpBasicInfo> domains) {
//		String INS_SQL = "INSERT INTO KRCMT_ANNLEA_INFO (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
//				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
//				+ " SID, CID, WORK_DAYS_PER_YEAR, WORK_DAYS_BEFORE_INTRO,"
//				+ " GRANT_TABLE_CODE, GRANT_STANDARD_DATE)"
//				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
//				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
//				+ " SID_VAL, CID_VAL, WORK_DAYS_PER_YEAR_VAL, "
//				+ " WORK_DAYS_BEFORE_INTRO_VAL, GRANT_TABLE_CODE_VAL, GRANT_STANDARD_DATE_VAL); ";
//		String insCcd = AppContexts.user().companyCode();
//		String insScd = AppContexts.user().employeeCode();
//		String insPg = AppContexts.programId();
//		
//		String updCcd = insCcd;
//		String updScd = insScd;
//		String updPg = insPg;
//		StringBuilder sb = new StringBuilder();
//		domains.stream().forEach(c -> {
//			String sql = INS_SQL;
//			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
//			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
//			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
//			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");
//
//			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
//			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
//			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
//			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
//
//			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
//			sql = sql.replace("CID_VAL", "'" + c.getCompanyId() + "'");
//			sql = sql.replace("WORK_DAYS_PER_YEAR_VAL",
//					c.getWorkingDaysPerYear().isPresent() ? "" + c.getWorkingDaysPerYear().get().v() + "" : "null");
//			sql = sql.replace("WORK_DAYS_BEFORE_INTRO_VAL",
//					c.getWorkingDayBeforeIntroduction().isPresent()? "" + c.getWorkingDayBeforeIntroduction().get().v() + "": "null");
//
//			sql = sql.replace("GRANT_TABLE_CODE_VAL", "'" + c.getGrantRule().getGrantTableCode().v() + "'");
//			sql = sql.replace("GRANT_STANDARD_DATE_VAL", "'" + c.getGrantRule().getGrantStandardDate() + "'");
//
//			sb.append(sql);
//		});
//
//		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
//		System.out.println(records);
		
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		
	}

	@Override
	public void updateAll(List<AnnualLeaveEmpBasicInfo> domains) {
//		String UP_SQL = "UPDATE KRCMT_ANNLEA_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
//				+ " WORK_DAYS_PER_YEAR = WORK_DAYS_PER_YEAR_VAL , WORK_DAYS_BEFORE_INTRO = WORK_DAYS_BEFORE_INTRO_VAL, GRANT_TABLE_CODE = GRANT_TABLE_CODE_VAL, "
//				+ " GRANT_STANDARD_DATE = GRANT_STANDARD_DATE_VAL"
//				+ " WHERE SID = SID_VAL AND CID = CID_VAL;";
//		String updCcd = AppContexts.user().companyCode();
//		String updScd = AppContexts.user().employeeCode();
//		String updPg = AppContexts.programId();
//		StringBuilder sb = new StringBuilder();
//		domains.stream().forEach(c -> {
//			String sql = UP_SQL;
//
//			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
//			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
//			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
//			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
//
//			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
//			sql = sql.replace("CID_VAL", "'" + c.getCompanyId() + "'");
//			sql = sql.replace("WORK_DAYS_PER_YEAR_VAL",
//					c.getWorkingDaysPerYear().isPresent() ? "" + c.getWorkingDaysPerYear().get().v() + "" : "null");
//			sql = sql.replace("WORK_DAYS_BEFORE_INTRO_VAL",
//					c.getWorkingDayBeforeIntroduction().isPresent()? "" + c.getWorkingDayBeforeIntroduction().get().v() + "": "null");
//
//			sql = sql.replace("GRANT_TABLE_CODE_VAL", "'" + c.getGrantRule().getGrantTableCode().v() + "'");
//			sql = sql.replace("GRANT_STANDARD_DATE_VAL", "'" + c.getGrantRule().getGrantStandardDate() + "'");
//
//			sb.append(sql);
//		});
//		
//		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
//		System.out.println(records);
		
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        
	}

	public List<AnnualLeaveEmpBasicInfo> getList(List<String> employeeIds) {
//		if (employeeIds.isEmpty())
//			return Collections.emptyList();
//		String query = "SELECT a FROM KrcmtAnnLeaBasicInfo a WHERE a.sid IN :sids";
//		List<AnnualLeaveEmpBasicInfo> result = new ArrayList<>();
//		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
//			result.addAll(this.queryProxy().query(query, KrcmtAnnLeaBasicInfo.class).setParameter("sids", subIdList)
//					.getList(ent -> AnnualLeaveEmpBasicInfo.createFromJavaType(ent.sid, ent.workDaysPerYear,
//							ent.workDaysBeforeIntro, ent.grantTableCode, ent.grantStandardDate)));
//		});
//		return result;
		
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}

}