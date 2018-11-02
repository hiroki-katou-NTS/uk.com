package nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
 * 給与分類情報
 */
@Getter
public class SalaryClassificationInformation extends AggregateRoot {

    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 給与分類コード
     */
    private SalaryClassificationCode salaryClassificationCode;

    /**
     * 給与分類名称
     */
    private SalaryClassificationName salaryClassificationName;

    /**
     * メモ
     */
    private Optional<Memo> memo;

    public SalaryClassificationInformation(String cid, String salaryClsCd, String salaryClsName, String memo) {
        this.companyId = cid;
        this.salaryClassificationCode = new SalaryClassificationCode(salaryClsCd);
        this.salaryClassificationName = new SalaryClassificationName(salaryClsName);
        this.memo = memo == null ? Optional.empty() : Optional.of(new Memo(memo));
    }

}
