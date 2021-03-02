package nts.uk.ctx.at.shared.infra.entity.workmanagement.workframe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "KSRMT_TASK_FRAME")
public class KsrmtTaskFrame extends ContractUkJpaEntity implements Serializable {

    public static final long serialVersionUID = 1L;
    /**
     * 職場ID:職場別作業の絞込->職場ID
     */
    @Id
    @Column(name = "CID")
    public String CID;

    /**
     * 	作業枠1利用区分: 作業枠利用設定.枠設定	->	利用区分
     */
    @Column(name = "FRAME1_USE_ATR")
    public int FRAME1USEATR;

    /**
     * 	作業枠1名称 :	作業枠利用設定.枠設定->	作業枠名
     */
    @Column(name = "FRAME1_NAME")
    public String 	FRAME1NAME;

    /**
     * 	作業枠1利用区分: 作業枠利用設定.枠設定	->	利用区分
     */
    @Column(name = "FRAME2_USE_ATR")
    public int FRAME2USEATR;

    /**
     * 	作業枠1名称 :	作業枠利用設定.枠設定->	作業枠名
     */
    @Column(name = "FRAME2_NAME")
    public String 	FRAME2NAME;
    /**
     * 	作業枠1利用区分: 作業枠利用設定.枠設定	->	利用区分
     */
    @Column(name = "FRAME3_USE_ATR")
    public int FRAME3USEATR;

    /**
     * 	作業枠1名称 :	作業枠利用設定.枠設定->	作業枠名
     */
    @Column(name = "FRAME3_NAME")
    public String 	FRAME3NAME;
    /**
     * 	作業枠1利用区分: 作業枠利用設定.枠設定	->	利用区分
     */
    @Column(name = "FRAME4_USE_ATR")
    public int FRAME4USEATR;

    /**
     * 	作業枠1名称 :	作業枠利用設定.枠設定->	作業枠名
     */
    @Column(name = "FRAME4_NAME")
    public String 	FRAME4NAME;
    /**
     * 	作業枠1利用区分: 作業枠利用設定.枠設定	->	利用区分
     */
    @Column(name = "FRAME5_USE_ATR")
    public int FRAME5USEATR;

    /**
     * 	作業枠1名称 :	作業枠利用設定.枠設定->	作業枠名
     */
    @Column(name = "FRAME5_NAME")
    public String 	FRAME5NAME;


    @Override
    protected Object getKey() {
        return CID;
    }
}
