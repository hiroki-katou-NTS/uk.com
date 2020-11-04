package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.personalcounter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounterCategory;
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
@Table(name = "KSCMT_PERSONAL_COUNTER") //TODO invalid name
public class KscmtPersonalCounter extends ContractUkJpaEntity implements Serializable {

	@EmbeddedId
	public KscmtPersonalCounterPk pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static List<KscmtPersonalCounter> toEntity(String companyId, PersonalCounter domain) {
		return domain.getUseCategories().stream().map(x -> {
			KscmtPersonalCounterPk pk = new KscmtPersonalCounterPk(companyId,x.value);
			KscmtPersonalCounter result = new KscmtPersonalCounter(pk);

			result.contractCd = AppContexts.user().contractCode();
			return result;
		}).collect(Collectors.toList());
	}

	public static PersonalCounter toDomain(List<KscmtPersonalCounter> entities) {
		List<PersonalCounterCategory> useCategories =  entities.stream().map(x ->{
			return EnumAdaptor.valueOf(x.pk.useCategories, PersonalCounterCategory.class);
		}).collect(Collectors.toList());

		return PersonalCounter.create(useCategories);
	}
}
