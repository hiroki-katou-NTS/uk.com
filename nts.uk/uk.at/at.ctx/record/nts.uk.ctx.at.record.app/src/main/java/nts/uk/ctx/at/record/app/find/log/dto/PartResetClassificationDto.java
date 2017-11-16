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

		//休業再設定
		private boolean closedHolidays;

		// 就業時間帯再設定
		private boolean resettingWorkingHours;

		// 打刻のみ再度反映
		private boolean reflectsTheNumberOfFingerprintChecks;

		// 特定日区分再設定
		private boolean specificDateClassificationResetting;

		// 申し送り時間再設定
		private boolean resetTimeAssignment;

		// 育児・介護短時間再設定
		private boolean resetTimeChildOrNurseCare;

		// 計算区分再設定
		private boolean calculationClassificationResetting;
		
		public static PartResetClassificationDto fromDomain(PartResetClassification domain){
			return new PartResetClassificationDto(
					domain.isMasterReconfiguration(),
					domain.isClosedHolidays(),
					domain.isResettingWorkingHours(),
					domain.isReflectsTheNumberOfFingerprintChecks(),
					domain.isSpecificDateClassificationResetting(),
					domain.isResetTimeAssignment(),
					domain.isResetTimeChildOrNurseCare(),
					domain.isCalculationClassificationResetting()
					);
			
		}
}
