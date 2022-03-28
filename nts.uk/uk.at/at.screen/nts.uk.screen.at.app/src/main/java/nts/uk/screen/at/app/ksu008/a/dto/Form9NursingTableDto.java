package nts.uk.screen.at.app.ksu008.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form9NursingTableDto {

    /**
     * 氏名
     **/
    private String fullName;

    /**
     * 1日目開始列
     **/
    private String day1StartColumn;

    /**
     * 明細設定
     **/
    private DetailSettingOfForm9Dto detailSetting;

    /**
     * 種別
     **/
    private String license;

    /**
     * 病棟名
     **/
    private String hospitalWardName;

    /**
     * 常勤
     **/
    private String fullTime;

    /**
     * 短時間
     **/
    private String shortTime;

    /**
     * 非常勤
     **/
    private String partTime;

    /**
     * 他部署兼務
     **/
    private String concurrentPost;

    /**
     * 夜勤専従
     **/
    private String nightShiftOnly;
}
