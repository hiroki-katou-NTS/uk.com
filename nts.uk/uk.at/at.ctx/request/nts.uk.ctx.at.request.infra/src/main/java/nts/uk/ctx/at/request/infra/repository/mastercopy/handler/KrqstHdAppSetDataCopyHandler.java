package nts.uk.ctx.at.request.infra.repository.mastercopy.handler;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.mastercopy.CopyMethod;
import nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KrqstHdAppSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KrqstHdAppSetDataCopyHandler implements DataCopyHandler {

	/** The entity manager. */
	private EntityManager entityManager;
	
	/** The copy method. */
	private CopyMethod copyMethod;
	
	/** The company Id. */
	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KRQST_HD_APP_SET(CID, USE_60H_HOLIDAY, OBSTACLE_NAME, REGIS_SHORT_LOST_HD, HD_NAME, REGIS_LACK_PUBLIC_HD, CHANGE_WRK_HOURS, "
			+ "CKUPER_LIMIT_HALFDAY_HD, ACTUAL_DISPLAY_ATR, WRK_HOURS_USE_ATR, PRIORITY_DIGESTION_ATR, YEAR_HD_NAME, "
			+ "REGIS_NUM_YEAR_OFF, FURIKYU_NAME, REGIS_INSUFF_NUMOFREST, USE_TIME_GENER_HD, USE_TIME_YEAR_HD, "
			+ "TIME_DIGEST_NAME, ABSENTEEISM_NAME, CONCHECK_OUTLEGAL_LAW, SPECIAL_VACATION_NAME, CONCHECK_DATE_RELEASEDATE, APPLI_DATE_CONTRA_ATR, "
			+ "YEAR_RESIG_NAME, REGIS_SHORT_RESER_YEAR, HD_APPTYPE, DISPLAY_UNSELECT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, USE_60H_HOLIDAY, OBSTACLE_NAME, REGIS_SHORT_LOST_HD, HD_NAME, REGIS_LACK_PUBLIC_HD, CHANGE_WRK_HOURS, "
			+ "CKUPER_LIMIT_HALFDAY_HD, ACTUAL_DISPLAY_ATR, WRK_HOURS_USE_ATR, PRIORITY_DIGESTION_ATR, YEAR_HD_NAME, "
			+ "REGIS_NUM_YEAR_OFF, FURIKYU_NAME, REGIS_INSUFF_NUMOFREST, USE_TIME_GENER_HD, USE_TIME_YEAR_HD, "
			+ "TIME_DIGEST_NAME, ABSENTEEISM_NAME, CONCHECK_OUTLEGAL_LAW, SPECIAL_VACATION_NAME, CONCHECK_DATE_RELEASEDATE, APPLI_DATE_CONTRA_ATR, "
			+ "YEAR_RESIG_NAME, REGIS_SHORT_RESER_YEAR, HD_APPTYPE, DISPLAY_UNSELECT FROM KRQST_HD_APP_SET WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_HD_APP_SET WHERE CID = ?";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		
		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY)
				.setParameter(1, AppContexts.user().zeroCompanyIdInContract());
		Object[] zeroCompanyDatas = selectQuery.getResultList().toArray();
		
		if(zeroCompanyDatas.length == 0)
			return;

		switch (copyMethod) {
			case REPLACE_ALL:
				Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY)
					.setParameter(1, this.companyId);
				deleteQuery.executeUpdate();
			case ADD_NEW:
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
					Object[] dataArr = (Object[]) zeroCompanyDatas[i];
					insertQuery.setParameter(i * 27 + 1, this.companyId);
					insertQuery.setParameter(i * 27 + 2, dataArr[1]);
					insertQuery.setParameter(i * 27 + 3, dataArr[2]);
					insertQuery.setParameter(i * 27 + 4, dataArr[3]);
					insertQuery.setParameter(i * 27 + 5, dataArr[4]);
					insertQuery.setParameter(i * 27 + 6, dataArr[5]);
					insertQuery.setParameter(i * 27 + 7, dataArr[6]);
					insertQuery.setParameter(i * 27 + 8, dataArr[7]);
					insertQuery.setParameter(i * 27 + 9, dataArr[8]);
					insertQuery.setParameter(i * 27 + 10, dataArr[9]);
					insertQuery.setParameter(i * 27 + 11, dataArr[10]);
					insertQuery.setParameter(i * 27 + 12, dataArr[11]);
					insertQuery.setParameter(i * 27 + 13, dataArr[12]);
					insertQuery.setParameter(i * 27 + 14, dataArr[13]);
					insertQuery.setParameter(i * 27 + 15, dataArr[14]);
					insertQuery.setParameter(i * 27 + 16, dataArr[15]);
					insertQuery.setParameter(i * 27 + 17, dataArr[16]);
					insertQuery.setParameter(i * 27 + 18, dataArr[17]);
					insertQuery.setParameter(i * 27 + 19, dataArr[18]);
					insertQuery.setParameter(i * 27 + 20, dataArr[19]);
					insertQuery.setParameter(i * 27 + 21, dataArr[20]);
					insertQuery.setParameter(i * 27 + 22, dataArr[21]);
					insertQuery.setParameter(i * 27 + 23, dataArr[22]);
					insertQuery.setParameter(i * 27 + 24, dataArr[23]);
					insertQuery.setParameter(i * 27 + 25, dataArr[24]);
					insertQuery.setParameter(i * 27 + 26, dataArr[25]);
					insertQuery.setParameter(i * 27 + 27, dataArr[26]);
				}
				
				// Run insert query
				insertQuery.executeUpdate();
			case DO_NOTHING:
				// Do nothing
			default: 
				break;
		}
		
	}
	
	/**
	 * Instantiates a new krqst hd app set data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstHdAppSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

}
