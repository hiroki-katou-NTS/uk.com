package nts.uk.ctx.at.request.app.find.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.WorkInformationForApplication;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author anhnm
 * 申請中の勤務情報
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkInformationForApplicationDto {
    /*
     * 就業時間帯
     */
    private String workTimeCode;
    
    /*
     * 勤務種類
     */
    private String workTypeCode;
    
    public WorkInformationForApplication toDomain() {
        return (workTimeCode == null && workTypeCode == null) ? null : new WorkInformationForApplication(
                workTimeCode == null ? null : new WorkTimeCode(workTimeCode), 
                workTypeCode == null ? null : new WorkTypeCode(workTypeCode));
    }
    
    public static WorkInformationForApplicationDto fromDomain(WorkInformationForApplication domain) {
        return new WorkInformationForApplicationDto(domain.getWorkTimeCode().v(), domain.getWorkTypeCode().v());
    }
}
