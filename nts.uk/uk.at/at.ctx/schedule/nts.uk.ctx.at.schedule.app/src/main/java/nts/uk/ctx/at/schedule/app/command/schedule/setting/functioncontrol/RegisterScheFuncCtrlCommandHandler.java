package nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.support.SupportFunctionControlRepository;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * スケジュール修正の機能制御を登録する
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM011_スケジュール前準備.C：スケジュール基本の設定.メニュー別OCD.登録時
 *
 * @author viet.tx
 */
@Stateless
@Transactional
public class RegisterScheFuncCtrlCommandHandler extends CommandHandler<RegisterScheFuncCtrlCommand> {

    @Inject
    private ScheFunctionControlRepository scheFunctionControlRepo;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private SupportFunctionControlRepository supportFunctionControlRepository;

    @Override
    protected void handle(CommandHandlerContext<RegisterScheFuncCtrlCommand> commandHandlerContext) {
        RegisterScheFuncCtrlCommand command = commandHandlerContext.getCommand();
        if (command == null) return;

        String companyId = AppContexts.user().companyId();
        Optional<ScheFunctionControl> scheFuncCtrl = scheFunctionControlRepo.get(companyId);

        // get list workType
        List<WorkType> lstWorkType = basicScheduleService.getAllWorkTypeNotAbolished(companyId);

//        List<WorkTypeDto> listWorkTypeDto = lstWorkType.stream().map(WorkTypeDto::new).collect(Collectors.toList());

        if (scheFuncCtrl.isPresent()) {
            scheFunctionControlRepo.update(companyId, command.toDomain(lstWorkType));
        } else {
            scheFunctionControlRepo.insert(companyId, command.toDomain(lstWorkType));
        }

        if (supportFunctionControlRepository.get(companyId) != null){
            supportFunctionControlRepository.update(companyId,command.toSupportFunc());
        }
    }
}
