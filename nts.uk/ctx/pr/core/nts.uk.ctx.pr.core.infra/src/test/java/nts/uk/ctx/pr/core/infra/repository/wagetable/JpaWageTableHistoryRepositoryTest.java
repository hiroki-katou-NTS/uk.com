///******************************************************************
// * Copyright (c) 2017 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.pr.core.infra.repository.wagetable;
///******************************************************************
// * All right reserved.                                            *
// *****************************************************************/
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import mockit.Tested;
//import mockit.integration.junit4.JMockit;
//import nts.uk.ctx.core.dom.company.CompanyCode;
//import nts.uk.ctx.pr.core.app.wagetable.command.WtAddHistoryCommand;
//import nts.uk.ctx.pr.core.app.wagetable.command.dto.WtElementDto;
//import nts.uk.ctx.pr.core.app.wagetable.command.dto.WtHistoryDto;
//import nts.uk.ctx.pr.core.app.wagetable.command.dto.WtItemDto;
//import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
//import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
//import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;
//import nts.uk.ctx.pr.core.dom.wagetable.element.RefMode;
//import nts.uk.ctx.pr.core.dom.wagetable.element.StepMode;
//import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
//import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.CodeItem;
//import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.RangeItem;
//import nts.uk.ctx.pr.core.infra.repository.support.AbstractDbUnitTestCase;
//import nts.uk.ctx.pr.core.infra.repository.wagetable.history.JpaWtHistoryRepository;
//
///**
// * The Class JpaWageTableHistoryRepositoryTest.
// */
//@RunWith(JMockit.class)
//public class JpaWageTableHistoryRepositoryTest extends AbstractDbUnitTestCase {
//
//	/** The repository. */
//	@Tested
//	private JpaWtHistoryRepository repository = new JpaWtHistoryRepository();
//
//	/**
//	 * Adds the 001.
//	 *
//	 * @throws Exception
//	 *             the exception
//	 */
//	@Test
//	public void add_001() throws Exception {
//
//		// // Get data set.
//		// XlsDataSet dataSet = this.getDataSet("001_in.xls");
//		//
//		// // Db operation.
//		// DatabaseOperation.CLEAN_INSERT.execute(getJpaConnection(), dataSet);
//
//		CompanyCode companyCode = new CompanyCode("0001");
//
//		List<CodeItem> codeItems = Arrays.asList(new CodeItem("refCode1", "uuid1"),
//				new CodeItem("refCode2", "uuid2"));
//
//		RefMode refMode = new RefMode(ElementType.MASTER_REF, companyCode,
//				new WtElementRefNo("tb01"), codeItems);
//
//		List<RangeItem> rangeItems = Arrays.asList(new RangeItem(1, 1d, 5d, "uuid3"),
//				new RangeItem(2, 6d, 10d, "uuid4"));
//
//		StepMode stepModeDto = new StepMode(ElementType.AGE_FIX, BigDecimal.valueOf(1),
//				BigDecimal.valueOf(10), BigDecimal.valueOf(5), rangeItems);
//
//		WtElementDto demensionDetailDto1 = new WtElementDto();
//		demensionDetailDto1.setDemensionNo(DemensionNo.DEMENSION_1ST);
//		demensionDetailDto1.setElementModeSetting(refMode);
//
//		WtElementDto demensionDetailDto2 = new WtElementDto();
//		demensionDetailDto2.setDemensionNo(DemensionNo.DEMENSION_2ND);
//		demensionDetailDto2.setElementModeSetting(stepModeDto);
//
//		List<WtElementDto> demensionDetails = Arrays.asList(demensionDetailDto1,
//				demensionDetailDto2);
//
//		WtItemDto wageTableItemDto = new WtItemDto();
//		wageTableItemDto.setElement1Id("element1Id");
//		wageTableItemDto.setElement2Id("element2Id");
//		wageTableItemDto.setElement3Id("element3Id");
//		wageTableItemDto.setAmount(BigDecimal.valueOf(111111));
//
//		List<WtItemDto> valueItems = Arrays.asList(wageTableItemDto);
//
//		WtHistoryDto wageTableHistoryDto = new WtHistoryDto();
//		wageTableHistoryDto.setStartMonth("2016/01");
//		wageTableHistoryDto.setEndMonth("2016/07");
//		wageTableHistoryDto.setDemensionDetails(demensionDetails);
//		wageTableHistoryDto.setValueItems(valueItems);
//
//		WtAddHistoryCommand command = new WtAddHistoryCommand();
//		command.setCreateHeader(true);
//		command.setWageTableHistoryDto(wageTableHistoryDto);
//
//		// Execute
//		WtHistory wageTableHistory = wageTableHistoryDto.toDomain(companyCode.v(), "001");
//
//		// Exec
//		repository.addHistory(wageTableHistory);
//
//		// // Get updated.
//		// IDataSet updatedDataSet = getJpaConnection().createDataSet();
//		// ITable updatedAnswerTable = new SortedTable(
//		// updatedDataSet.getTable("hmhdt_stresscheck_answer"));
//		// ITable updatedAnswerItemTable = new SortedTable(
//		// updatedDataSet.getTable("hmhdt_stresscheck_answer_item"));
//		//
//		// // Read expected output.
//		// IDataSet expectedDataSet = this.getDataSet("001_out.xls");
//		// ITable expectedAnswerTable = new SortedTable(
//		// expectedDataSet.getTable("hmhdt_stresscheck_answer"));
//		// ITable expectedAnswerItemTable = new SortedTable(
//		// expectedDataSet.getTable("hmhdt_stresscheck_answer_item"));
//		//
//		// // Assert.
//		// Assertion.assertEquals(expectedAnswerTable, updatedAnswerTable);
//		// Assertion.assertEquals(expectedAnswerItemTable,
//		// updatedAnswerItemTable);
//	}
//
//}
