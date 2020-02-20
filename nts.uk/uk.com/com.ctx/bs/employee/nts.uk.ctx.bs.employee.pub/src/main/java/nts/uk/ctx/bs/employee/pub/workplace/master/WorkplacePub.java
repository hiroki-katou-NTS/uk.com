package nts.uk.ctx.bs.employee.pub.workplace.master;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;

public interface WorkplacePub {

	/**
	 * [No.559]運用している職場の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param baseDate
	 * @return
	 */
	public List<WorkplaceInforExport> getAllActiveWorkplaceInfor(String companyId, GeneralDate baseDate);

	/**
	 * [No.560]職場IDから職場の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param listWorkplaceId
	 * @param baseDate
	 * @return
	 */
	public List<WorkplaceInforExport> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate);

	/**
	 * [No.561]過去の職場の情報を取得する
	 * 
	 * @param companyId
	 * @param historyId
	 * @param listWorkplaceId
	 * @return
	 */
	public List<WorkplaceInforExport> getPastWorkplaceInfor(String companyId, String historyId,
			List<String> listWorkplaceId);

	/**
	 * [No.567]職場の下位職場を取得する
	 *
	 * @param companyId
	 * @param baseDate
	 * @param parentWorkplaceId
	 * @return
	 */
	public List<String> getAllChildrenOfWorkplaceId(String companyId, GeneralDate baseDate, String parentWorkplaceId);

	/**
	 * [No.573]職場の下位職場を基準職場を含めて取得する
	 *
	 * @param companyId
	 * @param baseDate
	 * @param workplaceId
	 * @return
	 */
	public List<String> getWorkplaceIdAndChildren(String companyId, GeneralDate baseDate, String workplaceId);

	/**
	 * RequestList No.30
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	Optional<SWkpHistExport> findBySid(String employeeId, GeneralDate baseDate);
	
	/**
	 * [No.650]社員が所属している職場を取得する
	 * 社員と基準日から所属職場履歴項目を取得する
	 * @param employeeID
	 * @param date
	 * @return
	 */
	public AffWorkplaceHistoryItemExport getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date);
	
	/**
	 * [No.569]職場の上位職場を取得する
	 * @param companyID
	 * @param workplaceID
	 * @param date
	 * @return
	 */
	public List<String> getUpperWorkplace(String companyID, String workplaceID, GeneralDate date);
	/**
	 * [No.571]職場の上位職場を基準職場を含めて取得する
	 *
	 * @param companyId
	 * @param baseDate
	 * @param workplaceId
	 * @return
	 */
	public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId);
	/**
	 * [No.575]職場コードから職場IDを取得する
	 * @param 会社ID companyId
	 * @param 職場コード wkpCd
	 * @param 基準日 baseDate
	 * @return
	 */
	public Optional<String> getWkpNewByCdDate(String companyId, String wkpCd, GeneralDate baseDate);
}
