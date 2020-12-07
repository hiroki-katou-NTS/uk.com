package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log;

import lombok.Getter;

/**
 * @author ThanhNX
 *
 *         管理社員ごとの既読状態
 */
@Getter
public class TopPageAlarmManagerTrRQ {

	/** 管理社員ID */
	private String managerId;

	/** 了解フラグ */
	private RogerFlagRQ rogerFlag;

	public TopPageAlarmManagerTrRQ(String managerId, RogerFlagRQ rogerFlag) {
		super();
		this.managerId = managerId;
		this.rogerFlag = rogerFlag;
	}

}
