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
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifHolTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrHolTsPK;

public class JpaDiffTimeDayOffWorkTimezoneSetMemento implements DiffTimeDayOffWorkTimezoneSetMemento {

	/** The entity. */
	private KshmtWtDif entity;

	public JpaDiffTimeDayOffWorkTimezoneSetMemento(KshmtWtDif entity) {
		this.entity = entity;
	}

	@Override
	public void setRestTimezone(DiffTimeRestTimezone restTimezone) {
		//KSHMT_WT_DIF_BR_HOL_TS
		if (restTimezone == null ||restTimezone.getRestTimezones().isEmpty()) {
			this.entity.setLstKshmtWtDifBrHolTs(new ArrayList<>());
			return;
		}

		List<KshmtWtDifBrHolTs> lstEntity = this.entity.getLstKshmtWtDifBrHolTs();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtWtDifBrHolTsPK, KshmtWtDifBrHolTs> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtWtDifBrHolTs::getKshmtWtDifBrHolTsPK, Function.identity()));

		List<KshmtWtDifBrHolTs> lstReturn = new ArrayList<>();
		// set list entity
		for (int i = 0; i < restTimezone.getRestTimezones().size(); i++) {
			// newPk
			KshmtWtDifBrHolTsPK newPK = new KshmtWtDifBrHolTsPK();
			newPK.setCid(this.entity.getKshmtWtDifPK().getCid());
			newPK.setWorktimeCd(this.entity.getKshmtWtDifPK().getWorktimeCd());
			newPK.setPeriodNo(i + 1);

			// find entity if existed, else new entity
			KshmtWtDifBrHolTs entity = mapEntity.get(newPK);
			if (entity == null) {
				entity = new KshmtWtDifBrHolTs();
				entity.setKshmtWtDifBrHolTsPK(newPK);
			}

			// save to memento
			restTimezone.getRestTimezones().get(i).saveToMemento(new JpaDiffTimeDeductTimezoneGetMemento(entity));

			lstReturn.add(entity);
		}
		;
		this.entity.setLstKshmtWtDifBrHolTs(lstReturn);
	}

	@Override
	public void setWorkTimezones(List<HDWorkTimeSheetSetting> workTimezone) {
		//KSHMT_WT_DIF_HOL_TS
		if (workTimezone == null || workTimezone.isEmpty()) {
			this.entity.setLstKshmtWtDifHolTs(new ArrayList<>());
			return;
		}

		List<KshmtWtDifHolTs> lstEntity = this.entity.getLstKshmtWtDifHolTs();
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}

		// convert map entity
		Map<KshmtWtDifHolTsPK, KshmtWtDifHolTs> mapEntity = lstEntity.stream()
				.collect(Collectors.toMap(KshmtWtDifHolTs::getKshmtWtDifHolTsPK, Function.identity()));

		List<KshmtWtDifHolTs> lstReturn = new ArrayList<>();
		// set list entity
		for (int i = 0; i < workTimezone.size(); i++) {
			// newPk
			KshmtWtDifHolTsPK newPK = new KshmtWtDifHolTsPK();
			newPK.setCid(this.entity.getKshmtWtDifPK().getCid());
			newPK.setWorktimeCd(this.entity.getKshmtWtDifPK().getWorktimeCd());
			newPK.setWorkTimeNo(workTimezone.get(i).getWorkTimeNo());

			// find entity if existed, else new entity
			KshmtWtDifHolTs entity = mapEntity.get(newPK);
			if (entity == null) {
				entity = new KshmtWtDifHolTs();
				entity.setKshmtWtDifHolTsPK(newPK);
			}

			// save to memento
			workTimezone.get(i).saveToMemento(new JpaDayOffTimezoneSettingSetMemento(entity));

			lstReturn.add(entity);
		}
		;
		this.entity.setLstKshmtWtDifHolTs(lstReturn);
	}

}
