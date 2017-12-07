package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.security.hash.password.PasswordHash;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.PersonFileManagement;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.TypeFile;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.command.facade.PeregCommandFacade;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistory;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistoryRepository;
import nts.uk.ctx.sys.gateway.dom.login.User;
import nts.uk.ctx.sys.gateway.dom.login.UserRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

/**
 * @author sonnlb
 *
 */
@Stateless
public class AddEmployeeCommandHandler extends CommandHandler<AddEmployeeCommand> {
	@Inject
	private RegisterLayoutFinder layoutFinder;

	@Inject
	private PeregCommandFacade commandFacade;

	@Inject
	private UserRepository userRepository;

	@Inject
	private EmpFileManagementRepository perFileManagementRepository;

	@Inject
	private EmpRegHistoryRepository empHisRepo;

	@Inject
	private AffCompanyHistRepository companyHistRepo;

	@Inject
	private EmployeeDataMngInfoRepository empDataRepo;

	@Override
	protected void handle(CommandHandlerContext<AddEmployeeCommand> context) {

		AddEmployeeCommand command = context.getCommand();

		List<SettingItemDto> dataList = this.layoutFinder.itemListByCreateType(command.getCreateType(),
				command.getInitSettingId(), command.getHireDate(), command.getEmployeeCopyId());

		// merge data from client with dataList
		mergeData(dataList, command.getInputs());

		List<ItemsByCategory> inputs = new ArrayList<ItemsByCategory>();

		List<String> categoryCodeList = commandFacade.getAddCategoryCodeList();
		dataList.forEach(x -> {

			if (categoryCodeList.indexOf(x.getCategoryCode()) == -1 && x.getCategoryCode().charAt(1) == 'O') {

				categoryCodeList.add(x.getCategoryCode());

			}
		});

		categoryCodeList.forEach(categoryCd -> {

			ItemsByCategory newCtg = createNewItemsByCategoryCode(dataList, categoryCd);
			if (newCtg != null) {

				inputs.add(newCtg);
			}

		});
		// set Person Name
		setItemValue(inputs, "CS00002", "IS00003", command.getEmployeeName(), 2);

		// check duplicate employeeCode
		List<EmployeeDataMngInfo> infoList = this.empDataRepo
				.getEmployeeNotDeleteInCompany(AppContexts.user().companyId(), command.getEmployeeCode());

		if (!CollectionUtil.isEmpty(infoList)) {
			throw new BusinessException("Msg_345");
		}

		String personId = IdentifierUtil.randomUniqueId();

		String employeeId = IdentifierUtil.randomUniqueId();

		String userId = IdentifierUtil.randomUniqueId();

		// add AffCompanyHist

		List<AffCompanyHistByEmployee> comHistList = new ArrayList<AffCompanyHistByEmployee>();

		List<AffCompanyHistItem> comHistItemList = new ArrayList<AffCompanyHistItem>();

		comHistItemList.add(new AffCompanyHistItem(IdentifierUtil.randomUniqueId(), false,
				new DatePeriod(command.getHireDate(), GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"))));

		comHistList.add(new AffCompanyHistByEmployee(employeeId, comHistItemList));

		AffCompanyHist newComHist = new AffCompanyHist(personId, comHistList);

		this.companyHistRepo.add(newComHist);

		PeregInputContainer inputContainer = new PeregInputContainer(personId, employeeId, inputs);

		this.commandFacade.add(inputContainer);

		// add new user
		String passwordHash = PasswordHash.generate(command.getPassword(), userId);
		User newUser = User.createFromJavaType(userId, passwordHash, command.getLoginId(),
				AppContexts.user().contractCode(), GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), false, false,
				null, command.getEmployeeName(), employeeId);

		this.userRepository.addNewUser(newUser);

		// register avatar
		PersonFileManagement perFile = PersonFileManagement.createFromJavaType(personId, command.getAvatarId(),
				TypeFile.AVATAR_FILE.value, null, null);

		this.perFileManagementRepository.insert(perFile);

		// Update employee registration history

		String companyId = AppContexts.user().companyId();

		String currentEmpId = AppContexts.user().employeeId();

		Optional<EmpRegHistory> optRegHist = this.empHisRepo.getLastRegHistory(currentEmpId);

		EmpRegHistory newEmpRegHistory = EmpRegHistory.createFromJavaType(employeeId, companyId, GeneralDate.today(),
				currentEmpId);

		if (optRegHist.isPresent()) {

			this.empHisRepo.update(newEmpRegHistory);

		} else {

			this.empHisRepo.add(newEmpRegHistory);

		}

	}

	private void setItemValue(List<ItemsByCategory> ctgList, String ctgCode, String ItemCode, String NewValue,
			int saveType) {
		if (!CollectionUtil.isEmpty(ctgList)) {
			ItemsByCategory PersonCtg = ctgList.stream().filter(x -> x.getCategoryCd().equals(ctgCode)).findFirst()
					.get();

			Optional<ItemValue> PersonNameItemOpt = PersonCtg.getItems().stream()
					.filter(x -> x.itemCode().equals(ItemCode)).findFirst();

			if (PersonNameItemOpt.isPresent()) {
				ItemValue PersonNameItem = PersonNameItemOpt.get();
				PersonCtg.getItems().remove(PersonNameItem);
				PersonCtg.getItems().add(
						new ItemValue(PersonNameItem.definitionId(), PersonNameItem.itemCode(), NewValue, saveType));
			}
		}

	}

	private void mergeData(List<SettingItemDto> dataList, List<ItemsByCategory> inputs) {

		dataList.forEach(x -> {

			x.setSaveData(SettingItemDto.createSaveDataDto(x.getSaveData().getSaveDataType().value,
					getItemValueById(inputs, x.getItemCode())));
		});

	}

	private Object getItemValueById(List<ItemsByCategory> inputs, String itemCode) {
		Object returnString = null;

		for (ItemsByCategory ctg : inputs) {

			Optional<ItemValue> optItem = ctg.getItems().stream().filter(x -> x.itemCode().equals(itemCode))
					.findFirst();
			if (optItem.isPresent()) {
				returnString = optItem.get().value();
				break;
			}

		}
		return returnString;

	}

	private ItemsByCategory createNewItemsByCategoryCode(List<SettingItemDto> dataList, String categoryCd) {

		List<ItemValue> items = new ArrayList<ItemValue>();
		getAllItemInCategoryByCode(dataList, categoryCd).forEach(item -> {
			items.add(new ItemValue(item.getItemDefId(), item.getCategoryCode(), item.getValueAsString(),
					item.getSaveData().getSaveDataType().value));
		});
		if (CollectionUtil.isEmpty(items)) {
			return null;
		}
		return new ItemsByCategory(categoryCd, null, items);
	}

	private List<SettingItemDto> getAllItemInCategoryByCode(List<SettingItemDto> sourceList, String categoryCode) {
		return sourceList.stream().filter(x -> x.getCategoryCode().equals(categoryCode)).collect(Collectors.toList());
	}

}
