package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log;

import lombok.Getter;

/**
 * @author ThanhNX
 *
 *         管理社員ごとの既読状態
 */
@Getter
public class TopPageAlarmManagerTr {

	/** 管理社員ID */
	private String managerId;

	/** 了解フラグ */
	private RogerFlag rogerFlag;

	public TopPageAlarmManagerTr(String managerId, RogerFlag rogerFlag) {
		super();
		this.managerId = managerId;
		this.rogerFlag = rogerFlag;
	}

}
