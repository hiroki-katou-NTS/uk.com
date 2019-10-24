package nts.uk.ctx.sys.auth.infra.repository.wkpmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.export.wkpmanager.WorkPlaceSelectionExportData;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceFunction;
import nts.uk.ctx.sys.auth.infra.entity.wkpmanager.SacmtWorkplaceManager;
import nts.uk.ctx.sys.auth.infra.entity.wkpmanager.SacmtWorkplaceManagerPK;

@Stateless
public class JpaWorkplaceManagerRepository extends JpaRepository implements WorkplaceManagerRepository {
	/**
	 * Query strings
	 */
	private static final String SELECT_ALL = "SELECT wm FROM SacmtWorkplaceManager wm";
	private static final String SELECT_All_BY_SID_WKP_ID = SELECT_ALL
			+ " WHERE wm.employeeId = :employeeId AND wm.workplaceId = :workplaceId";
	private static final String SELECT_All_BY_WKP_ID = SELECT_ALL
			+ " WHERE wm.workplaceId = :workplaceId ORDER BY wm.employeeId, wm.startDate";
	private static final String SELECT_ALL_BY_SID_BASE_DATE = "SELECT wm FROM SacmtWorkplaceManager wm"
			+ " WHERE wm.employeeId = :employeeId AND wm.startDate <= :baseDate AND wm.endDate >= :baseDate";
	private static final String FIND_BY_WKP_DATE_MANAGER = "SELECT wm FROM SacmtWorkplaceManager wm"
			+ " WHERE wm.workplaceId = :workplaceId" + " AND wm.startDate <= :baseDate AND wm.endDate >= :baseDate"
			+ " AND wm.kacmtWorkplaceManagerPK.workplaceManagerId IN :wkpManagerLst";

	private static final String WORKPLACE_SELECT_ALL = "SELECT wm.wkpcd , wm.wkpName , edm.employeeCode , ps.businessName , wi.startDate, wi.endDate ,wi.kacmtWorkplaceManagerPK.workplaceManagerId "
			+ "FROM BsymtWorkplaceInfo wm "
			+ "LEFT JOIN SacmtWorkplaceManager wi ON wm.bsymtWorkplaceInfoPK.wkpid = wi.workplaceId "
			+ "LEFT JOIN BsymtEmployeeDataMngInfo edm ON wi.employeeId = edm.bsymtEmployeeDataMngInfoPk.sId "
			+ "LEFT JOIN BpsmtPerson ps ON edm.bsymtEmployeeDataMngInfoPk.pId = ps.bpsmtPersonPk.pId "
			+ "WHERE wm.bsymtWorkplaceInfoPK.cid =:companyId AND wi.kacmtWorkplaceManagerPK.workplaceManagerId IS NOT NULL ORDER BY wm.wkpcd, edm.employeeCode, wi.startDate ASC";

	private static final String WORKPLACE_SELECT_ALL_BY_CID = "SELECT WKPCD , WKP_NAME , SCD , BUSINESS_NAME , START_DATE, END_DATE , [1], [2], [3] "
			+ "FROM " + "( "
			+ "SELECT wm.WKPCD , wm.WKP_NAME , edm.SCD , ps.BUSINESS_NAME , wi.START_DATE, wi.END_DATE , AVAILABILITY, wkf.FUNCTION_NO "
			+ "FROM " + "BSYMT_WORKPLACE_INFO wm " + "LEFT JOIN SACMT_WORKPLACE_MANAGER wi ON wm.WKPID = wi.WKP_ID "
			+ "LEFT JOIN BSYMT_EMP_DTA_MNG_INFO edm ON wi.SID = edm.SID "
			+ "LEFT JOIN BPSMT_PERSON ps ON edm.PID = ps.PID "
			+ "INNER JOIN KASMT_WORKPLACE_AUTHORITY kwa ON wi.WKP_MANAGER_ID = kwa.ROLE_ID AND wm.CID = kwa.CID "
			+ "INNER JOIN KASMT_WORPLACE_FUNCTION wkf on wkf.FUNCTION_NO = kwa.FUNCTION_NO "
			+ "WHERE wm.CID  =:companyId" + ") " + "AS sourceTable PIVOT ( " + "MAX(AVAILABILITY) "
			+ "FOR [FUNCTION_NO] IN ([1],[2],[3]) " + ") AS pvt ORDER BY WKPCD, SCD, START_DATE ASC";

	private static final String WORKPLACE_SELECT_ALL_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString = new StringBuilder();
		builderString
				.append("SELECT wkpcd , wkpName , employeeCode , businessName , startDate, endDate , [1], [2], [3]");
		builderString.append(" FROM");
		builderString.append(
				" (SELECT wm.wkpcd , wm.wkpName , edm.employeeCode , ps.businessName , wi.startDate, wi.endDate , kwa.availability, wkf.functionNo");
		builderString.append(" FROM BsymtWorkplaceInfo wm");
		builderString.append(" LEFT JOIN SacmtWorkplaceManager wi ON wm.bsymtWorkplaceInfoPK.wkpid = wi.workplaceId");
		builderString.append(
				" LEFT JOIN BsymtEmployeeDataMngInfo edm ON wi.employeeId = edm.bsymtEmployeeDataMngInfoPk.sId");
		builderString.append(" LEFT JOIN BpsmtPerson ps ON edm.bsymtEmployeeDataMngInfoPk.pId = ps.bpsmtPersonPk.pId");
		builderString.append(
				" INNER JOIN KacmtWorkPlaceAuthority kwa ON wi.kacmtWorkplaceManagerPK.workplaceManagerId = kwa.kacmtWorkPlaceAuthorityPK.roleId AND wm.bsymtWorkplaceInfoPK.cid = kwa.kacmtWorkPlaceAuthorityPK.companyId");
		builderString.append(
				" INNER JOIN KacmtWorkPlaceFunction wkf ON kwa.kacmtWorkPlaceAuthorityPK.functionNo = wkf.functionNo");
		builderString.append(" WHERE wm.bsymtWorkplaceInfoPK.cid  =:companyId)");
		builderString.append(" AS sourceTable PIVOT ( ");
		builderString.append(" MAX(kwa.availability) ");
		builderString.append(" FOR [wkf.functionNo] IN ([1],[2],[3])");
		builderString.append(" ) AS pvt ORDER BY wkpcd, employeeCode, startDate ASC");

		WORKPLACE_SELECT_ALL_CID = builderString.toString();

	}

	/**
	 * Get workplace manager list by workplace id
	 * 
	 * 【input� ・会社ID ・職場ID ・基準日 【output� ・cls <職場表示�
	 */
	@Override
	public List<WorkplaceManager> getWkpManagerListByWkpId(String workplaceId) {
		return this.queryProxy().query(SELECT_All_BY_WKP_ID, SacmtWorkplaceManager.class)
				.setParameter("workplaceId", workplaceId).getList(c -> c.toDomain());
	}

	/**
	 * Get workplace manager list by workplace id and employeeid
	 */
	@Override
	public List<WorkplaceManager> getWkpManagerBySIdWkpId(String employeeId, String workplaceId) {
		return this.queryProxy().query(SELECT_All_BY_SID_WKP_ID, SacmtWorkplaceManager.class)
				.setParameter("employeeId", employeeId).setParameter("workplaceId", workplaceId)
				.getList(c -> c.toDomain());
	}

	@Override
	public void add(WorkplaceManager wkpManager) {
		this.commandProxy().insert(SacmtWorkplaceManager.toEntity(wkpManager));

	}

	@Override
	public void update(WorkplaceManager wkpManager) {
		SacmtWorkplaceManager updateData = SacmtWorkplaceManager.toEntity(wkpManager);
		SacmtWorkplaceManager oldData = this.queryProxy()
				.find(updateData.kacmtWorkplaceManagerPK, SacmtWorkplaceManager.class).get();
		oldData.employeeId = updateData.employeeId;
		oldData.workplaceId = updateData.workplaceId;
		oldData.startDate = updateData.startDate;
		oldData.endDate = updateData.endDate;

		this.commandProxy().update(oldData);
	}

	@Override
	public void delete(String wkpManagerId) {
		SacmtWorkplaceManagerPK kacmtWorkplaceManagerPK = new SacmtWorkplaceManagerPK(wkpManagerId);
		this.commandProxy().remove(SacmtWorkplaceManager.class, kacmtWorkplaceManagerPK);
	}

	@Override
	public List<WorkplaceManager> findListWkpManagerByEmpIdAndBaseDate(String employeeId, GeneralDate baseDate) {
		return this.queryProxy().query(SELECT_ALL_BY_SID_BASE_DATE, SacmtWorkplaceManager.class)
				.setParameter("employeeId", employeeId).setParameter("baseDate", baseDate).getList(c -> c.toDomain());
	}

	@Override
	public List<WorkplaceManager> findByWkpDateAndManager(String wkpID, GeneralDate baseDate,
			List<String> wkpManagerIDLst) {
		List<WorkplaceManager> resultList = new ArrayList<>();
		if(wkpManagerIDLst.isEmpty())
			return resultList;
		CollectionUtil.split(wkpManagerIDLst, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND_BY_WKP_DATE_MANAGER, SacmtWorkplaceManager.class)
					.setParameter("workplaceId", wkpID).setParameter("baseDate", baseDate)
					.setParameter("wkpManagerLst", subList).getList(c -> c.toDomain()));
		});
		return resultList;
	}

	/*
	 * @Override public List<WorkPlaceSelectionExportData>
	 * findAllWorkPlaceSelection(String companyId) { return
	 * this.queryProxy().query(WORKPLACE_SELECT_ALL_CID,
	 * Object[].class).setParameter("companyId", companyId).getList(x ->
	 * toReportData(x)); }
	 * 
	 * private WorkPlaceSelectionExportData toReportData(Object[] entity) { //
	 * TODO Auto-generated method stub return new
	 * WorkPlaceSelectionExportData(entity[0] == null ? null :
	 * entity[0].toString(), entity[1] == null ? null : entity[1].toString(),
	 * entity[2] == null ? null : entity[2].toString(), entity[3] == null ? null
	 * : entity[3].toString(), entity[4] == null ? null : (GeneralDate)
	 * entity[4], entity[5] == null ? null : (GeneralDate) entity[5], //
	 * entity[6] == null ? null : (Integer)entity[6], entity[6] == null ? null :
	 * entity[6].toString()); }
	 */

	// Export Data table
	@Override
	@SneakyThrows
	public List<WorkPlaceSelectionExportData> findAllWorkPlaceSelection(String companyId,
			List<WorkPlaceFunction> workPlaceFunction) {	
		String functions = workPlaceFunction.stream().map(x -> x.getFunctionNo().v().toString())
				.collect(Collectors.toList()).stream().collect(Collectors.joining("], [", "[", "]"));
		String SQL = "SELECT WKPCD , WKP_NAME , SCD , BUSINESS_NAME , START_DATE, END_DATE , %s " + "FROM ( "
				+ "SELECT wm.WKPCD , wm.WKP_NAME , edm.SCD , ps.BUSINESS_NAME , wi.START_DATE, wi.END_DATE , AVAILABILITY, wkf.FUNCTION_NO "
				+ "FROM " + "BSYMT_WORKPLACE_INFO wm " + "LEFT JOIN SACMT_WORKPLACE_MANAGER wi ON wm.WKPID = wi.WKP_ID "
				+ "LEFT JOIN BSYMT_EMP_DTA_MNG_INFO edm ON wi.SID = edm.SID "
				+ "LEFT JOIN BPSMT_PERSON ps ON edm.PID = ps.PID "
				+ "INNER JOIN KASMT_WORKPLACE_AUTHORITY kwa ON wi.WKP_MANAGER_ID = kwa.ROLE_ID AND wm.CID = kwa.CID "
				+ "INNER JOIN KASMT_WORPLACE_FUNCTION wkf on wkf.FUNCTION_NO = kwa.FUNCTION_NO " + "WHERE wm.CID = ? "
				+ ") " + "AS sourceTable PIVOT ( " + "MAX(AVAILABILITY) " + "FOR [FUNCTION_NO] IN (%s) "
				+ ") AS pvt ORDER BY WKPCD, SCD, START_DATE ASC ";

		try (val stmt = this.connection().prepareStatement(String.format(SQL, functions, functions))) {
			stmt.setString(1, companyId);
			return new NtsResultSet(stmt.executeQuery()).getList(rec -> {
				//WorkPlaceSelectionExportData item = new WorkPlaceSelectionExportData();
				return toReportDataTable(rec, workPlaceFunction);
			});
		}
	}

	private WorkPlaceSelectionExportData toReportDataTable(NtsResultRecord rec,
			List<WorkPlaceFunction> workPlaceFunction) {
		Map<String, String> values =new HashMap<String, String>();
	
		for (int i = 0 ; i < workPlaceFunction.size(); i++) {
			values.put(workPlaceFunction.get(i).getFunctionNo().v().toString(), rec.getString((i + 1) + ""));
		}
		WorkPlaceSelectionExportData item = new WorkPlaceSelectionExportData(
				rec.getString("WKPCD"),
				rec.getString("WKP_NAME"), 
				rec.getString("SCD"), 
				rec.getString("BUSINESS_NAME"),
				rec.getGeneralDate("START_DATE"), 
				rec.getGeneralDate("END_DATE"), 
				values);
		return item;
	}
}
