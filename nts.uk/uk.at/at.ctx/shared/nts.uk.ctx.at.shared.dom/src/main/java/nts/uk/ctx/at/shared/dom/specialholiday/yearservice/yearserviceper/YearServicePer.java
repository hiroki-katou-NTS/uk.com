package nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper;

import java.util.List;

import nts.arc.enums.EnumAdaptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.YearServiceIdCls;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.primitives.YearServiceCode;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.primitives.YearServiceName;
@Getter
@AllArgsConstructor
public class YearServicePer extends AggregateRoot{
	/**会社ID**/
	private String companyId;
	/**コード**/
	private String specialHolidayCode;
	private YearServiceCode yearServiceCode;
	/**名称**/
	private YearServiceName yearServiceName;
	private YearServiceIdCls yearServiceCls;
	private List<YearServicePerSet> yearServicePerSets;
	
	public static YearServicePer createFromJavaType(String companyId, String specialHolidayCode, String yearServiceCode, String yearServiceName, int yearServiceCls, List<YearServicePerSet> yearServicePerSets){
		return new YearServicePer(companyId, specialHolidayCode,new YearServiceCode(yearServiceCode), new YearServiceName(yearServiceName), EnumAdaptor.valueOf(yearServiceCls, YearServiceIdCls.class) , yearServicePerSets);
	}
}
