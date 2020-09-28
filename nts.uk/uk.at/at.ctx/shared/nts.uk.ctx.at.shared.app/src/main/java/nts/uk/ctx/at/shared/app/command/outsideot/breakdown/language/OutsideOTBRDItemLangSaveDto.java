/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.breakdown.language;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLang;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLangGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.LanguageId;

/**
 * The Class OutsideOTBRDItemLangSaveDto.
 */
@Getter
@Setter
public class OutsideOTBRDItemLangSaveDto{

	/** The name. */
	private String name;

	/** The language id. */
	private String languageId;

	/** The breakdown item no. */
	private Integer breakdownItemNo;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the overtime lang name
	 */
	public OutsideOTBRDItemLang toDomain(String companyId) {
		return new OutsideOTBRDItemLang(new OutsideOTBRDItemLangGetMemento() {

			@Override
			public LanguageId getLanguageId() {
				return new LanguageId(languageId);
			}

			@Override
			public CompanyId getCompanyId() {
				return new CompanyId(companyId);
			}

			@Override
			public BreakdownItemName getName() {
				return new BreakdownItemName(name);
			}

			@Override
			public BreakdownItemNo getBreakdownItemNo() {
				return BreakdownItemNo.valueOf(breakdownItemNo);
			}
		});
	}

}
