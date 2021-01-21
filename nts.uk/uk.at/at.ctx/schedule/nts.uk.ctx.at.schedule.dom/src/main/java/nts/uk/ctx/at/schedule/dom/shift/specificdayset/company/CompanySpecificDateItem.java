package nts.uk.ctx.at.schedule.dom.shift.specificdayset.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificName;
/**
 * 全社特定日設定
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CompanySpecificDateItem extends AggregateRoot {
	private String companyId;
	private GeneralDate specificDate;
	private SpecificDateItemNo specificDateItemNo;
	private SpecificName specificDateItemName;

	public static CompanySpecificDateItem createFromJavaType(String companyId, GeneralDate specificDate, Integer specificDateItemNo, String specificDateItemName) {
		return new CompanySpecificDateItem(
				companyId, 
				specificDate,
				new SpecificDateItemNo(specificDateItemNo),
				new SpecificName(specificDateItemName));
	}
}
