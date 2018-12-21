package nts.uk.file.at.infra.setworkinghoursanddays;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.file.at.app.export.setworkinghoursanddays.SetWorkingHoursAndDaysExRepository;
import nts.uk.file.at.app.export.setworkinghoursanddays.SetWorkingHoursAndDaysUtils;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
@Stateless
public class jpaSetWorkingHoursAndDays extends JpaRepository implements SetWorkingHoursAndDaysExRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	private static final String GET_EXPORT_MONTH = "SELECT m.STR_M FROM CBMST_BEGINNING_MONTH m WHERE m.CID = ?cid";
	
	private static final String GET_EXPORT_EXCEL = 
			" SELECT "
					+" KSHST_COM_NORMAL_SET.[YEAR], "
					+" KSHST_COM_NORMAL_SET.JAN_TIME AS N1, "
					+" KSHST_COM_NORMAL_SET.FEB_TIME AS N2, "
					+" KSHST_COM_NORMAL_SET.MAR_TIME AS N3, "
					+" KSHST_COM_NORMAL_SET.APR_TIME AS N4, "
					+" KSHST_COM_NORMAL_SET.MAY_TIME AS N5, "
					+" KSHST_COM_NORMAL_SET.JUN_TIME AS N6, "
					+" KSHST_COM_NORMAL_SET.JUL_TIME AS N7, "
					+" KSHST_COM_NORMAL_SET.AUG_TIME AS N8, "
					+" KSHST_COM_NORMAL_SET.SEP_TIME AS N9, "
					+" KSHST_COM_NORMAL_SET.OCT_TIME AS N10, "
					+" KSHST_COM_NORMAL_SET.NOV_TIME AS N11, "
					+" KSHST_COM_NORMAL_SET.DEC_TIME AS N12, "
					+" KSHST_COM_REG_LABOR_TIME.DAILY_TIME, KSHST_COM_REG_LABOR_TIME.WEEKLY_TIME, "
					+" KSHST_COM_REG_LABOR_TIME.WEEK_STR, "
					+" KRCST_COM_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR, "
					+" IIF (KRCST_COM_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_COM_REG_M_CAL_SET.INCLUDE_LEGAL_AGGR , NUll) AS INCLUDE_LEGAL_AGGR, "
					+" IIF (KRCST_COM_REG_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1, KRCST_COM_REG_M_CAL_SET.INCLUDE_HOLIDAY_AGGR , NUll) AS INCLUDE_HOLIDAY_AGGR, "
					+" KRCST_COM_REG_M_CAL_SET.INCLUDE_EXTRA_OT, "
					+" IIF (KRCST_COM_REG_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_COM_REG_M_CAL_SET.INCLUDE_LEGAL_OT , NUll) AS INCLUDE_LEGAL_OT, "
					+" IIF (KRCST_COM_REG_M_CAL_SET.INCLUDE_EXTRA_OT = 1, KRCST_COM_REG_M_CAL_SET.INCLUDE_HOLIDAY_OT , NUll) AS INCLUDE_HOLIDAY_OT, "
					+" KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME, "
					+" KSHST_COM_FLEX_SET.STAT_JAN_TIME AS F1, "
					+" KSHST_COM_FLEX_SET.STAT_FEB_TIME AS F2, "
					+" KSHST_COM_FLEX_SET.STAT_MAR_TIME AS F3, "
					+" KSHST_COM_FLEX_SET.STAT_APR_TIME AS F4, "
					+" KSHST_COM_FLEX_SET.STAT_MAY_TIME AS F5, "
					+" KSHST_COM_FLEX_SET.STAT_JUN_TIME AS F6, "
					+" KSHST_COM_FLEX_SET.STAT_JUL_TIME AS F7, "
					+" KSHST_COM_FLEX_SET.STAT_AUG_TIME AS F8, "
					+" KSHST_COM_FLEX_SET.STAT_SEP_TIME AS F9, "
					+" KSHST_COM_FLEX_SET.STAT_OCT_TIME AS F10, "
					+" KSHST_COM_FLEX_SET.STAT_NOV_TIME AS F11, "
					+" KSHST_COM_FLEX_SET.STAT_DEC_TIME AS F12, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_JAN_TIME , NUll) AS SPEC_T1, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_FEB_TIME , NUll) AS SPEC_T2, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_MAR_TIME , NUll) AS SPEC_T3, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_APR_TIME , NUll) AS SPEC_T4, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_MAY_TIME , NUll) AS SPEC_T5, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_JUN_TIME , NUll) AS SPEC_T6, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_JUL_TIME , NUll) AS SPEC_T7, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_AUG_TIME , NUll) AS SPEC_T8, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_SEP_TIME , NUll) AS SPEC_T9, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_OCT_TIME , NUll) AS SPEC_T10, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_NOV_TIME , NUll) AS SPEC_T11, "
					+" IIF (KSHST_FLX_GET_PRWK_TIME.REFERENCE_PRED_TIME = 0, KSHST_COM_FLEX_SET.SPEC_DEC_TIME , NUll) AS SPEC_T12, "
					+" KRCST_COM_FLEX_M_CAL_SET.AGGR_METHOD, "
					+" IIF (KRCST_COM_FLEX_M_CAL_SET.AGGR_METHOD = 0, KRCST_COM_FLEX_M_CAL_SET.INCLUDE_OT, NULL) AS INCLUDE_OT, "
					+" KRCST_COM_FLEX_M_CAL_SET.INSUFFIC_SET, "
					+" KSHST_COM_DEFOR_LAR_SET.JAN_TIME AS T1, "
					+" KSHST_COM_DEFOR_LAR_SET.FEB_TIME AS T2, "
					+" KSHST_COM_DEFOR_LAR_SET.MAR_TIME AS T3, "
					+" KSHST_COM_DEFOR_LAR_SET.APR_TIME AS T4, "
					+" KSHST_COM_DEFOR_LAR_SET.MAY_TIME AS T5, "
					+" KSHST_COM_DEFOR_LAR_SET.JUN_TIME AS T6, "
					+" KSHST_COM_DEFOR_LAR_SET.JUL_TIME AS T7, "
					+" KSHST_COM_DEFOR_LAR_SET.AUG_TIME AS T8, "
					+" KSHST_COM_DEFOR_LAR_SET.SEP_TIME AS T9, "
					+" KSHST_COM_DEFOR_LAR_SET.OCT_TIME AS T10, "
					+" KSHST_COM_DEFOR_LAR_SET.NOV_TIME AS T11, "
					+" KSHST_COM_DEFOR_LAR_SET.DEC_TIME AS T12, "
					+" KSHST_COM_REG_LABOR_TIME.DAILY_TIME AS REG_DAILY_TIME, "
					+" KSHST_COM_REG_LABOR_TIME.WEEKLY_TIME AS REG_WEEKLY_TIME, "
					+" KSHST_COM_REG_LABOR_TIME.WEEK_STR AS REG_WEEK_STR, "
					+" KRCST_COM_DEFOR_M_CAL_SET.STR_MONTH, "
					+" KRCST_COM_DEFOR_M_CAL_SET.PERIOD, "
					+" KRCST_COM_DEFOR_M_CAL_SET.REPEAT_ATR, "
					+" KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR AS DEFOR_INCLUDE_EXTRA_AGGR, "
					+" IIF(KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1 ,KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_LEGAL_AGGR, NULL) AS DEFOR_INCLUDE_LEGAL_AGGR, "
					+" IIF(KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1 ,KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_HOLIDAY_AGGR, NULL) AS DEFOR_INCLUDE_HOLIDAY_AGGR, "
					+" KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_EXTRA_OT AS DEFOR_INCLUDE_EXTRA_OT, "
					+" IIF(KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1 ,KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_LEGAL_OT, NULL) AS DEFOR_INCLUDE_LEGAL_OT, "
					+" IIF(KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_EXTRA_AGGR = 1 ,KRCST_COM_DEFOR_M_CAL_SET.INCLUDE_HOLIDAY_OT, NULL) AS DEFOR_INCLUDE_HOLIDAY_OT "
					+" FROM KRCST_COM_DEFOR_M_CAL_SET "
					+" INNER JOIN KRCST_COM_FLEX_M_CAL_SET ON KRCST_COM_DEFOR_M_CAL_SET.CID = KRCST_COM_FLEX_M_CAL_SET.CID "
					+" INNER JOIN KRCST_COM_REG_M_CAL_SET ON KRCST_COM_DEFOR_M_CAL_SET.CID = KRCST_COM_REG_M_CAL_SET.CID "
					+" INNER JOIN KSHST_COM_TRANS_LAB_TIME ON KRCST_COM_DEFOR_M_CAL_SET.CID = KSHST_COM_TRANS_LAB_TIME.CID "
					+" INNER JOIN KSHST_COM_REG_LABOR_TIME ON KRCST_COM_DEFOR_M_CAL_SET.CID = KSHST_COM_REG_LABOR_TIME.CID "
					+" INNER JOIN KSHST_COM_NORMAL_SET ON KRCST_COM_DEFOR_M_CAL_SET.CID = KSHST_COM_NORMAL_SET.CID "
					+" 																AND KSHST_COM_NORMAL_SET.[YEAR] >= ? "
					+" 																AND KSHST_COM_NORMAL_SET.[YEAR] <= ? "
					+" INNER JOIN KSHST_COM_FLEX_SET ON KRCST_COM_DEFOR_M_CAL_SET.CID = KSHST_COM_FLEX_SET.CID "
					+" 																AND KSHST_COM_FLEX_SET.[YEAR] = KSHST_COM_NORMAL_SET.[YEAR] "
					+" 																AND KSHST_COM_FLEX_SET.[YEAR] = KSHST_COM_NORMAL_SET.[YEAR] "
					+" INNER JOIN KSHST_COM_DEFOR_LAR_SET ON KRCST_COM_DEFOR_M_CAL_SET.CID = KSHST_COM_DEFOR_LAR_SET.CID "
					+" 																AND KSHST_COM_DEFOR_LAR_SET.[YEAR] = KSHST_COM_NORMAL_SET.[YEAR] "
					+" 																AND KSHST_COM_DEFOR_LAR_SET.[YEAR] = KSHST_COM_NORMAL_SET.[YEAR] "
					+" INNER JOIN KSHST_FLX_GET_PRWK_TIME ON KRCST_COM_DEFOR_M_CAL_SET.CID = KSHST_FLX_GET_PRWK_TIME.CID "
					+" WHERE KRCST_COM_DEFOR_M_CAL_SET.CID = ? "
					+" ORDER BY KSHST_COM_NORMAL_SET.[YEAR] ";
	@Override
	public List<MasterData> getCompanyExportData(int startDate, int endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		
		try (PreparedStatement stmt = this.connection().prepareStatement(GET_EXPORT_EXCEL.toString())){
			stmt.setInt(1, startDate);
			stmt.setInt(2, endDate);
			stmt.setString(3, cid);
			NtsResultSet result = new NtsResultSet(stmt.executeQuery());
			
			Query monthQuery = entityManager.createNativeQuery(GET_EXPORT_MONTH.toString()).setParameter("cid", cid);
			List data = monthQuery.getResultList();
			int month;
			if(data.size() == 0){
				month = 1;
			}
			else{
				month = Integer.valueOf(data.get(0).toString());
			}
			result.forEach(i->{
				datas.addAll(buildCompanyRow(i, month));
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;
	}
	
	private List<MasterData> buildCompanyRow(NtsResultRecord r, int month){
		List<MasterData> datas = new ArrayList<>();
		
		//Arow month
		datas.add(buildARow(
				//R8_1
				r.getString(("YEAR")),
				//R8_2 R8_3
				((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"), 
				//R8_4
				convertTime(r.getInt(("N"+ ((month - 1) % 12 + 1)))),
				//R8_5
				I18NText.getText("KMK004_177"),
				//R8_7
				convertTime(r.getInt(("DAILY_TIME"))), 
				//R8_9
				getWeekStart(r.getInt("WEEK_STR")),
				//R8_10
				getExtraType(r.getInt("INCLUDE_EXTRA_AGGR")),
				//R8_11
				r.getInt("INCLUDE_LEGAL_AGGR") != null ? getLegalType(r.getInt("INCLUDE_LEGAL_AGGR")) : null, 
				//R8_12
				r.getInt("INCLUDE_HOLIDAY_AGGR") != null ? getLegalType(r.getInt("INCLUDE_HOLIDAY_AGGR")) : null, 
				//R8_13
				getExtraType(r.getInt("INCLUDE_EXTRA_OT")), 
				//R8_14
				r.getInt("INCLUDE_LEGAL_OT") != null ? getLegalType(r.getInt("INCLUDE_LEGAL_OT")) : null,
				//R8_15
				r.getInt("INCLUDE_HOLIDAY_OT") != null ? getLegalType(r.getInt("INCLUDE_HOLIDAY_OT")) : null,
				//R8_16
				getFlexType(r.getInt("REFERENCE_PRED_TIME")), 
				//R8_17 R8_18
				((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"),
				//R8_19
				convertTime(r.getInt(("F"+ ((month - 1) % 12 + 1)))),
				//R8_20
				r.getInt(("SPEC_T"+ ((month - 1) % 12 + 1))) != null ? convertTime(r.getInt(("SPEC_T"+ ((month - 1) % 12 + 1)))) : null,
				//R8_21		
				getAggType(r.getInt("AGGR_METHOD")),
				//R8_22
				r.getInt("INCLUDE_OT") != null ? getInclude(r.getInt("INCLUDE_OT")) : null,
				//R8_23
				getShortageTime(r.getInt("INSUFFIC_SET")), 
				//R8_24 8_25
				((month - 1) % 12 + 1) + I18NText.getText("KMK004_176"),
				//R8_26
				convertTime(r.getInt(("T"+ ((month - 1) % 12 + 1)))), 
				//R8_27		
				I18NText.getText("KMK004_177"), 
				//R8_29
				convertTime(r.getInt("REG_DAILY_TIME")), 
				//R8_31
				getWeekStart(r.getInt("REG_WEEK_STR")),
				//R8_32 R8_33
				r.getInt("STR_MONTH") + I18NText.getText("KMK004_179"), 
				//R8_34 R8_35
				r.getInt("PERIOD") + I18NText.getText("KMK004_180"), 
				//R8_36
				r.getInt("REPEAT_ATR") == 1 ? "○" : "-", 
				//R8_37
				getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_AGGR")),
				//R8_38
				r.getInt("DEFOR_INCLUDE_LEGAL_AGGR") != null ? getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_AGGR")) : null,
				//R8_39
				r.getInt("DEFOR_INCLUDE_HOLIDAY_AGGR") != null ? getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_AGGR")) : null,
				//R8_40
				getWeeklySurcharge(r.getInt("DEFOR_INCLUDE_EXTRA_OT")),
				//R8_41
				r.getInt("DEFOR_INCLUDE_LEGAL_OT") != null ? getLegalType(r.getInt("DEFOR_INCLUDE_LEGAL_OT")) : null,
				//R8_42
				r.getInt("DEFOR_INCLUDE_HOLIDAY_OT") != null ? getLegalType(r.getInt("DEFOR_INCLUDE_HOLIDAY_OT")) : null
				));
		
		//Arow month + 1
		datas.add(buildARow(
				//R8_1
				"",
				//R8_2 R8_3
				((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"), 
				//R8_4
				convertTime(r.getInt(("N"+ ((month - 1) % 12 + 2)))),
				//R8_6
				I18NText.getText("KMK004_178"),
				//R8_8
				convertTime(r.getInt(("WEEKLY_TIME"))), 
				//R8_9
				null,
				//R8_10
				null,
				//R8_11
				null, 
				//R8_12
				null, 
				//R8_13
				null, 
				//R8_14
				null,
				//R8_15
				null,
				//R8_16
				null, 
				//R8_17 R8_18
				((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"),
				//R8_19
				convertTime(r.getInt(("F"+ ((month - 1) % 12 + 2)))),
				//R8_20
				r.getInt(("SPEC_T"+ ((month - 1) % 12 + 2))) != null ? convertTime(r.getInt(("SPEC_T"+ ((month - 1) % 12 + 2)))) : null,
				//R8_21		
				null,
				//R8_22
				null,
				//R8_23
				null, 
				//R8_24 8_25
				((month - 1) % 12 + 2) + I18NText.getText("KMK004_176"),
				//R8_26
				convertTime(r.getInt(("T"+ ((month - 1) % 12 + 2)))), 
				//R8_28	
				I18NText.getText("KMK004_178"), 
				//R8_30
				convertTime(r.getInt("REG_WEEKLY_TIME")), 
				//R8_31
				null,
				//R8_32 R8_33
				null, 
				//R8_34 R8_35
				null, 
				//R8_36
				null, 
				//R8_37
				null,
				//R8_38
				null,
				//R8_39
				null,
				//R8_40
				null,
				//R8_41
				null,
				//R8_42
				null
				));
		//Arow month remain
		for(int i = 1 ; i < 11; i++){
			datas.add(buildARow(
					//R8_1
					"",
					//R8_2 R8_3
					((month + i) % 12 + 1) + I18NText.getText("KMK004_176"), 
					//R8_4
					convertTime(r.getInt(("N"+ ((month + i) % 12 + 1)))),
					//R8_6
					null,
					//R8_8
					null, 
					//R8_9
					null,
					//R8_10
					null,
					//R8_11
					null, 
					//R8_12
					null, 
					//R8_13
					null, 
					//R8_14
					null,
					//R8_15
					null,
					//R8_16
					null, 
					//R8_17 R8_18
					(month + i) % 12 + 1 + I18NText.getText("KMK004_176"),
					//R8_19
					convertTime(r.getInt(("F"+ ((month + i) % 12 + 1)))),
					//R8_20
					r.getInt(("SPEC_T"+ ((month + i) % 12 + 1))) != null ? convertTime(r.getInt(("SPEC_T"+ ((month + i) % 12 + 1)))) : null,
					//R8_21		
					null,
					//R8_22
					null,
					//R8_23
					null, 
					//R8_24 8_25
					((month + i) % 12 + 1) + I18NText.getText("KMK004_176"),
					//R8_26
					convertTime(r.getInt(("T"+ ((month + i) % 12 + 1)))), 
					//R8_28	
					null, 
					//R8_30
					null, 
					//R8_31
					null,
					//R8_32 R8_33
					null, 
					//R8_34 R8_35
					null, 
					//R8_36
					null, 
					//R8_37
					null,
					//R8_38
					null,
					//R8_39
					null,
					//R8_40
					null,
					//R8_41
					null,
					//R8_42
					null
					));
		}
		return datas;
	}
	
	private MasterData buildARow(
			String value1, String value2, String value3, String value4, String value5,
			String value6, String value7, String value8, String value9, String value10,
			String value11, String value12, String value13, String value14, String value15,
			String value16, String value17, String value18, String value19, String value20,
			String value21, String value22, String value23, String value24, String value25,
			String value26, String value27, String value28, String value29, String value30,
			String value31, String value32, String value33) {
		
		Map<String, MasterCellData> data = new HashMap<>();
		data.put(SetWorkingHoursAndDaysUtils.KMK004_154, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_154)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_155, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_155)
                .value(value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_156, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_156)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_157, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_157)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_156_1, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_156_1)
                .value(value5)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_158, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_158)
                .value(value6)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_159, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_159)
                .value(value7)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_160, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_160)
                .value(value8)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_161, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_161)
                .value(value9)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_162, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_162)
                .value(value10)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_163, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_163)
                .value(value11)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_164, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_164)
                .value(value12)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_165, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_165)
                .value(value13)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_166, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_166)
                .value(value14)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_167, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_167)
                .value(value15)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_156_2, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_156_2)
                .value(value16)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_168, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_168)
                .value(value17)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_169, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_169)
                .value(value18)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_170, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_170)
                .value(value19)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_171, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_171)
                .value(value20)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_156_3, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_156_3)
                .value(value21)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_172, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_172)
                .value(value22)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_156_4, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_156_4)
                .value(value23)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_158_1, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_158_1)
                .value(value24)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_173, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_173)
                .value(value25)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_174, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_174)
                .value(value26)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_175, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_175)
                .value(value27)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_159_1, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_159_1)
                .value(value28)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_160_1, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_160_1)
                .value(value29)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_161_1, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_161_1)
                .value(value30)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_162_1, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_162_1)
                .value(value31)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_163_1, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_163_1)
                .value(value32)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(SetWorkingHoursAndDaysUtils.KMK004_164_1, MasterCellData.builder()
                .columnId(SetWorkingHoursAndDaysUtils.KMK004_164_1)
                .value(value33)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	private String convertTime(int seconds){
		return seconds / 60 + ":00";
	}
	
	private String getWeekStart(int weekStart) {
		String weekStartType = null;
		WeekStart type = EnumAdaptor.valueOf(weekStart, WeekStart.class);
		switch (type) {
			case Monday:
				weekStartType = I18NText.getText("Enum_DayOfWeek_Monday");
				break;
			case Tuesday:
				weekStartType = I18NText.getText("Enum_DayOfWeek_Tuesday");
				break;
			case Wednesday:
				weekStartType = I18NText.getText("Enum_DayOfWeek_Wednesday");
				break;
			case Thursday:
				weekStartType = I18NText.getText("Enum_DayOfWeek_Thursday");
				break;
			case Friday:
				weekStartType = I18NText.getText("Enum_DayOfWeek_Friday");
				break;
			case Saturday:
				weekStartType = I18NText.getText("Enum_DayOfWeek_Saturday");
				break;
			case Sunday:
				weekStartType = I18NText.getText("Enum_DayOfWeek_Sunday");
				break;
			case TighteningStartDate:
				weekStartType = "締め開始日";
				break;
			default:
				break;
			}
		return weekStartType;
	}
	
	public static String getExtraType(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_58");
    	case 1:
    		return TextResource.localize("KMK004_59");
    	default: 
    		return null;
    	}
    }
	
	public static String getLegalType(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_64");
    	case 1:
    		return TextResource.localize("KMK004_63");
    	default: 
    		return null;
    	}
    }
	
	public static String getFlexType(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_147");
    	case 1:
    		return TextResource.localize("KMK004_148");
    	default: 
    		return null;
    	}
    }
	
	public static String getAggType(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_181");
    	case 1:
    		return TextResource.localize("KMK004_182");
    	default: 
    		return null;
    	}
    }
	
	public static String getInclude(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_72");
    	case 1:
    		return TextResource.localize("KMK004_73");
    	default: 
    		return null;
    	}
    }
	
	public static String getShortageTime(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_76");
    	case 1:
    		return TextResource.localize("KMK004_77");
    	default: 
    		return null;
    	}
    }
	
	public static String getWeeklySurcharge(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_58");
    	case 1:
    		return TextResource.localize("KMK004_59");
    	default: 
    		return null;
    	}
    }

}
