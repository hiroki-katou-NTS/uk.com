package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.UnitPrice;
import nts.uk.shr.com.primitive.Memo;

/**
 * 人件費計算設定
 *
 * @author Doan Duy Hung
 */
@Value
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PersonCostCalculation extends AggregateRoot {
//    private String companyID;
//    private List<PremiumSetting> premiumSettings;
//    private UnitPrice unitPrice;
//
//    private String historyID;
//
//    private GeneralDate startDate;
//
//    private GeneralDate endDate;
//    private Memo memo;
//
//    public PersonCostCalculation(String companyID, String historyID, GeneralDate startDate, GeneralDate endDate,
//                                 UnitPrice unitPrice, Memo memo, List<PremiumSetting> premiumSettings) {
//        super();
//        this.companyID = companyID;
//        this.historyID = historyID;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.unitPrice = unitPrice;
//        this.memo = memo;
//        this.premiumSettings = premiumSettings;
//    }

    //1: 丸め設定 : 人件費丸め設定
    private PersonCostRoundingSetting roundingSetting;
    // 2: 会社ID
    private String companyID;
    // 3 : 備考
    private Remarks remark;
    // 4: 割増設定 : List<割増設定>`
    private List<PremiumSetting> premiumSettings;
    // 5: 単価 : Optional<単価>
    private Optional<UnitPrice> unitPrice;
    // 6: 単価の設定方法 :単価の設定方法
    private HowToSetUnitPrice howToSetUnitPrice;
    // 7: 就業時間単価: 単価
    private UnitPrice workingHoursUnitPrice;
    // 8: 履歴ID
    private String historyID;

    // 仮計算する
    public List<?> tentativeCalculation(int unitPrice, Map<Object, Object> workingHours) {
        return null;
    }
    
	/**
	 * 社員時間単価NOを取得する
	 * @param no 割増時間項目NO
	 * @return 「単価の設定方法」を判断して取得した「社員時間単価NO」
	 */
	public Optional<UnitPrice> getUnitPriceAsJudged(ExtraTimeItemNo no){
		if(this.howToSetUnitPrice.isPremiumRate()) {
			return this.unitPrice;
		}
		if(this.howToSetUnitPrice.isUnitPrice()) {
			return this.premiumSettings.stream()
					.filter(p -> p.getID().equals(no))
					.map(p -> p.getUnitPrice())
					.findFirst();
		}
		return Optional.empty();
	}
}