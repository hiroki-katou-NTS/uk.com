package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.LineByLineSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByCtg;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 明細書レイアウト設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_LAYOUT_SET")
public class QpbmtStatementLayoutSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStatementLayoutSetPk statementLayoutSetPk;

    /**
    * 明細書レイアウトパターン
    */
    @Basic(optional = false)
    @Column(name = "LAYOUT_PATTERN")
    public int layoutPattern;

    @JoinColumns({
            @JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = true, updatable = true),
            @JoinColumn(name="CTG_ATR",referencedColumnName = "CTG_ATR", insertable = true, updatable = true)})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<QpbmtLineByLineSet> listLineByLineSet;

    @Override
    protected Object getKey()
    {
        return statementLayoutSetPk;
    }

    public SettingByCtg toDomain() {
        CategoryAtr category = listLineByLineSet.isEmpty() ? null : EnumAdaptor.valueOf(listLineByLineSet.get(0).lineByLineSetPk.categoryAtr, CategoryAtr.class);
        List<LineByLineSetting> listLineByLineSetDomain = new ArrayList<>();
        Map<Integer, List<QpbmtLineByLineSet>> listItemGroupByLine = this.listLineByLineSet.stream().collect(Collectors.groupingBy(QpbmtLineByLineSet::getLine));

        if(!listItemGroupByLine.isEmpty()) {
            for (Map.Entry<Integer, List<QpbmtLineByLineSet>> entry : listItemGroupByLine.entrySet()) {
                int line = entry.getKey();
                List<QpbmtLineByLineSet> itemsInLine = entry.getValue();
                int printSet = itemsInLine.get(0).printSet;
                List<SettingByItem> listSetByItem = itemsInLine.stream().map(x -> QpbmtLineByLineSet.toDomain(x)).collect(Collectors.toList());

                listLineByLineSetDomain.add(new LineByLineSetting(printSet, line, listSetByItem));
            }
        }

        return new SettingByCtg(category, listLineByLineSetDomain);
    }
}
