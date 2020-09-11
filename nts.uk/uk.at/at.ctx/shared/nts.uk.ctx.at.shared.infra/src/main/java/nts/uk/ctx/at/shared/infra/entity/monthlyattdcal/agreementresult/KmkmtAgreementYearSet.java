package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.agreementresult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting.AgreementYearSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "KMKMT_AGREEMENT_YEAR_SET")
@AllArgsConstructor
public class KmkmtAgreementYearSet extends UkJpaEntity implements Serializable {

    @EmbeddedId
    public KmkmtAgreementYearSetPK pk;

    /** エラー時間 */
    @Column(name = "ERROR_YEARLY")
    public int errorOneYear;

    /** アラーム時間 */
    @Column(name = "ALARM_YEARLY")
    public int alarmOneYear;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static KmkmtAgreementYearSet fromDomain(AgreementYearSetting domain) {
        KmkmtAgreementYearSetPK setPK = new KmkmtAgreementYearSetPK(domain.getEmployeeId(), domain.getYear().v());
        return new KmkmtAgreementYearSet(setPK,domain.getErrorTimeInYear().getErrorTime().v(),domain.getErrorTimeInYear().getAlarmTime().v());
    }

    public static AgreementYearSetting toDomain(KmkmtAgreementYearSet entity) {
        ErrorTimeInYear oneMonthTime = new ErrorTimeInYear(new AgreementOneMonthTime(entity.errorOneYear),new AgreementOneMonthTime(entity.alarmOneYear));
        return new AgreementYearSetting(entity.pk.sId,new Year(entity.pk.year),oneMonthTime);
    }
}
