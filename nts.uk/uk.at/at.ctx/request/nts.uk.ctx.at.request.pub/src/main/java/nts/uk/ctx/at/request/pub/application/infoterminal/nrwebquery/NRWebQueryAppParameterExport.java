package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         NRWeb照会申請パラメータークエリ
 */
@Getter
public class NRWebQueryAppParameterExport {

	// date
	// 申請対象年月日
	private Optional<String> date;

	// kbn
	// 申請種類
	private Optional<Integer> kbn;

	// jikbn
	// 事前事後区分
	private Optional<Integer> jikbn;

	// ndate
	// 秒まで指定
	private Optional<String> ndate;

	public NRWebQueryAppParameterExport(Optional<String> date, Optional<Integer> kbn, Optional<Integer> jikbn,
			Optional<String> ndate) {
		this.date = date;
		this.kbn = kbn;
		this.jikbn = jikbn;
		this.ndate = ndate;
	}
}
