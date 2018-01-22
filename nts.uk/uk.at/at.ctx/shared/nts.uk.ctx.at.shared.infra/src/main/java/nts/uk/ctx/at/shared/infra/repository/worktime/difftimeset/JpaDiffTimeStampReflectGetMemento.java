package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeStampReflectGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;

/**
 * The Class JpaDiffTimeStampReflectGetMemento.
 */
public class JpaDiffTimeStampReflectGetMemento implements DiffTimeStampReflectGetMemento {

	/** The entity. */
	private KshmtDiffTimeWorkSet entity;

	/**
	 * Instantiates a new jpa diff time stamp reflect get memento.
	 *
	 * @param entity the entity
	 */
	public JpaDiffTimeStampReflectGetMemento(KshmtDiffTimeWorkSet entity) {
		this.entity = entity;
	}

	@Override
	public List<StampReflectTimezone> getStampReflectTimezone() {
		return this.entity.getLstKshmtDtStampReflect().stream().map(item -> {
			return new StampReflectTimezone(new JpaDTStampReflectTimezoneGetMemento(item));
		}).collect(Collectors.toList());
	}

	@Override
	public boolean isIsUpdateStartTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUpdStartTime());
	}

}
