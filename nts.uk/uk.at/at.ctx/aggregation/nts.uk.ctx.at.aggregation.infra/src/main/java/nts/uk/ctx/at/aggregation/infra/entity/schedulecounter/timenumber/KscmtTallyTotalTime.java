package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.timenumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_TALLY_TOTAL_TIMES")
public class KscmtTallyTotalTime extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = -8599571993669130360L;

	@EmbeddedId
	public KscmtTallyTotalTimePk pk;

	@Column(name = "DISPORDER")
    public int dispOrder;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static List<KscmtTallyTotalTime> toEntity(String companyId, TimesNumberCounterSelection domain) {
		
		List<KscmtTallyTotalTime> results = new ArrayList<KscmtTallyTotalTime>();
		
		for (int i = 0; i < domain.getSelectedNoList().size(); i++) {
			KscmtTallyTotalTimePk pk = new KscmtTallyTotalTimePk(companyId, domain.getType().value, domain.getSelectedNoList().get(i));
			KscmtTallyTotalTime entity = new KscmtTallyTotalTime(pk, i + 1);		
			entity.contractCd = AppContexts.user().contractCode();
			results.add(entity);
		}
		return results;
	}

	public static TimesNumberCounterSelection toDomain(List<KscmtTallyTotalTime> entities) {
		if (entities.size() > 0){
			List<Integer> selectedNoList =  entities.stream().map(x ->{
				return x.pk.timeNo;
			}).collect(Collectors.toList());
			val type = entities.stream().findFirst();
			return TimesNumberCounterSelection.create(TimesNumberCounterType.of(type.get().pk.countType),selectedNoList);
		}
		return null;
	}
}
