package nts.uk.ctx.at.function.ws.annualworkschedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.annualworkschedule.SetOutItemsWoScCommand;
import nts.uk.ctx.at.function.app.command.annualworkschedule.SetOutItemsWoScCommandSaveHandler;
import nts.uk.ctx.at.function.app.command.annualworkschedule.SetOutItemsWoScCopyCommand;
import nts.uk.ctx.at.function.app.command.annualworkschedule.SetOutItemsWoScCopyCommandHandler;
import nts.uk.ctx.at.function.app.command.annualworkschedule.SetOutItemsWoScDeleteCommand;
import nts.uk.ctx.at.function.app.command.annualworkschedule.SetOutItemsWoScDeleteCommandHandler;
import nts.uk.ctx.at.function.app.export.annualworkschedule.AnnualWorkScheduleExportQuery;
import nts.uk.ctx.at.function.app.export.annualworkschedule.AnnualWorkScheduleExportService;
import nts.uk.ctx.at.function.app.find.annualworkschedule.PeriodDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.PeriodFinder;
import nts.uk.ctx.at.function.app.find.annualworkschedule.RoleWhetherLoginDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.RoleWhetherLoginFinder;
import nts.uk.ctx.at.function.app.find.annualworkschedule.SetOutputItemOfAnnualWorkSchDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.SettingOutputItemOfAnnualWorkScheduleFinder;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.OutputAgreementTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.PageBreakIndicator;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemDto;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * Web Service for KWR008_年間勤務表
 */
@Path("at/function/annualworkschedule")
@Produces("application/json")
public class Kwr008WebService extends WebService {

	@Inject
	private I18NResourcesForUK i18n;

	@Inject
	private PeriodFinder periodFinder;

	@Inject
	private AnnualWorkScheduleExportService serive;

	@Inject
	private RoleWhetherLoginFinder roleWhetherLoginFinder;

	@Inject
	private SettingOutputItemOfAnnualWorkScheduleFinder settingFinder;
	
	@Inject
	private SetOutItemsWoScCommandSaveHandler saveHandler;
	
	@Inject
	private SetOutItemsWoScDeleteCommandHandler removeHandler;
	
	@Inject
	private SetOutItemsWoScCopyCommandHandler copyHandler;

	@POST
	@Path("getCurrentLoginerRole")
	public RoleWhetherLoginDto getCurrentLoginerRole() {
		return roleWhetherLoginFinder.getCurrentLoginerRole();
	}

	@POST
	@Path("get/period")
	public PeriodDto getList() {
		return this.periodFinder.getPeriod();
	}
	
	@POST
	@Path("get/currentMonth")
	public String currentMonth() {
		return this.periodFinder.getCurentMonth().toString();
	}

	/**
	 * KWR008 A
	 * 改頁選択 - Page break selection
	 * enum
	 * */
	@POST
	@Path("get/enum/pagebreak")
	public List<EnumConstant> getEnumPageBreakSelection(){
		return EnumAdaptor.convertToValueNameList(PageBreakIndicator.class, i18n);
	}
	
	/**
	 * 値の出力形式
	 * Enum
	 */
	@POST
	@Path("get/enum/valueoutputformat")
	public List<EnumConstant> getEnumValueOutputFormat(){
		return EnumAdaptor.convertToValueNameList(ValueOuputFormat.class, i18n);
	}
	
	/**
	 * 36協定時間を出力する場合の表示形式
	 * Enum
	 */
	@POST
	@Path("get/enum/outputagreementtime")
	public List<EnumConstant> getEnumOutputAgreementTime(){
		return EnumAdaptor.convertToValueNameList(OutputAgreementTime.class, i18n);
	}

	@POST
	@Path("export")
	public ExportServiceResult generate(AnnualWorkScheduleExportQuery query) {
		return this.serive.start(query);
	}

	/*
	 * ドメインモデル「年間勤務表（36チェックリスト）」を削除する
	 */
	@POST
	@Path("delete/outputitemsetting")
	public void deleteOutputItemSetting(SetOutItemsWoScDeleteCommand command){
		this.removeHandler.handle(command);
	}
	
	@POST
	@Path("save/outputitemsetting")
	public void addOutputItemSetting(SetOutItemsWoScCommand command) {
		this.saveHandler.handle(command);
	}

	@POST
	@Path("checkAverage/{layoutId}")
	public Boolean checkAverage(@PathParam("layoutId") String layoutId) {
		return this.settingFinder.checkAverage(layoutId);
	}
	
	@POST
	@Path("/findAll/{settingType}/{printForm}")
	public List<SetOutputItemOfAnnualWorkSchDto> findAll(@PathParam("settingType") int settingType
													   , @PathParam("printForm") int printForm) {
		return this.settingFinder.getAllAnnualSetting(settingType, printForm);
	}
	
	@POST
	@Path("/findByLayoutId/{layoutId}")
	public SetOutputItemOfAnnualWorkSchDto findByLayoutId(@PathParam("layoutId") String layoutId) {
		return this.settingFinder.findByLayoutId(layoutId);
	}
	
	@POST
	@Path("/getAtdItemsByDisplayFormat/{displayFormat}")
	public List<AttendanceItemDto> getAtdItemsByDisplayFormat(@PathParam("displayFormat") int displayFormat) {
		return this.settingFinder.getAtdItemsByType(displayFormat);
	}
	
	@Path("executeCopy")
	@POST
	public void executeCopy(SetOutItemsWoScCopyCommand command) {
		this.copyHandler.handle(command);
	}
	
	@Path("get/startMonth")
	@POST
	public JavaTypeResult<String> getStartMonth() {
		return new JavaTypeResult<>(this.periodFinder.getStartMonth());
	}
}
