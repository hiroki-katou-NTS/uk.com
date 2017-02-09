/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.app.wageledger.command.dto.CategorySettingDto;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingGetMemento;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingName;

/**
 * The Class OutputSettingCommand.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputSettingSaveCommand {
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The once sheet per person. */
	public boolean onceSheetPerPerson;
	
	/** The category settings. */
	public List<CategorySettingDto> categorySettings;
	
	/** The is create mode. */
	public boolean isCreateMode;
	
	/**
	 * To domain.
	 *
	 * @param companyCode the company code
	 * @return the WL output setting
	 */
	public WLOutputSetting toDomain(String companyCode) {
		OutputSettingSaveCommand command = this;
		return new WLOutputSetting(new WLOutputSettingGetMemento() {
			
			@Override
			public long getVersion() {
				return 0;
			}
			
			@Override
			public Boolean getOnceSheetPerPerson() {
				return command.onceSheetPerPerson;
			}
			
			@Override
			public WLOutputSettingName getName() {
				return new WLOutputSettingName(command.name);
			}
			
			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(companyCode);
			}
			
			@Override
			public WLOutputSettingCode getCode() {
				return new WLOutputSettingCode(command.code);
			}
			
			@Override
			public List<WLCategorySetting> getCategorySettings() {
				return command.categorySettings.stream()
						.map(setting -> setting.toDomain()).collect(Collectors.toList());
			}
		});
	}

}
