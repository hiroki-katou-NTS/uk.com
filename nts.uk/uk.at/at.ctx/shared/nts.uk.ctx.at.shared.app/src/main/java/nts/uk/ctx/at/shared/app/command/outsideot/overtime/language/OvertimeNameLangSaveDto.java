/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.overtime.language;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLang;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLangGetMemento;

/**
 * The Class OvertimeNameLangSaveDto.
 */
@Getter
@Setter
public class OvertimeNameLangSaveDto{

	/** The name. */
	private String name;

	/** The language id. */
	private String languageId;

	/** The overtime no. */
	private Integer overtimeNo;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the overtime lang name
	 */
	public OvertimeNameLang toDomain(String companyId) {
		return new OvertimeNameLang(new OvertimeNameLangGetMemento() {

			@Override
			public OvertimeNo getOvertimeNo() {
				return OvertimeNo.valueOf(overtimeNo);
			}

			@Override
			public OvertimeName getName() {
				return new OvertimeName(name);
			}

			@Override
			public LanguageId getLanguageId() {
				return new LanguageId(languageId);
			}

			@Override
			public CompanyId getCompanyId() {
				return new CompanyId(companyId);
			}
		});
	}

}
