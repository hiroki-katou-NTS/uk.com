package nts.uk.ctx.at.auth.pub.wplmanagementauthority;

import lombok.Value;

@Value
public class WorkPlaceFunctionExport {
	/**
	 * NO
	 */
	private int functionNo;
	
	/**
	 * 初期値
	 */
	private boolean initialValue;

	/**
	 * 表示名
	 */
	private String displayName;

	/**
	 * 表示順
	 */
	private int displayOrder;
	
	/**
	 * 説明文
	 */
	private String description;
}
