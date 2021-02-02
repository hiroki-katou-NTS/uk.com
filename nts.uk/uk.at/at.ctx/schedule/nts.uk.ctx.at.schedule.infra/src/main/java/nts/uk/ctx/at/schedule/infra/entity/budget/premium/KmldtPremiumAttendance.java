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
    @Override
    protected Object getKey() {
        return kmldpPremiumAttendancePK;
    }

    public static List<KmldtPremiumAttendance> toEntity(List<PremiumSetting> premiumSettings, String histId) {
        List<KmldtPremiumAttendance> rs = new ArrayList<>();
        premiumSettings.forEach(
                e -> {
                    rs.addAll(e.getAttendanceItems().stream().map(i -> new KmldtPremiumAttendance(
                            new KmldpPremiumAttendancePK(
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
