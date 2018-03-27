package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.ConfirmOfManagerOrYouself;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.FunctionalRestriction;

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
	
	public Optional<ConfirmOfManagerOrYouself> checkOld(FunctionalRestriction identityProcessUseSet) {
		if (identityProcessUseSet.getUseConfirmByYourself()) {
			if (identityProcessUseSet.getYourselfConfirmError() != null) {
				if (identityProcessUseSet.getYourselfConfirmError() == ConfirmOfManagerOrYouself.CAN_CHECK_WHEN_ERROR) {
					return Optional.of(ConfirmOfManagerOrYouself.CAN_CHECK_WHEN_ERROR);
				} else
					return Optional.of(ConfirmOfManagerOrYouself.CANNOT_CHECKED_WHEN_ERROR);
			} else {
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}
}
