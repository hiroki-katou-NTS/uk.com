package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ExcessErrorContent;

/**
 * 超過エラー内容
 *
 * @author Le Huu Dat
 */
@Getter
public class ExcessErrorContentDto {

    public ExcessErrorContentDto(ExcessErrorContent domain){
        this.errorClassification = domain.getErrorClassification().value;
        if (domain.getMaximumTimeMonth().isPresent()){
            this.maximumTimeMonth = domain.getMaximumTimeMonth().get().v();
        }
        if (domain.getMaximumTimeMonth().isPresent()){
            this.maximumTimeMonth = domain.getMaximumTimeMonth().get().v();
        }
        if (domain.getMaximumTimeYear().isPresent()){
            this.maximumTimeYear = domain.getMaximumTimeYear().get().v();
        }
        if (domain.getExceedUpperLimit().isPresent()){
            this.exceedUpperLimit = domain.getExceedUpperLimit().get().value;
        }
    }

    /**エラー区分*/
    private Integer errorClassification;

    /**１ヶ月上限時間*/
    private Integer maximumTimeMonth;

    /**1年上限間時間*/
    private Integer maximumTimeYear;

    /**超過上限回数*/
    private Integer exceedUpperLimit;
}
