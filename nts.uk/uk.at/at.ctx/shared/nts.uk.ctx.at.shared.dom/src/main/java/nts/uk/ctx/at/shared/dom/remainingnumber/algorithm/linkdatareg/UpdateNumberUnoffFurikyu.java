package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.AfterChangeHolidayInfoResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;

/**
 * @author thanh_nx
 *
 *         変更後の振休振出情報と暫定データ
 */
@AllArgsConstructor
@Getter
public class UpdateNumberUnoffFurikyu {

	/**
	 * 変更後の振休振出情報
	 */
	private AfterChangeHolidayInfoResult afterResult;

	/**
	 * 暫定振出
	 */
	List<InterimRecMng> furisyutsu;

	/**
	 * 暫定振休
	 */
	List<InterimAbsMng> furikyu;
}
