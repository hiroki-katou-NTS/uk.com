package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 勤務予定の登録対象日一覧
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
public class RegistrationListDateSchedule {
	private List<DateRegistedEmpSche> registrationListDateSchedule;
}
