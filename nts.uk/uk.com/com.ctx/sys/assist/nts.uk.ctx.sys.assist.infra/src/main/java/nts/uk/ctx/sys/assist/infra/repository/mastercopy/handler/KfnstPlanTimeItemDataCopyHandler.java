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
 * The Class KfnstPlanTimeItemDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KfnstPlanTimeItemDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KFNST_PLAN_TIME_ITEM(CID, SCHEDULE_ID, ATD_ID) VALUES (?,?,?)";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, SCHEDULE_ID, ATD_ID FROM KFNST_PLAN_TIME_ITEM WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KFNST_PLAN_TIME_ITEM WHERE CID = ?";

	private final int PARAMATER_QUANTITY = 3;

	/**
	 * Instantiates a new kfnst plan time item data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KfnstPlanTimeItemDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
				}

				// Run insert query
				if(!StringUtils.isEmpty(insertQueryStr)) insertQuery.executeUpdate();
			case DO_NOTHING:
				// Do nothing
			default:
				break;
			}
		}

	}
}
