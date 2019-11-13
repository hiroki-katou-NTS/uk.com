package nts.uk.ctx.hr.notice.app.find.report;

import java.util.List;

import lombok.Getter;
@Getter
public class SetTableItemReportDto extends ItemReportTypeStateDto{
private List<String> items;
	
	private SetTableItemReportDto(List<String> items) {
		super();
		this.itemType = 3;
		this.items = items;
	}

	public static SetTableItemReportDto createFromJavaType(List<String> items) {
		return new SetTableItemReportDto(items);
	}
}
