package nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KfnmtAnpFormPk {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "CODE")
    public String code;
}
