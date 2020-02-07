package nts.uk.ctx.hr.develop.ws.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.app.workrule.closure.workday.GetClosureDateFinder;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySetting;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingValue;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateSettingClass;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.algorithm.DateDisplaySettingService;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto.DateDisplaySettingPeriod;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto.PeriodDisplaySettingList;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.service.IGetDate;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.service.IGetDatePeriod;
import nts.uk.ctx.hr.develop.dom.workrule.closure.workday.ClosureDateOfEmploymentImport;

@Path("hrtest/datesetting")
@Produces(MediaType.APPLICATION_JSON)
public class DateSettingTestWebservice {

	@Inject
	private DateDisplaySettingService dateDisplaySettingSv;
	
	@Inject
	private IGetDate iGetDate;
	
	@Inject
	private IGetDatePeriod iGetDatePe;

	@POST
	@Path("/ag/1")
	public List<PeriodDisplaySettingList> ag1(TestDto dto) {
		return dateDisplaySettingSv.getDateDisplaySetting(dto.getProgramId(), dto.getCompanyId());
	}
	
	@Inject
	private GetClosureDateFinder finder;
	
	
	@POST
	@Path("/642/{companyId}")
	public List<ClosureDateOfEmploymentImport> getClosureDateAdaptor(@PathParam("companyId") String companyId) {

		return this.finder.getClosureDateAdaptor(companyId);
	}

	@POST
	@Path("/ag/2")
	public void ag2(TestDto dto) {
		
		DateDisplaySettingValue start = DateDisplaySettingValue.builder()
				.settingClass(EnumAdaptor.valueOf(dto.getStartDateSettingCategory(), DateSettingClass.class))
				.settingDate(dto.getStartDateSettingDate())
				.settingMonth(dto.getStartDateSettingMonth())
				.settingNum(dto.getStartDateSettingNum())
				.build();
		
		DateDisplaySettingValue end = DateDisplaySettingValue.builder()
				.settingClass(EnumAdaptor.valueOf(dto.getEndDateSettingCategory(), DateSettingClass.class))
				.settingDate(dto.getEndDateSettingDate())
				.settingMonth(dto.getEndDateSettingMonth())
				.settingNum(dto.getEndDateSettingNum())
				.build();
		
		DateDisplaySetting setting = DateDisplaySetting.builder()
				.companyId(dto.getCompanyId())
				.programId(dto.getProgramId())
				.endDateSetting(Optional.ofNullable(end))
				.startDateSetting(start)
				.build();
		
		dateDisplaySettingSv.add(dto.getCompanyId(), Arrays.asList(setting));
	}

	@POST
	@Path("/ag/3")
	public void ag3(TestDto dto) {
		DateDisplaySettingValue start = DateDisplaySettingValue.builder()
				.settingClass(EnumAdaptor.valueOf(dto.getStartDateSettingCategory(), DateSettingClass.class))
				.settingDate(dto.getStartDateSettingDate())
				.settingMonth(dto.getStartDateSettingMonth())
				.settingNum(dto.getStartDateSettingNum())
				.build();
		
		DateDisplaySettingValue end = DateDisplaySettingValue.builder()
				.settingClass(EnumAdaptor.valueOf(dto.getEndDateSettingCategory(), DateSettingClass.class))
				.settingDate(dto.getEndDateSettingDate())
				.settingMonth(dto.getEndDateSettingMonth())
				.settingNum(dto.getEndDateSettingNum())
				.build();
		
		DateDisplaySetting setting = DateDisplaySetting.builder()
				.companyId(dto.getCompanyId())
				.programId(dto.getProgramId())
				.endDateSetting(Optional.ofNullable(end))
				.startDateSetting(start)
				.build();
		
		dateDisplaySettingSv.update(dto.getCompanyId(), Arrays.asList(setting));
	}

	@POST
	@Path("/ag/4")
	public GeneralDate ag4(TestDto dto) {
		DateDisplaySettingValue start = DateDisplaySettingValue.builder()
				.settingClass(EnumAdaptor.valueOf(dto.getStartDateSettingCategory(), DateSettingClass.class))
				.settingDate(dto.getStartDateSettingDate())
				.settingMonth(dto.getStartDateSettingMonth())
				.settingNum(dto.getStartDateSettingNum())
				.build();
		return dateDisplaySettingSv.getDisplayDate(dto.getCompanyId(), start);
	}

	@POST
	@Path("/itf/1")
	public GeneralDate itf1(TestDto dto) {
		return iGetDate.getDate(dto.getCompanyId(), dto.getProgramId());
	}

	@POST
	@Path("/itf/2")
	public DateDisplaySettingPeriod itf2(TestDto dto) {
		return iGetDatePe.getDatePeriod(dto.getCompanyId(), dto.getProgramId());
	}
}
