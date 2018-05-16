package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.AgreeCondOtCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.AgreeConditionErrorCommand;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AlarmChkCondAgree36;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckCon;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RegisterAlarmCheckCondtionByCategoryCommandHandler
		extends CommandHandler<AlarmCheckConditionByCategoryCommand> {

	@Inject
	private AlarmCheckConditionByCategoryRepository conditionRepo;
	
	@Inject
	private WorkRecordExtraConAdapter workRecordExtraConRepo;
	
	@Inject
	private FixedConWorkRecordAdapter fixedConWorkRecordRepo;
	//monthly
	@Inject
	private FixedExtraMonFunAdapter fixedExtraMonFunAdapter;
	
	@Inject
	private IAgreeConditionErrorRepository conErrRep;

	@Inject
	private IAgreeCondOtRepository otRep;
	
	@Override
	protected void handle(CommandHandlerContext<AlarmCheckConditionByCategoryCommand> context) {
		AlarmCheckConditionByCategoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (command.getAction() == 0
				&& conditionRepo.isCodeExist(companyId, command.getCategory(), command.getCode())) {
			throw new BusinessException("Msg_3");
		}

		// msg_832 extractcondition > 0

		Optional<AlarmCheckConditionByCategory> domainOpt = conditionRepo.find(companyId, command.getCategory(),
				command.getCode());
		if (domainOpt.isPresent()) {
			// update
			AlarmCheckConditionByCategory domain = domainOpt.get();
			AlarmCheckTargetCondition targetConditionValue = new AlarmCheckTargetCondition("",
					command.getTargetCondition().isFilterByBusinessType(),
					command.getTargetCondition().isFilterByJobTitle(),
					command.getTargetCondition().isFilterByEmployment(),
					command.getTargetCondition().isFilterByClassification(),
					command.getTargetCondition().getTargetBusinessType(),
					command.getTargetCondition().getTargetJobTitle(),
					command.getTargetCondition().getTargetEmployment(),
					command.getTargetCondition().getTargetClassification());
			
			ExtractionCondition extractionCondition = null;
			AlarmCategory category = AlarmCategory.values()[command.getCategory()];
			switch (category) {
			case DAILY:
				//update WorkRecordExtractCondition
				DailyAlarmCondition dailyAlramCondition = (DailyAlarmCondition) domain.getExtractionCondition();
				List<String> listErrorAlarmId = this.workRecordExtraConRepo.checkUpdateListErAl(
						dailyAlramCondition.getExtractConditionWorkRecord(),
						command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork());
				
				extractionCondition = command.getDailyAlarmCheckCondition() == null ? null
						: new DailyAlarmCondition(IdentifierUtil.randomUniqueId(),
								command.getDailyAlarmCheckCondition().getConditionToExtractDaily(),
								command.getDailyAlarmCheckCondition().isAddApplication(),
								listErrorAlarmId,
								command.getDailyAlarmCheckCondition().getListErrorAlarmCode());

				// update FixedWorkRecordExtractCondition
				for(FixedConWorkRecordAdapterDto fixedConWorkRecordAdapterDto : command.getDailyAlarmCheckCondition().getListFixedExtractConditionWorkRecord()) {
					if (fixedConWorkRecordAdapterDto.getDailyAlarmConID() == null || fixedConWorkRecordAdapterDto.getDailyAlarmConID().equals("")) {
						fixedConWorkRecordAdapterDto.setDailyAlarmConID(dailyAlramCondition.getDailyAlarmConID());
						this.fixedConWorkRecordRepo.addFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
					} else {
						this.fixedConWorkRecordRepo.updateFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
					}
				}
				break;
			case MONTHLY:
				MonAlarmCheckCon monAlarmCheckCon = (MonAlarmCheckCon) domain.getExtractionCondition() ;
				//TODO: 
		
				//update list fixedExtraMonFun
				for(FixedExtraMonFunImport fixedExtraMonFun : command.getMonAlarmCheckCon().getListFixExtraMon()) {
					if(fixedExtraMonFun.getMonAlarmCheckID() == null || fixedExtraMonFun.getMonAlarmCheckID().equals("") ) {
						fixedExtraMonFun.setMonAlarmCheckID(monAlarmCheckCon.getMonAlarmCheckConID());
						this.fixedExtraMonFunAdapter.addFixedExtraMon(fixedExtraMonFun);
					}else {
						this.fixedExtraMonFunAdapter.updateFixedExtraMon(fixedExtraMonFun);
						
					}
				}
				break;
			case SCHEDULE_4WEEK:
				extractionCondition = command.getSchedule4WeekAlarmCheckCondition() == null ? null
						: new AlarmCheckCondition4W4D(IdentifierUtil.randomUniqueId(),
								command.getSchedule4WeekAlarmCheckCondition().getSchedule4WeekCheckCondition());
				break;
			case AGREEMENT: 
				// update agree condtion error
				List<AgreeConditionError> listError = new ArrayList<>();
				listError = command.getCondAgree36().getListCondError().stream().map(x -> 
					AgreeConditionError.createFromJavaType(x.getId(), x.getCompanyId(), x.getCategory(),
															x.getCode(), x.getUseAtr(), x.getPeriod(), 
															x.getErrorAlarm(), x.getMessageDisp())
				).collect(Collectors.toList());
				for(AgreeConditionError item : listError){
					if(item.getId() != null){
						Optional<AgreeConditionError> oldOption = conErrRep.findById(item.getId(), item.getCode().v(), 
																						companyId, item.getCategory().value);
						if(oldOption.isPresent()){
							conErrRep.update(item);
						}else{
							item.setId(UUID.randomUUID().toString());
							conErrRep.insert(item);
						}
					}else{
						item.setId(UUID.randomUUID().toString());
						conErrRep.insert(item);
					}
				}
				// update agree conditon ot
				List<AgreeCondOt> listOt = new ArrayList<>();
				listOt = command.getCondAgree36().getListCondOt().stream().map(x -> 
						AgreeCondOt.createFromJavaType(x.getId(), x.getCompanyId(), 
								x.getCategory(), x.getCode(), 
								x.getNo(), x.getOt36(), x.getExcessNum(), x.getMessageDisp())
				).collect(Collectors.toList());
				if(listOt.isEmpty()){
					throw new BusinessException("Msg_832"); 
				}
				if(listOt.size() > 10){
					throw new BusinessException("Msg_1242"); 
				}
				// find 1 list agreeconditionot from DB, if item in DB isn't existed in UI => delete 
				List<AgreeCondOt> agreeConditionDbList = otRep.findAll(command.getCode(), command.getCategory());
				for (AgreeCondOt itemDb : agreeConditionDbList) {
					Boolean exists = listOt.stream().anyMatch(x -> x.getId().equals(itemDb.getId()));
					if (!exists) {
						otRep.deleteId(command.getCode(), command.getCategory(), itemDb.getId(), itemDb.getNo());
					}
				}
				// update/insert listOt
				for(AgreeCondOt obj : listOt){
					if(!StringUtil.isNullOrEmpty(obj.getId(), true)){
						Optional<AgreeCondOt> oldOption = otRep.findById(obj.getId(), obj.getNo(), obj.getCode().v(),
																			companyId, obj.getCategory().value);
						if(oldOption.isPresent()){
							otRep.update(obj);
						}else{
							obj.setId(UUID.randomUUID().toString());
							otRep.insert(obj);
						}
					}else{
						obj.setId(UUID.randomUUID().toString());
						otRep.insert(obj);
					}
				}
				break;
			default:
				break;
			}
			domain.changeState(command.getName(), command.getAvailableRoles(), targetConditionValue, extractionCondition);
			
			
			conditionRepo.update(domain);
		} else {
			// add
			ExtractionCondition extractionCondition = null;
			AlarmCategory category = AlarmCategory.values()[command.getCategory()];
			switch (category) {
			case DAILY:
				String dailyAlarmId = IdentifierUtil.randomUniqueId();
				//add WorkRecordExtractCondition
				List<String> listErrorAlarmId = this.workRecordExtraConRepo.addNewListErAl(command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork());
				
				extractionCondition = command.getDailyAlarmCheckCondition() == null ? null
						: new DailyAlarmCondition(dailyAlarmId,
								command.getDailyAlarmCheckCondition().getConditionToExtractDaily(),
								command.getDailyAlarmCheckCondition().isAddApplication(),
								listErrorAlarmId,
								command.getDailyAlarmCheckCondition().getListErrorAlarmCode()
								);
				
				//add FixedWorkRecordExtractCondition
				for(FixedConWorkRecordAdapterDto fixedConWorkRecordAdapterDto : command.getDailyAlarmCheckCondition().getListFixedExtractConditionWorkRecord()) {
					fixedConWorkRecordAdapterDto.setDailyAlarmConID(dailyAlarmId);
					this.fixedConWorkRecordRepo.addFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
				}
				break;
			case MONTHLY:
				String monAlarmCheckConID = IdentifierUtil.randomUniqueId();
				
				extractionCondition = command.getMonAlarmCheckCon() == null ? null
						: new MonAlarmCheckCon(monAlarmCheckConID);
				//add list fixedExtraMonFun
				for(FixedExtraMonFunImport fixedExtraMonFun : command.getMonAlarmCheckCon().getListFixExtraMon()) {
					fixedExtraMonFun.setMonAlarmCheckID(monAlarmCheckConID);
					this.fixedExtraMonFunAdapter.addFixedExtraMon(fixedExtraMonFun);
				}
				
				
				break;
			case SCHEDULE_4WEEK:
				extractionCondition = command.getSchedule4WeekAlarmCheckCondition() == null ? null
						: new AlarmCheckCondition4W4D(IdentifierUtil.randomUniqueId(),
								command.getSchedule4WeekAlarmCheckCondition().getSchedule4WeekCheckCondition());
				break;
			default:
				break;
			}
			
			AlarmCheckConditionByCategory domain = new AlarmCheckConditionByCategory(companyId, command.getCategory(),
					command.getCode(), command.getName(),
					new AlarmCheckTargetCondition(IdentifierUtil.randomUniqueId(),
							command.getTargetCondition().isFilterByBusinessType(),
							command.getTargetCondition().isFilterByJobTitle(),
							command.getTargetCondition().isFilterByEmployment(),
							command.getTargetCondition().isFilterByClassification(),
							command.getTargetCondition().getTargetBusinessType(),
							command.getTargetCondition().getTargetJobTitle(),
							command.getTargetCondition().getTargetEmployment(),
							command.getTargetCondition().getTargetClassification()),
					command.getAvailableRoles(), extractionCondition, new AlarmChkCondAgree36(command.getCondAgree36().getListCondError().stream().map(x -> AgreeConditionErrorCommand.toDomain(x)).collect(Collectors.toList()), 
							command.getCondAgree36().getListCondOt().stream().map(c -> AgreeCondOtCommand.toDomain(c)).collect(Collectors.toList())));
			
			conditionRepo.add(domain);
		}
	}

}
