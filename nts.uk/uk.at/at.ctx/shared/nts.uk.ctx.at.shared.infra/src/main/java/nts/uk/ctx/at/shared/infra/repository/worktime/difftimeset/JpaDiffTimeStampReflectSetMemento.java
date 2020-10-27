package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeStampReflectSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifStmpRefTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifStmpRefTsPK;

public class JpaDiffTimeStampReflectSetMemento implements DiffTimeStampReflectSetMemento {

	private KshmtWtDif entity;
	
	public JpaDiffTimeStampReflectSetMemento(KshmtWtDif entity) {
		this.entity = entity;
	}

	@Override
	public void setStampReflectTimezone(List<StampReflectTimezone> stampReflectTimezone) {
		// KSHMT_WT_DIF_STMP_REF_TS

		if (this.entity.getLstKshmtWtDifStmpRefTs() == null) {
			this.entity.setLstKshmtWtDifStmpRefTs(new ArrayList<>());
		}
		// get list old entity
		Map<KshmtWtDifStmpRefTsPK, KshmtWtDifStmpRefTs> lstOldEntity = this.entity.getLstKshmtWtDifStmpRefTs().stream()
				.collect(Collectors.toMap(KshmtWtDifStmpRefTs::getKshmtWtDifStmpRefTsPK, Function.identity()));

		List<KshmtWtDifStmpRefTs> newListEntity = new ArrayList<>();

		stampReflectTimezone.forEach(domain -> {
			KshmtWtDifStmpRefTsPK pk = new KshmtWtDifStmpRefTsPK(this.entity.getKshmtWtDifPK().getCid(),
					this.entity.getKshmtWtDifPK().getWorktimeCd(), domain.getWorkNo().v(), domain.getClassification().value);
			KshmtWtDifStmpRefTs entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtDifStmpRefTs();
				entity.setKshmtWtDifStmpRefTsPK(pk);
			}
			domain.saveToMemento(new JpaDTStampReflectTimezoneSetMemento(entity));

			// add list
			newListEntity.add(entity);
		});

		this.entity.setLstKshmtWtDifStmpRefTs(newListEntity);
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		this.entity.setUpdStartTime(BooleanGetAtr.getAtrByBoolean(isUpdateStartTime));
	}

}
