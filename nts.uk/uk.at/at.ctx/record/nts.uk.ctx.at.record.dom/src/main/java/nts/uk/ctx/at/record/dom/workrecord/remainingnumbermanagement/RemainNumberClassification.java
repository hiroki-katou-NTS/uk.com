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
    
    public RemainNumberClassification(boolean chkChildNursing, boolean chkLongTermCare) {
    	this.chkSubHoliday = true;
    	this.chkPause = true;
    	this.chkAnnual = true;
    	this.chkFundingAnnual = true;
    	this.chkSpecial = true;
    	this.chkPublicHoliday = true;
    	this.chkSuperBreak = true;
    	this.chkChildNursing = chkChildNursing;
    	this.chkLongTermCare = chkLongTermCare;
    }
}
