package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.command.common.FacadeUtils;
import nts.uk.ctx.pereg.app.command.layout.ClassificationCommand;
import nts.uk.ctx.pereg.app.find.employee.category.EmpCtgFinder;
import nts.uk.ctx.pereg.app.find.processor.ItemDefFinder;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ReviseInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.DatePeriodSet;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.ItemValueType;
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
	private EmpCtgFinder empCtgFinder;

	@Inject
	private GetUserByEmpFinder userFinder;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	private final static String nameStartDate = "開始日";
	
	private final static String nameEndate = "終了日";
	
	private final static String valueEndate = "9999/12/31";
	
	/* employeeCode, stardCardNo */
	private final static List<String> specialItemCode = Arrays.asList("IS00001", "IS00779");
	
	/* target Key: null */
	private final static List<String> singleCategories = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("CS00002");
			add("CS00022");
			add("CS00023");
			add("CS00024");
			add("CS00035");
			add("CS00036");
		}
	};

	// list code of history category
	private static final List<String> historyCategoryCodeList = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("CS00003");
			add("CS00004");
			add("CS00014");
			add("CS00016");
			add("CS00017");
			add("CS00018");
			add("CS00019");
			add("CS00020");
			add("CS00021");
		}
	};

	/* target Key : code */
	private static final Map<String, String> specialItemCodes = new HashMap<String, String>() {
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
			put("CS00069", "IS00779");
		}
	};

	private static final Map<String, DatePeriodSet> datePeriodCode = new HashMap<String, DatePeriodSet>() {
		private static final long serialVersionUID = 1L;
		{
			put("CS00003", new DatePeriodSet("IS00020", "IS00021"));
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
			
			put("CS00070", new DatePeriodSet("IS00781", "IS00782"));
		}
	};

	/**
	 * return List Category Code
	 */
	public List<String> getAddCategoryCodeList() {
		return this.addHandlers.keySet().stream().collect(Collectors.toList());
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
	
	public Object registerHandler(PeregInputContainer inputContainer) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER, -99);
		String recordId = null;
		PersonCorrectionLogParameter target  = null;
		String employeeId = inputContainer.getEmployeeId();
		UserAuthDto user = new UserAuthDto("", "", "", employeeId, "", "");
		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(employeeId));
		
		if (userAuth.size() > 0) {
			user = userAuth.get(0);
		}
		
		List<ItemsByCategory> updateInput = inputContainer.getInputs().stream()
				.filter(p -> StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());
		
		if (updateInput.size() > 0) {
			target = new PersonCorrectionLogParameter(user.getUserID(), employeeId, user.getUserName(),
					PersonInfoProcessAttr.UPDATE, null);
		} else {
			target = new PersonCorrectionLogParameter(user.getUserID(), employeeId, user.getUserName(),
					PersonInfoProcessAttr.ADD, null);
		}
		
		// ADD COMMAND
		recordId = this.add(inputContainer, target);

		// UPDATE COMMAND
		this.update(inputContainer, target);

		// DELETE COMMAND
		this.delete(inputContainer);

		DataCorrectionContext.transactionFinishing(-99);
		
		return new Object[] { recordId };
	}
	
	public void deleteHandler(PeregDeleteCommand command) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER, -88);

		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(command.getEmployeeId()));
		UserAuthDto user = new UserAuthDto("", "", "", command.getEmployeeId(), "", "");

		if (userAuth.size() > 0) {
			user = userAuth.get(0);
		}

		PersonCorrectionLogParameter target = new PersonCorrectionLogParameter(user.getUserID(), command.getEmployeeId(),
				user.getUserName(), PersonInfoProcessAttr.UPDATE, null);
		
		DataCorrectionContext.setParameter(target.getHashID(), target);

		this.delete(command);
		DataCorrectionContext.transactionFinishing(-88);
	}

	/**
	 * hàm này viết cho cps001 Handles add commands.
	 * 
	 * @param container
	 *            inputs
	 */
	@Transactional
	public String add(PeregInputContainer container, PersonCorrectionLogParameter target) {
		return addNonTransaction(container, false, target);	
	}

	@Transactional
	public String addForCPS002(PeregInputContainer container, PersonCorrectionLogParameter target) {
		return addNonTransaction(container, true, target);
	}

	private String addNonTransaction(PeregInputContainer container, boolean isCps002, PersonCorrectionLogParameter target) {
		// Filter input category
		List<ItemsByCategory> addInputs = container.getInputs().stream()
				.filter(p -> StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());

		List<String> recordIds = new ArrayList<String>();
		String personId = container.getPersonId();
		String employeeId = container.getEmployeeId();
		
		// getall item
		Map<String, List<String>> itemByCtgId = perInfoItemDefRepositoty
				.getItemCDByListCategoryIdWithoutAbolition(container.getInputs().stream()
						.map(ItemsByCategory::getCategoryId).distinct().collect(Collectors.toList()),
						AppContexts.user().contractCode());
		
		if (isCps002 == false) {
			if (addInputs.size() > 0) {
				DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
				updateInputForAdd(addInputs);
				setParamsForCPS001(employeeId, personId, PersonInfoProcessAttr.ADD, addInputs, target);
				DataCorrectionContext.transactionFinishing();
			}
		}
		
		addInputs.forEach(itemsByCategory -> {
			val handler = this.addHandlers.get(itemsByCategory.getCategoryCd());
			
			// Check is enough item to regist
			List<String> listScreenItem = itemsByCategory.getItems().stream().map(i->i.itemCode()).collect(Collectors.toList());
			
			List<ItemValue> listDefault = FacadeUtils.getListDefaultItem(itemsByCategory.getCategoryCd(),listScreenItem);
			itemsByCategory.getItems().addAll(listDefault);
			
			List<String> listItemAfter = itemsByCategory.getItems().stream().map(i->i.itemCode()).collect(Collectors.toList());
			
			Optional<String> itemExclude = itemByCtgId.get(itemsByCategory.getCategoryId()).stream().filter(i -> !listItemAfter.contains(i)).findFirst();
			
			if (itemExclude.isPresent()){
				throw new BusinessException("Msg_1351");
			}
			
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
	public void update(PeregInputContainer container, PersonCorrectionLogParameter target) {
		List<ItemsByCategory> updateInputs = container.getInputs().stream()
				.filter(p -> !StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());

		if (updateInputs != null && !updateInputs.isEmpty()) {
			updateInputCategories(container, updateInputs);
			DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
			setParamsForCPS001(container.getEmployeeId(), container.getPersonId(), PersonInfoProcessAttr.UPDATE, updateInputs, target);
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

	//update input for case ADD
	private void updateInputForAdd(List<ItemsByCategory> inputs) {
		// Add item invisible to list
		for (ItemsByCategory itemByCategory : inputs) {
			List<ItemValue> items = itemByCategory.getItems();
			List<ItemValue> fullItemInfos = items.stream().map(c -> {
				c.setValueBefore(null);
				c.setContentBefore(null);
				return ItemValue.setContent(c);
			}).collect(Collectors.toList());
			itemByCategory.setItemLogs(fullItemInfos);
		}
	}

	/**
	 * set Params cho trường hợp update, add màn cps001
	 * @param sid
	 * @param pid
	 * @param isAdd
	 * @param inputs
	 * @param target
	 */
	private void setParamsForCPS001(String sid, String pid, PersonInfoProcessAttr isAdd, List<ItemsByCategory> inputs, PersonCorrectionLogParameter target) {
		if (target != null) {
			DataCorrectionContext.setParameter(target.getHashID(), target);
			String stringKey = null;

			for (ItemsByCategory input : inputs) {
				List<PersonCorrectionItemInfo> lstItemInfo = new ArrayList<>();

				ReviseInfo reviseInfo = null;
				List<ItemValue> itemLogs = input.getItemLogs() == null ?
						new ArrayList<>() :  input.getItemLogs().stream().filter(distinctByKey(p -> p.itemCode())).collect(Collectors.toList());
				
				boolean isHistory = input.getCategoryType() != CategoryType.SINGLEINFO.value || input.getCategoryType() != CategoryType.MULTIINFO.value;

				for (ItemValue item : itemLogs) {
					// kiểm tra các item của  category nghỉ đặc biệt, employee, lịch sử 
					if (specialItemCode.contains(item.itemCode())
							|| (isHistory && item.logType() == ItemValueType.DATE.value
								&& (item.itemName().equals(nameStartDate) || item.itemName().equals(nameEndate)))) {

						// lấy target Key
						if (specialItemCode.contains(item.itemCode()) || (item.itemName().equals(nameStartDate))) {
							stringKey = item.valueAfter();
						}
						
						// nếu startDate newValue != afterValue;
						if(input.getCategoryType() == CategoryType.CONTINUOUSHISTORY.value) {
							PeregQuery query = PeregQuery.createQueryCategory(input.getRecordId(),
									input.getCategoryCd(), sid,  pid);
							query.setCategoryId(input.getCategoryId());
							List<ComboBoxObject> historyLst =  this.empCtgFinder.getListInfoCtgByCtgIdAndSid(query);
							if(historyLst.size() == 1) {
								if (item.itemName().equals(nameEndate)) {
									item.setValueAfter(valueEndate);
									item.setContentAfter(valueEndate);
								}
								
							}else {
								// trường hợp tạo mới hoàn toàn category
								for (ComboBoxObject c : historyLst) {
									if (c.getOptionValue() != null) {
										// optionText có kiểu giá trị 2018/12/01 ~ 2018/12/31
										String[] history = c.getOptionText().split("~");
										switch (isAdd) {
										case ADD:
											//nếu thêm lịch sử thì endCode sẽ có giá trị 9999/12/31
											if (item.itemName().equals(nameEndate)) {
												item.setValueAfter(valueEndate);
												item.setContentAfter(valueEndate);
												break;
											}else {
												reviseInfo = new ReviseInfo(nameEndate,
													Optional.ofNullable(GeneralDate.fromString(item.valueAfter(), "yyyy/MM/dd").addDays(-1)),
													Optional.empty(), Optional.empty());
												break;
											}
											
											
										case UPDATE:
											if (!history[1].equals(" ")) {
												GeneralDate oldEnd = GeneralDate.fromString(history[1].substring(1), "yyyy/MM/dd");
												GeneralDate oldStart = GeneralDate.fromString(item.valueBefore(), "yyyy/MM/dd");
												if (oldStart.addDays(-1).equals(oldEnd)) {
													reviseInfo = new ReviseInfo(nameEndate,
															Optional.ofNullable(GeneralDate.fromString(item.valueAfter(), "yyyy/MM/dd").addDays(-1)),
															Optional.empty(), Optional.empty());
													break;
												}
											} else {
												break;
											}

										default:
											break;
										
										}

									}
								}
								
							}
				
						}
						
					}
					
					if(item.valueAfter() == null && item.valueBefore() == null) break;
					
					if (isAdd == PersonInfoProcessAttr.ADD) {
						
						if (!item.valueAfter().equals(item.valueBefore())) {
							lstItemInfo.add(PersonCorrectionItemInfo.createItemInfoToItemLog(item));
						}
					} else {

						if(!item.valueAfter().equals(item.valueBefore())) {
							lstItemInfo.add(PersonCorrectionItemInfo.createItemInfoToItemLog(item));
						}
						if (item.valueAfter() != null && item.valueBefore() == null) {
							lstItemInfo.add(PersonCorrectionItemInfo.createItemInfoToItemLog(item));
						}
					}
				}

				CategoryType ctgType = EnumAdaptor.valueOf(input.getCategoryType(), CategoryType.class);

				// Add category correction data
				PersonCategoryCorrectionLogParameter ctgTarget = null;

				if (isAdd == PersonInfoProcessAttr.ADD) {
					ctgTarget = setCategoryTarget(ctgType, ctgTarget, input, lstItemInfo, reviseInfo, stringKey, InfoOperateAttr.ADD);
				} else {
					ctgTarget = setCategoryTarget(ctgType, ctgTarget, input, lstItemInfo, reviseInfo, stringKey, InfoOperateAttr.UPDATE);
				}
				
				if (ctgTarget != null) {
					DataCorrectionContext.setParameter(ctgTarget.getHashID(), ctgTarget);
				}
				stringKey = null;
			}
		}
	}
	
	/**
	 *  trường hợp update :lấy ra danh sách các lịch sử của category liên tục, 
	 *  tìm ra  itemEndDate bị ảnh hưởng để thêm vào domain ReviseInfo
	 *  trường hợp tạo mới set EndDate = "9999/12/31" 
	 * @param sid
	 * @param pid
	 * @param isAdd
	 * @param input
	 * @param itemCode
	 * @param item
	 * @return
	 */
	private void getReviseInfoInContinuesHistory(String sid, String pid, PersonInfoProcessAttr isAdd,  ItemsByCategory input, DatePeriodSet itemCode, ItemValue item, ReviseInfo reviseInfo ) {
		PeregQuery query = PeregQuery.createQueryCategory(input.getRecordId(),
				input.getCategoryCd(), sid,  pid);
		query.setCategoryId(input.getCategoryId());
		List<ComboBoxObject> historyLst =  this.empCtgFinder.getListInfoCtgByCtgIdAndSid(query);
		for (ComboBoxObject c : historyLst) {
			if (c.getOptionValue() != null) {
				String[] startDate = c.getOptionText().split("~");
				switch (isAdd) {
				case ADD:
					if (item.itemCode().equals(itemCode.getEndCode())) {
						item.setValueAfter(valueEndate);
						item.setContentAfter(valueEndate);
					}else {
						reviseInfo = new ReviseInfo(nameEndate,
							Optional.ofNullable(GeneralDate.fromString( item.valueAfter(), "yyyy/MM/dd").addDays(-1)),
							Optional.empty(), Optional.empty());
						break;
					}
					break;
					
				case UPDATE:
					if (!startDate[1].equals(" ")) {
						GeneralDate oldEnd = GeneralDate.fromString(startDate[1].substring(1), "yyyy/MM/dd");
						GeneralDate oldStart = GeneralDate.fromString(item.valueBefore(), "yyyy/MM/dd");
						if (oldStart.addDays(-1).equals(oldEnd)) {
							reviseInfo = new ReviseInfo(nameEndate,
									Optional.ofNullable(GeneralDate.fromString(item.valueAfter(), "yyyy/MM/dd").addDays(-1)),
									Optional.empty(), Optional.empty());
							break;
						}
					}
					
				default:
					break;
				
				}

			}
		}
		
	}

	private PersonCategoryCorrectionLogParameter setCategoryTarget(CategoryType ctgType, PersonCategoryCorrectionLogParameter ctgTarget,
			ItemsByCategory input, List<PersonCorrectionItemInfo> lstItemInfo, ReviseInfo reviseInfo, String stringKey,
			InfoOperateAttr infoOperateAttr) {

		switch (ctgType) {

		case SINGLEINFO:

			if (singleCategories.contains(input.getCategoryCd())) {
				ctgTarget = new PersonCategoryCorrectionLogParameter(input.getCategoryId(), input.getCategoryName(), 
						infoOperateAttr, lstItemInfo,
						new TargetDataKey(CalendarKeyType.NONE, null, null),  Optional.ofNullable(reviseInfo));

			} else {
				String code = specialItemCodes.get(input.getCategoryCd());
				ctgTarget = new PersonCategoryCorrectionLogParameter(input.getCategoryId(), input.getCategoryName(), 
						infoOperateAttr, lstItemInfo,
						new TargetDataKey(CalendarKeyType.NONE, null,
						code.equals(specialItemCode.get(0)) == true  || code.equals(specialItemCode.get(1)) == true? stringKey : code), Optional.ofNullable(reviseInfo));
			}
			return ctgTarget;

		case MULTIINFO:
			ctgTarget = new PersonCategoryCorrectionLogParameter(input.getCategoryId(), input.getCategoryName(), infoOperateAttr, lstItemInfo,
					new TargetDataKey(CalendarKeyType.NONE, null, stringKey), Optional.ofNullable(reviseInfo));
			return ctgTarget;
		case NODUPLICATEHISTORY:
		case DUPLICATEHISTORY:
		case CONTINUOUSHISTORY:
			if(stringKey != null) {
				ctgTarget = new PersonCategoryCorrectionLogParameter(input.getCategoryId(), input.getCategoryName(), infoOperateAttr, lstItemInfo,
						TargetDataKey.of(GeneralDate.fromString(stringKey, "yyyy/MM/dd")), Optional.ofNullable(reviseInfo));
			}
			return ctgTarget;
		default:
			return null;
		}
	}

	private void updateInputCategories(PeregInputContainer container, List<ItemsByCategory> updateInputs) {
		// Add item invisible to list
		for (ItemsByCategory itemByCategory : updateInputs) {

			String itemCode = specialItemCodes.get(itemByCategory.getCategoryCd());
			
			DatePeriodSet datePeriod = datePeriodCode.get(itemByCategory.getCategoryCd());
			
			PeregQuery query = PeregQuery.createQueryCategory(itemByCategory.getRecordId(),
					itemByCategory.getCategoryCd(), container.getEmployeeId(), container.getPersonId());
			
			List<ItemValue> fullItems = itemDefFinder.getFullListItemDef(query);
			
			List<String> visibleItemCodes = itemByCategory.getItems().stream().map(ItemValue::itemCode)
					.collect(Collectors.toList());
			
			// List item invisible
			List<ItemValue> itemInvisible = fullItems.stream().filter(i -> {
				return i.itemCode().indexOf("O") == -1 && !visibleItemCodes.contains(i.itemCode());
			}).collect(Collectors.toList());
			
			List<ItemValue> fullItemInfos = ItemValue.convertItemLog(itemByCategory.getItems(), itemInvisible, itemCode, datePeriod);
			
			itemByCategory.setItemLogs(fullItemInfos);

			itemByCategory.getItems().addAll(itemInvisible);
		}
	}

	@Transactional
	public void updateForCPS002(PeregInputContainer container) {
		List<ItemsByCategory> updateInputs = container.getInputs().stream()
				.filter(p -> !StringUtils.isEmpty(p.getRecordId())).collect(Collectors.toList());

		if (updateInputs != null && !updateInputs.isEmpty()) {
			// Add item invisible to list
			for (ItemsByCategory itemByCategory : updateInputs) {
				PeregQuery query = PeregQuery.createQueryCategory(itemByCategory.getRecordId(),
						itemByCategory.getCategoryCd(), container.getEmployeeId(), container.getPersonId());

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
	
	/**
	 * 
	 * @param keyExtractor
	 * @return
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	/**
	 * @param inputContainer
	 *            delete data when click register cps001
	 */
	private void delete(PeregInputContainer inputContainer) {
		List<PeregDeleteCommand> deleteInputs = inputContainer.getInputs().stream().filter(p -> p.isDelete())
				.map(x -> new PeregDeleteCommand(inputContainer.getPersonId(), inputContainer.getEmployeeId(),
						x.getCategoryId(), x.getCategoryType(), x.getCategoryCd(), x.getCategoryName(), x.getRecordId()))
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
	private void delete(PeregDeleteCommand command) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);

		val handler = this.deleteHandlers.get(command.getCategoryCode());
		if (handler != null) {
			handler.handlePeregCommand(command);
		}

		val commandForUserDef = new PeregUserDefDeleteCommand(command);
		this.userDefDelete.handle(commandForUserDef);

		// Add category correction data
		PersonCategoryCorrectionLogParameter ctgTarget = new PersonCategoryCorrectionLogParameter(command.getCategoryId(), command.getCategoryName(),
				InfoOperateAttr.deleteOf(command.getCategoryType()), new ArrayList<PersonCorrectionItemInfo>(),
				TargetDataKey.of(GeneralDate.today()), Optional.ofNullable(null));
		
		DataCorrectionContext.setParameter(ctgTarget.getHashID(), ctgTarget);

		DataCorrectionContext.transactionFinishing();
	}

}
