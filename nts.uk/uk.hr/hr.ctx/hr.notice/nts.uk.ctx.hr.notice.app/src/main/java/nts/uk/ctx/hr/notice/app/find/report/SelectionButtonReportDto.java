package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class SelectionButtonReportDto extends SelectionItemReportDto {

	private ReferenceTypesReport referenceType;

	public SelectionButtonReportDto(ReferenceTypesReport referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValueReport.SELECTION_BUTTON.value;
	}
}
