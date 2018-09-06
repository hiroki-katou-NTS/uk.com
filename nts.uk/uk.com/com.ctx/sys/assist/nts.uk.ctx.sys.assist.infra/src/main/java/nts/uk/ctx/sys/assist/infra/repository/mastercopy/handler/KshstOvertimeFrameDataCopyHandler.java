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
 * The Class KshstOvertimeFrameDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KshstOvertimeFrameDataCopyHandler extends DataCopyHandler {
	
	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KSHST_OVERTIME_FRAME(CID ,OT_FR_NO ,USE_ATR ,OT_FR_NAME ,TRANS_FR_NAME) VALUES (?, ?, ?, ?, ?);";
	
	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, OT_FR_NO, USE_ATR, OT_FR_NAME, TRANS_FR_NAME FROM KSHST_OVERTIME_FRAME WHERE CID = ?";
	
	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_OVERTIME_FRAME WHERE CID = ?";
	
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
				// Create quuery string base on zero company data
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, sourceDatas.size());
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				
				// Loop to set parameter to query
				for (int i = 0, j = sourceDatas.size(); i < j; i++) {
					Object[] dataArr = (Object[]) sourceDatas.get(i);
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
