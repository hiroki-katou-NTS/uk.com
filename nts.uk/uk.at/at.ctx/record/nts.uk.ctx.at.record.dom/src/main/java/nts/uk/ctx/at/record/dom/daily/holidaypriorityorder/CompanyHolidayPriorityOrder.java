package nts.uk.ctx.at.record.dom.daily.holidaypriorityorder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
// 時間休暇相殺優先順位
public class CompanyHolidayPriorityOrder extends AggregateRoot{

	/** 会社ID */
	private final String companyId;
	
	/** 時間休暇相殺優先順位 */
	List<HolidayPriorityOrder> holidayPriorityOrders;
	
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public CompanyHolidayPriorityOrder(String companyId){
		
		this.companyId = companyId;
		this.holidayPriorityOrders = setDefaultOrders();
	}
	
	private List<HolidayPriorityOrder> setDefaultOrders(){
		List<HolidayPriorityOrder> orders = Collections.emptyList();
		orders = Arrays.asList(HolidayPriorityOrder.values());
		return orders;
	}
}
