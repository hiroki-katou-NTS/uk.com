package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * @author thanh_nx
 *
 *         NRWeb照会パラメータークエリ
 */
@Getter
public class NRWebQueryParameter implements DomainValue {

	//契約コード
	private String contractCode;
	// cno
	private String cno;

	// ver
	private String ver;

	// type
	private Optional<String> type;

	// ym
	private Optional<String> ym;

	// NRWeb照会申請パラメータークエリ
	private Optional<NRWebQueryAppParameter> application;

	public NRWebQueryParameter(String contractCode, String cno, String ver, Optional<String> type, Optional<String> ym,
			Optional<NRWebQueryAppParameter> application) {
		super();
		this.contractCode = contractCode;
		this.cno = cno;
		this.ver = ver;
		this.type = type;
		this.ym = ym;
		this.application = application;
	}

	@SuppressWarnings("unchecked")
	public <T> T getYmFormat() {

		if (!ym.isPresent())
			return null;

		if (ym.get().length() == 6) {
			return (T) new YearMonth(Integer.parseInt(ym.get()));
		} else {
			int ymdTemp = Integer.parseInt(ym.get());
			return (T) GeneralDate.ymd(ymdTemp / 10000, (ymdTemp - (ymdTemp / 10000) * 10000) / 100, ymdTemp % 100);
		}
	}
}
