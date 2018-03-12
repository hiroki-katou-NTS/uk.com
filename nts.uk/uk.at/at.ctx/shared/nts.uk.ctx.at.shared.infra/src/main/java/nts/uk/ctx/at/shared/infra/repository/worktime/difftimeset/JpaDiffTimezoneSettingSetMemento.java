package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtOtTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtOtTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtWorkTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtWorkTimeSetPK;

/**
 * The Class JpaDiffTimezoneSettingSetMemento.
 */
public class JpaDiffTimezoneSettingSetMemento implements DiffTimezoneSettingSetMemento {

	/** The entity. */
	private KshmtDiffTimeWorkSet entity;

	/** The type. */
	private int type;

	/** The period no. */
	private int periodNo;

	public JpaDiffTimezoneSettingSetMemento(KshmtDiffTimeWorkSet entity, int type) {
		this.entity = entity;
		this.type = type;
	}

	@Override
	public void setEmploymentTimezones(List<EmTimeZoneSet> employmentTimezones) {
		// KSHMT_DT_WORK_TIME_SET

		if (this.entity.getLstKshmtDtWorkTimeSet() == null) {
			this.entity.setLstKshmtDtWorkTimeSet(new ArrayList<>());
		}
		List<KshmtDtWorkTimeSet> otherList = this.entity.getLstKshmtDtWorkTimeSet().stream()
				.filter(entity -> entity.getKshmtDtWorkTimeSetPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());

		// get list old entity
		Map<KshmtDtWorkTimeSetPK, KshmtDtWorkTimeSet> lstOldEntity = this.entity.getLstKshmtDtWorkTimeSet().stream()
				.filter(entity -> entity.getKshmtDtWorkTimeSetPK().getAmPmAtr() == this.type)
				.collect(Collectors.toMap(KshmtDtWorkTimeSet::getKshmtDtWorkTimeSetPK, Function.identity()));

		List<KshmtDtWorkTimeSet> newListEntity = new ArrayList<>();

		periodNo = 0;
		employmentTimezones.stream().forEach(domain -> {
			periodNo++;
			KshmtDtWorkTimeSetPK pk = new KshmtDtWorkTimeSetPK(this.entity.getKshmtDiffTimeWorkSetPK().getCid(),
					this.entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd(), this.type, periodNo);
			KshmtDtWorkTimeSet entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtDtWorkTimeSet();
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
		// KSHMT_DT_OT_TIME_SET
		if (this.entity.getLstKshmtDtOtTimeSet() == null) {
			this.entity.setLstKshmtDtOtTimeSet(new ArrayList<>());
		}
		List<KshmtDtOtTimeSet> otherList = this.entity.getLstKshmtDtOtTimeSet().stream()
				.filter(entity -> entity.getKshmtDtOtTimeSetPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());

		// get list old entity
		Map<KshmtDtOtTimeSetPK, KshmtDtOtTimeSet> lstOldEntity = this.entity.getLstKshmtDtOtTimeSet().stream()
				.filter(entity -> entity.getKshmtDtOtTimeSetPK().getAmPmAtr() == this.type)
				.collect(Collectors.toMap(KshmtDtOtTimeSet::getKshmtDtOtTimeSetPK, Function.identity()));

		List<KshmtDtOtTimeSet> newListEntity = new ArrayList<>();

		periodNo = 0;
		oTTimezones.forEach(domain -> {
			periodNo++;
			KshmtDtOtTimeSetPK pk = new KshmtDtOtTimeSetPK(this.entity.getKshmtDiffTimeWorkSetPK().getCid(),
					this.entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd(), this.type, periodNo);
			KshmtDtOtTimeSet entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtDtOtTimeSet();
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
