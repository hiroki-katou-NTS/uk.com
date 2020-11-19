package nts.uk.ctx.at.function.infra.entity.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Table(name = "KFNMT_CAT_MAP_EACHTYPE")
public class KfnmtCatMapEachType extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KfnmtCatMapEachTypePk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCD;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public String getCategoryItemCD() {
        return this.pk.categoryItemCD;
    }
}
