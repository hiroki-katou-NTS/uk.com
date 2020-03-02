package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.LineByLineSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 行別設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_LINE_BY_LINE_SET")
public class QpbmtLineByLineSet extends UkJpaEntity implements Serializable {

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtLineByLineSetPk lineByLineSetPk;
    /**
     * 開始日
     */
    @Basic(optional = false)
    @Column(name = "PRINT_SET")
    public int printSet;

    @Override
    protected Object getKey() {
        return lineByLineSetPk;
    }

    @JoinColumns({
            @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
            @JoinColumn(name = "STATEMENT_CD", referencedColumnName = "STATEMENT_CD", insertable = true, updatable = true),
            @JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = true, updatable = true),
            @JoinColumn(name="CATEGORY_ATR",referencedColumnName = "CATEGORY_ATR", insertable = true, updatable = true),
            @JoinColumn(name="LINE_NUM",referencedColumnName = "LINE_NUM", insertable = true, updatable = true)})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<QpbmtSettingByItem> settingByItems;

    public Integer getCategory() {
        return lineByLineSetPk.categoryAtr;
    }

    public LineByLineSetting toDomain() {
        return new LineByLineSetting(this.printSet, this.lineByLineSetPk.lineNumber,
                settingByItems.stream().map(entity -> entity.toDomain()).collect(Collectors.toList()));
    }

    public static QpbmtLineByLineSet toEntity(String cid, String statementCd, String histId, int categoryAtr, LineByLineSetting lineByLineSetting) {
        QpbmtLineByLineSetPk lineByLineSetPk = new QpbmtLineByLineSetPk(cid, statementCd, histId, categoryAtr, lineByLineSetting.getLineNumber());

        return new QpbmtLineByLineSet(lineByLineSetPk, lineByLineSetting.getPrintSet().value,
                lineByLineSetting.getListSetByItem().stream().map(domain -> QpbmtSettingByItem.
                        toEntity(cid, statementCd, histId, categoryAtr, lineByLineSetting.getLineNumber(), domain)).collect(Collectors.toList()));
    }
}
