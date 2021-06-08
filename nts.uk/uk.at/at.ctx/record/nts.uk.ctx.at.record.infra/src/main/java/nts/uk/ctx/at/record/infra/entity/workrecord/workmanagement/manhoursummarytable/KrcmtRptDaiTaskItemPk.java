package nts.uk.ctx.at.record.infra.entity.workrecord.workmanagement.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class KrcmtRptDaiTaskItemPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Column(name = "CID")
    public String cid;

    /**
     * コード
     */
    @Column(name = "CODE")
    public String code;

    /**
     * 集計項目種類
     0:所属職場
     1:勤務職場
     2:社員
     3:作業1
     4:作業2
     5:作業3
     6:作業4
     7:作業5"
     */
    @Column(name = "SUMMARY_TYPE")
    public int summaryType;
}
