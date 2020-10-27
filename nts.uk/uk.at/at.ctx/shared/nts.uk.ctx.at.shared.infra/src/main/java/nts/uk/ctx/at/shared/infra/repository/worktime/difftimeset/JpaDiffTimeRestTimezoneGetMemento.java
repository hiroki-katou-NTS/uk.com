package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;

public class JpaDiffTimeRestTimezoneGetMemento implements DiffTimeRestTimezoneGetMemento {

	private KshmtWtDif entity;

	public JpaDiffTimeRestTimezoneGetMemento(KshmtWtDif entity) {
		this.entity = entity;
	}

	@Override
	public List<DiffTimeDeductTimezone> getRestTimezones() {
		return this.entity.getLstKshmtWtDifBrHolTs().stream().map(item -> {
			return new DiffTimeDeductTimezone(new JpaODDiffTimeDeductTimezoneGetMemento(item));
		}).collect(Collectors.toList());
	}

}
