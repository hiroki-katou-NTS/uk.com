/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.record.dom.optitem.calculation.SelectedAttendanceItem;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelection;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelectionPK;

/**
 * The Class JpaItemSelectionGetMemento.
 */
public class JpaItemSelectionGetMemento implements ItemSelectionGetMemento{
	
	/** The entity. */
	private List<KrcmtCalcItemSelection> entitys;
	
	/** The Constant FIRST_INDEX. */
	public static final int FIRST_INDEX = 0;

	/**
	 * Instantiates a new jpa item selection get memento.
	 *
	 * @param entity the entity
	 */
	public JpaItemSelectionGetMemento(List<KrcmtCalcItemSelection> entitys) {
		entitys.forEach(entity->{
			if(entity.getKrcmtCalcItemSelectionPK() == null){
				entity.setKrcmtCalcItemSelectionPK(new KrcmtCalcItemSelectionPK());
			}
		});
		this.entitys = entitys;
	}

	/**
	 * Gets the minus segment.
	 *
	 * @return the minus segment
	 */
	@Override
	public MinusSegment getMinusSegment() {
		if(CollectionUtil.isEmpty(this.entitys)){
			return null;
		}
		return MinusSegment.valueOf(this.entitys.get(FIRST_INDEX).getMinusSegment());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelectionGetMemento#getListSelectedAttendanceItem()
	 */
	@Override
	public List<SelectedAttendanceItem> getListSelectedAttendanceItem() {
		return this.entitys.stream()
				.map(entity -> new SelectedAttendanceItem(
						new JpaSelectedAttendanceItemGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
