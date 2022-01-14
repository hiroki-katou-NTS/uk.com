package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfoList;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;

/**
 * @author thanh_nx
 *
 *         変更後の紐付け情報と代休暫定データ
 */
@AllArgsConstructor
@Getter
public class UpdateNumberUnoffDaikyu {

	/**
	 * 	紐付け一覧
	 */
	private SeqVacationAssociationInfoList seqVacInfoList;

	/**
	 * 暫定休出
	 */
	private List<InterimBreakMng> kyusyutsu;

	/**
	 * 暫定代休
	 */
	private List<InterimDayOffMng> daikyu;

}
