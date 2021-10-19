package nts.uk.ctx.at.request.dom.application.businesstrip.service;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import lombok.Getter;

/**
 * @author anhnm
 * UKDesign.UniversalK.就業.KAF_申請.KAF008_出張申請.A:出張の申請（新規）.アルゴリズム.出張申請就業時刻の初期値をセットする.出張申請就業時間帯時刻
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessTripWorkingHours {
    
    // 勤務種類コード
    String workTypeCd;
    
    // 就業時間帯コード
    String workTimeCd;
    
    // 開始時刻１
    int startTime1;
    
    // 終了時刻１
    int endTime1;
    
    // 開始時刻２
    int startTime2;
    
    // 終業時刻２
    int endTime2;

    
    public static BusinessTripWorkingHours fromAchievementDetail(AchievementDetail achievementDetail) {
        return new BusinessTripWorkingHours(
                achievementDetail.getWorkTypeCD(), 
                achievementDetail.getWorkTimeCD(), 
                achievementDetail.getOpWorkTime().orElse(0), 
                achievementDetail.getOpLeaveTime().orElse(0), 
                achievementDetail.getOpWorkTime2().orElse(0), 
                achievementDetail.getOpDepartureTime2().orElse(0));
    }
}
