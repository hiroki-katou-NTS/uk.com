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

@Getter
@Setter
@NoArgsConstructor
public class KrqmtAppDispNameDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private static final String INSERT_QUERY = "INSERT INTO KRQMT_APP_DISP_NAME(CID, APP_TYPE, DISP_NAME) VALUES (?, ?, ?);";
	
	/** The select by cid query. */
	private static final String SELECT_BY_CID_QUERY = "SELECT CID, APP_TYPE, DISP_NAME FROM KRQMT_APP_DISP_NAME WHERE CID = ?";
	
	/** The delete by cid query. */
	private static final String DELETE_BY_CID_QUERY = "DELETE FROM KRQMT_APP_DISP_NAME WHERE CID = ?";
	
	@Override
	public void doCopy() {
		
		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY)
				.setParameter(1, AppContexts.user().zeroCompanyIdInContract());
		List<Object> sourceDatas = selectQuery.getResultList();
		
		if(sourceDatas.isEmpty())
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
					// ignore data existed
					for (int i = 0; i < sourceDatas.size(); i++) {
						Object[] dataAttr = (Object[]) sourceDatas.get(i);
						for (int j = 0; j < oldDatas.size(); j++) {
							Object[] targetAttr = (Object[]) oldDatas.get(j);
							// compare keys and remove
							if (dataAttr[1].equals(targetAttr[1])) {
								sourceDatas.remove(i);
								i -= 1;
								break;
							}
						}
					}
	
					if (sourceDatas.isEmpty())
						return;
				}
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, sourceDatas.size());
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				for (int i = 0, j = sourceDatas.size(); i < j; i++) {
					Object[] dataArr = (Object[]) sourceDatas.get(i);
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
	 * Instantiates a new krqmt app disp name data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KrqmtAppDispNameDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		super();
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

}
