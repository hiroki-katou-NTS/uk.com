package nts.uk.ctx.at.shared.infra.entity.scherec.anyperiodattdcal.editstate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KsrdtAnpEditStatePk {
    @Column(name = "SID")
    public String employeeId;

    @Column(name = "CODE")
    public String frameCode;

    @Column(name = "ATTENDANCE_ITEM_ID")
    public int attendanceItemId;
}
