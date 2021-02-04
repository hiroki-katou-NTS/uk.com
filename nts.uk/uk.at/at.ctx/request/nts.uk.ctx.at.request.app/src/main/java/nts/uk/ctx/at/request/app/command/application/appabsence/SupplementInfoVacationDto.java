package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.SupplementInfoVacation;

/**
 * @author anhnm
 * 特定の休暇指定時の補足情報
 *
 */
@Data
@AllArgsConstructor
public class SupplementInfoVacationDto {

    /**
     * 期間
     */
    private DatePeriodDto datePeriod;
    
    /**
     * 特別休暇申請
     */
    private ApplyforSpecialLeaveDto applyForSpeLeave;
    
    public SupplementInfoVacation toDomain() {
        return new SupplementInfoVacation(
                Optional.ofNullable(datePeriod).flatMap(x -> Optional.ofNullable(x.toDomain())),
                Optional.ofNullable(applyForSpeLeave).flatMap(x -> Optional.ofNullable(x.toDomain())));
    }
    
    public static SupplementInfoVacationDto fromDomain(SupplementInfoVacation domain) {
        return new SupplementInfoVacationDto(
        		domain.getDatePeriod().map(x -> new DatePeriodDto(x.start().toString(), x.end().toString())).orElse(null), 
                domain.getApplyForSpeLeaveOptional().map(ApplyforSpecialLeaveDto::fromDomain).orElse(null));
    }
}
