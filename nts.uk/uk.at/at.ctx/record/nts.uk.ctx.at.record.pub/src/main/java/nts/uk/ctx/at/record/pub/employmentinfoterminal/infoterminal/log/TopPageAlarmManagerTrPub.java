package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log;

import lombok.Getter;

/**
 * @author ThanhNX
 *
 *         管理社員ごとの既読状態
 */
@Getter
public class TopPageAlarmManagerTrPub {

	/** 管理社員ID */
	private String managerId;

	/** 了解フラグ */
	private RogerFlagPub rogerFlag;

	public TopPageAlarmManagerTrPub(String managerId, RogerFlagPub rogerFlag) {
		super();
		this.managerId = managerId;
		this.rogerFlag = rogerFlag;
	}

}
