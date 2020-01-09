package nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmpTiedProYearImport {
    /**
     * 会社ID
     */
    private String cid;

    /**
     * 処理区分NO
     */
    private int processCateNo;

    /**
     * 雇用コード
     */
    private List<String> employmentCodes;
}
