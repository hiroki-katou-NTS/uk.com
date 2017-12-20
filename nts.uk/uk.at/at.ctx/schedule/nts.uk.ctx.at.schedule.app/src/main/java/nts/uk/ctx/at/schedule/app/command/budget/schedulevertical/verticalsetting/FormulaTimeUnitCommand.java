package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaTimeUnit;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.TimeUnitFunc;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormulaTimeUnitCommand {
	
	/** コード */
	private String verticalCalCd;
	
	/** 汎用縦計項目ID */
	private String verticalCalItemId;

	/** 単位 */
	private int roundingTime;

	/** 端数処理 */
	private int roundingAtr;

	/** 単価 */
	private int unitPrice;

	/** 単価 */
	private int actualDisplayAtr;

	private List<TimeUnitFuncCommand> lstTimeUnitFuncs;
	
	/**
	 * toDomainFormTimeUnit
	 * @param companyId
	 * @param verticalCalCd
	 * @param verticalCalItemId
	 * @return
	 */
	 public FormulaTimeUnit toDomainFormTimeUnit(String companyId, String verticalCalCd, String verticalCalItemId){
		 List<TimeUnitFunc> formPeopleLst = this.lstTimeUnitFuncs != null
	    			? this.lstTimeUnitFuncs.stream().map(c -> c.toDomainUnitFunc(companyId,
	    					c.getVerticalCalCd(),
	    					c.getVerticalCalItemId(),
	    					c.getDispOrder(), 
	    					c.getAttendanceItemId(), 
	    					c.getPresetItemId(), 
	    					c.getOperatorAtr())).collect(Collectors.toList())
					: null;
			return FormulaTimeUnit.createFromJavatype(companyId, 
							verticalCalCd, 
							verticalCalItemId, 
							roundingTime, 
							roundingAtr, 
							unitPrice, 
							actualDisplayAtr, 
							formPeopleLst);
	    }	
}
