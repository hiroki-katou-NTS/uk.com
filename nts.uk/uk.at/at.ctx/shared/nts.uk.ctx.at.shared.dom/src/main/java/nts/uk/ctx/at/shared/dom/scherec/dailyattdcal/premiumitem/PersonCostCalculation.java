package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.primitive.Memo;

/**
 * 人件費計算設定
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class PersonCostCalculation extends AggregateRoot {

	private String companyID;

	private String historyID;
	
	private GeneralDate startDate;

	private GeneralDate endDate;

	private UnitPrice unitPrice;

	private Memo memo;
	
	private List<PremiumSetting> premiumSettings;
	
	/** 人件費丸め設定 */
	private PersonCostRoundingSetting roundingSet;

	public PersonCostCalculation(String companyID, String historyID, GeneralDate startDate, GeneralDate endDate, 
			UnitPrice unitPrice, Memo memo, List<PremiumSetting> premiumSettings, PersonCostRoundingSetting roundingSet) {
		super();
		this.companyID = companyID;
		this.historyID = historyID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.unitPrice = unitPrice;
		this.memo = memo;
		this.premiumSettings = premiumSettings;
		this.roundingSet = roundingSet;
	} 
}