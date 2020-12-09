package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcmtAnnLeaBasicInfo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaAnnLeaEmpBasicInfoRepo extends JpaRepository implements AnnLeaEmpBasicInfoRepository {
	
//	private static final String SELECT_ALL = "SELECT si FROM KrcmtAnnLeaBasicInfo si WHERE si.sid IN :listEmployeeId ";
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<AnnualLeaveEmpBasicInfo> get(String employeeId) {
		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaBasicInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
			return Optional.of(AnnualLeaveEmpBasicInfo.createFromJavaType(ent.sid, ent.workDaysPerYear,
					ent.workDaysBeforeIntro, ent.grantTableCode, ent.grantStandardDate));
		}
		return Optional.empty();
	}
	
	// chuyển sang jdbc, tăng tốc độ
	public List<AnnualLeaveEmpBasicInfo> getAll(String cid, List<String> sids) {
		List<AnnualLeaveEmpBasicInfo> result = new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_HDPAID_BASIC WHERE CID = ? AND SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString( 1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( 2 + i, subList.get(i));
				}
				
				List<AnnualLeaveEmpBasicInfo> annualLeavelst = new NtsResultSet(stmt.executeQuery()).getList(r -> {
					KrcmtAnnLeaBasicInfo entity= new KrcmtAnnLeaBasicInfo();
					entity.sid = r.getString("SID");
					entity.cid = r.getString("CID");
					entity.workDaysPerYear = r.getInt("WORK_DAYS_PER_YEAR");
					entity.workDaysBeforeIntro = r.getInt("WORK_DAYS_BEFORE_INTRO");
					entity.grantTableCode = r.getString("GRANT_TABLE_CODE");
					entity.grantStandardDate = r.getGeneralDate("GRANT_STANDARD_DATE");
					return entity.toDomain();
				});
				result.addAll(annualLeavelst);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return result;
	}

	@Override
	public void add(AnnualLeaveEmpBasicInfo basicInfo) {
		KrcmtAnnLeaBasicInfo entity = new KrcmtAnnLeaBasicInfo();
		entity.sid = basicInfo.getEmployeeId();
		entity.cid = basicInfo.getCompanyId();
		entity.workDaysPerYear = basicInfo.getWorkingDaysPerYear().isPresent()
				? basicInfo.getWorkingDaysPerYear().get().v() : null;
		entity.workDaysBeforeIntro = basicInfo.getWorkingDayBeforeIntroduction().isPresent()
				? basicInfo.getWorkingDayBeforeIntroduction().get().v() : null; 
		entity.grantTableCode = basicInfo.getGrantRule().getGrantTableCode().v();
		entity.grantStandardDate = basicInfo.getGrantRule().getGrantStandardDate();
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(AnnualLeaveEmpBasicInfo basicInfo) {
		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(basicInfo.getEmployeeId(),
				KrcmtAnnLeaBasicInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
			ent.workDaysPerYear = basicInfo.getWorkingDaysPerYear().isPresent()
					? basicInfo.getWorkingDaysPerYear().get().v() : null;
			ent.workDaysBeforeIntro = basicInfo.getWorkingDayBeforeIntroduction().isPresent()
					? basicInfo.getWorkingDayBeforeIntroduction().get().v() : null;
			ent.grantTableCode = basicInfo.getGrantRule().getGrantTableCode().v();
			ent.grantStandardDate = basicInfo.getGrantRule().getGrantStandardDate();
			this.commandProxy().update(ent);
		} else {
			add(basicInfo);
		}
	}

	@Override
	public void delete(String employeeId) {
		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaBasicInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
			this.commandProxy().remove(ent);
		}

	}

	@Override
	public void addAll(List<AnnualLeaveEmpBasicInfo> domains) {
		String INS_SQL = "INSERT INTO KRCMT_HDPAID_BASIC (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " SID, CID, WORK_DAYS_PER_YEAR, WORK_DAYS_BEFORE_INTRO,"
				+ " GRANT_TABLE_CODE, GRANT_STANDARD_DATE)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " SID_VAL, CID_VAL, WORK_DAYS_PER_YEAR_VAL, "
				+ " WORK_DAYS_BEFORE_INTRO_VAL, GRANT_TABLE_CODE_VAL, GRANT_STANDARD_DATE_VAL); ";
		String insCcd = AppContexts.user().companyCode();
		String insScd = AppContexts.user().employeeCode();
		String insPg = AppContexts.programId();
		
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg = insPg;
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c -> {
			String sql = INS_SQL;
			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
			sql = sql.replace("CID_VAL", "'" + c.getCompanyId() + "'");
			sql = sql.replace("WORK_DAYS_PER_YEAR_VAL",
					c.getWorkingDaysPerYear().isPresent() ? "" + c.getWorkingDaysPerYear().get().v() + "" : "null");
			sql = sql.replace("WORK_DAYS_BEFORE_INTRO_VAL",
					c.getWorkingDayBeforeIntroduction().isPresent()? "" + c.getWorkingDayBeforeIntroduction().get().v() + "": "null");

			sql = sql.replace("GRANT_TABLE_CODE_VAL", "'" + c.getGrantRule().getGrantTableCode().v() + "'");
			sql = sql.replace("GRANT_STANDARD_DATE_VAL", "'" + c.getGrantRule().getGrantStandardDate() + "'");

			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(List<AnnualLeaveEmpBasicInfo> domains) {
		String UP_SQL = "UPDATE KRCMT_HDPAID_BASIC SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " WORK_DAYS_PER_YEAR = WORK_DAYS_PER_YEAR_VAL , WORK_DAYS_BEFORE_INTRO = WORK_DAYS_BEFORE_INTRO_VAL, GRANT_TABLE_CODE = GRANT_TABLE_CODE_VAL, "
				+ " GRANT_STANDARD_DATE = GRANT_STANDARD_DATE_VAL"
				+ " WHERE SID = SID_VAL AND CID = CID_VAL;";
		String updCcd = AppContexts.user().companyCode();
		String updScd = AppContexts.user().employeeCode();
		String updPg = AppContexts.programId();
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c -> {
			String sql = UP_SQL;

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
			sql = sql.replace("CID_VAL", "'" + c.getCompanyId() + "'");
			sql = sql.replace("WORK_DAYS_PER_YEAR_VAL",
					c.getWorkingDaysPerYear().isPresent() ? "" + c.getWorkingDaysPerYear().get().v() + "" : "null");
			sql = sql.replace("WORK_DAYS_BEFORE_INTRO_VAL",
					c.getWorkingDayBeforeIntroduction().isPresent()? "" + c.getWorkingDayBeforeIntroduction().get().v() + "": "null");

			sql = sql.replace("GRANT_TABLE_CODE_VAL", "'" + c.getGrantRule().getGrantTableCode().v() + "'");
			sql = sql.replace("GRANT_STANDARD_DATE_VAL", "'" + c.getGrantRule().getGrantStandardDate() + "'");

			sb.append(sql);
		});
		
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
	}

	public List<AnnualLeaveEmpBasicInfo> getList(List<String> employeeIds) {
		if (employeeIds.isEmpty())
			return Collections.emptyList();
		String query = "SELECT a FROM KrcmtAnnLeaBasicInfo a WHERE a.sid IN :sids";
		List<AnnualLeaveEmpBasicInfo> result = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
			result.addAll(this.queryProxy().query(query, KrcmtAnnLeaBasicInfo.class).setParameter("sids", subIdList)
					.getList(ent -> AnnualLeaveEmpBasicInfo.createFromJavaType(ent.sid, ent.workDaysPerYear,
							ent.workDaysBeforeIntro, ent.grantTableCode, ent.grantStandardDate)));
		});
		return result;
	}

}
