package nts.uk.ctx.pereg.app.command.facade;

import java.math.BigDecimal;
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
import nts.gul.text.StringUtil;
import nts.uk.ctx.pereg.app.command.common.FacadeUtils;
import nts.uk.ctx.pereg.app.find.employee.category.EmpCtgFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpBody;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeInfoDto;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemTypeStateDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.app.find.processor.GridPeregProcessor;
import nts.uk.ctx.pereg.app.find.processor.ItemDefFinder;
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
import nts.uk.shr.pereg.app.find.PeregGridQuery;
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
	private CategoryValidate categoryValidate;
	
	@Inject
	private GridPeregProcessor gridProcessor;
	
	@Inject
	private FacadeUtils facadeUtils;
	
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

		
		GridEmployeeDto gridEmpDto = this.gridProcessor.getGridLayoutRegister(new PeregGridQuery(itemFirstByCtg.getCategoryId(),
				itemFirstByCtg.getCategoryCd(), baseDate,
				containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList())));
		
		List<GridEmpHead> itemRequireds = gridEmpDto.getHeadDatas().stream().filter(c -> c.getItemTypeState().getItemType() == 2).collect(Collectors.toList());
		containerAdds.stream().forEach(c -> {
			Optional<GridEmployeeInfoDto> gridEmployeeInfoDto = gridEmpDto.getBodyDatas().stream()
					.filter(p -> p.getEmployeeId().equals(c.getEmployeeId())).findFirst();
			List<String> listScreenItem = c.getInputs().getItems().stream().map(i -> i.itemCode()).collect(Collectors.toList());
			// Set all default item
			List<ItemValue>	 listDefault = facadeUtils.getListDefaultItem(c.getInputs().getCategoryCd(), listScreenItem,
					c.getEmployeeId(), itemsByCtgId.get(c.getInputs().getCategoryId()));
			c.getInputs().getItems().addAll(listDefault);
			if(gridEmployeeInfoDto.isPresent()) {
				if(c.getInputs().getRecordId() == null || c.getInputs().getRecordId() == "" || c.getInputs().getRecordId().indexOf("noData") > -1 || modeUpdate == 2) {
					itemRequireds.stream().forEach(r ->{
						SingleItemDto single = (SingleItemDto) r.getItemTypeState();
						if(single.getDataTypeState().getDataTypeValue() == 9 || single.getDataTypeState().getDataTypeValue() == 10 || single.getDataTypeState().getDataTypeValue() == 12) return;
						Optional<ItemValue> required = c.getInputs().getItems().stream().filter(i -> i.itemCode().equals(r.getItemCode())).findFirst();
						Optional<GridEmpBody> gridEmpOpt = gridEmployeeInfoDto.get().getItems().stream().filter(i -> i.getItemCode().equals(r.getItemCode())).findFirst();
						if(!required.isPresent() && gridEmpOpt.isPresent()) {
							GridEmpBody gridEmp = gridEmpOpt.get();
							ItemValue itemValue = new ItemValue(r.getItemId(), r.getItemCode(),
									r.getItemName(), gridEmp.getValue()== null? "":  gridEmp.getValue().toString(), gridEmp.getTextValue(), null, null,  this.convertType(r.getItemTypeState(), gridEmp.getValue()), single.getDataTypeState().getDataTypeValue());
							c.getInputs().getItems().add(itemValue);
						}
					});
				}
			}
		});
		
		containerAdds.stream().forEach(c -> {
			ItemsByCategory itemByCtg = c.getInputs();
			
			List<String> listItemAfter = itemByCtg.getItems().stream().map(i -> i.itemCode()).collect(Collectors.toList());

			if (requiredItemByCtgId.containsKey(itemByCtg.getCategoryId())) {
				itemExclude.addAll(requiredItemByCtgId.get(itemByCtg.getCategoryId()).stream()
						.filter(i -> !listItemAfter.contains(i.getItemCode())).collect(Collectors.toList()));
			}

		});
		
		categoryValidate.validateAdd(result, containerAdds, baseDate, modeUpdate);
		
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
			List<MyCustomizeException> myEx =  handler.handlePeregCommand(containerAdds);
			if(!myEx.isEmpty()) {
				if(itemFirstByCtg.getCategoryCd().equals("CS00069")) {
					result.addAll(myEx.stream().filter(c -> !c.getMessageId().equals("NOERROR"))
							.map(c -> new MyCustomizeException(c.getMessageId(), c.getErrorLst(),
									itemFirstByCtg.getByItemCode("IS00779").itemName()))
							.collect(Collectors.toList()));
				}else {
					result.addAll(myEx);
				}
			}
			Map<String, String> recordIds = new HashMap<>();
			
			result.stream().filter(c -> c.getMessageId().equals("NOERROR")).forEach(c -> {
				
				if(!c.getRecordIdBySid().isEmpty()) {
					
					recordIds.putAll(c.getRecordIdBySid());
					
				}
				
			});
			List<String> sidErrors = new ArrayList<>();
			result.stream().forEach(c ->{
				sidErrors.addAll(c.getErrorLst());
			});
			
		    // xử lí cho những item optional
			List<PeregUserDefAddCommand> commandForUserDef = new ArrayList<>();
			containerAdds.stream().forEach(c -> {
				List<String> sidError = sidErrors.stream().filter(e -> e.equals(c.getEmployeeId()))
						.collect(Collectors.toList());
				if (CollectionUtil.isEmpty(sidError)) {
					commandForUserDef.add(new PeregUserDefAddCommand(c.getPersonId(), c.getEmployeeId(),
							modeUpdate == 2 ? recordIds.get(c.getEmployeeId()) : c.getInputs().getRecordId(),
							c.getInputs()));
				}

			});
			if (!CollectionUtil.isEmpty(commandForUserDef)) {
				this.userDefAdd.handle(commandForUserDef);
			}
		}

		return result.stream().filter(c -> !c.getMessageId().equals("NOERROR")).collect(Collectors.toList());
	}
	
	private int convertType(ItemTypeStateDto itemTypeStateDto, Object value) {
		if (itemTypeStateDto.getItemType() == 2) {
			SingleItemDto single = (SingleItemDto) itemTypeStateDto;
			switch (single.getDataTypeState().getDataTypeValue()) {
			case 1:
				return 1;
			case 2:
			case 4:
			case 5:
			case 11:
				return 2;
			case 3:
				return 3;
			case 6:
			case 7:
			case 8:
				SelectionItemDto sel = (SelectionItemDto) single.getDataTypeState();
				switch (sel.getReferenceType().value) {
				// DESIGNATED_MASTER
				case 1:
					try {
						if ((value != null) && !(value instanceof Integer || value instanceof BigDecimal)
								&& value.toString().equals(String.valueOf(Integer.valueOf(value.toString())))) {
							return 2;
						}
						return 1;
					}catch(Exception e) {
						return 1;
					}

				// CODE_NAME
				case 2:
					return 1;
				// ENUM
				case 3:
					return 2;

				}

			}
			return 1;

		}
		return 1;
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
			
			categoryValidate.validateUpdate(result, containerAdds, baseDate, modeUpdate);
			
			if(containerAdds.isEmpty()) return result;
			
			// đoạn này viết log
			DataCorrectionContext.transactional(CorrectionProcessorId.MATRIX_REGISTER, () -> {
				setParamsForCPS003(containerAdds, PersonInfoProcessAttr.UPDATE, target, baseDate, 2);
			});

			// đoạn nay update những item không xuất hiện trên màn hình, vì của các nhân  viên sẽ khác nhau nên sẽ lấy khác nhau, khả năng mình sẽ trả về một Map<sid, List<ItemValue>
			GridEmployeeDto gridEmpDto = this.gridProcessor.getGridLayoutRegister(new PeregGridQuery(itemFirstByCtg.getCategoryId(),
					itemFirstByCtg.getCategoryCd(), baseDate,
					containerAdds.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList())));
			
			List<GridEmpHead> items = gridEmpDto.getHeadDatas().stream().filter(c -> c.getItemTypeState().getItemType() == 2).collect(Collectors.toList());
			containerAdds.stream().forEach(c -> {
				Optional<GridEmployeeInfoDto> gridEmployeeInfoDto = gridEmpDto.getBodyDatas().stream()
						.filter(p -> p.getEmployeeId().equals(c.getEmployeeId())).findFirst();
				if(gridEmployeeInfoDto.isPresent()) {
						items.stream().forEach(r ->{
							SingleItemDto single = (SingleItemDto) r.getItemTypeState();
							if(single.getDataTypeState().getDataTypeValue() == 9 || single.getDataTypeState().getDataTypeValue() == 10 || single.getDataTypeState().getDataTypeValue() == 12) return;
							Optional<ItemValue> required = c.getInputs().getItems().stream().filter(i -> i.itemCode().equals(r.getItemCode())).findFirst();
							Optional<GridEmpBody> gridEmpOpt = gridEmployeeInfoDto.get().getItems().stream().filter(i -> i.getItemCode().equals(r.getItemCode())).findFirst();
							if(!required.isPresent() && gridEmpOpt.isPresent()) {
								GridEmpBody gridEmp = gridEmpOpt.get();
								ItemValue itemValue = new ItemValue(r.getItemId(), r.getItemCode(),
										r.getItemName(), gridEmp.getValue()== null? "":  gridEmp.getValue().toString(), gridEmp.getTextValue(), null, null,  this.convertType(r.getItemTypeState(), gridEmp.getValue()), single.getDataTypeState().getDataTypeValue());
								c.getInputs().getItems().add(itemValue);
							}
						});
				}
			});

			//categoryValidate.historyValidate(result, containerAdds, baseDate, modeUpdate);
			
			if(containerAdds.isEmpty()) return result;
			
			// vì categoryCode của các nhân viên trong màn cps003 đều giống nhau nên mình sẽ
			// lấy itemByCtg của thằng nhân viên đầu tiên
			val handler = this.updateHandlers.get(itemFirstByCtg.getCategoryCd());

			// In case of optional category fix category doesn't exist
			if (handler != null) {
				List<MyCustomizeException> myEx = handler.handlePeregCommand(containerAdds);
				if(!myEx.isEmpty()) {
					if(itemFirstByCtg.getCategoryCd().equals("CS00069")) {
							result.addAll(myEx.stream()
									.map(c -> new MyCustomizeException(c.getMessageId(), c.getErrorLst(),
											itemFirstByCtg.getByItemCode("IS00779").itemName()))
									.collect(Collectors.toList()));
					}else {
						result.addAll(myEx);
					}
				}
			}

			val commandForUserDef = containerAdds.stream().map(c -> {
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
															if(item.valueAfter() != null) {
																reviseInfo = new ReviseInfo(nameEndate,
																		Optional.ofNullable(GeneralDate.fromString(item.valueAfter(), "yyyy/MM/dd").addDays(-1)),
																		Optional.empty(), Optional.empty());
															}

														}
													}
												}
												break;
												
											case UPDATE:
												info = InfoOperateAttr.UPDATE;
												if(ctgType == CategoryType.CONTINUOUSHISTORY || ctgType == CategoryType.CONTINUOUS_HISTORY_FOR_ENDDATE) {
													if(item.itemCode().equals(dateRange.getStartDateCode())) {
														if (!history[1].equals(" ")) {
															try {
																GeneralDate oldEnd = GeneralDate.fromString(history[1].substring(1), "yyyy/MM/dd");
																GeneralDate oldStart = GeneralDate.fromString(item.valueBefore(), "yyyy/MM/dd");
																if (oldStart.addDays(-1).equals(oldEnd)) {
																	if(item.valueAfter() != null) {
																		reviseInfo = new ReviseInfo(nameEndate,
																				Optional.ofNullable(GeneralDate.fromString(item.valueAfter(), "yyyy/MM/dd").addDays(-1)),
																				Optional.empty(), Optional.empty());																		
																	}

																}
															}catch(Exception e) {
																	reviseInfo = new ReviseInfo(nameEndate,
																		Optional.empty(), Optional.empty(), Optional.empty());
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
			if(!StringUtil.isNullOrEmpty(stringKey, true)) {
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
