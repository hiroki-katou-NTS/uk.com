package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;

@Value
public class DisplayWorkInfoByDateDto {
	
		/** 社員勤務情報　dto */
		private EmployeeWorkInfoDto workInfoDto;
		
		/** 社員勤務予定　dto */
		private EmployeeWorkScheduleDto workScheduleDto;
		
		/** 勤務固定情報　dto */
		private FixedWorkInforDto fixedWorkInforDto;
}
