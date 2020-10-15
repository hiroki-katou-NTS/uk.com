package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.optionalitemappsetting;

import nts.arc.primitive.PrimitiveValue;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.ApplicationSettingItem;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationSetting;
import nts.uk.ctx.at.shared.dom.scherec.event.OptionalItemNo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "KRQMT_APP_ANYV")
public class KrqmtAppAnyv extends ContractUkJpaEntity {
    @EmbeddedId
    private KrqmtAppAnyvPk pk;

    @Column(name = "ANYV_NAME")
    private String anyItemName;

    @Column(name = "USE_ATR")
    private int useAtr;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "ANYV_NO1")
    private Integer anyItemNo1;

    @Column(name = "ANYV_NO2")
    private Integer anyItemNo2;

    @Column(name = "ANYV_NO3")
    private Integer anyItemNo3;

    @Column(name = "ANYV_NO4")
    private Integer anyItemNo4;

    @Column(name = "ANYV_NO5")
    private Integer anyItemNo5;

    @Column(name = "ANYV_NO6")
    private Integer anyItemNo6;

    @Column(name = "ANYV_NO7")
    private Integer anyItemNo7;

    @Column(name = "ANYV_NO8")
    private Integer anyItemNo8;

    @Column(name = "ANYV_NO9")
    private Integer anyItemNo9;

    @Column(name = "ANYV_NO10")
    private Integer anyItemNo10;

    @Override
    protected Object getKey() {
        return pk;
    }

    public OptionalItemApplicationSetting toDomain() {
        List<ApplicationSettingItem> settingItems = new ArrayList<>();
        int dispOrder = 1;
        if (anyItemNo1 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo1), dispOrder++));
        }
        if (anyItemNo2 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo2), dispOrder++));
        }
        if (anyItemNo3 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo3), dispOrder++));
        }
        if (anyItemNo4 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo4), dispOrder++));
        }
        if (anyItemNo5 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo5), dispOrder++));
        }
        if (anyItemNo6 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo6), dispOrder++));
        }
        if (anyItemNo7 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo7), dispOrder++));
        }
        if (anyItemNo8 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo8), dispOrder++));
        }
        if (anyItemNo9 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo9), dispOrder++));
        }
        if (anyItemNo10 != null) {
            settingItems.add(new ApplicationSettingItem(new OptionalItemNo(anyItemNo10), dispOrder++));
        }

        return OptionalItemApplicationSetting.create(
                pk.getCompanyId(),
                pk.getAnyItemCode(),
                anyItemName,
                useAtr,
                note,
                settingItems
        );
    }

    public static KrqmtAppAnyv fromDomain(OptionalItemApplicationSetting domain) {
        KrqmtAppAnyv entity = new KrqmtAppAnyv();
        entity.pk = new KrqmtAppAnyvPk(domain.getCompanyId(), domain.getCode().v());
        entity.anyItemName = domain.getName().v();
        entity.useAtr = BooleanUtils.toInteger(domain.isUseAtr());
        entity.note = domain.getDescription().map(PrimitiveValue::v).orElse(null);
        domain.getSettingItems().stream().sorted(Comparator.comparing(ApplicationSettingItem::getDisplayOrder)).forEach(item -> {
            if (item.getDisplayOrder() == 1) {
                entity.anyItemNo1 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 2) {
                entity.anyItemNo2 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 3) {
                entity.anyItemNo3 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 4) {
                entity.anyItemNo4 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 5) {
                entity.anyItemNo5 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 6) {
                entity.anyItemNo6 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 7) {
                entity.anyItemNo7 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 8) {
                entity.anyItemNo8 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 9) {
                entity.anyItemNo9 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 10) {
                entity.anyItemNo10 = item.getOptionalItemNo().v();
            }
        });
        return entity;
    }

    public void update(OptionalItemApplicationSetting domain) {
        anyItemName = domain.getName().v();
        useAtr = BooleanUtils.toInteger(domain.isUseAtr());
        note = domain.getDescription().map(PrimitiveValue::v).orElse(null);
        domain.getSettingItems().stream().sorted(Comparator.comparing(ApplicationSettingItem::getDisplayOrder)).forEach(item -> {
            if (item.getDisplayOrder() == 1) {
                anyItemNo1 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 2) {
                anyItemNo2 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 3) {
                anyItemNo3 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 4) {
                anyItemNo4 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 5) {
                anyItemNo5 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 6) {
                anyItemNo6 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 7) {
                anyItemNo7 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 8) {
                anyItemNo8 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 9) {
                anyItemNo9 = item.getOptionalItemNo().v();
            }
            if (item.getDisplayOrder() == 10) {
                anyItemNo10 = item.getOptionalItemNo().v();
            }
        });
    }
}
