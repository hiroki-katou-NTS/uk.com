package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;

public class DiffTimeDayOffWorkTimezoneGetMemento
		implements nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezoneGetMemento {

	private KshmtDiffTimeWorkSet entity;

	public DiffTimeDayOffWorkTimezoneGetMemento(KshmtDiffTimeWorkSet entity) {
		this.entity = entity;
	}

	@Override
	public DiffTimeRestTimezone getRestTimezone() {
		return new DiffTimeRestTimezone(new JpaDiffTimeRestTimezoneGetMemento(this.entity));
	}

	@Override
	public List<HDWorkTimeSheetSetting> getWorkTimezones() {

		if (this.entity.getLstKshmtDiffTimeHolSet() == null) {
			return null;
		}
		return this.entity.getLstKshmtDiffTimeHolSet().stream().map(item -> {
			return new HDWorkTimeSheetSetting(new JpaDayOffTimezoneSettingGetMemento(item));
		}).collect(Collectors.toList());
	}

}
