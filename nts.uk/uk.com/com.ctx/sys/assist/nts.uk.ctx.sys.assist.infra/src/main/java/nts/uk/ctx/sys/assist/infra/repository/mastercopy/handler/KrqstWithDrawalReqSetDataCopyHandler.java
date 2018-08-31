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
 * The Class KrqstWithDrawalReqSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrqstWithDrawalReqSetDataCopyHandler extends DataCopyHandler {
	
	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KRQST_WITHDRAWAL_REQ_SET(CID, PERMISSION_DIVISION, APPLI_DATE_CONTRAC, USE_ATR, CHECK_UPLIMIT_HALFDAY_HD, PICKUP_COMMENT, PICKUP_BOLD, "
			+ "PICKUP_LETTER_COLOR, DEFERRED_COMMENT, DEFERRED_BOLD, DEFERRED_LETTER_COLOR, DEFERRED_WORKTIME_SELECT, SIMUL_APPLI_REQUIRE, LETTER_SUPER_LEAVE,SIMULTAN_APP_REQUIRED,LETTER_SUSPENSION_LEAVE) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, PERMISSION_DIVISION, APPLI_DATE_CONTRAC, USE_ATR, CHECK_UPLIMIT_HALFDAY_HD, PICKUP_COMMENT, PICKUP_BOLD, "
			+ "PICKUP_LETTER_COLOR, DEFERRED_COMMENT, DEFERRED_BOLD, DEFERRED_LETTER_COLOR, DEFERRED_WORKTIME_SELECT, SIMUL_APPLI_REQUIRE, LETTER_SUPER_LEAVE,SIMULTAN_APP_REQUIRED,LETTER_SUSPENSION_LEAVE "
			+ "FROM KRQST_WITHDRAWAL_REQ_SET WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_WITHDRAWAL_REQ_SET WHERE CID = ?";

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
				if (copyMethod == CopyMethod.ADD_NEW) {
					// get old data target by cid
					Query selectQueryTarget = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
							this.companyId);
					List<Object> oldDatas = selectQueryTarget.getResultList();
					
					if (!oldDatas.isEmpty()) 
						return;
				}
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
					Object[] dataArr = (Object[]) zeroCompanyDatas[i];
					insertQuery.setParameter(i * 16 + 1, this.companyId);
					insertQuery.setParameter(i * 16 + 2, dataArr[1]);
					insertQuery.setParameter(i * 16 + 3, dataArr[2]);
					insertQuery.setParameter(i * 16 + 4, dataArr[3]);
					insertQuery.setParameter(i * 16 + 5, dataArr[4]);
					insertQuery.setParameter(i * 16 + 6, dataArr[5]);
					insertQuery.setParameter(i * 16 + 7, dataArr[6]);
					insertQuery.setParameter(i * 16 + 8, dataArr[7]);
					insertQuery.setParameter(i * 16 + 9, dataArr[8]);
					insertQuery.setParameter(i * 16 + 10, dataArr[9]);
					insertQuery.setParameter(i * 16 + 11, dataArr[10]);
					insertQuery.setParameter(i * 16 + 12, dataArr[11]);
					insertQuery.setParameter(i * 16 + 13, dataArr[12]);
					insertQuery.setParameter(i * 16 + 14, dataArr[13]);
					insertQuery.setParameter(i * 16 + 15, dataArr[14]);
					insertQuery.setParameter(i * 16 + 16, dataArr[15]);
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
	 * Instantiates a new krqst with drawal req set data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqstWithDrawalReqSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

}
