package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateItemAttributes;
import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;

/**
 * @author tuannt-nws
 *
 */
public class JpaCalculateAttendanceRecordGetMemento implements CalculateAttendanceRecordGetMemento {

	/** The kfnst attnd rec. */
	private KfnstAttndRec kfnstAttndRec;

	/** The kfnst attnd rec item. */
	private List<KfnstAttndRecItem> kfnstAttndRecItems;
	
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
	public JpaCalculateAttendanceRecordGetMemento(KfnstAttndRec kfnstAttndRec,
			List<KfnstAttndRecItem> kfnstAttndRecItems) {
		super();
		this.kfnstAttndRec = kfnstAttndRec;
		this.kfnstAttndRecItems = kfnstAttndRecItems;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento#getAttribute()
	 */
	@Override
	public CalculateItemAttributes getAttribute() {
		return CalculateItemAttributes.valueOf(this.kfnstAttndRec.getAttribute().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento#getName()
	 */
	@Override
	public ItemName getName() {
		return new ItemName(this.kfnstAttndRec.getItemName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento#getAddedItem()
	 */
	@Override
	public List<Integer> getAddedItem() {
		if (this.kfnstAttndRecItems == null || this.kfnstAttndRecItems.isEmpty())
			return new ArrayList<Integer>();
		List<Integer> result = this.kfnstAttndRecItems.stream()
				.filter(e -> e.getFormulaType().compareTo(new BigDecimal(ADD_FORMULA_TYPE)) == 0)
				.map(e -> (int) e.getId().getTimeItemId()).collect(Collectors.toList());
		return result;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento#getSubtractedItem()
	 */
	@Override
	public List<Integer> getSubtractedItem() {
		if (this.kfnstAttndRecItems == null || this.kfnstAttndRecItems.isEmpty())
			return new ArrayList<Integer>();
		List<Integer> result = this.kfnstAttndRecItems.stream()
				.filter(e -> e.getFormulaType().compareTo(new BigDecimal(SUB_FORMULA_TYPE)) == 0)
				.map(obj -> (int) obj.getId().getTimeItemId()).collect(Collectors.toList());
		return result;
	}

}
