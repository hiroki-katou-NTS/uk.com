package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * @author thanh_nx
 *
 *         NRWeb照会申請パラメータークエリ
 */
@Getter
public class NRWebQueryAppParameter {

	// date
	private Optional<String> date;

	// kbn
	private Optional<Integer> kbn;

	// jikbn
	private Optional<Integer> jikbn;

	// ndate
	private Optional<String> ndate;

	public NRWebQueryAppParameter(Optional<String> date, Optional<Integer> kbn, Optional<Integer> jikbn,
			Optional<String> ndate) {
		this.date = date;
		this.kbn = kbn;
		this.jikbn = jikbn;
		this.ndate = ndate;
	}

	@SuppressWarnings("unchecked")
	public <T> T getYmFormat() {

		if (!date.isPresent())
			return null;

		if (date.get().length() == 6) {
			return (T) new YearMonth(Integer.parseInt(date.get()));
		} else {
			int ymdTemp = Integer.parseInt(date.get());
			return (T) GeneralDate.ymd(ymdTemp / 10000, (ymdTemp - (ymdTemp / 10000) * 10000) / 100, ymdTemp % 100);
		}
	}
}
