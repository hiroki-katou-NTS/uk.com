package nts.uk.ctx.office.dom.favorite.adapter;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class EmployeeJobHistImport {

	// 社員ID
	private String employeeId;

	// 職位ID
	private String jobTitleID;

	// 職位名称
	private String jobTitleName;
	
    /** The sequence code. */
    // 序列コード
    private String sequenceCode;

	// 配属期間 start
	private GeneralDate startDate;

	// 配属期間 end
	private GeneralDate endDate;

	// 社員コード
	private String jobTitleCode;
}
