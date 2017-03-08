/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.TestCase;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableDemensionDetailDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableItemDto;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;

/**
 * The Class CommonSettingUpdateHandlerTest.
 */
@RunWith(JMockit.class)
public class WageTableHistoryAddCommandHandlerTest extends TestCase {

	// ============= REPOSITORY ==================//
	/** The wage table history repo. */
	@Injectable
	private WageTableHistoryRepository wageTableHistoryRepo;

	/** The handler. */
	@Tested
	private WageTableHistoryAddCommandHandler handler;

	/**
	 * Handler 001.
	 */
	@Test
	public void handler_001() {

		// Expectation.
		new Expectations() {
			{
				wageTableHistoryRepo.update((WageTableHistory) this.any);
				this.times = 1;
			}
		};

		List<WageTableDemensionDetailDto> demensionDetails = Arrays
				.asList(new WageTableDemensionDetailDto());

		List<WageTableItemDto> valueItems = Arrays.asList(new WageTableItemDto());

		WageTableHistoryAddCommand command = new WageTableHistoryAddCommand();
		command.setCode("Code");
		command.setStartMonth("01/2016");
		command.setEndMonth("07/2016");
		command.setDemensionDetails(demensionDetails);
		command.setValueItems(valueItems);

		MockUp<CommandHandlerContext<WageTableHistoryAddCommand>> 
			mockedContext = new MockUp<CommandHandlerContext<WageTableHistoryAddCommand>>() {
			@Mock
			WageTableHistoryAddCommand getCommand() {
				return command;
			}
		};

		CommandHandlerContext<WageTableHistoryAddCommand> context = mockedContext.getMockInstance();

		// Execute
		this.handler.handle(context);
	}

}