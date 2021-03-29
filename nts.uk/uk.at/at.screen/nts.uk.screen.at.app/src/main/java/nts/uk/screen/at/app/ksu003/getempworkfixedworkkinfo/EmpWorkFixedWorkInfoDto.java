package nts.uk.screen.at.app.ksu003.getempworkfixedworkkinfo;

import lombok.Value;
import nts.uk.screen.at.app.ksu003.start.dto.EmployeeWorkInfoDto;
import nts.uk.screen.at.app.ksu003.start.dto.EmployeeWorkScheduleDto;
import nts.uk.screen.at.app.ksu003.start.dto.FixedWorkInforDto;

@Value
public class EmpWorkFixedWorkInfoDto {
	
		/** 社員勤務情報　dto */
		private EmployeeWorkInfoDto workInfoDto;
		
		/** 社員勤務予定　dto */
		private EmployeeWorkScheduleDto workScheduleDto;
		
		/** 勤務固定情報　dto */
		private FixedWorkInforDto fixedWorkInforDto;
}
