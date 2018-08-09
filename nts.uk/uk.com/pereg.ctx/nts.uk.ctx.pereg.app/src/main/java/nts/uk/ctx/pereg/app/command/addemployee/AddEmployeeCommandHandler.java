package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.security.hash.password.PasswordHash;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.app.command.shortworktime.AddShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.AddWorkingConditionCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.AddWorkingConditionCommandAssembler;
import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;
import nts.uk.ctx.pereg.dom.filemanagement.PersonFileManagement;
import nts.uk.ctx.pereg.dom.filemanagement.TypeFile;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinitionSimple;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistory;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistoryRepository;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.log.app.command.pereg.KeySetCorrectionLog;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.CategoryCorrectionTarget;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter.PersonCorrectionTarget;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

/**
 * @author sonnlb
 *
 */
@Stateless
public class AddEmployeeCommandHandler extends CommandHandlerWithResult<AddEmployeeCommand, String> {

	@Inject
	private AddEmployeeCommandHelper helper;

	@Inject
	private AddEmployeeCommandFacade commandFacade;

	@Inject
	private AddWorkingConditionCommandAssembler wkCodAs;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemRepo;
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private EmpFileManagementRepository perFileManagementRepository;
	
	@Inject
	private EmpRegHistoryRepository empHisRepo;
	
	
	private static final List<String> historyCategoryCodeList = Arrays.asList("CS00003", "CS00004", "CS00014", "CS00016", "CS00017", "CS00018",
			"CS00019", "CS00020", "CS00021", "CS00070");
			       
	private static final Map<String, String> startDateItemCodes;
	static {
		Map<String, String> aMap = new HashMap<>();
		// 所属会社履歴
		aMap.put("CS00003", "IS00020");
		// 分類１
		aMap.put("CS00004", "IS00026");
		// 雇用
		aMap.put("CS00014", "IS00066");
		// 職位本務
		aMap.put("CS00016", "IS00077");
		// 職場
		aMap.put("CS00017", "IS00082");
		// 休職休業
		aMap.put("CS00018", "IS00087");
		// 短時間勤務
		aMap.put("CS00019", "IS00102");
		// 労働条件
		aMap.put("CS00020", "IS00119");
		//勤務種別
		aMap.put("CS00021", "IS00255");
		// 労働条件２
		aMap.put("CS00070", "IS00781");

		startDateItemCodes = Collections.unmodifiableMap(aMap);
	}
	
	private static final Map<String, String> endDateItemCodes;
	static {
		Map<String, String> aMap = new HashMap<>();
		// 所属会社履歴
		aMap.put("CS00003", "IS00021");
		// 分類１
		aMap.put("CS00004", "IS00027");
		// 雇用
		aMap.put("CS00014", "IS00067");
		// 職位本務
		aMap.put("CS00016", "IS00078");
		// 職場
		aMap.put("CS00017", "IS00083");
		// 休職休業
		aMap.put("CS00018", "IS00088");
		// 短時間勤務
		aMap.put("CS00019", "IS00103");
		// 労働条件
		aMap.put("CS00020", "IS00120");
		//勤務種別
		aMap.put("CS00021", "IS00256");
		// 労働条件２
		aMap.put("CS00070", "IS00782");

		endDateItemCodes = Collections.unmodifiableMap(aMap);
	}
	
	private static final Map<String, String> mapSpecialCode;
	static {
		Map<String, String> aMap = new HashMap<>();
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

		mapSpecialCode = Collections.unmodifiableMap(aMap);
	}
	
	@Override
	protected String handle(CommandHandlerContext<AddEmployeeCommand> context) {
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER, -98);
		val command = context.getCommand();
		String employeeId = IdentifierUtil.randomUniqueId();
		String userId = IdentifierUtil.randomUniqueId();
		String personId = IdentifierUtil.randomUniqueId();
		String companyId = AppContexts.user().companyId();
		String comHistId = IdentifierUtil.randomUniqueId();

		List<ItemsByCategory> inputs = commandFacade.createData(command);

		validateTime(inputs, employeeId, personId);
		checkRequiredInputs(inputs, employeeId, personId, companyId);
		
		processHistoryPeriod(inputs, command.getHireDate());

		helper.addBasicData(command, personId, employeeId, comHistId, companyId);
		commandFacade.addNewFromInputs(personId, employeeId, comHistId, inputs);
		
		addNewUser(personId, command, userId);
		
		addAvatar(personId, command.getAvatarId());
		
		updateEmployeeRegHist(companyId, employeeId);
		
		setParamsForCorrection(command, inputs, employeeId, userId);
		DataCorrectionContext.transactionFinishing(-98);
		return employeeId;

	}
	
	private void setParamsForCorrection(AddEmployeeCommand command, List<ItemsByCategory> inputs, String employeeId,String userId) {
		// set PeregCorrectionLogParameter
		PersonCorrectionTarget target = new PersonCorrectionTarget(
				userId,
				employeeId, 
				command.getEmployeeName(),
			    PersonInfoProcessAttr.ADD, null);

		// set correction log
		PersonCorrectionLogParameter correction = new PersonCorrectionLogParameter(Arrays.asList(target));
		DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.PERSON_CORRECTION_LOG.value), correction);

		List<CategoryCorrectionTarget> ctgTargets = new ArrayList<>();

		for (ItemsByCategory input : inputs) {
			// prepare data
			GeneralDate startDateItemCode = null;
			String itemCode = null;
			if (historyCategoryCodeList.contains(input.getCategoryCd())) {
				itemCode = startDateItemCodes.get(input.getCategoryCd());
			}
			
			List<PersonCorrectionItemInfo> lstItemInfo = new ArrayList<>();
			for (ItemValue item : input.getItems()) {
				if(item.itemCode().equals(itemCode)) {
					startDateItemCode = item.value();
				}
				lstItemInfo.add(new PersonCorrectionItemInfo(
						item.definitionId(), 
						item.itemName(), 
						null,
						null,
						item.stringValue(),
						item.viewValue(),
						item.saveDataType().value));
			}
			
			//Add category correction data
			CategoryCorrectionTarget ctgTarget = null;
			switch (input.getCategoryCd()) {
			case "CS00001":
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.ADD, lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null, command.getEmployeeCode()), null);
				break;
			case "CS00002":
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.ADD, lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null, null), null);
				break;
			case "CS00003":
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.ADD, lstItemInfo, new TargetDataKey(CalendarKeyType.DATE, startDateItemCode, null), null);
				break;
			case "CS00004":
			case "CS00014":
			case "CS00016":
			case "CS00017":
			case "CS00018":
			case "CS00019":
			case "CS00020":
			case "CS00021":
			case "CS00070":
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.ADD_HISTORY, lstItemInfo, new TargetDataKey(CalendarKeyType.DATE, startDateItemCode, null), null);
				break;
				
			case "CS00022":
			case "CS00023":
			case "CS00024":
			case "CS00035":
			case "CS00036":
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.ADD, lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null, null), null);
				break;
			
			case "CS00025":
			case "CS00026":
			case "CS00027":
			case "CS00028":
			case "CS00029":
			case "CS00030":
			case "CS00031":
			case "CS00032":
			case "CS00033":
			case "CS00034":
			case "CS00049":
			case "CS00050":
			case "CS00051":
			case "CS00052":
			case "CS00053":
			case "CS00054":
			case "CS00055":
			case "CS00056":
			case "CS00057":
			case "CS00058":
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.ADD, lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null, mapSpecialCode.get(input.getCategoryCd())), null);
				break;
			case "CS00015":
			case "CS00037":
			case "CS00038":
				break;
			case "CS00069":
				ctgTarget = new CategoryCorrectionTarget(input.getCategoryName(), InfoOperateAttr.ADD, lstItemInfo, new TargetDataKey(CalendarKeyType.NONE, null, command.getCardNo()), null);
				break;
			default:
				break;
			}
			ctgTargets.add(ctgTarget);
		}
		
		PersonCategoryCorrectionLogParameter personCtg = new PersonCategoryCorrectionLogParameter(ctgTargets.stream().filter( c -> c != null).collect(Collectors.toList()));
		DataCorrectionContext.setParameter(String.valueOf(KeySetCorrectionLog.CATEGORY_CORRECTION_LOG.value), personCtg);

	}


	private void checkRequiredInputs(List<ItemsByCategory> inputs, String employeeId, String personId,
			String companyId) {

		List<String> ctgCodes = inputs.stream().map(x -> x.getCategoryCd()).collect(Collectors.toList());

		// làm phẳng data truyền vào để dễ thao tác
		List<ItemValue> items = new ArrayList<ItemValue>();

		inputs.forEach(ctg -> {

			items.addAll(ctg.getItems());

		});
		// lấy item system required để so sánh
		List<PersonInfoItemDefinitionSimple> requiredItems = perInfoItemRepo
				.getRequiredItemFromCtgCdLst(AppContexts.user().contractCode(), companyId, ctgCodes);

		List<String> nodataItems = new ArrayList<String>();
		requiredItems.forEach(item -> {
			Optional<ItemValue> requiredItemOpt = items.stream()
					.filter(x -> x.itemCode().equals(item.getItemCode().v())).findFirst();
			// kiểm tra item đó có trong data list truyền vào không
			if (requiredItemOpt.isPresent()) {
				ItemValue requiredItem = requiredItemOpt.get();
				// kiểm tra xem giá trị của nó có bị null không
				if (requiredItem.value() == null) {
					// nếu null thì thêm nó vào list lỗi
					nodataItems.add(item.getItemName().v());
				}

			} else {
				nodataItems.add(item.getItemName().v());
			}
		});
		// kiểm tra list lỗi để trả về thông báo
		if (!CollectionUtil.isEmpty(nodataItems)) {
			throw new BusinessException("Msg_925", String.join(",", nodataItems));

		}

	}

	private void validateTime(List<ItemsByCategory> inputs, String employeeId, String personId) {
		Optional<ItemsByCategory> shortWkOpt = inputs.stream().filter(ctg -> ctg.getCategoryCd().equals("CS00019"))
				.findFirst();
		if (shortWkOpt.isPresent()) {
			AddShortWorkTimeCommand shortWk = (AddShortWorkTimeCommand) shortWkOpt.get()
					.createCommandForSystemDomain(personId, employeeId, AddShortWorkTimeCommand.class);
			shortWk.getLstTimeSlot();
		}

		Optional<ItemsByCategory> wkCodOpt = inputs.stream().filter(ctg -> ctg.getCategoryCd().equals("CS00020"))
				.findFirst();
		if (wkCodOpt.isPresent()) {
			AddWorkingConditionCommand wkCod = (AddWorkingConditionCommand) wkCodOpt.get()
					.createCommandForSystemDomain(personId, employeeId, AddWorkingConditionCommand.class);
			wkCodAs.fromDTO(null, wkCod);
		}

	}
	
	private void processHistoryPeriod(List<ItemsByCategory> inputs, GeneralDate hireDate) {
		inputs.forEach( category -> {
			if (historyCategoryCodeList.contains(category.getCategoryCd())) {
				String startDateItemCode = startDateItemCodes.get(category.getCategoryCd());
				String endDateItemCode = endDateItemCodes.get(category.getCategoryCd());
				
				if (!category.getItems().stream().anyMatch(item -> item.itemCode().equals(startDateItemCode))) {
					category.getItems().add(new ItemValue("", startDateItemCode,"","","","", hireDate.toString(), 3));
				} 
				
				if (!category.getItems().stream().anyMatch(item -> item.itemCode().equals(endDateItemCode))) {
					category.getItems().add(new ItemValue("", endDateItemCode,"","","","",  GeneralDate.max().toString(), 3));
				} 
					
			}
		});
	}
	
	private void addNewUser(String personId, AddEmployeeCommand command, String userId) {
		// add new user
		String passwordHash = PasswordHash.generate(command.getPassword(), userId);
		User newUser = User.createFromJavatype(userId, false, passwordHash, command.getLoginId(),
				AppContexts.user().contractCode(), GeneralDate.max(), 0, 0, "",
				command.getEmployeeName(), personId, 1);

		this.userRepository.addNewUser(newUser);

	}
	
	private void addAvatar(String personId, String avatarId) {
		if (avatarId != "") {
			PersonFileManagement perFile = PersonFileManagement.createFromJavaType(personId, avatarId,
					TypeFile.AVATAR_FILE.value, null);

			this.perFileManagementRepository.insert(perFile);
		}

	}
	
	private void updateEmployeeRegHist(String companyId, String employeeId) {

		String currentEmpId = AppContexts.user().employeeId();

		Optional<EmpRegHistory> optRegHist = this.empHisRepo.getRegHistById(currentEmpId);

		EmpRegHistory newEmpRegHistory = EmpRegHistory.createFromJavaType(currentEmpId, companyId,
				GeneralDateTime.now(), employeeId, "");

		if (optRegHist.isPresent()) {

			this.empHisRepo.update(newEmpRegHistory);

		} else {

			this.empHisRepo.add(newEmpRegHistory);

		}
	}
}
