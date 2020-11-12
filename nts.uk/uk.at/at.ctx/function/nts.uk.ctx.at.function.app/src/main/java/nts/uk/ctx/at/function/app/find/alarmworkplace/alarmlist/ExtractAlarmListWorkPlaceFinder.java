package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.就業.KAL_アラームリスト.KAL011_アラームリスト(職場別)
 */
@Stateless
public class ExtractAlarmListWorkPlaceFinder {

    @Inject
    private AlarmPatternSettingWorkPlaceRepository alarmPatternSettingWorkPlaceRepo;
    @Inject
    private EmploymentAdapter employmentAdapter;
    @Inject
    private WorkClosureQueryProcessor workClosureQueryProcessor;
    @Inject
    private ClosureRepository closureRepo;

    /**
     * アラームリストの初期起動
     */
    public InitActiveAlarmListDto initActiveAlarmList() {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();

        // ドメインモデル「アラームリストパターン設定(職場別)」を取得する。
        List<AlarmPatternSettingWorkPlace> alarmPatterns = alarmPatternSettingWorkPlaceRepo.findByCompanyId(cid);

        // アルゴリズム「社員IDと基準日から社員の雇用コードを取得」を実行する。
        Optional<EmploymentHistoryImported> empHistory = this.employmentAdapter.getEmpHistBySid(cid, sid,
                GeneralDate.today());

        if (!empHistory.isPresent()) {
            return new InitActiveAlarmListDto(null, alarmPatterns);
        }

        String employmentCode = empHistory.get().getEmploymentCode();
        // 雇用に紐づく締めIDを取得する
        Integer closureId = workClosureQueryProcessor.findClosureByEmploymentCode(employmentCode);
        // 当月の年月を取得する。
        Optional<Closure> closureOpt = closureRepo.findById(cid, closureId);
        return new InitActiveAlarmListDto(employmentCode, alarmPatterns);
    }

    /**
     * アラームリストパータン設定を選択する
     */
    public List<CheckConditionDto> getCheckConditions() {
        List<CheckCondition> checkConList = new ArrayList<>();

        return checkConList.stream().map(CheckConditionDto::new).collect(Collectors.toList());
    }
}
