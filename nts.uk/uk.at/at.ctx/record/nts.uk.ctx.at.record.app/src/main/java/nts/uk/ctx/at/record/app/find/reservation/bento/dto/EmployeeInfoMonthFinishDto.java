package nts.uk.ctx.at.record.app.find.reservation.bento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 月締め処理が済んでいる社員情報
 * @author Le Huu Dat
 */
@Setter
@Getter
@AllArgsConstructor
public class EmployeeInfoMonthFinishDto {
    /** 社員コード */
    private String employeeCode;
    /** 社員名 */
    private String employeeName;
}
