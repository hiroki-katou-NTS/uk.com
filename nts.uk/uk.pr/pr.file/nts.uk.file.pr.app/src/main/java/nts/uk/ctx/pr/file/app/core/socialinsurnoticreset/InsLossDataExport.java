package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InsLossDataExport {

    private String empId;

    private String officeCode;

    private int other;

    private String otherReason;

    private Integer caInsurance;

    private Integer numRecoved;

    private Integer cause;
}
