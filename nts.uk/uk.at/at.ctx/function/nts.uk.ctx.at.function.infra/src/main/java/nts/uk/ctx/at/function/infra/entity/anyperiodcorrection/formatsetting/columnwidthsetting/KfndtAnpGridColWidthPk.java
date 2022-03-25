package nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting.columnwidthsetting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KfndtAnpGridColWidthPk {
    @Column(name = "SID")
    public String employeeId;

    @Column(name = "ATTENDANCE_ITEM_ID")
    public int attendanceItemId;
}
