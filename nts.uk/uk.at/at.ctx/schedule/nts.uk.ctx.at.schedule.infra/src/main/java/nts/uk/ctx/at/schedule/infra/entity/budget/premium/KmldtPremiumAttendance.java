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
@Table(name = "KMLDT_PREMIUM_ATTENDANCE")
public class KmldtPremiumAttendance extends UkJpaEntity {
    @EmbeddedId
    public KmldpPremiumAttendancePK kmldpPremiumAttendancePK;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumns({
            @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
            @PrimaryKeyJoinColumn(name = "HIS_ID", referencedColumnName = "HIS_ID"),
            @PrimaryKeyJoinColumn(name = "PREMIUM_NO", referencedColumnName = "PREMIUM_NO")
    })
    public KmlstPremiumSet kmlstPremiumSet;

    @Override
    protected Object getKey() {
        return kmldpPremiumAttendancePK;
    }

    public static List<KmldtPremiumAttendance> toEntity(List<PremiumSetting> premiumSettings) {
        List<KmldtPremiumAttendance> rs = new ArrayList<>();
        premiumSettings.forEach(
                e -> {
                    rs.addAll(e.getAttendanceItems().stream().map(i -> new KmldtPremiumAttendance(
                            new KmldpPremiumAttendancePK(
                                    e.getCompanyID(),
                                    e.getHistoryID(),
                                    e.getID().value,
                                    i
                            ),
                            null
                    )).collect(Collectors.toList()));
                }
        );
        return rs;
    }
}
