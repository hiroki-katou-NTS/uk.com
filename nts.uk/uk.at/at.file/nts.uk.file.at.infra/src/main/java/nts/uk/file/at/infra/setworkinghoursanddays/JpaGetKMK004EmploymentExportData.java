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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshmtLegalTimeMEmp;
import nts.uk.ctx.at.shared.infra.entity.workrule.week.KsrmtWeekRuleMng;
import nts.uk.file.at.app.export.setworkinghoursanddays.EmploymentColumn;
import nts.uk.file.at.app.export.setworkinghoursanddays.GetKMK004EmploymentExportRepository;
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
public class JpaGetKMK004EmploymentExportData extends JpaRepository implements GetKMK004EmploymentExportRepository {
	
	private static final String GET_EXPORT_MONTH = "SELECT m.MONTH_STR FROM BCMMT_COMPANY m WHERE m.CID = ?cid";
	
	private static final String LEGAL_TIME_EMP = "SELECT s FROM KshmtLegalTimeMEmp s WHERE "
			+ " s.pk.cid = :cid AND s.pk.ym >= :minYm AND s.pk.ym < :maxYm"
			+ " ORDER BY s.pk.ym";	
	
	private static final String GET_EMPLOYMENT_SQLSERVER = "SELECT "
											+" ROW_NUMBER() OVER(PARTITION BY BSYMT_EMPLOYMENT.CODE ORDER BY IIF(BSYMT_EMPLOYMENT.NAME IS NOT NULL,0,1), BSYMT_EMPLOYMENT.CODE ASC) AS ROW_NUMBER_CODE, "
											+" BSYMT_EMPLOYMENT.CODE AS CODE,  "
											+" IIF(BSYMT_EMPLOYMENT.NAME IS NOT NULL, BSYMT_EMPLOYMENT.NAME, 'マスタ未登録') AS NAME, "
											+" KSHMT_LEGALTIME_D_REG_EMP.DAILY_TIME, "
											+" KSHMT_LEGALTIME_D_REG_EMP.WEEKLY_TIME, "
											+" KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_AGGR AS INCLUDE_EXTRA_AGGR, "
											+" IIF (KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_AGGR = 1, KRCMT_CALC_M_SET_REG_EMP.INCLUDE_LEGAL_AGGR , NUll) AS INCLUDE_LEGAL_AGGR, "
											+" IIF (KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_AGGR = 1, KRCMT_CALC_M_SET_REG_EMP.INCLUDE_HOLIDAY_AGGR , NUll) AS INCLUDE_HOLIDAY_AGGR, "
											+" KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_OT, "
											+" IIF (KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_OT = 1, KRCMT_CALC_M_SET_REG_EMP.INCLUDE_LEGAL_OT , NUll) AS INCLUDE_LEGAL_OT, "
											+" IIF (KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_OT = 1, KRCMT_CALC_M_SET_REG_EMP.INCLUDE_HOLIDAY_OT , NUll) AS INCLUDE_HOLIDAY_OT, "
											+ "KRCMT_CALC_M_SET_FLE_COM.WITHIN_TIME_USE, "
											+" KRCMT_CALC_M_SET_FLE_EMP.AGGR_METHOD, "
											+" KRCMT_CALC_M_SET_FLE_EMP.SETTLE_PERIOD_MON, "
											+" KRCMT_CALC_M_SET_FLE_EMP.SETTLE_PERIOD, "
											+" KRCMT_CALC_M_SET_FLE_EMP.START_MONTH AS FLEX_START_MONTH, "
											+" KRCMT_CALC_M_SET_FLE_EMP.INCLUDE_HDWK, "
											+" KRCMT_CALC_M_SET_FLE_EMP.LEGAL_AGGR_SET, "
											+" IIF( KRCMT_CALC_M_SET_FLE_EMP.AGGR_METHOD = 0, KRCMT_CALC_M_SET_FLE_EMP.INCLUDE_OT , NULL ) AS INCLUDE_OT, "
											+" KRCMT_CALC_M_SET_FLE_EMP.INSUFFIC_SET, "
											+" KSHMT_LEGALTIME_D_DEF_EMP.DAILY_TIME AS LAR_DAILY_TIME, "
											+" KSHMT_LEGALTIME_D_DEF_EMP.WEEKLY_TIME AS LAR_WEEKLY_TIME, "
											+" KRCMT_CALC_M_SET_DEF_EMP.STR_MONTH, "
											+" KRCMT_CALC_M_SET_DEF_EMP.PERIOD, "
											+" KRCMT_CALC_M_SET_DEF_EMP.REPEAT_ATR, "
											+" KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR, "
											+" IIF(KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_AGGR = 1, KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_LEGAL_AGGR , NUll ) AS DEFOR_INCLUDE_LEGAL_AGGR, "
											+" IIF(KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_AGGR = 1, KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_HOLIDAY_AGGR , NUll ) AS DEFOR_INCLUDE_HOLIDAY_AGGR, "
											+" KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT, "
											+" IIF(KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_OT = 1, KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_LEGAL_OT , NUll ) AS DEFOR_INCLUDE_LEGAL_OT, "
											+" IIF(KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_OT = 1, KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_HOLIDAY_OT , NUll ) AS DEFOR_INCLUDE_HOLIDAY_OT "
											+" 	FROM BSYMT_EMPLOYMENT "
											+" LEFT JOIN KSHMT_LEGALTIME_D_REG_EMP ON BSYMT_EMPLOYMENT.CID = KSHMT_LEGALTIME_D_REG_EMP.CID " 
											+"            AND BSYMT_EMPLOYMENT.CODE = KSHMT_LEGALTIME_D_REG_EMP.EMP_CD  		" 
											+" LEFT JOIN KSHMT_LEGALTIME_D_DEF_EMP ON BSYMT_EMPLOYMENT.CID = KSHMT_LEGALTIME_D_DEF_EMP.CID  " 
											+"            AND BSYMT_EMPLOYMENT.CODE = KSHMT_LEGALTIME_D_DEF_EMP.EMP_CD  		" 
											+" LEFT JOIN KRCMT_CALC_M_SET_FLE_COM ON BSYMT_EMPLOYMENT.CID = KRCMT_CALC_M_SET_FLE_COM.CID  "
											+" LEFT JOIN KRCMT_CALC_M_SET_DEF_EMP ON BSYMT_EMPLOYMENT.CID = KRCMT_CALC_M_SET_DEF_EMP.CID  "
											+"            AND BSYMT_EMPLOYMENT.CODE = KRCMT_CALC_M_SET_DEF_EMP.EMP_CD  		"
											+" LEFT JOIN KRCMT_CALC_M_SET_FLE_EMP ON BSYMT_EMPLOYMENT.CID = KRCMT_CALC_M_SET_FLE_EMP.CID  "
											+"            AND BSYMT_EMPLOYMENT.CODE = KRCMT_CALC_M_SET_FLE_EMP.EMP_CD  		"
											+" LEFT JOIN KRCMT_CALC_M_SET_REG_EMP ON BSYMT_EMPLOYMENT.CID = KRCMT_CALC_M_SET_REG_EMP.CID  "
											+"            AND BSYMT_EMPLOYMENT.CODE = KRCMT_CALC_M_SET_REG_EMP.EMP_CD"
											+" WHERE BSYMT_EMPLOYMENT.CID = ? "
											+" ORDER BY IIF(BSYMT_EMPLOYMENT.NAME IS NOT NULL,0,1), BSYMT_EMPLOYMENT.CODE ";
	
	private static final String GET_EMPLOYMENT_POSTGRE = "SELECT "
			+" ROW_NUMBER() OVER(PARTITION BY BSYMT_EMPLOYMENT.CODE ORDER BY (CASE WHEN BSYMT_EMPLOYMENT.NAME IS NOT NULL THEN 0 ELSE 1 END), BSYMT_EMPLOYMENT.CODE ASC) AS ROW_NUMBER_CODE, "
			+" BSYMT_EMPLOYMENT.CODE AS CODE,  "
			+" (CASE WHEN BSYMT_EMPLOYMENT.NAME IS NOT NULL THEN BSYMT_EMPLOYMENT.NAME ELSE 'マスタ未登録' END) AS NAME, "
			+" KSHMT_LEGALTIME_D_REG_EMP.DAILY_TIME, "
			+" KSHMT_LEGALTIME_D_REG_EMP.WEEKLY_TIME, "
			+" KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_AGGR AS INCLUDE_EXTRA_AGGR, "
			+" (CASE WHEN KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_AGGR = '1' THEN KRCMT_CALC_M_SET_REG_EMP.INCLUDE_LEGAL_AGGR ELSE NUll END) AS INCLUDE_LEGAL_AGGR, "
			+" (CASE WHEN KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_AGGR = '1' THEN KRCMT_CALC_M_SET_REG_EMP.INCLUDE_HOLIDAY_AGGR ELSE NUll END) AS INCLUDE_HOLIDAY_AGGR, "
			+" KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_OT, "
			+" (CASE WHEN KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_OT = '1' THEN KRCMT_CALC_M_SET_REG_EMP.INCLUDE_LEGAL_OT ELSE NUll END) AS INCLUDE_LEGAL_OT, "
			+" (CASE WHEN KRCMT_CALC_M_SET_REG_EMP.INCLUDE_EXTRA_OT = '1' THEN KRCMT_CALC_M_SET_REG_EMP.INCLUDE_HOLIDAY_OT ELSE NUll END) AS INCLUDE_HOLIDAY_OT, "
			+ "KRCMT_CALC_M_SET_FLE_COM.WITHIN_TIME_USE, "
			+" KRCMT_CALC_M_SET_FLE_EMP.AGGR_METHOD, "
			+" KRCMT_CALC_M_SET_FLE_EMP.SETTLE_PERIOD_MON, "
			+" KRCMT_CALC_M_SET_FLE_EMP.SETTLE_PERIOD, "
			+" KRCMT_CALC_M_SET_FLE_EMP.START_MONTH AS FLEX_START_MONTH, "
			+" KRCMT_CALC_M_SET_FLE_EMP.INCLUDE_HDWK, "
			+" KRCMT_CALC_M_SET_FLE_EMP.LEGAL_AGGR_SET, "
			+" (CASE WHEN KRCMT_CALC_M_SET_FLE_EMP.AGGR_METHOD = '0' THEN KRCMT_CALC_M_SET_FLE_EMP.INCLUDE_OT ELSE NULL END) AS INCLUDE_OT, "
			+" KRCMT_CALC_M_SET_FLE_EMP.INSUFFIC_SET, "
			+" KSHMT_LEGALTIME_D_DEF_EMP.DAILY_TIME AS LAR_DAILY_TIME, "
			+" KSHMT_LEGALTIME_D_DEF_EMP.WEEKLY_TIME AS LAR_WEEKLY_TIME, "
			+" KRCMT_CALC_M_SET_DEF_EMP.STR_MONTH, "
			+" KRCMT_CALC_M_SET_DEF_EMP.PERIOD, "
			+" KRCMT_CALC_M_SET_DEF_EMP.REPEAT_ATR, "
			+" KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR, "
			+" (CASE WHEN KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_AGGR = '1' THEN KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_LEGAL_AGGR ELSE NUll END) AS DEFOR_INCLUDE_LEGAL_AGGR, "
			+" (CASE WHEN KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_AGGR = '1' THEN KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_HOLIDAY_AGGR ELSE NUll END) AS DEFOR_INCLUDE_HOLIDAY_AGGR, "
			+" KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT, "
			+" (CASE WHEN KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_OT = '1' THEN KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_LEGAL_OT ELSE NUll END) AS DEFOR_INCLUDE_LEGAL_OT, "
			+" (CASE WHEN KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_EXTRA_OT = '1' THEN KRCMT_CALC_M_SET_DEF_EMP.INCLUDE_HOLIDAY_OT ELSE NUll END) AS DEFOR_INCLUDE_HOLIDAY_OT "
			+" 	FROM BSYMT_EMPLOYMENT "
			+" LEFT JOIN KSHMT_LEGALTIME_D_REG_EMP ON BSYMT_EMPLOYMENT.CID = KSHMT_LEGALTIME_D_REG_EMP.CID " 
			+"            AND BSYMT_EMPLOYMENT.CODE = KSHMT_LEGALTIME_D_REG_EMP.EMP_CD  		" 
			+" LEFT JOIN KSHMT_LEGALTIME_D_DEF_EMP ON BSYMT_EMPLOYMENT.CID = KSHMT_LEGALTIME_D_DEF_EMP.CID  " 
			+"            AND BSYMT_EMPLOYMENT.CODE = KSHMT_LEGALTIME_D_DEF_EMP.EMP_CD  		" 
			+" LEFT JOIN KRCMT_CALC_M_SET_FLE_COM ON BSYMT_EMPLOYMENT.CID = KRCMT_CALC_M_SET_FLE_COM.CID  "
			+" LEFT JOIN KRCMT_CALC_M_SET_DEF_EMP ON BSYMT_EMPLOYMENT.CID = KRCMT_CALC_M_SET_DEF_EMP.CID  "
			+"            AND BSYMT_EMPLOYMENT.CODE = KRCMT_CALC_M_SET_DEF_EMP.EMP_CD  		"
			+" LEFT JOIN KRCMT_CALC_M_SET_FLE_EMP ON BSYMT_EMPLOYMENT.CID = KRCMT_CALC_M_SET_FLE_EMP.CID  "
			+"            AND BSYMT_EMPLOYMENT.CODE = KRCMT_CALC_M_SET_FLE_EMP.EMP_CD  		"
			+" LEFT JOIN KRCMT_CALC_M_SET_REG_EMP ON BSYMT_EMPLOYMENT.CID = KRCMT_CALC_M_SET_REG_EMP.CID  "
			+"            AND BSYMT_EMPLOYMENT.CODE = KRCMT_CALC_M_SET_REG_EMP.EMP_CD"
			+" WHERE BSYMT_EMPLOYMENT.CID = ? "
			+" ORDER BY (CASE WHEN BSYMT_EMPLOYMENT.NAME IS NOT NULL THEN 0 ELSE 1 END), BSYMT_EMPLOYMENT.CODE ";
	@Override
	public List<MasterData> getEmploymentExportData(int startDate, int endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		int month = this.month();

		String startOfWeek = getStartOfWeek(cid);

		val legalTimes = this.queryProxy().query(LEGAL_TIME_EMP, KshmtLegalTimeMEmp.class)
				.setParameter("cid", cid)
				.setParameter("minYm", startDate * 100 + month)
				.setParameter("maxYm", endDate * 100 + month)
				.getList();
			
		if (this.database().is(DatabaseProduct.MSSQLSERVER)) {
			try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOYMENT_SQLSERVER.toString())) {
				stmt.setString(1, cid);
				NtsResultSet result = new NtsResultSet(stmt.executeQuery());
				
				result.forEach(i -> {
					datas.addAll(buildEmploymentRow(i, legalTimes, startDate, endDate, month, startOfWeek));
				});
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (this.database().is(DatabaseProduct.POSTGRESQL)) {
			try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOYMENT_POSTGRE.toString())) {
				stmt.setString(1, cid);
				NtsResultSet result = new NtsResultSet(stmt.executeQuery());
				
				result.forEach(i -> {
					datas.addAll(buildEmploymentRow(i, legalTimes, startDate, endDate, month, startOfWeek));
				});
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("not supported");
		}
		return datas;
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
	
	private List<MasterData> buildEmploymentRow(NtsResultRecord r, List<KshmtLegalTimeMEmp> legals, int startDate, int endDate, int month, String startOfWeek) {
		List<MasterData> datas = new ArrayList<>();

		Integer refPreTime = convertToInteger(r, "WITHIN_TIME_USE");
		String kdp004_401 = I18NText.getText("KMK004_401");
		
		for (int y = startDate; y <= endDate; y++) {
			String employmentCode = r.getString("CODE");
			int ym = y *100 + month;
			
			val list = legals.stream().filter(l -> l.pk.ym == ym && l.pk.empCD.equals(employmentCode)).collect(Collectors.toList());
			
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
			
			datas.add(buildEmploymentARow(
					//R12_1
					r.getInt("ROW_NUMBER_CODE") == 1 ? r.getString("CODE") : null,
					//R12_2
					r.getInt("ROW_NUMBER_CODE") == 1 ? r.getString("NAME") : null,
					//R12_3
					String.valueOf(y),
					//R12_4
					((month - 1) % 12 + 1) + kdp004_401, 
					//R12_5
					KMK004PrintCommon.convertTime(normal.map(m -> m.legalTime).orElse(null)),
					//R12_6
					KMK004PrintCommon.convertTime(r.getInt(("DAILY_TIME"))),
					//R12_7
					KMK004PrintCommon.convertTime(r.getInt(("WEEKLY_TIME"))),
					//R12_8
					KMK004PrintCommon.getExtraType(includeExtraAggr),
					//R12_9
					includeExtraAggr == null ? null : includeExtraAggr != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "INCLUDE_LEGAL_AGGR")) : null,
					//R12_10
					includeExtraAggr == null ? null: includeExtraAggr != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "INCLUDE_HOLIDAY_AGGR")) : null, 
					//R12_11
					KMK004PrintCommon.getExtraType(includeExtraOt),
					//R12_12
					includeExtraOt == null ? null : includeExtraOt != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "INCLUDE_LEGAL_OT")) : null, 
					//R12_13		
					includeExtraOt == null ? null : includeExtraOt != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "INCLUDE_HOLIDAY_OT")) : null,
					//R12_14
					KMK004PrintCommon.getFlexType(refPreTime),
					//R12_15
					((month - 1) % 12 + 1) + kdp004_401,
					//R12_16 
					KMK004PrintCommon.convertTime(refPreTime == 0? null : flex.map(m -> m.withinTime).orElse(null)),
					//R12_17
					KMK004PrintCommon.convertTime(flex.map(m -> m.legalTime).orElse(null)),
					//R10_18
					KMK004PrintCommon.convertTime(flex.map(m -> m.weekAvgTime).orElse(null)),
					//R12_19
					KMK004PrintCommon.getSettle(convertToInteger(r, "SETTLE_PERIOD")) ,
					//R12_20
					flexStartMonth == null ? null : flexStartMonth.toString() + "月",
					//R12_21
					selectPeriodMon == null ? null : selectPeriodMon == 2 ? "2ヶ月" : "3ヶ月",
					//R12_22
					KMK004PrintCommon.getShortageTime(convertToInteger(r, "INSUFFIC_SET")), 
					//R12_23
					KMK004PrintCommon.getAggType(aggrMethod),
					//R12_24
					aggrMethod == null ? null : aggrMethod == 0 ? KMK004PrintCommon.getInclude(convertToInteger(r, "INCLUDE_OT")) : null,
					//R12_25
					KMK004PrintCommon.getInclude(convertToInteger(r, "INCLUDE_HDWK")),
					//R12_26
					KMK004PrintCommon.getLegal(convertToInteger(r, "LEGAL_AGGR_SET")),
					//R12_27
					((month - 1) % 12 + 1) + kdp004_401,
					//R12_28
					KMK004PrintCommon.convertTime(defor.isPresent() ? defor.get().legalTime : null),
					//R12_29
					KMK004PrintCommon.convertTime(r.getInt(("LAR_DAILY_TIME"))), 
					//R12_30
					KMK004PrintCommon.convertTime(r.getInt(("LAR_WEEKLY_TIME"))),
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
					deforIncludeExtraOt == null?null : deforIncludeExtraOt != 0 ? KMK004PrintCommon.getLegalType(convertToInteger(r, "DEFOR_INCLUDE_HOLIDAY_OT")) : null
					));

//			int nextYm = y *100 + month + 1;
//			
//			
//			val normalN = legals.stream()
//					.filter(l -> l.pk.ym == nextYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
//					.findFirst();
//			val deforN = legals.stream()
//					.filter(l -> l.pk.ym == nextYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
//					.findFirst();
//			val flexN = legals.stream()
//					.filter(l -> l.pk.ym == nextYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
//					.findFirst();
//			// buil arow = month + 1
//			datas.add(buildEmploymentARow(
//					//R12_1
//					 null,
//					//R12_2
//					null,
//					//R12_3
//					null,
//					//R12_4
//					((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"), 
//					//R12_5
//					KMK004PrintCommon.convertTime(normalN.isPresent() ? normalN.get().legalTime : null),
//					//R12_6
//					null,
//					//R12_7
//					null,
//					//R12_8
//					null,
//					//R12_9
//					null, 
//					//R12_10
//					null, 
//					//R12_11
//					null,
//					//R12_12
//					null, 
//					//R12_13		
//					null,
//					//R12_14
//					null,
//					//R12_15
//					((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
//					//R12_16 
//					KMK004PrintCommon.convertTime(refPreTime == 0? null :flexN.isPresent() ? flexN.get().withinTime : null),
//					//R12_17
//					KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().legalTime : null),
//					//R10_18
//					KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().weekAvgTime : null),
//					//R12_19
//					null,
//					//R12_20
//					null,
//					//R12_21
//					null,
//					//R12_22
//					null, 
//					//R12_23
//					null,
//					//R12_24
//					null,
//					//R12_25
//					null,
//					//R12_26
//					null,
//					//R12_27
//					((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
//					//R12_28
//					KMK004PrintCommon.convertTime(deforN.isPresent() ? deforN.get().legalTime : null),
//					//R12_29
//					null, 
//					//R12_30
//					null,
//					//R12_31
//					null,
//					//R12_32
//					null,
//					//R12_33
//					null,
//					//R12_34
//					null,
//					//R12_35
//					null,
//					//R12_36
//					null,
//					//R12_37
//					null,
//					//R12_38
//					null,
//					//R12_39
//					null
//					));
			
			// buil month remain
			for (int i = 1; i < 12; i++) {
				int nm = month + i;
				int m = nm > 12 ? nm % 12 : nm;
				int currentYm = (y + nm> 12? nm / 12 : 0) * 100 + m;
				
				val listC = legals.stream()
						.filter(l -> l.pk.ym == currentYm && l.pk.empCD.equals(employmentCode)).collect(Collectors.toList());
				
				val normalC = listC.stream()
						.filter(l -> l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
						.findFirst();
				val deforC = listC.stream()
						.filter(l -> l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
						.findFirst();
				val flexC = listC.stream()
						.filter(l -> l.pk.type == LaborWorkTypeAttr.FLEX.value)
						.findFirst();
				datas.add(buildEmploymentARow(
						//R12_1
						 null,
						//R12_2
						null,
						//R12_3
						null,
						//R12_4
						(m) + kdp004_401, 
						//R12_5
						KMK004PrintCommon.convertTime(normalC.map(f -> f.legalTime).orElse(null)),
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
						KMK004PrintCommon.convertTime(refPreTime == 0? null :flexC.map(f -> f.withinTime).orElse(null)),
						//R12_17
						KMK004PrintCommon.convertTime(flexC.map(f -> f.legalTime).orElse(null)),
						//R10_18
						KMK004PrintCommon.convertTime(flexC.map(f -> f.weekAvgTime).orElse(null)),
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
						KMK004PrintCommon.convertTime(deforC.map(f -> f.legalTime).orElse(null)),
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
	
	private MasterData buildEmploymentARow(
			String R12_1, String R12_2, String R12_3, String R12_4, String R12_5,
			String R12_6, String R12_7, String R12_8, String R12_9, String R12_10,
			String R12_11, String R12_12, String R12_13, String R12_14, String R12_15,
			String R12_16, String R12_17, String R12_18, String R12_19, String R12_20,
			String R12_21, String R12_22, String R12_23, String R12_24, String R12_25,
			String R12_26, String R12_27, String R12_28, String R12_29, String R12_30,
			String R12_31, String R12_32, String R12_33, String R12_34, String R12_35, 
			String R12_36, String R12_37,String R12_38, String R12_39) {
		
		Map<String, MasterCellData> data = new HashMap<>();
		data.put(EmploymentColumn.KMK004_185, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_185)
            .value(R12_1)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
       data.put(EmploymentColumn.KMK004_186, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_186)
            .value(R12_2)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
		data.put(EmploymentColumn.KMK004_372, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_372)
            .value(R12_3)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_373, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_373)
            .value(R12_4)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_374, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_374)
            .value(R12_5)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_375, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_375)
            .value(R12_6)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_376, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_376)
            .value(R12_7)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_377, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_377)
            .value(R12_8)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_378, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_378)
            .value(R12_9)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_379, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_379)
            .value(R12_10)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_380, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_380)
            .value(R12_11)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_381, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_381)
            .value(R12_12)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_382, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_382)
            .value(R12_13)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_383, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_383)
            .value(R12_14)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_384, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_384)
            .value(R12_15)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_385, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_385)
            .value(R12_16)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_386, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_386)
            .value(R12_17)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_387, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_387)
            .value(R12_18)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_388, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_388)
            .value(R12_19)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_389, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_389)
            .value(R12_20)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_390, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_390)
            .value(R12_21)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_391, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_391)
            .value(R12_22)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_392, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_392)
            .value(R12_23)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_393, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_393)
            .value(R12_24)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_394, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_394)
            .value(R12_25)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_395, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_395)
            .value(R12_26)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_396, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_396)
            .value(R12_27)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_397, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_397)
            .value(R12_28)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_375_1, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_375_1)
            .value(R12_29)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_376_1, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_376_1)
            .value(R12_30)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
            .build());
        data.put(EmploymentColumn.KMK004_398, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_398)
            .value(R12_31)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_399, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_399)
            .value(R12_32)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_400, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_400)
            .value(R12_33)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_377_1, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_377)
            .value(R12_34)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_378_1, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_378_1)
            .value(R12_35)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_379_1, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_379_1)
            .value(R12_36)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_380_1, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_380_1)
            .value(R12_37)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_381_1, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_381_1)
            .value(R12_38)
            .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
            .build());
        data.put(EmploymentColumn.KMK004_382_1, MasterCellData.builder()
            .columnId(EmploymentColumn.KMK004_382_1)
            .value(R12_39)
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
