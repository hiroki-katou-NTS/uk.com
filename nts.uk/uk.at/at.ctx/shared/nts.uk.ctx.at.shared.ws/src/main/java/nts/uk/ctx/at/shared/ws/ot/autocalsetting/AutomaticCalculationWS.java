/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.ot.autocalsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * The Class AutomaticCalculationWS.
 */
@Path("ctx/at/shared/ot/autocal")
@Produces("application/json")
public class AutomaticCalculationWS extends WebService {

	/** The i 18 n. */
	@Inject
	private I18NResourcesForUK i18n;
	
	/**
	 * Find enum auto cal atr overtime.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/autocalatrovertime")
	public List<EnumConstant> findEnumAutoCalAtrOvertime() {
		return EnumAdaptor.convertToValueNameList(AutoCalAtrOvertime.class, i18n);
	}
	
	/**
	 * Find enum auto cal atr overtime without time recorder.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/autocalatrovertimewithouttimerecorder")
	public List<EnumConstant> findEnumAutoCalAtrOvertimeWithoutTimeRecorder() {
		List<EnumConstant> result = EnumAdaptor.convertToValueNameList(AutoCalAtrOvertime.class, i18n).stream()
				.filter(item -> item.getValue() != AutoCalAtrOvertime.TIMERECORDER.value)
				.collect(Collectors.toList());
		return result;
	}

	/**
	 * Find enum use classification.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/autocaluseclassification")
	public List<EnumConstant> findEnumUseClassification() {
		return EnumAdaptor.convertToValueNameList(ApplyAtr.class, i18n);
	}

	/**
	 * Find enum time limit upper limit setting.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/autocaltimelimitsetting")
	public List<EnumConstant> findEnumTimeLimitUpperLimitSetting() {
		return EnumAdaptor.convertToValueNameList(TimeLimitUpperLimitSetting.class, i18n);
	}

}
