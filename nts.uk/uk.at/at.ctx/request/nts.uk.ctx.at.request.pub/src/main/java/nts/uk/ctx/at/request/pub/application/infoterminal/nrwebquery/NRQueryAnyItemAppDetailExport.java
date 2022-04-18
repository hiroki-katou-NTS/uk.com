package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         任意項目申請詳細
 */
@AllArgsConstructor
@Getter
public class NRQueryAnyItemAppDetailExport {

	// 任意項目名称
	private String anyItemName;

	// 値
	private String value;
}
