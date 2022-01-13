package nts.uk.file.at.infra.setworkinghoursanddays;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.Query;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.database.DatabaseProduct;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshmtLegalTimeMWkp;
import nts.uk.ctx.at.shared.infra.entity.workrule.week.KsrmtWeekRuleMng;
import nts.uk.file.at.app.export.setworkinghoursanddays.GetKMK004WorkPlaceExportRepository;
import nts.uk.file.at.app.export.setworkinghoursanddays.WorkPlaceColumn;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

/**
 * 
 * @author sonnlb
 *
 */
@Stateless
public class JpaGetKMK004WorkPlaceExportData extends JpaRepository implements GetKMK004WorkPlaceExportRepository {
	
	private static final String GET_EXPORT_MONTH = "SELECT m.MONTH_STR FROM BCMMT_COMPANY m WHERE m.CID = ?cid";
	
	private static final String LEGAL_TIME_WKP = "SELECT s FROM KshmtLegalTimeMWkp s WHERE "
			+ " s.pk.cid = :cid AND s.pk.ym >= :minYm AND s.pk.ym < :maxYm"
			+ " ORDER BY s.pk.ym";
	
	private static final String GET_WORKPLACE_SQLSERVER;
	  static {
	      StringBuilder exportSQL = new StringBuilder();
	     exportSQL.append("  SELECT * FROM ( ");
	     exportSQL.append("             SELECT IIF(BSYMT_WKP_INFO.HIERARCHY_CD IS NOT NULL, BSYMT_WKP_INFO.HIERARCHY_CD, '999999999999999999999999999999') AS HIERARCHY_CD,  ");
	     exportSQL.append("             ROW_NUMBER() OVER(PARTITION BY BSYMT_WKP_INFO.WKP_CD ORDER BY IIF(BSYMT_WKP_INFO.HIERARCHY_CD IS NOT NULL,0,1),BSYMT_WKP_INFO.HIERARCHY_CD ASC) AS rk2,  ");
	     exportSQL.append("             BSYMT_WKP_INFO.WKP_ID , BSYMT_WKP_INFO.WKP_CD AS WKPCD,   ");
	     exportSQL.append("             BSYMT_WKP_INFO.WKP_NAME AS WKP_NAME,   ");
	     exportSQL.append("             KSHMT_LEGALTIME_D_REG_WKP .DAILY_TIME,   ");
	     exportSQL.append("             KSHMT_LEGALTIME_D_REG_WKP.WEEKLY_TIME,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_AGGR,   ");
	     exportSQL.append("             IIF (KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_AGGR != 0, KRCMT_CALC_M_SET_REG_WKP.INCLUDE_LEGAL_AGGR, NULL) AS INCLUDE_LEGAL_AGGR,   ");
	     exportSQL.append("             IIF (KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_AGGR != 0, KRCMT_CALC_M_SET_REG_WKP.INCLUDE_HOLIDAY_AGGR, NULL) AS INCLUDE_HOLIDAY_AGGR,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_OT,   ");
	     exportSQL.append("             IIF (KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_OT != 0, KRCMT_CALC_M_SET_REG_WKP.INCLUDE_LEGAL_OT, NULL) AS INCLUDE_LEGAL_OT,   ");
	     exportSQL.append("             IIF (KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_OT != 0, KRCMT_CALC_M_SET_REG_WKP.INCLUDE_HOLIDAY_OT, NULL) AS INCLUDE_HOLIDAY_OT,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_COM.WITHIN_TIME_USE,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.AGGR_METHOD,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.SETTLE_PERIOD_MON,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.SETTLE_PERIOD,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.START_MONTH AS FLEX_START_MONTH,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.INCLUDE_HDWK,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.LEGAL_AGGR_SET,   ");
	     exportSQL.append("             IIF(KRCMT_CALC_M_SET_FLE_WKP.AGGR_METHOD = 0, KRCMT_CALC_M_SET_FLE_WKP.INCLUDE_OT, NULL) AS INCLUDE_OT,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.INSUFFIC_SET,   ");
	     exportSQL.append("             KSHMT_LEGALTIME_D_DEF_WKP.DAILY_TIME AS LAB_DAILY_TIME,   ");
	     exportSQL.append("             KSHMT_LEGALTIME_D_DEF_WKP.WEEKLY_TIME AS LAB_WEEKLY_TIME,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.STR_MONTH,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.PERIOD,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.REPEAT_ATR,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR,   ");
	     exportSQL.append("             IIF(KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_AGGR != 0, KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_LEGAL_AGGR, NULL) AS DEFOR_INCLUDE_LEGAL_AGGR,   ");
	     exportSQL.append("             IIF(KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_AGGR != 0, KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_HOLIDAY_AGGR, NULL) AS DEFOR_INCLUDE_HOLIDAY_AGGR,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT,   ");
	     exportSQL.append("             IIF(KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_OT != 0, KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_LEGAL_OT, NULL) AS DEFOR_INCLUDE_LEGAL_OT,   ");
	     exportSQL.append("             IIF(KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_OT != 0, KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_HOLIDAY_OT, NULL) AS DEFOR_INCLUDE_HOLIDAY_OT   ");
	     exportSQL.append("             FROM BSYMT_WKP_INFO   ");
	     exportSQL.append("              LEFT JOIN KSHMT_LEGALTIME_D_REG_WKP ON BSYMT_WKP_INFO.CID = KSHMT_LEGALTIME_D_REG_WKP.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_ID = KSHMT_LEGALTIME_D_REG_WKP.WKP_ID    ");
	     exportSQL.append("              LEFT JOIN KRCMT_CALC_M_SET_DEF_WKP ON BSYMT_WKP_INFO.CID = KRCMT_CALC_M_SET_DEF_WKP.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_ID = KRCMT_CALC_M_SET_DEF_WKP.WKP_ID   ");
	     exportSQL.append("              LEFT JOIN KRCMT_CALC_M_SET_FLE_WKP ON BSYMT_WKP_INFO.CID = KRCMT_CALC_M_SET_FLE_WKP.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_ID = KRCMT_CALC_M_SET_FLE_WKP.WKP_ID   ");
	     exportSQL.append("              LEFT JOIN KRCMT_CALC_M_SET_REG_WKP ON BSYMT_WKP_INFO.CID = KRCMT_CALC_M_SET_REG_WKP.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_ID = KRCMT_CALC_M_SET_REG_WKP.WKPID   ");
	     exportSQL.append("              LEFT JOIN (SELECT *, ROW_NUMBER() OVER ( PARTITION BY CID ORDER BY END_DATE DESC ) AS RN FROM BSYMT_WKP_CONFIG_2) AS BSYMT_WKP_CONFIG  ");
	     exportSQL.append("             ON BSYMT_WKP_INFO.CID = BSYMT_WKP_CONFIG.CID AND BSYMT_WKP_CONFIG.RN = 1  ");
	     exportSQL.append("              LEFT JOIN KRCMT_CALC_M_SET_FLE_COM ON BSYMT_WKP_INFO.CID = KRCMT_CALC_M_SET_FLE_COM.CID   ");
	     exportSQL.append("              LEFT JOIN KSHMT_LEGALTIME_D_DEF_WKP ON BSYMT_WKP_INFO.CID = KSHMT_LEGALTIME_D_DEF_WKP.CID   ");
	     exportSQL.append("              AND BSYMT_WKP_INFO.WKP_ID = KSHMT_LEGALTIME_D_DEF_WKP.WKP_ID   ");
	     exportSQL.append("              INNER JOIN BSYMT_WKP_CONFIG_2 ON BSYMT_WKP_INFO.CID = BSYMT_WKP_CONFIG_2.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_HIST_ID = BSYMT_WKP_CONFIG_2.WKP_HIST_ID    ");
	     exportSQL.append("             WHERE BSYMT_WKP_INFO.CID = ?  ");
	     exportSQL.append("             AND BSYMT_WKP_CONFIG_2.START_DATE <= ?  ");
	     exportSQL.append("             AND BSYMT_WKP_CONFIG_2.END_DATE >= ?  ");
	     exportSQL.append("             AND BSYMT_WKP_INFO.DELETE_FLAG = 0  ");
	     
	     exportSQL.append("            ) TBL  ");
	     exportSQL.append("             ORDER BY TBL.HIERARCHY_CD ASC");

	     GET_WORKPLACE_SQLSERVER = exportSQL.toString();
	  }
	  
	  private static final String GET_WORKPLACE_POSTGRE;
	  static {
	      StringBuilder exportSQL = new StringBuilder();
	     exportSQL.append("  SELECT * FROM ( ");
	     exportSQL.append("             SELECT (CASE WHEN BSYMT_WKP_INFO.HIERARCHY_CD IS NOT NULL THEN BSYMT_WKP_INFO.HIERARCHY_CD ELSE '999999999999999999999999999999' END) AS HIERARCHY_CD,  ");
	     exportSQL.append("             ROW_NUMBER() OVER(PARTITION BY BSYMT_WKP_INFO.WKP_CD ORDER BY (CASE WHEN BSYMT_WKP_INFO.HIERARCHY_CD IS NOT NULL THEN 0 ELSE 1 END),BSYMT_WKP_INFO.HIERARCHY_CD ASC) AS rk2,  ");
	     exportSQL.append("             BSYMT_WKP_INFO.WKP_ID , BSYMT_WKP_INFO.WKP_CD AS WKPCD,   ");
	     exportSQL.append("             BSYMT_WKP_INFO.WKP_NAME AS WKP_NAME,   ");
	     exportSQL.append("             KSHMT_LEGALTIME_D_REG_WKP .DAILY_TIME,   ");
	     exportSQL.append("             KSHMT_LEGALTIME_D_REG_WKP.WEEKLY_TIME,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_AGGR,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_AGGR != '0' THEN KRCMT_CALC_M_SET_REG_WKP.INCLUDE_LEGAL_AGGR ELSE NULL END) AS INCLUDE_LEGAL_AGGR,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_AGGR != '0' THEN KRCMT_CALC_M_SET_REG_WKP.INCLUDE_HOLIDAY_AGGR ELSE NULL END) AS INCLUDE_HOLIDAY_AGGR,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_OT,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_OT != '0' THEN KRCMT_CALC_M_SET_REG_WKP.INCLUDE_LEGAL_OT ELSE NULL END) AS INCLUDE_LEGAL_OT,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_REG_WKP.INCLUDE_EXTRA_OT != '0' THEN KRCMT_CALC_M_SET_REG_WKP.INCLUDE_HOLIDAY_OT ELSE NULL END) AS INCLUDE_HOLIDAY_OT,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_COM.WITHIN_TIME_USE,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.AGGR_METHOD,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.SETTLE_PERIOD_MON,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.SETTLE_PERIOD,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.START_MONTH AS FLEX_START_MONTH,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.INCLUDE_HDWK,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.LEGAL_AGGR_SET,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_FLE_WKP.AGGR_METHOD = '0' THEN KRCMT_CALC_M_SET_FLE_WKP.INCLUDE_OT ELSE NULL END) AS INCLUDE_OT,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_FLE_WKP.INSUFFIC_SET,   ");
	     exportSQL.append("             KSHMT_LEGALTIME_D_DEF_WKP.DAILY_TIME AS LAB_DAILY_TIME,   ");
	     exportSQL.append("             KSHMT_LEGALTIME_D_DEF_WKP.WEEKLY_TIME AS LAB_WEEKLY_TIME,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.STR_MONTH,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.PERIOD,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.REPEAT_ATR,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_AGGR != '0' THEN KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_LEGAL_AGGR ELSE NULL END) AS DEFOR_INCLUDE_LEGAL_AGGR,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_AGGR != '0' THEN KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_HOLIDAY_AGGR ELSE NULL END) AS DEFOR_INCLUDE_HOLIDAY_AGGR,   ");
	     exportSQL.append("             KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_OT != '0' THEN KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_LEGAL_OT ELSE NULL END) AS DEFOR_INCLUDE_LEGAL_OT,   ");
	     exportSQL.append("             (CASE WHEN KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_EXTRA_OT != '0' THEN KRCMT_CALC_M_SET_DEF_WKP.INCLUDE_HOLIDAY_OT ELSE NULL END) AS DEFOR_INCLUDE_HOLIDAY_OT   ");
	     exportSQL.append("             FROM BSYMT_WKP_INFO   ");
	     exportSQL.append("              LEFT JOIN KSHMT_LEGALTIME_D_REG_WKP ON BSYMT_WKP_INFO.CID = KSHMT_LEGALTIME_D_REG_WKP.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_ID = KSHMT_LEGALTIME_D_REG_WKP.WKP_ID    ");
	     exportSQL.append("              LEFT JOIN KRCMT_CALC_M_SET_DEF_WKP ON BSYMT_WKP_INFO.CID = KRCMT_CALC_M_SET_DEF_WKP.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_ID = KRCMT_CALC_M_SET_DEF_WKP.WKP_ID   ");
	     exportSQL.append("              LEFT JOIN KRCMT_CALC_M_SET_FLE_WKP ON BSYMT_WKP_INFO.CID = KRCMT_CALC_M_SET_FLE_WKP.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_ID = KRCMT_CALC_M_SET_FLE_WKP.WKP_ID   ");
	     exportSQL.append("              LEFT JOIN KRCMT_CALC_M_SET_REG_WKP ON BSYMT_WKP_INFO.CID = KRCMT_CALC_M_SET_REG_WKP.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_ID = KRCMT_CALC_M_SET_REG_WKP.WKPID   ");
	     exportSQL.append("              LEFT JOIN (SELECT *, ROW_NUMBER() OVER ( PARTITION BY CID ORDER BY END_DATE DESC ) AS RN FROM BSYMT_WKP_CONFIG_2) AS BSYMT_WKP_CONFIG  ");
	     exportSQL.append("             ON BSYMT_WKP_INFO.CID = BSYMT_WKP_CONFIG.CID AND BSYMT_WKP_CONFIG.RN = 1  ");
	     exportSQL.append("              LEFT JOIN KRCMT_CALC_M_SET_FLE_COM ON BSYMT_WKP_INFO.CID = KRCMT_CALC_M_SET_FLE_COM.CID   ");
	     exportSQL.append("              LEFT JOIN KSHMT_LEGALTIME_D_DEF_WKP ON BSYMT_WKP_INFO.CID = KSHMT_LEGALTIME_D_DEF_WKP.CID   ");
	     exportSQL.append("              AND BSYMT_WKP_INFO.WKP_ID = KSHMT_LEGALTIME_D_DEF_WKP.WKP_ID   ");
	     exportSQL.append("              INNER JOIN BSYMT_WKP_CONFIG_2 ON BSYMT_WKP_INFO.CID = BSYMT_WKP_CONFIG_2.CID   ");
	     exportSQL.append("               AND BSYMT_WKP_INFO.WKP_HIST_ID = BSYMT_WKP_CONFIG_2.WKP_HIST_ID    ");
	     exportSQL.append("             WHERE BSYMT_WKP_INFO.CID = ?  ");
	     exportSQL.append("             AND BSYMT_WKP_CONFIG_2.START_DATE <= ? ::DATE  ");
	     exportSQL.append("             AND BSYMT_WKP_CONFIG_2.END_DATE >= ? ::DATE  ");
	     exportSQL.append("             AND BSYMT_WKP_INFO.DELETE_FLAG = '0'  ");
	     
	     exportSQL.append("            ) TBL  ");
	     exportSQL.append("             ORDER BY TBL.HIERARCHY_CD ASC");

	     GET_WORKPLACE_POSTGRE = exportSQL.toString();
	  }

	  private String getStartOfWeek(String cid) {
			
			Optional<WeekRuleManagement> startOfWeek = this.queryProxy().find(cid, KsrmtWeekRuleMng.class)
																		.map(w -> w.toDomain());
			
			return KMK004PrintCommon.getWeekStart(startOfWeek);
		}
		
		private int month(){
			String cid = AppContexts.user().companyId();
			int month = 1;
			Query monthQuery = this.getEntityManager().createNativeQuery(GET_EXPORT_MONTH.toString()).setParameter("cid", cid);
			List<?> data = monthQuery.getResultList();
			if (data.size() == 0) {
				month = 1;
			} else {
				month = Integer.valueOf(data.get(0).toString());
			}
			return month;
		}
		
		@Override
		public List<MasterData> getWorkPlaceExportData(int startDate, int endDate) {
			String cid = AppContexts.user().companyId();
			List<MasterData> datas = new ArrayList<>();
			
			String startOfWeek = getStartOfWeek(cid);
			
			int month = this.month();

			val legalTimes = this.queryProxy().query(LEGAL_TIME_WKP, KshmtLegalTimeMWkp.class)
				.setParameter("cid", cid)
				.setParameter("minYm", startDate * 100 + month)
				.setParameter("maxYm", endDate * 100 + month)
				.getList();
			
			if (this.database().is(DatabaseProduct.MSSQLSERVER)) {
				try (PreparedStatement stmt = this.connection().prepareStatement(GET_WORKPLACE_SQLSERVER.toString())) {
					String systemDate = GeneralDate.ymd(startDate, GeneralDate.today().month(), GeneralDate.today().day()).toString("yyyy/MM/dd");
					
					stmt.setString(1, cid);
					stmt.setString(2, systemDate);
					stmt.setString(3, systemDate);
					NtsResultSet result = new NtsResultSet(stmt.executeQuery());
					
					result.forEach(i -> {
						datas.addAll(buildWorkPlaceRow(i, legalTimes, startDate, endDate, month, startOfWeek));
						
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (this.database().is(DatabaseProduct.POSTGRESQL)) {
				try (PreparedStatement stmt = this.connection().prepareStatement(GET_WORKPLACE_POSTGRE.toString())) {
					String systemDate = GeneralDate.ymd(startDate, GeneralDate.today().month(), GeneralDate.today().day()).toString("yyyy/MM/dd");
					
					stmt.setString(1, cid);
					stmt.setString(2, systemDate);
					stmt.setString(3, systemDate);
					NtsResultSet result = new NtsResultSet(stmt.executeQuery());
					
					result.forEach(i -> {
						datas.addAll(buildWorkPlaceRow(i, legalTimes, startDate, endDate, month, startOfWeek));
						
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				throw new RuntimeException("not supported");
			}
			
			return datas;
		}

		private List<MasterData> buildWorkPlaceRow(NtsResultRecord r, List<KshmtLegalTimeMWkp> legals, int startDate, int endDate, int month, String startOfWeek) {
			List<MasterData> datas = new ArrayList<>();

			Integer refPreTime = convertToInteger(r, "WITHIN_TIME_USE");
			String kdp004_401 = I18NText.getText("KMK004_401");
			
			for (int y = startDate; y <= endDate; y++) {
				String wid = r.getString("WKP_ID");
				int ym = y *100 + month;
				
				val list = legals.stream()
						.filter(l -> l.pk.ym == ym && l.pk.wkpId.equals(wid)).collect(Collectors.toList());
				
				val normal = list.stream()
						.filter(l -> l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
						.findFirst();
				val defor = list.stream()
						.filter(l -> l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
						.findFirst();
				val flex = list.stream()
						.filter(l -> l.pk.type == LaborWorkTypeAttr.FLEX.value)
						.findFirst();
				
				Integer includeExtraAggr = convertToInteger(r, "INCLUDE_EXTRA_AGGR");
				Integer includeExtraOt = convertToInteger(r, "INCLUDE_EXTRA_OT");
				Integer selectPeriodMon = r.getInt("SETTLE_PERIOD_MON");
				Integer aggrMethod = convertToInteger(r, "AGGR_METHOD");
				Integer strMonth = r.getInt("STR_MONTH");
				Integer flexStartMonth = r.getInt("FLEX_START_MONTH");
				Integer period = r.getInt("PERIOD");
				Integer repeatAtr = convertToInteger(r, "REPEAT_ATR");
				Integer deforIncludeExtraAggr = convertToInteger(r, "DEFOR_INCLUDE_EXTRA_AGGR");
				Integer deforIncludeExtraOt = convertToInteger(r, "DEFOR_INCLUDE_EXTRA_OT");
				
				datas.add(buildWorkPlaceARow(
						//R12_1
						r.getInt("rk2") == 1 ? r.getString("WKPCD") : null,
						//R12_2
						r.getInt("rk2") == 1 ? r.getString("WKP_NAME") : null,
						//R12_3
						String.valueOf(y),
						//R12_4
						((month - 1) % 12 + 1) + kdp004_401, 
						//R12_5
						KMK004PrintCommon.convertTime(normal.isPresent() ? normal.get().legalTime : null),
						//R12_6
						KMK004PrintCommon.convertTime(r.getInt(("DAILY_TIME"))),
						//R12_7
						KMK004PrintCommon.convertTime(r.getInt(("WEEKLY_TIME"))),
						//R12_8
						KMK004PrintCommon.getExtraType(includeExtraAggr),
						//R12_9
						includeExtraAggr == null ? null : includeExtraAggr != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "INCLUDE_LEGAL_AGGR")) : null,
						//R12_10
						includeExtraAggr == null ?null : includeExtraAggr != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "INCLUDE_HOLIDAY_AGGR")) : null, 
						//R12_11
						KMK004PrintCommon.getExtraType(includeExtraOt),
						//R12_12
						includeExtraOt == null ? null : includeExtraOt != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "INCLUDE_LEGAL_OT")) : null, 
						//R12_13		
						convertToInteger(r, "INCLUDE_EXTRA_OT") == null ?null:convertToInteger(r, "INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "INCLUDE_HOLIDAY_OT")) : null,
						//R12_14
						KMK004PrintCommon.getFlexType(refPreTime),
						//R12_15
						((month - 1) % 12 + 1) + kdp004_401,
						//R12_16 
						KMK004PrintCommon.convertTime(refPreTime == 0? null: flex.isPresent() ? flex.get().withinTime : null),
						//R12_17
						KMK004PrintCommon.convertTime(flex.isPresent() ? flex.get().legalTime : null),
						//R10_18
						KMK004PrintCommon.convertTime(flex.isPresent() ? flex.get().weekAvgTime : null),
						//R12_19
						KMK004PrintCommon.getSettle(r.getInt("SETTLE_PERIOD")),
						//R12_20
						flexStartMonth == null ? null: flexStartMonth.toString() + "月",
						//R12_21
						selectPeriodMon == null ? null : selectPeriodMon == 2 ? "2ヶ月" : "3ヶ月",
						//R12_22
						KMK004PrintCommon.getShortageTime(r.getInt("INSUFFIC_SET")), 
						//R12_23
						KMK004PrintCommon.getAggType(aggrMethod),
						//R12_24
						aggrMethod == null ?null: aggrMethod == 0 ? KMK004PrintCommon.getInclude(convertToInteger(r, "INCLUDE_OT")) : null,
						//R12_25
						KMK004PrintCommon.getInclude(convertToInteger(r, "INCLUDE_HDWK")),
						//R12_26
						KMK004PrintCommon.getLegal(convertToInteger(r, "LEGAL_AGGR_SET")),
						//R12_27
						((month - 1) % 12 + 1) + kdp004_401,
						//R12_28
						KMK004PrintCommon.convertTime(defor.isPresent() ? defor.get().legalTime : null),
						//R12_29
						KMK004PrintCommon.convertTime(r.getInt(("LAB_DAILY_TIME"))), 
						//R12_30
						KMK004PrintCommon.convertTime(r.getInt(("LAB_WEEKLY_TIME"))),
						//R12_31
						strMonth == null ? null : strMonth + I18NText.getText("KMK004_402"),
						//R12_32
						period == null ? null : period + I18NText.getText("KMK004_403"),
						//R12_33
						repeatAtr == null ? null : repeatAtr == 1 ? "○" : "-",
						//R12_34
						KMK004PrintCommon.getWeeklySurcharge(deforIncludeExtraAggr),
						//R12_35
						deforIncludeExtraAggr == null ? null : deforIncludeExtraAggr != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "DEFOR_INCLUDE_LEGAL_AGGR")) : null,
						//R12_36
						deforIncludeExtraAggr == null ? null : deforIncludeExtraAggr != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "DEFOR_INCLUDE_HOLIDAY_AGGR")) : null,
						//R12_37
						KMK004PrintCommon.getWeeklySurcharge(deforIncludeExtraOt),
						//R12_38
						deforIncludeExtraOt == null ? null : deforIncludeExtraOt != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "DEFOR_INCLUDE_LEGAL_OT")) : null,
						//E12_39
						deforIncludeExtraOt == null ? null : deforIncludeExtraOt != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "DEFOR_INCLUDE_HOLIDAY_OT")): null
						));

//				int nextYm = y *100 + month + 1;
//				val normalN = legals.stream()
//						.filter(l -> l.pk.ym == nextYm && l.pk.wkpId.equals(wid) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
//						.findFirst();
//				val deforN = legals.stream()
//						.filter(l -> l.pk.ym == nextYm && l.pk.wkpId.equals(wid) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
//						.findFirst();
//				val flexN = legals.stream()
//						.filter(l -> l.pk.ym == nextYm && l.pk.wkpId.equals(wid) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
//						.findFirst();
//				// buil arow = month + 1
//				datas.add(buildWorkPlaceARow(//R14_1
//						 null,
//						//R14_2
//						null,
//						//R14_3
//						null,
//						//R14_4
//						((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"), 
//						//R14_5
//						KMK004PrintCommon.convertTime(normalN.isPresent() ? normalN.get().legalTime : null),
//						//R14_6
//						null,
//						//R14_7
//						null,
//						//R14_8
//						null,
//						//R14_9
//						null, 
//						//R14_10
//						null, 
//						//R14_11
//						null,
//						//R14_12
//						null, 
//						//R14_13		
//						null,
//						//R14_14
//						null,
//						//R14_15
//						((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
//						//R14_16 
//						KMK004PrintCommon.convertTime(refPreTime == 0? null :flexN.isPresent() ? flexN.get().withinTime : null),
//						//R14_17
//						KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().legalTime : null),
//						//R10_18
//						KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().weekAvgTime : null),
//						//R14_19
//						null,
//						//R14_20
//						null,
//						//R14_21
//						null,
//						//R14_22
//						null, 
//						//R14_23
//						null,
//						//R14_24
//						null,
//						//R14_25
//						null,
//						//R14_26
//						null,
//						//R14_27
//						((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
//						//R14_28
//						KMK004PrintCommon.convertTime(deforN.isPresent() ? deforN.get().legalTime : null),
//						//R14_29
//						null, 
//						//R14_30
//						null,
//						//R14_31
//						null,
//						//R14_32
//						null,
//						//R14_33
//						null,
//						//R14_34
//						null,
//						//R14_35
//						null,
//						//R14_36
//						null,
//						//R14_37
//						null,
//						//R14_38
//						null,
//						//R14_39
//						null
//						));
				
				// buil month remain
				for (int i = 1; i < 11; i++) {
					int nm = month + i;
					int m = nm > 12 ? nm % 12 : nm;
					int currentYm = (y + nm> 12? nm / 12 : 0) * 100 + m;
					
					val listC = legals.stream()
							.filter(l -> l.pk.ym == currentYm && l.pk.wkpId.equals(wid)).collect(Collectors.toList());
					
					val normalC = listC.stream()
							.filter(l -> l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
							.findFirst();
					val deforC = listC.stream()
							.filter(l -> l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
							.findFirst();
					val flexC = listC.stream()
							.filter(l -> l.pk.type == LaborWorkTypeAttr.FLEX.value)
							.findFirst();
					datas.add(buildWorkPlaceARow(
							//R12_1
						 null,
						//R12_2
						null,
						//R12_3
						null,
						//R12_4
						(m) + kdp004_401, 
						//R12_5
						KMK004PrintCommon.convertTime(normalC.isPresent() ? normalC.get().legalTime : null),
						//R12_6
						null,
						//R12_7
						null,
						//R12_8
						null,
						//R12_9
						null, 
						//R12_10
						null, 
						//R12_11
						null,
						//R12_12
						null, 
						//R12_13		
						null,
						//R12_14
						null,
						//R12_15
						(m) + kdp004_401,
						//R12_16 
						KMK004PrintCommon.convertTime(refPreTime == 0? null: flexC.isPresent() ? flexC.get().withinTime : null),
						//R12_17
						KMK004PrintCommon.convertTime(flexC.isPresent() ? flexC.get().legalTime : null),
						//R10_18
						KMK004PrintCommon.convertTime(flexC.isPresent() ? flexC.get().weekAvgTime : null),
						//R12_19
						null,
						//R12_20
						null,
						//R12_21
						null,
						//R12_22
						null, 
						//R12_23
						null,
						//R12_24
						null,
						//R12_25
						null,
						//R12_26
						null,
						//R12_27
						(m) + kdp004_401,
						//R12_28
						KMK004PrintCommon.convertTime(deforC.isPresent() ? deforC.get().legalTime : null),
						//R12_29
						null, 
						//R12_30
						null,
						//R12_31
						null,
						//R12_32
						null,
						//R12_33
						null,
						//R12_34
						null,
						//R12_35
						null,
						//R12_36
						null,
						//R12_37
						null,
						//R12_38
						null,
						//R12_39
						null
							));
				}
			}
			return datas;
		}
		
		public static MasterData buildWorkPlaceARow(
				String R14_1, String R14_2, String R14_3, String R14_4, String R14_5,
				String R14_6, String R14_7, String R14_8, String R14_9, String R14_10,
				String R14_11, String R14_12, String R14_13, String R14_14, String R14_15,
				String R14_16, String R14_17, String R14_18, String R14_19, String R14_20,
				String R14_21, String R14_22, String R14_23, String R14_24, String R14_25,
				String R14_26, String R14_27, String R14_28, String R14_29, String R14_30,
				String R14_31, String R14_32, String R14_33, String R14_34, String R14_35, 
				String R14_36, String R14_37,String R14_38, String R14_39) {
			
			Map<String, MasterCellData> data = new HashMap<>();
			data.put(WorkPlaceColumn.KMK004_187, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_187)
	            .value(R14_1)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	       data.put(WorkPlaceColumn.KMK004_188, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_188)
	            .value(R14_2)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
			data.put(WorkPlaceColumn.KMK004_372, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_372)
	            .value(R14_3)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_373, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_373)
	            .value(R14_4)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_374, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_374)
	            .value(R14_5)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_375, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_375)
	            .value(R14_6)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_376, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_376)
	            .value(R14_7)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_377, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_377)
	            .value(R14_8)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_378, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_378)
	            .value(R14_9)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_379, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_379)
	            .value(R14_10)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_380, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_380)
	            .value(R14_11)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_381, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_381)
	            .value(R14_12)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_382, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_382)
	            .value(R14_13)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_383, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_383)
	            .value(R14_14)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_384, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_384)
	            .value(R14_15)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_385, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_385)
	            .value(R14_16)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_386, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_386)
	            .value(R14_17)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_387, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_387)
	            .value(R14_18)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_388, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_388)
	            .value(R14_19)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_389, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_389)
	            .value(R14_20)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_390, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_390)
	            .value(R14_21)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_391, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_391)
	            .value(R14_22)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_392, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_392)
	            .value(R14_23)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_393, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_393)
	            .value(R14_24)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_394, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_394)
	            .value(R14_25)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_395, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_395)
	            .value(R14_26)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_396, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_396)
	            .value(R14_27)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_397, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_397)
	            .value(R14_28)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_375_1, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_375_1)
	            .value(R14_29)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_376_1, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_376_1)
	            .value(R14_30)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_398, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_398)
	            .value(R14_31)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_399, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_399)
	            .value(R14_32)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_400, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_400)
	            .value(R14_33)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_377_1, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_377)
	            .value(R14_34)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_378_1, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_378_1)
	            .value(R14_35)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_379_1, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_379_1)
	            .value(R14_36)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_380_1, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_380_1)
	            .value(R14_37)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_381_1, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_381_1)
	            .value(R14_38)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
	        data.put(WorkPlaceColumn.KMK004_382_1, MasterCellData.builder()
	            .columnId(WorkPlaceColumn.KMK004_382_1)
	            .value(R14_39)
	            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
	            .build());
			return MasterData.builder().rowData(data).build();
		}
		
		private Integer convertToInteger(NtsResultRecord r, String name) {
			if (this.database().is(DatabaseProduct.MSSQLSERVER)) {
				return r.getInt(name);
			}
			if (this.database().is(DatabaseProduct.POSTGRESQL)) {
				Boolean b = r.getBoolean(name);
				if (b == null) {
					return null;
				}
				return b ? 1 : 0;
			}
			return null;
		}

}
