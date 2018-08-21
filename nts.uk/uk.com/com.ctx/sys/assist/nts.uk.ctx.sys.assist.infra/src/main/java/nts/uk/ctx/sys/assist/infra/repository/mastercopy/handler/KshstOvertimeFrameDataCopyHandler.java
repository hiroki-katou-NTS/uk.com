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
 * The Class KshstOvertimeFrameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class KshstOvertimeFrameDataCopyHandler extends DataCopyHandler {
	
//	/** The entity manager. */
//	private EntityManager entityManager;
//
//	/** The copy method. */
//	private CopyMethod copyMethod;
//
//	/** The company Id. */
//	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KSHST_OVERTIME_FRAME(CID ,OT_FR_NO ,USE_ATR ,OT_FR_NAME ,TRANS_FR_NAME) VALUES (?, ?, ?, ?, ?);";
	
	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, OT_FR_NO, USE_ATR, OT_FR_NAME, TRANS_FR_NAME FROM KSHST_OVERTIME_FRAME WHERE CID = ?";
	
	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_OVERTIME_FRAME WHERE CID = ?";
	
	/**
	 * Instantiates a new kshst overtime frame data copy handler.
	 *
	 * @param copyMethod the copy method
	 * @param companyCd the company cd
	 */
	public KshstOvertimeFrameDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {
		
		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY)
				.setParameter(1, AppContexts.user().zeroCompanyIdInContract());
		Object[] zeroCompanyDatas = selectQuery.getResultList().toArray();
		
		switch (copyMethod) {
			case REPLACE_ALL:
				Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY)
					.setParameter(1, this.companyId);
				deleteQuery.executeUpdate();
			case ADD_NEW:
				// Create quuery string base on zero company data
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				
				// Loop to set parameter to query
				for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
					Object[] dataArr = (Object[]) zeroCompanyDatas[i];
					insertQuery.setParameter(i * 5 + 1, this.companyId);
					insertQuery.setParameter(i * 5 + 2, dataArr[1]);
					insertQuery.setParameter(i * 5 + 3, dataArr[2]);
					insertQuery.setParameter(i * 5 + 4, dataArr[3]);
					insertQuery.setParameter(i * 5 + 5, dataArr[4]);
				}
				
				// Run insert query
				insertQuery.executeUpdate();
			case DO_NOTHING:
				// Do nothing
			default: 
				break;
		}
	}

}
