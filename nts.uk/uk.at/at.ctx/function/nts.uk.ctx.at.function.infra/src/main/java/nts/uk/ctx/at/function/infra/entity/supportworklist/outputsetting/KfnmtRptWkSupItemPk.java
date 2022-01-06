package nts.uk.ctx.at.function.infra.entity.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KfnmtRptWkSupItemPk {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "CODE")
    public String code;

    @Column(name = "ATTENDANCE_ITEM_ID")
    public int attendanceItemId;
}
