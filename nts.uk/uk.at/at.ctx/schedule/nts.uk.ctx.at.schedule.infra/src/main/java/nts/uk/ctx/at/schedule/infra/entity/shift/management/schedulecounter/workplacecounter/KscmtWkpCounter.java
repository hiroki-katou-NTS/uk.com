package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.workplacecounter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterCategory;
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
@Table(name = "KSCMT_WKP_COUNTER") //TODO invalid name
public class KscmtWkpCounter extends ContractUkJpaEntity implements Serializable {

	@EmbeddedId
	public KscmtWkpCounterPk pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static List<KscmtWkpCounter> toEntity(String companyId,WorkplaceCounter domain) {
		return domain.getUseCategories().stream().map(x -> {
			KscmtWkpCounterPk pk = new KscmtWkpCounterPk(companyId,x.value);
            KscmtWkpCounter result =  new KscmtWkpCounter(pk);
            result.contractCd = AppContexts.user().contractCode();
            return result;
		}).collect(Collectors.toList());
	}

	public static WorkplaceCounter toDomain(List<KscmtWkpCounter> entities) {
		List<WorkplaceCounterCategory> useCategories =  entities.stream().map(x ->{
			return EnumAdaptor.valueOf(x.pk.useCategories, WorkplaceCounterCategory.class);
		}).collect(Collectors.toList());

		return WorkplaceCounter.create(useCategories);
	}

}
