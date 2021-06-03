package nts.uk.screen.com.app.find.user.information;


import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.screen.com.app.find.user.information.avatar.UserAvatarDto;
import nts.uk.screen.com.app.find.user.information.employee.contact.EmployeeContactDto;
import nts.uk.screen.com.app.find.user.information.employee.data.management.information.EmployeeDataMngInfoDto;
import nts.uk.screen.com.app.find.user.information.password.changelog.PasswordChangeLogDto;
import nts.uk.screen.com.app.find.user.information.password.policy.PasswordPolicyDto;
import nts.uk.screen.com.app.find.user.information.personal.contact.PersonalContactDto;
import nts.uk.screen.com.app.find.user.information.personal.infomation.PersonDto;
import nts.uk.screen.com.app.find.user.information.setting.UserInformationUseMethodDto;
import nts.uk.screen.com.app.find.user.information.user.UserDto;
import nts.uk.screen.com.app.find.user.information.anniversary.AnniversaryNoticeDto;

import java.util.List;

/**
 * Dto ユーザ情報の表示
 */
@Data
@Builder
public class UserInformationDto {

    /**
     * パスワードポリシー
     */
    PasswordPolicyDto passwordPolicy;

    /**
     * ログイン者が担当者か
     */
    Boolean isInCharge;

    /**
     * ユーザー情報の使用方法
     */
    UserInformationUseMethodDto settingInformation;

    /**
     * 入社日
     */
    GeneralDate hireDate;

    /**
     * ユーザ
     */
    private UserDto user;

    /**
     * 個人
     */
    private PersonDto person;

    /**
     * 個人連絡先
     */
    private PersonalContactDto personalContact;

    /**
     * 社員データ管理情報
     */
    private EmployeeDataMngInfoDto employeeDataMngInfo;

    /**
     * 社員連絡先
     */
    private EmployeeContactDto employeeContact;

    /**
     * パスワード変更ログ
     */
    private PasswordChangeLogDto passwordChangeLog;

    /**
     * 個人の記念日情報
     */
    private List<AnniversaryNoticeDto> anniversaryNotices;

    /**
     * 個人の顔写真
     */
    private UserAvatarDto userAvatar;

    /**
     * 職位名称
     */
    private String positionName;

    /**
     * 職場表示名
     */
    private String wkpDisplayName;

}
