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
	private int specialHolidayCode;
	private YearServiceCode yearServiceCode;
	/**名称**/
	private YearServiceName yearServiceName;
	private int provision;
	private YearServiceIdCls yearServiceCls;
	private List<YearServicePerSet> yearServicePerSets;
	
	public static YearServicePer createFromJavaType(String companyId, int specialHolidayCode, String yearServiceCode, String yearServiceName, int provision, int yearServiceCls, List<YearServicePerSet> yearServicePerSets){
		return new YearServicePer(companyId, specialHolidayCode,new YearServiceCode(yearServiceCode), new YearServiceName(yearServiceName), provision, EnumAdaptor.valueOf(yearServiceCls, YearServiceIdCls.class) , yearServicePerSets);
	}
}
