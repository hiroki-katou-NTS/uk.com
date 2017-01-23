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
import nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.CreateUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.CreateUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.DeleteUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.DeleteUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.UpdateUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.UpdateUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.find.rule.employment.unitprice.UnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.rule.employment.unitprice.UnitPriceHistoryFinder;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;

@Path("pr/proto/unitprice")
@Produces("application/json")
public class UnitPriceHistoryWebService extends WebService {

	@Inject
	private UnitPriceHistoryFinder unitPriceHistoryFinder;
	@Inject
	private CreateUnitPriceHistoryCommandHandler createUnitPriceHistoryCommandHandler;
	@Inject
	private UpdateUnitPriceHistoryCommandHandler updateUnitPriceHistoryCommandHandler;
	@Inject
	private DeleteUnitPriceHistoryCommandHandler deleteUnitPriceHistoryCommandHandler;

	@POST
	@Path("findall")
	public List<UnitPriceHistoryDto> findAll() {
		return getMockData();
	}

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

	@POST
	@Path("create")
	public void create(CreateUnitPriceHistoryCommand command) {
		this.createUnitPriceHistoryCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(UpdateUnitPriceHistoryCommand command) {
		this.updateUnitPriceHistoryCommandHandler.handle(command);
	}

	@POST
	@Path("remove")
	public void remove(DeleteUnitPriceHistoryCommand command) {
		this.deleteUnitPriceHistoryCommandHandler.handle(command);
	}

	private List<UnitPriceHistoryDto> getMockData() {
		List<UnitPriceHistoryDto> mock = new ArrayList<UnitPriceHistoryDto>();
		mock.add(UnitPriceHistoryDto.builder().id("1").unitPriceCode("001").unitPriceName("ガソリン単価")
				.startMonth("2015/04").endMonth("2016/05").budget(new BigDecimal(340))
				.fixPaySettingType(SettingType.Company).fixPayAtr(ApplySetting.Apply).fixPayAtrDaily(ApplySetting.Apply)
				.fixPayAtrDayMonth(ApplySetting.NotApply).fixPayAtrHourly(ApplySetting.Apply)
				.fixPayAtrMonthly(ApplySetting.Apply).memo("1").build());
		mock.add(UnitPriceHistoryDto.builder().id("2").unitPriceCode("001").unitPriceName("ガソリン単価")
				.startMonth("2016/06").endMonth("9999/03").budget(new BigDecimal(340))
				.fixPaySettingType(SettingType.Contract).fixPayAtr(ApplySetting.Apply)
				.fixPayAtrDaily(ApplySetting.Apply).fixPayAtrDayMonth(ApplySetting.Apply)
				.fixPayAtrHourly(ApplySetting.NotApply).fixPayAtrMonthly(ApplySetting.NotApply).memo("2").build());
		mock.add(UnitPriceHistoryDto.builder().id("3").unitPriceCode("002").unitPriceName("宿直単価").startMonth("2016/04")
				.endMonth("9999/04").budget(new BigDecimal(340)).fixPaySettingType(SettingType.Company)
				.fixPayAtr(ApplySetting.Apply).fixPayAtrDaily(ApplySetting.Apply)
				.fixPayAtrDayMonth(ApplySetting.NotApply).fixPayAtrHourly(ApplySetting.Apply)
				.fixPayAtrMonthly(ApplySetting.NotApply).memo("3").build());
		mock.add(UnitPriceHistoryDto.builder().id("4").unitPriceCode("002").unitPriceName("宿直単価").startMonth("2015/04")
				.endMonth("2016/03").budget(new BigDecimal(340)).fixPaySettingType(SettingType.Contract)
				.fixPayAtr(ApplySetting.Apply).fixPayAtrDaily(ApplySetting.NotApply)
				.fixPayAtrDayMonth(ApplySetting.Apply).fixPayAtrHourly(ApplySetting.NotApply)
				.fixPayAtrMonthly(ApplySetting.Apply).memo("4").build());
		return mock;
	}
}
