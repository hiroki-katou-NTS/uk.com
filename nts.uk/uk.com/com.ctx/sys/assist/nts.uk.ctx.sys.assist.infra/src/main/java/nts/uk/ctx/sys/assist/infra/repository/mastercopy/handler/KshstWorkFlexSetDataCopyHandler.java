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

// TODO: Auto-generated Javadoc

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class KshstWorkFlexSetDataCopyHandler extends DataCopyHandler {

	/** The current parameter. */
	private final int CURRENT_COLUMN = 17;

//	/**
//	 * The entity manager.
//	 */
//	private EntityManager entityManager;
//
//	/**
//	 * The copy method.
//	 */
//	private CopyMethod copyMethod;
//
//	/**
//	 * The company Id.
//	 */
//	private String companyId;

	/**
	 * The insert query.
	 */
	private String INSERT_QUERY = "INSERT INTO KSHST_WORK_FLEX_SET(CID ,PRE_CALC_ACTUAL_OPERATION,PRE_EXEMPT_TAX_TIME,PRE_INC_CHILD_NURSE_CARE,PRE_PREDETERMIN_OT,PRE_ADDITION_TIME,PRE_NOT_DEDUCT_LATELEAVE,WKT_EXEMPT_TAX_TIME,WKT_MINUS_ABSENCE_TIME,WKT_CALC_ACTUAL_OPERATION,WKT_INC_CHILD_NURSE_CARE,WKT_NOT_DEDUCT_LATELEAVE,WKT_PREDETERMIN_DEF,WKT_ADDITION_TIME,ENABLE_SET_PER_WORK_HOUR1,ADDITION_WITHIN_MONTHLY_STATUTORY,ENABLE_SET_PER_WORK_HOUR2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	/**
	 * The select by cid query.
	 */
	private String SELECT_BY_CID_QUERY = "SELECT CID ,PRE_CALC_ACTUAL_OPERATION,PRE_EXEMPT_TAX_TIME,PRE_INC_CHILD_NURSE_CARE,PRE_PREDETERMIN_OT,PRE_ADDITION_TIME,PRE_NOT_DEDUCT_LATELEAVE,WKT_EXEMPT_TAX_TIME,WKT_MINUS_ABSENCE_TIME,WKT_CALC_ACTUAL_OPERATION,WKT_INC_CHILD_NURSE_CARE,WKT_NOT_DEDUCT_LATELEAVE,WKT_PREDETERMIN_DEF,WKT_ADDITION_TIME,ENABLE_SET_PER_WORK_HOUR1,ADDITION_WITHIN_MONTHLY_STATUTORY,ENABLE_SET_PER_WORK_HOUR2 FROM KSHST_WORK_FLEX_SET WHERE CID = ?";

	/**
	 * The delete by cid query.
	 */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_WORK_FLEX_SET WHERE CID = ?";

	/**
	 * Instantiates a new kshst work flex set data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KshstWorkFlexSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
		Object[] zeroCompanyDatas = selectQuery.getResultList().toArray();

		switch (copyMethod) {
		case REPLACE_ALL:
			Query deleteQuery = this.entityManager.createNativeQuery(DELETE_BY_CID_QUERY).setParameter(1,
					this.companyId);
			deleteQuery.executeUpdate();
		case ADD_NEW:
			// Create quuery string base on zero company data
			if (zeroCompanyDatas.length > 0) {
				String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.length);
				if (!StringUtils.isEmpty(insertQueryStr)) {
					Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);

					// Loop to set parameter to query
					for (int i = 0, j = zeroCompanyDatas.length; i < j; i++) {
						Object[] dataArr = (Object[]) zeroCompanyDatas[i];
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 1, this.companyId);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 2, dataArr[1]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 3, dataArr[2]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 4, dataArr[3]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 5, dataArr[4]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 6, dataArr[5]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 7, dataArr[6]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 8, dataArr[7]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 9, dataArr[8]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 10, dataArr[9]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 11, dataArr[10]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 12, dataArr[11]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 13, dataArr[12]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 14, dataArr[13]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 15, dataArr[14]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 16, dataArr[15]);
						insertQuery.setParameter(i * this.CURRENT_COLUMN + 17, dataArr[16]);

					}

					// Run insert query
					insertQuery.executeUpdate();
				}
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
	}
}
