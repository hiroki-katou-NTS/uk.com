package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryGetDegreeDto {

	private String qualificationId;
	
	private List<Holders> holders;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Holders {

	private String employeeId;

	private String employeeCd;

	private String employeeName;
}