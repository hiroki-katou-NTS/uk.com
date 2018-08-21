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
 * The Class KscmtScheItemOrderDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class KscmtScheItemOrderDataCopyHandler extends DataCopyHandler {

//	private EntityManager entityManager;
//
//	/** The copy method. */
//	private CopyMethod copyMethod;
//
//	/** The company Id. */
//	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KSCMT_SCHE_ITEM_ORDER(CID, SCHEDULE_ITEM_ID, DISPORDER) VALUES (?,?,?)";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, SCHEDULE_ITEM_ID, DISPORDER FROM KSCMT_SCHE_ITEM_ORDER WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KSCMT_SCHE_ITEM_ORDER WHERE CID = ?";

	/** The paramater quantity. */
	private final int PARAMATER_QUANTITY = 3;
	
	/**
	 * Instantiates a new kscmt sche item order data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KscmtScheItemOrderDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
				insertQuery.executeUpdate();
			case DO_NOTHING:
				// Do nothing
			default:
				break;
			}
		}

	}
}
