package nts.uk.screen.at.app.ksu008.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailSettingOfForm9Dto {
    /**
     * 明細開始行
     **/
    private Integer bodyStartRow;

    /**
     * 表示人数
     **/
    private Integer maxNumerOfPeople;

    /**
     * 日にち行
     **/
    private Integer rowDate;

    /**
     * 曜日行
     **/
    private Integer rowDayOfWeek;
}
