package nts.uk.ctx.at.record.dom.reservation.reservationsetting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 実績集計方法
 */
@AllArgsConstructor
@Getter
public class Achievements extends DomainObject {

    /**
     *月別実績の集計
     */
    // Aggregation of monthly results.
    private AchievementMethod monthlyResults;
}
