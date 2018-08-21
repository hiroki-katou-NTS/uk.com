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
 * The Class KrcstMonItemRoundDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrcstMonItemRoundDataCopyHandler extends DataCopyHandler {
	
//	/** The entity manager. */
//	private EntityManager entityManager;
//	
//	/** The copy method. */
//	private CopyMethod copyMethod;
//	
//	/** The company Id. */
//	private String companyId;
	
	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KRCST_MON_ITEM_ROUND(CID, ATTENDANCE_ITEM_ID, ROUND_UNIT, ROUND_PROC) VALUES (?, ?, ?, ?);";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, ATTENDANCE_ITEM_ID, ROUND_UNIT, ROUND_PROC FROM KRCST_MON_ITEM_ROUND WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KRCST_MON_ITEM_ROUND WHERE CID = ?";
	
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
					insertQuery.setParameter(i * 4 + 1, this.companyId);
					insertQuery.setParameter(i * 4 + 2, dataArr[1]);
					insertQuery.setParameter(i * 4 + 3, dataArr[2]);
					insertQuery.setParameter(i * 4 + 4, dataArr[3]);
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
	 * Instantiates a new krcst mon item round data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrcstMonItemRoundDataCopyHandler(EntityManager entityManager,CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}
}
