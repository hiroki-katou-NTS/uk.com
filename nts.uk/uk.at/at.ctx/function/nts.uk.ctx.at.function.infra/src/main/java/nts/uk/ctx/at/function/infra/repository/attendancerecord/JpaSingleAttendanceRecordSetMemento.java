package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;

import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleItemAttributes;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;

public class JpaSingleAttendanceRecordSetMemento implements SingleAttendanceRecordSetMemento {
	/** The kfnst attnd rec. */
	private KfnstAttndRec kfnstAttndRec;

	/** The kfnst attnd rec item. */
	private KfnstAttndRecItem kfnstAttndRecItem;

	/**
	 * Instantiates a new jpa single attendance record set memento.
	 */
	public JpaSingleAttendanceRecordSetMemento() {
		super();
	}

	/**
	 * Instantiates a new jpa single attendance record set memento.
	 *
	 * @param kfnstAttndRec
	 *            the kfnst attnd rec
	 * @param kfnstAttndRecItem
	 *            the kfnst attnd rec item
	 */
	public JpaSingleAttendanceRecordSetMemento(KfnstAttndRec kfnstAttndRec, KfnstAttndRecItem kfnstAttndRecItem) {
		super();
		this.kfnstAttndRec = kfnstAttndRec;
		this.kfnstAttndRecItem = kfnstAttndRecItem==null ? new KfnstAttndRecItem() : kfnstAttndRecItem;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordSetMemento#setAttribute(nts.uk.ctx.at.function.dom.attendancerecord.item.SingleItemAttributes)
	 */
	@Override
	public void setAttribute(SingleItemAttributes attribute) {
		this.kfnstAttndRec.setAttribute(new BigDecimal(attribute.value));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordSetMemento#setName(nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName)
	 */
	@Override
	public void setName(ItemName itemName) {
		this.kfnstAttndRec.setItemName(itemName.v());

	}

	@Override
	public void setTimeItemId(Integer timeItemId) {
		this.kfnstAttndRecItem.setTimeItemId(timeItemId);
	}

}
