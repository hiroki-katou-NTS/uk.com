package nts.uk.ctx.hr.shared.app.databeforereflecting.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.RetirementCategory;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.RetirementInformation;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.service.RetirementInformationService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegisterNewEmpCommandHandler extends CommandHandler<DataBeforeReflectCommand> {

	@Inject
	private RetirementInformationService retirementInformationService;
	
	public static final String TIME_DAY_START = " 00:00:00";
	
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	
	
	// 2.退職者を新規登録する(Đăng ký mới người nghỉ hưu)
	@Override
	protected void handle(CommandHandlerContext<DataBeforeReflectCommand> context) {
		DataBeforeReflectCommand command = context.getCommand();
		RetirementInformation domainObj = convertDataToDomainObj(command);
		retirementInformationService.addRetireInformation(domainObj);
	}
	
	private RetirementInformation convertDataToDomainObj(DataBeforeReflectCommand command){
		
		RetirementInformation domainObj = new RetirementInformation();
		domainObj.historyId = IdentifierUtil.randomUniqueId();
		domainObj.contractCode =  AppContexts.user().contractCode();
		domainObj.companyId =  AppContexts.user().companyId();
		domainObj.companyCode =  AppContexts.user().companyCode();
		domainObj.workId =  command.workId;
		domainObj.workName =  command.workName;
		domainObj.notificationCategory =  command.notificationCategory;
		domainObj.inputDate =  command.inputDate;
		domainObj.pendingFlag =  command.pendingFlag;
		domainObj.status =  command.status;
		domainObj.histId_Refer =  command.histId_Refer;
		domainObj.releaseDate =  GeneralDateTime.fromString(command.releaseDate+ TIME_DAY_START, DATE_TIME_FORMAT);
		domainObj.pId =  command.pId;
		domainObj.sId =  command.sId;
		domainObj.scd =  command.scd;
		domainObj.personName =  command.employeeName;
		domainObj.retirementDate =  GeneralDateTime.fromString(command.retirementDate+ TIME_DAY_START, DATE_TIME_FORMAT);
		domainObj.retirementCategory = EnumAdaptor.valueOf(command.retirementType, RetirementCategory.class);
		
		domainObj.retirementRemarks =  command.retirementRemarks;
		domainObj.retirementReasonVal =  command.retirementReasonVal;
		domainObj.dismissalNoticeDate      = (command.dismissalNoticeDate == "" || command.dismissalNoticeDate == null) ? null : GeneralDate.fromString(command.dismissalNoticeDate, "yyyy/MM/dd");
		domainObj.dismissalNoticeDateAllow = (command.dismissalNoticeDateAllow == "" || command.dismissalNoticeDateAllow == null) ? null : GeneralDate.fromString(command.dismissalNoticeDateAllow, "yyyy/MM/dd");
		domainObj.reaAndProForDis =  command.reaAndProForDis;
		domainObj.naturalUnaReasons_1 =  command.naturalUnaReasons_1;
		domainObj.naturalUnaReasons_2 =  command.businessReduction_2;
		domainObj.naturalUnaReasons_3 =  command.seriousViolationsOrder_3;
		domainObj.naturalUnaReasons_4 =  command.unauthorizedConduct_4;
		domainObj.naturalUnaReasons_5 =  command.leaveConsiderableTime_5;
		domainObj.naturalUnaReasons_6 =  command.other_6;
		domainObj.naturalUnaReasons_1Val =  command.naturalUnaReasons_1Val;
		domainObj.naturalUnaReasons_2Val =  command.businessReduction_2Val;
		domainObj.naturalUnaReasons_3Val =  command.seriousViolationsOrder_3Val;
		domainObj.naturalUnaReasons_4Val =  command.unauthorizedConduct_4Val;
		domainObj.naturalUnaReasons_5Val =  command.leaveConsiderableTime_5Val;
		domainObj.naturalUnaReasons_6Val =  command.other_6Val;
		
		switch (command.selectedCode_Reason1) {
		case 1:
			domainObj.retirementReasonCtgCode1 = "1";
			domainObj.retirementReasonCtgName1 = "自己都合による退職";
			break;
		case 2:
			domainObj.retirementReasonCtgCode1 = "2";
			domainObj.retirementReasonCtgName1 = "定年による退職";
			break;
			
		case 3:
			domainObj.retirementReasonCtgCode1 = "3";
			domainObj.retirementReasonCtgName1 = "会社都合による解雇";
			break;
		}
		
		switch (command.selectedCode_Reason2) {
		case 1:
			domainObj.retirementReasonCtgCode2 = "1";
			domainObj.retirementReasonCtgName2 = "結婚";
			break;
		case 2:
			domainObj.retirementReasonCtgCode2 = "2";
			domainObj.retirementReasonCtgName2 = "上司と合わない";
			break;
			
		case 3:
			domainObj.retirementReasonCtgCode2 = "3";
			domainObj.retirementReasonCtgName2 = "やる気がなくなった";
			break;
		case 4:
			domainObj.retirementReasonCtgCode2 = "4";
			domainObj.retirementReasonCtgName2 = "会社の業績不振";
			break;
		case 5:
			domainObj.retirementReasonCtgCode2 = "5";
			domainObj.retirementReasonCtgName2 = "その他";
			break;
		}
		return domainObj;
	}

	
}
