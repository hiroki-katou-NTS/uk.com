package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class SelectionRadioReportDto extends SelectionItemReportDto {

	private ReferenceTypesReport referenceType;

	public SelectionRadioReportDto(ReferenceTypesReport referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValueReport.SELECTION_BUTTON.value;
	}
}
