package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkpcounterlaborcostandtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.LaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.LaborCostAndTimeType;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_TALLY_BYWKP_LABOR_COST")
public class KscmtWkpLaborCostAndTime extends ContractUkJpaEntity implements Serializable {

	@EmbeddedId
	public KscmtWkpLaborCostAndTimePk pk;

	@Column(name = "USE_ATR")
	public int useClassification;

	@Column(name = "TIME_FOR_LABOR_COST")
	public int time;

	@Column(name = "LABOR_COST")
	public int laborCost;

	@Column(name = "BUDGET")
	public Integer budget;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static List<KscmtWkpLaborCostAndTime> toEntity(String companyId, WorkplaceCounterLaborCostAndTime domain) {
		return domain.getLaborCostAndTimeList().entrySet().stream().map(x -> {
			KscmtWkpLaborCostAndTimePk pk = new KscmtWkpLaborCostAndTimePk(companyId, x.getKey().value);
			KscmtWkpLaborCostAndTime result = new KscmtWkpLaborCostAndTime(
				pk,
				x.getValue().getUseClassification().value,
				x.getValue().getTime().value,
				x.getValue().getLaborCost().value,
				x.getValue().getBudget().isPresent() ? x.getValue().getBudget().get().value : null
			);

			result.contractCd = AppContexts.user().contractCode();
			return result;
		}).collect(Collectors.toList());
	}

	public static WorkplaceCounterLaborCostAndTime toDomain(List<KscmtWkpLaborCostAndTime> entities) {

		Map<LaborCostAndTimeType, LaborCostAndTime> laborCostAndTimeList = new HashMap<>();
		entities.forEach(x -> laborCostAndTimeList.put(
			EnumAdaptor.valueOf(x.pk.costType, LaborCostAndTimeType.class),
			new LaborCostAndTime(
				NotUseAtr.valueOf(x.useClassification),
				NotUseAtr.valueOf(x.time),
				NotUseAtr.valueOf(x.laborCost),
				x.budget == null ? Optional.empty() : Optional.of(NotUseAtr.valueOf(x.budget)))
		));

		return WorkplaceCounterLaborCostAndTime.create(laborCostAndTimeList);
	}
}
