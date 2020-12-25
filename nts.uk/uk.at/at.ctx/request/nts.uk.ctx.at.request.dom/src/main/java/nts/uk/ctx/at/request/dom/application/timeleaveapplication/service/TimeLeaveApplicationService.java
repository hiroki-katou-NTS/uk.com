package nts.uk.ctx.at.request.dom.application.timeleaveapplication.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.LeaveRemainingInfo;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;

import java.util.Optional;

public interface TimeLeaveApplicationService {
    /**
     * 時間休申請の設定を取得する
     * @param companyId
     * @return
     */
    TimeLeaveApplicationReflect getTimeLeaveAppReflectSetting(String companyId);

    /**
     * 休暇残数情報を取得する
     * @param companyId
     * @param employeeId
     * @param baseDate
     * @return
     */
    LeaveRemainingInfo getLeaveRemainingInfo(String companyId, String employeeId, GeneralDate baseDate);

    /**
     * 時間休暇申請登録前チェック
     */
    void checkBeforeRigister(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication, TimeLeaveApplicationOutput timeLeaveApplicationOutput);

    /**
     * 登録前チェック
     */
    void checkBeforeUpdate(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication, TimeLeaveApplicationOutput output);

    /**
     * 特別休暇枠を選択する
     */
    TimeLeaveApplicationOutput GetSpecialVacation(Optional<Integer> specialHdFrameNo, TimeLeaveApplicationOutput timeLeaveApplicationOutput);
}
