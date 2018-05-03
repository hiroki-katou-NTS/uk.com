/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.app.find.manualsetting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSaveRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author nam.lh
 *
 */

/**
 * The Class ManualSettingFinder.
 */
@Stateless
public class ManualSettingFinder {

	/** The repository. */
	@Inject
	private ManualSetOfDataSaveRepository repository;

	/**
	 * Find.
	 *
	 * @return the Manual Setting dto
	 */
	public ManualSettingDto find() {

		// get login info
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String cid = loginUserContext.companyId();
		// general storeProcessingId
		String storeProcessingId = UUID.randomUUID().toString();

		Optional<ManualSetOfDataSave> optManualSetting = this.repository.getManualSetOfDataSaveById(cid,
				storeProcessingId);

		if (!optManualSetting.isPresent()) {
			return null;
		}
		
		//ManualSetOfDataSave manual = new ManualSetOfDataSave();
	
		return null;
	}

	public List<ManualSettingDto> getAll() {
		return repository.getAllManualSetOfDataSave().stream().map(item -> ManualSettingDto.fromDomain(item))
				.collect(Collectors.toList());

	}
}
