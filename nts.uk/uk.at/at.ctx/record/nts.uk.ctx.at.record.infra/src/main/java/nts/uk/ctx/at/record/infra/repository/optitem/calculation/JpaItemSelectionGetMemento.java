/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.gul.collection.LazyList;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelection;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.ItemSelectionGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SelectedAttendanceItem;

/**
 * The Class JpaItemSelectionGetMemento.
 */
public class JpaItemSelectionGetMemento implements ItemSelectionGetMemento {

	/** The entities. */
	private List<KrcmtCalcItemSelection> entities;

	/** The Constant FIRST_INDEX. */
	public static final int FIRST_INDEX = 0;

	/**
	 * Instantiates a new jpa item selection get memento.
	 *
	 * @param entities the entities
	 */
	public JpaItemSelectionGetMemento(List<KrcmtCalcItemSelection> entities) {
		this.entities = entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionGetMemento#
	 * getMinusSegment()
	 */
	@Override
	public MinusSegment getMinusSegment() {
		if (CollectionUtil.isEmpty(this.entities)) {
			return null;
		}
		return MinusSegment.valueOf(this.entities.get(FIRST_INDEX).getMinusSegment());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionGetMemento#
	 * getListSelectedAttendanceItem()
	 */
	@Override
	public List<SelectedAttendanceItem> getListSelectedAttendanceItem() {
		return LazyList.withMap(() -> this.entities,
				item -> new SelectedAttendanceItem(new JpaSelectedAttendanceItemGetMemento(item)));
	}

}
