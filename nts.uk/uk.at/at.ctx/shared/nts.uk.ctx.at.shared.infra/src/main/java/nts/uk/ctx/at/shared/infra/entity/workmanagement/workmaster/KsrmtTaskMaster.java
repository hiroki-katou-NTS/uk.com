package nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
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


    public static KsrmtTaskMaster toEntity(Work domain) {
        return new KsrmtTaskMaster(
                new KsrmtTaskMasterPk(
                        AppContexts.user().companyId(),
                        domain.getTaskFrameNo().v(),
                        domain.getCode().v()
                ),
                domain.getDisplayInfo().getTaskName().v(),
                domain.getDisplayInfo().getTaskAbName().v(),
                domain.getExpirationDate().start(),
                domain.getExpirationDate().end(),
                domain.getCooperationInfo().getExternalCode1().isPresent() ? domain.getCooperationInfo().getExternalCode1().get().v() : null,
                domain.getCooperationInfo().getExternalCode2().isPresent() ? domain.getCooperationInfo().getExternalCode2().get().v() : null,
                domain.getCooperationInfo().getExternalCode3().isPresent() ? domain.getCooperationInfo().getExternalCode3().get().v() : null,
                domain.getCooperationInfo().getExternalCode4().isPresent() ? domain.getCooperationInfo().getExternalCode4().get().v() : null,
                domain.getCooperationInfo().getExternalCode5().isPresent() ? domain.getCooperationInfo().getExternalCode5().get().v() : null,
                domain.getDisplayInfo().getColor().isPresent() ? domain.getDisplayInfo().getColor().get().v() : null,
                domain.getDisplayInfo().getTaskNote().isPresent() ? domain.getDisplayInfo().getTaskNote().get().v() : null
        );
    }

    @Override
    protected Object getKey() {
        return pk;
    }
}
