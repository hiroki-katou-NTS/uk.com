/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WorkplaceInfo.
 */

/**
 * Gets the outside wkp code.
 *
 * @return the outside wkp code
 */
@Getter
//職場情報
public class WorkplaceInfo extends AggregateRoot {

	/** The company id. */
	//会社ID
	private String companyId;

	/** The history id. */
	//履歴ID
	private HistoryId historyId;

	/** The workplace id. */
	//職場ID
	private WorkplaceId workplaceId;

	/** The workplace code. */
	//職場コード
	private WorkplaceCode workplaceCode;

	/** The workplace name. */
	//職場名称
	private WorkplaceName workplaceName;

	/** The wkp generic name. */
	//職場総称
	private WorkplaceGenericName wkpGenericName;

	/** The wkp display name. */
	//職場表示名
	private WorkplaceDisplayName wkpDisplayName;

	/** The outside wkp code. */
	//職場外部コード
	private OutsideWorkplaceCode outsideWkpCode;

	/**
	 * Instantiates a new workplace info.
	 *
	 * @param memento the memento
	 */
	public WorkplaceInfo(WorkplaceInfoGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.historyId = memento.getHistoryId();
		this.workplaceId = memento.getWorkplaceId();
		this.workplaceCode = memento.getWorkplaceCode();
		this.workplaceName = memento.getWorkplaceName();
		this.wkpGenericName = memento.getWkpGenericName();
		this.wkpDisplayName = memento.getWkpDisplayName();
		this.outsideWkpCode = memento.getOutsideWkpCode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceInfoSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setHistoryId(this.historyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setWorkplaceCode(this.workplaceCode);
		memento.setWorkplaceName(this.workplaceName);
		memento.setWkpGenericName(this.wkpGenericName);
		memento.setWkpDisplayName(this.wkpDisplayName);
		memento.setOutsideWkpCode(this.outsideWkpCode);
	}
}
