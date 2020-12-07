package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;

import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleItemAttributes;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;

public class JpaSingleAttendanceRecordSetMemento implements SingleAttendanceRecordSetMemento {
	
	/** The kfnmt rpt wk atd outframe. */
	private KfnmtRptWkAtdOutframe kfnmtRptWkAtdOutframe;

	/** The kfnmt rpt wk atd outatd. */
	private KfnmtRptWkAtdOutatd kfnmtRptWkAtdOutatd;

	/**
	 * Instantiates a new jpa single attendance record set memento.
	 */
	public JpaSingleAttendanceRecordSetMemento() {
		super();
	}

	/**
	 * Instantiates a new jpa single attendance record set memento.
	 *
	 * @param kfnmtRptWkAtdOutframe
	 *            the kfnst attnd rec
	 * @param kfnmtRptWkAtdOutatd
	 *            the kfnst attnd rec item
	 */
	public JpaSingleAttendanceRecordSetMemento(KfnmtRptWkAtdOutframe kfnmtRptWkAtdOutframe, KfnmtRptWkAtdOutatd kfnmtRptWkAtdOutatd) {
		super();
		this.kfnmtRptWkAtdOutframe = kfnmtRptWkAtdOutframe;
		this.kfnmtRptWkAtdOutatd = kfnmtRptWkAtdOutatd==null ? new KfnmtRptWkAtdOutatd() : kfnmtRptWkAtdOutatd;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordSetMemento#setAttribute(nts.uk.ctx.at.function.dom.attendancerecord.item.SingleItemAttributes)
	 */
	@Override
	public void setAttribute(SingleItemAttributes attribute) {
		this.kfnmtRptWkAtdOutframe.setAttribute(new BigDecimal(attribute.value));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordSetMemento#setName(nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName)
	 */
	@Override
	public void setName(ItemName itemName) {
		this.kfnmtRptWkAtdOutframe.setItemName(itemName.v());

	}

	@Override
	public void setTimeItemId(Integer timeItemId) {
		if (timeItemId == null) {
			return;
		}
		this.kfnmtRptWkAtdOutatd.setTimeItemId(timeItemId);
	}

}
