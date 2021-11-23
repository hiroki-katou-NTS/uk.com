package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.残業休出申請.残業申請.複数回残業内容
 * 複数回残業内容
 */
@Getter
@AllArgsConstructor
public class OvertimeWorkMultipleTimes {
    // 残業時間帯
    private List<OvertimeHour> overtimeHours;

    // 残業理由
    private List<OvertimeReason> overtimeReasons;

    /**
     * 作成する
     * @param overtimeHours List<残業時間帯>
     * @param overtimeReasons List<残業理由>
     * @return 複数回残業内容
     */
    public static OvertimeWorkMultipleTimes create(List<OvertimeHour> overtimeHours, List<OvertimeReason> overtimeReasons) {
        Set<OvertimeNumber> overtimeNumberSet = overtimeHours.stream().map(OvertimeHour::getOvertimeNumber).collect(Collectors.toSet());
        if (overtimeNumberSet.size() != overtimeHours.size()) {
            throw new BusinessException("Msg_3238");
        }
        overtimeNumberSet = overtimeReasons.stream().map(OvertimeReason::getOvertimeNumber).collect(Collectors.toSet());
        if (overtimeNumberSet.size() != overtimeHours.size()) {
            throw new BusinessException("Msg_3238");
        }
        overtimeHours.sort(Comparator.comparing(OvertimeHour::getOvertimeNumber));
        overtimeReasons.sort(Comparator.comparing(OvertimeReason::getOvertimeNumber));
        return new OvertimeWorkMultipleTimes(overtimeHours, overtimeReasons);
    }

    /**
     * 申請の定型理由を作成する
     * @return Optional<申請定型理由コード>
     */
    public Optional<AppStandardReasonCode> createFixedReason() {
        if (overtimeReasons.isEmpty()) return Optional.empty();
        return overtimeReasons.get(0).getFixedReasonCode();
    }

    /**
     * 申請理由を作成する
     * @return Optional<申請理由>
     */
    public Optional<AppReason> createApplyReason() {
        List<String> appReasons = overtimeReasons.stream().filter(r -> r.getApplyReason().isPresent()).map(r -> r.getApplyReason().get().v()).collect(Collectors.toList());
        if (appReasons.isEmpty()) return Optional.empty();
        return Optional.of(new AppReason(StringUtil.cutOffAsLengthHalf(StringUtils.join(appReasons, "/"), 400)));
    }

    /**
     * 残業時間を計算のために勤務時間を判断する
     * @param timeZones List<時間帯(勤務NO付き)>
     * @return List<時間帯(勤務NO付き)>
     */
    public List<TimeZoneWithWorkNo> getWorkingHoursToCalculateOvertime(List<TimeZoneWithWorkNo> timeZones) {
        return new ArrayList<>();
    }

    /**
     * 残業時間を計算のために休憩時間帯を判断する
     */
    public void getBreakTimeToCalculateOvertime() {

    }


}
