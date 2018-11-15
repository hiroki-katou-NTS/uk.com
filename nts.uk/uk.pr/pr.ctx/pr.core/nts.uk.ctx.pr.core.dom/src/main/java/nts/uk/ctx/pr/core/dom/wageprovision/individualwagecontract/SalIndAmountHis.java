package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import java.util.Optional;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
 * 給与個人別金額履歴
 */
@Getter
public class SalIndAmountHis extends AggregateRoot {

    /**
     * 個人金額コード
     */
    private String perValCode;

    /**
     * 社員ID
     */
    private String empId;

    /**
     * カテゴリ区分
     */
    private CategoryIndicator cateIndicator;

    /**
     * 期間
     */
    private List<GenericHistYMPeriod> period;

    /**
     * 給与賞与区分
     */
    private SalBonusCate salBonusCate;

    public SalIndAmountHis(String perValCode, String empId, int cateIndicator, List<GenericHistYMPeriod> period, int salBonusCate) {
        super();
        this.empId = empId;
        this.perValCode = perValCode;
        this.salBonusCate = EnumAdaptor.valueOf(salBonusCate, SalBonusCate.class);
        this.cateIndicator = EnumAdaptor.valueOf(cateIndicator, CategoryIndicator.class);
        this.period = period;
    }


}
