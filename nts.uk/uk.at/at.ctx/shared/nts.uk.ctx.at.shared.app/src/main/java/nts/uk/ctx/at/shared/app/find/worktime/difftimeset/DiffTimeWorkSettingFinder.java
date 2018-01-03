/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto.DiffTimeWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DiffTimeWorkSettingFinder.
 */
@Stateless
public class DiffTimeWorkSettingFinder {

	/** The diff time work setting repository. */
	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	/**
	 * Find by work time code.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return the diff time work setting dto
	 */
	public DiffTimeWorkSettingDto findByWorkTimeCode(String workTimeCode) {
		String companyId = AppContexts.user().companyId();
		Optional<DiffTimeWorkSetting> diffTimeOp = diffTimeWorkSettingRepository.find(companyId, workTimeCode);
		if (diffTimeOp.isPresent()) {
			DiffTimeWorkSettingDto dto = new DiffTimeWorkSettingDto();
			diffTimeOp.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}
}
