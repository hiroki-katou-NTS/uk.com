package nts.uk.ctx.at.shared.dom.application.bussinesstrip;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;

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
	private Optional<Integer> departureTime;

	// 帰着時刻
	private Optional<Integer> returnTime;

	public BusinessTripShare(List<BusinessTripInfoShare> infos, Integer departureTime, Integer returnTime,
			ApplicationShare application) {
		super(application);
		this.infos = infos;
		this.departureTime = Optional.ofNullable(departureTime);
		this.returnTime = Optional.ofNullable(returnTime);
	}

	public BusinessTripShare(ApplicationShare application) {
		super(application);
	}
}
