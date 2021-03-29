package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.Optional;

/**
 * 確認状況
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class ConfirmationStatusDetails extends ValueObject {

    /** 確認者 */
    private final String confirmerSID;

    /** 確認状態 */
    private ConfirmationStatus confirmationStatus;

    /** 確認日 */
    private Optional<GeneralDate> confirmDate;

    /**
     * [C-1] 未確認
     */
    public static ConfirmationStatusDetails create(String confirmerSID) {

        return new ConfirmationStatusDetails(confirmerSID, ConfirmationStatus.UNCONFIRMED,Optional.empty());
    }
}
