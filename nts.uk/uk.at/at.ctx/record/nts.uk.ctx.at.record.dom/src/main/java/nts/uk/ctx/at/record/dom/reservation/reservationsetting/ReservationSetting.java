package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.予約設定.予約設定
 * @author Nguyen Huy Quang
 *
 */

@Getter
public class ReservationSetting extends AggregateRoot {

    /**
     * 会社ID
     */
    // The company id.
    private String companyId;

    /**
     * 予約の運用区別
     */
    // operation Distinction.
    private OperationDistinction operationDistinction;

    /**
     * 予約修正内容
     */
    // correction Content.
    private CorrectionContent correctionContent;

    /**
     * 実績集計
     */
    // achievements.
    private Achievements achievements;
    
    /**
     * 予約受付時間帯
     */
    private List<ReservationRecTimeZone> reservationRecTimeZoneLst;
    
    /**
     * 受付時間帯２使用区分
     */
    private boolean receptionTimeZone2Use;

    public ReservationSetting(String companyId, OperationDistinction operationDistinction, CorrectionContent correctionContent, 
    		Achievements achievements, List<ReservationRecTimeZone> reservationRecTimeZoneLst, boolean receptionTimeZone2Use)
    {
        this.companyId = companyId;
        this.operationDistinction = operationDistinction;
        this.correctionContent = correctionContent;
        this.achievements = achievements;
        this.reservationRecTimeZoneLst = reservationRecTimeZoneLst;
        this.receptionTimeZone2Use = receptionTimeZone2Use;
    }

}
