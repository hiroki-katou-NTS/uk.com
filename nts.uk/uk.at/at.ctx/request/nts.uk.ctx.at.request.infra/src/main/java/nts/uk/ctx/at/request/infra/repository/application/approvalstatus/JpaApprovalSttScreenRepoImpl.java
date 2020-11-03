package nts.uk.ctx.at.request.infra.repository.application.approvalstatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.ApprovalSttScreenRepository;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApprovalSttScreenRepoImpl extends JpaRepository implements ApprovalSttScreenRepository {
	
	@Override
	public String deleteTemporaryTable() {
		String sql = 
				"drop table IF EXISTS ##KAF018_WORKPLACE; " +
				"drop table IF EXISTS ##KAF018_SKBSYITERM; " +
				"drop table IF EXISTS ##KAF_MSNSNS; ";
		new NtsStatement(sql, this.jdbcProxy()).execute();
		this.getEntityManager().flush();
		return sql;
	}

	@Override
	public String setSqlSessionParam(DatePeriod period) {
		YearMonth yearMonth = new YearMonth(202010);
		ClosureId closureId = ClosureId.RegularEmployee;
		String companyId = AppContexts.user().companyId();
		String sql = 
				"declare @shime_start   datetime = @start; " +
				"declare @shime_end  datetime = @end; " +
				"declare @shime_YM    nchar(128) = @yearMonth; " +
				"declare @shime_ID nchar(128) = @closureId; " +
				"declare @CID nchar(128) = @companyId;";
		sql = sql.replaceAll("@start", "'" + period.start().toString() + "'")
				.replaceAll("@end", "'" + period.end().toString() + "'")
				.replaceAll("@yearMonth", "'" + yearMonth.v().toString() + "'")
				.replaceAll("@closureId", "'" + String.valueOf(closureId.value) + "'")
				.replaceAll("@companyId", "'" + companyId + "'");
		// new NtsStatement(sql, this.jdbcProxy()).execute();
		this.getEntityManager().createNativeQuery(sql).executeUpdate();
		return sql;
	}

	@Override
	public String setWorkPlaceTempTable(List<String> wkpIDLst) {
		String sql = 
				"SELECT DISTINCT BWI.WKPID " + 
				"INTO ##KAF018_WORKPLACE " +
				"FROM BSYMT_WORKPLACE_INFO BWI " +
				"WHERE BWI.WKPID IN @wkpIDLst ";
		if(CollectionUtil.isEmpty(wkpIDLst)) {
			// wkpIDLst = Arrays.asList("");
		}
//		try {
			new NtsStatement(sql, this.jdbcProxy())
				.paramString("wkpIDLst", wkpIDLst)
				.execute();
			this.getEntityManager().flush();
//		} catch (Exception e) {
//			this.deleteTemporaryTable();
//		}
		return sql;
	}

	@Override
	public String setEmployeeTemp(DatePeriod period, List<String> wkpCDLst) {
		String sql = 
				"SELECT SKBSYINIO.WORKPLACE_ID,SKBSYINIO.SID,KYO.EMP_CD,SKBSYINIO.WORK_ST,SKBSYINIO.WORK_ED,SKBSYINIO.COMP_ST,SKBSYINIO.COMP_ED,KYO.START_DATE as KYO_ST,KYO.END_DATE as KYO_ED " +
				"INTO   ##KAF018_SKBSYITERM " +
				"FROM ( " +
				"	SELECT SKBSYIN.WORKPLACE_ID,SKBSYIN.SID,SKBSYIN.START_DATE as WORK_ST,SKBSYIN.END_DATE as WORK_ED,SKR.START_DATE as COMP_ST,SKR.END_DATE as COMP_ED " +
				"	FROM ( " +
				"		SELECT SSRK.WORKPLACE_ID,SSRK.SID,SSR.START_DATE,SSR.END_DATE " +
				"		FROM         BSYMT_AFF_WORKPLACE_HIST SSR " +
				"		INNER JOIN   BSYMT_AFF_WPL_HIST_ITEM SSRK " +
				"		ON           SSR.HIST_ID = SSRK.HIST_ID " +
				"		INNER JOIN   ##KAF018_WORKPLACE KAF_WPL " +
				"		ON           SSRK.WORKPLACE_ID = KAF_WPL.WKPID " + 
				"		WHERE        @shime_start <= SSR.END_DATE " +
				"		AND          SSR.START_DATE <= @shime_end " +
				"		) SKBSYIN " +
				"	INNER JOIN       BSYMT_AFF_COM_HIST SKR " +
				"	ON               SKBSYIN.SID = SKR.SID " +
				"	INNER JOIN       BSYMT_EMP_DTA_MNG_INFO SDKJ " +
				"	ON               SDKJ.SID = SKR.SID " +
				"	WHERE            @shime_start <= SKR.END_DATE " +
				"	AND              SKR.START_DATE <= @shime_end " +
				"	AND SDKJ.DEL_STATUS_ATR = '0' " +
				") SKBSYINIO " +
				"LEFT JOIN  ( " +
				"	SELECT KRK.SID,KRK.EMP_CD,KR.START_DATE,KR.END_DATE " +
				"	FROM BSYMT_EMPLOYMENT_HIST KR " +
				"	INNER JOIN BSYMT_EMPLOYMENT_HIS_ITEM KRK " +
				"	ON KR.HIST_ID = KRK.HIST_ID " +
				"	WHERE KRK.EMP_CD IN @wkpCDLst " +
				"	AND @shime_start <= KR.END_DATE " +
				"	AND KR.START_DATE <= @shime_end " +
				") KYO " +
				"ON SKBSYINIO.SID = KYO.SID; ";
		if(CollectionUtil.isEmpty(wkpCDLst)) {
			wkpCDLst = Arrays.asList("");
		}
//		try {
			new NtsStatement(sql, this.jdbcProxy())
				.paramDate("shime_start", period.start())
				.paramDate("shime_end", period.end())
				.paramString("wkpCDLst", wkpCDLst)
				.execute();
			this.getEntityManager().flush();
//		} catch (Exception e) {
//			this.deleteTemporaryTable();
//		}
		return sql;
	}
	
	@Override
	public Map<String, Integer> getCountEmp() {
		Map<String, Integer> result = new HashMap<>();
		String sql = 
				"SELECT MSNSHIN.WORKPLACE_ID,COUNT(MSNSHIN.SID) AS COUNTSID " +
				"FROM ( " +
				"	SELECT DISTINCT SKBSYITERM.WORKPLACE_ID,SKBSYITERM.SID " +
				"	FROM ##KAF018_SKBSYITERM SKBSYITERM " +
				") MSNSHIN " +
				"GROUP BY MSNSHIN.WORKPLACE_ID; ";
//		try {
			List<Pair<String, Integer>> listPair = new NtsStatement(sql, this.jdbcProxy())
				.getList(rec -> {
					String wkpID = rec.getString("WORKPLACE_ID");
					Integer count = rec.getInt("COUNTSID");
					return Pair.of(wkpID, count);
				});
			for(Pair<String, Integer> pair : listPair) {
				result.put(pair.getLeft(), pair.getRight());
			}
//		} catch (Exception e) {
//			this.deleteTemporaryTable();
//		}
		return result;
	}

	@Override
	public void setUnApprApp(DatePeriod period) {
		String sql = 
				"SELECT             SRI.EMPLOYEE_ID as APP_SID,SRI.APPROVAL_RECORD_DATE as APP_DATE,SRI.ROOT_STATE_ID " +
				"INTO    ##KAF_MSNSNS " +
				"FROM               WWFDT_APPROVAL_ROOT_STATE SRI " +
				"INNER JOIN         WWFDT_APPROVAL_PHASE_ST SFI " +
				"ON                 SRI.ROOT_STATE_ID = SFI.ROOT_STATE_ID " +
				"WHERE              SFI.APP_PHASE_ATR IN ('0','3','4') " +
				"AND                @shime_start <= SRI.APPROVAL_RECORD_DATE " +
				"AND                SRI.APPROVAL_RECORD_DATE <= @shime_end; ";
//		try {
			new NtsStatement(sql, this.jdbcProxy())
				.paramDate("shime_start", period.start())
				.paramDate("shime_end", period.end())
				.execute();
			this.getEntityManager().flush();
//		} catch (Exception e) {
//			this.deleteTemporaryTable();
//		}
	}

	@Override
	public Map<String, Integer> getCountUnApprApp() {
		Map<String, Integer> result = new HashMap<>();
		String sql = 
				"SELECT MSNSHIN.WORKPLACE_ID,COUNT(MSNSHIN.SID) AS COUNTSID " +
				"FROM ( " +
				"	SELECT DISTINCT SKBSYITERM.WORKPLACE_ID,SKBSYITERM.SID " +
				"	FROM ##KAF018_SKBSYITERM SKBSYITERM " +
				"	INNER JOIN  ##KAF_MSNSNS MSNSNS " +
				"	ON              SKBSYITERM.SID  = MSNSNS.APP_SID " +
				"	WHERE           SKBSYITERM.WORK_ST <= MSNSNS.APP_DATE " +
				"	AND             SKBSYITERM.COMP_ST <= MSNSNS.APP_DATE " +
				"	AND             SKBSYITERM.KYO_ST <= MSNSNS.APP_DATE " +
				"	AND             MSNSNS.APP_DATE <= SKBSYITERM.WORK_ED " +
				"	AND             MSNSNS.APP_DATE <= SKBSYITERM.COMP_ED " +
				"	AND             MSNSNS.APP_DATE <= SKBSYITERM.KYO_ED " +
				") MSNSHIN " +
				"GROUP BY MSNSHIN.WORKPLACE_ID; ";
//		try {
			List<Pair<String, Integer>> listPair = new NtsStatement(sql, this.jdbcProxy())
				.getList(rec -> {
					String wkpID = rec.getString("WORKPLACE_ID");
					Integer count = rec.getInt("COUNTSID");
					return Pair.of(wkpID, count);
				});
			for(Pair<String, Integer> pair : listPair) {
				result.put(pair.getLeft(), pair.getRight());
			}
//		} catch (Exception e) {
//			this.deleteTemporaryTable();
//		}
		return result;
	}

	@Override
	public List<EmpPeriod> getEmpFromWkp(String wkpID) {
		String sql = 
				"SELECT WORKPLACE_ID,SID,EMP_CD,WORK_ST,WORK_ED,COMP_ST,COMP_ED,KYO_ST,KYO_ED " +
				"FROM   ##KAF018_SKBSYITERM " +
				"WHERE  WORKPLACE_ID =　@wkpID; ";
		return new NtsStatement(sql, this.jdbcProxy()).paramString("wkpID", wkpID).getList(rec -> {
			return new EmpPeriod(
					rec.getString("WORKPLACE_ID"), 
					rec.getString("SID"), 
					rec.getString("EMP_CD"), 
					rec.getGeneralDate("WORK_ST"), 
					rec.getGeneralDate("WORK_ED"), 
					rec.getGeneralDate("COMP_ST"), 
					rec.getGeneralDate("COMP_ED"), 
					rec.getGeneralDate("KYO_ST"), 
					rec.getGeneralDate("KYO_ED"));
		});
	}
}
