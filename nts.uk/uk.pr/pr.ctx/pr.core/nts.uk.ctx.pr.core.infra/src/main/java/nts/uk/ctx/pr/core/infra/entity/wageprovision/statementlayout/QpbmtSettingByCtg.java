package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByCtg;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書レイアウト設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SETTING_BY_CTG")
public class QpbmtSettingByCtg extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSettingByCtgPk settingByCtgPk;

    @JoinColumns({
            @JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID"),
            @JoinColumn(name="CTG_ATR",referencedColumnName = "CTG_ATR")})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<QpbmtLineByLineSet> listLineByLineSet;

    @Override
    protected Object getKey()
    {
        return settingByCtgPk;
    }

    public SettingByCtg toDomain() {
        return new SettingByCtg(this.settingByCtgPk.categoryAtr, listLineByLineSet.stream().map(entity -> entity.toDomain()).collect(Collectors.toList()));
    }

    public static QpbmtSettingByCtg toEntity(String histId, SettingByCtg settingByCtg) {
        QpbmtSettingByCtgPk pk = new QpbmtSettingByCtgPk(histId, settingByCtg.getCtgAtr().value);

        return new QpbmtSettingByCtg(pk, settingByCtg.getListLineByLineSet().stream().map(domain ->
                QpbmtLineByLineSet.toEntity(histId, settingByCtg.getCtgAtr().value, domain)).collect(Collectors.toList()));
    }
}
