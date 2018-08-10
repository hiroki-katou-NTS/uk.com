package nts.uk.ctx.sys.assist.dom.mastercopy;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class MasterCopyData.
 */
// マスタコピーデータ
@Getter
@Setter
public class MasterCopyData extends AggregateRoot {

	/** The master copy id. */
	// マスタコピーID
	private String masterCopyId;

	/** The target table. */
	// 対象テーブル
	private TargetTableInfo targetTable;

	/**
	 * Instantiates a new master copy data.
	 *
	 * @param memento
	 *            the memento
	 */
	public MasterCopyData(MasterCopyDataGetMemento memento) {
		this.masterCopyId = memento.getMasterCopyId();
		this.targetTable = memento.getTargetTable();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(MasterCopyDataSetMemento memento) {
		memento.setMasterCopyId(this.masterCopyId);
		memento.setTargetTable(this.targetTable);
	}

}
