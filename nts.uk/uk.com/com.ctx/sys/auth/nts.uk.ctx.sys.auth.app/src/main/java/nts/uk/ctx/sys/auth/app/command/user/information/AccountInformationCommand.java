package nts.uk.ctx.sys.auth.app.command.user.information;

import lombok.Data;
import nts.uk.ctx.sys.auth.app.command.user.information.anniversary.AnniversaryNoticeDto;
import nts.uk.ctx.sys.auth.app.command.user.information.avatar.UserAvatarDto;
import nts.uk.ctx.sys.auth.app.command.user.information.employee.contact.EmployeeContactDto;
import nts.uk.ctx.sys.auth.app.command.user.information.personal.contact.PersonalContactDto;
import nts.uk.ctx.sys.auth.app.command.user.information.user.UserDto;

import java.util.List;

/**
 * Command アカウント情報を登録する
 */
@Data
public class AccountInformationCommand {

    /**
     * ユーザを変更する
     */
    private UserDto userChange;

    /**
     * 個人の顔写真を登録する
     */
    private UserAvatarDto avatar;

    /**
     * 記念日を削除する + 個人の記念日情報を登録する
     */
    private List<AnniversaryNoticeDto> anniversaryNotices;

    /**
     * 個人連絡先を登録する
     */
    private PersonalContactDto personalContact;

    /**
     * 社員連絡先を登録する
     */
    private EmployeeContactDto employeeContact;
}
