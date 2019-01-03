/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.outsideot;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.outsideot.setting.OutsideOTSettingSaveCommand;
import nts.uk.ctx.at.shared.app.command.outsideot.setting.OutsideOTSettingSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.outsideot.OutsideOTSettingFinder;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTCalMed;

/**
 * The Class OutsideOTSettingWs.
 */
@Path("ctx/at/shared/outsideot/setting")
@Produces(MediaType.APPLICATION_JSON)
public class OutsideOTSettingWs extends WebService {
	
	/** The finder. */
	@Inject
	private OutsideOTSettingFinder finder;
	
	
	/** The save. */
	@Inject
	private OutsideOTSettingSaveCommandHandler save;

	/**
	 * Find all method.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll/method")
	public List<EnumConstant> findAllMethod() {
		return EnumAdaptor.convertToValueNameList(OutsideOTCalMed.class);
	}
	
	/**
	 * Find all unit.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll/unit")
	public List<EnumConstant> findAllUnit() {
		return EnumAdaptor.convertToValueNameList(Unit.class);
	}
	/**
	 * Find all unit.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll/rounding")
	public List<EnumConstant> findAllRounding() {
		return EnumAdaptor.convertToValueNameList(Rounding.class);
	}
	
	/**
	 * Find all unit.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll/roundingsub")
	public List<EnumConstant> findAllRoundingSub() {
		List<EnumConstant> roundings = EnumAdaptor.convertToValueNameList(Rounding.class);
		for (EnumConstant enumof : roundings) {
			if (enumof.getValue() == Rounding.ROUNDING_DOWN_OVER.value) {
				roundings.remove(enumof);
				break;
			}
		}
		return roundings;
	}
	
	/**
	 * Find all attendance item.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll/attendanceItem")
	public List<Integer> findAllAttendanceItem() {
		return this.finder.getAllAttendanceItem();
	}
	
	/**
	 * Find by id.
	 *
	 * @return the outside OT setting dto
	 */
	@POST
	@Path("findById")
	public OutsideOTSettingDto findById(){
		return this.finder.findById();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(OutsideOTSettingSaveCommand command) {
		this.save.handle(command);
	}
	
	/**
	 * Find all month item.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll/mothItem")
	public List<Integer> findAllMonthItem() {
		return this.finder.getDataKMK013();
	}
}
