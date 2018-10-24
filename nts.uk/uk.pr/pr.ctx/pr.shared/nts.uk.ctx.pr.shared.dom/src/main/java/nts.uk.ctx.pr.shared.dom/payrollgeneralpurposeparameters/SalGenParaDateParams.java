package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;


import lombok.Data;

@Data
public class SalGenParaDateParams {
    /**
     * パラメータNo
     */
    private String paraNo;

    /**
     * 会社ID
     */
    private String cID;

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 開始日
     */
    private String startDate;

    /**
     * 終了日
     */
    private String endDate;
}
