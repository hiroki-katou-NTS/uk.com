package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;

@Stateless
public class CheckIdentityVerification {
	public Optional<SelfConfirmError> check(IdentityProcessUseSet identityProcessUseSet) {
		if (identityProcessUseSet.isUseConfirmByYourself()) {
			if (identityProcessUseSet.getYourSelfConfirmError().isPresent()) {
				if (identityProcessUseSet.getYourSelfConfirmError().get() == SelfConfirmError.CAN_CONFIRM_WHEN_ERROR) {
					return Optional.of(SelfConfirmError.CAN_CONFIRM_WHEN_ERROR);
				} else
					return Optional.of(SelfConfirmError.CAN_NOT_REGISTER_WHEN_ERROR);
			} else {
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}
}
