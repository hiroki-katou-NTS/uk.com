package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.List;

import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateItemAttributes;
import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaCalculateAttendanceRecordSetMemento.
 *
 * @author tuannt-nws
 */
public class JpaCalculateAttendanceRecordSetMemento implements CalculateAttendanceRecordSetMemento {
	
	/** The kfnmt rpt wk atd outframe. */
	private KfnmtRptWkAtdOutframe kfnmtRptWkAtdOutframe;
	
	/** The kfnst attnd rec items. */
	private List<KfnmtRptWkAtdOutatd> kfnmtRptWkAtdOutatd;
	
	/** The add formula type. */
	private final int ADD_FORMULA_TYPE = 1;
	
	/** The subtract formualt type. */
	private final int SUBTRACT_FORMULA_TYPE = 2;

	/**
	 * Instantiates a new jpa calculate attendance record set memento.
	 *
	 * @param kfnmtRptWkAtdOutframe            the kfnst attnd rec
	 */
	public JpaCalculateAttendanceRecordSetMemento(KfnmtRptWkAtdOutframe kfnmtRptWkAtdOutframe, List<KfnmtRptWkAtdOutatd> kfnmtRptWkAtdOutatd ) {
		super();
		this.kfnmtRptWkAtdOutframe = kfnmtRptWkAtdOutframe;
		this.kfnmtRptWkAtdOutatd = kfnmtRptWkAtdOutatd;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento#setAttribute(nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateItemAttributes)
	 */
	@Override
	public void setAttribute(CalculateItemAttributes attribute) {
		this.kfnmtRptWkAtdOutframe.setAttribute(new BigDecimal(attribute.value));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento#setName(nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName)
	 */
	@Override
	public void setName(ItemName name) {
		this.kfnmtRptWkAtdOutframe.setItemName(name.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento#setAddedItem(java.util.List)
	 */
	@Override
	public void setAddedItem(List<Integer> idListAdd) {
		// remove old value
		this.kfnmtRptWkAtdOutatd.forEach(e -> {
			if (e.getFormulaType() == new BigDecimal(ADD_FORMULA_TYPE)) {
				this.kfnmtRptWkAtdOutatd.remove(e);
			}
		});
		// add new value
		idListAdd.forEach(e -> {
			UID uid = new UID();
			KfnmtRptWkAtdOutatd item = new KfnmtRptWkAtdOutatd();
			item.setRecordItemId(uid.toString());
			item.setContractCd(AppContexts.user().contractCode());
			item.setCid(this.kfnmtRptWkAtdOutframe.getCid());
			item.setLayoutId(this.kfnmtRptWkAtdOutframe.getId().getLayoutId());
			item.setColumnIndex(this.kfnmtRptWkAtdOutframe.getId().getColumnIndex());
			item.setPosition(this.kfnmtRptWkAtdOutframe.getId().getPosition());
			item.setOutputAtr(this.kfnmtRptWkAtdOutframe.getId().getOutputAtr());
			item.setTimeItemId(e);
			item.setFormulaType(new BigDecimal(ADD_FORMULA_TYPE));
			
			this.kfnmtRptWkAtdOutatd.add(item);
		});
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordSetMemento#setSubtractedItem(java.util.List)
	 */
	@Override
	public void setSubtractedItem(List<Integer> idListSubtract) {
		// remove old value
		this.kfnmtRptWkAtdOutatd.forEach(e -> {
			if (e.getFormulaType() == new BigDecimal(SUBTRACT_FORMULA_TYPE)) {
				this.kfnmtRptWkAtdOutatd.remove(e);
			}
		});
		// add new value
		idListSubtract.forEach(e -> {
			
			UID uid = new UID();
			KfnmtRptWkAtdOutatd item = new KfnmtRptWkAtdOutatd();
			item.setRecordItemId(uid.toString());
			item.setContractCd(AppContexts.user().contractCode());
			item.setCid(this.kfnmtRptWkAtdOutframe.getCid());
			item.setLayoutId(this.kfnmtRptWkAtdOutframe.getId().getLayoutId());
			item.setColumnIndex(this.kfnmtRptWkAtdOutframe.getId().getColumnIndex());
			item.setPosition(this.kfnmtRptWkAtdOutframe.getId().getPosition());
			item.setOutputAtr(this.kfnmtRptWkAtdOutframe.getId().getOutputAtr());
			item.setTimeItemId(e);
			item.setFormulaType(new BigDecimal(ADD_FORMULA_TYPE));
			this.kfnmtRptWkAtdOutatd.add(item);
		});
	}

}
