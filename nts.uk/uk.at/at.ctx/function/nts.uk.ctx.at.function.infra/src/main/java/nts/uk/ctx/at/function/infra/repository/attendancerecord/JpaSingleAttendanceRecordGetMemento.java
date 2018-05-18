package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleItemAttributes;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;

public class JpaSingleAttendanceRecordGetMemento implements SingleAttendanceRecordGetMemento {

	/** The kfnst attnd rec. */
	private KfnstAttndRec kfnstAttndRec;

	/** The kfnst attnd rec item. */
	private KfnstAttndRecItem kfnstAttndRecItem;

	/**
	 * Instantiates a new jpa single attendance record get memento.
	 *
	 * @param kfnstAttndRec
	 *            the kfnst attnd rec
	 * @param kfnstAttndRecItem
	 *            the kfnst attnd rec item
	 */
	public JpaSingleAttendanceRecordGetMemento(KfnstAttndRec kfnstAttndRec, KfnstAttndRecItem kfnstAttndRecItem) {
		super();
		this.kfnstAttndRec = kfnstAttndRec;
		this.kfnstAttndRecItem = kfnstAttndRecItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordGetMemento#getAttribute()
	 */
	@Override
	public SingleItemAttributes getAttribute() {
		return SingleItemAttributes.valueOf(kfnstAttndRec.getAttribute().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordGetMemento#getName()
	 */
	@Override
	public ItemName getName() {
		return new ItemName(kfnstAttndRec.getItemName().toString());
	}

	@Override
	public Integer getTimeItemId() {
		if (this.kfnstAttndRecItem == null)
			return 0;
		return (int) kfnstAttndRecItem.getId().getTimeItemId();
	}

}
