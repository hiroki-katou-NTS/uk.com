package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalOverTimeTransferOrder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalOverTimeTransferOrderOfAggrMonthly;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;


/**
 * 月次集計の法定内残業振替順　（テスト）
 * @author shuichu_ishida
 */
public class LegalOverTimeTransferOrderOfAggrMonthlyTest {

	private LegalOverTimeTransferOrderOfAggrMonthly targetClass;
	
	private void setup(){
		List<LegalOverTimeTransferOrder> legalOverTimeTransferOrders = new ArrayList<LegalOverTimeTransferOrder>();
		legalOverTimeTransferOrders.add(LegalOverTimeTransferOrder.of(new OverTimeFrameNo(1), 3));
		legalOverTimeTransferOrders.add(LegalOverTimeTransferOrder.of(new OverTimeFrameNo(2), 2));
		legalOverTimeTransferOrders.add(LegalOverTimeTransferOrder.of(new OverTimeFrameNo(3), 1));
		this.targetClass = LegalOverTimeTransferOrderOfAggrMonthly.of(legalOverTimeTransferOrders);
	}
	
	@Test
	public void factoryTest(){
		
		// setup
		this.setup();
		
		// exercize
		String actual = "";
		for (LegalOverTimeTransferOrder legalOverTimeTransferOrder : this.targetClass.getLegalOverTimeTransferOrders()){
			actual += legalOverTimeTransferOrder.getOverTimeFrameNo().v().toString();
		}
		
		// verify
		assertEquals("321", actual);
	}
}
