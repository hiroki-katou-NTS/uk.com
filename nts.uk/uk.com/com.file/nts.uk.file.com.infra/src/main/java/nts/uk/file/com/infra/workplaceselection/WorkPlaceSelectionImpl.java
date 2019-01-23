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
	private static final String GET_EXPORT_EXCEL = "SELECT "
			+" 	CASE	"
			+" 		WHEN"
			+" 			RESULT.ROW_NUMBER = 1 THEN"
			+" 				RESULT.WKPCD ELSE NULL "
			+" 			END WKPCD,"
			+" 	 CASE	"
			+" 		WHEN"
			+" 			RESULT.ROW_NUMBER = 1 THEN"
			+" 				RESULT.WKP_NAME ELSE NULL "
			+" 			END WKP_NAME"
			+" 	,RESULT.SCD"
			+" 	,RESULT.BUSINESS_NAME"
			+" 	,RESULT.START_DATE"
			+" 	,RESULT.END_DATE"
			+"  ,RESULT.HIERARCHY_CD"
			+" 	,%s"
			+" FROM"
			+" 	(SELECT"
			+" 		WKPCD"
			+" 		, WKP_NAME"
			+" 		, SCD"
			+" 		, BUSINESS_NAME "
			+" 		, START_DATE"
			+" 		, END_DATE, HIERARCHY_CD "
			+" 		, %s"
			+" 		, ROW_NUMBER () OVER ( PARTITION BY WKPCD, WKP_NAME ORDER BY HIERARCHY_CD, SCD  ASC ) AS ROW_NUMBER"
			+" 	FROM ("
			+" 					SELECT wm.WKPCD "
			+" 					,CASE	"
			+" 						WHEN" 
			+" 								bwh.END_DATE = CONVERT ( DATETIME, '9999-12-31T00:00:00.000Z', 127 ) THEN"
			+" 								wm.WKP_NAME ELSE 'マスタ未登録' "
			+" 							END WKP_NAME "
			+" 						, edm.SCD , ps.BUSINESS_NAME , wi.START_DATE, wi.END_DATE , AVAILABILITY, wci.HIERARCHY_CD, wkf.FUNCTION_NO					"
			+" 					FROM "
			+"					SACMT_WORKPLACE_MANAGER wi "
			+"						INNER JOIN BSYMT_WORKPLACE_INFO wm  ON wm.WKPID = wi.WKP_ID  "
			+"						INNER JOIN (SELECT *, ROW_NUMBER() OVER ( PARTITION BY CID, WKPID ORDER BY END_DATE DESC ) AS RN FROM BSYMT_WORKPLACE_HIST) bwh ON wm.WKPID = bwh.WKPID AND wm.HIST_ID = bwh.HIST_ID	AND bwh.RN = 1 "
			+"						INNER JOIN BSYMT_EMP_DTA_MNG_INFO edm ON wi.SID = edm.SID "
			+"						INNER JOIN BPSMT_PERSON ps ON edm.PID = ps.PID "
			+"						INNER JOIN KASMT_WORKPLACE_AUTHORITY kwa ON wi.WKP_MANAGER_ID = kwa.ROLE_ID "
			+"						INNER JOIN KASMT_WORPLACE_FUNCTION wkf on wkf.FUNCTION_NO = kwa.FUNCTION_NO "
			+"						INNER JOIN (SELECT *, ROW_NUMBER() OVER ( PARTITION BY CID ORDER BY END_DATE DESC ) AS RN FROM BSYMT_WKP_CONFIG) bwc ON bwh.CID = bwc.CID AND bwc.RN = 1 "
			+"						INNER JOIN BSYMT_WKP_CONFIG_INFO wci ON bwc.CID = wci.CID AND bwc.HIST_ID = wci.HIST_ID AND wci.WKPID = bwh.WKPID "
			+"					WHERE "
			+" 						wi.START_DATE <=  ?baseDate AND"
			+" 						wi.END_DATE   >=  ?baseDate AND wm.CID = ?cid"
			+" 				)"
			+" 				AS sourceTable PIVOT ("
			+" 			MAX(AVAILABILITY)"
			+" 			FOR [FUNCTION_NO] IN (%s)"
			+" 	) AS pvt ) AS RESULT ORDER BY RESULT.HIERARCHY_CD";

	@Override 
	public List<MasterData> getDataExport(String companyId, List<WorkPlaceFunction> workPlaceFunction, String baseDate) {
		String functions = workPlaceFunction.stream().map(x -> x.getFunctionNo().v().toString())
				.collect(Collectors.toList()).stream().collect(Collectors.joining("], [", "[", "]"));
		String functionsResult = workPlaceFunction.stream().map(x -> x.getFunctionNo().v().toString())
				.collect(Collectors.toList()).stream().collect(Collectors.joining("], RESULT.[", "RESULT.[", "]"));
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(String.format(GET_EXPORT_EXCEL, functionsResult, functions, functions).toString())
				.setParameter("cid", companyId).setParameter("baseDate", baseDate);

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
                .value(object[0] != null ? (String) object[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_28, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_28)
                .value(object[1] != null ? (String) object[1] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_29, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_29)
                .value(object[2] != null ? (String) object[2] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_30, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_30)
                .value(object[3] != null ?  (String) object[3] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_31, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_31)
                .value(object[4] != null ?  df.format(object[4]).toString() : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(WorkPlaceSelectionColumn.CMM051_32, MasterCellData.builder()
                .columnId(WorkPlaceSelectionColumn.CMM051_32)
                .value(object[5] != null ?  df.format(object[5]).toString() :"")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            
            for (int i = 0; i < workPlaceFunction.size(); i++) {
    			data.put(workPlaceFunction.get(i).getFunctionNo().v().toString(), MasterCellData.builder()
    	                .columnId(WorkPlaceSelectionColumn.CMM051_32_2)
    	                .value(((BigDecimal) object[i + 7]).intValue() == 1 ? "○" : "-")
    	                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
    	                .build());
    		}
            
		return MasterData.builder().rowData(data).build(); 
	}

}
