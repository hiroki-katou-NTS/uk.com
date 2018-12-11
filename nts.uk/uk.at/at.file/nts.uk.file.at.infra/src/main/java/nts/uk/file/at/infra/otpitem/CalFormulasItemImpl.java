package nts.uk.file.at.infra.otpitem;

import java.math.BigDecimal;
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
import nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountUnit;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberUnit;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.file.at.app.export.otpitem.CalFormulasItemColumn;
import nts.uk.file.at.app.export.otpitem.CalFormulasItemRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class CalFormulasItemImpl implements CalFormulasItemRepository {
	@PersistenceContext
	private EntityManager entityManager;

	private static final String GET_EXPORT_EXCEL_ONE = "SELECT"
			+ " 	CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.OPTIONAL_ITEM_NO" + " 	ELSE NULL"
			+ " 	END OPTIONAL_ITEM_NO " + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.OPTIONAL_ITEM_NAME"
			+ " 	ELSE NULL" + " 	END OPTIONAL_ITEM_NAME"
			+ " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.OPTIONAL_ITEM_ATR" + " 	ELSE NULL"
			+ " 	END OPTIONAL_ITEM_ATR" + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.UNIT_OF_OPTIONAL_ITEM"
			+ " 	ELSE NULL" + " 	END UNIT_OF_OPTIONAL_ITEM" + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.USAGE_ATR"
			+ " 	ELSE NULL" + " 	END USAGE_ATR" + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.PERFORMANCE_ATR"
			+ " 	ELSE NULL" + " 	END PERFORMANCE_ATR"
			+ " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.EMP_CONDITION_ATR" + " 	ELSE NULL"
			+ " 	END EMP_CONDITION_ATR" + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.NAME" + " 	ELSE NULL"
			+ " 	END NAME" + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.UPPER_LIMIT_ATR" + " 	ELSE NULL"
			+ " 	END UPPER_LIMIT_ATR" + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.UPPER_RANGE" + " 	ELSE NULL"
			+ " 	END UPPER_RANGE" + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.LOWER_LIMIT_ATR" + " 	ELSE NULL"
			+ " 	END LOWER_LIMIT_ATR" + " 	,CASE WHEN ddd.ROW_NUMBER = 1 THEN ddd.LOWER_RANGE" + " 	ELSE NULL"
			+ " 	END LOWER_RANGE				" + " 				,ddd.SYMBOL" + " 				,ddd.FORMULA_ATR"
			+ " 				,ddd.FORMULA_NAME" + " 				,ddd.CALC_ATR"
			+ " 				,ddd.DAY_ROUNDING_UNIT" + " 				,ddd.DAY_ROUNDING"
			+ " 				,ddd.MON_ROUNDING_UNIT" + " 				,ddd.MON_ROUNDING"
			+ " 				,ddd.CALC_ATR_2" + " 				,ddd.ATTENDANCE_ITEM_NAME"
			+ " 				,ddd.ATTENDANCE_ITEM_2" + " " + " FROM" + " 	(SELECT ccc.DISPORDER"
			+ " 				,ccc.OPTIONAL_ITEM_NO" + " 				, ccc.OPTIONAL_ITEM_NAME			"
			+ " 				,ccc.OPTIONAL_ITEM_ATR" + " 				,ccc.UNIT_OF_OPTIONAL_ITEM"
			+ " 				,ccc.USAGE_ATR" + " 				,ccc.PERFORMANCE_ATR"
			+ " 				,ccc.EMP_CONDITION_ATR " + " 				,ccc.NAME"
			+ " 				,ccc.UPPER_LIMIT_ATR" + " 				,ccc.UPPER_RANGE"
			+ " 				,ccc.LOWER_LIMIT_ATR" + " 				,ccc.LOWER_RANGE" + " 				,ccc.SYMBOL"
			+ " 				,ccc.FORMULA_ATR" + " 				,ccc.FORMULA_NAME" + " 				,ccc.CALC_ATR"
			+ " 				,ccc.DAY_ROUNDING_UNIT" + " 				,ccc.DAY_ROUNDING"
			+ " 				,ccc.MON_ROUNDING_UNIT" + " 				,ccc.MON_ROUNDING"
			+ " 				,ccc.CALC_ATR_2" + " 				,ccc.ATTENDANCE_ITEM_NAME"
			+ " 				,ccc.ATTENDANCE_ITEM_2" + " 				"
			+ " 				, ROW_NUMBER() OVER (PARTITION BY ccc.OPTIONAL_ITEM_NO ORDER BY ccc.DISPORDER, ccc.OPTIONAL_ITEM_NO, ccc.OPTIONAL_ITEM_NAME,ccc.OPTIONAL_ITEM_ATR ,ccc.UNIT_OF_OPTIONAL_ITEM ,ccc.USAGE_ATR ,ccc.PERFORMANCE_ATR ,ccc.EMP_CONDITION_ATR ,ccc.NAME ,ccc.UPPER_LIMIT_ATR ,ccc.UPPER_RANGE ,ccc.LOWER_LIMIT_ATR ,ccc.LOWER_RANGE ) AS ROW_NUMBER"
			+ " 	FROM" + " 	(SELECT RESULT_LEFT.OPTIONAL_ITEM_NO" + " 		,RESULT_LEFT.OPTIONAL_ITEM_NAME"
			+ " 		,RESULT_LEFT.OPTIONAL_ITEM_ATR" + " 		,RESULT_LEFT.UNIT_OF_OPTIONAL_ITEM"
			+ " 		,RESULT_LEFT.USAGE_ATR" + " 		,RESULT_LEFT.PERFORMANCE_ATR"
			+ " 		,RESULT_LEFT.EMP_CONDITION_ATR " + " 		,RESULT_LEFT.NAME"
			+ " 		,RESULT_RIGHT.UPPER_LIMIT_ATR" + " 		,RESULT_RIGHT.UPPER_RANGE"
			+ " 		,RESULT_RIGHT.LOWER_LIMIT_ATR" + " 		,RESULT_RIGHT.LOWER_RANGE"
			+ " 		,RESULT_RIGHT.SYMBOL" + " 		,RESULT_RIGHT.FORMULA_ATR" + " 		,RESULT_RIGHT.FORMULA_NAME"
			+ " 		,RESULT_RIGHT.CALC_ATR" + " 		,RESULT_RIGHT.DAY_ROUNDING_UNIT"
			+ " 		,RESULT_RIGHT.DAY_ROUNDING" + " 		,RESULT_RIGHT.MON_ROUNDING_UNIT"
			+ " 		,RESULT_RIGHT.MON_ROUNDING" + " 		,RESULT_RIGHT.CALC_ATR_2"
			+ " 		,RESULT_RIGHT.ATTENDANCE_ITEM_NAME" + " 		,RESULT_RIGHT.ATTENDANCE_ITEM_2"
			+ " 		,RESULT_RIGHT.DISPORDER" + " 	FROM" + " 		(SELECT"
			+ " 		RESULT_ONE.OPTIONAL_ITEM_NO " + " 		,RESULT_ONE.OPTIONAL_ITEM_NAME"
			+ " 		,RESULT_ONE.OPTIONAL_ITEM_ATR" + " 		,RESULT_ONE.UNIT_OF_OPTIONAL_ITEM"
			+ " 		,RESULT_ONE.USAGE_ATR" + " 		,RESULT_ONE.PERFORMANCE_ATR"
			+ " 		,RESULT_ONE.EMP_CONDITION_ATR "
			+ " 		,IIF(RESULT_ONE.EMP_CONDITION_ATR = 1,STRING_AGG (CONCAT(RESULT_ONE.EMP_CD,'+', RESULT_ONE.NAME),  '、' ), NULL) AS NAME"
			+ " 			FROM (SELECT" + " 			 oi.OPTIONAL_ITEM_NO"
			+ " 			,oi.OPTIONAL_ITEM_NAME			" + " 			,oi.OPTIONAL_ITEM_ATR"
			+ " 			,oi.UNIT_OF_OPTIONAL_ITEM" + " 			,oi.USAGE_ATR" + " 			,oi.PERFORMANCE_ATR"
			+ " 			,oi.EMP_CONDITION_ATR"
			+ " 			,IIF(oi.EMP_CONDITION_ATR = 1,ec.EMP_CD, NULL) AS EMP_CD	"
			+ " 			,IIF(oi.EMP_CONDITION_ATR = 1,emp.NAME, NULL) AS NAME" + " 		FROM"
			+ " 		KRCST_OPTIONAL_ITEM oi"
			+ " 		LEFT JOIN KRCST_APPL_EMP_CON ec ON oi.OPTIONAL_ITEM_NO = ec.OPTIONAL_ITEM_NO "
			+ " 		AND oi.CID = ec.CID" + " 		LEFT JOIN BSYMT_EMPLOYMENT emp ON ec.CID = emp.CID "
			+ " 		AND ec.EMP_CD = emp.CODE " + " 	WHERE" + " 		oi.CID = ?"
			+ " ) AS RESULT_ONE" + " GROUP BY" + " 	 RESULT_ONE.OPTIONAL_ITEM_NO" + " 	,RESULT_ONE.OPTIONAL_ITEM_NAME"
			+ " 	,RESULT_ONE.OPTIONAL_ITEM_ATR" + " 	,RESULT_ONE.UNIT_OF_OPTIONAL_ITEM" + " 	,RESULT_ONE.USAGE_ATR"
			+ " 	,RESULT_ONE.PERFORMANCE_ATR" + " 	,RESULT_ONE.EMP_CONDITION_ATR" + " ) RESULT_LEFT" + " LEFT JOIN"
			+ " (SELECT * FROM" + " (" + " 	SELECT" + " 	oi.OPTIONAL_ITEM_NO" + " 	,crr.UPPER_LIMIT_ATR"
			+ " 	,(CASE oi.OPTIONAL_ITEM_ATR" + "  		WHEN 0 THEN crr.UPPER_TIME_RANGE"
			+ "  		WHEN 1 THEN crr.UPPER_NUMBER_RANGE" + "  		WHEN 2 THEN crr.UPPER_AMOUNT_RANGE"
			+ "  	END) AS UPPER_RANGE" + "  	,crr.LOWER_LIMIT_ATR" + "  	,(CASE oi.OPTIONAL_ITEM_ATR "
			+ "  		WHEN 0 THEN crr.LOWER_TIME_RANGE" + "  		WHEN 1 THEN crr.LOWER_NUMBER_RANGE"
			+ "  		WHEN 2 THEN crr.LOWER_AMOUNT_RANGE" + "  	 END) AS LOWER_RANGE" + "   ,oif.SYMBOL"
			+ " 	,oif.FORMULA_ATR" + " 	,oif.FORMULA_NAME" + " 	,oif.CALC_ATR" + "    ,(CASE oi.OPTIONAL_ITEM_ATR"
			+ "  		WHEN 0 THEN fr_day.TIME_ROUNDING_UNIT" + "  		WHEN 1 THEN fr_day.NUMBER_ROUNDING_UNIT"
			+ "  		WHEN 2 THEN fr_day.AMOUNT_ROUNDING_UNIT" + "  	END) AS DAY_ROUNDING_UNIT"
			+ "  	,(CASE oi.OPTIONAL_ITEM_ATR " + "  		WHEN 0 THEN fr_day.TIME_ROUNDING "
			+ "  		WHEN 1 THEN fr_day.NUMBER_ROUNDING" + "  		WHEN 2 THEN fr_day.AMOUNT_ROUNDING_UNIT"
			+ "  	 END) AS DAY_ROUNDING" + "  	 " + "  	,(CASE oi.OPTIONAL_ITEM_ATR"
			+ "  		WHEN 0 THEN fr_month.TIME_ROUNDING_UNIT" + "  		WHEN 1 THEN fr_month.NUMBER_ROUNDING_UNIT"
			+ "  		WHEN 2 THEN fr_month.AMOUNT_ROUNDING_UNIT" + "  	END) AS MON_ROUNDING_UNIT"
			+ "  	,(CASE oi.OPTIONAL_ITEM_ATR" + "  		WHEN 0 THEN fr_month.TIME_ROUNDING"
			+ "  		WHEN 1 THEN fr_month.NUMBER_ROUNDING" + "  		WHEN 2 THEN fr_month.AMOUNT_ROUNDING"
			+ "  	END) AS MON_ROUNDING" + " 	,oif.CALC_ATR AS CALC_ATR_2" + " 	,fd.DISPORDER"
			+ " , IIF(oif.CALC_ATR = 0 , (STUFF((" + "  			SELECT"
			+ "  				 CASE cis.OPERATOR  WHEN 0 THEN '+'	WHEN 1 THEN '-'	WHEN 2 THEN '*'	WHEN 3 THEN '/' END  "
			+ " 				 + CASE oi.PERFORMANCE_ATR WHEN 0 THEN mai.M_ATD_ITEM_NAME 	WHEN 1 THEN dai.ATTENDANCE_ITEM_NAME END "
			+ "  			FROM" + "  				KRCMT_CALC_ITEM_SELECTION cis"
			+ "  				LEFT JOIN KRCMT_MON_ATTENDANCE_ITEM mai ON mai.CID = cis.CID AND mai.M_ATD_ITEM_ID = cis.ATTENDANCE_ITEM_ID "
			+ "  				LEFT JOIN KRCMT_DAI_ATTENDANCE_ITEM dai ON dai.CID = cis.CID AND dai.ATTENDANCE_ITEM_ID = cis.ATTENDANCE_ITEM_ID "
			+ "  			WHERE"
			+ "  				fd.FORMULA_ID = cis.FORMULA_ID 	AND fd.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO "
			+ "  			ORDER BY" + "  				cis.ATTENDANCE_ITEM_ID FOR XML PATH ('') "
			+ "  				)," + "  			1," + "  			1," + "  			'' "
			+ "  		)) , NULL) AS ATTENDANCE_ITEM_NAME" + " ," + " IIF(oif.CALC_ATR = 1 , "
			+ " CASE fs.LEFT_SET_METHOD WHEN 1 THEN  CONVERT(varchar, fs.LEFT_INPUT_VAL) + CASE fs.OPERATOR WHEN 0 THEN ' + ' WHEN 1 THEN ' - ' WHEN 2 THEN ' * ' WHEN 3 THEN ' / ' END + oif.SYMBOL ELSE oif.SYMBOL + CASE fs.OPERATOR WHEN 0 THEN ' + ' WHEN 1 THEN ' - ' WHEN 2 THEN ' * ' WHEN 3 THEN ' / ' END + CONVERT(varchar, fs.RIGHT_INPUT_VAL) END"
			+ " 	, NULL) AS ATTENDANCE_ITEM_2" + " FROM" + " 	KRCST_OPTIONAL_ITEM oi"
			+ " 	LEFT JOIN KRCST_CALC_RESULT_RANGE crr ON oi.CID = crr.CID AND oi.OPTIONAL_ITEM_NO = crr.OPTIONAL_ITEM_NO"
			+ " 	LEFT JOIN KRCMT_FORMULA_ROUNDING fr_day ON oi.CID = fr_day.CID AND oi.OPTIONAL_ITEM_NO = fr_day.OPTIONAL_ITEM_NO AND fr_day.ROUNDING_ATR = 1"
			+ " 	LEFT JOIN KRCMT_FORMULA_ROUNDING fr_month ON oi.CID = fr_month.CID AND oi.OPTIONAL_ITEM_NO = fr_month.OPTIONAL_ITEM_NO AND fr_month.ROUNDING_ATR = 2 AND fr_day.FORMULA_ID = fr_month.FORMULA_ID"
			+ " 	LEFT JOIN KRCMT_OPT_ITEM_FORMULA oif ON oi.CID = oif.CID AND oi.OPTIONAL_ITEM_NO = oif.OPTIONAL_ITEM_NO AND fr_day.FORMULA_ID = oif.FORMULA_ID"
			+ " 	LEFT JOIN KRCST_FORMULA_DISPORDER fd ON oif.OPTIONAL_ITEM_NO = fd.OPTIONAL_ITEM_NO AND oif.CID = fd.CID AND oif.FORMULA_ID = fd.FORMULA_ID"
			+ " 	LEFT JOIN KRCMT_CALC_ITEM_SELECTION cis ON fd.FORMULA_ID = cis.FORMULA_ID AND fd.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO AND cis.CID = fd.CID"
			+ " 	LEFT JOIN KRCMT_FORMULA_SETTING fs ON fd.FORMULA_ID = fs.FORMULA_ID  AND fd.OPTIONAL_ITEM_NO = fs.OPTIONAL_ITEM_NO "
			+ " WHERE" + " 	fr_day.CID = ? " + " 	) xxx"
			+ " GROUP BY OPTIONAL_ITEM_NO, SYMBOL, FORMULA_ATR, FORMULA_NAME, CALC_ATR, ATTENDANCE_ITEM_NAME, ATTENDANCE_ITEM_2, UPPER_LIMIT_ATR, UPPER_RANGE, LOWER_LIMIT_ATR, LOWER_RANGE, DAY_ROUNDING_UNIT, DAY_ROUNDING, MON_ROUNDING_UNIT, MON_ROUNDING, UPPER_LIMIT_ATR, CALC_ATR_2, DISPORDER) AS RESULT_RIGHT"
			+ " ON RESULT_LEFT.OPTIONAL_ITEM_NO = RESULT_RIGHT.OPTIONAL_ITEM_NO ) AS ccc"
			+ " )AS ddd ORDER BY ddd.OPTIONAL_ITEM_NO ASC ";

	@Override
	public List<MasterData> getDataTableOneExport(String companyId) {

		List<MasterData> datas = new ArrayList<>();

		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_ONE.toString()).setParameter(1, companyId)
				.setParameter(2, companyId);

		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		Integer optionalItemAtr = null;
		for (Object[] objects : data) {
			if (!Objects.isNull(objects[2])) {
				optionalItemAtr = ((BigDecimal) objects[2]).intValue();
			}
			datas.add(new MasterData(dataContentTableOne(objects, optionalItemAtr), null, ""));
		}
		return datas;
	}

	private Map<String, Object> dataContentTableOne(Object[] object, Integer optionalItemAtr) {
		Map<String, Object> data = new HashMap<>();
		data.put(CalFormulasItemColumn.KMK002_76, object[0] != null ? (BigDecimal) object[0] : "");
		data.put(CalFormulasItemColumn.KMK002_77, object[1] != null ? (String) object[1] : "");
		// OptionalItemAtr Enum
		data.put(CalFormulasItemColumn.KMK002_78, object[2] != null
				? EnumAdaptor.valueOf(((BigDecimal) object[2]).intValue(), OptionalItemAtr.class).description : "");
		data.put(CalFormulasItemColumn.KMK002_79, object[3] != null ? (String) object[3] : "");
		// OptionalItemUsageAtr Enum
		data.put(CalFormulasItemColumn.KMK002_80, object[4] != null
				? EnumAdaptor.valueOf(((BigDecimal) object[4]).intValue(), OptionalItemUsageAtr.class).description
				: "");
		// PerformanceAtr Enum
		data.put(CalFormulasItemColumn.KMK002_81, object[5] != null
				? EnumAdaptor.valueOf(((BigDecimal) object[5]).intValue(), PerformanceAtr.class).description : "");
		// EmpConditionAtr Enum
		data.put(CalFormulasItemColumn.KMK002_82, object[6] != null
				? EnumAdaptor.valueOf(((BigDecimal) object[6]).intValue(), EmpConditionAtr.class).description : "");

		data.put(CalFormulasItemColumn.KMK002_83, object[7] != null ? (String) object[7] : "");
		data.put(CalFormulasItemColumn.KMK002_84, object[8] != null ? (BigDecimal) object[8] : "");
		data.put(CalFormulasItemColumn.KMK002_85, object[9] != null ? (BigDecimal) object[9] : "");
		data.put(CalFormulasItemColumn.KMK002_86, object[10] != null ? (BigDecimal) object[10] : "");
		data.put(CalFormulasItemColumn.KMK002_87, object[11] != null ? (BigDecimal) object[11] : "");

		data.put(CalFormulasItemColumn.KMK002_88, (String) object[12]);

		// OptionalItemAtr Enum
		data.put(CalFormulasItemColumn.KMK002_89, object[13] != null
				? EnumAdaptor.valueOf(((BigDecimal) object[13]).intValue(), OptionalItemAtr.class).description : "");
		data.put(CalFormulasItemColumn.KMK002_90, object[14] != null ? (String) object[14] : "");
		// CalculationAtr Enum
		data.put(CalFormulasItemColumn.KMK002_91, object[15] != null
				? EnumAdaptor.valueOf(((BigDecimal) object[15]).intValue(), CalculationAtr.class).description : "");

		switch (optionalItemAtr) {
		case 0:
			data.put(CalFormulasItemColumn.KMK002_92, object[17] != null ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[17]).intValue(), Rounding.class).nameId) : "");
			break;
		case 1:
			data.put(CalFormulasItemColumn.KMK002_92, object[17] != null ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[17]).intValue(), NumberRounding.class).nameId) : "");
			break;
		case 2:
			data.put(CalFormulasItemColumn.KMK002_92,
					object[16] != null ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[17]).intValue(), AmountRounding.class).nameId) : "");
			break;
		}

		// A7_18
		switch (optionalItemAtr) {
		case 0:
			data.put(CalFormulasItemColumn.KMK002_93, object[16] != null
					? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[16]).intValue(), Unit.class).nameId) : "");
			break;
		case 1:
			data.put(CalFormulasItemColumn.KMK002_93, object[16] != null
					? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[16]).intValue(), NumberUnit.class).nameId) : "");
			break;
		case 2:
			data.put(CalFormulasItemColumn.KMK002_93,
					object[17] != null ? TextResource.localize(
							EnumAdaptor.valueOf(((BigDecimal) object[16]).intValue(), AmountUnit.class).nameId)
							: "");
			break;
		}
		// A7_19
		switch (optionalItemAtr) {
		case 0:
			data.put(CalFormulasItemColumn.KMK002_94, object[19] != null
					? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[19]).intValue(), Rounding.class).nameId) : "");
			break;
		case 1:
			data.put(CalFormulasItemColumn.KMK002_94, object[19] != null
					? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[19]).intValue(), NumberRounding.class).nameId) : "");
			break;
		case 2:
			data.put(CalFormulasItemColumn.KMK002_94,
					object[19] != null
							? TextResource.localize(
									EnumAdaptor.valueOf(((BigDecimal) object[19]).intValue(), AmountRounding.class).nameId)
							: "");
			break;
		}

		// A7_20
		switch (optionalItemAtr) {
		case 0:
			data.put(CalFormulasItemColumn.KMK002_95,
					object[19] != null ? TextResource.localize(
							EnumAdaptor.valueOf(((BigDecimal) object[18]).intValue(), Unit.class).nameId)
							: "");
			break;
		case 1:
			data.put(CalFormulasItemColumn.KMK002_95, object[18] != null
					? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) object[18]).intValue(), NumberUnit.class).nameId) : "");
			break;
		case 2:
			data.put(CalFormulasItemColumn.KMK002_95,
					object[19] != null ? TextResource.localize(
							EnumAdaptor.valueOf(((BigDecimal) object[18]).intValue(), AmountUnit.class).nameId)
							: "");
			break;
		}

		data.put(CalFormulasItemColumn.KMK002_96,
				object[20] != null ? ((BigDecimal) object[20]).intValue() == 1 ? "○" : "ー" : "");

		if (object[20] != null) {
			switch (((BigDecimal) object[20]).intValue()) {
			case 0:
				data.put(CalFormulasItemColumn.KMK002_97, object[21] != null ? ((String) object[21]) : "");
				break;
			case 1:
				data.put(CalFormulasItemColumn.KMK002_97, object[22] != null ? ((String) object[22]) : "");
				break;
			}
		}
		return data;
	}

}
