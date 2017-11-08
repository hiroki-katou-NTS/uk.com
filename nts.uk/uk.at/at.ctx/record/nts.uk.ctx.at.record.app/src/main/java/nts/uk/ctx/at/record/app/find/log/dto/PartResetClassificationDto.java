package nts.uk.ctx.at.record.app.find.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.log.PartResetClassification;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartResetClassificationDto {
	//マスタ再設定
		private boolean masterReconfiguration;
		private String masterReconfigurationName;

		//休業再設定
		private boolean closedHolidays;
		private String closedHolidaysName;

		// 就業時間帯再設定
		private boolean resettingWorkingHours;
		private String resettingWorkingHoursName;

		// 打刻のみ再度反映
		private boolean reflectsTheNumberOfFingerprintChecks;
		private String reflectsTheNumberOfFingerprintChecksName;

		// 特定日区分再設定
		private boolean specificDateClassificationResetting;
		private String specificDateClassificationResettingName;

		// 申し送り時間再設定
		private boolean resetTimeAssignment;
		private String resetTimeAssignmentName;

		// 育児・介護短時間再設定
		private boolean resetTimeChildOrNurseCare;
		private String resetTimeChildOrNurseCareName;

		// 計算区分再設定
		private boolean calculationClassificationResetting;
		private String calculationClassificationResettingName;
		
		public static PartResetClassificationDto fromDomain(PartResetClassification domain){
			return new PartResetClassificationDto(
					domain.isMasterReconfiguration(),
					"マスタ再設定",
					domain.isClosedHolidays(),
					"休業再設定",
					domain.isResettingWorkingHours(),
					"就業時間帯再設定",
					domain.isReflectsTheNumberOfFingerprintChecks(),
					"打刻のみ再度反映",
					domain.isSpecificDateClassificationResetting(),
					"特定日区分再設定",
					domain.isResetTimeAssignment(),
					"申し送り時間再設定",
					domain.isResetTimeChildOrNurseCare(),
					"育児・介護短時間再設定",
					domain.isCalculationClassificationResetting(),
					"計算区分再設定"
					);
			
		}
}
