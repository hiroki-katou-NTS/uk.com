package nts.uk.ctx.at.request.app.command.application.businesstrip;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateBusinessTripCommandHandler extends CommandHandlerWithResult<UpdateBusinessTripCommand, ProcessResult> {

    @Inject
    BusinessTripRepository businessTripRepository;

    @Inject
    private DetailBeforeUpdate beforeRegisterRepo;

    @Inject
    private ApplicationRepository applicationRepository;

    @Inject
    private DetailAfterUpdate detailAfterUpdate;

    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

    @Override
    protected ProcessResult handle(CommandHandlerContext<UpdateBusinessTripCommand> context) {
        UpdateBusinessTripCommand command = context.getCommand();
        Application application = command.getApplicationDto().toDomain();
        BusinessTripInfoOutput businessTripInfoOutput = command.getBusinessTripInfoOutputDto().toDomain();
        BusinessTrip businessTrip = command.getBusinessTripDto().toDomain(application);
        String cid = AppContexts.user().companyId();
        // アルゴリズム「4-1.詳細画面登録前の処理」を実行する
//        this.detailBeforeUpdate.processBeforeDetailScreenRegistration(
//                cid,
//                application.getEmployeeID(),
//                application.getAppDate(),
//                1,
//                application.getAppID(),
//                application.getPrePostAtr(),
//                application.getVersion(),
//                holidayWorkDomain.getWorkTypeCode() == null ? null : holidayWorkDomain.getWorkTypeCode().v(),
//                holidayWorkDomain.getWorkTimeCode() == null ? null : holidayWorkDomain.getWorkTimeCode().v());*/

        applicationRepository.update(application);
        businessTripRepository.update(businessTrip);

        List<GeneralDate> listDate = businessTrip.getInfos().stream().map(i -> i.getDate()).collect(Collectors.toList());
        // 暫定データの登録
//		interimRemainDataMngRegisterDateChange.registerDateChange(companyId, application.getEmployeeID(), listDate);

        // アルゴリズム「4-2.詳細画面登録後の処理」を実行する
        return detailAfterUpdate.processAfterDetailScreenRegistration(cid, application.getAppID());
    }
}
