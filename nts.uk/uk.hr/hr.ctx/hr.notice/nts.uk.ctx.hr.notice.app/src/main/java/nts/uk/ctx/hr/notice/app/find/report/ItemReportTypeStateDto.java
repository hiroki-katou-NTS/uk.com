package nts.uk.ctx.hr.notice.app.find.report;

import java.util.List;

import lombok.Getter;
@Getter
public class ItemReportTypeStateDto {
	protected int itemType;
	
	public static ItemReportTypeStateDto createSetItemDto(List<String> items) {
		return SetItemReport.createFromJavaType(items);
	};

	public static ItemReportTypeStateDto createSetTableItemDto(List<String> items) {
		return SetTableItemReportDto.createFromJavaType(items);
	};
//	
//	public static ItemTypeStateDto createSingleItemDto(DataTypeStateDto dataTypeState) {
//		return SingleItemDto.createFromJavaType(dataTypeState);
//	};
}
