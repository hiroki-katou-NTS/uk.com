package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 社会保険届作成設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_SYAHO_RPT_SETTING")
public class QqsmtSocInsuNotiSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtSocInsuNotiSetPk socInsuNotiSetPk;
    
    /**
    * 事業所情報
    */
    @Basic(optional = false)
    @Column(name = "OUTPUT_UNIT_ATR")
    public int officeInformation;
    
    /**
    * 事業所整理記号
    */
    @Basic(optional = false)
    @Column(name = "OUTPUT_OFFICE_SRNUM")
    public int bussinessArrSymbol;
    
    /**
    * 出力順
    */
    @Basic(optional = false)
    @Column(name = "OUTPUT_ORDER_ATR")
    public int outputOrder;
    
    /**
    * 印刷個人番号
    */
    @Basic(optional = false)
    @Column(name = "PRINT_MYNUM_ATR")
    public int printPersonNumber;
    
    /**
    * 提出氏名
    */
    @Basic(optional = false)
    @Column(name = "SUBMIT_NAME_ATR")
    public int submittedName;
    
    /**
    * 被保険者整理番号
    */
    @Basic(optional = false)
    @Column(name = "SYAHO_NUM_ATR")
    public int insuredNumber;
    
    /**
    * FD番号
    */
    @Basic(optional = true)
    @Column(name = "FD_NUM")
    public String fdNumber;
    
    /**
    * テキスト個人番号
    */
    @Basic(optional = true)
    @Column(name = "TEXT_MYNUM_ATR")
    public Integer textPersonNumber;
    
    /**
    * 出力形式
    */
    @Basic(optional = true)
    @Column(name = "OUTPUT_FORMAT_ATR")
    public Integer outputFormat;
    
    /**
    * 改行コード
    */
    @Basic(optional = true)
    @Column(name = "LINE_FEED_CODE_ATR")
    public Integer lineFeedCode;

    @Override
    protected Object getKey()
    {
        return socInsuNotiSetPk;
    }

    public SocialInsurNotiCreateSet toDomain() {
       return new SocialInsurNotiCreateSet(
               this.socInsuNotiSetPk.userId,
               this.socInsuNotiSetPk.cid,
               this.officeInformation,
               this.bussinessArrSymbol,
               this.outputOrder,
               this.printPersonNumber,
               this.submittedName,
               this.insuredNumber,
               this.fdNumber,
               this.textPersonNumber,
               this.outputFormat,
               this.lineFeedCode);
    }

    public static QqsmtSocInsuNotiSet toEntity(SocialInsurNotiCreateSet domain) {
        return new QqsmtSocInsuNotiSet(new QqsmtSocInsuNotiSetPk(domain.getUserId(), domain.getCid()),
               domain.getOfficeInformation().value,
               domain.getBusinessArrSymbol().value,
               domain.getOutputOrder().value,
               domain.getPrintPersonNumber().value,
               domain.getSubmittedName().value,
               domain.getInsuredNumber().value,
               domain.getFdNumber().isPresent() ? domain.getFdNumber().get().v() : null,
               domain.getTextPersonNumber().isPresent() ? domain.getTextPersonNumber().get().value : null,
               domain.getOutputFormat().isPresent() ? domain.getOutputFormat().get().value : null,
               domain.getLineFeedCode().isPresent() ? domain.getLineFeedCode().get().value : null
                );
    }

}
