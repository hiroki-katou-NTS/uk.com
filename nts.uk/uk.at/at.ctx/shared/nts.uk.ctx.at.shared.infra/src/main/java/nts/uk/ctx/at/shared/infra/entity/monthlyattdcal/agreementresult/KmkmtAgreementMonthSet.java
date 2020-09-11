package nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.agreementresult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting.AgreementMonthSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import nts.arc.time.YearMonth;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(name = "KMKMT_AGREEMENT_MONTH_SET")
@AllArgsConstructor
public class KmkmtAgreementMonthSet extends UkJpaEntity implements Serializable {

    @EmbeddedId
    public KmkmtAgreementMonthSetPK pk;

    /** エラー時間 */
    @Column(name = "ERROR_ONE_MONTH")
    public int errorOneMonth;

    /** アラーム時間 */
    @Column(name = "ERROR_ONE_MONTH")
    public int alarmOneMonth;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static KmkmtAgreementMonthSet fromDomain(AgreementMonthSetting domain) {
        KmkmtAgreementMonthSetPK setPK = new KmkmtAgreementMonthSetPK(domain.getEmployeeId(), domain.getYearMonth().v());
        return new KmkmtAgreementMonthSet(setPK,domain.getOneMonthTime().getErrorTime().v(),domain.getOneMonthTime().getAlarmTime().v());
    }

    public static AgreementMonthSetting toDomain(KmkmtAgreementMonthSet entity) {
        ErrorTimeInMonth oneMonthTime = new ErrorTimeInMonth(new AgreementOneMonthTime(entity.errorOneMonth),new AgreementOneMonthTime(entity.alarmOneMonth));
        return new AgreementMonthSetting(entity.pk.sId,new YearMonth(entity.pk.yearMonth),oneMonthTime);
    }
}
