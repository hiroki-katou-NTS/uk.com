package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.agreementresult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KmkmtAgreementYearSetPK {

    @Column(name = "SID")
    public String sId;

    /** 年月 */
    @Column(name = "Y_K")
    public int year;
}
