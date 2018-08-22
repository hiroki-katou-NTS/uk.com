package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * The Class KrqstTripRequestSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrqstTripRequestSetDataCopyHandler extends DataCopyHandler {
	
	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KRQST_TRIP_REQUEST_SET(CID, COMMENT_1, TOP_COMMENT_FONT_COLOR, TOP_COMMENT_FONT_WEIGHT, COMMENT_2, BOTTOM_COMMENT_FONT_COLOR, "
			+ "BOTTOM_COMMENT_FONT_WEIGHT, WORK_TYPE, WORK_CHANGE, WORK_CHANGE_APPTIME_ATR, CONTRA_CHECK_ATR, LATE_LEAVE_EARLY_SET) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, COMMENT_1, TOP_COMMENT_FONT_COLOR, TOP_COMMENT_FONT_WEIGHT, COMMENT_2, BOTTOM_COMMENT_FONT_COLOR, "
			+ "BOTTOM_COMMENT_FONT_WEIGHT, WORK_TYPE, WORK_CHANGE, WORK_CHANGE_APPTIME_ATR, CONTRA_CHECK_ATR, LATE_LEAVE_EARLY_SET"
			+ " FROM KRQST_TRIP_REQUEST_SET WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_TRIP_REQUEST_SET WHERE CID = ?";
	
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
				// get old data target by cid
				Query selectQueryTarget = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
						this.companyId);
				List<Object> oldDatas = selectQueryTarget.getResultList();
				
				if (!oldDatas.isEmpty()) 
					return;
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
					Object[] dataArr = (Object[]) zeroCompanyDatas[i];
					insertQuery.setParameter(i * 12 + 1, this.companyId);
					insertQuery.setParameter(i * 12 + 2, dataArr[1]);
					insertQuery.setParameter(i * 12 + 3, dataArr[2]);
					insertQuery.setParameter(i * 12 + 4, dataArr[3]);
					insertQuery.setParameter(i * 12 + 5, dataArr[4]);
					insertQuery.setParameter(i * 12 + 6, dataArr[5]);
					insertQuery.setParameter(i * 12 + 7, dataArr[6]);
					insertQuery.setParameter(i * 12 + 8, dataArr[7]);
					insertQuery.setParameter(i * 12 + 9, dataArr[8]);
					insertQuery.setParameter(i * 12 + 10, dataArr[9]);
					insertQuery.setParameter(i * 12 + 11, dataArr[10]);
					insertQuery.setParameter(i * 12 + 12, dataArr[11]);
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
	 * Instantiates a new krqst trip request set data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstTripRequestSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}
}
