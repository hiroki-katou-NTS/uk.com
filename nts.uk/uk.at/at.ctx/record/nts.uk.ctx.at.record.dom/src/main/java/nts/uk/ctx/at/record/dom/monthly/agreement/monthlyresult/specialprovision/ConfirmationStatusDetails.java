package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * 確認状況
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class ConfirmationStatusDetails {

    /** 確認状態 */
    private ConfirmationStatus confirmationStatus;

    /** 確認者 */
    private String confirmerSID;


    /** 確認日 */
    private Optional<GeneralDate> confirmDate;
}
