package nts.uk.file.at.infra.setworkinghoursanddays;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.Query;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
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
			+ " s.pk.cid = :cid AND s.pk.ym >= :start AND s.pk.ym <= :end"
			+ " ORDER BY s.pk.ym";
	
	private static final String GET_EMPLOYEE = " SELECT "  
			+ " ROW_NUMBER() OVER( "
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
			+ " KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME, "
			+ " KRCST_SHA_FLEX_M_CAL_SET.AGGR_METHOD, "
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
			+ " FROM KSHST_SHA_TRANS_LAB_TIME "
			+ " INNER JOIN KSHST_SHA_REG_LABOR_TIME ON  "
			+ " 	KSHST_SHA_TRANS_LAB_TIME.CID = KSHST_SHA_REG_LABOR_TIME.CID "
			+ " 	AND KSHST_SHA_TRANS_LAB_TIME.SID = KSHST_SHA_REG_LABOR_TIME.SID "
			+ " INNER JOIN KRCST_SHA_DEFOR_M_CAL_SET ON "
			+ " 	KSHST_SHA_TRANS_LAB_TIME.CID = KRCST_SHA_DEFOR_M_CAL_SET.CID "
			+ " 	AND KSHST_SHA_TRANS_LAB_TIME.SID = KRCST_SHA_DEFOR_M_CAL_SET.SID "
			+ " INNER JOIN KRCST_SHA_FLEX_M_CAL_SET ON  "
			+ " 	KSHST_SHA_TRANS_LAB_TIME.CID = KRCST_SHA_FLEX_M_CAL_SET.CID "
			+ " 	AND KSHST_SHA_TRANS_LAB_TIME.SID = KRCST_SHA_FLEX_M_CAL_SET.SID "
			+ " INNER JOIN KRCST_SHA_REG_M_CAL_SET ON  "
			+ " 	KSHST_SHA_TRANS_LAB_TIME.CID = KRCST_SHA_REG_M_CAL_SET.CID "
			+ " 	AND KSHST_SHA_TRANS_LAB_TIME.SID = KRCST_SHA_REG_M_CAL_SET.SID "
			+ " INNER JOIN BSYMT_EMP_DTA_MNG_INFO ON "
			+ " 	KSHST_SHA_TRANS_LAB_TIME.CID = BSYMT_EMP_DTA_MNG_INFO.CID "
			+ " 	AND KSHST_SHA_TRANS_LAB_TIME.SID = BSYMT_EMP_DTA_MNG_INFO.SID "
			+ " INNER JOIN BPSMT_PERSON ON "
			+ " 	BSYMT_EMP_DTA_MNG_INFO.PID = BPSMT_PERSON.PID "
			+ " LEFT JOIN KSHST_FLX_GET_PRWK_TIME ON  "
			+ "	KSHST_SHA_TRANS_LAB_TIME.CID = KSHST_FLX_GET_PRWK_TIME.CID "
			+ " WHERE  KSHST_SHA_TRANS_LAB_TIME.CID = ? "
			+ " ORDER BY BSYMT_EMP_DTA_MNG_INFO.SCD ASC " ;

	@Override
	public List<MasterData> getEmployeeData(int startDate, int endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		
		String startOfWeek = getStartOfWeek(cid);
		val legalTimes = this.queryProxy().query(LEGAL_TIME_SYA, KshmtLegalTimeMSya.class)
			.setParameter("cid", cid)
			.setParameter("start", startDate * 100 + 1)
			.setParameter("end", endDate * 100 + 12)
			.getList();
		
		try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOYEE.toString())) {
//			stmt.setInt(1, startDate);
//			stmt.setInt(2, endDate);
			stmt.setString(1, cid);
			NtsResultSet result = new NtsResultSet(stmt.executeQuery());
			int month = this.month();
			result.forEach(i -> {
				datas.addAll(buildEmployeeRow(i, legalTimes, startDate, endDate, month, startOfWeek));
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	private List<MasterData> buildEmployeeRow(NtsResultRecord r, List<KshmtLegalTimeMSya> legals, int startDate, int endDate, int month, String startOfWeek) {
		List<MasterData> datas = new ArrayList<>();

		Integer refPreTime = r.getInt("REFERENCE_PRED_TIME");
		
		for (int y = startDate; y <= endDate; y++) {
			String sid = r.getString("SID");
			int ym = y *100 + month;
			
			val normal = legals.stream()
					.filter(l -> l.pk.ym == ym && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
					.findFirst();
			val defor = legals.stream()
					.filter(l -> {
						return l.pk.ym == ym && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value;
					})
					.findFirst();
			val flex = legals.stream()
					.filter(l -> l.pk.ym == ym && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
					.findFirst();
			
			datas.add(buildEmployeeARow(
					String.valueOf(y),
					// R10_1
					r.getInt("ROW_NUMBER") == 1 ? r.getString("SCD") : null,
					// R10_2
					r.getInt("ROW_NUMBER") == 1 ? r.getString("BUSINESS_NAME") : null,
					// R10_3
					// R10_4 R10_5
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"),
					// R10_6
					KMK004PrintCommon.convertTime(normal.isPresent() ? normal.get().legalTime : 0),
					// R10_7
					I18NText.getText("KMK004_177"),
					// R10_9
					KMK004PrintCommon.convertTime(r.getInt("DAILY_TIME")),
					// R10_11
					startOfWeek,
					// R10_12
					KMK004PrintCommon.getExtraType(r.getInt("INCLUDE_EXTRA_AGGR")),
					// R10_13
					r.getInt("INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_LEGAL_AGGR")) : null,
					// R10_14
					r.getInt("INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_HOLIDAY_AGGR")) : null,
					// R10_15
							KMK004PrintCommon.getExtraType(r.getInt("INCLUDE_EXTRA_OT")),
					// R10_16
					r.getInt("INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_LEGAL_OT")) : null,
					// R10_17
					r.getInt("INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_HOLIDAY_OT")) : null,
					// R10_18
					refPreTime == null? null :KMK004PrintCommon.getFlexType(refPreTime),
					// R10_19 R10_20
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"),
					// R10_21
					refPreTime != null && refPreTime == 0 ? (flex.isPresent() ? String.valueOf(flex.get().withinTime) : null) : null,
					// R10_22
							KMK004PrintCommon.convertTime(flex.isPresent() ? flex.get().legalTime : 0),
					// R10_23
							KMK004PrintCommon.getAggType(r.getInt("AGGR_METHOD")),
					// R10_24
					r.getInt("AGGR_METHOD") == 0 ? KMK004PrintCommon.getInclude(r.getInt("INCLUDE_OT")) : null,
					// R10_25
							KMK004PrintCommon.getShortageTime(r.getInt("INSUFFIC_SET")),
					// R10_26 R10_27
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"),
					// R10_28
					KMK004PrintCommon.convertTime(defor.isPresent() ? defor.get().legalTime : 0),
					// R10_29
					I18NText.getText("KMK004_177"),
					// R10_31
					KMK004PrintCommon.convertTime(r.getInt("TRANS_DAILY_TIME")),
					// R10_33
					startOfWeek,
					// R10_34 35
					r.getInt("STR_MONTH") + I18NText.getText("KMK004_179"),
					// R10_36 37
					r.getInt("PERIOD") + I18NText.getText("KMK004_180"),
					// R10_38
					r.getInt("REPEAT_ATR") == 1 ? "○" : "-",
					// R14_39
							KMK004PrintCommon.getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_AGGR")),
					// R14_40
					r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_AGGR")) : null,
					// R14_41
					r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_AGGR")) : null,
					// R14_42
							KMK004PrintCommon.getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_OT")),
					// R14_43
					r.getInt("DEFOR_INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_OT")) : null,
					// R14_44
					r.getInt("DEFOR_INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_OT")) : null));

			int nextYm = y *100 + month + 1;
			val normalN = legals.stream()
					.filter(l -> l.pk.ym == nextYm && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
					.findFirst();
			val deforN = legals.stream()
					.filter(l -> l.pk.ym == nextYm && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
					.findFirst();
			val flexN = legals.stream()
					.filter(l -> l.pk.ym == nextYm && l.pk.sid.equals(sid) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
					.findFirst();
			// buil arow = month + 1
			datas.add(buildEmployeeARow(
				// R10_4 R10_5
				((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"),
				// R10_6
				KMK004PrintCommon.convertTime(normalN.isPresent() ? normalN.get().legalTime : 0),
				// R10_8
				I18NText.getText("KMK004_178"),
				// R10_9
				KMK004PrintCommon.convertTime(r.getInt("WEEKLY_TIME")),
				// R10_19 R10_20
				((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"),
				// R10_21
				refPreTime != null && refPreTime == 0 ? (flexN.isPresent() ? String.valueOf(flexN.get().withinTime) : null) : null,
				// R10_22
						KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().legalTime : 0),
				// R10_26 R10_27
				((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"),
				// R10_28
				KMK004PrintCommon.convertTime(deforN.isPresent() ? deforN.get().legalTime : 0),
				// R10_30
				I18NText.getText("KMK004_178"),
				// R10_32
				KMK004PrintCommon.convertTime(r.getInt("TRANS_WEEKLY_TIME"))));
			
			// buil month remain
			for (int i = 1; i < 11; i++) {
				int m = (month + i) % 12 + 1;
				int currentYm = y *100 + m;
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
						// R10_4 R10_5
						(m) + I18NText.getText("KMK004_176"),
						// R10_6
						KMK004PrintCommon.convertTime(normalC.isPresent() ? normalC.get().legalTime : 0),
						// R10_8
						null,
						// R10_9
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
						null,
						// R10_16
						null,
						// R10_17
						null,
						// R10_18
						null,
						// R10_19 R10_20
						(m) + I18NText.getText("KMK004_176"),
						// R10_21
						refPreTime != null && refPreTime == 0 ? (flexC.isPresent() ? String.valueOf(flexC.get().withinTime) : null) : null,
								// R10_22
								KMK004PrintCommon.convertTime(flexC.isPresent() ? flexC.get().legalTime : 0),
						// R10_23
						null,
						// R10_24
						null,
						// R10_25
						null,
						// R10_26 R10_27
						(m) + I18NText.getText("KMK004_176"),
						// R10_28
						KMK004PrintCommon.convertTime(deforC.isPresent() ? deforC.get().legalTime : 0),
						// R10_30
						null,
						// R10_32
						null,
						// R10_33
						null,
						// R10_34 35
						null,
						// R10_36 37
						null,
						// R10_38
						null,
						// R14_39
						null,
						// R14_40
						null,
						// R14_41
						null,
						// R14_42
						null,
						// R14_43
						null,
						// R14_44
						null));
			}
		}
		return datas;
	}
	
	private MasterData buildEmployeeARow(
			String value1, String value2, String value3, String value4, String value5,
			String value6, String value7, String value8, String value9, String value10,
			String value11, String value12, String value13, String value14, String value15,
			String value16, String value17, String value18, String value19, String value20,
			String value21, String value22, String value23, String value24, String value25,
			String value26, String value27, String value28, String value29, String value30,
			String value31, String value32, String value33, String value34, String value35) {
		
		Map<String, MasterCellData> data = new HashMap<>();
		
		data.put(EmployeeColumn.KMK004_154, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_154)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_183, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_183)
                .value(value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_184, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_184)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_155, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_155)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156)
                .value(value5)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_157, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_157)
                .value(value6)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156_1)
                .value(value7)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_158, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_158)
                .value(value8)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_159, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_159)
                .value(value9)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_160, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_160)
                .value(value10)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_161, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_161)
                .value(value11)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_162, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_162)
                .value(value12)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_163, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_163)
                .value(value13)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_164, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_164)
                .value(value14)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_165, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_165)
                .value(value15)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_166, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_166)
                .value(value16)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_167, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_167)
                .value(value17)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156_2, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156_2)
                .value(value18)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_168, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_168)
                .value(value19)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_169, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_169)
                .value(value20)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_170, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_170)
                .value(value21)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_171, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_171)
                .value(value22)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156_3, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156_3)
                .value(value23)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_172, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_172)
                .value(value24)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156_4, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156_4)
                .value(value25)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_158_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_158_1)
                .value(value26)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_173, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_173)
                .value(value27)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_174, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_174)
                .value(value28)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_175, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_175)
                .value(value29)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_159_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_159_1)
                .value(value30)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_160_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_160_1)
                .value(value31)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_161_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_161_1)
                .value(value32)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_162_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_162_1)
                .value(value33)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_163_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_163_1)
                .value(value34)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_164_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_164_1)
                .value(value35)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		
		return MasterData.builder().rowData(data).build();
	}
	
	private MasterData buildEmployeeARow(String value4, String value5,
			String value6, String value7, String value16, String value17, 
			String value18, String value22, String value23, String value24, String value25) {
		
		Map<String, MasterCellData> data = new HashMap<>();
		
		data.put(EmployeeColumn.KMK004_154, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_154)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_183, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_183)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_184, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_184)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_155, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_155)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156)
                .value(value5)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_157, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_157)
                .value(value6)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156_1)
                .value(value7)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_158, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_158)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_159, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_159)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_160, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_160)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_161, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_161)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_162, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_162)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_163, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_163)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_164, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_164)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_165, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_165)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_166, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_166)
                .value(value16)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_167, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_167)
                .value(value17)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156_2, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156_2)
                .value(value18)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_168, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_168)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_169, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_169)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_170, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_170)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_171, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_171)
                .value(value22)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156_3, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156_3)
                .value(value23)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_172, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_172)
                .value(value24)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_156_4, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_156_4)
                .value(value25)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_158_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_158_1)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_173, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_173)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_174, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_174)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		data.put(EmployeeColumn.KMK004_175, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_175)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_159_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_159_1)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_160_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_160_1)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_161_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_161_1)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_162_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_162_1)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_163_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_163_1)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(EmployeeColumn.KMK004_164_1, MasterCellData.builder()
                .columnId(EmployeeColumn.KMK004_164_1)
                .value(null)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		
		return MasterData.builder().rowData(data).build();
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
		List data = monthQuery.getResultList();
		if (data.size() == 0) {
			month = 1;
		} else {
			month = Integer.valueOf(data.get(0).toString());
		}
		return month;
	}

}
