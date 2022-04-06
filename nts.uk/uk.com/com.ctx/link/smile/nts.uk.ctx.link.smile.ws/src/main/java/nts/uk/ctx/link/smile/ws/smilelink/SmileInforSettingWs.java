package nts.uk.ctx.link.smile.ws.smilelink;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutputConditionCode;
import nts.uk.ctx.exio.app.command.exo.cdconvert.AddOutputCodeConvertCommandHandler;
import nts.uk.ctx.exio.app.command.exo.cdconvert.CdConvertDetailCommand;
import nts.uk.ctx.exio.app.command.exo.cdconvert.OutputCodeConvertCommand;
import nts.uk.ctx.exio.app.command.exo.charegister.ChacDataFmSetCommand;
import nts.uk.ctx.exio.app.command.exo.charegister.SettingDataCharRegisterService;
import nts.uk.ctx.exio.app.command.exo.condset.RegisterStdOutputCondSetCommandHandler;
import nts.uk.ctx.exio.app.command.exo.condset.StdOutItemOrderCommand;
import nts.uk.ctx.exio.app.command.exo.condset.StdOutputCondSetCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.AddPerformSettingByInTimeCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.AddPerformSettingByInTimeCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.AddPerformSettingByTimeCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.AddPerformSettingByTimeCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.DateFormatSettingCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.DateFormatSettingCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.NumberDataFormatSettingCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.NumberDataFormatSettingCommandHandler;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.NumberDfsCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting.TimeDfsCommand;
import nts.uk.ctx.exio.app.command.exo.outputitem.CategoryItemCommand;
import nts.uk.ctx.exio.app.command.exo.outputitem.RegisterStdOutItemCommandHandler;
import nts.uk.ctx.exio.app.command.exo.outputitem.StdOutItemCommand;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DataFormatSettingRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;
import nts.uk.ctx.link.smile.app.smilelink.SmileAcceptLinkDto;
import nts.uk.ctx.link.smile.app.smilelink.SmileAcceptLinkFinder;
import nts.uk.ctx.link.smile.app.smilelink.SmileOutLinkSetDto;
import nts.uk.ctx.link.smile.app.smilelink.SmileOutLinkSetFinder;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileCooperationOutputClassification;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/link/smile/smilelink")
@Produces("application/json")
public class SmileInforSettingWs {
	@Inject
	private SmileAcceptLinkFinder acceptFinder;
	
	@Inject
	private SmileOutLinkSetFinder outLinkFinder;
	
	@Inject
	private RegisterStdOutputCondSetCommandHandler registerStdOutputCondSetCommandHandler;
	
	@Inject
	private RegisterStdOutItemCommandHandler addStdOutItemCommandHandler;
	
	@Inject
	private SettingDataCharRegisterService settingDataCharRegisterService;
	
	@Inject
	private AddOutputCodeConvertCommandHandler addHandler;
	
	@Inject
	private SmileLinkageOutputSettingRepository linkOutSetRep;
	
	@Inject
	private OutputCodeConvertRepository convertRep;
	
	@Inject
	private DataFormatSettingRepository formatRep;
	
	@Inject
	private DateFormatSettingCommandHandler dateCmdHdl;
	
	@Inject
	private NumberDataFormatSettingCommandHandler numberCmdHdl;
	
	@Inject
	private AddPerformSettingByTimeCommandHandler performCmdHdl;
	
	@Inject
	private AddPerformSettingByInTimeCommandHandler performInCmdHdl;
	
	@POST
	@Path("get-accept-setting")
	public SmileAcceptLinkDto getAcceptInfor() {
		SmileAcceptLinkDto acceptSmile = acceptFinder.findAcceptForSmile();
		return acceptSmile;
	}	
	
	@POST
	@Path("get-outlink-setting")
	public SmileOutLinkSetDto getOutLinkInfor() {
		SmileOutLinkSetDto outLinkSmile = outLinkFinder.getOutLinkSetForSmile();
		return outLinkSmile;
	}	
	
	@POST
	@Path("init-ouput-data-register")
	public void register(String conditionSetCode) {
		String companyId = AppContexts.user().companyId();
		// B
		int categoryId = 160;
		int delimiter = 1;
		int itemOutputName = 1;
		int autoExecution = 1;
		String conditionSetName = "SMILE_月別実績";
		int conditionOutputName = 1;
		int stringFormat = 0;
		Long version = null;
		String copyDestinationCode = null;
		boolean overWrite = false;
		boolean newMode = true;
		String destinationName = null;
		int standType = 1;
		
		List<StdOutItemOrderCommand> listStandardOutputItem = new ArrayList<>();
		StdOutItemOrderCommand itemEmpCd = new StdOutItemOrderCommand(companyId, "0001", conditionSetCode, 1);
		listStandardOutputItem.add(itemEmpCd);
		StdOutItemOrderCommand itemYear = new StdOutItemOrderCommand(companyId, "0002", conditionSetCode, 2);
		listStandardOutputItem.add(itemYear);
		StdOutItemOrderCommand itemComCd = new StdOutItemOrderCommand(companyId, "0003", conditionSetCode, 3);
		listStandardOutputItem.add(itemComCd);
		StdOutItemOrderCommand itemMonth = new StdOutItemOrderCommand(companyId, "0004", conditionSetCode, 4);
		listStandardOutputItem.add(itemMonth);
		StdOutItemOrderCommand itemWorkDay = new StdOutItemOrderCommand(companyId, "0101", conditionSetCode, 5);
		listStandardOutputItem.add(itemWorkDay);
		StdOutItemOrderCommand itemEbsenDay = new StdOutItemOrderCommand(companyId, "0102", conditionSetCode, 6);
		listStandardOutputItem.add(itemEbsenDay);
		StdOutItemOrderCommand itemOTHour = new StdOutItemOrderCommand(companyId, "0109", conditionSetCode, 7);
		listStandardOutputItem.add(itemOTHour);
		
		StdOutputCondSetCommand command = new StdOutputCondSetCommand(companyId, conditionSetCode, categoryId, delimiter, itemOutputName, 
																		autoExecution, conditionSetName, conditionOutputName, stringFormat, version, copyDestinationCode, 
																		overWrite, newMode, destinationName, standType, 1, null, listStandardOutputItem);
		this.registerStdOutputCondSetCommandHandler.handle(command);
		
		// C
		boolean isNewMode = true;
        
        List<CategoryItemCommand> empCatItem = new ArrayList<>();
        CategoryItemCommand empCd = new CategoryItemCommand(1, categoryId, null, 1);
        empCatItem.add(empCd);
		StdOutItemCommand outItemEmp = new StdOutItemCommand(isNewMode, companyId, "0001", conditionSetCode, "社員CD", 1,
				empCatItem, null, null, null, null, null, null, 1);
		this.addStdOutItemCommandHandler.handle(outItemEmp);
		
		List<CategoryItemCommand> yearCatItem = new ArrayList<>();
		CategoryItemCommand year = new CategoryItemCommand(3, categoryId, null, 1);
		yearCatItem.add(year);
		StdOutItemCommand  outItemYear = new StdOutItemCommand(isNewMode, companyId, "0002", conditionSetCode, "年", 0,
				yearCatItem, null, null, null, null, 
				new NumberDfsCommand(conditionSetCode, "0002", companyId, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, null, null, null, 0, 0), 
				null, 2);
		this.addStdOutItemCommandHandler.handle(outItemYear);
		
		List<CategoryItemCommand> comCatItem = new ArrayList<>();
		CategoryItemCommand comCd = new CategoryItemCommand(1, categoryId, null, 1);
		comCatItem.add(comCd);
		StdOutItemCommand  outItemCom = new StdOutItemCommand(isNewMode, companyId, "0003", conditionSetCode, "会社CD", 1, 
				comCatItem, null, null, null, null, null, null, 3);
		this.addStdOutItemCommandHandler.handle(outItemCom);
		
		List<CategoryItemCommand> monthCatItem = new ArrayList<>();
		CategoryItemCommand month = new CategoryItemCommand(3, categoryId, null, 1);
		monthCatItem.add(month);
		StdOutItemCommand outItemMonth = new StdOutItemCommand(isNewMode, companyId, "0004", conditionSetCode, "月", 0, 
				monthCatItem, null, null, null, null, 
				new NumberDfsCommand(conditionSetCode, "0004", companyId, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, null, null, null, 0, 0), 
				null, 4);
		this.addStdOutItemCommandHandler.handle(outItemMonth);
		
		List<CategoryItemCommand> numberWorkDayCatItem = new ArrayList<>();
		CategoryItemCommand numberWorkDay = new CategoryItemCommand(150, categoryId, null, 1);
		numberWorkDayCatItem.add(numberWorkDay);
		StdOutItemCommand outItemNumberWorkDay = new StdOutItemCommand(isNewMode, companyId, "0101", conditionSetCode, "出勤日数", 0,
				numberWorkDayCatItem, null, null, null, null, 
				new NumberDfsCommand(conditionSetCode, "0101", companyId, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, null, null, null, 0, 0), 
				null, 5);
		this.addStdOutItemCommandHandler.handle(outItemNumberWorkDay);
		
		List<CategoryItemCommand> numberAbsenDayCatItem = new ArrayList<>();
		CategoryItemCommand numberAbsenDay = new CategoryItemCommand(187, categoryId, null, 1);
		numberAbsenDayCatItem.add(numberAbsenDay);
		StdOutItemCommand outItemNumberAbsenDay = new StdOutItemCommand(isNewMode, companyId, "0102", conditionSetCode, "欠勤日数", 0, 
				numberAbsenDayCatItem, null, null, null, null, null, null, 6);
		this.addStdOutItemCommandHandler.handle(outItemNumberAbsenDay);
		
		List<CategoryItemCommand> otTimeCatItem = new ArrayList<>();
		CategoryItemCommand otTime = new CategoryItemCommand(33, categoryId, null, 1);
		otTimeCatItem.add(otTime);
		StdOutItemCommand outItemOtTimeCatItem = new StdOutItemCommand(isNewMode, companyId, "0109", conditionSetCode, "残業時間", 3, 
				otTimeCatItem, null, null, null, null, null, 
				new TimeDfsCommand(conditionSetCode, "0109", companyId, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, null, 1, null, null, 0),
				7);
		this.addStdOutItemCommandHandler.handle(outItemOtTimeCatItem);
		
		// J
		this.formatRep.remove(new ChacDataFmSet(0, companyId, 0, null, 0, 0, 0, null, null, 0, 0, null, null, null));
		this.settingDataCharRegisterService.handle(new ChacDataFmSetCommand(companyId, 0, null, 0, 0, 0, null, null, 0, 0, null, null, null, version));
		
		// K
		this.formatRep.remove(new DateFormatSet(0, companyId, 0, 0, null, null, 0));
		this.dateCmdHdl.handle(new DateFormatSettingCommand(companyId, 0, 0, null, null, 0));
		
		// I
		this.formatRep.remove(new NumberDataFmSet(0, companyId, 0, null, 0, 0, null, 0, null, 0, 0, null, 0, null, 0, 0, 0));
		this.numberCmdHdl.handle(new NumberDataFormatSettingCommand(companyId, 0, null, 0, 0, null, 0, null, 0, 0, null, 0, null, 1, 1, 0));
		
		// L
		this.formatRep.remove(new TimeDataFmSet(0, companyId, 0, 0, 0, null, 0, null, 0, 0, 0, null, 0, 0, 0, null, null, 0));
		this.performCmdHdl.handle(new AddPerformSettingByTimeCommand(companyId, 0, 0, 0, null, 0, null, 0, 2, 0, null, 0, 0, 0, null, null, 1));
		
		// M
		this.formatRep.remove(new InTimeDataFmSet(0, companyId, 0, null, 0, 0, null, 0, 0, null, 0, 0, 0, 0, null, 0, 0));
		this.performInCmdHdl.handle(new AddPerformSettingByInTimeCommand(companyId, 0, null, 0, 0, null, 0, 0, null, 0, 2, 0, 0, null, 0, 1));
		
		// G
		this.convertRep.removeByCom(companyId);
		this.convertRep.removeDetailByCom(companyId, "00001");
		List<CdConvertDetailCommand> convertDetailList = new ArrayList<>();
		CdConvertDetailCommand detail = new CdConvertDetailCommand("00001", "1", "テスト１", "1", null);
		convertDetailList.add(detail);
		OutputCodeConvertCommand convert = new OutputCodeConvertCommand("00001", "変換テスト", companyId, 0, null, convertDetailList);
		this.addHandler.handle(convert);
	}
	
	@POST
	@Path("init-link-out-set")
	public void registerLinkOutSet(String conditionSetCode) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		this.linkOutSetRep.delete(contractCode, companyId);
		
		ExternalOutputConditionCode outCondCd = new ExternalOutputConditionCode(conditionSetCode);
		SmileLinkageOutputSetting domain = new SmileLinkageOutputSetting(EnumAdaptor.valueOf(1, SmileCooperationOutputClassification.class), 
				EnumAdaptor.valueOf(0, SmileCooperationOutputClassification.class), 
				EnumAdaptor.valueOf(0, SmileCooperationOutputClassification.class), 
				Optional.ofNullable(outCondCd));
		this.linkOutSetRep.insert(domain);
	}
}
