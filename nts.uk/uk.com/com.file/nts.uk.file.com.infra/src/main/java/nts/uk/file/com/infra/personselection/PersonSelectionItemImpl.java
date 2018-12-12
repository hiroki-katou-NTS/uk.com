package nts.uk.file.com.infra.personselection;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.time.GeneralDate;
import nts.uk.file.com.app.personselection.PersonSelectionItemColumn;
import nts.uk.file.com.app.personselection.PersonSelectionItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class PersonSelectionItemImpl implements PersonSelectionItemRepository {
	@PersistenceContext
	private EntityManager entityManager;

	// Export Data table

	private static final String GET_EXPORT_EXCEL = "SELECT "
			+ "CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.SELECTION_ITEM_NAME ELSE NULL END SELECTION_ITEM_NAME "
			+ ",CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.START_DATE ELSE NULL END START_DATE "
			+ ",CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.END_DATE ELSE NULL END END_DATE "
			+ ",TABLE_RESULT.INIT_SELECTION, TABLE_RESULT.SELECTION_CD, TABLE_RESULT.SELECTION_NAME, TABLE_RESULT.EXTERNAL_CD, TABLE_RESULT.MEMO "
			+ "FROM "
			+ "(SELECT si.SELECTION_ITEM_NAME, hs.START_DATE, hs.END_DATE, so.INIT_SELECTION, ss.SELECTION_CD, ss.SELECTION_NAME, ss.EXTERNAL_CD, ss.MEMO, ROW_NUMBER() OVER (PARTITION BY si.SELECTION_ITEM_NAME ORDER BY si.SELECTION_ITEM_NAME ASC) "
			+ "AS ROW_NUMBER " + "FROM " + "PPEMT_SELECTION_ITEM si "
			+ "INNER JOIN PPEMT_HISTORY_SELECTION hs ON si.SELECTION_ITEM_ID = hs.SELECTION_ITEM_ID "
			+ "INNER JOIN PPEMT_SEL_ITEM_ORDER so ON hs.HIST_ID = so.HIST_ID "
			+ "INNER JOIN PPEMT_SELECTION ss ON so.HIST_ID = ss.HIST_ID AND so.SELECTION_ID = ss.SELECTION_ID "
			+ "WHERE "
			+ "si.CONTRACT_CD = ? AND hs.CID = ? AND hs.START_DATE <=  CONVERT(DATETIME, ?date , 127) AND CONVERT(DATETIME, ?date , 127) <= hs.END_DATE ) TABLE_RESULT ORDER BY TABLE_RESULT.SELECTION_ITEM_NAME ASC;";

	@Override
	public List<MasterData> getDataExport(String contractCd, String date) {
		
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL.toString()).setParameter(1, contractCd)
				.setParameter(2, companyId).setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		if (data.isEmpty()) {
			return datas;
		} else {
			for (Object[] objects : data) {
				datas.add(new MasterData(dataContent(objects), null, ""));
			}
			return datas;
		}
	}

	private Map<String, Object> dataContent(Object[] object) {
		Map<String, Object> data = new HashMap<>();
		data.put(PersonSelectionItemColumn.CPS017_55, object[0] != null ? (String) object[0] : "");
		data.put(PersonSelectionItemColumn.CPS017_56, object[1] != null
				? GeneralDate.localDate(((Timestamp) object[1]).toLocalDateTime().toLocalDate()) : "");
		data.put(PersonSelectionItemColumn.CPS017_57, object[2] != null
				? GeneralDate.localDate(((Timestamp) object[2]).toLocalDateTime().toLocalDate()) : "");
		data.put(PersonSelectionItemColumn.CPS017_58,
				object[3] != null ? ((BigDecimal) object[3]).intValue() == 1 ? "○" : "ー" : "");
		data.put(PersonSelectionItemColumn.CPS017_59, (String) object[4]);
		data.put(PersonSelectionItemColumn.CPS017_60, (String) object[5]);
		data.put(PersonSelectionItemColumn.CPS017_61, (String) object[6]);
		data.put(PersonSelectionItemColumn.CPS017_62, (String) object[7]);
		return data;
	}

}
