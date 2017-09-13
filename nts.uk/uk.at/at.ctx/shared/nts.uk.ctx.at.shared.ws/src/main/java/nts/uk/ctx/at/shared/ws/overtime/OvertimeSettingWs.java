/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.overtime;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.overtime.OvertimeSettingFinder;
import nts.uk.ctx.at.shared.app.find.overtime.dto.OvertimeSettingDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeCalculationMethod;

/**
 * The Class CompanySettingWs.
 */
@Path("ctx/at/shared/overtime/setting")
@Produces(MediaType.APPLICATION_JSON)
public class OvertimeSettingWs extends WebService {
	
	/** The finder. */
	@Inject
	private OvertimeSettingFinder finder;

	/**
	 * Find all method.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll/method")
	public List<EnumConstant> findAllMethod() {
		return EnumAdaptor.convertToValueNameList(OvertimeCalculationMethod.class);
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
	 * Find by id.
	 *
	 * @return the overtime setting dto
	 */
	@POST
	@Path("findById")
	public OvertimeSettingDto findById(){
		return this.finder.findById();
	}

}
