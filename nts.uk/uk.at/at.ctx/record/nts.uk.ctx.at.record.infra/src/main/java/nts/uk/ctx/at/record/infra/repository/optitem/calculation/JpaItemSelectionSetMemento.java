/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfItemSelect;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfItemSelectPK;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfPK;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.ItemSelectionSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SelectedAttendanceItem;

/**
 * The Class JpaItemSelectionSetMemento.
 */
@Getter
public class JpaItemSelectionSetMemento implements ItemSelectionSetMemento {

	/** The item selections. */
	private List<KrcmtAnyfItemSelect> itemSelections;

	/** The formula pk. */
	private KrcmtAnyfPK formulaPk;

	/** The minus segment. */
	private int minusSegment;

	/**
	 * Instantiates a new jpa item selection set memento.
	 *
	 * @param pk the pk
	 */
	public JpaItemSelectionSetMemento(KrcmtAnyfPK pk) {
		this.formulaPk = pk;
		this.itemSelections = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionSetMemento#
	 * setMinusSegment(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * MinusSegment)
	 */
	@Override
	public void setMinusSegment(MinusSegment segment) {
		this.minusSegment = segment.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionSetMemento#
	 * setListSelectedAttendanceItem(java.util.List)
	 */
	@Override
	public void setListSelectedAttendanceItem(List<SelectedAttendanceItem> items) {

		items.forEach(item -> {

			KrcmtAnyfItemSelectPK pk = new KrcmtAnyfItemSelectPK(this.formulaPk, item.getAttendanceItemId());
			KrcmtAnyfItemSelect entity = new KrcmtAnyfItemSelect(pk);

			entity.setOperator(item.getOperator().value);
			entity.setMinusSegment(this.minusSegment);

			this.itemSelections.add(entity);
		});

	}

}
