package nts.uk.file.at.infra.otpitem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.optitem.UnitOfOptionalItem;
import nts.uk.file.at.app.export.otpitem.CalFormulasItemColumn;
import nts.uk.file.at.app.export.otpitem.CalFormulasItemRepository;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class CalFormulasItemImpl implements CalFormulasItemRepository {
	@PersistenceContext
	private EntityManager entityManager;

//	@Override
//	@SneakyThrows
//	public List<CalFormulasItemExportData> findAllCalFormulasItem(String companyId, String languageId) {
//		try (val stmt = this.connection().prepareStatement(
//				"SELECT ec.EMP_CD , kroi.OPTIONAL_ITEM_NO, kroi.OPTIONAL_ITEM_NAME, kroi.EMP_CONDITION_ATR FROM "
//						+ "KRCST_OPTIONAL_ITEM oi " + "LEFT JOIN KRCST_CALC_RESULT_RANGE rr ON kroi.CID = rr.CID "
//						+ "AND kroi.OPTIONAL_ITEM_NO = rr.OPTIONAL_ITEM_NO "
//						+ "LEFT JOIN KRCST_APPL_EMP_CON ec ON rr.OPTIONAL_ITEM_NO = ec.OPTIONAL_ITEM_NO AND rr.CID = ec.CID"
//						+ "where kroi.CID = ? AND ec.EMP_CD IS NOT NULL ORDER BY kroi.OPTIONAL_ITEM_NO ASC")) {
//			stmt.setString(1, companyId);
//			return new NtsResultSet(stmt.executeQuery()).getList(rec -> {
//				CalFormulasItemExportData item = new CalFormulasItemExportData();
//				return toReportData(rec);
//			});
//		} 
//
//	}

//	private CalFormulasItemExportData toReportData(NtsResultRecord rec) {
//		Map<String, String> empConditions = new HashMap<>();
//		empConditions.put(rec.getString("EMP_CD"), rec.getString("EMP_APPL_ATR"));
//		CalFormulasItemExportData item = new CalFormulasItemExportData(
//				rec.getString("OPTIONAL_ITEM_NO"),
//				rec.getString("OPTIONAL_ITEM_NAME"), 
//				rec.getString("EMP_CONDITION_ATR"), 
//				empConditions);
//		return item;
//	}

	// Export Data table
//		@Override
//		@SneakyThrows
//		public List<CalFormulasItemTableExportData> findAllCalFormulasTableItem(String companyId, String languageId) {
//			try (val stmt = this.connection().prepareStatement(
//					"SELECT "
//					+"kroi.OPTIONAL_ITEM_NO, kroi.OPTIONAL_ITEM_NAME, oif.SYMBOL, fd.DISPORDER "
//					+", fr.ROUNDING_ATR "
//					+", kroi.PERFORMANCE_ATR "
//					+", kroi.EMP_CONDITION_ATR, kroi.PERFORMANCE_ATR, oif.FORMULA_ID, fd.DISPORDER "
//					+", oif.FORMULA_ATR, oif.FORMULA_NAME, oif.CALC_ATR "
//					+", fr.NUMBER_ROUNDING, fr.TIME_ROUNDING, fr.AMOUNT_ROUNDING, fr.NUMBER_ROUNDING_UNIT, fr.TIME_ROUNDING_UNIT, fr.AMOUNT_ROUNDING_UNIT "
//					+", cis.MINUS_SEGMENT, cis.OPERATOR "
//					+", fs.LEFT_FORMULA_ITEM_ID, fs.LEFT_SET_METHOD, fs.LEFT_INPUT_VAL, fs.RIGHT_FORMULA_ITEM_ID, fs.RIGHT_SET_METHOD, fs.RIGHT_INPUT_VAL "
//					+"FROM "
//					+"KRCST_OPTIONAL_ITEM oi "
//					+"INNER JOIN KRCMT_OPT_ITEM_FORMULA oif ON kroi.CID = oif.CID AND kroi.OPTIONAL_ITEM_NO = oif.OPTIONAL_ITEM_NO "
//					+"INNER JOIN KRCST_FORMULA_DISPORDER fd ON oif.OPTIONAL_ITEM_NO = fd.OPTIONAL_ITEM_NO AND oif.CID = fd.CID AND oif.FORMULA_ID = fd.FORMULA_ID " 
//					+"INNER JOIN KRCMT_FORMULA_ROUNDING fr ON fd.FORMULA_ID = fr.FORMULA_ID and fd.OPTIONAL_ITEM_NO = fr.OPTIONAL_ITEM_NO "
//					+"INNER JOIN KRCMT_CALC_ITEM_SELECTION cis ON fd.FORMULA_ID = cis.FORMULA_ID AND fd.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO "
//					+"LEFT JOIN KRCMT_FORMULA_SETTING fs ON (fr.FORMULA_ID = fs.LEFT_FORMULA_ITEM_ID  OR fr.FORMULA_ID = fs.RIGHT_FORMULA_ITEM_ID) "
//					+"WHERE kroi.CID = ? "
//					)) {
//				stmt.setString(1, companyId);
//				return new NtsResultSet(stmt.executeQuery()).getList(rec -> {
//					CalFormulasItemTableExportData item = new CalFormulasItemTableExportData();
//					return toReportDataTable(rec);
//				});
//			}
//
//		}

//		private CalFormulasItemTableExportData toReportDataTable(NtsResultRecord rec) {
//			CalFormulasItemTableExportData item = new CalFormulasItemTableExportData(
//					//
//			);
//			return null;
//		}
		private static final String GET_EXPORT_EXCEL_ONE = "SELECT "
				+ "koi.OPTIONAL_ITEM_NO "
				+ ", koi.OPTIONAL_ITEM_NAME "
				+ ", koi.USAGE_ATR "
				+ ", koi.OPTIONAL_ITEM_ATR "
				+ ", koi.PERFORMANCE_ATR "
				+ ", koi.EMP_CONDITION_ATR "
				+ ", koi.UNIT_OF_OPTIONAL_ITEM "
				+ ", kcrr.UPPER_LIMIT_ATR "
				+ ", kcrr.UPPER_TIME_RANGE "
				+ ", kcrr.UPPER_NUMBER_RANGE "
				+ ", kcrr.UPPER_AMOUNT_RANGE "
				+ ", kcrr.LOWER_LIMIT_ATR "
				+ ", kcrr.LOWER_TIME_RANGE "
				+ ", kcrr.LOWER_NUMBER_RANGE "
				+ ", kcrr.LOWER_AMOUNT_RANGE "
				+ "FROM "
				+ "KRCST_OPTIONAL_ITEM koi "
				+ "LEFT JOIN KRCST_CALC_RESULT_RANGE kcrr ON koi.CID = kcrr.CID  "
				+ "AND koi.OPTIONAL_ITEM_NO = kcrr.OPTIONAL_ITEM_NO " 
				+ "WHERE "
				+ "koi.CID = ? "
				+ "ORDER BY "
				+ "koi.OPTIONAL_ITEM_NO ASC";
		
		private static final String GET_EXPORT_EXCEL_TWO = "SELECT "
				+ "oi.OPTIONAL_ITEM_NO, "
				+ "oi.OPTIONAL_ITEM_NAME, "
				+ "oi.EMP_CONDITION_ATR, "
				+ "ec.EMP_CD, "
				+ "emp.NAME, "
				+ "ec.EMP_APPL_ATR " 
				+ "FROM "
				+ "KRCST_OPTIONAL_ITEM oi "
				+ "INNER JOIN KRCST_CALC_RESULT_RANGE rr ON oi.CID = rr.CID " 
				+ "AND oi.OPTIONAL_ITEM_NO = rr.OPTIONAL_ITEM_NO "
				+ "INNER JOIN KRCST_APPL_EMP_CON ec ON rr.OPTIONAL_ITEM_NO = ec.OPTIONAL_ITEM_NO " 
				+ "AND rr.CID = ec.CID "
				+ "INNER JOIN BSYMT_EMPLOYMENT emp ON ec.CID = emp.CID " 
				+ "AND ec.EMP_CD = emp.CODE " 
				+ "WHERE "
					+ "oi.CID = ? " 
				+ "ORDER BY "
					+ "oi.OPTIONAL_ITEM_NO ASC";
		
		private static final String GET_EXPORT_EXCEL_THREE = "SELECT "
				+ "kroi.OPTIONAL_ITEM_NO, kroi.OPTIONAL_ITEM_NAME, oif.SYMBOL, fd.DISPORDER "
				+ ", fr.ROUNDING_ATR " 
				+ ", kroi.PERFORMANCE_ATR " 
				+ ", kroi.EMP_CONDITION_ATR, kroi.PERFORMANCE_ATR, oif.FORMULA_ID, fd.DISPORDER "
				+ ", oif.FORMULA_ATR, oif.FORMULA_NAME, oif.CALC_ATR "
				+ ", fr.NUMBER_ROUNDING, fr.TIME_ROUNDING, fr.AMOUNT_ROUNDING, fr.NUMBER_ROUNDING_UNIT, fr.TIME_ROUNDING_UNIT, fr.AMOUNT_ROUNDING_UNIT " 
				+ ", cis.MINUS_SEGMENT, cis.OPERATOR "
				+ ", fs.LEFT_FORMULA_ITEM_ID, fs.LEFT_SET_METHOD, fs.LEFT_INPUT_VAL, fs.RIGHT_FORMULA_ITEM_ID, fs.RIGHT_SET_METHOD, fs.RIGHT_INPUT_VAL "
				+ "FROM "
				+ "KRCST_OPTIONAL_ITEM kroi "
				+ "INNER JOIN KRCMT_OPT_ITEM_FORMULA oif ON kroi.CID = oif.CID AND kroi.OPTIONAL_ITEM_NO = oif.OPTIONAL_ITEM_NO " 
				+ "INNER JOIN KRCST_FORMULA_DISPORDER fd ON oif.OPTIONAL_ITEM_NO = fd.OPTIONAL_ITEM_NO AND oif.CID = fd.CID AND oif.FORMULA_ID = fd.FORMULA_ID "  
				+ "INNER JOIN KRCMT_FORMULA_ROUNDING fr ON fd.FORMULA_ID = fr.FORMULA_ID and fd.OPTIONAL_ITEM_NO = fr.OPTIONAL_ITEM_NO " 
				+ "INNER JOIN KRCMT_CALC_ITEM_SELECTION cis ON fd.FORMULA_ID = cis.FORMULA_ID AND fd.OPTIONAL_ITEM_NO = cis.OPTIONAL_ITEM_NO "  
				+ "LEFT JOIN KRCMT_FORMULA_SETTING fs ON (fr.FORMULA_ID = fs.LEFT_FORMULA_ITEM_ID  OR fr.FORMULA_ID = fs.RIGHT_FORMULA_ITEM_ID) "
				+ "WHERE kroi.OPTIONAL_ITEM_NO = 100 AND kroi.CID = ? ORDER BY kroi.OPTIONAL_ITEM_NO ASC ";
	
		@Override
		public List<MasterData> getDataTableOneExport(String companyId) {

			List<MasterData> datas = new ArrayList<>();
			
			Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_ONE.toString()).setParameter(1,
					companyId);

			@SuppressWarnings("unchecked")
			List<Object[]> data = query.getResultList();
			for (Object[] objects : data) {
				datas.add(new MasterData(dataContentTableOne(objects), null, ""));
			}
			return datas;
		}

	private Map<String, Object> dataContentTableOne(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		data.put(CalFormulasItemColumn.KMK002_74, (BigDecimal) object[0]);
		data.put(CalFormulasItemColumn.KMK002_75, (String) object[1]);
		data.put(CalFormulasItemColumn.KMK002_76,
				EnumAdaptor.valueOf(((BigDecimal) object[3]).intValue(), OptionalItemAtr.class).description);
		data.put(CalFormulasItemColumn.KMK002_77,
				EnumAdaptor.valueOf(((BigDecimal) object[2]).intValue(), OptionalItemUsageAtr.class).description);
		data.put(CalFormulasItemColumn.KMK002_78,
				EnumAdaptor.valueOf(((BigDecimal) object[4]).intValue(), PerformanceAtr.class).description);
		data.put(CalFormulasItemColumn.KMK002_79,
				EnumAdaptor.valueOf(((BigDecimal) object[5]).intValue(), EmpConditionAtr.class).description);
		data.put(CalFormulasItemColumn.KMK002_80, (String) object[6]);
		data.put(CalFormulasItemColumn.KMK002_81, ((BigDecimal) object[7]).intValue() == 1 ? "✓" : " ");

		// Check CalcResultRange
		switch (((BigDecimal) object[3]).intValue()) {
		case 0:
			data.put(CalFormulasItemColumn.KMK002_82, (BigDecimal) object[8]);
			break;
		case 1:
			data.put(CalFormulasItemColumn.KMK002_82, (BigDecimal) object[9]);
			break;
		case 2:
			data.put(CalFormulasItemColumn.KMK002_82, (BigDecimal) object[10]);
			break;
		}

		data.put(CalFormulasItemColumn.KMK002_83, ((BigDecimal) object[11]).intValue() == 1 ? "✓" : " ");
		// Check CalcResultRange
		switch (((BigDecimal) object[3]).intValue()) {
		case 0:
			data.put(CalFormulasItemColumn.KMK002_84, (BigDecimal) object[12]);
			break;
		case 1:
			data.put(CalFormulasItemColumn.KMK002_84, (BigDecimal) object[13]);
			break;
		case 2:
			data.put(CalFormulasItemColumn.KMK002_84, (BigDecimal) object[14]);
			break;
		}
		return data;
	}
		
		
	@Override
	public List<MasterData> getDataTableTwoExport(String companyId) {

		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_TWO.toString()).setParameter(1,
				companyId);

		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(dataContentTableTwo(objects), null, ""));
		}
		return datas;
	}

	private Map<String, Object> dataContentTableTwo(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		data.put(CalFormulasItemColumn.KMK002_85, (BigDecimal) object[0]);
		data.put(CalFormulasItemColumn.KMK002_86, (String) object[1]);
		data.put(CalFormulasItemColumn.KMK002_87, EnumAdaptor.valueOf(((BigDecimal) object[2]).intValue(), EmpConditionAtr.class).description);
		data.put(CalFormulasItemColumn.KMK002_104, (String) object[3]);
		data.put(CalFormulasItemColumn.KMK002_105, (String) object[4]);
		data.put(CalFormulasItemColumn.KMK002_106, ((BigDecimal) object[5]).intValue() == 1 ? "○" : "ー");
		return data;
	}
	
	@Override
	public List<MasterData> getDataTableThreeExport(String companyId) {

		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_THREE.toString()).setParameter(1,
				companyId);

		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(new MasterData(dataContentTableThree(objects), null, ""));
		}
		return datas;
	}

	private Map<String, Object> dataContentTableThree(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		
		return data;
	}
}
