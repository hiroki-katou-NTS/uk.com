package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfoList;

/**
 * @author thanh_nx
 *
 *         変更後の紐付け情報と振休暫定データ
 */
@AllArgsConstructor
@Getter
public class UpdateNumberUnoffFurikyu {

	// 紐付け一覧
	private final SeqVacationAssociationInfoList seqVacInfoList;

	/**
	 * 暫定振出
	 */
	List<InterimRecMng> furisyutsu;

	/**
	 * 暫定振休
	 */
	List<InterimAbsMng> furikyu;
}
