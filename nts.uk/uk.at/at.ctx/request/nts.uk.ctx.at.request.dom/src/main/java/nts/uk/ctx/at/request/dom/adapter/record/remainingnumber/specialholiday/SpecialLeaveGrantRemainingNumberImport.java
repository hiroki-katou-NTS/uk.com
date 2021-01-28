package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;

/**
 * 特別休暇不足ダミーフラグ
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialLeaveGrantRemainingNumberImport extends SpecialLeaveGrantRemainingData {
    // 特別休暇不足ダミーフラグ
    private boolean shortageDummyFlag = false;

    public SpecialLeaveGrantRemainingNumberImport(SpecialLeaveGrantRemainingData data, boolean dummyFlag) {
        super();
        this.setSpecialId(data.getSpecialId());
        this.setCId(data.getCId());
        this.setEmployeeId(data.getEmployeeId());
        this.setSpecialLeaveCode(data.getSpecialLeaveCode());
        this.setGrantDate(data.getGrantDate());
        this.setDeadlineDate(data.getDeadlineDate());
        this.setExpirationStatus(data.getExpirationStatus());
        this.setRegisterType(data.getRegisterType());
        this.setDetails(data.getDetails());
        this.shortageDummyFlag = dummyFlag;
    }
}
