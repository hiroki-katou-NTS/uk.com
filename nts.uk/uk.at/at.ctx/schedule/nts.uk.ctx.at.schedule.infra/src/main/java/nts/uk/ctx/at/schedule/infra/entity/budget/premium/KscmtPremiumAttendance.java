package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Doan Duy Hung
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "KSCMT_PER_COST_PREMIUM")
public class KscmtPremiumAttendance extends UkJpaEntity {
    @EmbeddedId
    public KscmtPremiumAttendancePK kscmtPremiumAttendancePK;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @PrimaryKeyJoinColumns({
//            @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
//            @PrimaryKeyJoinColumn(name = "HIS_ID", referencedColumnName = "HIS_ID"),
//            @PrimaryKeyJoinColumn(name = "PREMIUM_NO", referencedColumnName = "PREMIUM_NO")
//    })
//    public KmlstPremiumSet kmlstPremiumSet;

    @Override
    protected Object getKey() {
        return kscmtPremiumAttendancePK;
    }

    public static List<KscmtPremiumAttendance> toEntity(List<PremiumSetting> premiumSettings, String histId) {
        List<KscmtPremiumAttendance> rs = new ArrayList<>();
        premiumSettings.forEach(
                e -> {
                    rs.addAll(e.getAttendanceItems().stream().map(i -> new KscmtPremiumAttendance(
                            new KscmtPremiumAttendancePK(
                                    e.getCompanyID(),
                                    histId,
                                    e.getID().value,
                                    i
                            )
                    )).collect(Collectors.toList()));
                }
        );
        return rs;
    }
}
