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

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.common.FacadeUtils;
import nts.uk.ctx.pereg.app.find.employee.category.EmpCtgFinder;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMainCategoryDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpBody;
import nts.uk.ctx.pereg.app.find.processor.ItemDefFinder;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.dto.DateRangeDto;
import nts.uk.ctx.pereg.dom.person.info.item.ItemBasicInfo;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;
import nts.uk.ctx.sys.log.app.command.matrix.MatrixPersonCorrectionLogParams;
import nts.uk.ctx.sys.log.app.command.matrix.PersonCorrectionLogInter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ReviseInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
import nts.uk.shr.pereg.app.command.PeregInputContainerCps003;
import nts.uk.shr.pereg.app.command.PeregListCommandHandlerCollector;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefAddListCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefListUpdateCommandHandler;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefUpdateCommand;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;

@ApplicationScoped
public class PeregCommonCommandFacade {
	
	@Inject 
	private PerInfoCategoryRepositoty ctgRepo;

	@Inject
	private PeregListCommandHandlerCollector handlerCollector;

	/** Command handlers to add */
	private Map<String, PeregAddListCommandHandler<?>> addHandlers;

	/** Command handlers to update */
	private Map<String, PeregUpdateListCommandHandler<?>> updateHandlers;

	/** this handles command to add data defined by user. */
	@Inject
	private PeregUserDefAddListCommandHandler userDefAdd;

	/** this handles command to update data defined by user. */
	@Inject
	private PeregUserDefListUpdateCommandHandler userDefUpdate;

	@Inject
	private ItemDefFinder itemDefFinder;
	
	@Inject
	private EmpCtgFinder empCtgFinder;

	@Inject
	private GetUserByEmpFinder userFinder;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	@Inject
	private FacadeUtils facadeUtils;
	
	@Inject
	private CategoryValidate categoryValidate;
	
	private final static String nameEndate = "終了日";
	
	private final static String valueEndate = "9999/12/31";
	
	//edit with category CS00021 勤務種別 change type of category when history item is latest
	private final static String category21 = "CS00021";
	
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
	}
	
	public List<MyCustomizeException> registerHandler(List<PeregInputContainerCps003> inputContainerLst, int modeUpdate, GeneralDate baseDate) {
		if(CollectionUtil.isEmpty(inputContainerLst)) return new ArrayList<>();
		List<MyCustomizeException> result = new ArrayList<MyCustomizeException>();
		DataCorrectionContext.transactional(CorrectionProcessorId.MATRIX_REGISTER, -33, () -> {
			List<PersonCorrectionLogParameter> target  = new ArrayList<>();
			Map<String, String> mapSidPid = inputContainerLst.stream().filter(distinctByKey(PeregInputContainerCps003::getPersonId)).collect(Collectors.toMap(PeregInputContainerCps003::getEmployeeId, PeregInputContainerCps003::getPersonId));
			
			Map<String, List<UserAuthDto>> userMaps = this.userFinder.getByListEmp(new ArrayList<>(mapSidPid.keySet())).stream().collect(Collectors.groupingBy(c -> c.getEmpID()));
			if(userMaps.isEmpty()) return;
			userMaps.entrySet().stream().forEach(c ->{
				List<UserAuthDto> userLst = c.getValue();
				if(userLst != null) {
					target.add(new PersonCorrectionLogParameter(userLst.get(0).getUserID(), c.getKey(),
							userLst.get(0).getEmpName(), PersonInfoProcessAttr.UPDATE, null));
				}
			});
			
			List<MyCustomizeException> add = this.add(inputContainerLst, target, baseDate, modeUpdate);
			if(modeUpdate == 1) {
				List<MyCustomizeException> update = this.update(inputContainerLst, baseDate,  target, modeUpdate);
				result.addAll(update);
			}
			
			result.addAll(add);
			
		});
		return result;
	}
	/**
	 * hàm này viết cho cps001 Handles add commands.
	 * 
	 * @param container
	 *            inputs
	 */
	@Transactional
	public List<MyCustomizeException> add(List<PeregInputContainerCps003> containerLst, List<PersonCorrectionLogParameter> target, GeneralDate baseDate, int modeUpdate) {
		return addNonTransaction(containerLst, target, baseDate, modeUpdate);	
	}

	private List<MyCustomizeException> addNonTransaction(List<PeregInputContainerCps003> containerLst, List<PersonCorrectionLogParameter> target, GeneralDate baseDate, int modeUpdate) {
		List<PeregInputContainerCps003> containerAdds = new ArrayList<>();
		List<MyCustomizeException> result = new ArrayList<>();
		containerLst.stream().forEach(c ->{
			ItemsByCategory itemByCtg = c.getInputs();
			if(itemByCtg.getRecordId() == null || itemByCtg.getRecordId() == "" || itemByCtg.getRecordId().indexOf("noData") > -1 || modeUpdate == 2) {
				containerAdds.add(c);
			}
			
		});
		if(containerAdds.size() == 0) {
			return new ArrayList<MyCustomizeException>(); 
		}

		ItemsByCategory itemFirstByCtg = containerAdds.get(0).getInputs();
		
		if(modeUpdate == 2) {
			// đoạn nay update những item không xuất hiện trên màn hình, vì của các nhân  viên sẽ khác nhau nên sẽ lấy khác nhau, khả năng mình sẽ trả về một Map<sid, List<ItemValue>
			updateInputCategoriesCps003(containerAdds , baseDate);
		}
		
		// Get all items by category id
		Map<String, List<ItemBasicInfo>> itemsByCtgId = perInfoItemDefRepositoty
				.getItemCDByListCategoryIdWithAbolition(Arrays.asList(itemFirstByCtg.getCategoryId()),
						AppContexts.user().contractCode());
		
		// Filter required item
		Map<String, List<ItemBasicInfo>> requiredItemByCtgId = itemsByCtgId.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream()
						.filter(info -> info.getRequiredAtr() == 1 && info.getAbolitionAtr() == 0).collect(Collectors.toList())));
		
		// Check is enough item to regist
		// Item missing
		List<ItemBasicInfo> itemExclude = new ArrayList<>();
		
		// vì những item của các nhân viên đều được hiển thị giống nhau nên mình sẽ lấy
		// list item đầu tiên của nhân viên đầu tiên trong list employee đó thực hiện  mục đích đó
		List<String> listItemCodeInScreen = itemFirstByCtg.getItems().stream().map(i -> i.itemCode())
				.collect(Collectors.toList());
		
		Map<String, String> employees = containerAdds.stream()
				.collect(Collectors.toMap(PeregInputContainerCps003::getPersonId, PeregInputContainerCps003::getEmployeeId));

		Map<String, List<ItemValue>> itemsDefaultLstBySid = facadeUtils.getListDefaultItem(itemFirstByCtg.getCategoryCd(),
				listItemCodeInScreen, itemsByCtgId.get(itemFirstByCtg.getCategoryId()), employees);
		
		containerAdds.stream().forEach(c -> {
			ItemsByCategory itemByCtg = c.getInputs();
			List<ItemValue> itemDefaultLst = itemsDefaultLstBySid.get(c.getEmployeeId());
			
			if (itemDefaultLst != null) {
				c.getInputs().getItems().addAll(itemDefaultLst);
			}
			
			List<String> listItemAfter = itemByCtg.getItems().stream().map(i -> i.itemCode()).collect(Collectors.toList());

			if (requiredItemByCtgId.containsKey(itemByCtg.getCategoryId())) {
				List<ItemValue> selectedFirst = facadeUtils.getListDefaultEnum(requiredItemByCtgId.get(itemByCtg.getCategoryId()));
				if(!selectedFirst.isEmpty()) {
					c.getInputs().getItems().addAll(selectedFirst);
				}
				itemExclude.addAll(requiredItemByCtgId.get(itemByCtg.getCategoryId()).stream()
						.filter(i -> !listItemAfter.contains(i.getItemCode())).collect(Collectors.toList()));
			}

		});
		
		
		if(modeUpdate == 2) {
			
			List<String> indexs = new ArrayList<>();
			
			categoryValidate.validateSpaceCS0002(indexs, result, containerAdds);
			
			if(!indexs.isEmpty()) {
				indexs.stream().forEach(i ->{
					containerAdds.remove(Integer.valueOf(i).intValue());
				});
			}
			
			if(containerAdds.size() == 0) {
				return result; 
			}
			
			categoryValidate.historyValidate(result, containerAdds, baseDate, modeUpdate);
			
			if(containerAdds.isEmpty()) return result;
			
		}
		
		
		
		if(containerAdds.isEmpty()) return result;
		
		categoryValidate.validateItemOfCS0069(result, containerAdds);
		
		if(containerAdds.isEmpty()) return result;

		DataCorrectionContext.transactional(CorrectionProcessorId.MATRIX_REGISTER, () -> {
			if(modeUpdate == 1) {
				updateInputForAdd(containerAdds);
			}
			setParamsForCPS003(containerAdds, PersonInfoProcessAttr.ADD, target, baseDate, modeUpdate);
			
		});
		
		// đoạn này viết command
		val handler = this.addHandlers.get(itemFirstByCtg.getCategoryCd());
		
		if (handler != null && itemFirstByCtg.isHaveSomeSystemItems()) {
			handler.handlePeregCommand(containerAdds);
		    // xử lí cho những item optional
			List<PeregUserDefAddCommand> commandForUserDef = containerAdds.stream().map(c -> { return new PeregUserDefAddCommand(c.getPersonId(), c.getEmployeeId(), c.getInputs().getCategoryCd(), c.getInputs());}).collect(Collectors.toList());
			this.userDefAdd.handle(commandForUserDef);
		}

		return result;
	}

	/**
	 * Handles update commands.
	 * 
	 * @param container
	 *            inputs
	 */
	@Transactional
	public List<MyCustomizeException> update(List<PeregInputContainerCps003> containerLst, GeneralDate baseDate, List<PersonCorrectionLogParameter> target, int modeUpdate) {
		List<PeregInputContainerCps003> containerAdds = new ArrayList<>();
		List<MyCustomizeException> result = new ArrayList<MyCustomizeException>();
		containerLst.stream().forEach(c ->{
			ItemsByCategory itemByCtg = c.getInputs();
			if(itemByCtg.getRecordId() != null && itemByCtg.getRecordId().indexOf("noData") == -1) {
				containerAdds.add(c);
			}
			
		});
		try {

		if(containerAdds.size() > 0) {
			ItemsByCategory itemFirstByCtg = containerAdds.get(0).getInputs();
			
			List<String> indexs = new ArrayList<>();
			categoryValidate.validateSpaceCS0002(indexs, result, containerAdds);
			if(!indexs.isEmpty()) {
				indexs.stream().forEach(i ->{
					containerAdds.remove(Integer.valueOf(i).intValue());
				});
			}
			if(containerAdds.isEmpty()) {
				return result;
			}
			
			categoryValidate.validateItemOfCS0069(result, containerAdds);
			
			if(containerAdds.isEmpty()) return result;
			
			// đoạn này viết log
			DataCorrectionContext.transactional(CorrectionProcessorId.MATRIX_REGISTER, () -> {
				setParamsForCPS003(containerAdds, PersonInfoProcessAttr.UPDATE, target, baseDate, 2);
			});

			// đoạn nay update những item không xuất hiện trên màn hình, vì của các nhân  viên sẽ khác nhau nên sẽ lấy khác nhau, khả năng mình sẽ trả về một Map<sid, List<ItemValue>
			updateInputCategoriesCps003(containerAdds , baseDate);

			categoryValidate.historyValidate(result, containerAdds, baseDate, modeUpdate);
			
			if(containerAdds.isEmpty()) return result;
			
			// vì categoryCode của các nhân viên trong màn cps003 đều giống nhau nên mình sẽ
			// lấy itemByCtg của thằng nhân viên đầu tiên
			val handler = this.updateHandlers.get(itemFirstByCtg.getCategoryCd());

			// In case of optional category fix category doesn't exist
			if (handler != null) {
				result.addAll(handler.handlePeregCommand(containerAdds));
			}

			val commandForUserDef = containerLst.stream().map(c -> {
				return new PeregUserDefUpdateCommand(c.getPersonId(), c.getEmployeeId(), c.getInputs());
			}).collect(Collectors.toList());
			
			this.userDefUpdate.handle(commandForUserDef);
		}
		
	}catch(Exception e) {
		e.printStackTrace();
		throw e;
	}
		return result;
	}

	//update input for case ADD
	private void updateInputForAdd(List<PeregInputContainerCps003> containerLst) {
		// Add item invisible to list
		containerLst.stream().forEach(c ->{
			c.getInputs().getItems().stream().forEach(item -> {
				item.setValueBefore(null);
				item.setContentBefore(null);
			});
		});
	}

	/**
	 * set Params cho trường hợp update, add màn cps001
	 * @param sid
	 * @param pid
	 * @param isAdd
	 * @param inputs
	 * @param target
	 */
	private void setParamsForCPS003(List<PeregInputContainerCps003> containerLst,  PersonInfoProcessAttr isAdd, List<PersonCorrectionLogParameter> targets, GeneralDate standardDate, int modeUpdate) {
		if (targets.size() > 0 && !containerLst.isEmpty()) {
			List<PersonCorrectionLogInter> matrixLogs = new ArrayList<>();
			
			// Do tất cả nhân viên đều có categoryCode giống nhau nên 
			// mình sẽ lấy ra nhân viên đầu tiên để xử lý một số phần chung
			PeregInputContainerCps003 firstEmp = containerLst.get(0);
			List<DateRangeDto> ctgCodes = this.ctgRepo.dateRangeCode();
			List<PeregEmpInfoQuery> empInfos = new ArrayList<>();
			containerLst.stream().forEach(c ->{
				empInfos.add(new PeregEmpInfoQuery(c.getPersonId(),  c.getEmployeeId(), c.getInputs().getRecordId()));
			});
			PeregQueryByListEmp queryByListEmp = PeregQueryByListEmp.createQueryLayout(firstEmp.getInputs().getCategoryId(), firstEmp.getInputs().getCategoryCd(), standardDate, empInfos);
			Map<String, List<ItemValue>> itemValueBySids = this.getItemInvisiblesCPS003(queryByListEmp, containerLst , isAdd, modeUpdate);
			
			containerLst.stream().forEach(c ->{
				
				try {
					String stringKey = null;
					CategoryType ctgType = null;
					ReviseInfo reviseInfo = null;
					DateRangeDto dateRange = null;
					ItemsByCategory input = c.getInputs();
					List<PersonCorrectionItemInfo> lstItemInfo = new ArrayList<>();
					PeregQuery query = PeregQuery.createQueryCategory(input.getRecordId(), input.getCategoryCd(),c.getEmployeeId(), c.getPersonId());
					query.setCategoryId(c.getInputs().getCategoryId());
					
					List<ComboBoxObject> historyLst =  this.empCtgFinder.getListInfoCtgByCtgIdAndSid(query);
					List<ItemValue> invisibles = itemValueBySids.get(c.getEmployeeId());
					
					ctgType = EnumAdaptor.valueOf(firstEmp.getInputs().getCategoryType(), CategoryType.class);
					Optional<DateRangeDto> dateRangeOp = ctgCodes.stream().filter(ctgCode -> ctgCode.getCtgCode().equals(input.getCategoryCd())).findFirst();
					boolean isHistory = ctgType == CategoryType.DUPLICATEHISTORY
							|| ctgType == CategoryType.CONTINUOUSHISTORY || ctgType == CategoryType.NODUPLICATEHISTORY || ctgType == CategoryType.CONTINUOUS_HISTORY_FOR_ENDDATE;

					if(input.getCategoryCd().equals("CS00003")) {
						dateRange = new DateRangeDto(c.getInputs().getCategoryCd(), "IS00020", "IS00021");
					} else {
						dateRange = isHistory == true? dateRangeOp.get(): null;
					}
					List<ItemValue> itemLogs = input.getItems() == null ?
							new ArrayList<>() :   input.getItems().stream().filter(distinctByKey(p -> p.itemCode())).collect(Collectors.toList());
					if (!CollectionUtil.isEmpty(invisibles)) {
						for (ItemValue item : invisibles) {
							switch (ctgType) {
							case SINGLEINFO:
							case MULTIINFO:
								if (specialItemCode.contains(item.itemCode())) {
									itemLogs.add(item);
								}
								break;
							case DUPLICATEHISTORY:
							case CONTINUOUSHISTORY:
							case NODUPLICATEHISTORY:
							case CONTINUOUS_HISTORY_FOR_ENDDATE:
								if (item.itemCode().equals(dateRange.getStartDateCode())) {
									itemLogs.add(item);
								}
								break;
							default:
								break;
							}
						}
					}
					
					InfoOperateAttr info = InfoOperateAttr.ADD;
					for (ItemValue item : itemLogs) {
						// kiểm tra các item của  category nghỉ đặc biệt, employee, lịch sử 
						switch(ctgType) {
						case SINGLEINFO:
							if (specialItemCode.contains(item.itemCode())) {
								stringKey = item.valueAfter();
							}
							break;
							
						case MULTIINFO:						
						case CONTINUOUSHISTORY:
						case CONTINUOUS_HISTORY_FOR_ENDDATE:
						case DUPLICATEHISTORY:
						case NODUPLICATEHISTORY:
							if(specialItemCode.contains(item.itemCode()) || (isHistory == true && item.itemCode().equals(dateRange.getStartDateCode()))) {
								stringKey = item.valueAfter();
							}
							if(ctgType == CategoryType.CONTINUOUSHISTORY || ctgType == CategoryType.CONTINUOUS_HISTORY_FOR_ENDDATE
									|| ctgType == CategoryType.DUPLICATEHISTORY || ctgType == CategoryType.NODUPLICATEHISTORY) {
								// trường hợp category lịch sử không có history nào
								boolean isContinuousHistory = ctgType == CategoryType.CONTINUOUSHISTORY;
								if(historyLst.size() == 1) {
									if (item.itemCode().equals(dateRange.getEndDateCode())) {
										item.setValueAfter((isContinuousHistory && !input.getCategoryCd().equals(category21)) == true ? valueEndate: item.valueAfter());
										item.setContentAfter((isContinuousHistory && !input.getCategoryCd().equals(category21))== true? valueEndate: item.valueAfter());
									}
									
								}else {									
									// trường hợp tạo mới hoàn toàn category
									for (ComboBoxObject combox : historyLst) {
										if (combox.getOptionValue() != null) {
											// optionText có kiểu giá trị 2018/12/01 ~ 2018/12/31
											String[] history = combox.getOptionText().split("~");
											switch (isAdd) {
											case ADD:
												info = InfoOperateAttr.ADD_HISTORY;
												//nếu thêm lịch sử thì endCode sẽ có giá trị 9999/12/31
												if (item.itemCode().equals(dateRange.getEndDateCode())) {
													item.setValueAfter((isContinuousHistory && !input.getCategoryCd().equals("CS00021")) == true? valueEndate: item.valueAfter());
													item.setContentAfter((isContinuousHistory && !input.getCategoryCd().equals("CS00021")) == true? valueEndate: item.contentAfter());
												}else {
													if(ctgType == CategoryType.CONTINUOUSHISTORY || ctgType == CategoryType.CONTINUOUS_HISTORY_FOR_ENDDATE) {
														if(item.itemCode().equals(dateRange.getStartDateCode())) {
															reviseInfo = new ReviseInfo(nameEndate,
																Optional.ofNullable(GeneralDate.fromString(item.valueAfter(), "yyyy/MM/dd").addDays(-1)),
																Optional.empty(), Optional.empty());
														}
													}
												}
												break;
												
											case UPDATE:
												info = InfoOperateAttr.UPDATE;
												if(ctgType == CategoryType.CONTINUOUSHISTORY || ctgType == CategoryType.CONTINUOUS_HISTORY_FOR_ENDDATE) {
													if(item.itemCode().equals(dateRange.getStartDateCode())) {
														if (!history[1].equals(" ")) {
															GeneralDate oldEnd = GeneralDate.fromString(history[1].substring(1), "yyyy/MM/dd");
															GeneralDate oldStart = GeneralDate.fromString(item.valueBefore(), "yyyy/MM/dd");
															if (oldStart.addDays(-1).equals(oldEnd)) {
																reviseInfo = new ReviseInfo(nameEndate,
																		Optional.ofNullable(GeneralDate.fromString(item.valueAfter(), "yyyy/MM/dd").addDays(-1)),
																		Optional.empty(), Optional.empty());
															}
														}
													}
												}
												break;

											default:
												break;
											}

										}
									}
								}
							}
							break;
							
							default: break;
						}
						
						if (ItemValue.filterItem(item) != null) {
							input.getItems().stream().forEach(itemMid ->{
								if(item.itemCode().equals(itemMid.itemCode())) {
									ItemValue convertItem = ItemValue.setContentForCPS001(item);
									lstItemInfo.add(PersonCorrectionItemInfo.createItemInfoToItemLog(convertItem));
								}
							});
							
						}
					}

					// Add category correction data
					PersonCategoryCorrectionLogParameter ctgTarget = null;
					
					if (isAdd == PersonInfoProcessAttr.ADD) {
						ctgTarget = setCategoryTarget(ctgType, ctgTarget, input, lstItemInfo, reviseInfo, stringKey, info);
					} else {
						ctgTarget = setCategoryTarget(ctgType, ctgTarget, input, lstItemInfo, reviseInfo, stringKey, info != InfoOperateAttr.ADD ? info: InfoOperateAttr.UPDATE);
					}
					
					if (ctgTarget != null && lstItemInfo.size() > 0) {
						Optional<PersonCorrectionLogParameter> perLog = targets.stream().filter(p -> p.getEmployeeId().equals(c.getEmployeeId())).findFirst();
						if(perLog.isPresent()) {
							PersonCorrectionLogInter inter = new PersonCorrectionLogInter(perLog.get(), ctgTarget);
							matrixLogs.add(inter);
						}
;
					}
					stringKey = null;
					reviseInfo = null;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			
			});
			if(!matrixLogs.isEmpty()) {
				try {
					DataCorrectionContext.setParameter(new MatrixPersonCorrectionLogParams(matrixLogs));
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
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
						code == null? null: (code.equals(specialItemCode.get(0)) == true  || code.equals(specialItemCode.get(1)) == true? stringKey : code)), Optional.ofNullable(reviseInfo));
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
	
	/**
	 * updateInputCategoriesCps003
	 * @param containerLst
	 * @param standardDate
	 */
	private void updateInputCategoriesCps003(List<PeregInputContainerCps003> containerLst, GeneralDate standardDate) {
		//PeregQueryByListEmp query = new PeregQueryByListEmp();
		// Do thông tin của categoryId, categoryCode, standardDate của các nhân viên giống nhau
		// nên mình sẽ lấy nhân viên đầu tiên
		PeregInputContainerCps003 inputContainer = containerLst.get(0);
		List<PeregEmpInfoQuery> empInfos = containerLst.stream()
				.map(c -> new PeregEmpInfoQuery(c.getPersonId(), c.getEmployeeId(), c.getInputs().getRecordId()))
				.collect(Collectors.toList());
		PeregQueryByListEmp queryByListEmp = PeregQueryByListEmp.createQueryLayout(
				inputContainer.getInputs().getCategoryId(), inputContainer.getInputs().getCategoryCd(), standardDate,
				empInfos);
		// Add item invisible to list
		Map<String, List<ItemValue>> invisibles = this.getItemInvisiblesCPS003(queryByListEmp, containerLst , PersonInfoProcessAttr.UPDATE, 2);
		containerLst.stream().forEach(c ->{
			List<ItemValue> items = invisibles.get(c.getEmployeeId());
			if(CollectionUtil.isEmpty(items)) return;
			c.getInputs().getItems().addAll(items);
		});
	}
	/**
	 * dùng cho màn cps003
	 * lấy ra những item không được hiển thị trên màn hình layouts
	 * @param query
	 * @param itemByCategory
	 * @return
	 */
	private Map<String, List<ItemValue>> getItemInvisiblesCPS003(PeregQueryByListEmp query, List<PeregInputContainerCps003> containers, PersonInfoProcessAttr isAdd, int modeUpdate){
		Map<String, List<ItemValue>> result = new HashMap<>();
		if(isAdd == PersonInfoProcessAttr.UPDATE || modeUpdate == 2) {
			// Do số lượng item của các nhân viên đều giống nhau nên mình sẽ lấy ra
			// itemsByCtg của nhân viên đầu tiên rồi lọc ra itemCode được hiển thị trên màn hình
			Map<String, List<String>> visibleItemCodeMaps = new HashMap<>();
			containers.stream().forEach(c ->{
				visibleItemCodeMaps.put(c.getEmployeeId(), c.getInputs().getItems().stream().map(ItemValue::itemCode)
					.collect(Collectors.toList()));
			});

			
			Map<String, List<ItemValue>> fullItems = itemDefFinder.getFullListItemDefCPS003(query);
			fullItems.entrySet().stream().forEach(c ->{
				List<String> visibleItemCodes = visibleItemCodeMaps.get(c.getKey());
				if(visibleItemCodes != null) {
					List<ItemValue> items = c.getValue().stream().filter(i -> {
						return i.itemCode().indexOf("O") == -1 && !visibleItemCodes.contains(i.itemCode());
					}).collect(Collectors.toList());
					result.put(c.getKey(), items);
				}
			});
		}
		return result;
	}
	
	private void test(PeregQueryByListEmp query) {
		
		
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
}
