package nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "KFNMT_ANP_FORM")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtAnpForm extends ContractUkJpaEntity {
    @EmbeddedId
    public KfnmtAnpFormPk pk;

    @Column(name = "NAME")
    public String name;

    @OneToMany(mappedBy = "anyPeriodForm", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<KfnmtAnpFormSheet> sheets;

    @Override
    protected Object getKey() {
        return pk;
    }
}
