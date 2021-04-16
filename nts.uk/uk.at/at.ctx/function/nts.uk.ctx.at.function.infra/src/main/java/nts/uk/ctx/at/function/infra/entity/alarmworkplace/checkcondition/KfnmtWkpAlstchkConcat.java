package nts.uk.ctx.at.function.infra.entity.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Table(name = "KFNMT_WKP_ALSTCHK_CONCAT")
public class KfnmtWkpAlstchkConcat extends UkJpaEntity implements Serializable {

    @EmbeddedId
    public KfnmtWkpAlstchkConcatPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCD;

    @Column(name = "ALARMCHK_CONDTN_NAME")
    public String alarmCdtName;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static KfnmtWkpAlstchkConcat toEntity(AlarmCheckCdtWorkplaceCategory domain) {
        return new KfnmtWkpAlstchkConcat(
                new KfnmtWkpAlstchkConcatPk(
                        AppContexts.user().companyId(),
                        domain.getCategory().value,
                        domain.getCode().v()
                        ),
                AppContexts.user().contractCode(),
                domain.getName().v()
        );
    }
}
