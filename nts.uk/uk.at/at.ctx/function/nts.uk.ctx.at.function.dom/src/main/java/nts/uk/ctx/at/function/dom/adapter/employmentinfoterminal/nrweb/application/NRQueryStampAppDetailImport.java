package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         打刻申請詳細
 */
@AllArgsConstructor
@Getter
public class NRQueryStampAppDetailImport {

	// 打刻種類名
	private String stampTypeName;

	// 申請内容
	private String contents;
}
