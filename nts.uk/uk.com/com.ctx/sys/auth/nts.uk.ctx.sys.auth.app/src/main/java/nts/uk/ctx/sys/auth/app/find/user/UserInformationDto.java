package nts.uk.ctx.sys.auth.app.find.user;

import nts.uk.ctx.sys.auth.app.find.anniversary.AnniversaryNoticeDto;
import nts.uk.ctx.sys.auth.app.find.avatar.UserAvatarDto;
import nts.uk.ctx.sys.auth.app.find.employee.contact.EmployeeContactDto;
import nts.uk.ctx.sys.auth.app.find.personal.contact.PersonalContactDto;

import java.util.List;

/**
 * Dto ユーザ情報の表示
 */
public class UserInformationDto {

    /**
     *パスワードポリシー
     */

    /**
     *ユーザ
     */
    private UserFullDto user;

    /**
     *ユーザー情報の使用方法
     */

    /**
     *ログイン者が担当者か
     */

    /**
     *個人
     */

    /**
     *個人連絡先
     */
    private PersonalContactDto personalContact;

    /**
     *社員データ管理情報
     */

    /**
     *社員連絡先
     */
    private EmployeeContactDto employeeContact;

    /**
     * パスワード変更ログ
     */


    /**
     *個人の記念日情報
     */
    private List<AnniversaryNoticeDto> anniversaryNotices;

    /**
     *個人の顔写真
     */
    private UserAvatarDto userAvatar;

    /**
     *入社日
     */


    /**
     *職位名称
     */

    /**
     *職場表示名
     */


}
