package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatedCategoryReportDto extends DataTypeStateReportDto {
	private String relatedCtgCode;

	public RelatedCategoryReportDto(String relatedCtgCode) {
		super();
		this.dataTypeValue = DataTypeValueReport.RELATE_CATEGORY.value;
		this.relatedCtgCode = relatedCtgCode;
	}

	public static RelatedCategoryReportDto createFromJavaType(String relatedCtgCode) {
		return new RelatedCategoryReportDto(relatedCtgCode);
	}

}
