package nts.uk.ctx.pr.file.app.core.temp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InsLossDataExport {
    /**
     * 社員ID
     */
    private String empId;

    /**
     * その他
     */
    private int other;

    /**
     * その他理由
     */
    private String otherReason;

    /**
     * 保険証回収添付枚数
     */
    private Integer caInsurance;

    /**
     * 保険証回収返不能枚数
     */
    private Integer numRecoved;

    /**
     * 資格喪失原因
     */
    private Integer cause;
}
