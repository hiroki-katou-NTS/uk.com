package nts.uk.file.at.app.export.form9.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 様式９の色設定
 */
public class Form9ColorSettingsDTO {
    /** 申し送り時間控除日 */
    private DeliveryTimeDeductionDateDto deliveryTimeDeductionDate;

    /** 勤務時間 */
    private Form9WorkingHoursDto workingHours;
}

