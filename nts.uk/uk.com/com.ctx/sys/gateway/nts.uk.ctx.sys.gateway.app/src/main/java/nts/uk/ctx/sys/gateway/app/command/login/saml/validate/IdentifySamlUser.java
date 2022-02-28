package nts.uk.ctx.sys.gateway.app.command.login.saml.validate;

import lombok.val;
import nts.gul.util.Either;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.EmployeeIdentify;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.IdpUserAssociation;

import java.util.Optional;

/**
 * SAML認証しようとしている社員を識別する
 */
public class IdentifySamlUser {

    public static Either<ErrorType, IdentifiedEmployeeInfo> identify(Require require, String idpUserId) {

        val idpUser = require.getIdpUserAssociation(idpUserId);
        if (!idpUser.isPresent()) {
            return Either.left(ErrorType.NO_IDP_USER_ASSOCIATION);
        }

        val result = EmployeeIdentify.identifyByEmployeeId(require, idpUser.get().getEmployeeId());
        if (result.isFailure()) {
            return Either.left(ErrorType.NO_EMPLOYEE);
        }

        return Either.right(result.getEmployeeInfo());
    }

    public enum ErrorType {

        /** IdPユーザとの紐付けが無い */
        NO_IDP_USER_ASSOCIATION,

        /** 紐付けはあるが社員が存在しない */
        NO_EMPLOYEE,
    }

    public interface Require extends EmployeeIdentify.RequireByEmployeeId {
        Optional<IdpUserAssociation> getIdpUserAssociation(String idpUserId);
    }
}
