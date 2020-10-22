package nts.uk.ctx.sys.auth.app.command.user.information;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
        boolean isWriteLog = false;

        //cmd 1 : ユーザを変更する
        if (!this.isPasswordTabNull(command.getUserChange())) {
            isWriteLog = this.userChangeHandler(command.getUserChange(), userId);
        } else {
            this.updateLanguage(command.getUserChange().getLanguage(), userId);
        }

        //cmd 2 : ログを記載する
        if (isWriteLog) {
            this.passwordChangeWriteLog(command.getUserChange().getNewPassword(), userId);
        }

        //cmd 3 : 個人の顔写真を登録する
        String fileId = command.getAvatar().getFileId();
        if (fileId.trim().length() != 0) {
            this.updateAvatar(command.getAvatar());
        }

        //TODO cmd 4+5 : 記念日を削除する + 個人の記念日情報を登録する
//        this.updateAnniversary(command.getAnniversaryNotices(), personalId);

        //cmd 6 : 個人連絡先を登録する
        this.updateEmployeeContact(command.getEmployeeContact(), employeeId);

        //cmd 7 : 社員連絡先を登録する
        this.updatePersonalContact(command.getPersonalContact(), personalId);
    }

    //ユーザを変更する - check is password tab is null
    private boolean isPasswordTabNull(UserDto user) {
        return (
                user.getCurrentPassword().trim().length() == 0
                        && user.getNewPassword().trim().length() == 0
                        && user.getConfirmPassword().trim().length() == 0
        );
    }

    //ユーザを変更する - update language only
    private void updateLanguage(int language, String userId) {
        Optional<User> currentUser = userRepository.getByUserID(userId);
        currentUser.ifPresent(current -> {
            current.setLanguage(EnumAdaptor.valueOf(language, Language.class));
            userRepository.update(current);
        });
    }

    //ユーザを変更する - main
    private boolean userChangeHandler(UserDto user, String userId) {
        AtomicBoolean isWriteLog = new AtomicBoolean(false);
        CheckBeforeChangePassImport checkBeforeChangePassImport = checkBeforePasswordAdapter
                .checkBeforeChangePassword(userId,
                        user.getCurrentPassword(),
                        user.getNewPassword(),
                        user.getConfirmPassword());
        if (!checkBeforeChangePassImport.isError()) {
            Optional<User> currentUser = userRepository.getByUserID(userId);
            currentUser.ifPresent(current -> {
                current.setPassword(new HashPassword(user.getNewPassword()));
                current.setLanguage(EnumAdaptor.valueOf(user.getLanguage(), Language.class));
                userRepository.update(current);
                isWriteLog.set(true);
            });
        }
        return isWriteLog.get();
    }

    //ログを記載する - main
    private void passwordChangeWriteLog(String password, String userId) {
        PasswordChangeLog log = passwordChangeLogRepository.findByUserId(userId, 1).get(0);
        PasswordChangeLog passwordChangeLog = new PasswordChangeLog(log.getLogID(), userId, GeneralDateTime.now(), new HashPassword(password));
        passwordChangeLogRepository.add(passwordChangeLog);
    }

    //個人の顔写真を登録する - main
    private void updateAvatar(UserAvatarDto userAvatarDto) {
        Optional<UserAvatar> userAvatar = avatarRepository.getAvatarByPersonalId(userAvatarDto.getPersonalId());
        userAvatar.ifPresent(avatar -> {
            avatar.setMemento(userAvatarDto);
            avatarRepository.update(avatar);
        });
    }

    //記念日を削除する + 個人の記念日情報を登録する - main
//    private void updateAnniversary(List<AnniversaryNoticeDto> listFromClient, String personalId) {
//        List<AnniversaryNotice> listFromDatabase = anniversaryRepository.getByPersonalId(personalId);
//        //delete or update
//        for (AnniversaryNoticeDto fromClient : listFromClient) {
//            for (AnniversaryNotice fromDatabase : listFromDatabase)
//                //delete
//                if (fromDatabase.getAnniversary().compareTo(fromClient.getAnniversary()) != 0) {
//                    anniversaryRepository.delete(fromDatabase);
//                    //update
//                } else if (fromDatabase.getAnniversary().compareTo(fromClient.getAnniversary()) == 0) {
//                    GeneralDate currentSeenDate = fromDatabase.getSeenDate();
//                    fromClient.setSeenDate(currentSeenDate);
//                    AnniversaryNotice updateAnniversary = new AnniversaryNotice();
//                    updateAnniversary.setMemento(fromClient);
//                    anniversaryRepository.update(updateAnniversary);
//                }
//        }
//        //create
//        for (AnniversaryNotice fromDatabase : listFromDatabase) {
//            for (AnniversaryNoticeDto fromClient : listFromClient)
//                if (fromClient.getAnniversary().compareTo(fromDatabase.getAnniversary()) != 0) {
//                    AnniversaryNotice newAnniversary = new AnniversaryNotice();
//                    newAnniversary.setMemento(fromClient);
//                    anniversaryRepository.insert(newAnniversary);
//                }
//        }
//    }

    //個人連絡先を登録する - main
    private void updateEmployeeContact(EmployeeContactDto employeeContactDto, String employeeId) {
        Optional<EmployeeContact> employeeContact = employeeContactRepository.getByEmployeeId(employeeId);
        if (employeeContact.isPresent()) {
            EmployeeContact updateEmployeeContact = new EmployeeContact();
            updateEmployeeContact.setMemento(employeeContactDto);
            employeeContactRepository.update(updateEmployeeContact);
        } else {
            EmployeeContact newEmployeeContact = new EmployeeContact();
            newEmployeeContact.setMemento(employeeContactDto);
            employeeContactRepository.insert(newEmployeeContact);
        }
    }

    //社員連絡先を登録する - main
    private void updatePersonalContact(PersonalContactDto PersonalContactDto, String PersonalId) {
        Optional<PersonalContact> personalContact = personalContactRepository.getByPersonalId(PersonalId);
        if (personalContact.isPresent()) {
            PersonalContact updatePersonalContact = new PersonalContact();
            updatePersonalContact.setMemento(PersonalContactDto);
            personalContactRepository.update(updatePersonalContact);
        } else {
            PersonalContact newPersonalContact = new PersonalContact();
            newPersonalContact.setMemento(PersonalContactDto);
            personalContactRepository.insert(newPersonalContact);
        }
    }
}
