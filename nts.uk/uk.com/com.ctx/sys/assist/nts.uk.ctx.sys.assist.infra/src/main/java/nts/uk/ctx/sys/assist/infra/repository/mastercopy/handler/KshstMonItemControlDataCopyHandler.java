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
 * The Class KshstMonItemControlDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KshstMonItemControlDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private static final String INSERT_QUERY = "INSERT INTO KSHST_MON_ITEM_CONTROL(CID, ITEM_MONTHLY_ID, HEADER_BACKGROUND_COLOR, TIME_INPUT_UNIT) VALUES (?,?,?,?)";

	/** The select by cid query. */
	private static final String SELECT_BY_CID_QUERY = "SELECT CID, ITEM_MONTHLY_ID, HEADER_BACKGROUND_COLOR, TIME_INPUT_UNIT FROM KSHST_MON_ITEM_CONTROL WHERE CID = ?";

	/** The delete by cid query. */
	private static final String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_MON_ITEM_CONTROL WHERE CID = ?";

	/** The paramater quantity. */
	private static final int PARAMATER_QUANTITY = 4;
	
	/**
	 * Instantiates a new kshst mon item control data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KshstMonItemControlDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	@Override
	public void doCopy() {
		/// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
				AppContexts.user().zeroCompanyIdInContract());
		Object[] zeroCompanyDatas = selectQuery.getResultList().toArray();
		if (zeroCompanyDatas.length == 0) {
			return;
		} else {
			switch (copyMethod) {
			case REPLACE_ALL:
				Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY).setParameter(1,
						this.companyId);
				deleteQuery.executeUpdate();
			case ADD_NEW:
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
				for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
					Object[] dataArr = (Object[]) zeroCompanyDatas[i];
					insertQuery.setParameter(i * PARAMATER_QUANTITY + 1, this.companyId);
					insertQuery.setParameter(i * PARAMATER_QUANTITY + 2, dataArr[1]);
					insertQuery.setParameter(i * PARAMATER_QUANTITY + 3, dataArr[2]);
					insertQuery.setParameter(i * PARAMATER_QUANTITY + 4, dataArr[3]);
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
}
