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
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtOtTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtWorkTimeSetPK;

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

		if (this.entity.getLstKshmtDtWorkTimeSet() == null) {
			this.entity.setLstKshmtDtWorkTimeSet(new ArrayList<>());
		}
		List<KshmtWtDifWorkTs> otherList = this.entity.getLstKshmtDtWorkTimeSet().stream()
				.filter(entity -> entity.getKshmtDtWorkTimeSetPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());

		// get list old entity
		Map<KshmtDtWorkTimeSetPK, KshmtWtDifWorkTs> lstOldEntity = this.entity.getLstKshmtDtWorkTimeSet().stream()
				.filter(entity -> entity.getKshmtDtWorkTimeSetPK().getAmPmAtr() == this.type)
				.collect(Collectors.toMap(KshmtWtDifWorkTs::getKshmtDtWorkTimeSetPK, Function.identity()));

		List<KshmtWtDifWorkTs> newListEntity = new ArrayList<>();

		periodNo = 0;
		employmentTimezones.stream().forEach(domain -> {
			periodNo++;
			KshmtDtWorkTimeSetPK pk = new KshmtDtWorkTimeSetPK(this.entity.getKshmtDiffTimeWorkSetPK().getCid(),
					this.entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd(), this.type, periodNo);
			KshmtWtDifWorkTs entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtDifWorkTs();
				entity.setKshmtDtWorkTimeSetPK(pk);
			}
			domain.saveToMemento(new JpaEmTimeZoneSetSetMemento(entity));

			// add list
			newListEntity.add(entity);
		});

		newListEntity.addAll(otherList);

		this.entity.setLstKshmtDtWorkTimeSet(newListEntity);
	}

	@Override
	public void setOTTimezones(List<DiffTimeOTTimezoneSet> oTTimezones) {
		// KSHMT_WT_DIF_OVER_TS
		if (this.entity.getLstKshmtDtOtTimeSet() == null) {
			this.entity.setLstKshmtDtOtTimeSet(new ArrayList<>());
		}
		List<KshmtWtDifOverTs> otherList = this.entity.getLstKshmtDtOtTimeSet().stream()
				.filter(entity -> entity.getKshmtDtOtTimeSetPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());

		// get list old entity
		Map<KshmtDtOtTimeSetPK, KshmtWtDifOverTs> lstOldEntity = this.entity.getLstKshmtDtOtTimeSet().stream()
				.filter(entity -> entity.getKshmtDtOtTimeSetPK().getAmPmAtr() == this.type)
				.collect(Collectors.toMap(KshmtWtDifOverTs::getKshmtDtOtTimeSetPK, Function.identity()));

		List<KshmtWtDifOverTs> newListEntity = new ArrayList<>();

		periodNo = 0;
		oTTimezones.forEach(domain -> {
			periodNo++;
			KshmtDtOtTimeSetPK pk = new KshmtDtOtTimeSetPK(this.entity.getKshmtDiffTimeWorkSetPK().getCid(),
					this.entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd(), this.type, periodNo);
			KshmtWtDifOverTs entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtDifOverTs();
				entity.setKshmtDtOtTimeSetPK(pk);
			}
			domain.saveToMemento(new JpaDiffTimeOTTimezoneSetMemento(entity));

			// add list
			newListEntity.add(entity);
		});

		newListEntity.addAll(otherList);

		this.entity.setLstKshmtDtOtTimeSet(newListEntity);

	}

}
