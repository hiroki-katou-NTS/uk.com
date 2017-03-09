/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;
/******************************************************************
 * All right reserved.                                            *
 *****************************************************************/

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.command.WageTableHistoryAddCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.CodeItemDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.RangeItemDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.RefModeDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.StepModeDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableDemensionDetailDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableItemDto;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
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

		// // Get data set.
		// XlsDataSet dataSet = this.getDataSet("001_in.xls");
		//
		// // Db operation.
		// DatabaseOperation.CLEAN_INSERT.execute(getJpaConnection(), dataSet);

		CompanyCode companyCode = new CompanyCode("0001");

		CodeItemDto codeItemDto1 = new CodeItemDto();
		codeItemDto1.setReferenceCode("refCode1");
		codeItemDto1.setUuid("uuid1");

		CodeItemDto codeItemDto2 = new CodeItemDto();
		codeItemDto2.setReferenceCode("refCode2");
		codeItemDto2.setUuid("uuid2");

		List<CodeItemDto> codeItemDtos = Arrays.asList(codeItemDto1, codeItemDto2);

		RefModeDto refModeDto = new RefModeDto();
		refModeDto.setType(ElementType.MASTER_REF.value);
		refModeDto.setCompanyCode(companyCode.v());
		refModeDto.setRefNo("tb01");
		refModeDto.setItems(codeItemDtos);

		RangeItemDto rangeItemDto1 = new RangeItemDto();
		rangeItemDto1.setOrderNumber(1);
		rangeItemDto1.setStartVal(1d);
		rangeItemDto1.setEndVal(5d);
		rangeItemDto1.setUuid("uuid3");

		RangeItemDto rangeItemDto2 = new RangeItemDto();
		rangeItemDto2.setOrderNumber(2);
		rangeItemDto2.setStartVal(5d);
		rangeItemDto2.setEndVal(10d);
		rangeItemDto2.setUuid("uuid4");

		List<RangeItemDto> rangeItemDtos = Arrays.asList(rangeItemDto1, rangeItemDto2);

		StepModeDto stepModeDto = new StepModeDto();
		stepModeDto.setType(ElementType.AGE_FIX.value);
		stepModeDto.setLowerLimit(1d);
		stepModeDto.setUpperLimit(10d);
		stepModeDto.setInterval(5d);
		stepModeDto.setItems(rangeItemDtos);

		WageTableDemensionDetailDto demensionDetailDto1 = new WageTableDemensionDetailDto();
		demensionDetailDto1.setDemensionNo(DemensionNo.DEMENSION_1ST.value);
		demensionDetailDto1.setElementModeSetting(refModeDto);

		WageTableDemensionDetailDto demensionDetailDto2 = new WageTableDemensionDetailDto();
		demensionDetailDto2.setDemensionNo(DemensionNo.DEMENSION_2ND.value);
		demensionDetailDto2.setElementModeSetting(stepModeDto);

		List<WageTableDemensionDetailDto> demensionDetails = Arrays.asList(demensionDetailDto1,
				demensionDetailDto2);

		WageTableItemDto wageTableItemDto = new WageTableItemDto();
		wageTableItemDto.setElement1Id("element1Id");
		wageTableItemDto.setElement2Id("element2Id");
		wageTableItemDto.setElement3Id("element3Id");
		wageTableItemDto.setAmount(BigDecimal.valueOf(111111));

		List<WageTableItemDto> valueItems = Arrays.asList(wageTableItemDto);

		WageTableHistoryAddCommand command = new WageTableHistoryAddCommand();
		command.setCode("001");
		command.setStartMonth("2016/01");
		command.setEndMonth("2016/07");
		command.setDemensionDetails(demensionDetails);
		command.setValueItems(valueItems);

		// Execute
		WageTableHistory wageTableHistory = command.toDomain(companyCode);

		// Exec
		repository.add(wageTableHistory);

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
