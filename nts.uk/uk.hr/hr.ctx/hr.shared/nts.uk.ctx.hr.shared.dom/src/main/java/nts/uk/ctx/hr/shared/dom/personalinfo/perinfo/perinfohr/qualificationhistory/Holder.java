package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 保有者
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Holder {

	// 社員ID
	private String employeeId;
	
	// 社員CD
	private String employeeCd;
	
	// 社員名
	private Optional<String> employeeName;
}