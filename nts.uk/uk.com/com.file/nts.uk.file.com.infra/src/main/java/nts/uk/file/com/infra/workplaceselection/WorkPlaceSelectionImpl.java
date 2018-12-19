package nts.uk.file.com.infra.workplaceselection;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceFunction;
import nts.uk.file.com.app.workplaceselection.WorkPlaceSelectionColumn;
import nts.uk.file.com.app.workplaceselection.WorkPlaceSelectionRepository;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class WorkPlaceSelectionImpl implements WorkPlaceSelectionRepository {
	@PersistenceContext
	private EntityManager entityManager;

	// Export Data table

	// @Override

	// @SneakyThrows
	// public List<WorkPlaceSelectionExportData>
	// findAllWorkPlaceSelection(String companyId,
	// List<WorkPlaceFunction> workPlaceFunction) {
	// String functions = workPlaceFunction.stream().map(x ->
	// x.getFunctionNo().v().toString())
	// .collect(Collectors.toList()).stream().collect(Collectors.joining("], [",
	// "[", "]"));
	// String SQL = "SELECT WKPCD , WKP_NAME , SCD , BUSINESS_NAME , START_DATE,
	// END_DATE , %s " + "FROM ( "
	// + "SELECT wm.WKPCD , wm.WKP_NAME , edm.SCD , ps.BUSINESS_NAME ,
	// wi.START_DATE, wi.END_DATE , AVAILABILITY, wkf.FUNCTION_NO "
	// + "FROM " + "BSYMT_WORKPLACE_INFO wm " + "LEFT JOIN
	// SACMT_WORKPLACE_MANAGER wi ON wm.WKPID = wi.WKP_ID "
	// + "LEFT JOIN BSYMT_EMP_DTA_MNG_INFO edm ON wi.SID = edm.SID "
	// + "LEFT JOIN BPSMT_PERSON ps ON edm.PID = ps.PID "
	// + "INNER JOIN KASMT_WORKPLACE_AUTHORITY kwa ON wi.WKP_MANAGER_ID =
	// kwa.ROLE_ID AND wm.CID = kwa.CID "
	// + "INNER JOIN KASMT_WORPLACE_FUNCTION wkf on wkf.FUNCTION_NO =
	// kwa.FUNCTION_NO " + "WHERE wm.CID = ? "
	// + ") " + "AS sourceTable PIVOT ( " + "MAX(AVAILABILITY) " + "FOR
	// [FUNCTION_NO] IN (%s) "
	// + ") AS pvt ORDER BY WKPCD, SCD, START_DATE ASC ";
	//
	// try (val stmt = this.connection().prepareStatement(String.format(SQL,
	// functions, functions))) {
	// stmt.setString(1, companyId);
	// return new NtsResultSet(stmt.executeQuery()).getList(rec -> { //
	// WorkPlaceSelectionExportData item = new //
	// WorkPlaceSelectionExportData();
	// return toReportDataTable(rec, workPlaceFunction);
	// });
	// }
	// }

	// private WorkPlaceSelectionExportData toReportDataTable(NtsResultRecord
	// rec,
	// List<WorkPlaceFunction> workPlaceFunction) {
	// Map<String, String> values = new HashMap<String, String>();
	//
	// for (int i = 0; i < workPlaceFunction.size(); i++) {
	// values.put(workPlaceFunction.get(i).getFunctionNo().v().toString(),
	// rec.getString((i + 1) + ""));
	// }
	// WorkPlaceSelectionExportData item = new
	// WorkPlaceSelectionExportData(rec.getString("WKPCD"),
	// rec.getString("WKP_NAME"), rec.getString("SCD"),
	// rec.getString("BUSINESS_NAME"),
	// rec.getGeneralDate("START_DATE"), rec.getGeneralDate("END_DATE"),
	// values);
	// return item;
	// }

	private static final String GET_EXPORT_EXCEL = "SELECT WKPCD , WKP_NAME , SCD , BUSINESS_NAME , START_DATE, END_DATE , %s "
			+ "FROM ( "
			+ "SELECT wm.WKPCD , wm.WKP_NAME , edm.SCD , ps.BUSINESS_NAME , wi.START_DATE, wi.END_DATE , AVAILABILITY, wkf.FUNCTION_NO "
			+ "FROM " + "BSYMT_WORKPLACE_INFO wm " + "LEFT JOIN SACMT_WORKPLACE_MANAGER wi ON wm.WKPID = wi.WKP_ID "
			+ "LEFT JOIN BSYMT_EMP_DTA_MNG_INFO edm ON wi.SID = edm.SID "
			+ "LEFT JOIN BPSMT_PERSON ps ON edm.PID = ps.PID "
			+ "INNER JOIN KASMT_WORKPLACE_AUTHORITY kwa ON wi.WKP_MANAGER_ID = kwa.ROLE_ID AND wm.CID = kwa.CID "
			+ "INNER JOIN KASMT_WORPLACE_FUNCTION wkf on wkf.FUNCTION_NO = kwa.FUNCTION_NO " + "WHERE wm.CID = ? "
			+ ") " + "AS sourceTable PIVOT ( " + "MAX(AVAILABILITY) " + "FOR [FUNCTION_NO] IN (%s) "
			+ ") AS pvt ORDER BY WKPCD, SCD, START_DATE ASC";

	@Override
	public List<MasterData> getDataExport(String companyId, List<WorkPlaceFunction> workPlaceFunction) {
		String functions = workPlaceFunction.stream().map(x -> x.getFunctionNo().v().toString())
				.collect(Collectors.toList()).stream().collect(Collectors.joining("], [", "[", "]"));
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(String.format(GET_EXPORT_EXCEL, functions, functions).toString())
				.setParameter(1, companyId);

		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		for (Object[] objects : data) {
			datas.add(dataContent(objects, workPlaceFunction));
		}
		return datas;
	}

	private MasterData dataContent(Object[] object, List<WorkPlaceFunction> workPlaceFunction) {
		Map<String,MasterCellData> data = new HashMap<>();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		
		data.put(WorkPlaceSelectionColumn.CMM051_27, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_27)
                .value((String) object[0])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_28, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_28)
                .value((String) object[1])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_29, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_29)
                .value((String) object[2])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_30, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_30)
                .value((String) object[3])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_31, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_31)
                .value(df.format(object[4]).toString())
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_32, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_32)
                .value(df.format(object[5]).toString())
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            
            for (int i = 0; i < workPlaceFunction.size(); i++) {
    			data.put(workPlaceFunction.get(i).getFunctionNo().v().toString(), MasterCellData.builder()
    	                .columnId(WorkPlaceSelectionColumn.CMM051_32)
    	                .value(((BigDecimal) object[i + 6]).intValue() == 1 ? "○" : "ー")
    	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
    	                .build());
    		}
            
		return MasterData.builder().rowData(data).build(); 
	}

}
