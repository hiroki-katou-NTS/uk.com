package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * The Class KrqstTimeHdAppSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrqstTimeHdAppSetDataCopyHandler extends DataCopyHandler {
	
	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KRQST_TIME_HD_APP_SET(CID, CHECK_DAY_OFF, USE_60H_HD, USE_ATTEND_BEFORE2, NAMES2_BEFORE_WORK, USE_BEFORE_GOTO_WORK, NAME_BEFORE_WORK, ACTUAL_DISPLAY_ATR, CHECK_OVER_DILIVER, USE_TIME_HD, "
			+ "USE_TIME_YEAR_HD, USE_PRIVATE_OUT, PRIVATE_OUT_NAME, USE_UNION_LEAVE, UNION_NAME_GOOUT, USE2_AFTER_LEAVE_HOME, NAMES2_AFTER_LEAVE_HOME, USE_AFTER_LEAVE_WORK, NAME_AFTER_LEAVE_HOME) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, CHECK_DAY_OFF, USE_60H_HD, USE_ATTEND_BEFORE2, NAMES2_BEFORE_WORK, USE_BEFORE_GOTO_WORK, NAME_BEFORE_WORK, ACTUAL_DISPLAY_ATR, CHECK_OVER_DILIVER, USE_TIME_HD, "
			+ "USE_TIME_YEAR_HD, USE_PRIVATE_OUT, PRIVATE_OUT_NAME, USE_UNION_LEAVE, UNION_NAME_GOOUT, USE2_AFTER_LEAVE_HOME, NAMES2_AFTER_LEAVE_HOME, USE_AFTER_LEAVE_WORK, NAME_AFTER_LEAVE_HOME "
			+ "FROM KRQST_TIME_HD_APP_SET WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_TIME_HD_APP_SET WHERE CID = ?";
	
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
					insertQuery.setParameter(i * 19 + 1, this.companyId);
					insertQuery.setParameter(i * 19 + 2, dataArr[1]);
					insertQuery.setParameter(i * 19 + 3, dataArr[2]);
					insertQuery.setParameter(i * 19 + 4, dataArr[3]);
					insertQuery.setParameter(i * 19 + 5, dataArr[4]);
					insertQuery.setParameter(i * 19 + 6, dataArr[5]);
					insertQuery.setParameter(i * 19 + 7, dataArr[6]);
					insertQuery.setParameter(i * 19 + 8, dataArr[7]);
					insertQuery.setParameter(i * 19 + 9, dataArr[8]);
					insertQuery.setParameter(i * 19 + 10, dataArr[9]);
					insertQuery.setParameter(i * 19 + 11, dataArr[10]);
					insertQuery.setParameter(i * 19 + 12, dataArr[11]);
					insertQuery.setParameter(i * 19 + 13, dataArr[12]);
					insertQuery.setParameter(i * 19 + 14, dataArr[13]);
					insertQuery.setParameter(i * 19 + 15, dataArr[14]);
					insertQuery.setParameter(i * 19 + 16, dataArr[15]);
					insertQuery.setParameter(i * 19 + 17, dataArr[16]);
					insertQuery.setParameter(i * 19 + 18, dataArr[17]);
					insertQuery.setParameter(i * 19 + 19, dataArr[18]);
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
	 * Instantiates a new krqst time hd app set data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstTimeHdAppSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}
	
}
