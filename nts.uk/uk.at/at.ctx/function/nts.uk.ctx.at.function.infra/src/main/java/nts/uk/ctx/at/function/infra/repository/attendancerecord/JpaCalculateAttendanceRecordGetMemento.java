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

public class JpaCalculateAttendanceRecordGetMemento implements CalculateAttendanceRecordGetMemento {

	/** The kfnst attnd rec. */
	private KfnstAttndRec kfnstAttndRec;

	/** The kfnst attnd rec item. */
	private List<KfnstAttndRecItem> kfnstAttndRecItems;

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

	@Override
	public CalculateItemAttributes getAttribute() {
		return CalculateItemAttributes.valueOf(this.kfnstAttndRec.getAttribute().intValue());
	}

	@Override
	public ItemName getName() {
		return new ItemName(this.kfnstAttndRec.getItemName());
	}

	@Override
	public List<Integer> getAddedItem() {
		if(this.kfnstAttndRecItems == null || this.kfnstAttndRecItems.isEmpty())  return new ArrayList<Integer>();
		return this.kfnstAttndRecItems.stream().filter(e -> e.getFormulaType().compareTo(new BigDecimal(1)) == 0)
				.map(obj -> (int) obj.getId().getTimeItemId()).collect(Collectors.toList());
	}

	@Override
	public List<Integer> getSubtractedItem() {
		if(this.kfnstAttndRecItems == null || this.kfnstAttndRecItems.isEmpty() ) return new ArrayList<Integer>();
		return this.kfnstAttndRecItems.stream().filter(e -> e.getFormulaType().compareTo(new BigDecimal(2)) == 0)
				.map(obj -> (int) obj.getId().getTimeItemId()).collect(Collectors.toList());
	}

}
