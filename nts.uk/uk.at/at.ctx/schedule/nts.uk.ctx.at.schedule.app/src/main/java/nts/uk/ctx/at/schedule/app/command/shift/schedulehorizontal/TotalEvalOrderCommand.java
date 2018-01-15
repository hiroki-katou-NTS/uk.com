package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriCalDaysSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCNTSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class TotalEvalOrderCommand {
	/** 集計項目NO */
	private int totalItemNo;
	/** 並び順 */
	private Integer dispOrder;
	private HoriCalDaysSetCommand horiCalDaysSet;
	private List<HoriTotalCNTSetCommand> cntSetls;
	
	/**
	 * convert total eval order to domain
	 * @param companyId
	 * @param categoryCode
	 * @return
	 */
	public TotalEvalOrder toDomainOrder(String companyId, String categoryCode){
		HoriCalDaysSet horiCalDaysSet = this.horiCalDaysSet != null 
				? this.horiCalDaysSet.toDomainCalSet(companyId, categoryCode, totalItemNo)
				: null;
		List<HoriTotalCNTSet> calsetList = this.cntSetls != null
				? this.cntSetls.stream().map(c -> c.toDomainCNTSet(companyId, c.getCategoryCode(), c.getTotalItemNo(), c.getTotalTimeNo())).collect(Collectors.toList())
				: null;
		return TotalEvalOrder.createFromJavaType(companyId, categoryCode, 
												totalItemNo, dispOrder, 
												horiCalDaysSet, calsetList);
	}
}
