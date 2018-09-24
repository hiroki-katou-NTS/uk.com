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
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.AgreeCondOtCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.AgreeConditionErrorCommand;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.AlarmCheckConditionByCategoryFinder;
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
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckConEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCond;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCondEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;
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
	
	@Inject
	private AlarmCheckConditionByCategoryFinder alarmCheckConByCategoryFinder;
	
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
//				boolean checkErrorFixed = false;
//				for(FixedExtraMonFunImport fixedExtraMonFun : command.getMonAlarmCheckCon().getListFixExtraMon()) {
//					if(fixedExtraMonFun.isUseAtr()) {
//						checkErrorFixed = true;
//						break;
//					}
//				}
//				boolean checkArbExtraCon = false;
//				if(command.getMonAlarmCheckCon().getArbExtraCon().size() >0) {
//					for(ExtraResultMonthlyDomainEventDto dto : command.getMonAlarmCheckCon().getArbExtraCon() ) {
//						if(dto.isUseAtr()) {
//							checkArbExtraCon = true;
//							break;
//						}
//					}
//				}
//				
//				
//				if(checkErrorFixed == false && checkArbExtraCon == false) {
//					throw new BusinessException("Msg_832"); 
//				}
				
				//update list mon
				List<String> listEralCheckIDOld = alarmCheckConByCategoryFinder.getDataByCode(command.getCategory(), command.getCode())
						.getMonAlarmCheckConDto().getListEralCheckIDOld();
				for(int i = 0;i<command.getMonAlarmCheckCon().getArbExtraCon().size();i++) {
					if(command.getMonAlarmCheckCon().getArbExtraCon().get(i).getErrorAlarmCheckID().equals("")) {
						command.getMonAlarmCheckCon().getArbExtraCon().get(i).setErrorAlarmCheckID(IdentifierUtil.randomUniqueId());
					}
				}
				
				extractionCondition = command.getMonAlarmCheckCon() == null ? null
						: new MonAlarmCheckCon(IdentifierUtil.randomUniqueId(),
								command.getMonAlarmCheckCon().getArbExtraCon().stream().map(c->c.getErrorAlarmCheckID()).collect(Collectors.toList())
								);					
				MonAlarmCheckConEvent event = new MonAlarmCheckConEvent(monAlarmCheckCon.getMonAlarmCheckConID(),true,false,false,command.getMonAlarmCheckCon().getArbExtraCon(),listEralCheckIDOld);
				event.toBePublished();
		
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
				
				
			case MULTIPLE_MONTH:
				MulMonAlarmCond mulMonAlarmCond = (MulMonAlarmCond) domain.getExtractionCondition() ;
				
//				boolean checkMulMonConds = false;
				 List<MulMonCheckCondDomainEventDto> listMulMonCheckConds = 
					      command.getMulMonCheckCond().getListMulMonCheckConds();
//				 if(!CollectionUtil.isEmpty(listMulMonCheckConds)) {
//					for(MulMonCheckCondDomainEventDto dto : listMulMonCheckConds) {
//						if(dto.isUseAtr()) {
//							checkMulMonConds = true;
//							break;
//						}
//					}
//				}
//				
//				
//				if(checkMulMonConds == false) {
//					throw new BusinessException("Msg_832"); 
//				}
				
				//update list multiple month
				List<String> listEralCheckMulIDOld = alarmCheckConByCategoryFinder
						.getDataByCode(command.getCategory(), command.getCode()).getMulMonAlarmCheckConDto().getErrorAlarmCheckIDOlds();
				for (int i = 0; i < listMulMonCheckConds.size(); i++) {
					MulMonCheckCondDomainEventDto dto = listMulMonCheckConds.get(i);
					if (dto.getErrorAlarmCheckID().equals("")) {
						dto.setErrorAlarmCheckID(IdentifierUtil.randomUniqueId());
					}
				}
				
				extractionCondition = command.getMulMonCheckCond() == null ? null
						: new MulMonAlarmCond(IdentifierUtil.randomUniqueId(),
								listMulMonCheckConds.stream().map(c->c.getErrorAlarmCheckID()).collect(Collectors.toList())
								);					
				MulMonAlarmCondEvent eventUpdate = new MulMonAlarmCondEvent(mulMonAlarmCond.getMulMonAlarmCondID(),true,false,false,
						listMulMonCheckConds,listEralCheckMulIDOld);
				eventUpdate.toBePublished();

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
				List<Integer> useList = listError.stream().map(x -> x.getUseAtr().value).collect(Collectors.toList());
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
				boolean check = useList.contains(1);
				if(!check && listOt.isEmpty()){
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
//				boolean checkErrorFixed = false;
//				for(FixedExtraMonFunImport fixedExtraMonFun : command.getMonAlarmCheckCon().getListFixExtraMon()) {
//					if(fixedExtraMonFun.isUseAtr()) {
//						checkErrorFixed = true;
//						break;
//					}
//				}
//				
//				boolean checkArbExtraCon = false;
//				if(command.getMonAlarmCheckCon().getArbExtraCon().size() >0) {
//					for(ExtraResultMonthlyDomainEventDto dto : command.getMonAlarmCheckCon().getArbExtraCon() ) {
//						if(dto.isUseAtr()) {
//							checkArbExtraCon = true;
//							break;
//						}
//					}
//				}
//				if(checkErrorFixed == false && checkArbExtraCon == false) {
//					throw new BusinessException("Msg_832"); 
//				}
				
				String monAlarmCheckConID = IdentifierUtil.randomUniqueId();
				for(ExtraResultMonthlyDomainEventDto item:command.getMonAlarmCheckCon().getArbExtraCon()) {
					item.setErrorAlarmCheckID(IdentifierUtil.randomUniqueId());
				}
				extractionCondition = command.getMonAlarmCheckCon() == null ? null
						: new MonAlarmCheckCon(monAlarmCheckConID,
								command.getMonAlarmCheckCon().getArbExtraCon().stream().map(c->c.getErrorAlarmCheckID()).collect(Collectors.toList())
								);
				//add list mon
				List<String> listEralCheckIDOld = new ArrayList<>();
				MonAlarmCheckConEvent event = new MonAlarmCheckConEvent(monAlarmCheckConID,false,true,false,command.getMonAlarmCheckCon().getArbExtraCon(),listEralCheckIDOld);
				event.toBePublished();
				//add list fixedExtraMonFun
				for(FixedExtraMonFunImport fixedExtraMonFun : command.getMonAlarmCheckCon().getListFixExtraMon()) {
					fixedExtraMonFun.setMonAlarmCheckID(monAlarmCheckConID);
					this.fixedExtraMonFunAdapter.addFixedExtraMon(fixedExtraMonFun);
				}
				
				break;
				
			case MULTIPLE_MONTH:
//				boolean checkMulMonConds = false;
				 List<MulMonCheckCondDomainEventDto> listMulMonCheckConds = 
					      command.getMulMonCheckCond().getListMulMonCheckConds();
//				 if(!CollectionUtil.isEmpty(listMulMonCheckConds)) {
//					for(MulMonCheckCondDomainEventDto item : listMulMonCheckConds) {
//						if(item.isUseAtr()) {
//							checkMulMonConds = true;
//							break;
//						}
//					}
//				}
//				if(checkMulMonConds == false) {
//					throw new BusinessException("Msg_832"); 
//				}
				
				
				String mulMonAlarmCondID = IdentifierUtil.randomUniqueId();
				for (MulMonCheckCondDomainEventDto item : listMulMonCheckConds) {
					item.setErrorAlarmCheckID(IdentifierUtil.randomUniqueId());
				}
				
				extractionCondition = command.getMulMonCheckCond() == null ? null
						: new MulMonAlarmCond(mulMonAlarmCondID, listMulMonCheckConds
								.stream().map(c->c.getErrorAlarmCheckID()).collect(Collectors.toList()));
				
				//add list multiple months
				List<String> listEralCheckMulIDOld = new ArrayList<>();
				MulMonAlarmCondEvent mulMonAlarmCondEvent = new MulMonAlarmCondEvent(mulMonAlarmCondID,false,true,false,
						listMulMonCheckConds,listEralCheckMulIDOld);
				mulMonAlarmCondEvent.toBePublished();
				
				break;
			case SCHEDULE_4WEEK:
				extractionCondition = command.getSchedule4WeekAlarmCheckCondition() == null ? null
						: new AlarmCheckCondition4W4D(IdentifierUtil.randomUniqueId(),
								command.getSchedule4WeekAlarmCheckCondition().getSchedule4WeekCheckCondition());
				break;
				
			case AGREEMENT:
				// update agree conditon ot
				List<AgreeCondOtCommand> listOt = new ArrayList<>();
				List<AgreeConditionErrorCommand> listError = new ArrayList<>();
				listError = command.getCondAgree36().getListCondError();
				listOt = command.getCondAgree36().getListCondOt();
				List<Integer> useList = listError.stream().map(x -> x.getUseAtr()).collect(Collectors.toList());
				boolean check = useList.contains(1);
				if(!check && listOt.isEmpty()){
					throw new BusinessException("Msg_832"); 
				}
				if(listOt.size() > 10){
					throw new BusinessException("Msg_1242"); 
				}
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
