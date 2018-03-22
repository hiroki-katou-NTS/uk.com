package nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Data
public class AddSpecialLeaCommand {

	private String sid;
	
	private String specialLeaId;

	private int specialLeaCode;
	/**
	 * 付与日
	 */
	private GeneralDate grantDate;

	/**
	 * 期限日
	 */
	private GeneralDate deadlineDate;

	/**
	 * 期限切れ状態
	 */
	private int expStatus;

	/**
	 * 期限切れ状態
	 */
	private int registerType;

	/**
	 * 付与日数
	 */
	private int numberOfDayGrant;

	/**
	 * 付与時間
	 */
	private int timeGrant;

	/**
	 * 使用日数
	 */
	private double numberOfDayUse;

	private double numberOfDayUseToLose;
	/**
	 * 使用時間
	 */
	private int timeUse;

	/**
	 * 上限超過消滅日数
	 */
	private int numberOfExceededDays;
	/**
	 * 上限超過消滅時間
	 */
	private int timeExceeded;

	/**
	 * 残日数
	 */
	private double numberOfDayRemain;

	/**
	 * 残時間
	 */
	private int timeRemain;

}
