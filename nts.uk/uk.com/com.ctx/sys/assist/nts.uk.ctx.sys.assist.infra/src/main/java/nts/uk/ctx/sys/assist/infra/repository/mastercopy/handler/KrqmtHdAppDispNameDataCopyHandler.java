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
 * The Class KrqmtHdAppDispNameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrqmtHdAppDispNameDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KRQMT_HD_APP_DISP_NAME(CID ,HD_APP_TYPE ,DISP_NAME) VALUES (?, ?, ?);";
	
	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, HD_APP_TYPE, DISP_NAME FROM KRQMT_HD_APP_DISP_NAME WHERE CID = ?";
	
	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KRQMT_HD_APP_DISP_NAME WHERE CID = ?";

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
					insertQuery.setParameter(i * 3 + 1, this.companyId);
					insertQuery.setParameter(i * 3 + 2, dataArr[1]);
					insertQuery.setParameter(i * 3 + 3, dataArr[2]);
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
	 * Instantiates a new krqmt hd app disp name data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqmtHdAppDispNameDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

}
