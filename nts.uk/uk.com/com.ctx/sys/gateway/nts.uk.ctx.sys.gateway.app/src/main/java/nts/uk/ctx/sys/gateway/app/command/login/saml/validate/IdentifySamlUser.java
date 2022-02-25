package nts.uk.ctx.sys.gateway.app.command.login.saml.validate;

import lombok.val;
import nts.arc.error.ErrorMessageId;
import nts.gul.util.Either;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.IdpUserAssociation;

import java.util.Optional;

/**
 * SAML認証しようとしている社員を識別する
 */
public class IdentifySamlUser {

    public static Either<ErrorMessageId, IdentifiedEmployeeInfo> identify(Require require, String idpUserId) {

        val idpUser = require.getIdpUserAssociation(idpUserId);
        if (!idpUser.isPresent()) {
            return Either.left(new ErrorMessageId("Msg_1989"));
        }

        val result = EmployeeIdentify.identifyByEmployeeId(require, idpUser.get().getEmployeeId());
        if (result.isFailure()) {
            return Either.left(new ErrorMessageId("Msg_1990"));
        }

        return Either.right(result.getEmployeeInfo());
    }

    public interface Require extends EmployeeIdentify.RequireByEmployeeId {
        Optional<IdpUserAssociation> getIdpUserAssociation(String idpUserId);
    }
}
