package nts.uk.ctx.at.function.infra.entity.alarm.workplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "KFNMT_WKP_ALSTCHK_CONCAT")
public class KfnmtWkpAlstchkConcat extends UkJpaEntity implements Serializable {

    @Column(name="CONTRACT_CD")
    public String contractCd;

    // 職場のエラーアラームチェックID
    @Id
    @Column(name="WP_ERROR_ALARM_CHKID")
    public String id;

    // 会社ID
    @Column(name="CID")
    public String cid;

    // カテゴリ
    @Column(name="CATEGORY")
    public int category;

    // コード
    @Column(name="ALARMCHK_CONDITN_CD")
    public String code;

    // アラームリストの結果が閲覧できるロール一覧
    @Column(name="ROLL_ID")
    public String rollID;

    // 名称
    @Column(name="ALARMCHK_CONDTN_NAME")
    public String name;

    @Override
    protected Object getKey() {
        return this.id;
    }

    public AlarmCheckCdtWorkplaceCategory toDomain() {
        return new AlarmCheckCdtWorkplaceCategory(

        );
    }

    public static KfnmtWkpAlstchkConcat toEntity(AlarmCheckCdtWorkplaceCategory domain) {
        return new KfnmtWkpAlstchkConcat();
    }
}
