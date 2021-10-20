package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author thanh_nx
 *
 *         消化発生の変更要求Output
 */
@AllArgsConstructor
@Data
public class CreateChangeReqDigestOccurOutput {

	// 消化の変更要求
	private RequestChangeDigestOccr changeDigest;

	// 発生の変更要求
	private RequestChangeDigestOccr changeOccr;
}
