/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.scherec.totaltimes;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.totaltimes.SaveTotalTimesCommandHandler;
import nts.uk.ctx.at.shared.app.command.scherec.totaltimes.TotalTimesCommand;
import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.TotalTimesFinder;
import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto.TotalTimesDetailDto;
import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto.TotalTimesItemDto;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * The Class TotalTimesWS.
 */
@Path("at/shared/scherec/totaltimes")
@Produces("application/json")
public class TotalTimesWS extends WebService {

	/** The total times finder. */
	@Inject
	private TotalTimesFinder totalTimesFinder;

	/** The save total times command handler. */
	@Inject
	private SaveTotalTimesCommandHandler saveTotalTimesCommandHandler;

	/**
	 * Gets the all total times.
	 *
	 * @return the all total times
	 */
	@POST
	@Path("getallitem")
	public List<TotalTimesItemDto> getAllTotalTimes() {
		return this.totalTimesFinder.getAllTotalTimesItems();
	}

	/**
	 * Gets the total times detail.
	 *
	 * @param totalCountNo
	 *            the total count no
	 * @return the total times detail
	 */
	@POST
	@Path("getdetail/{totalCountNo}")
	public TotalTimesDetailDto getTotalTimesDetail(
			@PathParam("totalCountNo") Integer totalCountNo) {
		return this.totalTimesFinder.getTotalTimesDetails(totalCountNo);
	}

	/**
	 * Find completion list.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/totalclassification")
	public List<EnumConstant> findEnumTotal() {
		return EnumAdaptor.convertToValueNameList(SummaryAtr.class);
	}

	/**
	 * Find totalUseEnum .
	 *
	 * @return the list
	 */
	@POST
	@Path("find/totalUseEnum")
	public List<EnumConstant> findEnumUse() {
		return EnumAdaptor.convertToValueNameList(UseAtr.class);
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(TotalTimesCommand command) {
		this.saveTotalTimesCommandHandler.handle(command);
	}

}
