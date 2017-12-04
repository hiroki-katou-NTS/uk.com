package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import java.util.List;

import lombok.Value;

@Value
public class ReferenceHistoryDto {
	private List<String> lstSelItemId;
	private String baseDate;
}
