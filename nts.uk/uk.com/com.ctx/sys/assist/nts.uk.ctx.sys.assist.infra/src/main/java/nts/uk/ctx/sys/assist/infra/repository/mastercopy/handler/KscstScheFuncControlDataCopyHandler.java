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
 * The Class KscstScheFuncControlDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KscstScheFuncControlDataCopyHandler implements DataCopyHandler {
	private EntityManager entityManager;

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company Id. */
	private String companyId;

	/** The insert query. */
	private String INSERT_QUERY = "INSERT INTO KSCST_SCHE_FUNC_CONTROL(CID, ALARM_CHECK_USE_CLS, CONFIRMED_CLS, PUBLIC_CLS, OUTPUT_CLS, WORK_DORMITION_CLS, TEAM_CLS, RANK_CLS, "
			+ "START_DATE_IN_WEEK, SHORT_NAME_DISP, TIME_DISP, SYMBOL_DISP, DAYS_CYCLE_28, LAST_DAY_DISP, INDIVIDUAL_DISP, DISP_BY_DATE, INDICATION_BY_SHIFT, REGULAR_WORK, "
			+ "FLUID_WORK, WORKING_FOR_FLEX, OVERTIME_WORK, NORMAL_CREATION, SIMULATION_CLS, CAPTURE_USAGE_CLS, COMPLETED_FUNC_CLS, HOW_TO_COMPLETE, ALARM_CHECK_CLS, "
			+ "EXECUTION_METHOD, HANDLE_REPAIR_ATR, CONFIRM, SEARCH_METHOD, SEARCH_METHOD_DISP_CLS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/** The select by cid query. */
	private String SELECT_BY_CID_QUERY = "SELECT CID, ALARM_CHECK_USE_CLS, CONFIRMED_CLS, PUBLIC_CLS, OUTPUT_CLS, WORK_DORMITION_CLS, TEAM_CLS, RANK_CLS, "
			+ "START_DATE_IN_WEEK, SHORT_NAME_DISP, TIME_DISP, SYMBOL_DISP, DAYS_CYCLE_28, LAST_DAY_DISP, INDIVIDUAL_DISP, DISP_BY_DATE, INDICATION_BY_SHIFT, REGULAR_WORK, "
			+ "FLUID_WORK, WORKING_FOR_FLEX, OVERTIME_WORK, NORMAL_CREATION, SIMULATION_CLS, CAPTURE_USAGE_CLS, COMPLETED_FUNC_CLS, HOW_TO_COMPLETE, ALARM_CHECK_CLS, "
			+ "EXECUTION_METHOD, HANDLE_REPAIR_ATR, CONFIRM, SEARCH_METHOD, SEARCH_METHOD_DISP_CLS FROM KSCST_SCHE_FUNC_CONTROL WHERE CID = ?";

	/** The delete by cid query. */
	private String DELETE_BY_CID_QUERY = "DELETE FROM KSCST_SCHE_FUNC_CONTROL WHERE CID = ?";

	/**
	 * Instantiates a new kscst sche func control data copy handler.
	 *
	 * @param entityManager the entity manager
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	public KscstScheFuncControlDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}

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
				insertQuery.setParameter(i * 5 + 16, dataArr[15]);
				insertQuery.setParameter(i * 5 + 17, dataArr[16]);
				insertQuery.setParameter(i * 5 + 18, dataArr[17]);
				insertQuery.setParameter(i * 5 + 19, dataArr[18]);
				insertQuery.setParameter(i * 5 + 20, dataArr[19]);
				insertQuery.setParameter(i * 5 + 21, dataArr[20]);
				insertQuery.setParameter(i * 5 + 22, dataArr[21]);
				insertQuery.setParameter(i * 5 + 23, dataArr[22]);
				insertQuery.setParameter(i * 5 + 24, dataArr[23]);
				insertQuery.setParameter(i * 5 + 25, dataArr[24]);
				insertQuery.setParameter(i * 5 + 26, dataArr[25]);
				insertQuery.setParameter(i * 5 + 27, dataArr[26]);
				insertQuery.setParameter(i * 5 + 28, dataArr[27]);
				insertQuery.setParameter(i * 5 + 29, dataArr[28]);
				insertQuery.setParameter(i * 5 + 30, dataArr[29]);
				insertQuery.setParameter(i * 5 + 31, dataArr[30]);
				insertQuery.setParameter(i * 5 + 32, dataArr[31]);
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
