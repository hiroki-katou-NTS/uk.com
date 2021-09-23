package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author anhnm
 * 残数チェック区分
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemainNumberClassification {
    /** ・代休チェック区分 */
    private boolean chkSubHoliday;
    /** ・振休チェック区分 */
    private boolean chkPause;
    /** ・年休チェック区分 */
    private boolean chkAnnual;
    /** ・積休チェック区分 */
    private boolean chkFundingAnnual;
    /** ・特休チェック区分 */
    private boolean chkSpecial;
    /** ・公休チェック区分 */
    private boolean chkPublicHoliday;
    /** ・超休チェック区分 */
    private boolean chkSuperBreak;
    /** 子の看護チェック区分 */
    private boolean chkChildNursing;
    /** 介護チェック区分 */
    private boolean chkLongTermCare;
    
    public static RemainNumberClassification createTrue() {
        RemainNumberClassification remaimClassification = new RemainNumberClassification();
        remaimClassification.chkSubHoliday = true;
        remaimClassification.chkPause = true;
        remaimClassification.chkAnnual = true;
        remaimClassification.chkFundingAnnual = true;
        remaimClassification.chkSpecial = true;
        remaimClassification.chkPublicHoliday = true;
        remaimClassification.chkSuperBreak = true;
        remaimClassification.chkChildNursing = true;
        remaimClassification.chkLongTermCare = true;
    	return remaimClassification;
    }
}
