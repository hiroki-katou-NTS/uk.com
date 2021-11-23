package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.record.dom.workrecord.role.Role;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ReservationQueryOuput {

    // 予約設定
    public ReservationSettingDto setting;
    
    // ロール
    public List<Role> roles;
    
    public List<EnumConstant> contentChangeDeadlineDayEnum;
}
