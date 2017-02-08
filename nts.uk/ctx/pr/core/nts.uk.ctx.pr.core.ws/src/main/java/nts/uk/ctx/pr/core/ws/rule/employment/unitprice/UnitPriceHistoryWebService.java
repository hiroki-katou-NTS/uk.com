package nts.uk.ctx.pr.core.ws.rule.employment.unitprice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.core.app.rule.employment.unitprice.command.CreateUnitPriceHistoryCommand;
import nts.uk.ctx.core.app.rule.employment.unitprice.command.CreateUnitPriceHistoryCommandHandler;
import nts.uk.ctx.core.app.rule.employment.unitprice.command.DeleteUnitPriceHistoryCommand;
import nts.uk.ctx.core.app.rule.employment.unitprice.command.DeleteUnitPriceHistoryCommandHandler;
import nts.uk.ctx.core.app.rule.employment.unitprice.command.UpdateUnitPriceHistoryCommand;
import nts.uk.ctx.core.app.rule.employment.unitprice.command.UpdateUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.find.UnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.find.UnitPriceHistoryFinder;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;

/**
 * The Class UnitPriceHistoryWebService.
 */
@Path("pr/proto/unitprice")
@Produces("application/json")
public class UnitPriceHistoryWebService extends WebService {

	/** The unit price history finder. */
	@Inject
	private UnitPriceHistoryFinder unitPriceHistoryFinder;

	/** The create unit price history command handler. */
	@Inject
	private CreateUnitPriceHistoryCommandHandler createUnitPriceHistoryCommandHandler;

	/** The update unit price history command handler. */
	@Inject
	private UpdateUnitPriceHistoryCommandHandler updateUnitPriceHistoryCommandHandler;

	/** The delete unit price history command handler. */
	@Inject
	private DeleteUnitPriceHistoryCommandHandler deleteUnitPriceHistoryCommandHandler;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<UnitPriceHistoryDto> findAll() {
		return getMockData();
	}

	/**
	 * Find.
	 *
	 * @param id
	 *            the id
	 * @return the unit price history dto
	 */
	@POST
	@Path("find/{id}")
	public UnitPriceHistoryDto find(@PathParam("id") String id) {
		List<UnitPriceHistoryDto> mock = getMockData();
		UnitPriceHistoryDto dto = UnitPriceHistoryDto.builder().build();
		for (UnitPriceHistoryDto i : mock) {
			if (id.equals(i.getId())) {
				dto = i;
			}
		}
		return dto;
	}

	/**
	 * Creates the.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("create")
	public void create(CreateUnitPriceHistoryCommand command) {
		this.createUnitPriceHistoryCommandHandler.handle(command);
	}

	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("update")
	public void update(UpdateUnitPriceHistoryCommand command) {
		this.updateUnitPriceHistoryCommandHandler.handle(command);
	}

	/**
	 * Removes the.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("remove")
	public void remove(DeleteUnitPriceHistoryCommand command) {
		this.deleteUnitPriceHistoryCommandHandler.handle(command);
	}

	/**
	 * Gets the mock data.
	 *
	 * @return the mock data
	 */
	private List<UnitPriceHistoryDto> getMockData() {
		List<UnitPriceHistoryDto> mock = new ArrayList<UnitPriceHistoryDto>();
		mock.add(UnitPriceHistoryDto.builder().id("1").version(1).unitPriceCode("001").unitPriceName("ガソリン単価")
				.startMonth("2015/04").endMonth("2016/05").budget(new BigDecimal(340))
				.fixPaySettingType(SettingType.Company).fixPayAtr(ApplySetting.Apply).fixPayAtrDaily(ApplySetting.Apply)
				.fixPayAtrDayMonth(ApplySetting.NotApply).fixPayAtrHourly(ApplySetting.Apply)
				.fixPayAtrMonthly(ApplySetting.Apply).memo("1").build());
		mock.add(UnitPriceHistoryDto.builder().id("2").version(1).unitPriceCode("001").unitPriceName("ガソリン単価")
				.startMonth("2016/06").endMonth("9999/03").budget(new BigDecimal(340))
				.fixPaySettingType(SettingType.Contract).fixPayAtr(ApplySetting.Apply)
				.fixPayAtrDaily(ApplySetting.Apply).fixPayAtrDayMonth(ApplySetting.Apply)
				.fixPayAtrHourly(ApplySetting.NotApply).fixPayAtrMonthly(ApplySetting.NotApply).memo("2").build());
		mock.add(UnitPriceHistoryDto.builder().id("3").version(1).unitPriceCode("002").unitPriceName("宿直単価").startMonth("2016/04")
				.endMonth("9999/04").budget(new BigDecimal(340)).fixPaySettingType(SettingType.Company)
				.fixPayAtr(ApplySetting.Apply).fixPayAtrDaily(ApplySetting.Apply)
				.fixPayAtrDayMonth(ApplySetting.NotApply).fixPayAtrHourly(ApplySetting.Apply)
				.fixPayAtrMonthly(ApplySetting.NotApply).memo("3").build());
		mock.add(UnitPriceHistoryDto.builder().id("4").version(1).unitPriceCode("002").unitPriceName("宿直単価").startMonth("2015/04")
				.endMonth("2016/03").budget(new BigDecimal(340)).fixPaySettingType(SettingType.Contract)
				.fixPayAtr(ApplySetting.Apply).fixPayAtrDaily(ApplySetting.NotApply)
				.fixPayAtrDayMonth(ApplySetting.Apply).fixPayAtrHourly(ApplySetting.NotApply)
				.fixPayAtrMonthly(ApplySetting.Apply).memo("4").build());
		return mock;
	}
}
