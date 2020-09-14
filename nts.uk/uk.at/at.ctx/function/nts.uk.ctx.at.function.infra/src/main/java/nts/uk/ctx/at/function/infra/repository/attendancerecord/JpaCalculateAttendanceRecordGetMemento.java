package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateItemAttributes;
import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;

/**
 * @author tuannt-nws
 *
 */
public class JpaCalculateAttendanceRecordGetMemento implements CalculateAttendanceRecordGetMemento {

	/** The kfnst attnd rec. */
	private KfnmtRptWkAtdOutframe kfnmtRptWkAtdOutframe;

	/** The kfnst attnd rec item. */
	private List<KfnmtRptWkAtdOutatd> kfnmtRptWkAtdOutatd;
	
	/** The add formular type. */
	private final int ADD_FORMULA_TYPE = 1;
	
	/** The sub formular type. */
	private final int SUB_FORMULA_TYPE = 2;

	/**
	 * Instantiates a new jpa calculate attendance record get memento.
	 *
	 * @param kfnstAttndRec
	 *            the kfnst attnd rec
	 * @param kfnstAttndRecItem
	 *            the kfnst attnd rec item
	 */
	public JpaCalculateAttendanceRecordGetMemento(KfnmtRptWkAtdOutframe kfnmtRptWkAtdOutframe,
			List<KfnmtRptWkAtdOutatd> kfnmtRptWkAtdOutatd) {
		super();
		this.kfnmtRptWkAtdOutframe = kfnmtRptWkAtdOutframe;
		this.kfnmtRptWkAtdOutatd = kfnmtRptWkAtdOutatd;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento#getAttribute()
	 */
	@Override
	public CalculateItemAttributes getAttribute() {
		return CalculateItemAttributes.valueOf(this.kfnmtRptWkAtdOutframe.getAttribute().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento#getName()
	 */
	@Override
	public ItemName getName() {
		return new ItemName(this.kfnmtRptWkAtdOutframe.getItemName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento#getAddedItem()
	 */
	@Override
	public List<Integer> getAddedItem() {
		if (this.kfnmtRptWkAtdOutatd == null || this.kfnmtRptWkAtdOutatd.isEmpty())
			return new ArrayList<Integer>();
		List<Integer> result = this.kfnmtRptWkAtdOutatd.stream()
				.filter(e -> e.getFormulaType().compareTo(new BigDecimal(ADD_FORMULA_TYPE)) == 0)
				.map(e -> (int) e.getTimeItemId()).collect(Collectors.toList());
		return result;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento#getSubtractedItem()
	 */
	@Override
	public List<Integer> getSubtractedItem() {
		if (this.kfnmtRptWkAtdOutatd == null || this.kfnmtRptWkAtdOutatd.isEmpty())
			return new ArrayList<Integer>();
		List<Integer> result = this.kfnmtRptWkAtdOutatd.stream()
				.filter(e -> e.getFormulaType().compareTo(new BigDecimal(SUB_FORMULA_TYPE)) == 0)
				.map(obj -> (int) obj.getTimeItemId()).collect(Collectors.toList());
		return result;
	}

}
