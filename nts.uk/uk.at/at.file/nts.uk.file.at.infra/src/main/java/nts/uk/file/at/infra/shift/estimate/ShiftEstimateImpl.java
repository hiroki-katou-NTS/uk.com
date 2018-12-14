package nts.uk.file.at.infra.shift.estimate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
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
	
	private static final String GET_EXPORT_EXCEL_SHEET_TWO = "";

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
		Object[] data = (Object[]) query.getSingleResult();
		for (int i = 0; i < data.length; i++) {
			datas.add(new MasterData(dataContentTwo(data[i],i), null, ""));
		}
		return datas;
	}

	private Map<String, Object> dataContentTwo(Object object,int rowNumber) {
		Map<String, Object> data = new HashMap<>();
		
			return data;
	}

}
