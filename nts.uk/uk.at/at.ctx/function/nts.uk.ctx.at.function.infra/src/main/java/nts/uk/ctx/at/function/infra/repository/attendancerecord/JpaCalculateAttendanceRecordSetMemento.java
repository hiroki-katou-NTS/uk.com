package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateItemAttributes;
import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaCalculateAttendanceRecordSetMemento.
 *
 * @author tuannt-nws
 */
public class JpaCalculateAttendanceRecordSetMemento implements CalculateAttendanceRecordSetMemento {
	/** The kfnst attnd rec. */
	private KfnstAttndRec kfnstAttndRec;
	
	/** The kfnst attnd rec items. */
	private List<KfnstAttndRecItem> kfnstAttndRecItems;
	
	/** The add formula type. */
	private final int ADD_FORMULA_TYPE = 1;
	
	/** The subtract formualt type. */
	private final int SUBTRACT_FORMULA_TYPE = 2;

	/**
	 * Instantiates a new jpa calculate attendance record set memento.
	 *
	 * @param kfnstAttndRec            the kfnst attnd rec
	 */
	public JpaCalculateAttendanceRecordSetMemento(KfnstAttndRec kfnstAttndRec, List<KfnstAttndRecItem> kfnstAttndRecItems ) {
		super();
		this.kfnstAttndRec = kfnstAttndRec;
		this.kfnstAttndRecItems = kfnstAttndRecItems;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento#setAttribute(nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateItemAttributes)
	 */
	@Override
	public void setAttribute(CalculateItemAttributes attribute) {
		this.kfnstAttndRec.setAttribute(new BigDecimal(attribute.value));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento#setName(nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName)
	 */
	@Override
	public void setName(ItemName name) {
		this.kfnstAttndRec.setItemName(name.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento#setAddedItem(java.util.List)
	 */
	@Override
	public void setAddedItem(List<Integer> idListAdd) {
		// remove old value
		this.kfnstAttndRecItems.forEach(e -> {
			if (e.getFormulaType() == new BigDecimal(ADD_FORMULA_TYPE)) {
				this.kfnstAttndRecItems.remove(e);
			}
		});
		// add new value
		idListAdd.forEach(e -> {
			UID uid = new UID();
			KfnstAttndRecItem item = new KfnstAttndRecItem(uid.toString(), this.kfnstAttndRec.getId().getCid(),  this.kfnstAttndRec.getId().getColumnIndex(),
					this.kfnstAttndRec.getId().getExportCd(),  new BigDecimal(ADD_FORMULA_TYPE),  this.kfnstAttndRec.getId().getOutputAtr()
					,  this.kfnstAttndRec.getId().getPosition(), e);
			this.kfnstAttndRecItems.add(item);
		});
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento#setSubtractedItem(java.util.List)
	 */
	@Override
	public void setSubtractedItem(List<Integer> idListSubtract) {
		// remove old value
		this.kfnstAttndRecItems.forEach(e -> {
			if (e.getFormulaType() == new BigDecimal(SUBTRACT_FORMULA_TYPE)) {
				this.kfnstAttndRecItems.remove(e);
			}
		});
		// add new value
		idListSubtract.forEach(e -> {
			UID uid = new UID();
			KfnstAttndRecItem item = new KfnstAttndRecItem(uid.toString(), this.kfnstAttndRec.getId().getCid(),  this.kfnstAttndRec.getId().getColumnIndex(),
					this.kfnstAttndRec.getId().getExportCd(),  new BigDecimal(SUBTRACT_FORMULA_TYPE),  this.kfnstAttndRec.getId().getOutputAtr()
					,  this.kfnstAttndRec.getId().getPosition(), e);
			this.kfnstAttndRecItems.add(item);
		});
	}

}
