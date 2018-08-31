/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.pub.command.mastercopy;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.event.DomainEvent;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GlobalMasterDataCopyEvent extends DomainEvent {

	/** The company id. */
	// コピー先会社
	private String companyId;

	/** The copy target list. */
	// コピー対象一覧
	private List<GlobalCopyTargetItem> copyTargetList;

	/** The task id. */
	// タスクID
	private String taskId;

	/**
	 * Instantiates a new global master data copy event.
	 *
	 * @param companyId
	 *            the company id
	 * @param copyTargetList
	 *            the copy target list
	 * @param taskId
	 *            the task id
	 */
	public GlobalMasterDataCopyEvent(String companyId, List<GlobalCopyTargetItem> copyTargetList,
			String taskId) {
		super();
		this.companyId = companyId;
		this.copyTargetList = copyTargetList;
		this.taskId = taskId;
	}

	/**
	 * Gets the copy target item.
	 *
	 * @param itemEnum
	 *            the item enum
	 * @return the copy target item
	 */
	public Optional<GlobalCopyTargetItem> getCopyTargetItem(GlobalCopyItemEnum itemEnum) {
		return this.copyTargetList.stream()
				.filter(item -> item.getMasterCopyTarget().equals(itemEnum.copyTargetString))
				.findFirst();
	}

}
