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

// TODO: Auto-generated Javadoc

/**
 * The Class KrcstComFlexMCalSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KrcstComFlexMCalSetDataCopyHandler extends DataCopyHandler {
	/** The current parameter. */
	private final int CURRENT_COLUMN = 5;

	// /**
	// * The entity manager.
	// */
	// private EntityManager entityManager;
	//
	// /**
	// * The copy method.
	// */
	// private CopyMethod copyMethod;
	//
	// /**
	// * The company Id.
	// */
	// private String companyId;

	/**
	 * The insert query.
	 */
	private static final String INSERT_QUERY = "INSERT INTO KRCST_COM_FLEX_M_CAL_SET(CID ,INSUFFIC_SET,INCLUDE_OT,AGGR_METHOD,LEGAL_AGGR_SET) VALUES (?,?,?,?,?);";

	/**
	 * The select by cid query.
	 */
	private static final String SELECT_BY_CID_QUERY = "SELECT CID ,INSUFFIC_SET,INCLUDE_OT,AGGR_METHOD,LEGAL_AGGR_SET FROM KRCST_COM_FLEX_M_CAL_SET WHERE CID = ?";

	/**
	 * The delete by cid query.
	 */
	private static final String DELETE_BY_CID_QUERY = "DELETE FROM KRCST_COM_FLEX_M_CAL_SET WHERE CID = ?";

	/**
	 * Instantiates a new krcst com flex M cal set data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KrcstComFlexMCalSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 3, dataArr[2]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 4, dataArr[3]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 5, dataArr[4]);
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
