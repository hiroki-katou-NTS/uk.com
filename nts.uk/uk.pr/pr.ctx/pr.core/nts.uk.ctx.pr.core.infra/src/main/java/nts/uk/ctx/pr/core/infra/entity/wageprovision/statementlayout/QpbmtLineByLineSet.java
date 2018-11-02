package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemName;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

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

    /**
     * 終了日
     */
    @Basic(optional = false)
    @Column(name = "ITEM_POSITION")
    public int itemPosition;

    // ????????????????????????????????????????????????????????????????
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="ITEM_ID", referencedColumnName="ITEM_NAME_CD")
    })
    public QpbmtStatementItemName statementItemName;

    @Override
    protected Object getKey() {
        return lineByLineSetPk;
    }

    public Integer getLine() {
        return lineByLineSetPk.lineNumber;
    }

    public static SettingByItem toDomain(QpbmtLineByLineSet entity) {
        return new SettingByItem(entity.itemPosition, entity.lineByLineSetPk.itemID);
    }
}
