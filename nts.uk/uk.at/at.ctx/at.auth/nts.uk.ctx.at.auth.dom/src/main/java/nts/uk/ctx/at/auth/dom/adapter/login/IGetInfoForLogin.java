package nts.uk.ctx.at.auth.dom.adapter.login;

import java.util.Optional;

public interface IGetInfoForLogin {

	public Optional<String> getUserIdFromLoginId(String perId);
}
