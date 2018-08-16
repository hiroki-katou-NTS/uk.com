/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.pub.command.mastercopy;

import lombok.Value;

/**
 * The Class GlobalCopyTargetItem.
 */
@Value
public class GlobalCopyTargetItem {

	/** The company id. */
	// マスタコピーID
	private String masterCopyId;

	// マスタコピー対象
	private String masterCopyTarget;

	/** The copy method. */
	// コピー方法
	private GlobalCopyMethod processMethod;

	/**
	 * Instantiates a new global copy target item.
	 *
	 * @param masterCopyId the master copy id
	 * @param masterCopyTarget the master copy target
	 * @param processMethod the process method
	 */
	public GlobalCopyTargetItem(String masterCopyId, String masterCopyTarget,
			GlobalCopyMethod processMethod) {
		super();
		this.masterCopyId = masterCopyId;
		this.masterCopyTarget = masterCopyTarget;
		this.processMethod = processMethod;
	}

}
