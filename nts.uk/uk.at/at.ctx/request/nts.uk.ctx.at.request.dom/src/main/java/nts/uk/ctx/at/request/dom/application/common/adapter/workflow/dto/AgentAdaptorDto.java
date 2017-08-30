package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;


import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class AgentAdaptorDto {
	/**
	 * employee id
	 */
	private String employeeId;
	
	private String requestId;

	/**
	 * 代行依頼期間
	 * Start date
	 */
	private GeneralDate startDate;

	/**
	 * 代行依頼期間
	 * End date
	 */
	private GeneralDate endDate;

	//----就業承認---
	
	/**
	 * agent employee id
	 */
	private String agentSid1;
	
	/**
	 * 0:代行者指定
	 * 1:パス
	 * 2:設定なし
	 */
	private int agentAppType1;

	//----人事承認---
	
	/**
	 * agent employee id
	 */
	private String agentSid2;

	/**
	 * 0:代行者指定
	 * 1:パス
	 * 2:設定なし
	 */
	private int agentAppType2;

	//----給与承認---
	
	/**
	 * agent employee id
	 */
	private String agentSid3;

	/**
	 * 0:代行者指定
	 * 1:パス
	 * 2:設定なし
	 */
	private int agentAppType3;

	//----経理承認---
	
	/**
	 * agent employee id
	 */
	private String agentSid4;

	/**
	 * 0:代行者指定
	 * 1:パス
	 * 2:設定なし
	 */
	private int agentAppType4;
}
