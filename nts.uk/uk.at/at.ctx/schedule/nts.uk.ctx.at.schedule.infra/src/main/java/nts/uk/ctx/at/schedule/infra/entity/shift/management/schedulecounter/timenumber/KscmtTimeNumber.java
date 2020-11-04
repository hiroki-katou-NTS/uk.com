package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.timenumber;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_WKP_TIME_NUMBER") //TODO invalid name
public class KscmtTimeNumber extends ContractUkJpaEntity implements Serializable {

	@EmbeddedId
	public KscmtTimeNumberPk pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static List<KscmtTimeNumber> toEntity(String companyId, TimesNumberCounterSelection domain) {
		return domain.getSelectedNoList().stream().map(x -> {
			KscmtTimeNumberPk pk = new KscmtTimeNumberPk(companyId, domain.getType().value, x);
			KscmtTimeNumber result = new KscmtTimeNumber(pk);

			result.contractCd = AppContexts.user().contractCode();
			return result;
		}).collect(Collectors.toList());
	}

	public static TimesNumberCounterSelection toDomain(List<KscmtTimeNumber> entities) {
		if (entities.size() > 0){
			List<Integer> selectedNoList =  entities.stream().map(x ->{
				return x.pk.no;
			}).collect(Collectors.toList());
			val type = entities.stream().findFirst();
			return TimesNumberCounterSelection.create(TimesNumberCounterType.of(type.get().pk.type),selectedNoList);
		}
		return null;
	}
}
