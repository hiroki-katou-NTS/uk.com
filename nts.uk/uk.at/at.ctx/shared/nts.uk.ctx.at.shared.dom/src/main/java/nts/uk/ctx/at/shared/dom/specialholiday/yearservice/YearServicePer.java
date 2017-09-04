package nts.uk.ctx.at.shared.dom.specialholiday.yearservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@Getter
public class YearServicePer {

	/*会社ID*/
	private String companyId;
	
	/*勤続年数基ID*/
	private String yearServiceId ;
	
	/*勤続年数基コード*/
	private YearServiceCode yearServiceCode;
	
	/*勤続年数基名称*/
	private YearServiceName yearServiceName;
	
	/*勤続年数基準日*/
	private YearServiceIdCls yearServiceIdCls;
	
	public static YearServicePer createSimpleFromJavaType(String companyId,
			String yearServiceId,
			String yearServiceCode,
			String yearServiceName, 
			int yearServiceIdCls){
					return new YearServicePer(companyId, 
							yearServiceId,
							new YearServiceCode(yearServiceCode),
							new YearServiceName(yearServiceName),
							EnumAdaptor.valueOf(yearServiceIdCls, YearServiceIdCls.class));
	}
}
