package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.*;
import nts.uk.shr.com.context.AppContexts;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
* 資格グループ設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_QUALIFI_GROUP_SET")
public class QpbmtQualificationGroupSetting {

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtQualificationGroupSettingPk qualificationGroupSettingPk;

    /**
    * 支払方法
    */
    @Basic(optional = false)
    @Column(name = "PAYMENT_METHOD")
    public int paymentMethod;

    /**
    * 資格グループ名
    */
    @Basic(optional = false)
    @Column(name = "QUALIFICATION_GROUP_NAME")
    public String qualificationGroupName;

    public static List<QpbmtQualificationGroupSetting> toEntity(QualificationGroupSetting domain) {
        if (domain.getEligibleQualificationCode().isEmpty()) return Arrays.asList(new QpbmtQualificationGroupSetting(new QpbmtQualificationGroupSettingPk(AppContexts.user().companyId(), domain.getQualificationGroupCode().v(), ""), domain.getPaymentMethod().value, domain.getQualificationGroupName().v()));
        return domain.getEligibleQualificationCode().stream().map(item -> new QpbmtQualificationGroupSetting(new QpbmtQualificationGroupSettingPk(AppContexts.user().companyId(), domain.getQualificationGroupCode().v(), item.v()), domain.getPaymentMethod().value, domain.getQualificationGroupName().v())).collect(Collectors.toList());
    }

    public static List<QualificationGroupSetting> toDomain(List<QpbmtQualificationGroupSetting> entities) {
        return entities.stream().map(item -> new QualificationGroupSetting(item.qualificationGroupSettingPk.cid, item.qualificationGroupSettingPk.qualificationGroupCode, item.paymentMethod, Collections.emptyList(), item.qualificationGroupName)).collect(Collectors.toList());
    }

    public static Optional<QualificationGroupSetting> toDomainForGroup(List<QpbmtQualificationGroupSetting> entities) {
        if (entities.isEmpty()) return Optional.empty();
        List<String> eligibleQualificationCode = new ArrayList<>();
        if (!entities.get(0).qualificationGroupSettingPk.eligibleQualificationCode.isEmpty())
            eligibleQualificationCode = entities.stream().map(item -> item.qualificationGroupSettingPk.eligibleQualificationCode).collect(Collectors.toList());
        return Optional.of(new QualificationGroupSetting(entities.get(0).qualificationGroupSettingPk.cid, entities.get(0).qualificationGroupSettingPk.qualificationGroupCode, entities.get(0).paymentMethod, eligibleQualificationCode, entities.get(0).qualificationGroupName));
    }
}
