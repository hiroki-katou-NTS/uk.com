package nts.uk.screen.at.app.ksm011.e.command;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.*;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CorrectDeadline;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.apache.commons.lang3.BooleanUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <<Command>> 権限機能制御の設定を登録する
 *
 * @author viet.tx
 */
@Stateless
public class RegisterConfigurationSettingCommand {
    @Inject
    private ScheAuthModifyDeadlineRepository deadlineRepo;

    @Inject
    private ScheModifyAuthCtrlCommonRepository commonRepo;

    @Inject
    private ScheModifyAuthCtrlByWorkPlaceRepository workPlaceRepo;

    @Inject
    private ScheModifyAuthCtrlByPersonRepository personRepo;

    public void registerConfigurationSetting(RegisterConfigurationSettingDto request) {
        String companyId = AppContexts.user().companyId();
        String roleId = request.getRoleId();

        //1. get(会社ID、ロールID) : Optional<スケジュール修正のの修正期限>
        ScheAuthModifyDeadline scheAuthModifyDeadline = new ScheAuthModifyDeadline(
                roleId,
                EnumAdaptor.valueOf(request.getUseAtr(), NotUseAtr.class),
                new CorrectDeadline(request.getDeadLineDay()));
        Optional<ScheAuthModifyDeadline> deadline = deadlineRepo.get(companyId, roleId);
        if (deadline.isPresent()) {
            deadlineRepo.update(companyId, scheAuthModifyDeadline);
        } else {
            deadlineRepo.insert(companyId, scheAuthModifyDeadline);
        }

        // 4.スケジュール修正の共通の権限
        List<ScheModifyAuthCtrlCommon> scheModifyAuthCtrlCommons = request.getScheModifyCtrlCommons()
                .stream().map(x -> new ScheModifyAuthCtrlCommon(companyId, roleId, x.getFunctionNo(), BooleanUtils.toBoolean(x.getAvailable())))
                .collect(Collectors.toList());
        List<ScheModifyAuthCtrlCommon> commons = commonRepo.getAllByRoleId(companyId, roleId);
        if (commons.isEmpty()) {
            scheModifyAuthCtrlCommons.forEach(c -> commonRepo.insert(c));
        } else {
            scheModifyAuthCtrlCommons.forEach(c -> commonRepo.update(c));
        }

        // 8.get List<スケジュール修正職場別の権限制御>
        List<ScheModifyAuthCtrlByWorkplace> scheModifyAuthCtrlWkpls = request.getScheModifyByWorkplaces()
                .stream().map(x -> new ScheModifyAuthCtrlByWorkplace(companyId, roleId, x.getFunctionNo(), BooleanUtils.toBoolean(x.getAvailable())))
                .collect(Collectors.toList());
        List<ScheModifyAuthCtrlByWorkplace> workplaces = workPlaceRepo.getAllByRoleId(companyId, roleId);
        if (workplaces.isEmpty()) {
            scheModifyAuthCtrlWkpls.forEach(w -> workPlaceRepo.insert(w));
        } else {
            scheModifyAuthCtrlWkpls.forEach(w -> workPlaceRepo.update(w));
        }

        // 12. get List<スケジュール修正個人別の権限制御>
        List<ScheModifyAuthCtrlByPerson> scheModifyAuthCtrlPersons = request.getScheModifyByPersons()
                .stream().map(x -> new ScheModifyAuthCtrlByPerson(companyId, roleId, x.getFunctionNo(), BooleanUtils.toBoolean(x.getAvailable())))
                .collect(Collectors.toList());

        List<ScheModifyAuthCtrlByPerson> persons = personRepo.getAllByRoleId(companyId, roleId);
        if (persons.isEmpty()) {
            scheModifyAuthCtrlPersons.forEach(p -> personRepo.insert(p));
        } else {
            scheModifyAuthCtrlPersons.forEach(p -> personRepo.update(p));
        }
    }
}
