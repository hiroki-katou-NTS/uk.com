package nts.uk.ctx.at.function.infra.entity.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@Entity
@Getter
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

    public AlarmCheckCdtWorkplaceCategory toDomain() {
        return new AlarmCheckCdtWorkplaceCategory(

        );
    }

    public static KfnmtWkpAlstchkConcat toEntity(AlarmCheckCdtWorkplaceCategory domain) {
        return null;
    }
}
