package nts.uk.ctx.at.schedule.dom.adapter.employmentstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 社員の在職状態
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Getter
public class EmploymentStatusImported {

	/** 社員ID */
	private String employeeId;

	/** 在職情報 */
	private List<EmploymentInfoImported> employmentInfo;
}
