package nts.uk.ctx.at.schedule.dom.adapter.jobtitle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmployeePosition;

/**
 * 
 * @author sonnh1
 *
 */
public interface SyJobTitleAdapter {
	/**
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	Optional<EmployeeJobHistImported> findBySid(String employeeId, GeneralDate baseDate);
	
	Map<Pair<String, GeneralDate>, Pair<String,String>> getJobTitleMapIdBaseDateName(String companyId, List<String> jobIds, List<GeneralDate> baseDates);

	// 会社の職位リストを取得する
	List<PositionImport> findAll(String companyId, GeneralDate baseDate);
	
	// 社員職位Adapter.取得する (基準日: 年月日, 社員IDリスト: List<社員ID>): List<<Imported> 社員職位
	List<EmployeePosition> findSJobHistByListSIdV2(List<String> employeeIds, GeneralDate baseDate);

}
