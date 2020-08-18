package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryGetDegreeInput {
	GeneralDate baseDate;
	String cId;
	List<String> qualificationIds;
	boolean getEmployeeCode;
	boolean getEmployeeName;
}