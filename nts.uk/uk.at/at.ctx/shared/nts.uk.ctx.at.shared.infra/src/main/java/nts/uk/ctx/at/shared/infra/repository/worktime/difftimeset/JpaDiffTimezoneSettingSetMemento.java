package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifOverTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifWorkTsPK;

/**
 * The Class JpaDiffTimezoneSettingSetMemento.
 */
public class JpaDiffTimezoneSettingSetMemento implements DiffTimezoneSettingSetMemento {

	/** The entity. */
	private KshmtWtDif entity;

	/** The type. */
	private int type;

	/** The period no. */
	private int periodNo;

	public JpaDiffTimezoneSettingSetMemento(KshmtWtDif entity, int type) {
		this.entity = entity;
		this.type = type;
	}

	@Override
	public void setEmploymentTimezones(List<EmTimeZoneSet> employmentTimezones) {
		// KSHMT_WT_DIF_WORK_TS

		if (this.entity.getLstKshmtWtDifWorkTs() == null) {
			this.entity.setLstKshmtWtDifWorkTs(new ArrayList<>());
		}
		List<KshmtWtDifWorkTs> otherList = this.entity.getLstKshmtWtDifWorkTs().stream()
				.filter(entity -> entity.getKshmtWtDifWorkTsPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());

		// get list old entity
		Map<KshmtWtDifWorkTsPK, KshmtWtDifWorkTs> lstOldEntity = this.entity.getLstKshmtWtDifWorkTs().stream()
				.filter(entity -> entity.getKshmtWtDifWorkTsPK().getAmPmAtr() == this.type)
				.collect(Collectors.toMap(KshmtWtDifWorkTs::getKshmtWtDifWorkTsPK, Function.identity()));

		List<KshmtWtDifWorkTs> newListEntity = new ArrayList<>();

		periodNo = 0;
		employmentTimezones.stream().forEach(domain -> {
			periodNo++;
			KshmtWtDifWorkTsPK pk = new KshmtWtDifWorkTsPK(this.entity.getKshmtWtDifPK().getCid(),
					this.entity.getKshmtWtDifPK().getWorktimeCd(), this.type, periodNo);
			KshmtWtDifWorkTs entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtDifWorkTs();
				entity.setKshmtWtDifWorkTsPK(pk);
			}
			domain.saveToMemento(new JpaEmTimeZoneSetSetMemento(entity));

			// add list
			newListEntity.add(entity);
		});

		newListEntity.addAll(otherList);

		this.entity.setLstKshmtWtDifWorkTs(newListEntity);
	}

	@Override
	public void setOTTimezones(List<DiffTimeOTTimezoneSet> oTTimezones) {
		// KSHMT_WT_DIF_OVER_TS
		if (this.entity.getLstKshmtWtDifOverTs() == null) {
			this.entity.setLstKshmtWtDifOverTs(new ArrayList<>());
		}
		List<KshmtWtDifOverTs> otherList = this.entity.getLstKshmtWtDifOverTs().stream()
				.filter(entity -> entity.getKshmtWtDifOverTsPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());

		// get list old entity
		Map<KshmtWtDifOverTsPK, KshmtWtDifOverTs> lstOldEntity = this.entity.getLstKshmtWtDifOverTs().stream()
				.filter(entity -> entity.getKshmtWtDifOverTsPK().getAmPmAtr() == this.type)
				.collect(Collectors.toMap(KshmtWtDifOverTs::getKshmtWtDifOverTsPK, Function.identity()));

		List<KshmtWtDifOverTs> newListEntity = new ArrayList<>();

		periodNo = 0;
		oTTimezones.forEach(domain -> {
			periodNo++;
			KshmtWtDifOverTsPK pk = new KshmtWtDifOverTsPK(this.entity.getKshmtWtDifPK().getCid(),
					this.entity.getKshmtWtDifPK().getWorktimeCd(), this.type, periodNo);
			KshmtWtDifOverTs entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtDifOverTs();
				entity.setKshmtWtDifOverTsPK(pk);
			}
			domain.saveToMemento(new JpaDiffTimeOTTimezoneSetMemento(entity));

			// add list
			newListEntity.add(entity);
		});

		newListEntity.addAll(otherList);

		this.entity.setLstKshmtWtDifOverTs(newListEntity);

	}

}
