package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;

public class DiffTimeDayOffWorkTimezoneGetMemento
		implements nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezoneGetMemento {

	private KshmtWtDif entity;

	public DiffTimeDayOffWorkTimezoneGetMemento(KshmtWtDif entity) {
		this.entity = entity;
	}

	@Override
	public DiffTimeRestTimezone getRestTimezone() {
		return new DiffTimeRestTimezone(new JpaDiffTimeRestTimezoneGetMemento(this.entity));
	}

	@Override
	public List<HDWorkTimeSheetSetting> getWorkTimezones() {

		if (this.entity.getLstKshmtWtDifHolTs() == null) {
			return null;
		}
		return this.entity.getLstKshmtWtDifHolTs().stream().map(item -> {
			return new HDWorkTimeSheetSetting(new JpaDayOffTimezoneSettingGetMemento(item));
		}).collect(Collectors.toList());
	}

}
