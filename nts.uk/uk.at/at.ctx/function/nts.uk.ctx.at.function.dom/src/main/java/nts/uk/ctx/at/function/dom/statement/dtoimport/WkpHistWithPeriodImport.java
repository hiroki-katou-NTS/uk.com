/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.statement.dtoimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class WkpHistWithPeriodImport {
	/** The wkp code. */
	// 職場ID
	private String wkpId;

	/** The wkp display name. */
	// 職場情報履歴一覧
	private List<WkpInfoHistImport> wkpInfoHistLst;

	/**
	 * Instantiates a new wkp hist with period import.
	 *
	 * @param wkpId the wkp id
	 * @param wkpInfoHistLst the wkp info hist lst
	 */
	public WkpHistWithPeriodImport(String wkpId, List<WkpInfoHistImport> wkpInfoHistLst) {
		super();
		this.wkpId = wkpId;
		this.wkpInfoHistLst = wkpInfoHistLst;
	}
}
