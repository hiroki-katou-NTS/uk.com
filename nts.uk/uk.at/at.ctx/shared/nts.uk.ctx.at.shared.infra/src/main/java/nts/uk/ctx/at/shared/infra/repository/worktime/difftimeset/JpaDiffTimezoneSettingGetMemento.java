package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDiffTimezoneSettingGetMemento.
 */
public class JpaDiffTimezoneSettingGetMemento implements DiffTimezoneSettingGetMemento {

	private KshmtWtDif entity;

	private Integer type;

	public JpaDiffTimezoneSettingGetMemento(KshmtWtDif entity, Integer type) {
		this.entity = entity;
		this.type = type;
	}

	@Override
	public List<EmTimeZoneSet> getEmploymentTimezones() {
		return this.entity.getLstKshmtWtDifWorkTs().stream()
				.filter(item -> item.getKshmtWtDifWorkTsPK().getAmPmAtr() == this.type).map(item -> {
					return new EmTimeZoneSet(new EmTimeFrameNo(item.getKshmtWtDifWorkTsPK().getTimeFrameNo()),
							new TimeZoneRounding(new TimeWithDayAttr(item.getTimeStr()),
									new TimeWithDayAttr(item.getTimeEnd()), new TimeRoundingSetting(
											Unit.valueOf(item.getUnit()), Rounding.valueOf(item.getRounding()))));
				}).collect(Collectors.toList());
	}

	@Override
	public List<DiffTimeOTTimezoneSet> getOTTimezones() {
		return this.entity.getLstKshmtWtDifOverTs().stream()
				.filter(item -> item.getKshmtWtDifOverTsPK().getAmPmAtr() == this.type).map(item -> {
					return new DiffTimeOTTimezoneSet(new JpaDiffTimeOTTimezoneGetMemento(item));
				}).collect(Collectors.toList());
	}

}
