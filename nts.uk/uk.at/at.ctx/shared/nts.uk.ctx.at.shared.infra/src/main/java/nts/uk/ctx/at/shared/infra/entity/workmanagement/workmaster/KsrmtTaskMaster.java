package nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "KSRMT_TASK_MASTER")
public class KsrmtTaskMaster extends ContractCompanyUkJpaEntity implements Serializable {

    @EmbeddedId
    public KsrmtTaskMasterPk pk;

    /**
     * 作業名称 : 作業.表示情報	->名称
     */
    @Column(name = "NAME")
    public String NAME;

    /**
     * 作業略名	: 	作業.表示情報	->	略名
     */
    @Column(name = "ABNAME")
    public String ABNAME;

    /**
     * 有効開始日	 :作業.有効期限	->開始日
     */
    @Column(name = "EXP_START_DATE")
    public GeneralDate EXPSTARTDATE;

    /**
     * 有効終了日 : 作業.有効期限	->終了日
     */
    @Column(name = "EXP_END_DATE")
    public GeneralDate EXPENDDATE;

    /**
     * 外部コード1	: 	作業.外部連携情報	->	外部コード1
     */
    @Column(name = "EXT_CD1")
    public String EXTCD1;

    /**
     * 外部コード2	: 	作業.外部連携情報	->	外部コード2
     */
    @Column(name = "EXT_CD2")
    public String EXTCD2;
    /**
     * 外部コード3	: 	作業.外部連携情報	->	外部コード3
     */
    @Column(name = "EXT_CD3")
    public String EXTCD3;
    /**
     * 外部コード4	: 	作業.外部連携情報	->	外部コード4
     */
    @Column(name = "EXT_CD4")
    public String EXTCD4;
    /**
     * 外部コード5	: 	作業.外部連携情報	->	外部コード5
     */
    @Column(name = "EXT_CD5")
    public String EXTCD5;
    /**
     * カラーコード	: 	作業.表示情報	->	作業色
     */
    @Column(name = "COLOR")
    public String COLOR;
    /**
     * 備考: 	作業.表示情報	->	備考
     */
    @Column(name = "NOTE")
    public String NOTE;


    @Override
    protected Object getKey() {
        return null;
    }
}
