/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Class OutputSetting.
 */
@Getter
public class WLOutputSetting extends AggregateRoot{
	
	/** The code. */
	private WLOutputSettingCode code;
	
	/** The name. */
	private WLOutputSettingName name;
	
	/** The once sheet per person. */
	private Boolean onceSheetPerPerson;
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The category settings. */
	private List<WLCategorySetting> categorySettings;

	/**
	 * Instantiates a new WL output setting.
	 *
	 * @param memento the memento
	 */
	public WLOutputSetting(WLOutputSettingGetMemento memento) {
		super();
		this.code = memento.getCode();
		this.name = memento.getName();
		this.onceSheetPerPerson = memento.getOnceSheetPerPerson();
		this.companyCode = memento.getCompanyCode();
		this.categorySettings = memento.getCategorySettings();
		this.setVersion(memento.getVersion());
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WLOutputSettingSetMemento memento) {
		memento.setCode(this.code);
		memento.setName(this.name);
		memento.setOnceSheetPerPerson(this.onceSheetPerPerson);
		memento.setCompanyCode(this.companyCode);
		memento.setCategorySettings(this.categorySettings);
		memento.setVersion(this.getVersion());
	}
}
