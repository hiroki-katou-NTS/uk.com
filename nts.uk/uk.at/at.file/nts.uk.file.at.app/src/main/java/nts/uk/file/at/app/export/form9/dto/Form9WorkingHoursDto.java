package nts.uk.file.at.app.export.form9.dto;

import lombok.Value;

@Value
public class Form9WorkingHoursDto {
    // 利用区分
    private boolean isUse;

    // 表示対象
    private int displayTarget;

    // 予定色
    private String scheduleColor;

    // 実績色
    private String actualColor;
}
