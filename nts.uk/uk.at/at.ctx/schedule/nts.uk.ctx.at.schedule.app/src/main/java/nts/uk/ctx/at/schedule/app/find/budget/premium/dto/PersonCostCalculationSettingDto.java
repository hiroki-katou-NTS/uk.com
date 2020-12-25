package nts.uk.ctx.at.schedule.app.find.budget.premium.dto;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.*;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@Getter
public class PersonCostCalculationSettingDto {
	String companyID;

	String historyID;

	Integer unitPrice;

	String memo;

	List<PremiumSetDto> premiumSets;

//	//1: 丸め設定 : 人件費丸め設定
//	private PersonCostRoundingSetting roundingSetting;
//	// 2: 会社ID
//	private String companyID;
//	// 3 : 備考
//	private Remarks remark;
//	// 4: 割増設定 : List<割増設定>
//	private List<PremiumSetting> premiumSettings;
//	// 5: 単価 : Optional<単価>
//	private Optional<UnitPrice> unitPrice;
//	// 6: 単価の設定方法 :単価の設定方法
//	private HowToSetUnitPrice howToSetUnitPrice;
//	// 7: 就業時間単価: 単価
//	private WorkingHoursUnitPrice workingHoursUnitPrice;
//	// 8: 履歴ID
//	private String historyID;
}



