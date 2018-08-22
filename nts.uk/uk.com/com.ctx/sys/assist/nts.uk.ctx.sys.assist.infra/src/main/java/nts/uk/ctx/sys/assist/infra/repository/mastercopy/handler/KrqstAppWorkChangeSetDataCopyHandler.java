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
 * The Class KrqstAppWorkChangeSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrqstAppWorkChangeSetDataCopyHandler extends DataCopyHandler {
	
	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KRQST_APP_WORK_CHANGE_SET(CID, EXCLUDE_HOLIDAY, WORK_CHANGE_TIME_ATR, DISPLAY_RESULT_ATR, INIT_DISPLAY_WORKTIME, COMMENT_CONTENT1, COMMENT_FONT_WEIGHT1, COMMENT_FONT_COLOR1, COMMENT_CONTENT2, COMMENT_FONT_WEIGHT2, COMMENT_FONT_COLOR2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, EXCLUDE_HOLIDAY, WORK_CHANGE_TIME_ATR, DISPLAY_RESULT_ATR, INIT_DISPLAY_WORKTIME, COMMENT_CONTENT1, COMMENT_FONT_WEIGHT1, COMMENT_FONT_COLOR1, COMMENT_CONTENT2, COMMENT_FONT_WEIGHT2, COMMENT_FONT_COLOR2 FROM KRQST_APP_WORK_CHANGE_SET WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_APP_WORK_CHANGE_SET WHERE CID = ?";
	
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
					insertQuery.setParameter(i * 11 + 1, this.companyId);
					insertQuery.setParameter(i * 11 + 2, dataArr[1]);
					insertQuery.setParameter(i * 11 + 3, dataArr[2]);
					insertQuery.setParameter(i * 11 + 4, dataArr[3]);
					insertQuery.setParameter(i * 11 + 5, dataArr[4]);
					insertQuery.setParameter(i * 11 + 6, dataArr[5]);
					insertQuery.setParameter(i * 11 + 7, dataArr[6]);
					insertQuery.setParameter(i * 11 + 8, dataArr[7]);
					insertQuery.setParameter(i * 11 + 9, dataArr[8]);
					insertQuery.setParameter(i * 11 + 10, dataArr[9]);
					insertQuery.setParameter(i * 11 + 11, dataArr[10]);
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
	 * Instantiates a new krqst app work change set data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstAppWorkChangeSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

}
