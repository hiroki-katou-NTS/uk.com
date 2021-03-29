package nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BanHolidayTogetherQueryDto {
    public BanHolidayTogetherQueryDto() {
        banHolidayTogetherCode = "";
        banHolidayTogetherName = "";
        checkDayReference = false;
        selectedWorkDayReference = null;
        minOfWorkingEmpTogether = null;
        classificationCode = "";
        classificationName = "";
        workplaceInfoId = "";
        workplaceInfoCode = "";
        workplaceInfoName = "";
        empsCanNotSameHolidays = null;
    }

    /** コード */
    private String banHolidayTogetherCode;

    /** 名称 */
    private String banHolidayTogetherName;

    /** 稼働日のみとする */
    private boolean checkDayReference;

    /** 稼働日の参照先 */
    private Integer selectedWorkDayReference;

    /** 最低限出勤すべき人数 */
    private Integer minOfWorkingEmpTogether;


    /** 分類コード */
    private String classificationCode;

    /** 分類名称 */
    private String classificationName;


    /** 職場ID */
    private String workplaceInfoId;

    /** 職場コード */
    private String workplaceInfoCode;

    /** 職場表示名 */
    private String workplaceInfoName;

    /** 同日の休日取得を禁止する社員 */
    private List<String> empsCanNotSameHolidays;
}
