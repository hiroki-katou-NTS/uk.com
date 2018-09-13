package nts.uk.ctx.sys.assist.infra.repository.mastercopy.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * The Class KscstScheFuncControlDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class KscstScheFuncControlDataCopyHandler extends DataCopyHandler {

	/** The insert query. */
	private final String INSERT_QUERY = "INSERT INTO KSCST_SCHE_FUNC_CONTROL(CID, ALARM_CHECK_USE_CLS, CONFIRMED_CLS, PUBLIC_CLS, OUTPUT_CLS, WORK_DORMITION_CLS, TEAM_CLS, RANK_CLS, "
			+ "START_DATE_IN_WEEK, SHORT_NAME_DISP, TIME_DISP, SYMBOL_DISP, DAYS_CYCLE_28, LAST_DAY_DISP, INDIVIDUAL_DISP, DISP_BY_DATE, INDICATION_BY_SHIFT, REGULAR_WORK, "
			+ "FLUID_WORK, WORKING_FOR_FLEX, OVERTIME_WORK, NORMAL_CREATION, SIMULATION_CLS, CAPTURE_USAGE_CLS, COMPLETED_FUNC_CLS, HOW_TO_COMPLETE, ALARM_CHECK_CLS, "
			+ "EXECUTION_METHOD, HANDLE_REPAIR_ATR, CONFIRM, SEARCH_METHOD, SEARCH_METHOD_DISP_CLS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/** The select by cid query. */
	private final String SELECT_BY_CID_QUERY = "SELECT CID, ALARM_CHECK_USE_CLS, CONFIRMED_CLS, PUBLIC_CLS, OUTPUT_CLS, WORK_DORMITION_CLS, TEAM_CLS, RANK_CLS, "
			+ "START_DATE_IN_WEEK, SHORT_NAME_DISP, TIME_DISP, SYMBOL_DISP, DAYS_CYCLE_28, LAST_DAY_DISP, INDIVIDUAL_DISP, DISP_BY_DATE, INDICATION_BY_SHIFT, REGULAR_WORK, "
			+ "FLUID_WORK, WORKING_FOR_FLEX, OVERTIME_WORK, NORMAL_CREATION, SIMULATION_CLS, CAPTURE_USAGE_CLS, COMPLETED_FUNC_CLS, HOW_TO_COMPLETE, ALARM_CHECK_CLS, "
			+ "EXECUTION_METHOD, HANDLE_REPAIR_ATR, CONFIRM, SEARCH_METHOD, SEARCH_METHOD_DISP_CLS FROM KSCST_SCHE_FUNC_CONTROL WHERE CID = ?";

	/** The delete by cid query. */
	private final String DELETE_BY_CID_QUERY = "DELETE FROM KSCST_SCHE_FUNC_CONTROL WHERE CID = ?";

	/** The paramater quantity. */
	private final int PARAMATER_QUANTITY = 32;

	/**
	 * Instantiates a new kscst sche func control data copy handler.
	 *
	 * @param entityManager
	 *            the entity manager
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public KscstScheFuncControlDataCopyHandler(EntityManager entityManager, CopyMethod copyMethod, String companyId) {
		this.entityManager = entityManager;
		this.copyMethod = copyMethod;
		this.companyId = companyId;
	}
	@SuppressWarnings("unchecked")
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
			//check old data existed
			Query selectQueryTarget = this.entityManager.createNativeQuery(SELECT_BY_CID_QUERY).setParameter(1,
								this.companyId);
						Object[] targetDatas = selectQueryTarget.getResultList().toArray();
						if(targetDatas.length!=0) return ;
			//copy data
//			String insertQueryStr = StringUtils.repeat(INSERT_QUERY, zeroCompanyDatas.size());
//			Query insertQuery = this.entityManager.createNativeQuery(insertQueryStr);
			for (int i = 0, j = zeroCompanyDatas.size(); i < j; i++) {
				Query insertQuery = this.entityManager.createNativeQuery(INSERT_QUERY);
				Object[] dataArr = (Object[]) zeroCompanyDatas.get(i);
				insertQuery.setParameter(1, this.companyId);
				insertQuery.setParameter(2, dataArr[1]);
				insertQuery.setParameter(3, dataArr[2]);
				insertQuery.setParameter(4, dataArr[3]);
				insertQuery.setParameter(5, dataArr[4]);
				insertQuery.setParameter(6, dataArr[5]);
				insertQuery.setParameter(7, dataArr[6]);
				insertQuery.setParameter(8, dataArr[7]);
				insertQuery.setParameter(9, dataArr[8]);
				insertQuery.setParameter(10, dataArr[9]);
				insertQuery.setParameter(11, dataArr[10]);
				insertQuery.setParameter(12, dataArr[11]);
				insertQuery.setParameter(13, dataArr[12]);
				insertQuery.setParameter(14, dataArr[13]);
				insertQuery.setParameter(15, dataArr[14]);
				insertQuery.setParameter(16, dataArr[15]);
				insertQuery.setParameter(17, dataArr[16]);
				insertQuery.setParameter(18, dataArr[17]);
				insertQuery.setParameter(19, dataArr[18]);
				insertQuery.setParameter(20, dataArr[19]);
				insertQuery.setParameter(21, dataArr[20]);
				insertQuery.setParameter(22, dataArr[21]);
				insertQuery.setParameter(23, dataArr[22]);
				insertQuery.setParameter(24, dataArr[23]);
				insertQuery.setParameter(25, dataArr[24]);
				insertQuery.setParameter(26, dataArr[25]);
				insertQuery.setParameter(27, dataArr[26]);
				insertQuery.setParameter(28, dataArr[27]);
				insertQuery.setParameter(29, dataArr[28]);
				insertQuery.setParameter(30, dataArr[29]);
				insertQuery.setParameter(31, dataArr[30]);
				insertQuery.setParameter(32, dataArr[31]);
				// Run insert query
				if (!StringUtils.isEmpty(insertQuery.toString()))insertQuery.executeUpdate();
			}
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}

	}
}
