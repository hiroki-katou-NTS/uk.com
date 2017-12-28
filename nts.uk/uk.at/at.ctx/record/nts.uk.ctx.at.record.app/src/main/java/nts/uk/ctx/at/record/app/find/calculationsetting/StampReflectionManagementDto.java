package nts.uk.ctx.at.record.app.find.calculationsetting;

import java.math.BigDecimal;

import lombok.Value;
/**
 * 
 * @author phongtq
 *
 */
@Value
public class StampReflectionManagementDto {
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

	/** 入退門の管理をする */
	private int managementOfEntrance;

	/** 未来日の自動打刻セット区分 */
	private int autoStampForFutureDayClass;

	/** 休憩として扱う外出区分 */
	private int outingAtr;

	/** 最大使用回数 */
	private BigDecimal maxUseCount;
}
