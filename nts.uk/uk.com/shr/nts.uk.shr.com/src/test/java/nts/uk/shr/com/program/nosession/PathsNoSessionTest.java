package nts.uk.shr.com.program.nosession;

import static mockit.Deencapsulation.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class PathsNoSessionTest {

	@Test
	public void testName() {
		
		boolean result = PathsNoSession.WEB_SCREENS.sessionRequired("/view/common/error/sessiontimeout/index.xhtml");
		assertThat(result, is(false));
	}

}
