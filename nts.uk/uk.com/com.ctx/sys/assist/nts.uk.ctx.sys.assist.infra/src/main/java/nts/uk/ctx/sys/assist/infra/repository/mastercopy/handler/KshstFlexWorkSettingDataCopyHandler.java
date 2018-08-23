package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

/**
 * Instantiates a new kshst flex work setting data copy handler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KshstFlexWorkSettingDataCopyHandler extends DataCopyHandler {

	/** The current parameter. */
	private final int CURRENT_COLUMN = 2;

	/**
	 * The insert query.
	 */
	private final String INSERT_QUERY = "INSERT INTO KSHST_FLEX_WORK_SETTING(CID ,MANAGE_FLEX_WORK) VALUES (?,?);";

	/**
	 * The select by cid query.
	 */
	private final String SELECT_BY_CID_QUERY = "SELECT CID ,MANAGE_FLEX_WORK FROM KSHST_FLEX_WORK_SETTING WHERE CID = ?";

	/**
	 * The delete by cid query.
	 */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_FLEX_WORK_SETTING WHERE CID = ?";

	/**
	 * Instantiates a new kshst flex work setting data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KshstFlexWorkSettingDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public void doCopy() {

		// Get all company zero data
		Query selectQuery = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
				AppContexts.user().zeroCompanyIdInContract());
		List<Object> zeroCompanyDatas = selectQuery.getResultList();

		if (zeroCompanyDatas.isEmpty())
			return;
		switch (copyMethod) {
		case REPLACE_ALL:
			Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY).setParameter(1,
					this.companyId);
			deleteQuery.executeUpdate();
		case ADD_NEW:
			// Get all company zero data
			Query query = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1, this.companyId);
			List<Object> curentCompanyDatas = query.getResultList();

			if (!curentCompanyDatas.isEmpty())
				return;
			// Create quuery string base on zero company data
			String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.size());
			if (!StringUtils.isEmpty(insertQueryStr)) {
				Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);

				// Loop to set parameter to query
				for (int i = 0, j = zeroCompanyDatas.size(); i < j; i++) {
					Object[] dataArr = (Object[]) zeroCompanyDatas.get(i);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 1, this.companyId);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 2, dataArr[1]);
				}

				// Run insert query
				insertQuery.executeUpdate();
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}

}
