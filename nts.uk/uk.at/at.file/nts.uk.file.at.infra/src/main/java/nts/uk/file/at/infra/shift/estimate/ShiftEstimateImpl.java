package nts.uk.file.at.infra.shift.estimate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.file.at.app.export.otpitem.CalFormulasItemColumn;
import nts.uk.file.at.app.export.shift.estimate.ShiftEstimateColumn;
import nts.uk.file.at.app.export.shift.estimate.ShiftEstimateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class ShiftEstimateImpl extends JpaRepository implements ShiftEstimateRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String GET_EXPORT_EXCEL = "SELECT eas.YEAR_HD_ATR ,eas.HAVY_HD_ATR ,eas.SPHD_ATR ,eas.HALF_DAY_ATR"
			+ " ,STUFF(( SELECT ', '+kpi.PREMIUM_NAME FROM KSCST_EST_AGGREGATE_SET eas LEFT JOIN KSCST_PER_COST_EXTRA_ITEM cei ON eas.CID = cei.CID LEFT JOIN KMNMT_PREMIUM_ITEM kpi ON eas.CID = kpi.CID AND cei.PREMIUM_NO = kpi.PREMIUM_NO WHERE eas.CID = ?cid ORDER BY kpi.PREMIUM_NO ASC FOR XML PATH ('') ), 1, 1, '' ) as NAME"
			+ " FROM KSCST_EST_AGGREGATE_SET eas" + " INNER JOIN KSCST_PER_COST_EXTRA_ITEM cei ON eas.CID = cei.CID"
			+ " INNER JOIN KMNMT_PREMIUM_ITEM kpi ON eas.CID = kpi.CID AND cei.PREMIUM_NO = kpi.PREMIUM_NO"
			+ " WHERE eas.CID = ?cid" + " GROUP BY eas.YEAR_HD_ATR ,eas.HAVY_HD_ATR ,eas.SPHD_ATR ,eas.HALF_DAY_ATR";
	
	private static final String GET_EXPORT_EXCEL_SHEET_TWO = "SELECT"
			+" ecs.TIME_Y_DISP"
			+" ,ecs.TIME_M_DISP"
			+" ,ecs.TIME_Y_ALARM_CHECK"
			+" ,ecs.TIME_M_ALARM_CHECK"
			+" ,ecs.PRICE_Y_DISP"
			+" ,ecs.PRICE_M_DISP"
			+" ,ecs.PRICE_Y_ALARM_CHECK"
			+" ,ecs.PRICE_M_ALARM_CHECK"
			+" ,ecs.DAYS_Y_DISP"
			+" ,ecs.DAYS_M_DISP"
			+" ,ecs.DAYS_Y_ALARM_CHECK"
			+" ,ecs.DAYS_M_ALARM_CHECK"
			+" ,ec.COMPARISON_ATR"
			+" ,eac.COLOR_CD"
			+" FROM"
			+" KSCST_EST_ALARM_COLOR eac"
			+" INNER JOIN KSCST_EST_COM_SET ecs ON ecs.CID = eac.CID"
			+" INNER JOIN KSCST_EST_COMPARISON ec ON ecs.CID = ec.CID"
			+" WHERE ecs.CID = ?cid"
			+" ORDER BY eac.EST_CONDITION ASC";
	
	private static final String GET_EXPORT_EXCEL_SHEET_THREE = "SELECT"
			+" CASE	"
			+" 	WHEN"
			+" 		TABLE_RESULT.ROW_NUMBER = 1 THEN"
			+" 			TABLE_RESULT.YEAR_TIME ELSE NULL "
			+" 		END YEAR_TIME"
			+" 		,TABLE_RESULT.MONTH_TIME"
			+" 		,TABLE_RESULT.EST_CONDITION_1ST_TIME"
			+" 		,TABLE_RESULT.EST_CONDITION_2ND_TIME"
			+" 		,TABLE_RESULT.EST_CONDITION_3RD_TIME"
			+" 		,TABLE_RESULT.EST_CONDITION_4TH_TIME"
			+" 		,TABLE_RESULT.EST_CONDITION_5TH_TIME"
			+" 		,TABLE_RESULT.EST_CONDITION_1ST_MNY"
			+" 		,TABLE_RESULT.EST_CONDITION_2ND_MNY"
			+" 		,TABLE_RESULT.EST_CONDITION_3RD_MNY"
			+" 		,TABLE_RESULT.EST_CONDITION_4TH_MNY"
			+" 		,TABLE_RESULT.EST_CONDITION_5TH_MNY"
			+" 		,TABLE_RESULT.EST_CONDITION_1ST_DAYS"
			+" 		,TABLE_RESULT.EST_CONDITION_2ND_DAYS"
			+" 		,TABLE_RESULT.EST_CONDITION_3RD_DAYS"
			+" 		,TABLE_RESULT.EST_CONDITION_4TH_DAYS"
			+" 		,TABLE_RESULT.EST_CONDITION_5TH_DAYS"
			+" FROM"
			+" (SELECT"
			+" 	etcs.TARGET_YEAR AS YEAR_TIME"
			+" 	,etcs.TARGET_CLS AS MONTH_TIME"
			+" 	,etcs.EST_CONDITION_1ST_TIME"
			+" 	,etcs.EST_CONDITION_2ND_TIME"
			+" 	,etcs.EST_CONDITION_3RD_TIME"
			+" 	,etcs.EST_CONDITION_4TH_TIME"
			+" 	,etcs.EST_CONDITION_5TH_TIME"
			+" 	,epcs.EST_CONDITION_1ST_MNY"
			+" 	,epcs.EST_CONDITION_2ND_MNY"
			+" 	,epcs.EST_CONDITION_3RD_MNY"
			+" 	,epcs.EST_CONDITION_4TH_MNY"
			+" 	,epcs.EST_CONDITION_5TH_MNY"
			+" 	,edcs.EST_CONDITION_1ST_DAYS"
			+" 	,edcs.EST_CONDITION_2ND_DAYS"
			+" 	,edcs.EST_CONDITION_3RD_DAYS"
			+" 	,edcs.EST_CONDITION_4TH_DAYS"
			+" 	,edcs.EST_CONDITION_5TH_DAYS	"
			+" 	,epcs.TARGET_YEAR AS YEAR_PRICE"
			+" 	,etcs.TARGET_CLS AS MONTH_PRICE"
			+" 	,edcs.TARGET_YEAR AS YEAR_DAYS"
			+" 	,etcs.TARGET_CLS AS MONTH_DAYS"
			+" 	,ROW_NUMBER () OVER ( PARTITION BY etcs.TARGET_YEAR ORDER BY etcs.TARGET_YEAR ASC ) AS ROW_NUMBER"
			+" FROM"
			+" 	KSCMT_EST_TIME_COM_SET etcs "
			+" 	INNER JOIN KSCMT_EST_PRICE_COM_SET epcs ON etcs.CID = epcs.CID AND etcs.TARGET_YEAR = epcs.TARGET_YEAR AND etcs.TARGET_CLS = epcs.TARGET_CLS"
			+" 	INNER JOIN KSCMT_EST_DAYS_COM_SET edcs ON etcs.CID = edcs.CID AND etcs.TARGET_YEAR = edcs.TARGET_YEAR AND etcs.TARGET_CLS = edcs.TARGET_CLS"
			+" 	WHERE etcs.CID = ?cid AND  ?startDate <= etcs.TARGET_YEAR AND etcs.TARGET_YEAR <= ?endDate"
			+" ) AS TABLE_RESULT ORDER BY TABLE_RESULT.YEAR_TIME, TABLE_RESULT.MONTH_TIME ASC";

	/** Sheet 1 **/
	@Override
	public List<MasterData> getDataExport() {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL.toString()).setParameter("cid", cid);
		Object[] data = (Object[]) query.getSingleResult();
		for (int i = 0; i < data.length; i++) {
			datas.add(new MasterData(dataContent(data[i],i), null, ""));
		}
		return datas;
	}

	private Map<String, Object> dataContent(Object object,int rowNumber) {
		Map<String, Object> data = new HashMap<>();
			data.put(ShiftEstimateColumn.KSM001_101,    rowNumber == 0 ?  ShiftEstimateColumn.KSM001_103 : rowNumber == 4 ? ShiftEstimateColumn.KSM001_110 : "");
			data.put(ShiftEstimateColumn.KSM001_101_1,  rowNumber == 0 ?  ShiftEstimateColumn.KSM001_104 : rowNumber == 3 ? ShiftEstimateColumn.KSM001_108 : rowNumber == 4 ? ShiftEstimateColumn.KSM001_111 : "");
			data.put(ShiftEstimateColumn.KSM001_101_2,  rowNumber == 0 ?  ShiftEstimateColumn.KSM001_105 : rowNumber == 1 ? ShiftEstimateColumn.KSM001_106 :rowNumber == 2 ? ShiftEstimateColumn.KSM001_107 :rowNumber == 3 ? ShiftEstimateColumn.KSM001_109 :"");
			data.put(ShiftEstimateColumn.KSM001_102,  rowNumber == 0 || rowNumber == 1 || rowNumber == 2 || rowNumber == 3 ?  ((BigDecimal) object).intValue() == 1 ? "○" : "ー" : rowNumber == 4 ? (String)object :"");
			return data;
	}
	
	/** Sheet 2 **/
	@Override
	public List<MasterData> getDataSheetTwoExport() {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_SHEET_TWO.toString()).setParameter("cid", cid);
		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		for (int i = 0; i < 18; i++) {
			datas.add(new MasterData(dataContentTwo(data, i), null, ""));
		}
		return datas;
	}

	@Override
	public List<MasterData> getDataSheetThreeExport(String startDate, String endDate) {
		String cid = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_SHEET_THREE.toString()).setParameter("cid", cid).setParameter("startDate", startDate).setParameter("endDate", endDate);
		@SuppressWarnings("unchecked")
		List<Object[]> data =  query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(dataContentThree(objects), null, ""));
		}
		return datas;
	}
	
	private Map<String, Object> dataContentTwo(List<Object[]> dataFromDB, int rowNumber) {
 		Map<String, Object> data = new HashMap<>();
		data.put(ShiftEstimateColumn.KSM001_112, 
				rowNumber == 0 ?  ShiftEstimateColumn.KSM001_114 
				: rowNumber == 5 ? ShiftEstimateColumn.KSM001_125 
				: rowNumber == 9 ? ShiftEstimateColumn.KSM001_130
				: rowNumber == 13 ? ShiftEstimateColumn.KSM001_135 
				: rowNumber == 17 ? ShiftEstimateColumn.KSM001_140 
				: "");
		
		data.put(ShiftEstimateColumn.KSM001_112_1,
				rowNumber == 0 ? ShiftEstimateColumn.KSM001_115
				: rowNumber == 1 ? ShiftEstimateColumn.KSM001_117
				: rowNumber == 2 ? ShiftEstimateColumn.KSM001_119
				: rowNumber == 3 ? ShiftEstimateColumn.KSM001_121
				: rowNumber == 4 ? ShiftEstimateColumn.KSM001_123
				: rowNumber == 5 ? ShiftEstimateColumn.KSM001_126
				: rowNumber == 6 ? ShiftEstimateColumn.KSM001_127
				: rowNumber == 7 ? ShiftEstimateColumn.KSM001_128
				: rowNumber == 8 ? ShiftEstimateColumn.KSM001_129
				: rowNumber == 9 ? ShiftEstimateColumn.KSM001_131
				: rowNumber == 10 ? ShiftEstimateColumn.KSM001_132
				: rowNumber == 11 ? ShiftEstimateColumn.KSM001_133
				: rowNumber == 12 ? ShiftEstimateColumn.KSM001_134
				: rowNumber == 13 ? ShiftEstimateColumn.KSM001_136
				: rowNumber == 14 ? ShiftEstimateColumn.KSM001_137
				: rowNumber == 15 ? ShiftEstimateColumn.KSM001_138
				: rowNumber == 16 ? ShiftEstimateColumn.KSM001_139
				: "");
		
		data.put(ShiftEstimateColumn.KSM001_112_2, 
				rowNumber == 0 ?  ShiftEstimateColumn.KSM001_116 
				: rowNumber == 1 ? ShiftEstimateColumn.KSM001_118 
				: rowNumber == 2 ? ShiftEstimateColumn.KSM001_120
				: rowNumber == 3 ? ShiftEstimateColumn.KSM001_122 
				: rowNumber == 4 ? ShiftEstimateColumn.KSM001_124 
				: "");
		if (rowNumber < 5) {
			data.put(ShiftEstimateColumn.KSM001_113, dataFromDB.get(rowNumber)[13]);
		}
		
		if (rowNumber > 4) {
			data.put(ShiftEstimateColumn.KSM001_113, EnumAdaptor.valueOf(((BigDecimal) dataFromDB.get(0)[rowNumber - 5]).intValue(), EstimatedCondition.class).description);
		}
		
		return data;
	}
 
	private Map<String, Object> dataContentThree(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		data.put(ShiftEstimateColumn.KSM001_141, object[0] != null ? (BigDecimal) object[0] : "");
		data.put(ShiftEstimateColumn.KSM001_142, object[1] != null ? (BigDecimal) object[1] : "");
		data.put(ShiftEstimateColumn.KSM001_143, object[2] != null ? formatTime(((BigDecimal) object[2]).intValue()) : "");
		data.put(ShiftEstimateColumn.KSM001_144, object[3] != null ? formatTime(((BigDecimal) object[3]).intValue()) : "");
		data.put(ShiftEstimateColumn.KSM001_145, object[4] != null ? formatTime(((BigDecimal) object[4]).intValue()) : "");
		data.put(ShiftEstimateColumn.KSM001_146, object[5] != null ? formatTime(((BigDecimal) object[5]).intValue()) : "");
		data.put(ShiftEstimateColumn.KSM001_147, object[6] != null ? formatTime(((BigDecimal) object[6]).intValue()) : "");
		data.put(ShiftEstimateColumn.KSM001_148, object[7] != null ?  (BigDecimal) object[7] : "");
		data.put(ShiftEstimateColumn.KSM001_149, object[8] != null ?  ShiftEstimateColumn.KSM001_196+(BigDecimal) object[8] : "");
		data.put(ShiftEstimateColumn.KSM001_150, object[9] != null ?  ShiftEstimateColumn.KSM001_196+(BigDecimal) object[9] : "");
		data.put(ShiftEstimateColumn.KSM001_151, object[10] != null ? ShiftEstimateColumn.KSM001_196+(BigDecimal) object[10] : "");
		data.put(ShiftEstimateColumn.KSM001_152, object[11] != null ? ShiftEstimateColumn.KSM001_196+(BigDecimal) object[11] : "");
		data.put(ShiftEstimateColumn.KSM001_153, object[12] != null ? (BigDecimal) object[12] : "");
		data.put(ShiftEstimateColumn.KSM001_154, object[13] != null ? (BigDecimal) object[13] : "");
		data.put(ShiftEstimateColumn.KSM001_155, object[14] != null ? (BigDecimal) object[14] : "");
		data.put(ShiftEstimateColumn.KSM001_156, object[15] != null ? (BigDecimal) object[15] : "");
		data.put(ShiftEstimateColumn.KSM001_157, object[16] != null ? (BigDecimal) object[16] : "");
		return data;
	}
	
	private String formatTime(int time){
		int hours = time / 60;
		int minutes = time % 60;
		String result = String.format("%d:%02d", hours, minutes);
		return result;
	}

}
