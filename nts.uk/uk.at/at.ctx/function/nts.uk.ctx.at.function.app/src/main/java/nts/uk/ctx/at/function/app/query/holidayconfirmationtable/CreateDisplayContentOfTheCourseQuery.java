package nts.uk.ctx.at.function.app.query.holidayconfirmationtable;


import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.AffCompanyHistImport;
import nts.uk.ctx.at.function.dom.adapter.EmployeeHistWorkRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ManagermentAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 代休確認表の表示内容を作成する
 * UKDesign.UniversalK.就業.KDR_休暇帳表.KDR003_代休確認表.A:代休確認表.A:メニュー別OCD.代休確認表を作成する.代休確認表の表示内容を作成する.代休確認表の表示内容を作成する
 * @author chinhhm
 */
@Stateless
public class CreateDisplayContentOfTheCourseQuery {

    @Inject
    EmployeeHistWorkRecordAdapter employeeHistWorkRecordAdapter;
    @Inject
    private ClosureRepository closureRepo;
    @Inject
    private ClosureEmploymentRepository closureEmploymentRepo;
    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;
    @Inject
    private RemainNumberTempRequireService requireService;
    public  Object getDisplayContent(GeneralDate referenceDate, List<EmployeeBasicInfoImport> basicInfoImportList,
                                           ManagermentAtr mngAtr, boolean moreSubstituteHolidaysThanHolidays, boolean moreHolidaysThanSubstituteHolidays,
                                           List<AffAtWorkplaceImport> lstAffAtWorkplaceImport, List<WorkplaceInfor> lstWorkplaceInfo) {
        // 1. ① Get(「２」List<社員情報>．社員ID):所属会社履歴（社員別）
        val listSid = basicInfoImportList.stream().map(EmployeeBasicInfoImport::getSid)
                .collect(Collectors.toList());
        val listCompanyHist = employeeHistWorkRecordAdapter.getWplByListSid(listSid);
        // 2. ② Call 社員に対応する締め期間を取得する
        // 社員に対応する締め期間を取得する
        val sids = listCompanyHist.stream().map(AffCompanyHistImport::getEmployeeId)
                .collect(Collectors.toList());
        val cid = AppContexts.user().companyId();
        for (String sid:sids  ) {
            // Call 社員に対応する締め期間を取得する
            DatePeriod period = ClosureService.findClosurePeriod(
                    ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
                    new CacheCarrier(), sid, referenceDate);
            val rq = requireService.createRequire();
            // Call RQ 203
            BreakDayOffRemainMngRefactParam inputRefactor = new BreakDayOffRemainMngRefactParam(
                    cid,
                    sid,
                    period,
                    false,
                    GeneralDate.today(),
                    false,
                    new ArrayList<>(),
                    Optional.empty(),
                    Optional.empty(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Optional.empty(),
                    new FixedManagementDataMonth());

           val  substituteHolidayAggrResult = NumberRemainVacationLeaveRangeQuery
                    .getBreakDayOffMngInPeriod(rq, inputRefactor);
        }
        return null;
    }
}
