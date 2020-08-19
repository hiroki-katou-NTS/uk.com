package nts.uk.ctx.at.record.dom.daily.holidaypriorityorder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 時間休暇相殺優先順位
 * @author daiki_ichioka
 *
 */
@Getter
public class CompanyHolidayPriorityOrder extends AggregateRoot{

	/** 会社ID */
	private final String companyId;
	
	/** 時間休暇相殺優先順位 */
	List<HolidayPriorityOrder> holidayPriorityOrders;
	
	
	/**
	 * constructor
	 * @param companyId 会社ID
	 */
	public CompanyHolidayPriorityOrder(String companyId){
		
		this.companyId = companyId;
		this.holidayPriorityOrders = setDefaultOrders();
	}
	
	/**
	 * デフォルトの優先順位を設定する
	 * @return 優先順位(List)
	 */
	private List<HolidayPriorityOrder> setDefaultOrders(){
		List<HolidayPriorityOrder> orders = Collections.emptyList();
		orders = Arrays.asList(HolidayPriorityOrder.values());
		return orders;
	}
}
