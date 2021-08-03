package nts.uk.ctx.at.function.ws.arbitraryperiodsummarytable;


import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable.*;
import nts.uk.ctx.at.function.app.find.arbitraryperiodsummarytable.GetRoleArbitraryScheduleFinder;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.GetOutputSettingDetailArbitraryQuery;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.GetOutputSettingListArbitraryQuery;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.OutputSettingArbitraryDto;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleWhetherLoginPubImported;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.ws.outputworkstatustable.dto.SettingIdParams;
import nts.uk.ctx.at.function.ws.outputworkstatustable.dto.SettingParams;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/function/kwr")
@Produces("application/json")
public class OutputSettingListArbitraryWebService extends WebService {
    @Inject
    private GetOutputSettingListArbitraryQuery arbitraryQuery;

    @Inject
    private GetOutputSettingDetailArbitraryQuery detailArbitraryQuery;

    @Inject
    private CreateOutputSettingCommandHandler createOutputSettingCommandHandler;

    @Inject
    private UpdateOutputSettingCommandHandle updateOutputSettingCommandHandle;

    @Inject
    private DuplicateOutputSettingCommandHandler duplicateCommandHandler;

    @Inject
    private DeleteOutputSettingCommandHandler deleteCommandHandler;

    @Inject
    private GetRoleArbitraryScheduleFinder getRoleArbitraryScheduleFinder;

    @Inject
    private ClosureEmploymentRepository closureEmploymentRepo;

    @Inject
    private ClosureRepository closureRepo;

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;

    @POST
    @Path("007/a/listoutputsetting")
    public List<OutputSettingArbitraryDto> getListWorkStatus(SettingParams params) {
        return arbitraryQuery.getListOutputSetting(EnumAdaptor.valueOf(params.getSetting(), SettingClassificationCommon.class));
    }

    @POST
    @Path("007/a/getroleinfor")
    public RoleWhetherLoginPubImported getRoleInfor() {
        return getRoleArbitraryScheduleFinder.getRoleInfor();
    }

    @POST
    @Path("007/b/detailoutputsetting")
    public OutputSettingDetailArbitraryDto getDetail(SettingIdParams params) {
        val domain = detailArbitraryQuery.getDetail(params.getSettingId());
        if (domain != null) {
            return new OutputSettingDetailArbitraryDto(
                    domain.getSettingId(),
                    domain.getCode().v(),
                    domain.getOutputItemList(),
                    domain.getName().v(),
                    domain.getStandardFreeClassification(),
                    domain.getEmployeeId().isPresent() ? domain.getEmployeeId().get() : null
            );
        }
        return null;
    }

    @POST
    @Path("007/b/create")
    public void create(CreateOutputSettingCommand dto) {
        createOutputSettingCommandHandler.handle(dto);
    }

    @POST
    @Path("007/b/update")
    public void update(UpdateOutputSettingCommand dto) {
        updateOutputSettingCommandHandle.handle(dto);
    }

    @POST
    @Path("007/b/delete")
    public void delete(DeleteOutputSettingCommand dto) {
        deleteCommandHandler.handle(dto);
    }

    @POST
    @Path("007/c/duplicate")
    public void duplicate(DuplicateOutputSettingCommand dto) {
        duplicateCommandHandler.handle(dto);
    }

    @POST
    @Path("007/a/getperiod")
    public GeneralDate getPeriod() {
        // 社員に対応する締め期間を取得する(Lấy closurePeriod ứng với employee)
        val employeeID = AppContexts.user().employeeId();
        val ymd = GeneralDate.today();
        DatePeriod period = ClosureService.findClosurePeriod(
                ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
                new CacheCarrier(), employeeID, ymd);
        if (period != null) {
            return period.end();
        } else {
            return null;
        }
    }
}
