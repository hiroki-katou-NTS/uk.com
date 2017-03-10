/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.command.WageTableHistoryAddCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.WageTableHistoryAddCommandHandler;
import nts.uk.ctx.pr.core.app.wagetable.command.WageTableHistoryUpdateCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.WageTableHistoryUpdateCommandHandler;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableDemensionDetailDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableHeadDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableHistoryDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableItemDto;
import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.CodeItem;
import nts.uk.ctx.pr.core.dom.wagetable.element.RangeItem;
import nts.uk.ctx.pr.core.dom.wagetable.element.RefMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.StepMode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHistoryService;
import nts.uk.ctx.pr.core.ws.base.simplehistory.SimpleHistoryWs;
import nts.uk.ctx.pr.core.ws.base.simplehistory.dto.HistoryModel;
import nts.uk.ctx.pr.core.ws.base.simplehistory.dto.MasterModel;
import nts.uk.ctx.pr.core.ws.wagetable.dto.WageTableHistoryModel;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableWs.
 */
@Path("pr/proto/wagetable")
@Produces(MediaType.APPLICATION_JSON)
public class WageTableWs extends SimpleHistoryWs<WageTableHead, WageTableHistory> {

	/** The create wage table history command handler. */
	@Inject
	private WageTableHistoryAddCommandHandler createWageTableHistoryCommandHandler;

	/** The update wage table history command handler. */
	@Inject
	private WageTableHistoryUpdateCommandHandler updateWageTableHistoryCommandHandler;

	/** The wage table head repo. */
	@Inject
	private WageTableHeadRepository wageTableHeadRepo;

	/** The wage table history repo. */
	@Inject
	private WageTableHistoryRepository wageTableHistoryRepo;

	/** The service. */
	@Inject
	private WageTableHistoryService service;

	/**
	 * Find.
	 *
	 * @param id
	 *            the id
	 * @return the wage table history dto
	 */
	@POST
	@Path("find/{id}")
	public WageTableHistoryModel find(@PathParam("id") String id) {
		// Get the detail history.
		Optional<WageTableHistory> optWageTableHistory = this.wageTableHistoryRepo
				.findHistoryByUuid(id);
		WageTableHistoryModel dto = null;

		// Check exsit.
		if (optWageTableHistory.isPresent()) {
			WageTableHistory wageTableHistory = optWageTableHistory.get();
			Optional<WageTableHead> optWageTable = this.wageTableHeadRepo.findByCode(
					wageTableHistory.getCompanyCode().v(), wageTableHistory.getWageTableCode().v());
			dto = WageTableHistoryModel.builder().build();
			wageTableHistory.saveToMemento(dto);
			dto.setWageTableCode(optWageTable.get().getCode());
		}

		// Return
		return dto;
	}

	/**
	 * Creates the.
	 *
	 * @param command
	 *            the command
	 * @return the history model
	 */
	@POST
	@Path("create")
	public HistoryModel create() {
		WageTableHistoryAddCommand command = null;
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		List<CodeItem> codeItems = Arrays.asList(new CodeItem("refCode1", "uuid1"),
				new CodeItem("refCode2", "uuid2"));

		RefMode refMode = new RefMode(ElementType.MASTER_REF, companyCode,
				new WtElementRefNo("tb01"), codeItems);

		List<RangeItem> rangeItems = Arrays.asList(new RangeItem(1, 1d, 5d, "uuid3"),
				new RangeItem(2, 6d, 10d, "uuid4"));

		StepMode stepModeDto = new StepMode(ElementType.AGE_FIX, BigDecimal.valueOf(1),
				BigDecimal.valueOf(10), BigDecimal.valueOf(5), rangeItems);

		WageTableDemensionDetailDto demensionDetailDto1 = new WageTableDemensionDetailDto();
		demensionDetailDto1.setDemensionNo(DemensionNo.DEMENSION_1ST);
		demensionDetailDto1.setElementModeSetting(refMode);

		WageTableDemensionDetailDto demensionDetailDto2 = new WageTableDemensionDetailDto();
		demensionDetailDto2.setDemensionNo(DemensionNo.DEMENSION_2ND);
		demensionDetailDto2.setElementModeSetting(stepModeDto);

		List<WageTableDemensionDetailDto> demensionDetails = Arrays.asList(demensionDetailDto1,
				demensionDetailDto2);

		WageTableItemDto wageTableItemDto = new WageTableItemDto();
		wageTableItemDto.setElement1Id("uuid1");
		wageTableItemDto.setElement2Id("uuid3");
		// wageTableItemDto.setElement3Id("element3Id");
		wageTableItemDto.setAmount(BigDecimal.valueOf(111111));

		List<WageTableItemDto> valueItems = Arrays.asList(wageTableItemDto);

		WageTableHistoryDto wageTableHistoryDto = new WageTableHistoryDto();
		wageTableHistoryDto.setStartMonth("2016/08");
		wageTableHistoryDto.setEndMonth("2016/09");
		wageTableHistoryDto.setDemensionDetails(demensionDetails);
		wageTableHistoryDto.setValueItems(valueItems);

		WageTableHeadDto wageTableHeadDto = new WageTableHeadDto();
		wageTableHeadDto.setCode("001");
		wageTableHeadDto.setName("WageTable1");
		wageTableHeadDto.setDemensionSet(ElementCount.Two.value);
		wageTableHeadDto.setMemo("memo");
		wageTableHeadDto.setDemensionDetails(demensionDetails);

		command = new WageTableHistoryAddCommand();
		command.setCreateHeader(true);
		command.setWageTableHistoryDto(wageTableHistoryDto);
		command.setWageTableHeadDto(wageTableHeadDto);

		WageTableHistory history = this.createWageTableHistoryCommandHandler.handle(command);
		return HistoryModel.builder().uuid(history.getUuid()).start(history.getStart().v())
				.end(history.getEnd().v()).build();
	}

	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("update")
	public void update(WageTableHistoryUpdateCommand command) {
		this.updateWageTableHistoryCommandHandler.handle(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.ws.base.simplehistory.SimpleHistoryWs#
	 * loadMasterModelList()
	 */
	@Override
	@Path("masterhistory")
	@POST
	public List<MasterModel> loadMasterModelList() {
		// Get list of wage table.
		List<WageTableHead> wageTables = this.wageTableHeadRepo
				.findAll(AppContexts.user().companyCode());

		// Get list of wage table history.
		List<WageTableHistory> wageTableHistories = this.wageTableHistoryRepo
				.findAll(AppContexts.user().companyCode());

		// Group histories by unit code.
		Map<WageTableCode, List<HistoryModel>> historyMap = wageTableHistories.stream()
				.collect(Collectors.groupingBy(WageTableHistory::getWageTableCode,
						Collectors.mapping((res) -> {
							return HistoryModel.builder().uuid(res.getUuid())
									.start(res.getStart().v()).end(res.getEnd().v()).build();
						}, Collectors.toList())));

		// Return
		return wageTables.stream().map(item -> {
			return MasterModel.builder().code(item.getCode().v()).name(item.getName().v())
					.historyList(historyMap.get(item.getCode())).build();
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.ws.base.simplehistory.SimpleHistoryWs#getServices()
	 */
	@Override
	protected SimpleHistoryBaseService<WageTableHead, WageTableHistory> getServices() {
		return this.service;
	}
}
