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
import nts.arc.time.GeneralDateTime;
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
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.person.dom.person.info.BloodType;
import nts.uk.ctx.bs.person.dom.person.info.GenderPerson;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
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

	@Inject
	private PersonRepository personRepo;

	AddEmployeeCommand command;
	String employeeId = IdentifierUtil.randomUniqueId();
	String userId = IdentifierUtil.randomUniqueId();
	String personId = IdentifierUtil.randomUniqueId();
	List<ItemsByCategory> inputs;
	String companyId = AppContexts.user().companyId();

	@Override
	protected void handle(CommandHandlerContext<AddEmployeeCommand> context) {

		command = context.getCommand();

		// add newPerson

		addNewPerson();

		// addmngInfo

		addEmployeeDataMngInfo();

		// add AffCompanyHist

		addAffCompanyHist();

		// update input

		inputsProcess();

		// add new User
		addNewUser();

		// register avatar

		addAvatar();

		// Update employee registration history
		updateEmployeeRegHist();

	}

	private void addNewPerson() {

		Person newPerson = Person.createFromJavaType(GeneralDate.min(), BloodType.Unselected.value,
				GenderPerson.Male.value, personId, "", "", command.getEmployeeName(), "", "", "", "", "", "", "", "",
				"", "", "");

		this.personRepo.addNewPerson(newPerson);

	}

	private void inputsProcess() {

		List<SettingItemDto> dataServer = this.layoutFinder.itemListByCreateType(command.getCreateType(),
				command.getInitSettingId(), command.getHireDate(), command.getEmployeeCopyId());

		// merge data from client with dataServer
		mergeData(dataServer, command.getInputs());

		inputs = new ArrayList<ItemsByCategory>();
		List<String> categoryCodeList = commandFacade.getAddCategoryCodeList();
		dataServer.forEach(x -> {

			if (categoryCodeList.indexOf(x.getCategoryCode()) == -1 && x.getCategoryCode().charAt(1) == 'O') {

				categoryCodeList.add(x.getCategoryCode());

			}
		});

		categoryCodeList.forEach(categoryCd -> {

			ItemsByCategory newCtg = createNewItemsByCategoryCode(dataServer, categoryCd);
			if (newCtg != null) {

				inputs.add(newCtg);
			}

		});

		List<ItemsByCategory> updateInputs = inputs.stream()
				.filter(x -> x.getCategoryCd() == "CS00002" || x.getCategoryCd() == "CS00003")
				.collect(Collectors.toList());

		PeregInputContainer updateContainer = new PeregInputContainer(personId, employeeId, updateInputs);

		this.commandFacade.update(updateContainer);

		inputs = inputs.stream().filter(x -> x.getCategoryCd() != "CS00002" && x.getCategoryCd() != "CS00003")
				.collect(Collectors.toList());
		// call add commandFacade
		PeregInputContainer addContainer = new PeregInputContainer(personId, employeeId, inputs);

		this.commandFacade.add(addContainer);
	}

	private void addEmployeeDataMngInfo() {
		// check duplicate employeeCode
		List<EmployeeDataMngInfo> infoList = this.empDataRepo
				.getEmployeeNotDeleteInCompany(AppContexts.user().companyId(), command.getEmployeeCode());

		if (!CollectionUtil.isEmpty(infoList)) {
			throw new BusinessException("Msg_345");
		}

		this.empDataRepo.add(EmployeeDataMngInfo.createFromJavaType(companyId, personId, employeeId,
				command.getEmployeeCode(), EmployeeDeletionAttr.NOTDELETED.value, GeneralDateTime.min(), "", ""));

	}

	private void addAffCompanyHist() {
		List<AffCompanyHistByEmployee> comHistList = new ArrayList<AffCompanyHistByEmployee>();

		List<AffCompanyHistItem> comHistItemList = new ArrayList<AffCompanyHistItem>();

		comHistItemList.add(new AffCompanyHistItem(IdentifierUtil.randomUniqueId(), false,
				new DatePeriod(command.getHireDate(), GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"))));

		comHistList.add(new AffCompanyHistByEmployee(employeeId, comHistItemList));

		AffCompanyHist newComHist = new AffCompanyHist(personId, comHistList);

		this.companyHistRepo.add(newComHist);

	}

	private void addAvatar() {
		if (command.getAvatarId() != "") {
			PersonFileManagement perFile = PersonFileManagement.createFromJavaType(personId, command.getAvatarId(),
					TypeFile.AVATAR_FILE.value, null, null);

			this.perFileManagementRepository.insert(perFile);
		}

	}

	private void updateEmployeeRegHist() {

		String currentEmpId = AppContexts.user().employeeId();

		Optional<EmpRegHistory> optRegHist = this.empHisRepo.getLastRegHistory(currentEmpId);

		EmpRegHistory newEmpRegHistory = EmpRegHistory.createFromJavaType(currentEmpId, companyId, GeneralDate.today(),
				employeeId);

		if (optRegHist.isPresent()) {

			this.empHisRepo.update(newEmpRegHistory);

		} else {

			this.empHisRepo.add(newEmpRegHistory);

		}

	}

	private void addNewUser() {
		// add new user
		String passwordHash = PasswordHash.generate(command.getPassword(), userId);
		User newUser = User.createFromJavaType(userId, passwordHash, command.getLoginId(),
				AppContexts.user().contractCode(), GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), false, false,
				null, command.getEmployeeName(), employeeId);

		this.userRepository.addNewUser(newUser);

	}

	private void mergeData(List<SettingItemDto> dataList, List<ItemsByCategory> inputs) {

		dataList.forEach(x -> {

			x.setSaveData(SettingItemDto.createSaveDataDto(x.getSaveData().getSaveDataType().value,
					getItemValueById(inputs, x.getItemCode())));
		});

	}

	private String getItemValueById(List<ItemsByCategory> inputs, String itemCode) {
		String returnString = "";

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
			items.add(new ItemValue(item.getItemDefId(), item.getItemCode(), item.getValueAsString(),
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
