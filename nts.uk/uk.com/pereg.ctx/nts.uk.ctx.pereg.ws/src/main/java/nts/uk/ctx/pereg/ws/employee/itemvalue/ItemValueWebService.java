package nts.uk.ctx.pereg.ws.employee.itemvalue;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.itemvalue.FamilyCommand;
import nts.uk.ctx.bs.employee.app.command.itemvalue.ItemDefinitionValueCommand;
import nts.uk.ctx.pereg.app.find.itemvalue.ItemClassificationDto;
import nts.uk.ctx.pereg.app.find.itemvalue.ItemDefinitionValueDto;

@Path("bs/itemvalue")
@Produces(MediaType.APPLICATION_JSON)
public class ItemValueWebService extends WebService {

	@POST
	@Path("putvalue")
	public List<ItemDefinitionValueCommand> post(FamilyCommand command) {
		List<ItemDefinitionValueCommand> c = command.getItems();
		return c;
	}

	@POST
	@Path("getvalue")
	public List<Object> get() {
		List<Object> data = new ArrayList<Object>();
		{
			data.add(getSingle());
			data.add(getSet());
			data.add(getList());
		}

		return data;
	}

	@POST
	@Path("getsingle")
	public ItemClassificationDto getSingle() {
		ItemClassificationDto dto = new ItemClassificationDto();
		{
			List<Object> data = new ArrayList<Object>();

			ItemDefinitionValueDto singelItem = new ItemDefinitionValueDto("CODE", "NAME", 2);
			data.add(singelItem);

			dto.setItems(data);
		}
		return dto;
	}

	@POST
	@Path("getset")
	public ItemClassificationDto getSet() {
		ItemClassificationDto dto = new ItemClassificationDto();
		{
			List<Object> data = new ArrayList<Object>();

			ItemDefinitionValueDto setItem1 = new ItemDefinitionValueDto("CODE1", "NAME1", 0);
			ItemDefinitionValueDto setItem2 = new ItemDefinitionValueDto("CODE2", "NAME2", 12.5);
			ItemDefinitionValueDto setItem3 = new ItemDefinitionValueDto("CODE3", "NAME3", "value");

			data.add(setItem1);
			data.add(setItem2);
			data.add(setItem3);

			dto.setItems(data);
		}
		return dto;
	}

	@POST
	@Path("getlist")
	public ItemClassificationDto getList() {
		ItemClassificationDto dto = new ItemClassificationDto();
		{
			List<Object> data = new ArrayList<Object>();

			for (int i = 0; i <= 3; i++) {
				List<ItemDefinitionValueDto> set1 = new ArrayList<ItemDefinitionValueDto>();

				ItemDefinitionValueDto setItem11 = new ItemDefinitionValueDto("CODE1", "NAME1", 0);
				ItemDefinitionValueDto setItem12 = new ItemDefinitionValueDto("CODE2", "NAME2", 12.5);
				ItemDefinitionValueDto setItem13 = new ItemDefinitionValueDto("CODE3", "NAME3", "value");

				set1.add(setItem11);
				set1.add(setItem12);
				set1.add(setItem13);

				data.add(set1);
			}

			dto.setItems(data);
		}
		return dto;
	}
}
