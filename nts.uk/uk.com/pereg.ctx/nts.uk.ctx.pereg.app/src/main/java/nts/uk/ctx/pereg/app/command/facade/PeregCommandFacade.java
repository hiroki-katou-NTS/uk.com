package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import nts.uk.ctx.sys.auth.dom.user.User;
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
	/* employeeCode, stardCardNo */
	private final static List<String> specialItemCode = Arrays.asList("IS00001","IS00779");
	/*  target Key: null */
	private final static List<String> singleCategories = Arrays.asList("CS00002", "CS00022", "CS00023", "CS00024", "CS00035", "CS00036"); 
	/*  target Key : code */
	private static final Map<String, String> specialItemCodes;
	static {
		Map<String, String> aMap = new HashMap<>();
		aMap.put("CS00001", "IS00001");
		aMap.put("CS00025", "1");
		aMap.put("CS00026", "2");
		aMap.put("CS00027", "3");
		aMap.put("CS00028", "4");
		aMap.put("CS00029", "5");
		aMap.put("CS00030", "6");
		aMap.put("CS00031", "7");
		aMap.put("CS00032", "8");
		aMap.put("CS00033", "9");
		aMap.put("CS00034", "10");
		aMap.put("CS00049", "11");
		aMap.put("CS00050", "12");
		aMap.put("CS00051", "13");
		aMap.put("CS00052", "14");
		aMap.put("CS00053", "15");
		aMap.put("CS00054", "16");
		aMap.put("CS00055", "17");
		aMap.put("CS00056", "18");
		aMap.put("CS00057", "19");
		aMap.put("CS00058", "20");
		specialItemCodes = Collections.unmodifiableMap(aMap);
	}
	
	private static final List<String> historyCategoryCodeList = Arrays.asList("CS00003", "CS00004", "CS00014", "CS00016", "CS00017", "CS00018",
			"CS00019", "CS00020", "CS00021");
			       
	private static final Map<String, DatePeriodSet> datePeriodCode;
	static {
		Map<String, DatePeriodSet> aMap = new HashMap<>();
		aMap.put("CS00003", new DatePeriodSet("IS00020","IS00021"));
		// 分類１
		aMap.put("CS00004", new DatePeriodSet("IS00026","IS00027"));
		// 雇用
		aMap.put("CS00014", new DatePeriodSet("IS00066", "IS00027"));
		// 職位本務
		aMap.put("CS00016", new DatePeriodSet("IS00077","IS00078"));
		// 職場
		aMap.put("CS00017", new DatePeriodSet("IS00082","IS00083"));
		// 休職休業
		aMap.put("CS00018", new DatePeriodSet("IS00087","IS00088"));
		// 短時間勤務
		aMap.put("CS00019", new DatePeriodSet("IS00102","IS00103"));
		// 労働条件
		aMap.put("CS00020", new DatePeriodSet("IS00119","IS00120"));
		//勤務種別
		aMap.put("CS00021", new DatePeriodSet("IS00255","IS00256"));

		datePeriodCode = Collections.unmodifiableMap(aMap);
	}

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
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
		String result = addNonTransaction(container);
//		setParamsForCPS001(container);
		DataCorrectionContext.transactionFinishing();
		return result;
	}
	
	// cật nhật dữ liệu của các category 
	private void setParamsForCPS001(PeregInputContainer container, List<ItemsByCategory> inputs) {
		
		User user = new User(null, false, null, null, null, null, null, null, null, null, null, null);
		/* màn hình cps001 các trường hợp cật nhật đăng kí */
		// set PeregCorrectionLogParameter
		PersonCorrectionTarget target = new PersonCorrectionTarget(
				user.getUserID(),
				container.getEmployeeId(), 
				"",
			    PersonInfoProcessAttr.UPDATE, null);

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
			List<ItemLog>  itemLogs = input.getItemLogs();
			
			for (ItemLog item : itemLogs) {
				if(specialItemCode.contains(item.getItemCode()) || item.getItemCode().equals(itemCode.getStartCode())) {
					stringKey = item.getValueBefore();
					// nếu startDate newValue != afterValue;  
//					if(!item.getValueAfter().equals(item.getValueBefore())){
//					       int indexOfEnd= itemLogs.indexOf(itemCode.getEndCode());
//					       ItemLog endDate = itemLogs.get(indexOfEnd);
//					       reviseInfo = new ReviseInfo(endDate.getItemName(), Optional.ofNullable(GeneralDate.fromString(endDate.getValueBefore(), "yyyy/MM/dd")), null, null);
//					}
				}
				lstItemInfo.add(new PersonCorrectionItemInfo(item.getItemId(), item.getItemName(), item.getValueAfter(), item.getValueBefore(),
						item.getType()));
				
			}
			CategoryType ctgType = EnumAdaptor.valueOf(input.getCategoryType(), CategoryType.class);
			
			//Add category correction data
			CategoryCorrectionTarget ctgTarget = null;
			switch (ctgType) {
			case SINGLEINFO:
				if (singleCategories.contains(input.getCategoryCd())) {
					ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.UPDATE,
							lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null, null), Optional.ofNullable(reviseInfo));
				} else {
					String code = specialItemCodes.get(input.getCategoryCd());
					ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.UPDATE,
							lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null,
							code.equals(specialItemCode.get(0)) == true ? stringKey : code), Optional.ofNullable(reviseInfo));
				}
				break;
			case MULTIINFO:
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.UPDATE, lstItemInfo, 
					new TargetDataKey(CalendarKeyType.NONE, null, stringKey),  Optional.ofNullable(reviseInfo));
				break;
			case CONTINUOUSHISTORY:
			case NODUPLICATEHISTORY:
			case DUPLICATEHISTORY:
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.UPDATE, lstItemInfo, 
					TargetDataKey.of(GeneralDate.fromString(stringKey, "yyyy-MM-dd")), Optional.ofNullable(reviseInfo));
				break;
			default:
				break;
			}
			ctgTargets.add(ctgTarget);
			
		}
		
		PersonCategoryCorrectionLogParameter personCtg = new PersonCategoryCorrectionLogParameter(ctgTargets);
		DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.CATEGORY_CORRECTION_LOG.value), personCtg);		
	}
	
	
	@Transactional
	public String addForCPS002(PeregInputContainer container) {
		String result = addNonTransaction(container);
		return result;
	}
	
	private String addNonTransaction(PeregInputContainer container) {
		// Filter input category
		List<ItemsByCategory> addInputs = container.getInputs().stream()
				.filter(p -> StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());
		
		List<String> recordIds = new ArrayList<String>();
		String personId = container.getPersonId();
		String employeeId = container.getEmployeeId();

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
			// Add item invisible to list
			for (ItemsByCategory itemByCategory : updateInputs) {

				PeregQuery query = PeregQuery.createQueryCategory(itemByCategory.getRecordId(), itemByCategory.getCategoryCd(),
						container.getEmployeeId(), container.getPersonId());

				List<ItemValue> fullItems = itemDefFinder.getFullListItemDef(query);
				List<String> visibleItemCodes = itemByCategory.getItems().stream().map(ItemValue::itemCode)
						.collect(Collectors.toList());
				
				List<ItemLog> fullItemInfos = new ArrayList<>();
				for(ItemValue itemNew  : itemByCategory.getItems()) {
					for(ItemValue itemOld : fullItems) {
						if(itemNew.itemCode().equals(itemOld.itemCode())) {
							fullItemInfos.add(new ItemLog(itemOld.definitionId(), 
									itemOld.itemCode(), 
									itemOld.itemName(), 
									itemOld.type(), 
									itemOld.stringValue(), 
									itemNew.stringValue()));
							break;
						}
					}
				}
				
				itemByCategory.setItemLogs(fullItemInfos);
				
				// List item invisible
				List<ItemValue> itemInvisible = fullItems.stream().filter(i -> {
					return i.itemCode().indexOf("O") == -1 && !visibleItemCodes.contains(i.itemCode());
				}).collect(Collectors.toList());
				
				itemByCategory.getItems().addAll(itemInvisible);
			}

		}

		setParamsForCPS001(container, updateInputs);
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
		// start trasaction
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER, -99);
		
		// ADD COMMAND
		String recordId = this.add(inputContainer);

		// UPDATE COMMAND
		this.update(inputContainer);
		
		// DELETE COMMAND
		this.delete(inputContainer);
		
		// finish transaction log (begin write)
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
						inputContainer.getEmployeeId(), x.getCategoryCd(), x.getRecordId()))
				.collect(Collectors.toList());
		
		deleteInputs.forEach(deleteCommand -> delete(deleteCommand));

	}

	/**
	 * Handles delete command.
	 * 
	 * @param command
	 *            command
	 */
	@Transactional
	public void delete(PeregDeleteCommand command) {

		val handler = this.deleteHandlers.get(command.getCategoryId());
		if (handler != null) {
			handler.handlePeregCommand(command);
		}
		val commandForUserDef = new PeregUserDefDeleteCommand(command);
		this.userDefDelete.handle(commandForUserDef);
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
