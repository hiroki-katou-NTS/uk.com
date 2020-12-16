package nts.uk.ctx.at.request.infra.entity.application.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.request.infra.entity.application.businesstrip.KrqdtAppTripPK;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQDT_APP_TIME_HD")
public class KrqdtAppTimeHd extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KrqdtAppTimeHdPK krqdtAppTimeHdPKPK;

    @Column(name = "WORK_TIME_END")
    public Integer workTimeEnd;

    @Column(name = "WORK_TIME_START")
    public Integer workTimeStart;

    @Override
    protected Object getKey() {
        return this.krqdtAppTimeHdPKPK;
    }


    public static List<KrqdtAppTimeHd> toEntity(TimeLeaveApplication domain) {
        List<KrqdtAppTimeHd> result = new ArrayList<>();

        domain.getLeaveApplicationDetails().forEach(detail ->
            detail.getTimeZoneWithWorkNoLst().forEach(x -> {
                result.add(new KrqdtAppTimeHd(
                    new KrqdtAppTimeHdPK(
                        AppContexts.user().companyId(),
                        domain.getAppID(),
                        detail.getAppTimeType().value,
                        x.getWorkNo().v()
                    ),
                    x.getTimeZone().getEndTime().v(),
                    x.getTimeZone().getStartTime().v()
                ));
            })
        );
        return result;
    }


}
