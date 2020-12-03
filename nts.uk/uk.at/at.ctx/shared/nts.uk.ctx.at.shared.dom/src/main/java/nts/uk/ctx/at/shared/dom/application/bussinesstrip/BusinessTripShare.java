package nts.uk.ctx.at.shared.dom.application.bussinesstrip;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         出張申請
 */
@Getter
@Setter
@NoArgsConstructor
public class BusinessTripShare extends ApplicationShare {

	// 出張勤務情報
	private List<BusinessTripInfoShare> infos;

    // 出発時刻
    private Optional<TimeWithDayAttr> departureTime;

    // 帰着時刻
    private Optional<TimeWithDayAttr> returnTime;

    public BusinessTripShare(List<BusinessTripInfoShare> infos, Integer departureTime, Integer returnTime, ApplicationShare application) {
        super(application);
        this.infos = infos;
        this.departureTime = departureTime == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(departureTime));
        this.returnTime = returnTime == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(returnTime));
    }

	public BusinessTripShare(ApplicationShare application) {
		super(application);
	}
}
