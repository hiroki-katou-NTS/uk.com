package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.personalcounter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounterCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_TALLY_BYPERSON")
public class KscmtTallyByPerson extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KscmtTallyByPersonPk pk;

    @Column(name = "USE_ATR")
    public int useAtr;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static List<KscmtTallyByPerson> toEntity(String companyId, PersonalCounter domain, List<EnumConstant> listEnum) {

        return listEnum.stream().map(x -> {
            KscmtTallyByPersonPk pk = new KscmtTallyByPersonPk(companyId, x.getValue());
            return new KscmtTallyByPerson(
                pk,
                domain.isUsed(PersonalCounterCategory.of(x.getValue())) ? 1 : 0
            );
        }).collect(Collectors.toList());
    }

    public static PersonalCounter toDomain(List<KscmtTallyByPerson> entities) {
        //TODO how to map entity to domain with category used ?
        List<PersonalCounterCategory> useCategories = entities.stream().filter(i -> i.useAtr == 1).map(x -> {
            return EnumAdaptor.valueOf(x.pk.category, PersonalCounterCategory.class);
        }).collect(Collectors.toList());

        return PersonalCounter.create(useCategories);
    }
}
