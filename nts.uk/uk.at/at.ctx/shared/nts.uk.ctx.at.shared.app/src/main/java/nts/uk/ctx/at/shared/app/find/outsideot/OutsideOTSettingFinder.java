/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTSettingDto;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OutsideOTSettingFinder.
 */
@Stateless
public class OutsideOTSettingFinder {
	
	/** The repository. */
	@Inject
	private OutsideOTSettingRepository repository;
	
	/**
	 * Find by id.
	 *
	 * @return the outside OT setting dto
	 */
	public OutsideOTSettingDto findById() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		Optional<OutsideOTSetting> overtimeSetting = this.repository.findById(companyId);

		OutsideOTSettingDto dto = new OutsideOTSettingDto();
		if (overtimeSetting.isPresent()) {
			overtimeSetting.get().saveToMemento(dto);
		}

		return dto;

	}
	
	/**
	 * Report by id.
	 *
	 * @return the outside OT setting dto
	 */
	public OutsideOTSettingDto reportById() {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// call repository find data
		Optional<OutsideOTSetting> overtimeSetting = this.repository.reportById(companyId);
		
		OutsideOTSettingDto dto = new OutsideOTSettingDto();
		if (overtimeSetting.isPresent()) {
			overtimeSetting.get().saveToMemento(dto);
		}
		
		return dto;
		
	}
	
	
}
