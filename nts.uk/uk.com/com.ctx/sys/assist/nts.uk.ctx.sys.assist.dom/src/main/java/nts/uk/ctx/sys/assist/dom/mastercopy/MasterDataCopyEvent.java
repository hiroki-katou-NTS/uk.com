/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.event.DomainEvent;

/**
 * The Class MasterDataCopyEvent.
 */
@Builder
@Getter
// 初期値コピー
public class MasterDataCopyEvent extends DomainEvent {

	/** The company id. */
	// コピー先会社
	private String companyId;

	/** The copy target list. */
	// コピー対象一覧
	private List<CopyTargetItem> copyTargetList;

	/** The task id. */
	// タスクID
	private String taskId;

	/**
	 * Instantiates a new master data copy event.
	 */
	public MasterDataCopyEvent() {
		super();
	}

	/**
	 * Instantiates a new master data copy event.
	 *
	 * @param companyId
	 *            the company id
	 * @param copyTargetList
	 *            the copy target list
	 * @param taskId
	 *            the task id
	 */
	public MasterDataCopyEvent(String companyId, List<CopyTargetItem> copyTargetList,
			String taskId) {
		super();
		this.companyId = companyId;
		this.copyTargetList = copyTargetList;
		this.taskId = taskId;
	}

}
