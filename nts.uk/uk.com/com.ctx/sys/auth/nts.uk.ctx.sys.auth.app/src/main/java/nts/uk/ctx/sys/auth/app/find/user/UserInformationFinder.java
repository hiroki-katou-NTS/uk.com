package nts.uk.ctx.sys.auth.app.find.user;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInformationImport;
import nts.uk.ctx.sys.auth.dom.adapter.employee.service.EmployeeService;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryRepository;
import nts.uk.ctx.sys.auth.dom.avatar.AvatarRepository;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatar;
import nts.uk.ctx.sys.auth.dom.employee.contact.EmployeeContact;
import nts.uk.ctx.sys.auth.dom.employee.contact.EmployeeContactRepository;
import nts.uk.ctx.sys.auth.dom.personal.contact.PersonalContact;
import nts.uk.ctx.sys.auth.dom.personal.contact.PersonalContactRepository;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserInformationFinder {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PersonalContactRepository personalContactRepository;

    @Inject
    private EmployeeContactRepository employeeContactRepository;

    @Inject
    private AnniversaryRepository anniversaryRepository;

    @Inject
    private AvatarRepository avatarRepository;

    public UserInformationDto getUserInformation() {

        String loginUserId = AppContexts.user().userId();
        String loginPersonalId = AppContexts.user().personId();
        String loginEmployeeId = AppContexts.user().employeeId();

        //TODO SQ1 - get from CMM049

        //SQ2 - call DomainService 社員情報を取得する
        EmployeeInformationImport employeeInformation = employeeService.getEmployeeInformation(loginEmployeeId, GeneralDate.today());

        //TODO SQ3 - Handle ユーザ情報の使用方法.Empty (pending because of CMM049)

        //SQ4 - get ユーザ
        User loginUser = userRepository.getByUserID(loginUserId).orElse(null);

        //TODO SQ5 - get 個人

        //TODO SQ6 - get 社員データ管理情報

        //SQ7 - get 個人連絡先
        PersonalContact personalContact = personalContactRepository.getByPersonalId(loginPersonalId).orElse(null);

        //TODO SQ8 - call 所属会社履歴

        //SQ9 - get 個人の記念日情報
        List<AnniversaryNotice> anniversaryNoticeList = anniversaryRepository.getByPersonalId(loginPersonalId);

        //TODO SQ10 - get パスワード変更ログ

        //TODO SQ11 - get パスワードポリシー

        //TODO SQ12 - get 社員連絡先
        EmployeeContact employeeContact = employeeContactRepository.getByEmployeeId(loginEmployeeId).orElse(null);

        //SQ13 - get 個人の顔写真
        UserAvatar userAvatar = avatarRepository.getAvatarByPersonalId(loginPersonalId).orElse(null);
        return null;
    }
}
