package nts.uk.ctx.at.request.infra.entity.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="KRQDT_APP_TRIP")
public class KrqdtAppTrip extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KrqdtAppTripPK krqdtAppTripPK;

    @Column(name="WORK_TYPE_CD")
    public String workTypeCD;

    @Column(name="WORK_TIME_CD")
    public String workTimeCD;

    @Column(name="WORK_TIME_START")
    public Integer workTimeStart;

    @Column(name="WORK_TIME_END")
    public Integer workTimeEnd;

    @Column(name="START_TIME")
    public Integer startTime;

    @Column(name="ARRIVAL_TIME")
    public Integer arrivalTime;

    @Override
    protected Object getKey() {
        return this.krqdtAppTripPK;
    }

    public GeneralDate getAppDate() {
        return this.getKrqdtAppTripPK().getTargetDate();
    }

}
