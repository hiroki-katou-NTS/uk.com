package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTs;

public class JpaDiffTimeHalfDayGetMemento implements DiffTimeHalfDayGetMemento {

	private KshmtWtDif entity;

	private Integer type;

	public JpaDiffTimeHalfDayGetMemento(KshmtWtDif entity, AmPmAtr type) {
		this.entity = entity;
		this.type = type.value;
	}

	@Override
	public DiffTimeRestTimezone getRestTimezone() {
		List<KshmtWtDifBrWekTs> lstKshmtWtDifBrWekTs = this.entity.getLstKshmtWtDifBrWekTs().stream()
				.filter(item -> item.getKshmtWtDifBrWekTsPK().getAmPmAtr() == this.type)
				.collect(Collectors.toList());
		return new DiffTimeRestTimezone(new JpaHalfDTRestTimezoneGetMemento(lstKshmtWtDifBrWekTs));
	}

	@Override
	public DiffTimezoneSetting getWorkTimezone() {
		return new DiffTimezoneSetting( new JpaDiffTimezoneSettingGetMemento(this.entity,this.type));
	}

	@Override
	public AmPmAtr getAmPmAtr() {
		return AmPmAtr.valueOf(this.type);
	}

}
