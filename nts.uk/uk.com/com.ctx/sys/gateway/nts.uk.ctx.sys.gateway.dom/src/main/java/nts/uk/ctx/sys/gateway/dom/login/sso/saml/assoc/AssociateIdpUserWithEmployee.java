package nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc;

import lombok.val;
import nts.arc.task.tran.AtomTask;

/**
 * IdPユーザと社員を紐付ける
 */
public class AssociateIdpUserWithEmployee {

    public static AtomTask associate(Require require, String tenantCode, SamlIdpUserName idpUserName, String employeeId) {

        val newAssoc = new IdpUserAssociation(tenantCode, idpUserName, employeeId);

        return AtomTask.of(() -> {
            require.deleteIdpUserAssociation(tenantCode, idpUserName);
            require.save(newAssoc);
        });
    }

    public interface Require {

        void deleteIdpUserAssociation(String tenantCode, SamlIdpUserName idpUserName);

        void save(IdpUserAssociation assoc);
    }
}
