package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ReservationSettingDto {

    /**
     * 会社ID
     */
    // The company id.
    private String companyId;

    /**
     * 予約の運用区別
     */
    // operation Distinction.
    private int operationDistinction;

    /**
     * 予約修正内容
     */
    // correction Content.
    private CorrectionContentDto correctionContent;

    /**
     * 実績集計
     */
    // achievements.
    private int monthlyResultsMethod;
    
    /**
     * 予約受付時間帯
     */
    private List<ReservationRecTimeZoneDto> reservationRecTimeZoneLst;
    
    /**
     * 受付時間帯２使用区分
     */
    private boolean receptionTimeZone2Use;
    
    public static ReservationSettingDto fromDomain(ReservationSetting domain) {
        return new ReservationSettingDto(
                domain.getCompanyId(), 
                domain.getOperationDistinction().value, 
                CorrectionContentDto.fromDomain(domain.getCorrectionContent()), 
                domain.getAchievements().getMonthlyResults().value, 
                domain.getReservationRecTimeZoneLst().stream()
                    .map(x -> ReservationRecTimeZoneDto.fromDomain(x)).collect(Collectors.toList()), 
                domain.isReceptionTimeZone2Use());
    }
}
