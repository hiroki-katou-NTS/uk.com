package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         打刻申請詳細
 */
@AllArgsConstructor
@Getter
public class NRQueryStampAppDetailExport {

	// 打刻種類名
	private String stampTypeName;

	// 申請内容
	private String contents;
}
