package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

import java.util.Optional;

/**
* 厚生年金基金電子申請内容
*/
@AllArgsConstructor
@Getter
public class EmpPensionFundInfor extends DomainObject
{

    /**
    * 加算適用区分(給与)
    */
    private Optional<AdditiAppCategory> addAppCtgSal;

    /**
    * 加算給与月額
    */
    private Optional<MonthlyFeeForFund> addSal;

    /**
    * 標準給与月額
    */
    private Optional<MonthlyFeeForFund> standSal;

    /**
    * 第２加算給与月額
    */
    private Optional<MonthlyFeeForFund> secAddSalary;

    /**
    * 第２標準給与月額
    */
    private Optional<MonthlyFeeForFund> secStandSal;

    /**
    * 加算適用区分(賞与)
    */
    private Optional<AdditiAppCategory> addAppCtgBs;

    /**
    * 加算賞与月額
    */
    private Optional<MonthlyFeeForFund> addBonus;

    /**
    * 標準賞与月額
    */
    private Optional<MonthlyFeeForFund> standBonus;

    /**
    * 第２加算賞与月額
    */
    private Optional<MonthlyFeeForFund> secStandBonus;

    /**
    * 第２標準賞与月額
    */
    private Optional<MonthlyFeeForFund> secAddBonus;

    /**
    * 基金固有項目１
    */
    private Optional<FundSpecificItems> fundSpecific1;

    /**
    * 基金固有項目２
    */
    private Optional<FundSpecificItems> fundSpecific2;

    /**
    * 基金固有項目３
    */
    private Optional<FundSpecificItems> fundSpecific3;

    /**
    * 基金固有項目４
    */
    private Optional<FundSpecificItems> fundSpecific4;

    /**
    * 基金固有項目５
    */
    private Optional<FundSpecificItems> fundSpecific5;

    /**
    * 基金固有項目７
    */
    private Optional<FundSpecificItems> fundSpecific7;

    /**
    * 基金固有項目８
    */
    private Optional<FundSpecificItems> fundSpecific8;

    /**
    * 基金固有項目９
    */
    private Optional<FundSpecificItems> fundSpecific9;

    /**
    * 基金固有項目１０
    */
    private Optional<FundSpecificItems> fundSpecific10;

    /**
    * 加入形態区分
    */
    private Optional<SubscriptionType> subType;

    /**
    * 適用形態区分
    */
    private Optional<SubscriptionType> appFormCls;

    /**
    * 退職後の郵便番号
    */
    private Optional<String> postCd;

    /**
    * 退職後の住所カナ
    */
    Optional<RetireAddKanaFund> retirementAddBefore;

    /**
    * 退職後の住所
    */
    private Optional<RetireAddFund> retirementAdd;

    /**
    * 喪失理由区分
    */
    private Optional<ReasonForLoss> reasonForLoss;

    /**
    * 適用終了理由区分
    */
    private Optional<TerTypeCls> reason;

    /**
    * 基金固有項目６
    */
    private Optional<FundSpecificItems> fundSpecific6;

    public EmpPensionFundInfor(int addAppCtgSal, String addSal, String standSal, String secAddSalary, String secStandSal, int addAppCtgBs, String addBonus, String standBonus, String secStandBonus, String secAddBonus, String fundSpecific1, String fundSpecific2, String fundSpecific3, String fundSpecific4, String fundSpecific5, String fundSpecific7, String fundSpecific8, String fundSpecific9, String fundSpecific10, int subType, int appFormCls, String postCd, String retirementAddBefore, String retirementAdd, int reasonForLoss, int reason, String fundSpecific6) {
        this.addAppCtgSal =  Optional.of(EnumAdaptor.valueOf(addAppCtgSal, AdditiAppCategory.class));
        this.addSal = addSal == null ? Optional.empty() : Optional.of(new MonthlyFeeForFund(addSal));
        this.standSal = standSal == null ? Optional.empty() : Optional.of(new MonthlyFeeForFund(standSal));
        this.secAddSalary = secAddSalary == null ? Optional.empty() : Optional.of(new MonthlyFeeForFund(secAddSalary));
        this.secStandSal = secStandSal == null ? Optional.empty() : Optional.of(new MonthlyFeeForFund(secStandSal));
        this.addAppCtgBs =  Optional.of(EnumAdaptor.valueOf(addAppCtgBs, AdditiAppCategory.class));
        this.standBonus = standBonus == null ? Optional.empty() : Optional.of(new MonthlyFeeForFund(standBonus));
        this.secStandBonus =secStandBonus == null ? Optional.empty() : Optional.of(new MonthlyFeeForFund(standBonus));
        this.secAddBonus = secAddBonus == null ? Optional.empty() : Optional.of(new MonthlyFeeForFund(secAddBonus));
        this.fundSpecific1 = fundSpecific1 == null ? Optional.empty() : Optional.of(new FundSpecificItems(fundSpecific1));
        this.fundSpecific2 = fundSpecific2 == null ? Optional.empty() : Optional.of(new FundSpecificItems(fundSpecific2));
        this.fundSpecific3 = fundSpecific3 == null ? Optional.empty() : Optional.of(new FundSpecificItems(fundSpecific3));
        this.fundSpecific4 = fundSpecific4== null ? Optional.empty() : Optional.of(new FundSpecificItems(fundSpecific4));
        this.fundSpecific5  = fundSpecific5== null ? Optional.empty() : Optional.of(new FundSpecificItems(fundSpecific5));
        this.fundSpecific7 =fundSpecific7 == null ? Optional.empty() : Optional.of(new FundSpecificItems(fundSpecific7));
        this.fundSpecific8 = fundSpecific8== null ? Optional.empty() : Optional.of(new FundSpecificItems(standBonus));
        this.fundSpecific9  =fundSpecific9 == null ? Optional.empty() : Optional.of(new FundSpecificItems(standBonus));
        this.fundSpecific10 =fundSpecific10 == null ? Optional.empty() : Optional.of(new FundSpecificItems(standBonus));
        this.subType = Optional.of(EnumAdaptor.valueOf(subType, SubscriptionType.class));
        this.appFormCls = Optional.of(EnumAdaptor.valueOf(appFormCls, SubscriptionType.class));
        this.postCd = postCd == null ? Optional.empty() : Optional.of("Postal ");
        this.retirementAddBefore = retirementAddBefore  == null ? Optional.empty() : Optional.of(new RetireAddKanaFund(retirementAddBefore));
        this.retirementAdd =retirementAdd== null ? Optional.empty() : Optional.of(new RetireAddFund(retirementAdd));
        this.reasonForLoss = Optional.of(EnumAdaptor.valueOf(reasonForLoss,ReasonForLoss.class));
        this.reason =  Optional.of(EnumAdaptor.valueOf(reason, TerTypeCls.class));
        this.fundSpecific6 = fundSpecific6  == null ? Optional.empty() : Optional.of(new FundSpecificItems(fundSpecific6));
    }

}
