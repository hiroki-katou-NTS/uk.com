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
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshmtLegalTimeMSya;
import nts.uk.ctx.at.shared.infra.entity.workrule.week.KsrmtWeekRuleMng;
import nts.uk.file.at.app.export.setworkinghoursanddays.EmployeeColumn;
import nts.uk.file.at.app.export.setworkinghoursanddays.GetKMK004EmployeeExportRepository;
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
public class JpaGetKMK004EmployeeExportData extends JpaRepository implements GetKMK004EmployeeExportRepository {

	private static final String GET_EXPORT_MONTH = "SELECT m.MONTH_STR FROM BCMMT_COMPANY m WHERE m.CID = ?cid";

	private static final String LEGAL_TIME_SYA = "SELECT s FROM KshmtLegalTimeMSya s WHERE "
			+ " s.pk.cid = :cid AND s.pk.ym >= :minYm AND s.pk.ym < :maxYm"
			+ " ORDER BY s.pk.ym";

	private static final String GET_EMPLOYEE_SQLSERVER = " SELECT " + " ROW_NUMBER() OVER( "
			+ " 	PARTITION BY BSYMT_SYAIN.SCD "
			+ " 	ORDER BY BSYMT_SYAIN.SCD ASC) AS ROW_NUMBER, " 
			+ " BSYMT_SYAIN.SID, "
			+ " BSYMT_SYAIN.SCD, " 
			+ " BPSMT_PERSON.BUSINESS_NAME, "
			+ " KSHMT_LEGALTIME_D_REG_SYA.DAILY_TIME, " 
			+ " KSHMT_LEGALTIME_D_REG_SYA.WEEKLY_TIME, "
			+ " KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_AGGR, "
			+ " IIF(KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_AGGR = 1, KRCMT_CALC_M_SET_REG_SYA.INCLUDE_LEGAL_AGGR, NULL) AS INCLUDE_LEGAL_AGGR, "
			+ " IIF(KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_AGGR = 1, KRCMT_CALC_M_SET_REG_SYA.INCLUDE_HOLIDAY_AGGR, NULL) AS INCLUDE_HOLIDAY_AGGR, "
			+ " KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_OT, "
			+ " IIF(KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_OT = 1, KRCMT_CALC_M_SET_REG_SYA.INCLUDE_LEGAL_OT, NULL) AS INCLUDE_LEGAL_OT, "
			+ " IIF(KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_OT = 1, KRCMT_CALC_M_SET_REG_SYA.INCLUDE_HOLIDAY_OT, NULL) AS INCLUDE_HOLIDAY_OT, "
			+ " KRCMT_CALC_M_SET_FLE_COM.WITHIN_TIME_USE, " 
			+ " KRCMT_CALC_M_SET_FLE_SYA.AGGR_METHOD, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.SETTLE_PERIOD_MON, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.SETTLE_PERIOD, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.INCLUDE_HDWK, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.LEGAL_AGGR_SET, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.START_MONTH, "
			+ " IIF(KRCMT_CALC_M_SET_FLE_SYA.AGGR_METHOD = 0, KRCMT_CALC_M_SET_FLE_SYA.INCLUDE_OT, NULL) AS INCLUDE_OT, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.INSUFFIC_SET, " 
			+ " KSHMT_LEGALTIME_D_DEF_SYA.DAILY_TIME AS TRANS_DAILY_TIME, "
			+ " KSHMT_LEGALTIME_D_DEF_SYA.WEEKLY_TIME AS TRANS_WEEKLY_TIME, "
			+ " KRCMT_CALC_M_SET_DEF_SYA.STR_MONTH, "
			+ " KRCMT_CALC_M_SET_DEF_SYA.PERIOD, " 
			+ " KRCMT_CALC_M_SET_DEF_SYA.REPEAT_ATR, "
			+ " KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR, "
			+ " IIF(KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_AGGR = 1, KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_LEGAL_AGGR, NULl) AS DEFOR_INCLUDE_LEGAL_AGGR, "
			+ " IIF(KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_AGGR = 1, KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_HOLIDAY_AGGR, NULl) AS DEFOR_INCLUDE_HOLIDAY_AGGR, "
			+ " KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT, "
			+ " IIF(KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_OT = 1, KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_LEGAL_OT, NULl) AS DEFOR_INCLUDE_LEGAL_OT, "
			+ " IIF(KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_OT = 1, KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_HOLIDAY_OT, NULl) AS DEFOR_INCLUDE_HOLIDAY_OT "
			+ " FROM BSYMT_SYAIN"
			+ " LEFT JOIN KSHMT_LEGALTIME_D_DEF_SYA ON BSYMT_SYAIN.CID = KSHMT_LEGALTIME_D_DEF_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KSHMT_LEGALTIME_D_DEF_SYA.SID "
			+ " LEFT JOIN KSHMT_LEGALTIME_D_REG_SYA ON BSYMT_SYAIN.CID = KSHMT_LEGALTIME_D_REG_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KSHMT_LEGALTIME_D_REG_SYA.SID "
			+ " LEFT JOIN KRCMT_CALC_M_SET_DEF_SYA ON BSYMT_SYAIN.CID = KRCMT_CALC_M_SET_DEF_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KRCMT_CALC_M_SET_DEF_SYA.SID "
			+ " LEFT JOIN KRCMT_CALC_M_SET_FLE_SYA ON BSYMT_SYAIN.CID = KRCMT_CALC_M_SET_FLE_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KRCMT_CALC_M_SET_FLE_SYA.SID "
			+ " LEFT JOIN KRCMT_CALC_M_SET_REG_SYA ON BSYMT_SYAIN.CID = KRCMT_CALC_M_SET_REG_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KRCMT_CALC_M_SET_REG_SYA.SID "
			+ " LEFT JOIN BPSMT_PERSON ON BSYMT_SYAIN.PID = BPSMT_PERSON.PID "
			+ " LEFT JOIN KRCMT_CALC_M_SET_FLE_COM ON  "
			+ "	BSYMT_SYAIN.CID = KRCMT_CALC_M_SET_FLE_COM.CID "
			+ " WHERE  BSYMT_SYAIN.CID = ? " 
			+ " AND  BSYMT_SYAIN.DEL_STATUS_ATR = 0 " 
			+ " ORDER BY BSYMT_SYAIN.SCD ASC ";
	
	private static final String GET_EMPLOYEE_POSTGRE = " SELECT " + " ROW_NUMBER() OVER( "
			+ " 	PARTITION BY BSYMT_SYAIN.SCD "
			+ " 	ORDER BY BSYMT_SYAIN.SCD ASC) AS ROW_NUMBER, " 
			+ " BSYMT_SYAIN.SID, "
			+ " BSYMT_SYAIN.SCD, " 
			+ " BPSMT_PERSON.BUSINESS_NAME, "
			+ " KSHMT_LEGALTIME_D_REG_SYA.DAILY_TIME, " 
			+ " KSHMT_LEGALTIME_D_REG_SYA.WEEKLY_TIME, "
			+ " KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_AGGR, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_AGGR = '1' THEN KRCMT_CALC_M_SET_REG_SYA.INCLUDE_LEGAL_AGGR ELSE NULL END) AS INCLUDE_LEGAL_AGGR, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_AGGR = '1' THEN KRCMT_CALC_M_SET_REG_SYA.INCLUDE_HOLIDAY_AGGR ELSE NULL END) AS INCLUDE_HOLIDAY_AGGR, "
			+ " KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_OT, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_OT = '1' THEN KRCMT_CALC_M_SET_REG_SYA.INCLUDE_LEGAL_OT ELSE NULL END) AS INCLUDE_LEGAL_OT, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_REG_SYA.INCLUDE_EXTRA_OT = '1' THEN KRCMT_CALC_M_SET_REG_SYA.INCLUDE_HOLIDAY_OT ELSE NULL END) AS INCLUDE_HOLIDAY_OT, "
			+ " KRCMT_CALC_M_SET_FLE_COM.WITHIN_TIME_USE, " 
			+ " KRCMT_CALC_M_SET_FLE_SYA.AGGR_METHOD, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.SETTLE_PERIOD_MON, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.SETTLE_PERIOD, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.INCLUDE_HDWK, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.LEGAL_AGGR_SET, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.START_MONTH, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_FLE_SYA.AGGR_METHOD = '0' THEN KRCMT_CALC_M_SET_FLE_SYA.INCLUDE_OT ELSE NULL END) AS INCLUDE_OT, "
			+ " KRCMT_CALC_M_SET_FLE_SYA.INSUFFIC_SET, " 
			+ " KSHMT_LEGALTIME_D_DEF_SYA.DAILY_TIME AS TRANS_DAILY_TIME, "
			+ " KSHMT_LEGALTIME_D_DEF_SYA.WEEKLY_TIME AS TRANS_WEEKLY_TIME, "
			+ " KRCMT_CALC_M_SET_DEF_SYA.STR_MONTH, "
			+ " KRCMT_CALC_M_SET_DEF_SYA.PERIOD, " 
			+ " KRCMT_CALC_M_SET_DEF_SYA.REPEAT_ATR, "
			+ " KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_AGGR = '1' THEN KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_LEGAL_AGGR ELSE NULl END) AS DEFOR_INCLUDE_LEGAL_AGGR, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_AGGR = '1' THEN KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_HOLIDAY_AGGR ELSE NULl END) AS DEFOR_INCLUDE_HOLIDAY_AGGR, "
			+ " KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_OT = '1' THEN KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_LEGAL_OT ELSE NULl END) AS DEFOR_INCLUDE_LEGAL_OT, "
			+ " (CASE WHEN KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_EXTRA_OT = '1' THEN KRCMT_CALC_M_SET_DEF_SYA.INCLUDE_HOLIDAY_OT ELSE NULl END) AS DEFOR_INCLUDE_HOLIDAY_OT "
			+ " FROM BSYMT_SYAIN"
			+ " LEFT JOIN KSHMT_LEGALTIME_D_DEF_SYA ON BSYMT_SYAIN.CID = KSHMT_LEGALTIME_D_DEF_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KSHMT_LEGALTIME_D_DEF_SYA.SID "
			+ " LEFT JOIN KSHMT_LEGALTIME_D_REG_SYA ON BSYMT_SYAIN.CID = KSHMT_LEGALTIME_D_REG_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KSHMT_LEGALTIME_D_REG_SYA.SID "
			+ " LEFT JOIN KRCMT_CALC_M_SET_DEF_SYA ON BSYMT_SYAIN.CID = KRCMT_CALC_M_SET_DEF_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KRCMT_CALC_M_SET_DEF_SYA.SID "
			+ " LEFT JOIN KRCMT_CALC_M_SET_FLE_SYA ON BSYMT_SYAIN.CID = KRCMT_CALC_M_SET_FLE_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KRCMT_CALC_M_SET_FLE_SYA.SID "
			+ " LEFT JOIN KRCMT_CALC_M_SET_REG_SYA ON BSYMT_SYAIN.CID = KRCMT_CALC_M_SET_REG_SYA.CID "
			+ "           AND BSYMT_SYAIN.SID = KRCMT_CALC_M_SET_REG_SYA.SID "
			+ " LEFT JOIN BPSMT_PERSON ON BSYMT_SYAIN.PID = BPSMT_PERSON.PID "
			+ " LEFT JOIN KRCMT_CALC_M_SET_FLE_COM ON  "
			+ "	BSYMT_SYAIN.CID = KRCMT_CALC_M_SET_FLE_COM.CID "
			+ " WHERE  BSYMT_SYAIN.CID = ? " 
			+ " AND BSYMT_SYAIN.DEL_STATUS_ATR = '0' " 
			+ " ORDER BY BSYMT_SYAIN.SCD ASC ";

	@Override
	public List<MasterData> getEmployeeData(int startDate, int endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();

		int month = this.month();

		String startOfWeek = getStartOfWeek(cid);
		
		val legalTimes = this.queryProxy().query(LEGAL_TIME_SYA, KshmtLegalTimeMSya.class)
				.setParameter("cid", cid)
				.setParameter("minYm", startDate * 100 + month)
				.setParameter("maxYm", endDate * 100 + month)
				.getList();
		
		if (this.database().is(DatabaseProduct.MSSQLSERVER)) {
			try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOYEE_SQLSERVER.toString())) {
				stmt.setString(1, cid);
				NtsResultSet result = new NtsResultSet(stmt.executeQuery());
				
				result.forEach(i -> {
					datas.addAll(buildEmployeeRow(i, legalTimes, startDate, endDate, month, startOfWeek));
				});
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (this.database().is(DatabaseProduct.POSTGRESQL)) {
			try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOYEE_POSTGRE.toString())) {
				stmt.setString(1, cid);
				NtsResultSet result = new NtsResultSet(stmt.executeQuery());
				
				result.forEach(i -> {
					datas.addAll(buildEmployeeRow(i, legalTimes, startDate, endDate, month, startOfWeek));
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
	
	private Integer convertToPostgre(NtsResultRecord r, String name) {
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

	private List<MasterData> buildEmployeeRow(NtsResultRecord r, List<KshmtLegalTimeMSya> legals, int startDate,
			int endDate, int month, String startOfWeek) {
		List<MasterData> datas = new ArrayList<>();

		Integer refPreTime = convertToPostgre(r, "WITHIN_TIME_USE");
		String kdp004_401 = I18NText.getText("KMK004_401");

		for (int y = startDate; y <= endDate; y++) {
			String sid = r.getString("SID");
			int ym = y * 100 + month;

			val list = legals.stream().filter(l -> l.pk.ym == ym && l.pk.sid.equals(sid)).collect(Collectors.toList());
			
			val normal = list.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value).findFirst();
			val defor = list.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value).findFirst();
			val flex = list.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.FLEX.value).findFirst();
			
			Integer rowNumber = r.getInt("ROW_NUMBER");
			Integer includeExtraAggr = convertToPostgre(r, "INCLUDE_EXTRA_AGGR");
			Integer includeExtraOt = convertToPostgre(r, "INCLUDE_EXTRA_OT");
			Integer startMonth = r.getInt("START_MONTH");
			Integer selectPeriodMon = r.getInt("SETTLE_PERIOD_MON");
			Integer aggrMethod = convertToPostgre(r, "AGGR_METHOD");
			Integer strMonth = r.getInt("STR_MONTH");
			Integer period = r.getInt("PERIOD");
			Integer repeatAtr = convertToPostgre(r, "REPEAT_ATR");
			Integer deforIncludeExtraAggr = convertToPostgre(r, "DEFOR_INCLUDE_EXTRA_AGGR");
			Integer deforIncludeExtraOt = convertToPostgre(r, "DEFOR_INCLUDE_EXTRA_OT");

			datas.add(buildEmployeeARow(
					// R10_1
					rowNumber == null ? null : rowNumber == 1 ? r.getString("SCD") : null,
					// R10_2
					rowNumber == null ? null : rowNumber == 1 ? r.getString("BUSINESS_NAME") : null,
					// R10_3
					String.valueOf(y),
					// R10_4
					((month - 1) % 12 + 1) + kdp004_401,
					// R10_5
					KMK004PrintCommon.convertTime(normal.map(m -> m.legalTime).orElse(null)),
					// R10_6
					KMK004PrintCommon.convertTime(r.getInt("DAILY_TIME")),
					// R10_7
					KMK004PrintCommon.convertTime(r.getInt("WEEKLY_TIME")),
					// R10_8
					KMK004PrintCommon.getExtraType(includeExtraAggr),
					// R10_9
					includeExtraAggr == null ? null : includeExtraAggr != 0 ? KMK004PrintCommon.getLegalType(convertToPostgre(r, "INCLUDE_LEGAL_AGGR"))
							: null,
					// R10_10
					includeExtraAggr == null ? null : includeExtraAggr != 0
							? KMK004PrintCommon.getLegalType(convertToPostgre(r, "INCLUDE_HOLIDAY_AGGR")) : null,
					// R10_11
					KMK004PrintCommon.getExtraType(includeExtraOt),
					// R10_12
					includeExtraAggr == null ? null : includeExtraOt != 0 ? KMK004PrintCommon.getLegalType(convertToPostgre(r, "INCLUDE_LEGAL_OT"))
							: null,
					// R10_13
					includeExtraAggr == null ? null : includeExtraOt != 0 ? KMK004PrintCommon.getLegalType(convertToPostgre(r, "INCLUDE_HOLIDAY_OT"))
							: null,
					// R10_14
					KMK004PrintCommon.getFlexType(refPreTime),
					// R10_15
					((month - 1) % 12 + 1) + kdp004_401,
					// R10_16
					KMK004PrintCommon.convertTime(refPreTime == 0?null:flex.map(m -> m.withinTime).orElse(null)),
					// R10_17
					KMK004PrintCommon.convertTime(flex.map(m -> m.legalTime).orElse(null)),
					// R10_18
					KMK004PrintCommon.convertTime(flex.map(m -> m.weekAvgTime).orElse(null)),
					// R10_19
					KMK004PrintCommon.getSettle(convertToPostgre(r, "SETTLE_PERIOD")),
					// R10_20
					startMonth == null ? null : startMonth.toString() + "月",
					// R10_21
					selectPeriodMon == null ? null : selectPeriodMon == 2 ? "2ヶ月" : "3ヶ月",
					// R10_22
					KMK004PrintCommon.getShortageTime(convertToPostgre(r, "INSUFFIC_SET")),
					// R10_23
					KMK004PrintCommon.getAggTypeEmployee(aggrMethod == null ? 3 : aggrMethod),
					// R10_24
					aggrMethod == null ? null : aggrMethod == 0 ? KMK004PrintCommon.getInclude(convertToPostgre(r, "INCLUDE_OT")) : null,
					// R10_25
					KMK004PrintCommon.getInclude(convertToPostgre(r, "INCLUDE_HDWK")),
					// R10_26
					KMK004PrintCommon.getLegal(convertToPostgre(r, "LEGAL_AGGR_SET")),
					// R10_27
					((month - 1) % 12 + 1) + kdp004_401,
					// R10_28
					KMK004PrintCommon.convertTime(defor.map(m -> m.legalTime).orElse(null)),
					// R10_29
					KMK004PrintCommon.convertTime(convertToPostgre(r, "TRANS_DAILY_TIME")),
					// R10_30
					KMK004PrintCommon.convertTime(convertToPostgre(r, "TRANS_WEEKLY_TIME")),
					// R10_31
					strMonth == null ? null : strMonth + I18NText.getText("KMK004_402"),
					// R10_32
					period == null ? null : period + I18NText.getText("KMK004_403"),
					// R10_33
					repeatAtr == null ? null : repeatAtr == 1 ? "○" : "-",
					// R10_34
					KMK004PrintCommon.getWeeklySurcharge(deforIncludeExtraAggr),
					// R10_35
					deforIncludeExtraAggr == null ? null : deforIncludeExtraAggr != 0
							? KMK004PrintCommon.getLegalType(convertToPostgre(r, "DEFOR_INCLUDE_LEGAL_AGGR")) : null,
					// R10_36
					deforIncludeExtraAggr == null ? null : deforIncludeExtraAggr != 0
							? KMK004PrintCommon.getLegalType(convertToPostgre(r, "DEFOR_INCLUDE_HOLIDAY_AGGR")) : null,
					// R10_37
					KMK004PrintCommon.getWeeklySurcharge(deforIncludeExtraOt),
					// R10_38
					deforIncludeExtraOt == null ? null : deforIncludeExtraOt != 0
							? KMK004PrintCommon.getLegalType(convertToPostgre(r, "DEFOR_INCLUDE_LEGAL_OT")) : null,
					// R10_39
					deforIncludeExtraOt == null ? null : deforIncludeExtraOt != 0
							? KMK004PrintCommon.getLegalType(convertToPostgre(r, "DEFOR_INCLUDE_HOLIDAY_OT")) : null));

//			int nextYm = ym + 1;
//			
//			val listNextYear = legals.stream().filter(l -> l.pk.ym == nextYm && l.pk.sid.equals(sid)).collect(Collectors.toList());
//			
//			val normalN = listNextYear.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value).findFirst();
//			val deforN = listNextYear.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value).findFirst();
//			val flexN = listNextYear.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.FLEX.value).findFirst();
//			
////			 buil arow = month + 1
//			 datas.add(buildEmployeeARowChild(
//			 // R10_4
//			 ((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
//			 // R10_5
//			 KMK004PrintCommon.convertTime(normalN.isPresent() ? normalN.get().legalTime : null),
//			 // R10_15
//			 ((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
//			 // R10_16
//			 KMK004PrintCommon.convertTime(refPreTime == 0?null:flexN.isPresent() ? flexN.get().withinTime : null),
//			 // R10_17
//			 KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().legalTime : null),
//			 // R10_18
//			 KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().weekAvgTime : null),
//			 // R10_27
//			 ((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
//			 // R10_28
//			 KMK004PrintCommon.convertTime(deforN.isPresent() ? deforN.get().legalTime : null)));
			
			 for (int i = 1; i < 12; i++) {
				int nm = month + i;
				int m = nm > 12 ? nm % 12 : nm;
				int currentYm = (y + nm> 12? nm / 12 : 0) * 100 + m;
				
				val listC = legals.stream().filter(l -> l.pk.ym == currentYm && l.pk.sid.equals(sid)).collect(Collectors.toList());
				
				val normalC = listC.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value).findFirst();
				val deforC = listC.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value).findFirst();
				val flexC = listC.stream().filter(l -> l.pk.type == LaborWorkTypeAttr.FLEX.value).findFirst();
				
				datas.add(buildEmployeeARow(	 
					// R10_1
					null,
					// R10_2
					null,
					// R10_3
					null,
					// R10_4
					(m) + kdp004_401,
					// R10_5
					KMK004PrintCommon.convertTime(normalC.map(l -> l.legalTime).orElse(null)),
					// R10_6
					null,
					// R10_7
					null,
					// R10_8
					null,
					// R10_9
					null,
					// R10_10
					null,
					// R10_11
					null,
					// R10_12
					null,
					// R10_13
					null,
					// R10_14
					null,
					// R10_15
					(m) + kdp004_401,
					// R10_16
					KMK004PrintCommon.convertTime(refPreTime == 0?null:flexC.map(f -> f.withinTime).orElse(null)),
					// R10_17
					KMK004PrintCommon.convertTime(flexC.map(f -> f.legalTime).orElse(null)),
					// R10_18
					KMK004PrintCommon.convertTime(flexC.map(f -> f.weekAvgTime).orElse(null)),
					// R10_19
					null,
					// R10_20
					null,
					// R10_21
					null,
					// R10_22
					null,
					// R10_23
					null,
					// R10_24
					null,
					// R10_25
					null,
					// R10_26
					null,
					// R10_27
					(m) + kdp004_401,
					// R10_28
					KMK004PrintCommon.convertTime(deforC.map(f -> f.legalTime).orElse(null)),
					// R10_29
					null,
					// R10_30
					null,
					// R10_31
					null,
					// R10_32
					null,
					// R10_33
					null,
					// R10_34
					null,
					// R10_35
					null,
					// R10_36
					null,
					// R10_37
					null,
					// R10_38
					null,
					// R10_39
					null));
			 }
		}
		return datas;
	}
	
	
	private MasterData buildEmployeeARow(String R10_1, String R10_2, String R10_3, String R10_4, String R10_5,
			String R10_6, String R10_7, String R10_8, String R10_9, String R10_10, String R10_11, String R10_12,
			String R10_13, String R10_14, String R10_15, String R10_16, String R10_17, String R10_18,
			String R10_19, String R10_20, String R10_21, String R10_22, String R10_23, String R10_24,
			String R10_25, String R10_26, String R10_27, String R10_28, String R10_29, String R10_30,
			String R10_31, String R10_32, String R10_33, String R10_34, String R10_35, String R10_36,
			String R10_37, String R10_38, String R10_39) {

		Map<String, MasterCellData> data = new HashMap<>();

		data.put(EmployeeColumn.KMK004_183, MasterCellData.builder().columnId(EmployeeColumn.KMK004_183).value(R10_1)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_184, MasterCellData.builder().columnId(EmployeeColumn.KMK004_184).value(R10_2)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_372, MasterCellData.builder().columnId(EmployeeColumn.KMK004_372).value(R10_3)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_373, MasterCellData.builder().columnId(EmployeeColumn.KMK004_373).value(R10_4)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_374, MasterCellData.builder().columnId(EmployeeColumn.KMK004_374).value(R10_5)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_375, MasterCellData.builder().columnId(EmployeeColumn.KMK004_375).value(R10_6)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_376, MasterCellData.builder().columnId(EmployeeColumn.KMK004_376).value(R10_7)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_377, MasterCellData.builder().columnId(EmployeeColumn.KMK004_377).value(R10_8)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_378, MasterCellData.builder().columnId(EmployeeColumn.KMK004_378).value(R10_9)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_379, MasterCellData.builder().columnId(EmployeeColumn.KMK004_379).value(R10_10)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_380, MasterCellData.builder().columnId(EmployeeColumn.KMK004_380).value(R10_11)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_381, MasterCellData.builder().columnId(EmployeeColumn.KMK004_381).value(R10_12)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_382, MasterCellData.builder().columnId(EmployeeColumn.KMK004_382).value(R10_13)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_383, MasterCellData.builder().columnId(EmployeeColumn.KMK004_383).value(R10_14)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_384, MasterCellData.builder().columnId(EmployeeColumn.KMK004_384).value(R10_15)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_385, MasterCellData.builder().columnId(EmployeeColumn.KMK004_385).value(R10_16)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_386, MasterCellData.builder().columnId(EmployeeColumn.KMK004_386).value(R10_17)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_387, MasterCellData.builder().columnId(EmployeeColumn.KMK004_387).value(R10_18)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_388, MasterCellData.builder().columnId(EmployeeColumn.KMK004_388).value(R10_19)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_389, MasterCellData.builder().columnId(EmployeeColumn.KMK004_389).value(R10_20)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_390, MasterCellData.builder().columnId(EmployeeColumn.KMK004_390).value(R10_21)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_391, MasterCellData.builder().columnId(EmployeeColumn.KMK004_391).value(R10_22)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_392, MasterCellData.builder().columnId(EmployeeColumn.KMK004_392).value(R10_23)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_393, MasterCellData.builder().columnId(EmployeeColumn.KMK004_393).value(R10_24)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_394, MasterCellData.builder().columnId(EmployeeColumn.KMK004_394).value(R10_25)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_395, MasterCellData.builder().columnId(EmployeeColumn.KMK004_395).value(R10_26)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_396, MasterCellData.builder().columnId(EmployeeColumn.KMK004_396).value(R10_27)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_397, MasterCellData.builder().columnId(EmployeeColumn.KMK004_397).value(R10_28)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_375_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_375_1).value(R10_29)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_376_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_376_1).value(R10_30)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_398, MasterCellData.builder().columnId(EmployeeColumn.KMK004_398).value(R10_31)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_399, MasterCellData.builder().columnId(EmployeeColumn.KMK004_399).value(R10_32)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_400, MasterCellData.builder().columnId(EmployeeColumn.KMK004_400).value(R10_33)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_377_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_377_1).value(R10_34)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_378_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_378_1).value(R10_35)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_379_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_379_1).value(R10_36)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_380_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_380_1).value(R10_37)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_381_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_381_1).value(R10_38)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_382_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_382_1).value(R10_39)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		return MasterData.builder().rowData(data).build();
	}

	private String getStartOfWeek(String cid) {

		Optional<WeekRuleManagement> startOfWeek = this.queryProxy().find(cid, KsrmtWeekRuleMng.class)
				.map(w -> w.toDomain());

		return KMK004PrintCommon.getWeekStart(startOfWeek);
	}

	private int month() {
		String cid = AppContexts.user().companyId();
		Query monthQuery = this.getEntityManager().createNativeQuery(GET_EXPORT_MONTH.toString()).setParameter("cid",
				cid);
		List<?> data = monthQuery.getResultList();
		
		if (data.size() != 0) {
			return Integer.valueOf(data.get(0).toString());
		}
		
		return 1;
	}

}
