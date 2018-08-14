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
 * The Class KscmtScheDispControlDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KscmtScheDispControlDataCopyHandler implements DataCopyHandler {

	private EntityManager entityManager;

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company Id. */
	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KSCMT_SCHE_DISP_CONTROL(CID, PERSON_SYQUALIFY, PUB_HD_SHORTAGE_ATR, PUB_HD_EXCESS_ATR, SYMBOL_ATR, SYMBOL_HALF_DAY_ATR, SYMBOL_HALF_DAY_NAME) VALUES (?,?,?)";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, PERSON_SYQUALIFY, PUB_HD_SHORTAGE_ATR, PUB_HD_EXCESS_ATR, SYMBOL_ATR, SYMBOL_HALF_DAY_ATR, SYMBOL_HALF_DAY_NAME FROM KSCMT_SCHE_DISP_CONTROL WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KSCMT_SCHE_DISP_CONTROL WHERE CID = ?";

	/**
	 * Instantiates a new kscmt sche disp control data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KscmtScheDispControlDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
				insertQuery.setParameter(i * 5 + 1, this.companyId);
				insertQuery.setParameter(i * 5 + 2, dataArr[1]);
				insertQuery.setParameter(i * 5 + 3, dataArr[2]);
				insertQuery.setParameter(i * 5 + 4, dataArr[3]);
				insertQuery.setParameter(i * 5 + 5, dataArr[4]);
				insertQuery.setParameter(i * 5 + 6, dataArr[5]);
				insertQuery.setParameter(i * 5 + 7, dataArr[6]);
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
