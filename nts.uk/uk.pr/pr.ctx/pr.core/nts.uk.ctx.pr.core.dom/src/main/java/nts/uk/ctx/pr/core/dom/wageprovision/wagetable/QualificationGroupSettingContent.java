package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 資格グループ設定内容
*/
@AllArgsConstructor
@Getter
public class QualificationGroupSettingContent extends DomainObject
{
    
    /**
    * 資格グループコード
    */
    private QualificationGroupCode qualificationGroupCode;
    
    /**
    * 支払方法
    */
    private QualificationPaymentMethod paymentMethod;
    
    /**
    * 対象資格コード
    */
    private List<QualificationCode> eligibleQualificationCode;
    
    public QualificationGroupSettingContent(String qualificationGroupCode, Integer paymentMethod, List<String> eligibleQualificationCode) {
        this.qualificationGroupCode = new QualificationGroupCode(qualificationGroupCode);
        this.paymentMethod = EnumAdaptor.valueOf(paymentMethod, QualificationPaymentMethod.class);
        this.eligibleQualificationCode = eligibleQualificationCode.stream().map(item -> new QualificationCode(item)).collect(Collectors.toList());
    }
    
}
