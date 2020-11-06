package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.workplacecounter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterCategory;
import nts.uk.shr.com.context.AppContexts;
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
@Table(name = "KSCMT_TALLY_BYWKP")
public class KscmtTallyByWkp extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KscmtTallyByWkpPk pk;

    @Column(name = "USE_ATR")
    public int useAtr;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static List<KscmtTallyByWkp> toEntity(String companyId, WorkplaceCounter domain, List<EnumConstant> listEnum) {

        return listEnum.stream().map(x -> {
            KscmtTallyByWkpPk pk = new KscmtTallyByWkpPk(companyId, x.getValue());
            KscmtTallyByWkp data = new KscmtTallyByWkp(
                pk,
                domain.isUsed(WorkplaceCounterCategory.of(x.getValue())) ? 1 : 0
            );
            data.contractCd = AppContexts.user().contractCode();
            return data;
        }).collect(Collectors.toList());

    }

    public static WorkplaceCounter toDomain(List<KscmtTallyByWkp> entities) {
        //TODO how to map entity to domain with category used ?
        List<WorkplaceCounterCategory> useCategories = entities.stream().filter(i -> i.useAtr == 1).map(x -> {
            return EnumAdaptor.valueOf(x.pk.category, WorkplaceCounterCategory.class);
        }).collect(Collectors.toList());

        return WorkplaceCounter.create(useCategories);
    }

}
