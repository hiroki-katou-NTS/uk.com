package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.util.List;

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
	 * @param kfnstAttndRec the kfnst attnd rec
	 * @param kfnstAttndRecItem the kfnst attnd rec item
	 */
	public JpaCalculateAttendanceRecordGetMemento(KfnstAttndRec kfnstAttndRec, List<KfnstAttndRecItem> kfnstAttndRecItems) {
		super();
		this.kfnstAttndRec = kfnstAttndRec;
		this.kfnstAttndRecItems = kfnstAttndRecItems;
	}

	@Override
	public CalculateItemAttributes getAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemName getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getAddedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getSubtractedItem() {
		// TODO Auto-generated method stub
		return null;
	}

}
