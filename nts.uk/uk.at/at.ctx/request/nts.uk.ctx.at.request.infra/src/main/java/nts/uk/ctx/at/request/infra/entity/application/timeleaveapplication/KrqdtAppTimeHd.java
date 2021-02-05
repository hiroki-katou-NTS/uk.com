package nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_APP_TIME_HD")
public class KrqdtAppTimeHd extends ContractUkJpaEntity {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KrqdtAppTimeHdPK pk;

    @Column(name = "WORK_TIME_END")
    public Integer workTimeEnd;

    @Column(name = "WORK_TIME_START")
    public Integer workTimeStart;

    @Override
    protected Object getKey() {
        return this.pk;
    }

}
