package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApplicationTime;

@Getter
public class ApplicationTimeDto {
    public ApplicationTimeDto(ApplicationTime applicationTime) {
        this.typeAgreement = applicationTime.getTypeAgreement().value;
        if (applicationTime.getOneMonthTime().isPresent())
            this.oneMonthTime = new OneMonthTimeDto(applicationTime.getOneMonthTime().get());
        if (applicationTime.getOneYearTime().isPresent())
            this.oneYearTime = new OneYearTimeDto(applicationTime.getOneYearTime().get());
    }

    /**
     * ３６協定申請種類
     */
    private int typeAgreement;
    /**
     * 1ヶ月時間
     */
    private OneMonthTimeDto oneMonthTime;
    /**
     * 年間時間
     */
    private OneYearTimeDto oneYearTime;

}
