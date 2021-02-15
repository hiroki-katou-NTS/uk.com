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
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
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
			+ " s.pk.cid = :cid AND s.pk.ym IN :yms"
			+ " ORDER BY s.pk.ym";

	private static final String GET_EMPLOYEE = " SELECT " + " ROW_NUMBER() OVER( "
			+ " 	PARTITION BY BSYMT_EMP_DTA_MNG_INFO.SCD "
			+ " 	ORDER BY BSYMT_EMP_DTA_MNG_INFO.SCD ASC) AS ROW_NUMBER, " 
			+ " BSYMT_EMP_DTA_MNG_INFO.SID, "
			+ " BSYMT_EMP_DTA_MNG_INFO.SCD, " 
			+ " BPSMT_PERSON.BUSINESS_NAME, "
			+ " KSHST_SHA_REG_LABOR_TIME.DAILY_TIME, " 
			+ " KSHST_SHA_REG_LABOR_TIME.WEEKLY_TIME, "
			+ " KRCST_SHA_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR, "
			+ " IIF(KRCST_SHA_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_SHA_REG_M_CAL_SET.INCLUDE_LEGAL_AGGR, NULL) AS INCLUDE_LEGAL_AGGR, "
			+ " IIF(KRCST_SHA_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_SHA_REG_M_CAL_SET.INCLUDE_HOLIDAY_AGGR, NULL) AS INCLUDE_HOLIDAY_AGGR, "
			+ " KRCST_SHA_REG_M_CAL_SET.INCLUDE_EXTRA_OT, "
			+ " IIF(KRCST_SHA_REG_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_SHA_REG_M_CAL_SET.INCLUDE_LEGAL_OT, NULL) AS INCLUDE_LEGAL_OT, "
			+ " IIF(KRCST_SHA_REG_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_SHA_REG_M_CAL_SET.INCLUDE_HOLIDAY_OT, NULL) AS INCLUDE_HOLIDAY_OT, "
			+ " KRCST_COM_FLEX_M_CAL_SET.WITHIN_TIME_USE, " 
			+ " KRCST_SHA_FLEX_M_CAL_SET.AGGR_METHOD, "
			+ " KRCST_SHA_FLEX_M_CAL_SET.SETTLE_PERIOD_MON, "
			+ " KRCST_SHA_FLEX_M_CAL_SET.SETTLE_PERIOD, "
			+ " KRCST_SHA_FLEX_M_CAL_SET.INCLUDE_HDWK, "
			+ " KRCST_SHA_FLEX_M_CAL_SET.LEGAL_AGGR_SET, "
			+ " KRCST_SHA_FLEX_M_CAL_SET.START_MONTH, "
			+ " IIF(KRCST_SHA_FLEX_M_CAL_SET.AGGR_METHOD = 0, KRCST_SHA_FLEX_M_CAL_SET.INCLUDE_OT, NULL) AS INCLUDE_OT, "
			+ " KRCST_SHA_FLEX_M_CAL_SET.INSUFFIC_SET, " 
			+ " KSHST_SHA_TRANS_LAB_TIME.DAILY_TIME AS TRANS_DAILY_TIME, "
			+ " KSHST_SHA_TRANS_LAB_TIME.WEEKLY_TIME AS TRANS_WEEKLY_TIME, "
			+ " KRCST_SHA_DEFOR_M_CAL_SET.STR_MONTH, "
			+ " KRCST_SHA_DEFOR_M_CAL_SET.PERIOD, " 
			+ " KRCST_SHA_DEFOR_M_CAL_SET.REPEAT_ATR, "
			+ " KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR, "
			+ " IIF(KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_LEGAL_AGGR, NULl) AS DEFOR_INCLUDE_LEGAL_AGGR, "
			+ " IIF(KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_HOLIDAY_AGGR, NULl) AS DEFOR_INCLUDE_HOLIDAY_AGGR, "
			+ " KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT, "
			+ " IIF(KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_LEGAL_OT, NULl) AS DEFOR_INCLUDE_LEGAL_OT, "
			+ " IIF(KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_SHA_DEFOR_M_CAL_SET.INCLUDE_HOLIDAY_OT, NULl) AS DEFOR_INCLUDE_HOLIDAY_OT "
			+ " FROM BSYMT_EMP_DTA_MNG_INFO"
			+ " LEFT JOIN KSHST_SHA_TRANS_LAB_TIME ON BSYMT_EMP_DTA_MNG_INFO.CID = KSHST_SHA_TRANS_LAB_TIME.CID "
			+ "           AND BSYMT_EMP_DTA_MNG_INFO.SID = KSHST_SHA_TRANS_LAB_TIME.SID "
			+ " LEFT JOIN KSHST_SHA_REG_LABOR_TIME ON BSYMT_EMP_DTA_MNG_INFO.CID = KSHST_SHA_REG_LABOR_TIME.CID "
			+ "           AND BSYMT_EMP_DTA_MNG_INFO.SID = KSHST_SHA_REG_LABOR_TIME.SID "
			+ " LEFT JOIN KRCST_SHA_DEFOR_M_CAL_SET ON BSYMT_EMP_DTA_MNG_INFO.CID = KRCST_SHA_DEFOR_M_CAL_SET.CID "
			+ "           AND BSYMT_EMP_DTA_MNG_INFO.SID = KRCST_SHA_DEFOR_M_CAL_SET.SID "
			+ " LEFT JOIN KRCST_SHA_FLEX_M_CAL_SET ON BSYMT_EMP_DTA_MNG_INFO.CID = KRCST_SHA_FLEX_M_CAL_SET.CID "
			+ "           AND BSYMT_EMP_DTA_MNG_INFO.SID = KRCST_SHA_FLEX_M_CAL_SET.SID "
			+ " LEFT JOIN KRCST_SHA_REG_M_CAL_SET ON BSYMT_EMP_DTA_MNG_INFO.CID = KRCST_SHA_REG_M_CAL_SET.CID "
			+ "           AND BSYMT_EMP_DTA_MNG_INFO.SID = KRCST_SHA_REG_M_CAL_SET.SID "
			+ " LEFT JOIN BPSMT_PERSON ON BSYMT_EMP_DTA_MNG_INFO.PID = BPSMT_PERSON.PID "
			+ " LEFT JOIN KRCST_COM_FLEX_M_CAL_SET ON  "
			+ "	BSYMT_EMP_DTA_MNG_INFO.CID = KRCST_COM_FLEX_M_CAL_SET.CID "
			+ " WHERE  BSYMT_EMP_DTA_MNG_INFO.CID = ? " 
			+ " AND  BSYMT_EMP_DTA_MNG_INFO.DEL_STATUS_ATR = 0 " 
			+ " ORDER BY BSYMT_EMP_DTA_MNG_INFO.SCD ASC ";

	@Override
	public List<MasterData> getEmployeeData(int startDate, int endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();

		int month = this.month();

		// int startYM = startDate * 100 + month;
		// int endYM = startDate * 100 + ((month + 11) / 12) * 100 + (month +
		// 11) % 12;

		YearMonthPeriod ymPeriod = new YearMonthPeriod(YearMonth.of(startDate, month),
				YearMonth.of(endDate, month).nextYear().previousMonth());
		String startOfWeek = getStartOfWeek(cid);

		val legalTimes = this.queryProxy().query(LEGAL_TIME_SYA, KshmtLegalTimeMSya.class)
				.setParameter("cid", cid)
				.setParameter("yms",
						ymPeriod.yearMonthsBetween().stream().map(x -> x.v().toString()).collect(Collectors.toList()))
				// .setParameter("end",endYM)
				.getList();

		try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOYEE.toString())) {
			stmt.setString(1, cid);
			NtsResultSet result = new NtsResultSet(stmt.executeQuery());
			
			result.forEach(i -> {
				datas.addAll(buildEmployeeRow(i, legalTimes, startDate, endDate, month, startOfWeek));
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return datas;
	}

	private List<MasterData> buildEmployeeRow(NtsResultRecord r, List<KshmtLegalTimeMSya> legals, int startDate,
			int endDate, int month, String startOfWeek) {
		List<MasterData> datas = new ArrayList<>();

		Integer refPreTime = r.getInt("WITHIN_TIME_USE");

		for (int y = startDate; y <= endDate; y++) {
			String sid = r.getString("SID");
			int ym = y * 100 + month;

			val normal = legals.stream().filter(
					l -> l.pk.ym == ym && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
					.findFirst();
			val defor = legals.stream().filter(l -> {
				return l.pk.ym == ym && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value;
			}).findFirst();
			val flex = legals.stream()
					.filter(l -> l.pk.ym == ym && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
					.findFirst();

			datas.add(buildEmployeeARow(
					// R10_1
					r.getInt("ROW_NUMBER") == null ? null : r.getInt("ROW_NUMBER") == 1 ? r.getString("SCD") : null,
					// R10_2
					r.getInt("ROW_NUMBER") == null ? null : r.getInt("ROW_NUMBER") == 1 ? r.getString("BUSINESS_NAME") : null,
					// R10_3
					String.valueOf(y),
					// R10_4
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_401"),
					// R10_5
					KMK004PrintCommon.convertTime(normal.isPresent() ? normal.get().legalTime : null),
					// R10_6
					KMK004PrintCommon.convertTime(r.getInt("DAILY_TIME")),
					// R10_7
					KMK004PrintCommon.convertTime(r.getInt("WEEKLY_TIME")),
					// R10_8
					KMK004PrintCommon.getExtraType(r.getInt("INCLUDE_EXTRA_AGGR")),
					// R10_9
					r.getInt("INCLUDE_EXTRA_AGGR") == null ? null : r.getInt("INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_LEGAL_AGGR"))
							: null,
					// R10_10
					r.getInt("INCLUDE_EXTRA_AGGR") == null ? null : r.getInt("INCLUDE_EXTRA_AGGR") != 0
							? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_HOLIDAY_AGGR")) : null,
					// R10_11
					KMK004PrintCommon.getExtraType(r.getInt("INCLUDE_EXTRA_OT")),
					// R10_12
					r.getInt("INCLUDE_EXTRA_AGGR") == null ? null : r.getInt("INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_LEGAL_OT"))
							: null,
					// R10_13
					r.getInt("INCLUDE_EXTRA_AGGR") == null ? null : r.getInt("INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_HOLIDAY_OT"))
							: null,
					// R10_14
					KMK004PrintCommon.getFlexType(refPreTime),
					// R10_15
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_401"),
					// R10_16
					KMK004PrintCommon.convertTime(refPreTime == 0?null:flex.isPresent() ? flex.get().withinTime : null),
					// R10_17
					KMK004PrintCommon.convertTime(flex.isPresent() ? flex.get().legalTime : null),
					// R10_18
					KMK004PrintCommon.convertTime(flex.isPresent() ? flex.get().weekAvgTime : null),
					// R10_19
					KMK004PrintCommon.getSettle(r.getInt("SETTLE_PERIOD")),
					// R10_20
					r.getInt("START_MONTH") == null ? null : r.getInt("START_MONTH").toString() + "月",
					// R10_21
					r.getInt("SETTLE_PERIOD_MON") == null ? null : r.getInt("SETTLE_PERIOD_MON") == 2 ? "2ヶ月" : "3ヶ月",
					// R10_22
					KMK004PrintCommon.getShortageTime(r.getInt("INSUFFIC_SET")),
					// R10_23
					KMK004PrintCommon.getAggTypeEmployee(r.getInt("AGGR_METHOD") == null ? 3 : r.getInt("AGGR_METHOD")),
					// R10_24
					r.getInt("AGGR_METHOD") == null ? null : r.getInt("AGGR_METHOD") == 0 ? KMK004PrintCommon.getInclude(r.getInt("INCLUDE_OT")) : null,
					// R10_25
					KMK004PrintCommon.getInclude(r.getInt("INCLUDE_HDWK")),
					// R10_26
					KMK004PrintCommon.getLegal(r.getInt("LEGAL_AGGR_SET")),
					// R10_27
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_401"),
					// R10_28
					KMK004PrintCommon.convertTime(defor.isPresent() ? defor.get().legalTime : null),
					// R10_29
					KMK004PrintCommon.convertTime(r.getInt("TRANS_DAILY_TIME")),
					// R10_30
					KMK004PrintCommon.convertTime(r.getInt("TRANS_WEEKLY_TIME")),
					// R10_31
					r.getInt("STR_MONTH") == null ? null : r.getInt("STR_MONTH") + I18NText.getText("KMK004_402"),
					// R10_32
					r.getInt("PERIOD") == null ? null : r.getInt("PERIOD") + I18NText.getText("KMK004_403"),
					// R10_33
					r.getInt("REPEAT_ATR") == null ? null : r.getInt("REPEAT_ATR") == 1 ? "○" : "-",
					// R10_34
					KMK004PrintCommon.getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_AGGR")),
					// R10_35
					r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") == null ? null : r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") != 0
							? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_AGGR")) : null,
					// R10_36
					r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") == null ? null : r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") != 0
							? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_AGGR")) : null,
					// R10_37
					KMK004PrintCommon.getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_OT")),
					// R10_38
					r.getInt("DEFOR_INCLUDE_EXTRA_OT") == null ? null : r.getInt("DEFOR_INCLUDE_EXTRA_OT") != 0
							? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_OT")) : null,
					// R10_39
					r.getInt("DEFOR_INCLUDE_EXTRA_OT") == null ? null : r.getInt("DEFOR_INCLUDE_EXTRA_OT") != 0
							? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_OT")) : null));

			int nextYm = y * 100 + month + 1;
			val normalN = legals.stream().filter(l -> l.pk.ym == nextYm && l.pk.sid.equals(sid)
					&& l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value).findFirst();
			val deforN = legals.stream().filter(
					l -> l.pk.ym == nextYm && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
					.findFirst();
			val flexN = legals.stream()
					.filter(l -> l.pk.ym == nextYm && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
					.findFirst();
			
			
			
//			 buil arow = month + 1
			 datas.add(buildEmployeeARowChild(
			 // R10_4
			 ((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
			 // R10_5
			 KMK004PrintCommon.convertTime(normalN.isPresent() ? normalN.get().legalTime : null),
			 // R10_15
			 ((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
			 // R10_16
			 KMK004PrintCommon.convertTime(refPreTime == 0?null:flexN.isPresent() ? flexN.get().withinTime : null),
			 // R10_17
			 KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().legalTime : null),
			 // R10_18
			 KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().weekAvgTime : null),
			 // R10_27
			 ((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
			 // R10_28
			 KMK004PrintCommon.convertTime(deforN.isPresent() ? deforN.get().legalTime : null)));
			
			 
			 for (int i = 1; i < 11; i++) {
				int m = (month + i) % 12 + 1;
				int currentYm = y * 100 + ((month + i) / 12) * 100 + m;
				val normalC = legals.stream()
						.filter(l -> l.pk.ym == currentYm && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
						.findFirst();
				val deforC = legals.stream()
						.filter(l -> l.pk.ym == currentYm && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
						.findFirst();
				val flexC = legals.stream()
						.filter(l -> l.pk.ym == currentYm && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
						.findFirst();
				datas.add(buildEmployeeARow(
					 
					// R10_1
					null,
					// R10_2
					null,
					// R10_3
					null,
					// R10_4
					(m) + I18NText.getText("KMK004_401"),
					// R10_5
					KMK004PrintCommon.convertTime(normalC.isPresent() ? normalC.get().legalTime : null),
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
					(m) + I18NText.getText("KMK004_401"),
					// R10_16
					KMK004PrintCommon.convertTime(refPreTime == 0?null:flexC.isPresent() ? flexC.get().withinTime : null),
					// R10_17
					KMK004PrintCommon.convertTime(flexC.isPresent() ? flexC.get().legalTime : null),
					// R10_18
					KMK004PrintCommon.convertTime(flexC.isPresent() ? flexC.get().weekAvgTime : null),
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
					(m) + I18NText.getText("KMK004_401"),
					// R10_28
					KMK004PrintCommon.convertTime(deforC.isPresent() ? deforC.get().legalTime : null),
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
					 
					 
//			 // R10_1
//			 null,
//			 // R10_2
//			 null,
//			 // R10_3
//			 null,
//			 // R10_4 R10_5
//			 (m) + I18NText.getText("KMK004_401"),
//			 // R10_6
//			 KMK004PrintCommon.convertTime(normalC.isPresent() ? normalC.get().legalTime : 0),
//			 // R10_8
//			 null,
//			 // R10_9
//			 null,
//			 // R10_11
//			 null,
//			 // R10_12
//			 null,
//			 // R10_13
//			 null,
//			 // R10_14
//			 null,
//			 // R10_15
//			 null,
//			 // R10_16
//			 null,
//			 // R10_17
//			 null,
//			 // R10_18
//			 null,
//			 // R10_19 R10_20
//			 (m) + I18NText.getText("KMK004_176"),
//			 // R10_21
//			 refPreTime != null && refPreTime == 0 ? (flexC.isPresent() ?
//			 String.valueOf(flexC.get().withinTime) : null) : null,
//			 // R10_22
//			 KMK004PrintCommon.convertTime(flexC.isPresent() ?
//			 flexC.get().legalTime : 0),
//			 // R10_23
//			 null,
//			 // R10_24
//			 null,
//			 // R10_25
//			 null,
//			 // R10_26 R10_27
//			 (m) + I18NText.getText("KMK004_176"),
//			 // R10_28
//			 KMK004PrintCommon.convertTime(deforC.isPresent() ?
//			 deforC.get().legalTime : 0),
//			 // R10_30
//			 null,
//			 // R10_32
//			 null,
//			 // R10_33
//			 null,
//			 // R10_34 35
//			 null,
//			 // R10_36 37
//			 null,
//			 // R10_38
//			 null,
//			 // R14_39
//			 null,
//			 // R14_40
//			 null,
//			 // R14_41
//			 null,
//			 // R14_42
//			 null,
//			 // R14_43
//			 null,
//			 // R14_44
//			 null,
//			 null,
//			 null,
//			 null,
//			 null));
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

	 private MasterData buildEmployeeARowChild(String value4, String value5,
			 String value15, String value16, String value17,
			 String value18, String value27, String value28) {
	
	 Map<String, MasterCellData> data = new HashMap<>();
	 
	 data.put(EmployeeColumn.KMK004_183, MasterCellData.builder().columnId(EmployeeColumn.KMK004_183).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_184, MasterCellData.builder().columnId(EmployeeColumn.KMK004_184).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_372, MasterCellData.builder().columnId(EmployeeColumn.KMK004_372).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_373, MasterCellData.builder().columnId(EmployeeColumn.KMK004_373).value(value4)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_374, MasterCellData.builder().columnId(EmployeeColumn.KMK004_374).value(value5)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_375, MasterCellData.builder().columnId(EmployeeColumn.KMK004_375).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_376, MasterCellData.builder().columnId(EmployeeColumn.KMK004_376).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_377, MasterCellData.builder().columnId(EmployeeColumn.KMK004_377).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_378, MasterCellData.builder().columnId(EmployeeColumn.KMK004_378).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_379, MasterCellData.builder().columnId(EmployeeColumn.KMK004_379).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_380, MasterCellData.builder().columnId(EmployeeColumn.KMK004_380).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_381, MasterCellData.builder().columnId(EmployeeColumn.KMK004_381).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_382, MasterCellData.builder().columnId(EmployeeColumn.KMK004_382).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_383, MasterCellData.builder().columnId(EmployeeColumn.KMK004_383).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_384, MasterCellData.builder().columnId(EmployeeColumn.KMK004_384).value(value15)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_385, MasterCellData.builder().columnId(EmployeeColumn.KMK004_385).value(value16)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_386, MasterCellData.builder().columnId(EmployeeColumn.KMK004_386).value(value17)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_387, MasterCellData.builder().columnId(EmployeeColumn.KMK004_387).value(value18)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_388, MasterCellData.builder().columnId(EmployeeColumn.KMK004_388).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_389, MasterCellData.builder().columnId(EmployeeColumn.KMK004_389).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_390, MasterCellData.builder().columnId(EmployeeColumn.KMK004_390).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_391, MasterCellData.builder().columnId(EmployeeColumn.KMK004_391).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_392, MasterCellData.builder().columnId(EmployeeColumn.KMK004_392).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_393, MasterCellData.builder().columnId(EmployeeColumn.KMK004_393).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_394, MasterCellData.builder().columnId(EmployeeColumn.KMK004_394).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_395, MasterCellData.builder().columnId(EmployeeColumn.KMK004_395).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_396, MasterCellData.builder().columnId(EmployeeColumn.KMK004_396).value(value27)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_397, MasterCellData.builder().columnId(EmployeeColumn.KMK004_397).value(value28)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_375_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_375_1).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_376_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_376_1).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
		data.put(EmployeeColumn.KMK004_398, MasterCellData.builder().columnId(EmployeeColumn.KMK004_398).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_399, MasterCellData.builder().columnId(EmployeeColumn.KMK004_399).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_400, MasterCellData.builder().columnId(EmployeeColumn.KMK004_400).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_377_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_377_1).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_378_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_378_1).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_379_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_379_1).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_380_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_380_1).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_381_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_381_1).value(null)
				.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmployeeColumn.KMK004_382_1, MasterCellData.builder().columnId(EmployeeColumn.KMK004_382_1).value(null)
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
		int month = 1;
		Query monthQuery = this.getEntityManager().createNativeQuery(GET_EXPORT_MONTH.toString()).setParameter("cid",
				cid);
		List<?> data = monthQuery.getResultList();
		if (data.size() == 0) {
			month = 1;
		} else {
			month = Integer.valueOf(data.get(0).toString());
		}
		return month;
	}

}
