package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class KrqstWithDrawalReqSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class KrqstWithDrawalReqSetDataCopyHandler extends DataCopyHandler {
	
//	/** The entity manager. */
//	private EntityManager entityManager;
//
//	/** The copy method. */
//	private CopyMethod copyMethod;
//
//	/** The company Id. */
//	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KRQST_WITHDRAWAL_REQ_SET(CID, PERMISSION_DIVISION, APPLI_DATE_CONTRAC, USE_ATR, CHECK_UPLIMIT_HALFDAY_HD, PICKUP_COMMENT, PICKUP_BOLD, "
			+ "PICKUP_LETTER_COLOR, DEFERRED_COMMENT, DEFERRED_BOLD, DEFERRED_LETTER_COLOR, DEFERRED_WORKTIME_SELECT, SIMUL_APPLI_REQUIRE, LETTER_SUPER_LEAVE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, PERMISSION_DIVISION, APPLI_DATE_CONTRAC, USE_ATR, CHECK_UPLIMIT_HALFDAY_HD, PICKUP_COMMENT, PICKUP_BOLD, "
			+ "PICKUP_LETTER_COLOR, DEFERRED_COMMENT, DEFERRED_BOLD, DEFERRED_LETTER_COLOR, DEFERRED_WORKTIME_SELECT, SIMUL_APPLI_REQUIRE, LETTER_SUPER_LEAVE "
			+ "FROM KRQST_WITHDRAWAL_REQ_SET WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KRQST_WITHDRAWAL_REQ_SET WHERE CID = ?";

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
					insertQuery.setParameter(i * 14 + 1, this.companyId);
					insertQuery.setParameter(i * 14 + 2, dataArr[1]);
					insertQuery.setParameter(i * 14 + 3, dataArr[2]);
					insertQuery.setParameter(i * 14 + 4, dataArr[3]);
					insertQuery.setParameter(i * 14 + 5, dataArr[4]);
					insertQuery.setParameter(i * 14 + 6, dataArr[5]);
					insertQuery.setParameter(i * 14 + 7, dataArr[6]);
					insertQuery.setParameter(i * 14 + 8, dataArr[7]);
					insertQuery.setParameter(i * 14 + 9, dataArr[8]);
					insertQuery.setParameter(i * 14 + 10, dataArr[9]);
					insertQuery.setParameter(i * 14 + 11, dataArr[10]);
					insertQuery.setParameter(i * 14 + 12, dataArr[11]);
					insertQuery.setParameter(i * 14 + 13, dataArr[12]);
					insertQuery.setParameter(i * 14 + 14, dataArr[13]);
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
