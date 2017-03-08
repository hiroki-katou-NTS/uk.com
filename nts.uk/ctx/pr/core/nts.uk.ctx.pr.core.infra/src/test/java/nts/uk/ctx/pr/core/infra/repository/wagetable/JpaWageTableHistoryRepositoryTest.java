/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;
/******************************************************************
 * All right reserved.                                            *
 *****************************************************************/

import java.util.Arrays;
import java.util.List;

import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.command.WageTableHistoryAddCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableDemensionDetailDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableItemDto;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.infra.repository.support.AbstractDbUnitTestCase;
import nts.uk.ctx.pr.core.infra.repository.wagetable.history.JpaWageTableHistoryRepository;

/**
 * The Class JpaWageTableHistoryRepositoryTest.
 */
@RunWith(JMockit.class)
public class JpaWageTableHistoryRepositoryTest extends AbstractDbUnitTestCase {

	/** The repository. */
	@Tested
	private JpaWageTableHistoryRepository repository = new JpaWageTableHistoryRepository();

	/**
	 * Adds the 001.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void add_001() throws Exception {

		// Get data set.
		XlsDataSet dataSet = this.getDataSet("001_in.xls");

		// Db operation.
		DatabaseOperation.CLEAN_INSERT.execute(getJpaConnection(), dataSet);

		List<WageTableDemensionDetailDto> demensionDetails = Arrays
				.asList(new WageTableDemensionDetailDto());

		List<WageTableItemDto> valueItems = Arrays.asList(new WageTableItemDto());

		WageTableHistoryAddCommand command = new WageTableHistoryAddCommand();
		command.setCode("Code");
		command.setStartMonth("01/2016");
		command.setEndMonth("07/2016");
		command.setDemensionDetails(demensionDetails);
		command.setValueItems(valueItems);

		WageTableHistory wageTableHistory = command.toDomain(new CompanyCode("001"));
		// Exec
		this.executeTestWithTransaction(() -> {
			repository.add(wageTableHistory);
			return null;
		});

		// // Get updated.
		// IDataSet updatedDataSet = getJpaConnection().createDataSet();
		// ITable updatedAnswerTable = new SortedTable(
		// updatedDataSet.getTable("hmhdt_stresscheck_answer"));
		// ITable updatedAnswerItemTable = new SortedTable(
		// updatedDataSet.getTable("hmhdt_stresscheck_answer_item"));
		//
		// // Read expected output.
		// IDataSet expectedDataSet = this.getDataSet("001_out.xls");
		// ITable expectedAnswerTable = new SortedTable(
		// expectedDataSet.getTable("hmhdt_stresscheck_answer"));
		// ITable expectedAnswerItemTable = new SortedTable(
		// expectedDataSet.getTable("hmhdt_stresscheck_answer_item"));
		//
		// // Assert.
		// Assertion.assertEquals(expectedAnswerTable, updatedAnswerTable);
		// Assertion.assertEquals(expectedAnswerItemTable,
		// updatedAnswerItemTable);
	}

}
