package nts.uk.ctx.at.record.app.command.calculationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;

/**
 * Instantiates a new stamp reflection management command.
 *
 * @param companyId the company id
 * @param breakSwitchClass the break switch class
 * @param autoStampReflectionClass the auto stamp reflection class
 * @param actualStampOfPriorityClass the actual stamp of priority class
 * @param reflectWorkingTimeClass the reflect working time class
 * @param goBackOutCorrectionClass the go back out correction class
 * @param autoStampForFutureDayClass the auto stamp for future day class
 */
@AllArgsConstructor
@Data
public class StampReflectionManagementCommand {
	/** 会社ID */
	private String companyId;

	/** 休出切替区分 */
	private int breakSwitchClass;

	/** 自動打刻反映区分 */
	private int autoStampReflectionClass;

	/** 実打刻と申請の優先区分 */
	private int actualStampOfPriorityClass;

	/** 就業時間帯打刻反映区分 */
	private int reflectWorkingTimeClass;

	/** 直行直帰外出補正区分 */
	private int goBackOutCorrectionClass;

	/** 未来日の自動打刻セット区分 */
	private int autoStampForFutureDayClass;


	public StampReflectionManagement toDomain(String companyId) {
		return StampReflectionManagement.createJavaType(companyId, this.breakSwitchClass, this.autoStampReflectionClass,
				this.actualStampOfPriorityClass, this.reflectWorkingTimeClass, this.goBackOutCorrectionClass,
				this.autoStampForFutureDayClass
				);
	}
}
