package nts.uk.ctx.pr.core.ws.rule.employment.unitprice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.rule.employment.unitprice.UnitPriceHistoryDto;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;

@Path("pr/proto/unitprice")
@Produces("application/json")
public class UnitPriceHistoryWebService extends WebService {
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
	public void add() {
	}

	@POST
	@Path("update")
	public void update() {
	}

	@POST
	@Path("remove")
	public void remove() {
	}

	private List<UnitPriceHistoryDto> getMockData() {
		List<UnitPriceHistoryDto> mock = new ArrayList<UnitPriceHistoryDto>();
		mock.add(UnitPriceHistoryDto.builder().id("1").unitPriceCode("001").unitPriceName("ガソリン単価")
				.startMonth("2015/04").endMonth("9999/04").budget(120).fixPaySettingType(SettingType.Contract)
				.fixPayAtr(ApplySetting.Apply).fixPayAtrDaily(ApplySetting.Apply).fixPayAtrDayMonth(ApplySetting.Apply)
				.fixPayAtrHourly(ApplySetting.Apply).fixPayAtrMonthly(ApplySetting.Apply).memo("abc").build());
		mock.add(UnitPriceHistoryDto.builder().id("2").unitPriceCode("001").unitPriceName("ガソリン単価")
				.startMonth("2015/04").endMonth("9999/04").budget(230).fixPaySettingType(SettingType.Contract)
				.fixPayAtr(ApplySetting.Apply).fixPayAtrDaily(ApplySetting.Apply).fixPayAtrDayMonth(ApplySetting.Apply)
				.fixPayAtrHourly(ApplySetting.Apply).fixPayAtrMonthly(ApplySetting.Apply).memo("abc").build());
		mock.add(UnitPriceHistoryDto.builder().id("3").unitPriceCode("002").unitPriceName("宿直単価").startMonth("2015/04")
				.endMonth("9999/04").budget(340).fixPaySettingType(SettingType.Contract).fixPayAtr(ApplySetting.Apply)
				.fixPayAtrDaily(ApplySetting.Apply).fixPayAtrDayMonth(ApplySetting.Apply)
				.fixPayAtrHourly(ApplySetting.Apply).fixPayAtrMonthly(ApplySetting.Apply).memo("abc").build());
		mock.add(UnitPriceHistoryDto.builder().id("4").unitPriceCode("002").unitPriceName("宿直単価").startMonth("2015/04")
				.endMonth("9999/04").budget(450).fixPaySettingType(SettingType.Contract).fixPayAtr(ApplySetting.Apply)
				.fixPayAtrDaily(ApplySetting.Apply).fixPayAtrDayMonth(ApplySetting.Apply)
				.fixPayAtrHourly(ApplySetting.Apply).fixPayAtrMonthly(ApplySetting.Apply).memo("abc").build());
		return mock;
	}
}
