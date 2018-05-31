package nts.uk.shr.com.context;

import nts.arc.scoped.request.RequestContextProvider;
import nts.arc.scoped.session.SessionContextProvider;
import nts.uk.shr.infra.application.auth.WindowsAccount;

public class AppContextsConfig {
	
	public static final String KEY_PROGRAM_ID = "programId";
	
	public static final String WINS_ACCOUNT = "windowsAccount";

	public static void setProgramId(String programId) {
		RequestContextProvider.get().put(KEY_PROGRAM_ID, programId);
	}
	
	public static void setWindowsAccount(WindowsAccount account) {
		SessionContextProvider.get().put(WINS_ACCOUNT, account);
	}
}
