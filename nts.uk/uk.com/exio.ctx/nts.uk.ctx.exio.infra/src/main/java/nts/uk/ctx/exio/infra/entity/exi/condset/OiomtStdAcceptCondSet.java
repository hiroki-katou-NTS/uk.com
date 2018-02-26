package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 受入条件設定（定型）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_ACCEPT_COND_SET")
public class OiomtStdAcceptCondSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "EXCLUS_VER")
    public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtStdAcceptCondSetPk stdAcceptCondSetPk;
    
    /**
    * 外部受入カテゴリID
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    public String categoryId;
    
    /**
    * CSVデータの項目名行
    */
    @Basic(optional = false)
    @Column(name = "CSV_DATA_LINE_NUMBER")
    public int csvDataLineNumber;
    
    /**
    * システム種類
    */
    @Basic(optional = false)
    @Column(name = "SYSTEM_TYPE")
    public int systemType;
    
    /**
    * 既存データの削除
    */
    @Basic(optional = false)
    @Column(name = "DELETE_EXIST_DATA")
    public int deleteExistData;
    
    /**
    * CSVデータの取込開始行
    */
    @Basic(optional = false)
    @Column(name = "CSV_DATA_START_LINE")
    public int csvDataStartLine;
    
    /**
    * 受入モード
    */
    @Basic(optional = false)
    @Column(name = "ACCEPT_MODE")
    public int acceptMode;
    
    /**
    * 外部受入条件名称
    */
    @Basic(optional = false)
    @Column(name = "CONDITION_SET_NAME")
    public String conditionSetName;
    
    /**
    * チェック完了
    */
    @Basic(optional = false)
    @Column(name = "CHECK_COMPLETED")
    public int checkCompleted;
    
    /**
    * 既存データの削除方法
    */
    @Basic(optional = false)
    @Column(name = "DELETE_EXT_DATA_METHOD")
    public int deleteExtDataMethod;
    
    @Override
    protected Object getKey()
    {
        return stdAcceptCondSetPk;
    }
}
