package nts.uk.ctx.sys.auth.app.command.user.information;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.auth.app.command.user.information.anniversary.AnniversaryNoticeDto;
import nts.uk.ctx.sys.auth.app.command.user.information.avatar.UserAvatarDto;
import nts.uk.ctx.sys.auth.app.command.user.information.employee.contact.EmployeeContactDto;
import nts.uk.ctx.sys.auth.app.command.user.information.personal.contact.PersonalContactDto;
import nts.uk.ctx.sys.auth.app.command.user.information.user.UserDto;
import nts.uk.ctx.sys.auth.dom.adapter.checkpassword.CheckBeforeChangePassImport;
import nts.uk.ctx.sys.auth.dom.adapter.checkpassword.CheckBeforePasswordAdapter;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryRepository;
import nts.uk.ctx.sys.auth.dom.avatar.AvatarRepository;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatar;
import nts.uk.ctx.sys.auth.dom.employee.contact.EmployeeContact;
import nts.uk.ctx.sys.auth.dom.employee.contact.EmployeeContactRepository;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.personal.contact.PersonalContact;
import nts.uk.ctx.sys.auth.dom.personal.contact.PersonalContactRepository;
import nts.uk.ctx.sys.auth.dom.user.HashPassword;
import nts.uk.ctx.sys.auth.dom.user.Language;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * アカウント情報を登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AccountInformationCommandHandler extends CommandHandler<AccountInformationCommand> {

	@Inject
	private UserRepository userRepository;

	@Inject
	private AvatarRepository avatarRepository;

	@Inject
	private AnniversaryRepository anniversaryRepository;

	@Inject
	private EmployeeContactRepository employeeContactRepository;

	@Inject
	private PersonalContactRepository personalContactRepository;

	@Inject
	private CheckBeforePasswordAdapter checkBeforePasswordAdapter;

	@Inject
	private PasswordChangeLogRepository passwordChangeLogRepository;

	@Override
	protected void handle(CommandHandlerContext<AccountInformationCommand> commandHandlerContext) {

		AccountInformationCommand command = commandHandlerContext.getCommand();
		String userId = AppContexts.user().userId();
		String personalId = AppContexts.user().personId();
		String employeeId = AppContexts.user().employeeId();

		// cmd 1 : ユーザを変更する + cmd 2 : ログを記載する
		if (!this.isPasswordTabNull(command.getUserChange())) {
			this.userChangeHandler(command.getUserChange(), userId);
		}
		this.updateLanguage(command.getUserChange().getLanguage(), userId);

		// cmd 3 : 個人の顔写真を登録する
		String fileId = command.getAvatar().getFileId();
		if (fileId != null) {
			this.updateAvatar(command.getAvatar());
		}

		// cmd 4+5 : 記念日を削除する + 個人の記念日情報を登録する
		this.updateAnniversary(command.getAnniversaryNotices(), personalId);

		// cmd 6 : 個人連絡先を登録する
		this.updateEmployeeContact(command.getEmployeeContact(), employeeId);

		// cmd 7 : 社員連絡先を登録する
		this.updatePersonalContact(command.getPersonalContact(), personalId);
	}

	// ユーザを変更する - check is password tab is null
	private boolean isPasswordTabNull(UserDto user) {
		return (user.getCurrentPassword().trim().length() == 0 && user.getNewPassword().trim().length() == 0
				&& user.getConfirmPassword().trim().length() == 0);
	}

	// TODO ユーザを変更する - update language only
	private void updateLanguage(int language, String userId) {
		Optional<User> currentUser = userRepository.getByUserID(userId);
        currentUser.ifPresent(current -> {
           current.setLanguage(EnumAdaptor.valueOf(language, Language.class));
            userRepository.update(current);
        });
	}

	// ユーザを変更する - main
	private void userChangeHandler(UserDto user, String userId) {
		CheckBeforeChangePassImport checkBeforeChangePassImport = checkBeforePasswordAdapter.checkBeforeChangePassword(
				userId, user.getCurrentPassword(), user.getNewPassword(), user.getConfirmPassword());
		if (!checkBeforeChangePassImport.isError()) {
			Optional<User> currentUser = userRepository.getByUserID(userId);
			currentUser.ifPresent(current -> {
				String newPassHash = PasswordHash.generate(user.getNewPassword(), userId);
				HashPassword hashPW = new HashPassword(newPassHash);
				current.setPassword(hashPW);
				userRepository.update(current);
				//isWriteLog == true
				// ログを記載する
				PasswordChangeLog passwordChangeLog = new PasswordChangeLog(current.getLoginID().v(), userId, GeneralDateTime.now(), hashPW);
				passwordChangeLogRepository.add(passwordChangeLog);
			});
		} else {
			throw new BusinessException(checkBeforeChangePassImport.getMessage().get(0).getMessage());
		}
	}


	// 個人の顔写真を登録する - main
	private void updateAvatar(UserAvatarDto userAvatarDto) {
		Optional<UserAvatar> userAvatar = avatarRepository.getAvatarByPersonalId(userAvatarDto.getPersonalId());
		if(userAvatar.isPresent()) {
			userAvatar.get().getMemento(userAvatarDto);
			avatarRepository.update(userAvatar.get());
		} else {
			UserAvatar avatar = new UserAvatar();
			avatar.getMemento(userAvatarDto);
			avatarRepository.insert(avatar);
		}
	}

	// 記念日を削除する + 個人の記念日情報を登録する - main
	private void updateAnniversary(List<AnniversaryNoticeDto> list, String personalId) {
		List<AnniversaryNotice> listFromDatabase = anniversaryRepository.getByPersonalId(personalId);
		List<AnniversaryNotice> listFromClient = list.stream()
				.map(item -> {
					AnniversaryNotice newAnniversary = new AnniversaryNotice();
					newAnniversary.getMemento(item);
					return newAnniversary;
				})
				.collect(Collectors.toList());

		Map<MonthDay, AnniversaryNotice> mapFromDatabase = listFromDatabase.stream()
				.collect(Collectors.toMap(AnniversaryNotice::getAnniversary, Function.identity()));
		
		Map<MonthDay, AnniversaryNotice> mapFromClient = listFromClient.stream()
				.collect(Collectors.toMap(AnniversaryNotice::getAnniversary, Function.identity()));
		
		List<AnniversaryNotice> listCreate = listFromClient.stream()
				.filter(item -> mapFromDatabase.get(item.getAnniversary()) == null)
				.collect(Collectors.toList());
		
		List<AnniversaryNotice> listDelete = listFromDatabase.stream()
				.filter(item -> mapFromClient.get(item.getAnniversary()) == null)
				.collect(Collectors.toList());
		
		List<AnniversaryNotice> listUpdate = listFromClient.stream()
				.filter(item -> mapFromDatabase.get(item.getAnniversary()) != null)
				.map(item -> {
					GeneralDate currentSeenDate =mapFromDatabase.get(item.getAnniversary()).getSeenDate();
					item.updateSeenDate(currentSeenDate);
					return item;
				})
				.collect(Collectors.toList());
		
		anniversaryRepository.insertAll(listCreate);
		anniversaryRepository.deleteAll(listDelete);
		anniversaryRepository.updateAll(listUpdate);
	}

	// 個人連絡先を登録する - main
	private void updateEmployeeContact(EmployeeContactDto employeeContactDto, String employeeId) {
		Optional<EmployeeContact> employeeContact = employeeContactRepository.getByEmployeeId(employeeId);
		if (employeeContact.isPresent()) {
			EmployeeContact updateEmployeeContact = new EmployeeContact();
			updateEmployeeContact.getMemento(employeeContactDto);
			employeeContactRepository.update(updateEmployeeContact);
		} else {
			EmployeeContact newEmployeeContact = new EmployeeContact();
			newEmployeeContact.getMemento(employeeContactDto);
			employeeContactRepository.insert(newEmployeeContact);
		}
	}

	// 社員連絡先を登録する - main
	private void updatePersonalContact(PersonalContactDto PersonalContactDto, String PersonalId) {
		Optional<PersonalContact> personalContact = personalContactRepository.getByPersonalId(PersonalId);
		if (personalContact.isPresent()) {
			PersonalContact updatePersonalContact = new PersonalContact();
			updatePersonalContact.getMemento(PersonalContactDto);
			personalContactRepository.update(updatePersonalContact);
		} else {
			PersonalContact newPersonalContact = new PersonalContact();
			newPersonalContact.getMemento(PersonalContactDto);
			personalContactRepository.insert(newPersonalContact);
		}
	}
}
