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
	
	/**
	 * get data kmk011
	 */
	public List<Integer> getDataKMK011() {
		List<Integer> datas = new ArrayList<>();
		datas.add(216);
		datas.add(217);
		datas.add(221);
		datas.add(222);
		datas.add(226);
		datas.add(227);
		datas.add(231);
		datas.add(232);
		datas.add(236);
		datas.add(237);
		datas.add(241);
		datas.add(242);
		datas.add(246);
		datas.add(247);
		datas.add(251);
		datas.add(252);
		datas.add(256);
		datas.add(257);
		datas.add(261);
		datas.add(262);
		datas.add(266);
		datas.add(267);
		datas.add(271);
		datas.add(272);
		datas.add(276);
		datas.add(277);
		datas.add(281);
		datas.add(282);
		datas.add(286);
		datas.add(287);
		datas.add(291);
		datas.add(292);
		datas.add(296);
		datas.add(297);
		datas.add(301);
		datas.add(302);
		datas.add(306);
		datas.add(307);
		datas.add(311);
		datas.add(312);
		datas.add(551);
		datas.add(556);
		datas.add(566);
		datas.add(568);
		datas.add(570);
		datas.add(572);
		datas.add(617);
		datas.add(618);
		datas.add(619);
		datas.add(620);
		datas.add(621);
		datas.add(622);
		return datas;

	}
	
}
