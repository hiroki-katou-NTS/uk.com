package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTs;

public class JpaHalfDTRestTimezoneGetMemento implements DiffTimeRestTimezoneGetMemento {

	private List<KshmtWtDifBrWekTs> lstEntity;

	public JpaHalfDTRestTimezoneGetMemento(List<KshmtWtDifBrWekTs> lstKshmtWtDifBrWekTs) {
		this.lstEntity = lstKshmtWtDifBrWekTs;
	}

	@Override
	public List<DiffTimeDeductTimezone> getRestTimezones() {
		return this.lstEntity.stream().map(item -> {
			return new DiffTimeDeductTimezone( new JpaHalfDiffTimeDeductTimezoneGetMemento(item));
		}).collect(Collectors.toList());
	}

}
