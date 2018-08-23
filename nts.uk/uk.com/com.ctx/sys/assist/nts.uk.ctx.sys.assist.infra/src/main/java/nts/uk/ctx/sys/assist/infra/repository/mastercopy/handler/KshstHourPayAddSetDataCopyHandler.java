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
 * The Class KshstHourPayAddSetDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KshstHourPayAddSetDataCopyHandler extends DataCopyHandler {

	/** The current parameter. */
	private final int CURRENT_COLUMN = 14;

	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KSHST_HOUR_PAY_ADD_SET(CID ,CALC_PERMIUM_VACATION ,ADDITION1 ,DEFORMAT_EXEC_VALUE ,INC_CHILD_NURSE_CARE ,DEDUCT,CALC_INC_INTERVAL_EXEMP_TIME1,CALC_WORK_HOUR_VACATION,ADDITION2,CALC_INC_CARE_TIME,NOT_DEDUCT_LATE_LEAVE_EARLY,CALC_INC_INTERVAL_EXEMP_TIME2,ENABLE_SELECT_PER_WORK_HOUR1,ENABLE_SELECT_PER_WORK_HOUR2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID ,CALC_PERMIUM_VACATION ,ADDITION1 ,DEFORMAT_EXEC_VALUE ,INC_CHILD_NURSE_CARE ,DEDUCT,CALC_INC_INTERVAL_EXEMP_TIME1,CALC_WORK_HOUR_VACATION,ADDITION2,CALC_INC_CARE_TIME,NOT_DEDUCT_LATE_LEAVE_EARLY,CALC_INC_INTERVAL_EXEMP_TIME2,ENABLE_SELECT_PER_WORK_HOUR1,ENABLE_SELECT_PER_WORK_HOUR2 FROM KSHST_HOUR_PAY_ADD_SET WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KSHST_HOUR_PAY_ADD_SET WHERE CID = ?";

	/**
	 * Instantiates a new kshst hour pay add set data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KshstHourPayAddSetDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
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
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 6, dataArr[5]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 7, dataArr[6]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 8, dataArr[7]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 9, dataArr[8]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 10, dataArr[9]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 11, dataArr[10]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 12, dataArr[11]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 13, dataArr[12]);
					insertQuery.setParameter(i * this.CURRENT_COLUMN + 14, dataArr[13]);
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
