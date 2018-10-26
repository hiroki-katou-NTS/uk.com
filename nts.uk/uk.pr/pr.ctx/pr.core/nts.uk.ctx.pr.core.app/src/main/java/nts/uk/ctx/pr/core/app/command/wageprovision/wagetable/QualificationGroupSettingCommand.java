package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSetting;

import java.util.List;
import java.util.stream.Collectors;

/**
* 資格グループ設定
*/
@NoArgsConstructor
@Data
public class QualificationGroupSettingCommand extends AggregateRoot {

    /**
    * 資格グループコード
    */
    private String qualificationGroupCode;

    /**
    * 支払方法
    */
    private int paymentMethod;

    /**
    * 対象資格コード
    */
    private List<String> eligibleQualificationCode;

    /**
    * 資格グループ名
    */
    private String qualificationGroupName;

    /**
    * 会社ID
    */
    private String companyID;

    public QualificationGroupSetting fromCommandToDomain() {
        return new QualificationGroupSetting(qualificationGroupCode, paymentMethod, eligibleQualificationCode, qualificationGroupName, companyID);
    }
    
}
