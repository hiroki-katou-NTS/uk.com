/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.dom.mastercopy;

import lombok.Data;

// コピー対象一覧
@Data
public class CopyTargetItem {

	/** The company id. */
	// マスタコピーID
	private String masterCopyId;

	// マスタコピー対象
	private String masterCopyTarget;

	/** The copy method. */
	// コピー方法
	private CopyMethod processMethod;

	/**
	 * Instantiates a new copy target item.
	 *
	 * @param masterCopyId
	 *            the master copy id
	 * @param masterCopyTarget
	 *            the master copy target
	 * @param processMethod
	 *            the process method
	 */
	public CopyTargetItem(String masterCopyId, String masterCopyTarget, CopyMethod processMethod) {
		super();
		this.masterCopyId = masterCopyId;
		this.masterCopyTarget = masterCopyTarget;
		this.processMethod = processMethod;
	}

}
