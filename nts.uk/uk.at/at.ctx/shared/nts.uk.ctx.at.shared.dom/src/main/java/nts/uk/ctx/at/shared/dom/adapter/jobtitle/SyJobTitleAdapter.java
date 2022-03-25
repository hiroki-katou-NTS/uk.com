package nts.uk.ctx.at.shared.dom.adapter.jobtitle;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.EmployeePosition;

import java.util.List;

/**
 * @author sonnh1
 */
public interface SyJobTitleAdapter {

    // 社員職位Adapter.取得する (基準日: 年月日, 社員IDリスト: List<社員ID>): List<<Imported> 社員職位
    List<EmployeePosition> findSJobHistByListSIdV2(List<String> employeeIds, GeneralDate baseDate);

}