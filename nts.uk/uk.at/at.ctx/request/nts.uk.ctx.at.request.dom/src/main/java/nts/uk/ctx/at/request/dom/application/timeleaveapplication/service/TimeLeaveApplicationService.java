package nts.uk.ctx.at.request.dom.application.timeleaveapplication.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeDigestAppType;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeVacationManagementOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeVacationRemainingOutput;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;

import java.util.Optional;

public interface TimeLeaveApplicationService {
    /**
     * 時間休申請の設定を取得する
     * @param companyId
     * @return
     */
    TimeLeaveApplicationReflect getTimeLeaveAppReflectSetting(String companyId);

    /**
     * 時間休暇の管理区分を取得する
     * @param companyId
     * @param employeeId
     * @param baseDate
     * @param condition
     * @return
     */
    TimeVacationManagementOutput getTimeLeaveManagement(String companyId, String employeeId, GeneralDate baseDate, TimeLeaveAppReflectCondition condition);

    /**
     * 各時間休暇の残数を取得する
     * @param companyId
     * @param employeeId
     * @param baseDate
     * @param timeLeaveManagement
     * @return
     */
    TimeVacationRemainingOutput getTimeLeaveRemaining(String companyId, String employeeId, GeneralDate baseDate, TimeVacationManagementOutput timeLeaveManagement);

    /**
     * 特別休暇残数情報を取得する
     * @param companyId
     * @param specialFrameNo
     * @return
     */
    TimeLeaveApplicationOutput getSpecialLeaveRemainingInfo(String companyId, Optional<Integer> specialFrameNo, TimeLeaveApplicationOutput timeLeaveAppOutput);

    /**
     * 時間休暇申請登録前チェック
     */
    void checkBeforeRegister(String companyId, TimeDigestAppType timeLeaveType, TimeLeaveApplication timeLeaveApplication, TimeLeaveApplicationOutput timeLeaveApplicationOutput);
}
