package nts.uk.shr.com.context;

import lombok.val;
import nts.arc.scoped.request.RequestContextProvider;
import nts.arc.scoped.session.SessionContextProvider;
import nts.uk.shr.com.context.loginuser.NullLoginUserContext;

public final class AppContexts {

	public static LoginUserContext user() {
		val context = SessionContextProvider.get().get(LoginUserContext.KEY_SESSION_SCOPED);
		if (context == null) {
			return new NullLoginUserContext();
		} else {
			return (LoginUserContext) context;
		}
	}
	
	public static String programId() {
		return RequestContextProvider.get().get(AppContextsConfig.KEY_PROGRAM_ID);
	}
}
