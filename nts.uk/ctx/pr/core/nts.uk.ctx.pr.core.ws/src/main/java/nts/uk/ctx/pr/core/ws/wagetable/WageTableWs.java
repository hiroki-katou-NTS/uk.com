/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable;

import java.util.ArrayList;
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

import nts.uk.ctx.pr.core.app.wagetable.command.WtHistoryAddCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.WtHistoryAddCommandHandler;
import nts.uk.ctx.pr.core.app.wagetable.command.WtHistoryUpdateCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.WtHistoryUpdateCommandHandler;
import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtHead;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WtHistoryService;
import nts.uk.ctx.pr.core.ws.base.simplehistory.SimpleHistoryWs;
import nts.uk.ctx.pr.core.ws.base.simplehistory.dto.HistoryModel;
import nts.uk.ctx.pr.core.ws.base.simplehistory.dto.MasterModel;
import nts.uk.ctx.pr.core.ws.wagetable.dto.DemensionItemDto;
import nts.uk.ctx.pr.core.ws.wagetable.dto.WageTableHeadDto;
import nts.uk.ctx.pr.core.ws.wagetable.dto.WageTableHistoryDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableWs.
 */
@Path("pr/proto/wagetable")
@Produces(MediaType.APPLICATION_JSON)
public class WageTableWs extends SimpleHistoryWs<WtHead, WtHistory> {

	/** The create wage table history command handler. */
	@Inject
	private WtHistoryAddCommandHandler createWageTableHistoryCommandHandler;

	/** The update wage table history command handler. */
	@Inject
	private WtHistoryUpdateCommandHandler updateWageTableHistoryCommandHandler;

	/** The wage table head repo. */
	@Inject
	private WtHeadRepository wageTableHeadRepo;

	/** The wage table history repo. */
	@Inject
	private WtHistoryRepository wageTableHistoryRepo;

	/** The service. */
	@Inject
	private WtHistoryService service;

	/**
	 * Find.
	 *
	 * @param id
	 *            the id
	 * @return the wage table history model
	 */
	@POST
	@Path("find/{id}")
	public WageTableHistoryDto find(@PathParam("id") String id) {
		// Get the detail history.
		Optional<WtHistory> optWageTableHistory = this.wageTableHistoryRepo
				.findHistoryByUuid(id);
		WageTableHistoryDto historyDto = null;

		// Check exsit.
		if (optWageTableHistory.isPresent()) {
			WtHistory wageTableHistory = optWageTableHistory.get();
			Optional<WtHead> optWageTable = this.wageTableHeadRepo.findByCode(
					wageTableHistory.getCompanyCode().v(), wageTableHistory.getWageTableCode().v());
			historyDto = new WageTableHistoryDto();
			WageTableHeadDto headDto = WageTableHeadDto.builder().build();
			wageTableHistory.saveToMemento(historyDto);
			optWageTable.get().saveToMemento(headDto);
			historyDto.setHead(headDto);
		}

		// Return
		return historyDto;
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
	public HistoryModel create(WtHistoryAddCommand command) {
		WtHistory history = this.createWageTableHistoryCommandHandler.handle(command);
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
	public void update(WtHistoryUpdateCommand command) {
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
		List<WtHead> wageTables = this.wageTableHeadRepo
				.findAll(AppContexts.user().companyCode());

		// Get list of wage table history.
		List<WtHistory> wageTableHistories = this.wageTableHistoryRepo
				.findAll(AppContexts.user().companyCode());

		// Group histories by unit code.
		Map<WtCode, List<HistoryModel>> historyMap = wageTableHistories.stream()
				.collect(Collectors.groupingBy(WtHistory::getWageTableCode,
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

	/**
	 * Load demension selection list.
	 *
	 * @return the list
	 */
	@POST
	@Path("demensions")
	public List<DemensionItemDto> loadDemensionSelectionList() {
		List<DemensionItemDto> items = new ArrayList<>();

		/** The age fix. */
		// AGE_FIX(4, false, true),
		items.add(new DemensionItemDto(ElementType.AGE_FIX));

		/** The experience fix. */
		// EXPERIENCE_FIX(3, false, true),
		items.add(new DemensionItemDto(ElementType.EXPERIENCE_FIX));

		/** The family mem fix. */
		// FAMILY_MEM_FIX(5, false, true),
		items.add(new DemensionItemDto(ElementType.FAMILY_MEM_FIX));

		/** The master ref. */
		// MASTER_REF(0, true, false),
		// Find all in MASTER_REF table.

		/** The code ref. */
		// CODE_REF(1, true, false),
		// Find all in CODE_REF table.

		/** The item data ref. */
		// ITEM_DATA_REF(2, false, true),
		items.add(new DemensionItemDto(ElementType.ITEM_DATA_REF));

		return items;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.ws.base.simplehistory.SimpleHistoryWs#getServices()
	 */
	@Override
	protected SimpleHistoryBaseService<WtHead, WtHistory> getServices() {
		return this.service;
	}
}
