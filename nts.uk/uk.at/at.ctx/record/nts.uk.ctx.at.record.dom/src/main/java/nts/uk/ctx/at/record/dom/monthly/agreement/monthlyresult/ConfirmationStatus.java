package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * 確認状況
 * @author quang.nh1
 */
public class ConfirmationStatus {

    /** 確認状態 */
    private ConfirmationStatusEnum confirmationStatusEnum;

    /** 確認者 */
    private String confirmerSID;


    /** 確認日 */
    private Optional<GeneralDate> confirmDate;
}
