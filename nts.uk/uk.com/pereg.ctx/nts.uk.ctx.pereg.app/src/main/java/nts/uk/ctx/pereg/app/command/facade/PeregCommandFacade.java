package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.processor.ItemDefFinder;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;
import nts.uk.ctx.sys.log.app.command.pereg.KeySetCorrectionLog;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.CategoryCorrectionTarget;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter.PersonCorrectionTarget;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ReviseInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.pereg.app.ItemLog;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregCommandHandlerCollector;
import nts.uk.shr.pereg.app.command.PeregDeleteCommand;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
import nts.uk.shr.pereg.app.command.PeregInputContainer;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefDeleteCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefDeleteCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommandHandler;
import nts.uk.shr.pereg.app.find.PeregQuery;

@ApplicationScoped
public class PeregCommandFacade {


	@Inject
	private PeregCommandHandlerCollector handlerCollector;

	/** Command handlers to add */
	private Map<String, PeregAddCommandHandler<?>> addHandlers;

	/** Command handlers to update */
	private Map<String, PeregUpdateCommandHandler<?>> updateHandlers;

	/** Command handlers to delete */
	private Map<String, PeregDeleteCommandHandler<?>> deleteHandlers;

	/** this handles command to add data defined by user. */
	@Inject
	private PeregUserDefAddCommandHandler userDefAdd;

	/** this handles command to update data defined by user. */
	@Inject
	private PeregUserDefUpdateCommandHandler userDefUpdate;

	/** this handles command to delete data defined by user. */
	@Inject
	private PeregUserDefDeleteCommandHandler userDefDelete;

	@Inject
	private ItemDefFinder itemDefFinder;
	
	@Inject
	private GetUserByEmpFinder userFinder;
	
	private final static String nameEndate = "終了日";
	/* employeeCode, stardCardNo */
	private final static List<String> specialItemCode = Arrays.asList("IS00001","IS00779");
	/*  target Key: null */
	private final static List<String> singleCategories = Arrays.asList("CS00002", "CS00022", "CS00023", "CS00024", "CS00035", "CS00036"); 
	
	private static final List<String> historyCategoryCodeList = Arrays.asList("CS00003", "CS00004", "CS00014",
			"CS00016", "CS00017", "CS00018", "CS00019", "CS00020", "CS00021");
	/*  target Key : code */
	private static final Map<String, String> specialItemCodes = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
		 	put("CS00001", "IS00001");
			put("CS00025", "1");
			put("CS00026", "2");
			put("CS00027", "3");
			put("CS00028", "4");
			put("CS00029", "5");
			put("CS00030", "6");
			put("CS00031", "7");
			put("CS00032", "8");
			put("CS00033", "9");
			put("CS00034", "10");
			put("CS00049", "11");
			put("CS00050", "12");
			put("CS00051", "13");
			put("CS00052", "14");
			put("CS00053", "15");
			put("CS00054", "16");
			put("CS00055", "17");
			put("CS00056", "18");
			put("CS00057", "19");
			put("CS00058", "20");
		}
	};

	private static final Map<String, DatePeriodSet> datePeriodCode = new HashMap<String, DatePeriodSet>() {
		private static final long serialVersionUID = 1L;
		{
			put("CS00003", new DatePeriodSet("IS00020","IS00021"));
			// 分類１
			put("CS00004", new DatePeriodSet("IS00026", "IS00027"));
			// 雇用
			put("CS00014", new DatePeriodSet("IS00066", "IS00067"));
			// 職位本務
			put("CS00016", new DatePeriodSet("IS00077", "IS00078"));
			// 職場
			put("CS00017", new DatePeriodSet("IS00082", "IS00083"));
			// 休職休業
			put("CS00018", new DatePeriodSet("IS00087", "IS00088"));
			// 短時間勤務
			put("CS00019", new DatePeriodSet("IS00102", "IS00103"));
			// 労働条件
			put("CS00020", new DatePeriodSet("IS00119", "IS00120"));
			// 勤務種別
			put("CS00021", new DatePeriodSet("IS00255", "IS00256"));

		}

	};

	/**
	 * Initializes.
	 */
	public void init(@Observes @Initialized(ApplicationScoped.class) Object event) {

		this.addHandlers = this.handlerCollector.collectAddHandlers().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCd(), h -> h));

		this.updateHandlers = this.handlerCollector.collectUpdateHandlers().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCd(), h -> h));

		this.deleteHandlers = this.handlerCollector.collectDeleteHandlers().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCd(), h -> h));

	}

	/**
	 * hàm này viết cho cps001
	 * Handles add commands.
	 * 
	 * @param container
	 *            inputs
	 */
	@Transactional
	public String add(PeregInputContainer container) {
		
		String result = addNonTransaction(container, false);
		
		return result;
	}
	
	private void updateInputForAdd(List<ItemsByCategory> inputs) {
		
		List<ItemLog> fullItemInfos = new ArrayList<>();
 		// Add item invisible to list
		for (ItemsByCategory itemByCategory : inputs) {
			
			List<ItemValue> items = itemByCategory.getItems();
			
			items.stream().forEach(c ->{
					fullItemInfos.add(new ItemLog(c.definitionId(), 
									c.itemCode(), 
									c.itemName(), 
									c.type(), 
									null, null,
									c.stringValue() , c.viewValue() == null ? c.stringValue() : c.viewValue()));
				
			});
			
			itemByCategory.setItemLogs(fullItemInfos);
		}
		
	}
	private void  setParamsForCPS001(String employeeId, boolean isAdd, List<ItemsByCategory> inputs) {
		
		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(employeeId));
		
		UserAuthDto user = new UserAuthDto("", "", "", employeeId, "", "");
		
		if(userAuth.size() > 0) {
			
			 user = userAuth.get(0);
			 
		}

		PersonCorrectionTarget target  = null;
		
		if(isAdd == true) {
			
			target = new PersonCorrectionTarget(
					user.getUserID(),
					employeeId, 
					user.getUserName(),
				    PersonInfoProcessAttr.ADD, null);
			
		}else {
			
			target = new PersonCorrectionTarget(
					user.getUserID(),
					employeeId, 
					user.getUserName(),
				    PersonInfoProcessAttr.UPDATE, null);
		}
		
		
		if(target != null) {
			
			// set correction log
			PersonCorrectionLogParameter correction = new PersonCorrectionLogParameter(Arrays.asList(target));
			
			DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.PERSON_CORRECTION_LOG.value), correction);
			
			List<CategoryCorrectionTarget> ctgTargets = new ArrayList<>();
			
			String stringKey = null;

			for (ItemsByCategory input : inputs) {
				
				List<PersonCorrectionItemInfo> lstItemInfo = new ArrayList<>();
				
				DatePeriodSet itemCode = new DatePeriodSet(null, null);
				
				if (historyCategoryCodeList.contains(input.getCategoryCd())) {
					
					itemCode = datePeriodCode.get(input.getCategoryCd());
					
				}
				
				ReviseInfo reviseInfo = null;
				
				List<ItemLog>  itemLogs = input.getItemLogs() == null? new ArrayList<>(): input.getItemLogs();
				
				for (ItemLog item : itemLogs) {
					if(specialItemCode.contains(item.getItemCode()) || item.getItemCode().equals(itemCode.getStartCode())) {
						
						stringKey = item.getValueAfter();
						// nếu startDate newValue != afterValue;  
						if(isAdd == true) {
							 reviseInfo = new ReviseInfo(nameEndate, Optional.ofNullable(GeneralDate.fromString(item.getValueAfter(), "yyyy/MM/dd").addDays(-1)), Optional.empty(), Optional.empty());
						} else {
							if(!item.getValueAfter().equals(item.getValueBefore())){
							       reviseInfo = new ReviseInfo(nameEndate, Optional.ofNullable(GeneralDate.fromString(item.getValueAfter(), "yyyy/MM/dd").addDays(-1)), Optional.empty(), Optional.empty());
							}	
						}
					}
					if(isAdd == false) {
						if (!item.getValueAfter().equals(item.getValueBefore())) {
							
							lstItemInfo.add(new PersonCorrectionItemInfo(item.getItemId(), item.getItemName(),
									item.getValueBefore(), item.getContentBefore(), item.getValueAfter(), item.getContentAfter(), item.getType()));
						}
					} else {
						
						if(item.getValueAfter() == null && item.getValueBefore() != null) {
							
							lstItemInfo.add(new PersonCorrectionItemInfo(item.getItemId(), item.getItemName(),
									item.getValueBefore(), item.getContentBefore(), item.getValueAfter(), item.getContentAfter(), item.getType()));
						}
						
					}

				}
				
				CategoryType ctgType = EnumAdaptor.valueOf(input.getCategoryType(), CategoryType.class);
				
				//Add category correction data
				CategoryCorrectionTarget ctgTarget = null;
				
				if(isAdd == true) {
					
					ctgTarget = setCategoryTarget( ctgType,  ctgTarget,  input,  lstItemInfo,  reviseInfo,  stringKey, InfoOperateAttr.ADD);
					
				} else {
					
					ctgTarget = setCategoryTarget( ctgType,  ctgTarget,  input,  lstItemInfo,  reviseInfo,  stringKey, InfoOperateAttr.UPDATE);
				}
				if(ctgTarget != null) {
					ctgTargets.add(ctgTarget);
				}
			}
			
			PersonCategoryCorrectionLogParameter personCtg = new PersonCategoryCorrectionLogParameter(ctgTargets);
			
			DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.CATEGORY_CORRECTION_LOG.value), personCtg);					
		}
		
	}
	
	private CategoryCorrectionTarget setCategoryTarget(CategoryType ctgType, CategoryCorrectionTarget ctgTarget, ItemsByCategory input, List<PersonCorrectionItemInfo> lstItemInfo, ReviseInfo reviseInfo, String stringKey, InfoOperateAttr infoOperateAttr) {
	
		switch (ctgType) {
		
		case SINGLEINFO:
			
			if (singleCategories.contains(input.getCategoryCd())) {
				
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), infoOperateAttr,
						lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null, null), reviseInfo == null? Optional.empty(): Optional.of(reviseInfo));
				
			} else {
				
				String code = specialItemCodes.get(input.getCategoryCd());
				
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), infoOperateAttr,
						lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null,
						code.equals(specialItemCode.get(0)) == true ? stringKey : code), reviseInfo == null? Optional.empty(): Optional.of(reviseInfo));
			
			}
			
			return ctgTarget;
			
		case MULTIINFO:
			
			ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), infoOperateAttr, lstItemInfo, 
				new TargetDataKey(CalendarKeyType.NONE, null, stringKey),  reviseInfo == null? Optional.empty(): Optional.of(reviseInfo));
			return ctgTarget;
			
		case CONTINUOUSHISTORY:
		case NODUPLICATEHISTORY:
		case DUPLICATEHISTORY:
			
			ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), infoOperateAttr, lstItemInfo, 
				TargetDataKey.of(GeneralDate.fromString(stringKey, "yyyy/MM/dd")), reviseInfo == null? Optional.empty(): Optional.of(reviseInfo));
			return ctgTarget;
			
		default:
			return null;
		}
		
	}
	
	@Transactional
	public String addForCPS002(PeregInputContainer container) {
		String result = addNonTransaction(container, true);
		return result;
	}
	
	private String addNonTransaction(PeregInputContainer container, boolean isCps002) {
		
	
		// Filter input category
		List<ItemsByCategory> addInputs = container.getInputs().stream()
				.filter(p -> StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());
		
		List<String> recordIds = new ArrayList<String>();
		String personId = container.getPersonId();
		String employeeId = container.getEmployeeId();
		if(isCps002 == false) {
			if(addInputs.size() > 0) {
				DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
				updateInputForAdd(addInputs);
				setParamsForCPS001(employeeId, true, addInputs);
				DataCorrectionContext.transactionFinishing();
			}
		}
		addInputs.forEach(itemsByCategory -> {
			
			val handler = this.addHandlers.get(itemsByCategory.getCategoryCd());
			// In case of optional category fix category doesn't exist
			String recordId = null;
			if (handler != null && itemsByCategory.isHaveSomeSystemItems()) {
				val result = handler.handlePeregCommand(personId, employeeId, itemsByCategory);
				// pass new record ID that was generated by add domain command
				recordId = result.getAddedRecordId();
			}
			
			// pass new record ID that was generated by add domain command
			// handler
			val commandForUserDef = new PeregUserDefAddCommand(personId, employeeId, recordId, itemsByCategory);
			this.userDefAdd.handle(commandForUserDef);

			// Keep record id to focus in UI
			recordIds.add(recordId);
		});

		if (recordIds.size() == 1) {
			return recordIds.get(0);
		}
		
		return null;
	}

	/**
	 * Handles update commands.
	 * 
	 * @param container
	 *            inputs
	 */
	@Transactional
	public void update(PeregInputContainer container) {
		updateNonTransaction(container);
	}
	
	private void updateNonTransaction(PeregInputContainer container) {

		List<ItemsByCategory> updateInputs = container.getInputs().stream()
				.filter(p -> !StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());

		if (updateInputs != null && !updateInputs.isEmpty()) {
			updateInputCategories(container, updateInputs);
			DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
			setParamsForCPS001(container.getEmployeeId(), false, updateInputs);
			DataCorrectionContext.transactionFinishing();
		}
		
		updateInputs.forEach(itemsByCategory -> {
			
			val handler = this.updateHandlers.get(itemsByCategory.getCategoryCd());
			
			// In case of optional category fix category doesn't exist
			if (handler != null) {
				handler.handlePeregCommand(container.getPersonId(), container.getEmployeeId(), itemsByCategory);
			}
			
			val commandForUserDef = new PeregUserDefUpdateCommand(container.getPersonId(), container.getEmployeeId(),
					itemsByCategory);
			
			this.userDefUpdate.handle(commandForUserDef);
			
		});
	}
	
	private void updateInputCategories(PeregInputContainer container, List<ItemsByCategory> updateInputs) {
		// Add item invisible to list
		for (ItemsByCategory itemByCategory : updateInputs) {
			
			String itemCode = specialItemCodes.get(itemByCategory.getCategoryCd());
			
			DatePeriodSet  datePeriod =  datePeriodCode.get(itemByCategory.getCategoryCd());
			
			PeregQuery query = PeregQuery.createQueryCategory(itemByCategory.getRecordId(), itemByCategory.getCategoryCd(),
					container.getEmployeeId(), container.getPersonId());

			List<ItemValue> fullItems = itemDefFinder.getFullListItemDef(query);
			
			List<String> visibleItemCodes = itemByCategory.getItems().stream().map(ItemValue::itemCode)
					.collect(Collectors.toList());
			
			List<ItemLog> fullItemInfos = convertItemLog(fullItems, itemByCategory, itemCode, datePeriod);
			
			// List item invisible
			List<ItemValue> itemInvisible = fullItems.stream().filter(i -> {
				
				return i.itemCode().indexOf("O") == -1 && !visibleItemCodes.contains(i.itemCode());
				
			}).collect(Collectors.toList());
			
			itemInvisible.stream().forEach(c ->{
				
				if(c.itemCode().equals(itemCode)) {
					
					fullItemInfos.add(new ItemLog(c.definitionId(), 
									c.itemCode(), 
									c.itemName(), 
									c.type(), 
									c.stringValue(), 
									c.stringValue(),"",""));
				}
				
			});
			
			itemByCategory.setItemLogs(fullItemInfos);
			
			itemByCategory.getItems().addAll(itemInvisible);
		}
	}
	
	private List<ItemLog> convertItemLog(List<ItemValue> fullItems, ItemsByCategory  itemByCategory, String itemCode, DatePeriodSet datePeriod) {
		List<ItemLog> fullItemInfos = new ArrayList<>();
		
		for(ItemValue itemOld : fullItems) {
			for(ItemValue itemNew  : itemByCategory.getItems())  {
				
				if(itemNew.itemCode().equals(itemOld.itemCode()) ) {
					ItemLog itemLog = null;

					switch (itemNew.saveDataType()){
						case DATE:
						case STRING:
							// case special item as EmployCode, specialCode
							if(itemOld.itemCode().equals(itemCode)) {
								itemLog = new ItemLog(itemOld.definitionId(), 
										itemOld.itemCode(), 
										itemOld.itemName(), 
										itemOld.type(), 
										itemOld.stringValue(), 
										itemNew.dViewValue() == null ? itemOld.stringValue(): itemNew.dViewValue(), 
										itemNew.stringValue(), 
										(itemNew.viewValue() == null || itemNew.viewValue() == "") ? itemNew.stringValue(): itemNew.viewValue());
								break;
							}
							if(datePeriod != null) {
								if(itemOld.itemCode().equals(datePeriod.getStartCode())) {
									itemLog = new ItemLog(itemOld.definitionId(), 
											itemOld.itemCode(), 
											itemOld.itemName(), 
											itemOld.type(), 
											itemOld.stringValue(), 
											itemNew.dViewValue() == null ? itemOld.stringValue(): itemNew.dViewValue(), 
											itemNew.stringValue(), 
											(itemNew.viewValue() == null || itemNew.viewValue() == "") ? itemNew.stringValue(): itemNew.viewValue());
									break;
								}
							}
							
							if(!itemOld.stringValue().equals(itemNew.stringValue())) {
								itemLog = new ItemLog(itemOld.definitionId(), 
										itemOld.itemCode(), 
										itemOld.itemName(), 
										itemOld.type(), 
										itemOld.stringValue(), 
										itemNew.dViewValue() == null ? itemOld.stringValue(): itemNew.dViewValue(), 
										itemNew.stringValue(), 
										(itemNew.viewValue() == null || itemNew.viewValue() == "") ? itemNew.stringValue(): itemNew.viewValue());
								break;
							}
							break;
							
						case NUMERIC:
							if(itemOld.stringValue() == null && itemNew.stringValue() == null)  break;
							Double oldValue = Double.valueOf(itemOld.stringValue());
							Double newValue = Double.valueOf(itemNew.stringValue());
							if(oldValue.compareTo(newValue) != 0) {
								itemLog = new ItemLog(itemOld.definitionId(), 
										itemOld.itemCode(), 
										itemOld.itemName(), 
										itemOld.type(), 
										itemOld.stringValue(), 
										itemNew.dViewValue() == null ? itemOld.stringValue(): itemNew.dViewValue(), 
										itemNew.stringValue(), 
										(itemNew.viewValue() == null || itemNew.viewValue() == "") ? itemNew.stringValue(): itemNew.viewValue());
							}
							break;
						default:
							break;
						
					}
					if(itemLog != null) fullItemInfos.add(itemLog);
				}
			}
		}
		return fullItemInfos;
	}
	
	@Transactional
	public void updateForCPS002(PeregInputContainer container) {
		updateNonTransactionForCPS002(container);
	}
	
	private void updateNonTransactionForCPS002(PeregInputContainer container) {

		List<ItemsByCategory> updateInputs = container.getInputs().stream()
				.filter(p -> !StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());

		if (updateInputs != null && !updateInputs.isEmpty()) {
			// Add item invisible to list
			for (ItemsByCategory itemByCategory : updateInputs) {

				PeregQuery query = PeregQuery.createQueryCategory(itemByCategory.getRecordId(), itemByCategory.getCategoryCd(),
						container.getEmployeeId(), container.getPersonId());

				List<ItemValue> fullItems = itemDefFinder.getFullListItemDef(query);
				List<String> visibleItemCodes = itemByCategory.getItems().stream().map(ItemValue::itemCode)
						.collect(Collectors.toList());

				// List item invisible
				List<ItemValue> itemInvisible = fullItems.stream().filter(i -> {
					return i.itemCode().indexOf("O") == -1 && !visibleItemCodes.contains(i.itemCode());
				}).collect(Collectors.toList());

				itemByCategory.getItems().addAll(itemInvisible);
			}

		}

		updateInputs.forEach(itemsByCategory -> {
			val handler = this.updateHandlers.get(itemsByCategory.getCategoryCd());
			// In case of optional category fix category doesn't exist
			if (handler != null) {
				handler.handlePeregCommand(container.getPersonId(), container.getEmployeeId(), itemsByCategory);
			}
			val commandForUserDef = new PeregUserDefUpdateCommand(container.getPersonId(), container.getEmployeeId(),
					itemsByCategory);
			this.userDefUpdate.handle(commandForUserDef);
		});
	}

	@Transactional
	public Object register(PeregInputContainer inputContainer) {
		
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER, -99);
		String recordId = null;
		
		// ADD COMMAND
		recordId = this.add(inputContainer);
		
		// UPDATE COMMAND
		this.update(inputContainer);
		
		// DELETE COMMAND
		this.delete(inputContainer);
		
		DataCorrectionContext.transactionFinishing(-99);
		return new Object[] { recordId };
	}
	
	/**
	 * @param inputContainer
	 * delete data when click register cps001
	 */
	private void delete(PeregInputContainer inputContainer) {
		List<PeregDeleteCommand> deleteInputs = inputContainer.getInputs().stream()
				.filter(p -> p.isDelete()).map(x -> new PeregDeleteCommand(inputContainer.getPersonId(),
						inputContainer.getEmployeeId(), x.getCategoryType(), x.getCategoryCd(), x.getCategoryName(), x.getRecordId()))
				.collect(Collectors.toList());
		
		deleteInputs.forEach(deleteCommand -> delete(deleteCommand));
	}
	
	public void deleteHandler(PeregDeleteCommand command) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER, -88);
		
		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(command.getEmployeeId()));
		UserAuthDto user = new UserAuthDto("", "", "", command.getEmployeeId(), "", "");
		
		if(userAuth.size() > 0) {
			 user = userAuth.get(0);
		}
		
		PersonCorrectionTarget target = new PersonCorrectionTarget(
				user.getUserID(),
				command.getEmployeeId(), 
				user.getUserName(),
			    PersonInfoProcessAttr.UPDATE, null);

		// set correction log
		PersonCorrectionLogParameter correction = new PersonCorrectionLogParameter(Arrays.asList(target));
		DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.PERSON_CORRECTION_LOG.value), correction);
		
		this.delete(command);
		DataCorrectionContext.transactionFinishing(-88);		
	}

	/**
	 * Handles delete command.
	 * 
	 * @param command
	 *            command
	 */
	@Transactional
	private void delete(PeregDeleteCommand command) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
		// DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.PERSON_CORRECTION_LOG.value), correction);
		
		val handler = this.deleteHandlers.get(command.getCategoryCode());
		if (handler != null) {
			handler.handlePeregCommand(command);
		}
		val commandForUserDef = new PeregUserDefDeleteCommand(command);
		this.userDefDelete.handle(commandForUserDef);
		
		// Add category correction data
		CategoryCorrectionTarget ctgTarget = new CategoryCorrectionTarget(command.getCategoryName(),
				InfoOperateAttr.deleteOf(command.getCategoryType()), new ArrayList<PersonCorrectionItemInfo>(), TargetDataKey.of(GeneralDate.today()),
				Optional.ofNullable(null));

		PersonCategoryCorrectionLogParameter personCtg = new PersonCategoryCorrectionLogParameter(
				Arrays.asList(ctgTarget));
		DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.CATEGORY_CORRECTION_LOG.value),
				personCtg);
		
		DataCorrectionContext.transactionFinishing();
	}

	/**
	 * return List Category Code
	 * 
	 */
	public List<String> getAddCategoryCodeList() {

		List<String> ctgCodeList = new ArrayList<String>();
		this.addHandlers.forEach((k, v) -> {
			ctgCodeList.add(k);
		});
		return ctgCodeList;

	}
}
