package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.*;

import java.util.List;
import java.util.stream.Collectors;

/**
* 資格グループ設定
*/
@NoArgsConstructor
@Data
public class QualificationGroupSettingDto extends AggregateRoot {

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

    public static QualificationGroupSettingDto fromDomainToDto(QualificationGroupSetting domain) {
        QualificationGroupSettingDto dto = new QualificationGroupSettingDto();
        dto.companyID = domain.getCompanyID();
        dto.qualificationGroupCode = domain.getQualificationGroupCode().v();
        dto.qualificationGroupName = domain.getQualificationGroupName().v();
        dto.paymentMethod = domain.getPaymentMethod().value;
        dto.eligibleQualificationCode = domain.getEligibleQualificationCode().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
        return dto;
    }
    
}
