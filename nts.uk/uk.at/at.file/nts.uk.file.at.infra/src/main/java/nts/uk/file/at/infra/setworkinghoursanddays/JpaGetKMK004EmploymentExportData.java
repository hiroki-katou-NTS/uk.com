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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshmtLegalTimeMEmp;
import nts.uk.ctx.at.shared.infra.entity.workrule.week.KsrmtWeekRuleMng;
import nts.uk.file.at.app.export.setworkinghoursanddays.EmploymentColumn;
import nts.uk.file.at.app.export.setworkinghoursanddays.GetKMK004EmploymentExportRepository;
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
public class JpaGetKMK004EmploymentExportData extends JpaRepository implements GetKMK004EmploymentExportRepository {
	
	private static final String GET_EXPORT_MONTH = "SELECT m.MONTH_STR FROM BCMMT_COMPANY m WHERE m.CID = ?cid";
	
	private static final String LEGAL_TIME_EMP = "SELECT s FROM KshmtLegalTimeMEmp s WHERE "
			+ " s.pk.cid = :cid AND s.pk.ym >= :start AND s.pk.ym <= :end"
			+ " ORDER BY s.pk.ym";	
	
	private static final String GET_EMPLOYMENT = "SELECT "
											+" ROW_NUMBER() OVER(PARTITION BY BSYMT_EMPLOYMENT.CODE ORDER BY IIF(BSYMT_EMPLOYMENT.NAME IS NOT NULL,0,1), BSYMT_EMPLOYMENT.CODE ASC) AS ROW_NUMBER_CODE, "
											+" BSYMT_EMPLOYMENT.CODE AS CODE,  "
											+" IIF(BSYMT_EMPLOYMENT.NAME IS NOT NULL, BSYMT_EMPLOYMENT.NAME, 'マスタ未登録') AS NAME, "
											+" KSHST_EMP_REG_LABOR_TIME.DAILY_TIME, "
											+" KSHST_EMP_REG_LABOR_TIME.WEEKLY_TIME, "
											+" KRCST_EMP_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR AS INCLUDE_EXTRA_AGGR, "
											+" IIF (KRCST_EMP_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_EMP_REG_M_CAL_SET.INCLUDE_LEGAL_AGGR , NUll) AS INCLUDE_LEGAL_AGGR, "
											+" IIF (KRCST_EMP_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_EMP_REG_M_CAL_SET.INCLUDE_HOLIDAY_AGGR , NUll) AS INCLUDE_HOLIDAY_AGGR, "
											+" KRCST_EMP_REG_M_CAL_SET.INCLUDE_EXTRA_OT, "
											+" IIF (KRCST_EMP_REG_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_EMP_REG_M_CAL_SET.INCLUDE_LEGAL_OT , NUll) AS INCLUDE_LEGAL_OT, "
											+" IIF (KRCST_EMP_REG_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_EMP_REG_M_CAL_SET.INCLUDE_HOLIDAY_OT , NUll) AS INCLUDE_HOLIDAY_OT, "
											+ "KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME, "
											+" KRCST_EMP_FLEX_M_CAL_SET.AGGR_METHOD, "
											+" IIF( KRCST_EMP_FLEX_M_CAL_SET.AGGR_METHOD = 0, KRCST_EMP_FLEX_M_CAL_SET.INCLUDE_OT , NULL ) AS INCLUDE_OT, "
											+" KRCST_EMP_FLEX_M_CAL_SET.INSUFFIC_SET, "
											+" KSHST_EMP_TRANS_LAB_TIME.DAILY_TIME AS LAR_DAILY_TIME, "
											+" KSHST_EMP_TRANS_LAB_TIME.WEEKLY_TIME AS LAR_WEEKLY_TIME, "
											+" KRCST_EMP_DEFOR_M_CAL_SET.STR_MONTH, "
											+" KRCST_EMP_DEFOR_M_CAL_SET.PERIOD, "
											+" KRCST_EMP_DEFOR_M_CAL_SET.REPEAT_ATR, "
											+" KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR, "
											+" IIF(KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_LEGAL_AGGR , NUll ) AS DEFOR_INCLUDE_LEGAL_AGGR, "
											+" IIF(KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_HOLIDAY_AGGR , NUll ) AS DEFOR_INCLUDE_HOLIDAY_AGGR, "
											+" KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT, "
											+" IIF(KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_LEGAL_OT , NUll ) AS DEFOR_INCLUDE_LEGAL_OT, "
											+" IIF(KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_EMP_DEFOR_M_CAL_SET.INCLUDE_HOLIDAY_OT , NUll ) AS DEFOR_INCLUDE_HOLIDAY_OT "
											+" 	FROM KSHST_EMP_TRANS_LAB_TIME "
											+" 		INNER JOIN KSHST_EMP_REG_LABOR_TIME ON KSHST_EMP_TRANS_LAB_TIME.CID = KSHST_EMP_REG_LABOR_TIME.CID "
											+" 			AND KSHST_EMP_TRANS_LAB_TIME.EMP_CD = KSHST_EMP_REG_LABOR_TIME.EMP_CD "
											+" 		INNER JOIN KRCST_EMP_DEFOR_M_CAL_SET ON KSHST_EMP_TRANS_LAB_TIME.CID = KRCST_EMP_DEFOR_M_CAL_SET.CID "
											+" 			AND KSHST_EMP_TRANS_LAB_TIME.EMP_CD = KRCST_EMP_DEFOR_M_CAL_SET.EMP_CD "
											+" 		INNER JOIN KRCST_EMP_FLEX_M_CAL_SET ON KSHST_EMP_TRANS_LAB_TIME.CID = KRCST_EMP_FLEX_M_CAL_SET.CID "
											+" 			AND KSHST_EMP_TRANS_LAB_TIME.EMP_CD = KRCST_EMP_FLEX_M_CAL_SET.EMP_CD "
											+" 		INNER JOIN KRCST_EMP_REG_M_CAL_SET ON KSHST_EMP_TRANS_LAB_TIME.CID = KRCST_EMP_REG_M_CAL_SET.CID "
											+" 			AND KSHST_EMP_TRANS_LAB_TIME.EMP_CD = KRCST_EMP_REG_M_CAL_SET.EMP_CD "
											+" 		LEFT JOIN BSYMT_EMPLOYMENT ON KSHST_EMP_TRANS_LAB_TIME.CID = BSYMT_EMPLOYMENT.CID  "
											+" 			AND KSHST_EMP_TRANS_LAB_TIME.EMP_CD = BSYMT_EMPLOYMENT.CODE "
											+" 		LEFT JOIN KSHST_FLX_GET_PRWK_TIME ON KSHST_EMP_TRANS_LAB_TIME.CID = KSHST_FLX_GET_PRWK_TIME.CID "
											+" WHERE KSHST_EMP_TRANS_LAB_TIME.CID = ?  "
											+" ORDER BY IIF(BSYMT_EMPLOYMENT.NAME IS NOT NULL,0,1), KSHST_EMP_TRANS_LAB_TIME.EMP_CD";

	@Override
	public List<MasterData> getEmploymentExportData(int startDate, int endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		
		String startOfWeek = getStartOfWeek(cid);

		val legalTimes = this.queryProxy().query(LEGAL_TIME_EMP, KshmtLegalTimeMEmp.class)
				.setParameter("cid", cid)
				.setParameter("start", startDate * 100 + 1)
				.setParameter("end", endDate * 100 + 12)
				.getList();
			
		try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOYMENT.toString())) {
//			stmt.setInt(1, startDate);
//			stmt.setInt(2, endDate);
			stmt.setString(1, cid);
			NtsResultSet result = new NtsResultSet(stmt.executeQuery());
			int month = this.month();
			result.forEach(i -> {
				datas.addAll(buildEmploymentRow(i, legalTimes, startDate, endDate, month, startOfWeek));
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		List data = monthQuery.getResultList();
		if (data.size() == 0) {
			month = 1;
		} else {
			month = Integer.valueOf(data.get(0).toString());
		}
		return month;
	}
	
	private List<MasterData> buildEmploymentRow(NtsResultRecord r, List<KshmtLegalTimeMEmp> legals, int startDate, int endDate, int month, String startOfWeek) {
		List<MasterData> datas = new ArrayList<>();

		Integer refPreTime = r.getInt("REFERENCE_PRED_TIME");
		
		for (int y = startDate; y <= endDate; y++) {
			String employmentCode = r.getString("CODE");
			int ym = y *100 + month;
			
			val normal = legals.stream()
					.filter(l -> l.pk.ym == ym && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
					.findFirst();
			val defor = legals.stream()
					.filter(l -> {
						return l.pk.ym == ym && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value;
					})
					.findFirst();
			val flex = legals.stream()
					.filter(l -> l.pk.ym == ym && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
					.findFirst();
			
			datas.add(buildWorkPlaceARow(
					//R12_3
					String.valueOf(y),
					//R12_1
					r.getInt("ROW_NUMBER_CODE") == 1 ? r.getString("CODE") : null,
					//R12_2
					r.getInt("ROW_NUMBER_CODE") == 1 ? r.getString("NAME") : null,
					//R12_4 R12_5
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"), 
					//R12_6
					KMK004PrintCommon.convertTime(normal.isPresent() ? normal.get().legalTime : 0),
					//R12_7
					I18NText.getText("KMK004_177"),
					//R12_9
					KMK004PrintCommon.convertTime(r.getInt("DAILY_TIME")),
					//R12_11
					startOfWeek,
					//R12_12
					KMK004PrintCommon.getExtraType(r.getInt("INCLUDE_EXTRA_AGGR")),
					//R12_13
					r.getInt("INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_LEGAL_AGGR")) : null, 
					//R12_14		
					r.getInt("INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_HOLIDAY_AGGR")) : null, 
					//R12_15		
							KMK004PrintCommon.getExtraType(r.getInt("INCLUDE_EXTRA_OT")),
					//R12_16
					r.getInt("INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_LEGAL_OT")) : null, 
					//R12_17
					r.getInt("INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_HOLIDAY_OT")) : null, 
					//R12_18
					refPreTime == null? null : KMK004PrintCommon.getFlexType(r.getInt("REFERENCE_PRED_TIME")),
					//R12_19 R12_20
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"),
					//R12_21
					refPreTime != null && refPreTime == 0 ? (flex.isPresent() ? String.valueOf(flex.get().withinTime) : null) : null,
					// R10_22
							KMK004PrintCommon.convertTime(flex.isPresent() ? flex.get().legalTime : 0),
					//R12_23
							KMK004PrintCommon.getAggType(r.getInt("AGGR_METHOD")),
					//R12_24
					r.getInt("AGGR_METHOD") == 0 ? KMK004PrintCommon.getInclude(r.getInt("INCLUDE_OT")) : null,
					//R12_25
							KMK004PrintCommon.getShortageTime(r.getInt("INSUFFIC_SET")), 
					//R12_26 R12_27
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"),
					//R12_28
					KMK004PrintCommon.convertTime(defor.isPresent() ? defor.get().legalTime : 0),
					//R12_29
					I18NText.getText("KMK004_177"), 
					//R12_31
					KMK004PrintCommon.convertTime(r.getInt("LAR_DAILY_TIME")),
					//R12_33
					startOfWeek,
					//R12_34
					r.getInt("STR_MONTH") + I18NText.getText("KMK004_179"), 
					//R12_35
					r.getInt("PERIOD") + I18NText.getText("KMK004_180"), 
					//R12_38
					r.getInt("REPEAT_ATR") == 1 ? "○" : "-", 
					//R8_39
							KMK004PrintCommon.getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_AGGR")),
					//R8_40
					r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_AGGR")) : null,
					//R8_41
					r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_AGGR")) : null,
					//R8_42
							KMK004PrintCommon.getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_OT")),
					//R8_43
					r.getInt("DEFOR_INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_OT")) : null,
					//R8_44
					r.getInt("DEFOR_INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_OT")) : null
					));

			int nextYm = y *100 + month + 1;
			val normalN = legals.stream()
					.filter(l -> l.pk.ym == nextYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
					.findFirst();
			val deforN = legals.stream()
					.filter(l -> l.pk.ym == nextYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
					.findFirst();
			val flexN = legals.stream()
					.filter(l -> l.pk.ym == nextYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
					.findFirst();
			// buil arow = month + 1
			datas.add(buildEmploymentARow(
					//R12_1
					null,
					//R12_2
					null,
					//R12_3
					null,
					//R12_4 R12_5
					((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"), 
					//R12_6
					KMK004PrintCommon.convertTime(normalN.isPresent() ? normalN.get().legalTime : 0),
					//R12_8
					I18NText.getText("KMK004_178"),
					//R12_9
					KMK004PrintCommon.convertTime(r.getInt("WEEKLY_TIME")),
					//R12_11
					null,
					//R12_12
					null,
					//R12_13
					null, 
					//R12_14		
					null, 
					//R12_15		
					null,
					//R12_16
					null, 
					//R12_17
					null, 
					//R12_18
					null,
					//R12_19 R12_20
					((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"),
					//R12_21
					refPreTime != null && refPreTime == 0 ? (flexN.isPresent() ? String.valueOf(flexN.get().withinTime) : null) : null,
					// R10_22
							KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().legalTime : 0),
					//R12_23
					null,
					//R12_24
					null,
					//R12_25
					null, 
					//R12_26 R12_27
					((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"),
					//R12_28
					KMK004PrintCommon.convertTime(deforN.isPresent() ? deforN.get().legalTime : 0),
					//R12_30
					I18NText.getText("KMK004_178"), 
					//R12_32
					KMK004PrintCommon.convertTime(r.getInt("LAR_WEEKLY_TIME")),
					//R12_33
					null,
					//R12_34
					null, 
					//R12_35
					null, 
					//R12_38
					null, 
					//R8_39
					null,
					//R8_40
					null,
					//R8_41
					null,
					//R8_42
					null,
					//R8_43
					null,
					//R8_44
					null
					));
			
			// buil month remain
			for (int i = 1; i < 11; i++) {
				int m = (month + i) % 12 + 1;
				int currentYm = y *100 + m;
				val normalC = legals.stream()
						.filter(l -> l.pk.ym == currentYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.REGULAR_LABOR.value)
						.findFirst();
				val deforC = legals.stream()
						.filter(l -> l.pk.ym == currentYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.DEFOR_LABOR.value)
						.findFirst();
				val flexC = legals.stream()
						.filter(l -> l.pk.ym == currentYm && l.pk.empCD.equals(employmentCode) && l.pk.type == LaborWorkTypeAttr.FLEX.value)
						.findFirst();
				datas.add(buildEmploymentARow(
						//R12_1
						null,
						//R12_2
						null,
						//R12_3
						null,
						//R12_4 R12_5
						(m) + I18NText.getText("KMK004_176"), 
						//R12_6
						KMK004PrintCommon.convertTime(normalC.isPresent() ? normalC.get().legalTime : 0),
						//R12_8
						null,
						//R12_9
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
						null,
						//R12_16
						null, 
						//R12_17
						null, 
						//R12_18
						null,
						//R12_19 R12_20
						(m) + I18NText.getText("KMK004_176"),
						//R12_21
						refPreTime != null && refPreTime == 0 ? (flexC.isPresent() ? String.valueOf(flexC.get().withinTime) : null) : null,
						// R10_22
								KMK004PrintCommon.convertTime(flexC.isPresent() ? flexC.get().legalTime : 0),
						//R12_23
						null,
						//R12_24
						null,
						//R12_25
						null, 
						//R12_26 R12_27
						(m) + I18NText.getText("KMK004_176"),
						//R12_28
						KMK004PrintCommon.convertTime(deforC.isPresent() ? deforC.get().legalTime : 0),
						//R12_30
						null, 
						//R12_32
						null,
						//R12_33
						null,
						//R12_34
						null, 
						//R12_35
						null, 
						//R12_38
						null, 
						//R8_39
						null,
						//R8_40
						null,
						//R8_41
						null,
						//R8_42
						null,
						//R8_43
						null,
						//R8_44
						null
						));
			}
		}
		return datas;
	}
	
	private MasterData buildEmploymentARow(
			String value1, String value2, String value3, String value4, String value5,
			String value6, String value7, String value8, String value9, String value10,
			String value11, String value12, String value13, String value14, String value15,
			String value16, String value17, String value18, String value19, String value20,
			String value21, String value22, String value23, String value24, String value25,
			String value26, String value27, String value28, String value29, String value30,
			String value31, String value32, String value33, String value34, String value35) {
		
		Map<String, MasterCellData> data = new HashMap<>();
			data.put(EmploymentColumn.KMK004_154, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_154)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_185, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_185)
                .value(value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_186, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_186)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_155, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_155)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_156, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_156)
                .value(value5)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_157, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_157)
                .value(value6)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_156_1, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_156_1)
                .value(value7)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_158, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_158)
                .value(value8)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_159, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_159)
                .value(value9)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_160, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_160)
                .value(value10)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_161, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_161)
                .value(value11)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_162, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_162)
                .value(value12)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_163, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_163)
                .value(value13)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_164, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_164)
                .value(value14)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_165, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_165)
                .value(value15)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_166, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_166)
                .value(value16)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_167, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_167)
                .value(value17)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_156_2, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_156_2)
                .value(value18)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_168, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_168)
                .value(value19)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_169, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_169)
                .value(value20)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_170, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_170)
                .value(value21)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_171, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_171)
                .value(value22)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_156_3, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_156_3)
                .value(value23)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_172, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_172)
                .value(value24)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_156_4, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_156_4)
                .value(value25)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_158_1, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_158_1)
                .value(value26)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_173, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_173)
                .value(value27)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_174, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_174)
                .value(value28)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(EmploymentColumn.KMK004_175, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_175)
                .value(value29)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_159_1, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_159_1)
                .value(value30)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_160_1, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_160_1)
                .value(value31)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_161_1, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_161_1)
                .value(value32)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_162_1, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_162_1)
                .value(value33)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_163_1, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_163_1)
                .value(value34)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(EmploymentColumn.KMK004_164_1, MasterCellData.builder()
                .columnId(EmploymentColumn.KMK004_164_1)
                .value(value35)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
			
		return MasterData.builder().rowData(data).build();
	}
	
	public static MasterData buildWorkPlaceARow(
			String value1, String value2, String value3, String value4, String value5,
			String value6, String value7, String value8, String value9, String value10,
			String value11, String value12, String value13, String value14, String value15,
			String value16, String value17, String value18, String value19, String value20,
			String value21, String value22, String value23, String value24, String value25,
			String value26, String value27, String value28, String value29, String value30,
			String value31, String value32, String value33, String value34, String value35) {
		
		Map<String, MasterCellData> data = new HashMap<>();
		//R14_1
		data.put(WorkPlaceColumn.KMK004_154, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_154)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_2
		data.put(WorkPlaceColumn.KMK004_187, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_187)
                .value(value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_3
		data.put(WorkPlaceColumn.KMK004_188, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_188)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_4
		data.put(WorkPlaceColumn.KMK004_155, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_155)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_6
		data.put(WorkPlaceColumn.KMK004_156, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_156)
                .value(value5)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_9
		data.put(WorkPlaceColumn.KMK004_157, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_157)
                .value(value6)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_10
		data.put(WorkPlaceColumn.KMK004_156_1, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_156_1)
                .value(value7)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_11
		data.put(WorkPlaceColumn.KMK004_158, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_158)
                .value(value8)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_12
		data.put(WorkPlaceColumn.KMK004_159, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_159)
                .value(value9)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_13
		data.put(WorkPlaceColumn.KMK004_160, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_160)
                .value(value10)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_14
		data.put(WorkPlaceColumn.KMK004_161, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_161)
                .value(value11)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_15
		data.put(WorkPlaceColumn.KMK004_162, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_162)
                .value(value12)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_16
		data.put(WorkPlaceColumn.KMK004_163, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_163)
                .value(value13)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_17
		data.put(WorkPlaceColumn.KMK004_164, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_164)
                .value(value14)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_18
		data.put(WorkPlaceColumn.KMK004_165, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_165)
                .value(value15)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_19
		data.put(WorkPlaceColumn.KMK004_166, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_166)
                .value(value16)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_21
		data.put(WorkPlaceColumn.KMK004_167, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_167)
                .value(value17)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_22
		data.put(WorkPlaceColumn.KMK004_156_2, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_156_2)
                .value(value18)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_23
		data.put(WorkPlaceColumn.KMK004_168, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_168)
                .value(value19)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_24
		data.put(WorkPlaceColumn.KMK004_169, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_169)
                .value(value20)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_25
		data.put(WorkPlaceColumn.KMK004_170, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_170)
                .value(value21)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_26
		data.put(WorkPlaceColumn.KMK004_171, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_171)
                .value(value22)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_28
		data.put(WorkPlaceColumn.KMK004_156_3, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_156_3)
                .value(value23)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_31
		data.put(WorkPlaceColumn.KMK004_172, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_172)
                .value(value24)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_32
		data.put(WorkPlaceColumn.KMK004_156_4, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_156_4)
                .value(value25)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_33
		data.put(WorkPlaceColumn.KMK004_158_1, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_158_1)
                .value(value26)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_34
		data.put(WorkPlaceColumn.KMK004_173, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_173)
                .value(value27)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_36
		data.put(WorkPlaceColumn.KMK004_174, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_174)
                .value(value28)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
		//R14_38
		data.put(WorkPlaceColumn.KMK004_175, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_175)
                .value(value29)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_39
		data.put(WorkPlaceColumn.KMK004_159_1, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_159_1)
                .value(value30)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_40
		data.put(WorkPlaceColumn.KMK004_160_1, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_160_1)
                .value(value31)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_41
		data.put(WorkPlaceColumn.KMK004_161_1, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_161_1)
                .value(value32)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_42
		data.put(WorkPlaceColumn.KMK004_162_1, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_162_1)
                .value(value33)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_43
		data.put(WorkPlaceColumn.KMK004_163_1, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_163_1)
                .value(value34)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		//R14_44
		data.put(WorkPlaceColumn.KMK004_164_1, MasterCellData.builder()
                .columnId(WorkPlaceColumn.KMK004_164_1)
                .value(value35)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		return MasterData.builder().rowData(data).build();
	}

}
