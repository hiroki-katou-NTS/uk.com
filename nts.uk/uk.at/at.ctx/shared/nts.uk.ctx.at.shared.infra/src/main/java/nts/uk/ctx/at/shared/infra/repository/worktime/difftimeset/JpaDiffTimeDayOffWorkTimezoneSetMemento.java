package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeHolSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeHolSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHolRestTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHolRestTimePK;

public class JpaDiffTimeDayOffWorkTimezoneSetMemento implements DiffTimeDayOffWorkTimezoneSetMemento {

	/** The entity. */
	private KshmtDiffTimeWorkSet entity;

	public JpaDiffTimeDayOffWorkTimezoneSetMemento(KshmtDiffTimeWorkSet entity) {
		this.entity = entity;
	}

	@Override
	public void setRestTimezone(DiffTimeRestTimezone restTimezone) {
		//KSHMT_DT_HOL_REST_TIME
		if (restTimezone == null ||restTimezone.getRestTimezones().isEmpty()) {
			this.entity.setLstKshmtDtHolRestTime(new ArrayList<>());
			return;
		}

		List<KshmtDtHolRestTime> lstEntity = this.entity.getLstKshmtDtHolRestTime();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtDtHolRestTimePK, KshmtDtHolRestTime> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtDtHolRestTime::getKshmtDtHolRestTimePK, Function.identity()));

		List<KshmtDtHolRestTime> lstReturn = new ArrayList<>();
		// set list entity
		for (int i = 0; i < restTimezone.getRestTimezones().size(); i++) {
			// newPk
			KshmtDtHolRestTimePK newPK = new KshmtDtHolRestTimePK();
			newPK.setCid(this.entity.getKshmtDiffTimeWorkSetPK().getCid());
			newPK.setWorktimeCd(this.entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd());
			newPK.setPeriodNo(i + 1);

			// find entity if existed, else new entity
			KshmtDtHolRestTime entity = mapEntity.get(newPK);
			if (entity == null) {
				entity = new KshmtDtHolRestTime();
				entity.setKshmtDtHolRestTimePK(newPK);
			}

			// save to memento
			restTimezone.getRestTimezones().get(i).saveToMemento(new JpaDiffTimeDeductTimezoneGetMemento(entity));

			lstReturn.add(entity);
		}
		;
		this.entity.setLstKshmtDtHolRestTime(lstReturn);
	}

	@Override
	public void setWorkTimezones(List<HDWorkTimeSheetSetting> workTimezone) {
		//KSHMT_DIFF_TIME_HOL_SET
		if (workTimezone == null || workTimezone.isEmpty()) {
			this.entity.setLstKshmtDiffTimeHolSet(new ArrayList<>());
			return;
		}

		List<KshmtDiffTimeHolSet> lstEntity = this.entity.getLstKshmtDiffTimeHolSet();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtDiffTimeHolSetPK, KshmtDiffTimeHolSet> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtDiffTimeHolSet::getKshmtDiffTimeHolSetPK, Function.identity()));

		List<KshmtDiffTimeHolSet> lstReturn = new ArrayList<>();
		// set list entity
		for (int i = 0; i < workTimezone.size(); i++) {
			// newPk
			KshmtDiffTimeHolSetPK newPK = new KshmtDiffTimeHolSetPK();
			newPK.setCid(this.entity.getKshmtDiffTimeWorkSetPK().getCid());
			newPK.setWorktimeCd(this.entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd());
			newPK.setWorkTimeNo(workTimezone.get(i).getWorkTimeNo());

			// find entity if existed, else new entity
			KshmtDiffTimeHolSet entity = mapEntity.get(newPK);
			if (entity == null) {
				entity = new KshmtDiffTimeHolSet();
				entity.setKshmtDiffTimeHolSetPK(newPK);
			}

			// save to memento
			workTimezone.get(i).saveToMemento(new JpaDayOffTimezoneSettingSetMemento(entity));

			lstReturn.add(entity);
		}
		;
		this.entity.setLstKshmtDiffTimeHolSet(lstReturn);
	}

}
