package nts.uk.ctx.at.request.dom.application.appabsence;

import lombok.AllArgsConstructor;
/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.休暇申請の種類
 * 休暇申請の種類
 *
 */
@AllArgsConstructor
public enum HolidayApplicationType {

    /**
     * 年次有休
     */
    ANNUAL_PAID_LEAVE(0, "年次有休"),
    
    /**
     * 代休
     */ 
    SUBSTITUTE_HOLIDAY(1, "代休"),
    
    /**
     * 欠勤
     */ 
    ABSENCE(2, "欠勤"),
    
    /**
     * 特別休暇
     */ 
    SPECIAL_HOLIDAY(3, "特別休暇"),
    
    /**
     * 積立年休
     */ 
    YEARLY_RESERVE(4, "積立年休"),
    
    /**
     * 休日
     */
    HOLIDAY(5, "積立年休"),
    
    /**
     * 時間消化
     */
    DIGESTION_TIME(6, "時間消化"),
    
    /**
     * 【廃止】振休
     */
    REST_TIME(7, "【廃止】振休");
    
    public final int value;
    
    public final String nameString;
}
