package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 * 
 *         申請引数
 */
@AllArgsConstructor
public enum ApplicationArgument {
	
	PT1("PT1", "年月日指定"),
	
	PT2("PT2", "１件の申請だけを指定"),
	
	PT3("PT3", "期間指定"),
	
	PT4("PT4", "なし");
	
	public final String value;

	public final String name;
	
}
