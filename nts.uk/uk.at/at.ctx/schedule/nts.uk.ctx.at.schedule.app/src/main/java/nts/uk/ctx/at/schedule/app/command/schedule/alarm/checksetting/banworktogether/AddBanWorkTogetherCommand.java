package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import lombok.Data;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TargetOrgIdenInforDto;

import java.util.List;

@Data
public class AddBanWorkTogetherCommand {

    // 対象組織
    private TargetOrgIdenInforDto targetOrgIdenInfor;

    // コード
    private String code;

    // 名称
    private String name;

    // 適用する時間帯
    private int applicableTimeZoneCls;

    // 許容する人数
    private int upperLimit;

    // 同時出勤を禁止する社員の組み合わせ
    private List<String> targetList;

}
