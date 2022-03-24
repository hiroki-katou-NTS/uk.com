package nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KFNMT_ANP_FORM_DEFAULT")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtAnpFormDefault extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    public String companyId;

    @Column(name = "CODE")
    public String code;

    @Override
    protected Object getKey() {
        return companyId;
    }
}
