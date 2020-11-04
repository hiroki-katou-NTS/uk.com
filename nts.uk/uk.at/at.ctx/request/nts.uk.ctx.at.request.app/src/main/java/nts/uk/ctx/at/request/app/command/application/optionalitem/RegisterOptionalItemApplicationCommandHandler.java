package nts.uk.ctx.at.request.app.command.application.optionalitem;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Stateless
@Transactional
public class RegisterOptionalItemApplicationCommandHandler extends CommandHandler<RegisterOptionalItemApplicationCommand> {

    @Inject
    private NewBeforeRegister registerBefore;

    @Inject
    private OptionalItemApplicationRepository repository;

    @Inject
    private ApplicationApprovalService appRepository;

    @Inject
    private RegisterAtApproveReflectionInfoService registerService;

    @Inject
    private NewAfterRegister newAfterRegister;

    @Inject
    private OptionalItemRepository optionalItemRepository;


    @Override
    protected void handle(CommandHandlerContext<RegisterOptionalItemApplicationCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();
        /*登録時チェック処理（全申請共通）*/
        RegisterOptionalItemApplicationCommand command = commandHandlerContext.getCommand();
        OptionalItemApplication domain = command.getOptItemAppCommand().toDomain();
        boolean register = false;
        List<AnyItemValue> optionalItems = domain.getOptionalItems();
        List<Integer> collect = optionalItems.stream().map(anyItemNo -> anyItemNo.getItemNo().v()).collect(Collectors.toList());
        Map<Integer, OptionalItem> optionalItemMap = optionalItemRepository.findByListNos(cid, collect).stream().collect(Collectors.toMap(optionalItem -> optionalItem.getOptionalItemNo().v(), item -> item));
        for (Iterator<AnyItemValue> iterator = optionalItems.iterator(); iterator.hasNext(); ) {
            AnyItemValue inputOptionalItem = iterator.next();
            /* Kiểm tra giá trị nằm trong giới hạn, vượt ra ngoài khoảng giới hạn thì thông báo lỗi Msg_1692 */
            /* kiểm tra bội của đơn vị, không phải là bội thì thông báo lỗi Msg_1693*/
            OptionalItem optionalItem = optionalItemMap.get(inputOptionalItem.getItemNo().v());
            CalcResultRange range = optionalItem.getCalcResultRange();
            if (inputOptionalItem.getAmount().isPresent()) {
                Integer amountLower = null;
                Integer amountUpper = null;
                Integer amount = inputOptionalItem.getAmount().get().v();
                if (range.getAmountRange().isPresent() && range.getAmountRange().get().getLowerLimit().isPresent()) {
                    amountLower = range.getAmountRange().get().getLowerLimit().get().v();
                }
                if (range.getAmountRange().isPresent() && range.getAmountRange().get().getUpperLimit().isPresent()) {
                    amountLower = range.getAmountRange().get().getUpperLimit().get().v();
                }
                if ((range.getLowerLimit().isSET() && amountLower != null && amountLower.compareTo(amount) > 0)
                        || (range.getUpperLimit().isSET() && amountUpper != null && amountUpper.compareTo(amount) < 0)) {
                    throw new BusinessException("Msg_1692", "KAF020_22");
                }
                register = true;
            }
            if (inputOptionalItem.getTimes().isPresent()) {
                Double numberLower = null;
                Double numberUpper = null;
                if (range.getNumberRange().isPresent() && range.getNumberRange().get().getLowerLimit().isPresent()) {
                    numberLower = range.getNumberRange().get().getLowerLimit().get().v();
                }
                if (range.getNumberRange().isPresent() && range.getNumberRange().get().getUpperLimit().isPresent()) {
                    numberUpper = range.getNumberRange().get().getUpperLimit().get().v();
                }
                if ((range.getLowerLimit().isSET() && numberLower != null && numberLower.compareTo(inputOptionalItem.getTimes().get().v().doubleValue()) > 0)
                        || (range.getUpperLimit().isSET() && numberUpper != null && numberUpper.compareTo(inputOptionalItem.getTimes().get().v().doubleValue()) < 0)) {
                    throw new BusinessException("Msg_1692", "KAF020_22");
                }
                register = true;
            }
            if (inputOptionalItem.getTime().isPresent()) {
                Integer timeLower = null;
                Integer timeUpper = null;
                if (range.getTimeRange().isPresent() && range.getTimeRange().get().getLowerLimit().isPresent()) {
                    timeLower = range.getTimeRange().get().getLowerLimit().get().v();
                }
                if (range.getTimeRange().isPresent() && range.getTimeRange().get().getUpperLimit().isPresent()) {
                    timeUpper = range.getTimeRange().get().getUpperLimit().get().v();
                }
                if ((range.getLowerLimit().isSET() && timeLower != null && timeLower.compareTo(inputOptionalItem.getTime().get().v()) > 0)
                        || (range.getUpperLimit().isSET() && timeUpper != null && timeUpper.compareTo(inputOptionalItem.getTime().get().v()) < 0)) {
                    throw new BusinessException("Msg_1692", "KAF020_22");
                }
                register = true;
            }
        }
        /*Không có dữ liệu thì hiển thị lỗi*/
        if (!register) {
            throw new BusinessException("Msg_1691");
        }
        ApplicationDto applicationDto = command.getApplication();
        AppDispInfoStartupOutput appDispInfoStartup = command.getAppDispInfoStartup().toDomain();
        Application application = Application.createFromNew(EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                sid,
                EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                sid,
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
                applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                ));
        registerBefore.processBeforeRegister_New(cid,
                EmploymentRootAtr.APPLICATION,
                false,
                application,
                null,
                appDispInfoStartup.getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
                Collections.emptyList(),
                appDispInfoStartup);

        appRepository.insertApp(application, command
                .getAppDispInfoStartup().toDomain()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState().isPresent() ? command.getAppDispInfoStartup().toDomain()
                .getAppDispInfoWithDateOutput()
                .getOpListApprovalPhaseState().get() : null);

        domain.setAppID(application.getAppID());
        repository.save(domain);
        /* 2-2.新規画面登録時承認反映情報の整理(register: reflection info setting) */
        registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
        newAfterRegister.processAfterRegister(application.getAppID(),
                appDispInfoStartup.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings().stream().findFirst().get(),
                appDispInfoStartup.getAppDispInfoNoDateOutput().isMailServerSet());
    }

}
