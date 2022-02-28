package nts.uk.file.at.app.export.form9.dto;

import lombok.Value;

@Value
public class DeliveryTimeDeductionDateDto {
    // 利用区分
        private boolean isUse;

        // 表示対象
        private int displayTarget;

        // 色
        private String color;
}
