package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingContent;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationPaymentMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
* 資格グループ設定内容
*/
@Data
@NoArgsConstructor
public class QualificationGroupSettingContentDto {

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

    public static QualificationGroupSettingContentDto fromDomainToDto(QualificationGroupSettingContent domain) {
        QualificationGroupSettingContentDto dto = new QualificationGroupSettingContentDto();
        dto.qualificationGroupCode = domain.getQualificationGroupCode().v();
        dto.paymentMethod = domain.getPaymentMethod().value;
        dto.eligibleQualificationCode = domain.getEligibleQualificationCode().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
        return dto;
    }
    
}
