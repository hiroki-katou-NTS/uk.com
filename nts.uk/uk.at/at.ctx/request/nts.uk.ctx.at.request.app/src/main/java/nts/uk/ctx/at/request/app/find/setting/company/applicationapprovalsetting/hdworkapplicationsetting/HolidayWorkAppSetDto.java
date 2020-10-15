package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import org.apache.commons.lang3.BooleanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayWorkAppSetDto {
    /**
     * 打刻漏れ計算区分
     */
    private int calcStampMiss;

    /**
     * 直行直帰の機能の利用設定
     */
    private int useDirectBounceFunction;

    /**
     * 申請詳細設定
     */
    private ApplicationDetailSettingDto applicationDetailSetting;

    /**
     * 残業休出申請共通設定
     */
    private OvertimeLeaveAppCommonSetDto overtimeLeaveAppCommonSet;

    public static HolidayWorkAppSetDto fromDomain(HolidayWorkAppSet domain) {
        return new HolidayWorkAppSetDto(
                domain.getCalcStampMiss().value,
                BooleanUtils.toInteger(domain.isUseDirectBounceFunction()),
                ApplicationDetailSettingDto.fromDomain(domain.getApplicationDetailSetting()),
                OvertimeLeaveAppCommonSetDto.fromDomain(domain.getOvertimeLeaveAppCommonSet())
        );
    }
}
