package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetCommand;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import org.apache.commons.lang3.BooleanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayWorkAppSetCommand {
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
    private ApplicationDetailSettingCommand applicationDetailSetting;

    /**
     * 残業休出申請共通設定
     */
    private OvertimeLeaveAppCommonSetCommand overtimeLeaveAppCommonSet;

    public HolidayWorkAppSet toDomain(String companyId) {
        return new HolidayWorkAppSet(
                companyId,
                EnumAdaptor.valueOf(calcStampMiss, CalcStampMiss.class),
                BooleanUtils.toBoolean(useDirectBounceFunction),
                applicationDetailSetting.toDomain(),
                overtimeLeaveAppCommonSet.toDomain()
        );
    }
}
