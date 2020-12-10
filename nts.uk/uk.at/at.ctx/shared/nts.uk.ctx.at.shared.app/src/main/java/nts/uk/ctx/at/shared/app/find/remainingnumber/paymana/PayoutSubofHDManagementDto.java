package nts.uk.ctx.at.shared.app.find.remainingnumber.paymana;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PayoutSubofHDManagementDto {
    // 社員ID
    private String sid;

    // 逐次休暇の紐付け情報 . 発生日
    private GeneralDate outbreakDay;

    // 逐次休暇の紐付け情報 . 使用日
    private GeneralDate dateOfUse;

    // 逐次休暇の紐付け情報 . 使用日数
    private double dayNumberUsed;

    // 逐次休暇の紐付け情報 . 対象選択区分
    private int targetSelectionAtr;

    public PayoutSubofHDManagement toDomain() {
        return new PayoutSubofHDManagement(sid, outbreakDay, dateOfUse, dayNumberUsed, targetSelectionAtr);
    }

    public static PayoutSubofHDManagementDto fromDomain(PayoutSubofHDManagement domain) {
        return new PayoutSubofHDManagementDto(
                domain.getSid(),
                domain.getAssocialInfo().getOutbreakDay(),
                domain.getAssocialInfo().getDateOfUse(),
                domain.getAssocialInfo().getDayNumberUsed().v(),
                domain.getAssocialInfo().getTargetSelectionAtr().value
        );
    }
}
