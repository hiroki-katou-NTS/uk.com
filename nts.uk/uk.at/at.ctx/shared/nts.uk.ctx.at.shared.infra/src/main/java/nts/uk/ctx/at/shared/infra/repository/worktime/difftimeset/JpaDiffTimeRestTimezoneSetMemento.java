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
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHalfRestTimePK;

public class JpaDiffTimeRestTimezoneSetMemento implements DiffTimeRestTimezoneSetMemento {

	private KshmtWtDif entity;

	private int type;

	private String companyId;

	private String workTimeCode;

	private int periodNo;

	public JpaDiffTimeRestTimezoneSetMemento(KshmtWtDif entity, int type) {
		this.entity = entity;
		this.type = type;
		this.companyId = entity.getKshmtDiffTimeWorkSetPK().getCid();
		this.workTimeCode = entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd();
	}

	@Override
	public void setRestTimezones(List<DiffTimeDeductTimezone> restTimezone) {
		//KSHMT_WT_DIF_BR_WEK_TS
		if (this.entity.getLstKshmtDtHalfRestTime() == null) {
			this.entity.setLstKshmtDtHalfRestTime(new ArrayList<>());
		}
		List<KshmtWtDifBrWekTs> otherList = this.entity.getLstKshmtDtHalfRestTime().stream()
				.filter(entity -> entity.getKshmtDtHalfRestTimePK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());
		// get list old entity
		Map<KshmtDtHalfRestTimePK, KshmtWtDifBrWekTs> lstOldEntity = this.entity.getLstKshmtDtHalfRestTime().stream()
				.filter(entity -> entity.getKshmtDtHalfRestTimePK().getAmPmAtr() == this.type)
				.collect(Collectors.toMap(KshmtWtDifBrWekTs::getKshmtDtHalfRestTimePK, Function.identity()));

		List<KshmtWtDifBrWekTs> newListEntity = new ArrayList<>();

		periodNo = 0;
		restTimezone.forEach(domain -> {
			periodNo++;
			KshmtDtHalfRestTimePK pk = new KshmtDtHalfRestTimePK(this.companyId, this.workTimeCode, this.type,
					periodNo);
			KshmtWtDifBrWekTs entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtDifBrWekTs();
				entity.setKshmtDtHalfRestTimePK(pk);
			}
			domain.saveToMemento(new JpaDiffTimeDeductTimezoneSetMemento(entity));

			// add list
			newListEntity.add(entity);
		});

		newListEntity.addAll(otherList);

		this.entity.setLstKshmtDtHalfRestTime(newListEntity);
	}

}
