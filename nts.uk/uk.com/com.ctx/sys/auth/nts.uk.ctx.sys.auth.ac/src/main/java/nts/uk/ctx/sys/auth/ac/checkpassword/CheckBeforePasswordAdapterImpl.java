package nts.uk.ctx.sys.auth.ac.checkpassword;

import nts.uk.ctx.sys.auth.dom.adapter.checkpassword.CheckBeforeChangePassImport;
import nts.uk.ctx.sys.auth.dom.adapter.checkpassword.CheckBeforePasswordAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.checkpassword.PasswordMessageObject;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforeChangePassOutput;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforePasswordPublisher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CheckBeforePasswordAdapterImpl implements CheckBeforePasswordAdapter {

    @Inject
    private CheckBeforePasswordPublisher checkBeforePasswordPublisher;

    @Override
    public CheckBeforeChangePassImport checkBeforeChangePassword(String userId, String currentPass, String newPass, String reNewPass) {
        CheckBeforeChangePassOutput output = checkBeforePasswordPublisher.checkBeforeChangePassword(userId, currentPass, newPass, reNewPass);
        List<PasswordMessageObject> passwordMessageObjects = output.getMessage().stream()
                .map(mess -> new PasswordMessageObject(mess.getMessage(), mess.getParam()))
                .collect(Collectors.toList());
        return CheckBeforeChangePassImport.builder()
                .error(output.isError())
                .message(passwordMessageObjects)
                .build();
    }
}
