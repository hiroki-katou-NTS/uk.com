package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * エラーアラームのカテゴリ別抽出条件PK
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcmtEralCategoryCondPK implements Serializable {
    /** 会社ID */
    @Column(name = "CID")
    public String cid;

    /** カテゴリ */
    @Column(name = "ERAL_CATEGORY")
    public int category;

    /** エラーアラム条件コード */
    @Column(name = "ERAL_ALARM_CD")
    public String code;

}
