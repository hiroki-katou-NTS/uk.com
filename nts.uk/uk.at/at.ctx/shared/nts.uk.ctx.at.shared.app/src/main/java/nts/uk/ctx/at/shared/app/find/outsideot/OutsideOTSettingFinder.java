/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot;

import java.util.ArrayList;
import java.util.List;
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
	
	/**
	 * Gets the all attendance item.
	 *
	 * @return the all attendance item
	 */
	public List<Integer> getAllAttendanceItem(){
		List<Integer> attendanceItems = new ArrayList<>();
		attendanceItems.add(5);
		attendanceItems.add(13);
		attendanceItems.add(14);
		attendanceItems.add(15);
		attendanceItems.add(16);
		attendanceItems.add(17);
		attendanceItems.add(18);
		attendanceItems.add(19);
		attendanceItems.add(20);
		attendanceItems.add(21);
		attendanceItems.add(22);
		attendanceItems.add(24);
		attendanceItems.add(25);
		attendanceItems.add(26);
		attendanceItems.add(27);
		attendanceItems.add(28);
		attendanceItems.add(29);
		attendanceItems.add(30);
		attendanceItems.add(31);
		attendanceItems.add(32);
		attendanceItems.add(33);
		attendanceItems.add(35);
		attendanceItems.add(36);
		attendanceItems.add(37);
		attendanceItems.add(38);
		attendanceItems.add(39);
		attendanceItems.add(40);
		attendanceItems.add(41);
		attendanceItems.add(42);
		attendanceItems.add(43);
		attendanceItems.add(44);
		attendanceItems.add(115);
		attendanceItems.add(116);
		attendanceItems.add(117);
		attendanceItems.add(281);
		attendanceItems.add(847);
		attendanceItems.add(848);
		attendanceItems.add(849);
		attendanceItems.add(850);
		attendanceItems.add(851);
		attendanceItems.add(852);
		attendanceItems.add(853);
		attendanceItems.add(854);
		attendanceItems.add(855);
		attendanceItems.add(856);
		return attendanceItems;
	}
	
	
}
