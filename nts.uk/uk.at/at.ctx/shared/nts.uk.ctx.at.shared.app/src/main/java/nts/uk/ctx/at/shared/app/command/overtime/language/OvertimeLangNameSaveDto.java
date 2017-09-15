/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.language;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangName;
import nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameGetMemento;

/**
 * The Class OvertimeLangNameSaveDto.
 */
@Getter
@Setter
public class OvertimeLangNameSaveDto{

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
	public OvertimeLangName toDomain(String companyId) {
		return new OvertimeLangName(new OvertimeLangNameGetMemento() {

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
