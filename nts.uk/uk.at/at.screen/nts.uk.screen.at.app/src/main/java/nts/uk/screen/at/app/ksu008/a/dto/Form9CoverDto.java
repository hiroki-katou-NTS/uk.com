package nts.uk.screen.at.app.ksu008.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form9CoverDto {
    /**
     * 集計年
     **/
    private String cellYear;

    /**
     * 集計月
     **/
    private String cellMonth;

    /**
     * 夜勤時間帯の開始時刻
     **/
    private String cellStartTime;

    /**
     * 夜勤時間帯の終了時刻
     **/
    private String cellEndTime;

    /**
     * 出力情報のタイトル
     **/
    private String cellTitle;

    /**
     * 出力情報の期間
     **/
    private String cellPrintPeriod;
}
