package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common;

import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import lombok.Getter;
import nts.arc.error.BusinessException;
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

	//作る
	public static NRWebQueryParameter create(MultivaluedMap<String, String> queryParam, NRWebQueryMenuName menuName) {

		String contractCode = queryParam.getFirst(NRWebQueryArg.CONTRACT_CODE.value);
		
		String cno = queryParam.getFirst(NRWebQueryArg.CNO.value);

		if (cno == null) {
			throw new BusinessException(NRWebQueryError.NO1.value);
		} else if (cno.length() < 20) {
			throw new BusinessException(NRWebQueryError.NO2.value);
		}
		
		Optional<NRWebQueryAppParameter> application = Optional.empty();
		switch (menuName) {
		case APPLICATION:

			if ((!queryParam.containsKey(NRWebQueryArg.CNO.value) || !queryParam.containsKey(NRWebQueryArg.VER.value))
					&& (!queryParam.containsKey(NRWebQueryArg.YM.value)
							|| !queryParam.containsKey(NRWebQueryArg.DATE.value))
					|| (!queryParam.containsKey(NRWebQueryArg.DATE.value)
							&& !queryParam.containsKey(NRWebQueryArg.NDATE.value)
							&& !queryParam.containsKey(NRWebQueryArg.KBN.value)
							&& !queryParam.containsKey(NRWebQueryArg.JIKBN.value))) {
				throw new BusinessException(NRWebQueryError.NO5.value);
			}

			application = Optional
					.of(new NRWebQueryAppParameter(Optional.ofNullable(queryParam.getFirst(NRWebQueryArg.DATE.value)),
							Optional.ofNullable(queryParam.getFirst(NRWebQueryArg.KBN.value)).map(x -> Integer.parseInt(x)),
							Optional.ofNullable(queryParam.getFirst(NRWebQueryArg.JIKBN.value)).map(x -> Integer.parseInt(x)),
							Optional.ofNullable(queryParam.getFirst(NRWebQueryArg.NDATE.value))));
			break;

		case MENU:
			if (!queryParam.containsKey(NRWebQueryArg.CNO.value) || !queryParam.containsKey(NRWebQueryArg.VER.value)) {
				throw new BusinessException(NRWebQueryError.NO5.value);
			}
			break;

		default:
			if (!queryParam.containsKey(NRWebQueryArg.CNO.value) || !queryParam.containsKey(NRWebQueryArg.VER.value)
					|| !queryParam.containsKey(NRWebQueryArg.YM.value)) {
				throw new BusinessException(NRWebQueryError.NO5.value);
			}
			break;
		}

		return new NRWebQueryParameter(contractCode, cno, queryParam.getFirst(NRWebQueryArg.VER.value),
				Optional.ofNullable(queryParam.getFirst(NRWebQueryArg.TYPE.value)),
				Optional.ofNullable(queryParam.getFirst(NRWebQueryArg.YM.value)), application);
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
