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
 * The Class KshstWorkDepLaborSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KshstWorkDepLaborSetDataCopyHandler implements DataCopyHandler {

	private EntityManager entityManager;

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company Id. */
	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KSHST_WORK_DEF_LABOR_SET(CID, PRE_CALC_ACTUAL_OPERATION, PRE_EXEMPT_TAX_TIME, PRE_INC_CHILD_NURSE_CARE, PRE_ADDITION_TIME, "
			+ "PRE_NOT_DEDUCT_LATELEAVE, PRE_DEFORMAT_EXC_VALUE, WKT_EXEMPT_TAX_TIME, WKT_MINUS_ABSENCE_TIME, WKT_CALC_ACTUAL_OPERATION, WKT_INC_CHILD_NURSE_CARE, "
			+ "WKT_NOT_DEDUCT_LATELEAVE, WKT_ADDITION_TIME, ENABLE_SET_PER_WORK_HOUR1, ENABLE_SET_PER_WORK_HOUR2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, PRE_CALC_ACTUAL_OPERATION, PRE_EXEMPT_TAX_TIME, PRE_INC_CHILD_NURSE_CARE, PRE_ADDITION_TIME, "
			+ "PRE_NOT_DEDUCT_LATELEAVE, PRE_DEFORMAT_EXC_VALUE, WKT_EXEMPT_TAX_TIME, WKT_MINUS_ABSENCE_TIME, WKT_CALC_ACTUAL_OPERATION, WKT_INC_CHILD_NURSE_CARE, "
			+ "WKT_NOT_DEDUCT_LATELEAVE, WKT_ADDITION_TIME, ENABLE_SET_PER_WORK_HOUR1, ENABLE_SET_PER_WORK_HOUR2 FROM KSHST_WORK_DEF_LABOR_SET WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_WORK_DEF_LABOR_SET WHERE CID = ?";

	/**
	 * Instantiates a new kshst work dep labor set data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KshstWorkDepLaborSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
				insertQuery.setParameter(i * 5 + 8, dataArr[7]);
				insertQuery.setParameter(i * 5 + 9, dataArr[8]);
				insertQuery.setParameter(i * 5 + 10, dataArr[9]);
				insertQuery.setParameter(i * 5 + 11, dataArr[10]);
				insertQuery.setParameter(i * 5 + 12, dataArr[11]);
				insertQuery.setParameter(i * 5 + 13, dataArr[12]);
				insertQuery.setParameter(i * 5 + 14, dataArr[13]);
				insertQuery.setParameter(i * 5 + 15, dataArr[14]);
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
