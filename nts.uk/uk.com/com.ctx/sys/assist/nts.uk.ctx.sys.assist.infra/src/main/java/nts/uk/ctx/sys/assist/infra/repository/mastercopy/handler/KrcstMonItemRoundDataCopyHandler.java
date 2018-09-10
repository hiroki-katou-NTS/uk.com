package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import java.util.List;

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

	/** The insert query. */
	private static final String INSERT_QUERY = "INSERT INTO KRCST_MON_ITEM_ROUND(CID, ATTENDANCE_ITEM_ID, ROUND_UNIT, ROUND_PROC) VALUES (?, ?, ?, ?);";

	/** The select by cid query. */
	private static final String SELECT_BY_CID_QUERY = "SELECT CID, ATTENDANCE_ITEM_ID, ROUND_UNIT, ROUND_PROC FROM KRCST_MON_ITEM_ROUND WHERE CID = ?";

	/** The delete by cid query. */
	private static final String DELETE_BY_CID_QUERY = "DELETE FROM KRCST_MON_ITEM_ROUND WHERE CID = ?";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.mastercopy.DataCopyHandler#doCopy()
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
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, sourceDatas.size());
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				for (int i = 0, j = sourceDatas.size(); i < j; i++) {
					Object[] dataArr = (Object[]) sourceDatas.get(i);
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
