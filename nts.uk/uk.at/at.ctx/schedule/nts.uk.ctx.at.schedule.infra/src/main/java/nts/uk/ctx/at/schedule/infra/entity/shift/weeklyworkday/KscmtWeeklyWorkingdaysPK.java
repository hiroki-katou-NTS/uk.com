package nts.uk.ctx.at.schedule.infra.entity.shift.weeklyworkday;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author datnk
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtWeeklyWorkingdaysPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CID")
    public String companyId;

    @Column(name = "DAY_OF_WEEK")
    public int dayOfWeek;

}
