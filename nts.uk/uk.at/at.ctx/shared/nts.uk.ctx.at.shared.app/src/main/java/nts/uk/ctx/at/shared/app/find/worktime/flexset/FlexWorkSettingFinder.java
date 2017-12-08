/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.dto.FlexWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlexWorkSettingFinder.
 */
@Stateless
public class FlexWorkSettingFinder {
	
	/** The repository. */
	@Inject
	private FlexWorkSettingRepository repository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<FlexWorkSettingDto> findAll(){
		
		// get company id
		String companyId = AppContexts.user().companyId();
		
		// call repository find all
		List<FlexWorkSetting> flexWorkSettings = this.repository.findAll(companyId);
		
		if(CollectionUtil.isEmpty(flexWorkSettings)){
			return new ArrayList<>();
		}
		return flexWorkSettings.stream().map(domain->{
			FlexWorkSettingDto dto = new FlexWorkSettingDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

}
