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

	/** The master copy target. */
	// マスタコピー対象
	private MasterCopyTarget masterCopyTarget;

	/**
	 * Instantiates a new master copy data.
	 *
	 * @param memento
	 *            the memento
	 */
	public MasterCopyData(MasterCopyDataGetMemento memento) {
		this.masterCopyId = memento.getMasterCopyId();
		this.masterCopyTarget = memento.getMasterCopyTarget();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(MasterCopyDataSetMemento memento) {
		memento.setMasterCopyId(this.masterCopyId);
		memento.setMasterCopyTarget(this.masterCopyTarget);
	}

}
