package nts.uk.shr.com.context;

import javax.enterprise.inject.spi.CDI;

public final class AppContexts {

	public static LoginUserContext user() {
		return new LoginUserContext();
	} 
	
	public static ProgramContext program() {
		return CDI.current().select(ProgramContext.class).get();
	}
}
