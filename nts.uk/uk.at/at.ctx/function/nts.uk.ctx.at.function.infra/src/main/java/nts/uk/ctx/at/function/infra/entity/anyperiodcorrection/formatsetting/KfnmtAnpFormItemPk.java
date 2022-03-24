package nts.uk.ctx.at.function.infra.entity.anyperiodcorrection.formatsetting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KfnmtAnpFormItemPk {
    @Column(name = "CID")
    public String companyId;

    @Column(name = "CODE")
    public String code;

    @Column(name = "SHEET_NO")
    public int sheetNo;

    @Column(name = "ATTENDANCE_ITEM_ID")
    public int attendanceItemId;
}
