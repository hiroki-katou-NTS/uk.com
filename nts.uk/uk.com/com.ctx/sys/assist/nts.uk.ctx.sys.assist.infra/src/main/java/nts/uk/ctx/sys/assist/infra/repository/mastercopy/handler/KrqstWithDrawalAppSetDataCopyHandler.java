package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KrqstWithDrawalAppSetDataCopyHandler.
 */
@NoArgsConstructor
public class KrqstWithDrawalAppSetDataCopyHandler extends DataCopyHandler {
	
	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KRQST_WITHDRAWAL_APP_SET(CID, PRE_REFLECT_SET, BREAK_TIME, WORK_TIME, CHECK_NO_HD_TIME, TYPES_OF_PAID_LEAVE, WORK_CHANGE_SET, "
			+ "TIME_INITIAL_DISP, CHECK_OUTSIDE_LEGAL, PREFIX_LEAVE_PERMISSION, "
			+ "UNIT_TIME_HD, APP_SIMULTANEOUS_APP_SET, BOUNCE_SEGMENT_ATR, DIRECT_DIVISION_ATR, REST_TIME, OVERRIDE_SET, CALCULATION_STAMP_MISS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, PRE_REFLECT_SET, BREAK_TIME, WORK_TIME, CHECK_NO_HD_TIME, TYPES_OF_PAID_LEAVE, WORK_CHANGE_SET, "
			+ "TIME_INITIAL_DISP, CHECK_OUTSIDE_LEGAL, PREFIX_LEAVE_PERMISSION, "
			+ "UNIT_TIME_HD, APP_SIMULTANEOUS_APP_SET, BOUNCE_SEGMENT_ATR, DIRECT_DIVISION_ATR, REST_TIME, OVERRIDE_SET, CALCULATION_STAMP_MISS "
			+ "FROM KRQST_WITHDRAWAL_APP_SET WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_WITHDRAWAL_APP_SET WHERE CID = ?";
	
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
					insertQuery.setParameter(i * 17 + 1, this.companyId);
					insertQuery.setParameter(i * 17 + 2, dataArr[1]);
					insertQuery.setParameter(i * 17 + 3, dataArr[2]);
					insertQuery.setParameter(i * 17 + 4, dataArr[3]);
					insertQuery.setParameter(i * 17 + 5, dataArr[4]);
					insertQuery.setParameter(i * 17 + 6, dataArr[5]);
					insertQuery.setParameter(i * 17 + 7, dataArr[6]);
					insertQuery.setParameter(i * 17 + 8, dataArr[7]);
					insertQuery.setParameter(i * 17 + 9, dataArr[8]);
					insertQuery.setParameter(i * 17 + 10, dataArr[9]);
					insertQuery.setParameter(i * 17 + 11, dataArr[10]);
					insertQuery.setParameter(i * 17 + 12, dataArr[11]);
					insertQuery.setParameter(i * 17 + 13, dataArr[12]);
					insertQuery.setParameter(i * 17 + 14, dataArr[13]);
					insertQuery.setParameter(i * 17 + 15, dataArr[14]);
					insertQuery.setParameter(i * 17 + 16, dataArr[15]);
					insertQuery.setParameter(i * 17 + 17, dataArr[16]);
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
	 * Instantiates a new krqst with drawal app set data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstWithDrawalAppSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

}
