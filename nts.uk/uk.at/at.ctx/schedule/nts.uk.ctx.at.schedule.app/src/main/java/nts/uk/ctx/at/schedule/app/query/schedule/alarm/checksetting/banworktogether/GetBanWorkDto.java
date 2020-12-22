package nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether;

import lombok.Data;

@Data
public class GetBanWorkDto {

    // 対象組織の単位
    private int unit;
    // 職場ID
    private String workplaceId;
    // 職場グループID
    private String workplaceGroupId;
    // 選択項目のコード
    private String code;

}
