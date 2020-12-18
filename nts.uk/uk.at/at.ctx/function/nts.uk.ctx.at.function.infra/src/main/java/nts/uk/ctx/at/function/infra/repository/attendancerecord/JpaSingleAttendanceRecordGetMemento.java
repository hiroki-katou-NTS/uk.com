package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleItemAttributes;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;

/**
 * @author tuannt-nws
 *
 */
public class JpaSingleAttendanceRecordGetMemento implements SingleAttendanceRecordGetMemento {

	/** The kfnst attnd rec. */
	private KfnmtRptWkAtdOutframe kfnmtRptWkAtdOutframe;

	/** The kfnst attnd rec item. */
	private KfnmtRptWkAtdOutatd kfnmtRptWkAtdOutatd;

	
	/**
	 * Instantiates a new jpa single attendance record get memento.
	 *
	 * @param kfnmtRptWkAtdOutframe the kfnmt rpt wk atd outframe
	 * @param kfnstAttndRecItem the kfnst attnd rec item
	 */
	public JpaSingleAttendanceRecordGetMemento(KfnmtRptWkAtdOutframe kfnmtRptWkAtdOutframe, KfnmtRptWkAtdOutatd kfnmtRptWkAtdOutatd) {
		super();
		this.kfnmtRptWkAtdOutframe = kfnmtRptWkAtdOutframe;
		if (kfnmtRptWkAtdOutatd != null) {
			this.kfnmtRptWkAtdOutatd = kfnmtRptWkAtdOutatd;
		}else {
			this.kfnmtRptWkAtdOutatd = new KfnmtRptWkAtdOutatd();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordGetMemento#getAttribute()
	 */
	@Override
	public SingleItemAttributes getAttribute() {
		return SingleItemAttributes.valueOf(kfnmtRptWkAtdOutframe.getAttribute().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordGetMemento#getName()
	 */
	@Override
	public ItemName getName() {
		return new ItemName(kfnmtRptWkAtdOutframe.getItemName().toString());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento#getTimeItemId()
	 */
	@Override
	public Integer getTimeItemId() {
		return (int)kfnmtRptWkAtdOutatd.getTimeItemId();
	}

}
