package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 社員の勤務予定の登録対象日
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
public class DateRegistedEmpSche {
	private String employeeId;
	private List<GeneralDate> listDate;
}
