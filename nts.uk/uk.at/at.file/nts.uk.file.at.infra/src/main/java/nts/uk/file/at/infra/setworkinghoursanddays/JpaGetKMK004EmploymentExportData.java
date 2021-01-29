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
			+ " s.pk.cid = :cid AND s.pk.ym IN :yms"
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
											+ "KRCST_COM_FLEX_M_CAL_SET.WITHIN_TIME_USE, "
											+" KRCST_EMP_FLEX_M_CAL_SET.AGGR_METHOD, "
											+" KRCST_EMP_FLEX_M_CAL_SET.SETTLE_PERIOD_MON, "
											+" KRCST_EMP_FLEX_M_CAL_SET.SETTLE_PERIOD, "
											+" KRCST_EMP_FLEX_M_CAL_SET.START_MONTH AS FLEX_START_MONTH, "
											+" KRCST_EMP_FLEX_M_CAL_SET.INCLUDE_HDWK, "
											+" KRCST_EMP_FLEX_M_CAL_SET.LEGAL_AGGR_SET, "
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
											+" 	FROM BSYMT_EMPLOYMENT "
											+" LEFT JOIN KSHST_EMP_REG_LABOR_TIME ON BSYMT_EMPLOYMENT.CID = KSHST_EMP_REG_LABOR_TIME.CID " 
											+"            AND BSYMT_EMPLOYMENT.CODE = KSHST_EMP_REG_LABOR_TIME.EMP_CD  		" 
											+" LEFT JOIN KSHST_EMP_TRANS_LAB_TIME ON BSYMT_EMPLOYMENT.CID = KSHST_EMP_TRANS_LAB_TIME.CID  " 
											+"            AND BSYMT_EMPLOYMENT.CODE = KSHST_EMP_TRANS_LAB_TIME.EMP_CD  		" 
											+" LEFT JOIN KRCST_COM_FLEX_M_CAL_SET ON BSYMT_EMPLOYMENT.CID = KRCST_COM_FLEX_M_CAL_SET.CID  "
											+" LEFT JOIN KRCST_EMP_DEFOR_M_CAL_SET ON BSYMT_EMPLOYMENT.CID = KRCST_EMP_DEFOR_M_CAL_SET.CID  "
											+"            AND BSYMT_EMPLOYMENT.CODE = KRCST_EMP_DEFOR_M_CAL_SET.EMP_CD  		"
											+" LEFT JOIN KRCST_EMP_FLEX_M_CAL_SET ON BSYMT_EMPLOYMENT.CID = KRCST_EMP_FLEX_M_CAL_SET.CID  "
											+"            AND BSYMT_EMPLOYMENT.CODE = KRCST_EMP_FLEX_M_CAL_SET.EMP_CD  		"
											+" LEFT JOIN KRCST_EMP_REG_M_CAL_SET ON BSYMT_EMPLOYMENT.CID = KRCST_EMP_REG_M_CAL_SET.CID  "
											+"            AND BSYMT_EMPLOYMENT.CODE = KRCST_EMP_REG_M_CAL_SET.EMP_CD"
											+" WHERE BSYMT_EMPLOYMENT.CID = ? "
											+" ORDER BY IIF(BSYMT_EMPLOYMENT.NAME IS NOT NULL,0,1), BSYMT_EMPLOYMENT.CODE ";
	@Override
	public List<MasterData> getEmploymentExportData(int startDate, int endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		int month = this.month();
		
		YearMonthPeriod ymPeriod = new YearMonthPeriod(YearMonth.of(startDate, month), YearMonth.of(endDate, month).nextYear().previousMonth());
		String startOfWeek = getStartOfWeek(cid);

		val legalTimes = this.queryProxy().query(LEGAL_TIME_EMP, KshmtLegalTimeMEmp.class)
				.setParameter("cid", cid)
				.setParameter("yms", ymPeriod.yearMonthsBetween().stream().map(x -> x.v().toString()).collect(Collectors.toList()))
				.getList();
			
		try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOYMENT.toString())) {
			stmt.setString(1, cid);
			NtsResultSet result = new NtsResultSet(stmt.executeQuery());
			
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

		Integer refPreTime = r.getInt("WITHIN_TIME_USE");
		
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
			
			datas.add(buildEmploymentARow(
					//R12_1
					r.getInt("ROW_NUMBER_CODE") == 1 ? r.getString("CODE") : null,
					//R12_2
					r.getInt("ROW_NUMBER_CODE") == 1 ? r.getString("NAME") : null,
					//R12_3
					String.valueOf(y),
					//R12_4
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_401"), 
					//R12_5
					KMK004PrintCommon.convertTime(normal.isPresent() ? normal.get().legalTime : null),
					//R12_6
					KMK004PrintCommon.convertTime(r.getInt(("DAILY_TIME"))),
					//R12_7
					KMK004PrintCommon.convertTime(r.getInt(("WEEKLY_TIME"))),
					//R12_8
					KMK004PrintCommon.getExtraType(r.getInt("INCLUDE_EXTRA_AGGR")),
					//R12_9
					r.getInt("INCLUDE_EXTRA_AGGR")==null ?null: r.getInt("INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_LEGAL_AGGR")) : null,
					//R12_10
					r.getInt("INCLUDE_EXTRA_AGGR")==null ?null:r.getInt("INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_HOLIDAY_AGGR")) : null, 
					//R12_11
					KMK004PrintCommon.getExtraType(r.getInt("INCLUDE_EXTRA_OT")),
					//R12_12
					r.getInt("INCLUDE_EXTRA_OT") == null ?null:r.getInt("INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_LEGAL_OT")) : null, 
					//R12_13		
					r.getInt("INCLUDE_EXTRA_OT") == null ?null:r.getInt("INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("INCLUDE_HOLIDAY_OT")) : null,
					//R12_14
					KMK004PrintCommon.getFlexType(refPreTime),
					//R12_15
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_401"),
					//R12_16 
					KMK004PrintCommon.convertTime(refPreTime == 0? null : flex.isPresent() ? flex.get().withinTime : null),
					//R12_17
					KMK004PrintCommon.convertTime(flex.isPresent() ? flex.get().legalTime : null),
					//R10_18
					KMK004PrintCommon.convertTime(flex.isPresent() ? flex.get().weekAvgTime : null),
					//R12_19
					KMK004PrintCommon.getSettle(r.getInt("SETTLE_PERIOD")) ,
					//R12_20
					r.getInt("FLEX_START_MONTH")== null ? null: r.getInt("FLEX_START_MONTH").toString() + "月",
					//R12_21
					r.getInt("SETTLE_PERIOD_MON")== null?null: r.getInt("SETTLE_PERIOD_MON") == 2 ? "2ヶ月" : "3ヶ月",
					//R12_22
					KMK004PrintCommon.getShortageTime(r.getInt("INSUFFIC_SET")), 
					//R12_23
					KMK004PrintCommon.getAggType(r.getInt("AGGR_METHOD")),
					//R12_24
					r.getInt("AGGR_METHOD") == null ?null: r.getInt("AGGR_METHOD") == 0 ? KMK004PrintCommon.getInclude(r.getInt("INCLUDE_OT")) : null,
					//R12_25
					KMK004PrintCommon.getInclude(r.getInt("INCLUDE_HDWK")),
					//R12_26
					KMK004PrintCommon.getLegal(r.getInt("LEGAL_AGGR_SET")),
					//R12_27
					((month - 1) % 12 + 1) + I18NText.getText("KMK004_401"),
					//R12_28
					KMK004PrintCommon.convertTime(defor.isPresent() ? defor.get().legalTime : null),
					//R12_29
					KMK004PrintCommon.convertTime(r.getInt(("LAR_DAILY_TIME"))), 
					//R12_30
					KMK004PrintCommon.convertTime(r.getInt(("LAR_WEEKLY_TIME"))),
					//R12_31
					r.getInt("STR_MONTH")== null ?null: r.getInt("STR_MONTH") + I18NText.getText("KMK004_402"),
					//R12_32
					r.getInt("PERIOD")== null ?null:r.getInt("PERIOD") + I18NText.getText("KMK004_403"),
					//R12_33
					r.getInt("REPEAT_ATR") ==null ?null:r.getInt("REPEAT_ATR") == 1 ? "○" : "-",
					//R12_34
					KMK004PrintCommon.getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_AGGR")),
					//R12_35
					r.getInt("DEFOR_INCLUDE_EXTRA_AGGR")==null ?null:r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_AGGR")) : null,
					//R12_36
					r.getInt("DEFOR_INCLUDE_EXTRA_AGGR")==null ?null:r.getInt("DEFOR_INCLUDE_EXTRA_AGGR") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_AGGR")) : null,
					//R12_37
					KMK004PrintCommon.getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_OT")),
					//R12_38
					r.getInt("DEFOR_INCLUDE_EXTRA_OT")==null?null:r.getInt("DEFOR_INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_OT")) : null,
					//E12_39
					r.getInt("DEFOR_INCLUDE_EXTRA_OT")==null?null:r.getInt("DEFOR_INCLUDE_EXTRA_OT") != 0 ? KMK004PrintCommon.getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_OT")): null
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
					//R12_4
					((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"), 
					//R12_5
					KMK004PrintCommon.convertTime(normalN.isPresent() ? normalN.get().legalTime : null),
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
					((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
					//R12_16 
					KMK004PrintCommon.convertTime(refPreTime == 0? null :flexN.isPresent() ? flexN.get().withinTime : null),
					//R12_17
					KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().legalTime : null),
					//R10_18
					KMK004PrintCommon.convertTime(flexN.isPresent() ? flexN.get().weekAvgTime : null),
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
					((month - 1) % 12 + 2) + I18NText.getText("KMK004_401"),
					//R12_28
					KMK004PrintCommon.convertTime(deforN.isPresent() ? deforN.get().legalTime : null),
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
			
			// buil month remain
			for (int i = 1; i < 11; i++) {
				int m = (month + i) % 12 + 1;
				int currentYm = y * 100 + ((month + i) / 12) * 100 + m;
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
						//R12_4
						(m) + I18NText.getText("KMK004_401"), 
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
						(m) + I18NText.getText("KMK004_401"),
						//R12_16 
						KMK004PrintCommon.convertTime(refPreTime == 0? null :flexC.isPresent() ? flexC.get().withinTime : null),
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
						(m) + I18NText.getText("KMK004_401"),
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
	

}
