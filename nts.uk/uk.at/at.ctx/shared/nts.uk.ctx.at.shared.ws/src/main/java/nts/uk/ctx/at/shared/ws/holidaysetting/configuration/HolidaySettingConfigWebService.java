package nts.uk.ctx.at.shared.ws.holidaysetting.configuration;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.holidaysetting.configuration.HolidaySettingConfigSaveCommand;
import nts.uk.ctx.at.shared.app.command.holidaysetting.configuration.HolidaySettingConfigSaveCommandHandler;
import nts.uk.ctx.at.shared.app.command.holidaysetting.configuration.PublicHolidaySettingCommand;
import nts.uk.ctx.at.shared.app.find.holidaysetting.configuration.HolidaySettingConfigDto;
import nts.uk.ctx.at.shared.app.find.holidaysetting.configuration.HolidaySettingConfigFinder;
import nts.uk.ctx.at.shared.app.find.holidaysetting.configuration.PublicHolidaySettingDto;
import nts.uk.ctx.at.shared.app.find.holidaysetting.configuration.PublicHolidaySettingFindDto;
import nts.uk.ctx.at.shared.app.find.holidaysetting.configuration.PublicHolidaySettingFinder;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.ManagementDistinction;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayCarryOverDeadline;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayPeriod;

/**
 * The Class HolidaySettingConfigWebService.
 */
@Path("at/shared/holidaysetting/config")
@Produces(MediaType.APPLICATION_JSON)
public class HolidaySettingConfigWebService extends WebService {
	
	/** The finder. */
//	@Inject
//	private HolidaySettingConfigFinder finder;
	
	@Inject
	private PublicHolidaySettingFinder finder;
	
	/** The save handler. */
	@Inject
	private HolidaySettingConfigSaveCommandHandler saveHandler;
	
	/**
	 * Find data.
	 *
	 * @return the holiday setting config dto
	 */
	@Path("find")
	@POST
	public PublicHolidaySettingDto findData(){
		return this.finder.findPubHdSetting();
	}
	
	/**
	 * Save data.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void saveData(PublicHolidaySettingCommand command){
		this.saveHandler.handle(command);
	}
	
	/**
	 * Gets the enum day of public holiday.
	 *
	 * @return the enum day of public holiday
	 */
	@Path("enum/dayofpublicholiday")
	@POST
	public List<EnumConstant> getEnumDayOfPublicHoliday(){
		return EnumAdaptor.convertToValueNameList(DayOfPublicHoliday.class);
	}
	
	/**
	 * Gets the enum day of week.
	 *
	 * @return the enum day of week
	 */
	@Path("enum/dayofweek")
	@POST
	public List<EnumConstant> getEnumDayOfWeek(){
		return EnumAdaptor.convertToValueNameList(DayOfWeek.class);
	}
	
	/**
	 * Gets the enum public holiday period.
	 *
	 * @return the enum public holiday period
	 */
	@Path("enum/publicholidayperiod")
	@POST
	public List<EnumConstant> getEnumPublicHolidayPeriod(){
		return EnumAdaptor.convertToValueNameList(PublicHolidayPeriod.class);
	}
	
	/**
	 * Gets the enum public holiday management classification.
	 *
	 * @return the enum public holiday management classification
	 */
	@Path("enum/pubhdmanagementatr")
	@POST
	public List<EnumConstant> getEnumPublicHolidayManagementClassification(){
		return EnumAdaptor.convertToValueNameList(PublicHolidayManagementClassification.class);
	}
	
	/**
	 * Gets the enum public holiday carry over deadline.
	 *
	 * @return the enum public holiday carry over deadline
	 */
	@Path("enum/publicholidaycarryoverdeadline")
	@POST
	public List<EnumConstant> getEnumPublicHolidayCarryOverDeadline(){
		return EnumAdaptor.convertToValueNameList(PublicHolidayCarryOverDeadline.class);
	}
	
	@Path("enum/manage")
	@POST
	public List<EnumConstant> getEnumManage(){
		return EnumAdaptor.convertToValueNameList(ManagementDistinction.class);
	}
	
	@Path("findPublicHolidaySetting")
	@POST
	public PublicHolidaySettingFindDto findPublicHolidaySettingFindDto(){
//		return this.finder.findIsManage();
		return null;
	}
}
