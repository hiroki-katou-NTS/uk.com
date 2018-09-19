package nts.uk.ctx.at.function.ws.annualworkschedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.annualworkschedule.AddSetOutItemsWoScCommandHandler;
import nts.uk.ctx.at.function.app.command.annualworkschedule.RemoveSetOutItemsWoScCommandHandler;
import nts.uk.ctx.at.function.app.command.annualworkschedule.SetOutItemsWoScCommand;
import nts.uk.ctx.at.function.app.command.annualworkschedule.UpdateSetOutItemsWoScCommandHandler;
import nts.uk.ctx.at.function.app.export.annualworkschedule.AnnualWorkScheduleExportQuery;
import nts.uk.ctx.at.function.app.export.annualworkschedule.AnnualWorkScheduleExportService;
import nts.uk.ctx.at.function.app.find.annualworkschedule.ItemOutTblBookDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.ItemOutTblBookFinder;
import nts.uk.ctx.at.function.app.find.annualworkschedule.PeriodDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.PeriodFinder;
import nts.uk.ctx.at.function.app.find.annualworkschedule.RoleWhetherLoginDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.RoleWhetherLoginFinder;
import nts.uk.ctx.at.function.app.find.annualworkschedule.SetOutItemsWoScDto;
import nts.uk.ctx.at.function.app.find.annualworkschedule.SetOutItemsWoScFinder;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.OutputAgreementTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.PageBreakIndicator;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * Web Service for KWR008_年間勤務表
 */
@Path("at/function/annualworkschedule")
@Produces("application/json")
public class Kwr008WebService extends WebService {

	@Inject
	private I18NResourcesForUK i18n;

	// @Inject
	// private PermissionOfEmploymentFinder permissionOfEmploymentFinder;

	@Inject
	private PeriodFinder periodFinder;

	@Inject
	private SetOutItemsWoScFinder outputItemSetting; 
	
	@Inject
	private RemoveSetOutItemsWoScCommandHandler rmOutputItemSetting;
	
	@Inject
	private AddSetOutItemsWoScCommandHandler addOutputItemSetting;
	
	@Inject
	private UpdateSetOutItemsWoScCommandHandler updateOutputItemSetting;
	
	@Inject
	private ItemOutTblBookFinder listItemOut;

	@Inject
	private AnnualWorkScheduleExportService serive;

	@Inject
	private RoleWhetherLoginFinder roleWhetherLoginFinder;

	@POST
	@Path("getCurrentLoginerRole")
	public RoleWhetherLoginDto getCurrentLoginerRole() {
		//return this.permissionOfEmploymentFinder.getPermissionOfEmploymentForm();
		return roleWhetherLoginFinder.getCurrentLoginerRole();
	}

	@POST
	@Path("get/period")
	public PeriodDto getList() {
		return this.periodFinder.getPeriod();
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
	
	/* *
	 * 出力項目設定コード
	 * */
	@POST
	@Path("get/outputitemsetting")
	public List<SetOutItemsWoScDto> getOutputItemSetting(){
		return outputItemSetting.getAllSetOutItemsWoSc();
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
	public void deleteOutputItemSetting(SetOutItemsWoScCommand command){
		this.rmOutputItemSetting.handle(command);
	}
	
	@POST
	@Path("add/outputitemsetting")
	public void addOutputItemSetting(SetOutItemsWoScCommand command){
		this.addOutputItemSetting.handle(command);
	}
	
	@POST
	@Path("update/outputitemsetting")
	public void updateOutputItemSetting(SetOutItemsWoScCommand command){
		this.updateOutputItemSetting.handle(command);
	}
	
	/**
	 * 帳表に出力する項目
	 */
	@POST
	@Path("get/listItemOutput/{setOutCd}")
	public List<ItemOutTblBookDto> listItemOuput(@PathParam("setOutCd") String setOutCd){
		return this.listItemOut.getItemOutTblBookBySetOutCd(setOutCd);
	}
}
