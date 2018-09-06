package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class MasterCopyData.
 */
// マスタコピーデータ
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MasterCopyData extends AggregateRoot {

	/** The master copy id. */
	// マスタコピーID
	private MasterCopyCategoryNo categoryNo;

	/** The target table. */
	// 対象テーブル
	private List<TargetTableInfo> targetTables;

	/**
	 * Instantiates a new master copy data.
	 *
	 * @param memento
	 *            the memento
	 */
	public MasterCopyData(MasterCopyDataGetMemento memento) {
		this.categoryNo = memento.getCategoryNo();
		this.targetTables = memento.getTargetTable();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(MasterCopyDataSetMemento memento) {
		memento.setCategoryNo(this.categoryNo);
		memento.setTargetTable(this.targetTables);
	}

}
