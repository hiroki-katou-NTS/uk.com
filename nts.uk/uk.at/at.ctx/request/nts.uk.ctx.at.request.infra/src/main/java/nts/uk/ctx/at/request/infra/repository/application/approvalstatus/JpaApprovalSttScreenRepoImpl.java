package nts.uk.ctx.at.request.infra.repository.application.approvalstatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.ApprovalSttScreenRepository;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.WkpEmpMail;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApprovalSttScreenRepoImpl extends JpaRepository implements ApprovalSttScreenRepository {
	
	@Override
	public List<EmpPeriod> getCountEmp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst) {
		String sql = 
				"SELECT SKBSYINIO.WORKPLACE_ID,SKBSYINIO.SID,KYO.EMP_CD,SKBSYINIO.WORK_ST,SKBSYINIO.WORK_ED,SKBSYINIO.COMP_ST,SKBSYINIO.COMP_ED,KYO.START_DATE as KYO_ST,KYO.END_DATE as KYO_ED " +
				"FROM ( " +
				"        SELECT SKBSYIN.WORKPLACE_ID,SKBSYIN.SID,SKBSYIN.START_DATE as WORK_ST,SKBSYIN.END_DATE as WORK_ED,SKR.START_DATE as COMP_ST,SKR.END_DATE as COMP_ED " +
				"        FROM ( " +
				"                SELECT SSRK.WORKPLACE_ID,SSRK.SID,SSR.START_DATE,SSR.END_DATE " +
				"                FROM         BSYMT_AFF_WORKPLACE_HIST SSR " +
				"                INNER JOIN   BSYMT_AFF_WPL_HIST_ITEM SSRK " +
				"                ON           SSR.HIST_ID = SSRK.HIST_ID " +
				"                WHERE        @startDate <= SSR.END_DATE " +
				"                AND          SSR.START_DATE <= @endDate " +
				"                AND          SSRK.WORKPLACE_ID IN @wkpIDLst " +
				"                ) SKBSYIN " +
				"        INNER JOIN       BSYMT_AFF_COM_HIST SKR " +
				"        ON               SKBSYIN.SID = SKR.SID " +
				"        INNER JOIN       BSYMT_EMP_DTA_MNG_INFO SDKJ " +
				"        ON               SDKJ.SID = SKR.SID " +
				"        WHERE            @startDate <= SKR.END_DATE " +
				"        AND              SKR.START_DATE <= @endDate " +
				"        AND SDKJ.DEL_STATUS_ATR = '0' " +
				") SKBSYINIO " +
				"LEFT JOIN  ( " +
				"        SELECT KRK.SID,KRK.EMP_CD,KR.START_DATE,KR.END_DATE " +
				"        FROM BSYMT_EMPLOYMENT_HIST KR " +
				"        INNER JOIN BSYMT_EMPLOYMENT_HIS_ITEM KRK " +
				"        ON KR.HIST_ID = KRK.HIST_ID " +
				"        WHERE KRK.EMP_CD IN @employmentCDLst " +
				"        AND @startDate <= KR.END_DATE " +
				"        AND KR.START_DATE <= @endDate " +
				") KYO " +
				"ON SKBSYINIO.SID = KYO.SID " +
				"ORDER BY SKBSYINIO.WORKPLACE_ID,KYO.EMP_CD;";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramDate("startDate", startDate)
				.paramDate("endDate", endDate)
				.paramString("wkpIDLst", wkpIDLst)
				.paramString("employmentCDLst", employmentCDLst)
				.getList(rec -> {
					return new EmpPeriod(
							rec.getString("WORKPLACE_ID"), 
							rec.getString("SID"), 
							rec.getString("EMP_CD"), 
							rec.getGeneralDate("WORK_ST"), 
							rec.getGeneralDate("WORK_ED"), 
							rec.getGeneralDate("COMP_ST"), 
							rec.getGeneralDate("COMP_ED"), 
							rec.getGeneralDate("KYO_ST"), 
							rec.getGeneralDate("KYO_ED"), 
							"", 
							"");
		});
	}

	@Override
	public Map<String, Integer> getCountUnApprApp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst) {
		Map<String, Integer> result = new HashMap<>();
		String sql = 
				"SELECT MSNSHIN.WORKPLACE_ID,COUNT(MSNSHIN.SID) AS COUNTSID " +
				"FROM ( " +
				"        SELECT DISTINCT SKBSYITERM.WORKPLACE_ID,SKBSYITERM.SID " +
				"        FROM ( " +
				"                    SELECT SKBSYINIO.WORKPLACE_ID,SKBSYINIO.SID,KYO.EMP_CD,SKBSYINIO.WORK_ST,SKBSYINIO.WORK_ED,SKBSYINIO.COMP_ST,SKBSYINIO.COMP_ED,KYO.START_DATE as KYO_ST,KYO.END_DATE as KYO_ED " +
				"                    FROM ( " +
				"                            SELECT SKBSYIN.WORKPLACE_ID,SKBSYIN.SID,SKBSYIN.START_DATE as WORK_ST,SKBSYIN.END_DATE as WORK_ED,SKR.START_DATE as COMP_ST,SKR.END_DATE as COMP_ED " +
				"                            FROM ( " +
				"                                    SELECT SSRK.WORKPLACE_ID,SSRK.SID,SSR.START_DATE,SSR.END_DATE " +
				"                                    FROM         BSYMT_AFF_WORKPLACE_HIST SSR " +
				"                                    INNER JOIN   BSYMT_AFF_WPL_HIST_ITEM SSRK " +
				"                                    ON           SSR.HIST_ID = SSRK.HIST_ID " +
				"                                    WHERE        @startDate <= SSR.END_DATE " +
				"                                    AND          SSR.START_DATE <= @endDate " +
				"                                            AND          SSRK.WORKPLACE_ID IN @wkpIDLst " +
				"                                    ) SKBSYIN " +
				"                            INNER JOIN       BSYMT_AFF_COM_HIST SKR " +
				"                            ON               SKBSYIN.SID = SKR.SID " +
				"                            INNER JOIN       BSYMT_EMP_DTA_MNG_INFO SDKJ " +
				"                            ON               SDKJ.SID = SKR.SID " +
				"                            WHERE            @startDate <= SKR.END_DATE " +
				"                            AND              SKR.START_DATE <= @endDate " +
				"                            AND SDKJ.DEL_STATUS_ATR = '0' " +
				"                    ) SKBSYINIO " +
				"                    LEFT JOIN  ( " +
				"                            SELECT KRK.SID,KRK.EMP_CD,KR.START_DATE,KR.END_DATE " +
				"                            FROM BSYMT_EMPLOYMENT_HIST KR " +
				"                            INNER JOIN BSYMT_EMPLOYMENT_HIS_ITEM KRK " +
				"                            ON KR.HIST_ID = KRK.HIST_ID " +
				"                            WHERE KRK.EMP_CD IN @employmentCDLst " +
				"                            AND @startDate <= KR.END_DATE " +
				"                            AND KR.START_DATE <= @endDate " +
				"                    ) KYO " +
				"                    ON SKBSYINIO.SID = KYO.SID " +
				"                   ) SKBSYITERM " +
				"        INNER JOIN  ( " +
				"                               SELECT             SRI.EMPLOYEE_ID as APP_SID,SRI.APPROVAL_RECORD_DATE as APP_DATE,SRI.ROOT_STATE_ID " +
				"                               FROM               WWFDT_APPROVAL_ROOT_STATE SRI " +
				"                               INNER JOIN         WWFDT_APPROVAL_PHASE_ST SFI " +
				"                               ON                 SRI.ROOT_STATE_ID = SFI.ROOT_STATE_ID " +
				"                               WHERE              SFI.APP_PHASE_ATR IN ('0','3','4') " +
				"                               AND                @startDate <= SRI.APPROVAL_RECORD_DATE " +
				"                               AND                SRI.APPROVAL_RECORD_DATE <= @endDate " +
				"                               ) MSNSNS " +
				"        ON              SKBSYITERM.SID = MSNSNS.APP_SID " +
				"        WHERE SKBSYITERM.WORK_ST <= MSNSNS.APP_DATE " +
				"        AND SKBSYITERM.COMP_ST <= MSNSNS.APP_DATE " +
				"        AND SKBSYITERM.KYO_ST <= MSNSNS.APP_DATE " +
				"        AND MSNSNS.APP_DATE <= SKBSYITERM.WORK_ED " +
				"        AND MSNSNS.APP_DATE <= SKBSYITERM.COMP_ED " +
				"        AND MSNSNS.APP_DATE <= SKBSYITERM.KYO_ED " +
				") MSNSHIN " +
				"GROUP BY MSNSHIN.WORKPLACE_ID;";
		List<Pair<String, Integer>> listPair = new NtsStatement(sql, this.jdbcProxy())
				.paramDate("startDate", startDate)
				.paramDate("endDate", endDate)
				.paramString("wkpIDLst", wkpIDLst)
				.paramString("employmentCDLst", employmentCDLst)
				.getList(rec -> {
					String wkpID = rec.getString("WORKPLACE_ID");
					Integer count = rec.getInt("COUNTSID");
					return Pair.of(wkpID, count);
				});
		for(Pair<String, Integer> pair : listPair) {
			result.put(pair.getLeft(), pair.getRight());
		}
		return result;
	}

	@Override
	public Map<String, Integer> getCountDayResult(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst,
			List<String> employmentCDLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getCountMonthResult(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst,
			List<String> employmentCDLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getCountWorkConfirm(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst,
			List<String> employmentCDLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpPeriod> getMailCountUnApprApp(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst, List<String> employmentCDLst) {
		String sql = 
				"SELECT SKBSYINIO.WORKPLACE_ID,SKBSYINIO.SID,SKBSYINIO.COMP_ST,SKBSYINIO.COMP_ED,KYO.START_DATE as KYO_ST,KYO.END_DATE as KYO_ED " +
				"FROM ( " +
				"        SELECT SKBSYIN.WORKPLACE_ID,SKBSYIN.SID,SKBSYIN.START_DATE as WORK_ST,SKBSYIN.END_DATE as WORK_ED,SKR.START_DATE as COMP_ST,SKR.END_DATE as COMP_ED " +
				"        FROM ( " +
				"                        SELECT SSRK.WORKPLACE_ID,SSRK.SID,SSR.START_DATE,SSR.END_DATE " +
				"                        FROM         BSYMT_AFF_WORKPLACE_HIST SSR " +
				"                        INNER JOIN   BSYMT_AFF_WPL_HIST_ITEM SSRK " +
				"                        ON           SSR.HIST_ID = SSRK.HIST_ID " +
				"                        WHERE        @startDate <= SSR.END_DATE " +
				"                        AND          SSR.START_DATE <= @endDate " +
				"                      　      　　　　　　	 AND          SSRK.WORKPLACE_ID IN @wkpIDLst " +    
				"                    ) SKBSYIN " +
				"        INNER JOIN       BSYMT_AFF_COM_HIST SKR " +
				"        ON               SKBSYIN.SID = SKR.SID " +
				"        INNER JOIN       BSYMT_EMP_DTA_MNG_INFO SDKJ " +
				"        ON               SDKJ.SID = SKR.SID " +
				"        WHERE            @startDate <= SKR.END_DATE " +
				"        AND              SKR.START_DATE <= @endDate " +
				"        AND SDKJ.DEL_STATUS_ATR = '0' " +
				") SKBSYINIO " +
				"LEFT JOIN  ( " +
				"        SELECT KRK.SID,KRK.EMP_CD,KR.START_DATE,KR.END_DATE " +
				"        FROM BSYMT_EMPLOYMENT_HIST KR " +
				"        INNER JOIN BSYMT_EMPLOYMENT_HIS_ITEM KRK " +
				"        ON KR.HIST_ID = KRK.HIST_ID " +
				"        WHERE KRK.EMP_CD IN @employmentCDLst " +
				"        AND @startDate <= KR.END_DATE " +
				"        AND KR.START_DATE <= @endDate " +
				") KYO " +
				"ON SKBSYINIO.SID = KYO.SID;";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramDate("startDate", startDate)
				.paramDate("endDate", endDate)
				.paramString("wkpIDLst", wkpIDLst)
				.paramString("employmentCDLst", employmentCDLst)
				.getList(rec -> {
					return new EmpPeriod(
							rec.getString("WORKPLACE_ID"), 
							rec.getString("SID"), 
							"", 
							null, 
							null, 
							rec.getGeneralDate("COMP_ST"), 
							rec.getGeneralDate("COMP_ED"), 
							rec.getGeneralDate("KYO_ST"), 
							rec.getGeneralDate("KYO_ED"), 
							"", 
							"");
		});
	}

	@Override
	public List<WkpEmpMail> getMailCountUnConfirmDay(GeneralDate startDate, GeneralDate endDate,
			List<String> wkpIDLst, List<String> employmentCDLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WkpEmpMail> getMailCountUnApprDay(GeneralDate startDate, GeneralDate endDate, List<String> wkpIDLst,
			List<String> employmentCDLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WkpEmpMail> getMailCountUnConfirmMonth(GeneralDate startDate, GeneralDate endDate,
			List<String> wkpIDLst, List<String> employmentCDLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WkpEmpMail> getMailCountUnApprMonth(GeneralDate startDate, GeneralDate endDate,
			List<String> wkpIDLst, List<String> employmentCDLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WkpEmpMail> getMailCountWorkConfirm(GeneralDate startDate, GeneralDate endDate,
			List<String> wkpIDLst, List<String> employmentCDLst) {
		// TODO Auto-generated method stub
		return null;
	}
}
