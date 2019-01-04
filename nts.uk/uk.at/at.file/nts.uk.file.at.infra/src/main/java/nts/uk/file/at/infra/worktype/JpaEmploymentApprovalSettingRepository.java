package nts.uk.file.at.infra.worktype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.worktype.EmploymentApprovalSettingExportImpl;
import nts.uk.file.at.app.export.worktype.EmploymentApprovalSettingRepository;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaEmploymentApprovalSettingRepository extends JpaRepository implements EmploymentApprovalSettingRepository {

	private static final String SELECT_EMPLOYMENT_APPROVAL_SETTING = "SELECT "
			+ "	CASE WHEN MIN(ROW_NUM) = 1 THEN TEMP.CODE ELSE NULL END EMPLOYMENT_CODE, "
			+ "	CASE WHEN MIN(ROW_NUM) = 1 THEN MIN(TEMP.NAME) ELSE NULL END EMPLOYMENT_NAME, "
			+ "	TEMP.APP_TYPE, "
			+ "	TEMP.HOLIDAY_OR_PAUSE_TYPE, "
			+ "	MIN(TEMP.HOLIDAY_TYPE_USE_FLG) AS HOLIDAY_TYPE_USE_FLG, "
			+ "	STUFF((SELECT ',' + WT.CD + WT.NAME "
			+ "			FROM "
			+ "				KRQDT_APP_EMPLOY_WORKTYPE EMP_WT LEFT JOIN KSHMT_WORKTYPE WT "
			+ "					ON EMP_WT.CID = WT.CID "
			+ "					AND EMP_WT.WORK_TYPE_CODE = WT.CD "
			+ "			WHERE EMP_WT.CID = TEMP.CID "
			+ "				AND EMP_WT.EMPLOYMENT_CODE = TEMP.CODE "
			+ "				AND EMP_WT.APP_TYPE = TEMP.APP_TYPE "
			+ "				AND EMP_WT.HOLIDAY_OR_PAUSE_TYPE = TEMP.HOLIDAY_OR_PAUSE_TYPE "
			+ "		FOR XML PATH('')), 1 , 1, '') AS WORK_TYPE_NAME "
			+ "FROM "
			+ "	(SELECT "
			+ "		EMP.CID, "
			+ "		EMP.CODE, "
			+ "		EMP.NAME, "
			+ "		EMP_SET.APP_TYPE, "
			+ "		EMP_SET.HOLIDAY_OR_PAUSE_TYPE, "
			+ "		EMP_SET.HOLIDAY_TYPE_USE_FLG, "
			+ "		ROW_NUMBER() OVER (PARTITION BY EMP.CID, EMP.CODE ORDER BY EMP.CID, EMP.CODE, EMP_SET.APP_TYPE, EMP_SET.HOLIDAY_OR_PAUSE_TYPE) AS ROW_NUM "
			+ "	FROM "
			+ "		BSYMT_EMPLOYMENT EMP "
			+ "		LEFT JOIN KRQST_APP_EMPLOYMENT_SET EMP_SET "
			+ "			ON EMP.CID = EMP_SET.CID "
			+ "			AND EMP.CODE = EMP_SET.EMPLOYMENT_CODE "
			+ "	WHERE "
			+ "		EMP.CID = ?cid AND EMP_SET.DISPLAY_FLAG = 1) TEMP "
			+ "GROUP BY TEMP.CID, TEMP.CODE, TEMP.APP_TYPE, TEMP.HOLIDAY_OR_PAUSE_TYPE "
			+ "ORDER BY TEMP.CODE, TEMP.APP_TYPE, TEMP.HOLIDAY_OR_PAUSE_TYPE";
	
	@Override
	public List<MasterData> getAllEmploymentApprovalSetting(String cid) {
		Query queryString = getEntityManager().createNativeQuery(SELECT_EMPLOYMENT_APPROVAL_SETTING)
				.setParameter("cid", cid);
		List<Object[]> resultQuery = queryString.getResultList();
		List<MasterData> result = new ArrayList<MasterData>();
		for (Object[] obj : resultQuery) {
			result.add(toData(obj));
		}
		return result;
	}

	private MasterData toData(Object[] r) {
		Map<String, MasterCellData> data = new HashMap<>();
		data.put(EmploymentApprovalSettingExportImpl.KAF022_628,
				MasterCellData.builder().columnId(EmploymentApprovalSettingExportImpl.KAF022_628).value(r[0])
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmploymentApprovalSettingExportImpl.KAF022_629,
				MasterCellData.builder().columnId(EmploymentApprovalSettingExportImpl.KAF022_629).value(r[1])
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmploymentApprovalSettingExportImpl.KAF022_630,
				MasterCellData.builder().columnId(EmploymentApprovalSettingExportImpl.KAF022_630).value(r[2])
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmploymentApprovalSettingExportImpl.KAF022_631,
				MasterCellData.builder().columnId(EmploymentApprovalSettingExportImpl.KAF022_631).value(r[3])
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmploymentApprovalSettingExportImpl.KAF022_632,
				MasterCellData.builder().columnId(EmploymentApprovalSettingExportImpl.KAF022_632).value(r[4])
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(EmploymentApprovalSettingExportImpl.KAF022_633,
				MasterCellData.builder().columnId(EmploymentApprovalSettingExportImpl.KAF022_633).value(r[5])
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		
		return MasterData.builder().rowData(data).build();
	}

}
