package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTsPK;

public class JpaDiffTimeRestTimezoneSetMemento implements DiffTimeRestTimezoneSetMemento {

	private KshmtWtDif entity;

	private int type;

	private String companyId;

	private String workTimeCode;

	private int periodNo;

	public JpaDiffTimeRestTimezoneSetMemento(KshmtWtDif entity, int type) {
		this.entity = entity;
		this.type = type;
		this.companyId = entity.getKshmtWtDifPK().getCid();
		this.workTimeCode = entity.getKshmtWtDifPK().getWorktimeCd();
	}

	@Override
	public void setRestTimezones(List<DiffTimeDeductTimezone> restTimezone) {
		//KSHMT_WT_DIF_BR_WEK_TS
		if (this.entity.getLstKshmtWtDifBrWekTs() == null) {
			this.entity.setLstKshmtWtDifBrWekTs(new ArrayList<>());
		}
		List<KshmtWtDifBrWekTs> otherList = this.entity.getLstKshmtWtDifBrWekTs().stream()
				.filter(entity -> entity.getKshmtWtDifBrWekTsPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());
		// get list old entity
		Map<KshmtWtDifBrWekTsPK, KshmtWtDifBrWekTs> lstOldEntity = this.entity.getLstKshmtWtDifBrWekTs().stream()
				.filter(entity -> entity.getKshmtWtDifBrWekTsPK().getAmPmAtr() == this.type)
				.collect(Collectors.toMap(KshmtWtDifBrWekTs::getKshmtWtDifBrWekTsPK, Function.identity()));

		List<KshmtWtDifBrWekTs> newListEntity = new ArrayList<>();

		periodNo = 0;
		restTimezone.forEach(domain -> {
			periodNo++;
			KshmtWtDifBrWekTsPK pk = new KshmtWtDifBrWekTsPK(this.companyId, this.workTimeCode, this.type,
					periodNo);
			KshmtWtDifBrWekTs entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtDifBrWekTs();
				entity.setKshmtWtDifBrWekTsPK(pk);
			}
			domain.saveToMemento(new JpaDiffTimeDeductTimezoneSetMemento(entity));

			// add list
			newListEntity.add(entity);
		});

		newListEntity.addAll(otherList);

		this.entity.setLstKshmtWtDifBrWekTs(newListEntity);
	}

}
