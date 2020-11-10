package nts.uk.file.com.infra.personselection;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.file.com.app.personselection.PersonSelectionItemColumn;
import nts.uk.file.com.app.personselection.PersonSelectionItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class PersonSelectionItemImpl extends JpaRepository implements PersonSelectionItemRepository {

	// Export Data table

	private static final String GET_EXPORT_EXCEL = "SELECT" 
			+ " CASE" 
			+ " WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN"
					+ " TABLE_RESULT.SELECTION_ITEM_NAME ELSE NULL" 
					+ " END SELECTION_ITEM_NAME," 
			+ " CASE"
			+ " WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN" 
				+ " TABLE_RESULT.START_DATE ELSE NULL" 
				+ " END START_DATE,"
			+ " CASE" 
				+ " WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN" 
				+ " TABLE_RESULT.END_DATE ELSE NULL" 
				+ " END END_DATE,"
			+ " TABLE_RESULT.INIT_SELECTION," 
			+ " TABLE_RESULT.SELECTION_CD," 
			+ " TABLE_RESULT.SELECTION_NAME,"
			+ " TABLE_RESULT.EXTERNAL_CD," 
			+ " TABLE_RESULT.MEMO, TABLE_RESULT.DISPORDER" + " FROM"
			+ " (" 
				+ " SELECT"
				+ " si.SELECTION_ITEM_NAME," 
				+ " hs.START_DATE," 
				+ " hs.END_DATE," 
				+ " so.INIT_SELECTION,"
				+ " ss.SELECTION_CD," 
				+ " ss.SELECTION_NAME," 
				+ " ss.EXTERNAL_CD," 
				+ " ss.MEMO,"
				+ " ROW_NUMBER () OVER ( PARTITION BY si.SELECTION_ITEM_NAME ORDER BY si.SELECTION_ITEM_NAME, so.DISPORDER ASC , hs.START_DATE DESC) AS ROW_NUMBER, so.DISPORDER"
				+ " FROM" 
				+ " PPEMT_SELECTION_ITEM si"
				+ " INNER JOIN PPEMT_HISTORY_SELECTION hs ON si.SELECTION_ITEM_ID = hs.SELECTION_ITEM_ID"
				+ " AND hs.START_DATE <= CONVERT ( DATETIME, ?date, 111 )" 
				+ " AND hs.CID = ?companyId"
				+ " AND CONVERT ( DATETIME, ?date, 111 ) <= hs.END_DATE"
				+ " INNER JOIN PPEMT_SEL_ITEM_ORDER so ON hs.HIST_ID = so.HIST_ID"
				+ " INNER JOIN PPEMT_SELECTION ss ON so.HIST_ID = ss.HIST_ID" 
				+ " AND so.SELECTION_ID = ss.SELECTION_ID"
				+ " WHERE" 
				+ " si.CONTRACT_CD = ?contractCd" 
			+ " ) TABLE_RESULT ORDER BY TABLE_RESULT.SELECTION_ITEM_NAME, TABLE_RESULT.DISPORDER ASC ,TABLE_RESULT.START_DATE DESC";

	@Override
	public List<MasterData> getDataExport(String contractCd, String date) {

		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Query query = getEntityManager().createNativeQuery(GET_EXPORT_EXCEL.toString())
				.setParameter("contractCd", contractCd).setParameter("companyId", companyId).setParameter("date", date);
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
			for (Object[] objects : data) {
				datas.add(dataContent(objects));
			}
			return datas;
	}
	
	private MasterData dataContent(Object[] object) {
		Map<String,MasterCellData> data = new HashMap<>();
		data.put(PersonSelectionItemColumn.CPS017_55, MasterCellData.builder()
                .columnId(PersonSelectionItemColumn.CPS017_55)
                .value(object[0] != null ? (String) object[0] : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(PersonSelectionItemColumn.CPS017_56, MasterCellData.builder()
                .columnId(PersonSelectionItemColumn.CPS017_56)
                .value(object[1] != null ? GeneralDate.localDate(((Timestamp) object[1]).toLocalDateTime().toLocalDate()) : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(PersonSelectionItemColumn.CPS017_57, MasterCellData.builder()
                .columnId(PersonSelectionItemColumn.CPS017_57)
                .value(object[2] != null ? GeneralDate.localDate(((Timestamp) object[2]).toLocalDateTime().toLocalDate()) : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(PersonSelectionItemColumn.CPS017_58, MasterCellData.builder()
                .columnId(PersonSelectionItemColumn.CPS017_58)
                .value(object[3] != null ? ((BigDecimal) object[3]).intValue() == 1 ? "○" : "-" : "")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(PersonSelectionItemColumn.CPS017_59, MasterCellData.builder()
                .columnId(PersonSelectionItemColumn.CPS017_59)
                .value((String) object[4])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(PersonSelectionItemColumn.CPS017_60, MasterCellData.builder()
                .columnId(PersonSelectionItemColumn.CPS017_60)
                .value((String) object[5])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(PersonSelectionItemColumn.CPS017_61, MasterCellData.builder()
                .columnId(PersonSelectionItemColumn.CPS017_61)
                .value((String) object[6])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(PersonSelectionItemColumn.CPS017_62, MasterCellData.builder()
                .columnId(PersonSelectionItemColumn.CPS017_62)
                .value((String) object[7])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		
		return MasterData.builder().rowData(data).build();
	}

}
