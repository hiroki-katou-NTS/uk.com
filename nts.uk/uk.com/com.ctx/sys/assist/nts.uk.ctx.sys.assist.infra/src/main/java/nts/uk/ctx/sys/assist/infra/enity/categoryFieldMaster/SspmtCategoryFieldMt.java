/*package nts.uk.ctx.sys.assist.infra.enity.categoryFieldMaster;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.categoryFieldMaster.CategoryFieldMt;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

*//**
* カテゴリ項目マスタ
*//*
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_CATEGORY_FIELD_MT")
public class SspmtCategoryFieldMt extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    *//**
    * ID
    *//*
    @EmbeddedId
    public SspmtCategoryFieldMtPk categoryFieldMtPk;
    
    
    *//**
    * テーブル日本語名
    *//*
    @Basic(optional = true)
    @Column(name = "TABLE_JAPAN_NAME")
    public String tableJapanName;
    
    *//**
    * テーブル物理名
    *//*
    @Basic(optional = true)
    @Column(name = "TABLE_ENGLISH_NAME")
    public String tableEnglishName;
    
    *//**
    * カテゴリID
    *//*
    @Basic(optional = true)
    @Column(name = "CATEGORY_ID")
    public int categoryId;
    
    *//**
    * 補正区分
    *//*
    @Basic(optional = true)
    @Column(name = "CORRECT_CLASSCIFICATION")
    public int correctClasscification;
    
    *//**
    * テーブルNo
    *//*
    @Basic(optional = true)
    @Column(name = "TABLE_NO")
    public int tableNo;
    
    *//**
    * 置き換え列
    *//*
    @Basic(optional = true)
    @Column(name = "REPLACE_COLUMN")
    public String replaceColumn;
    
    *//**
    * 削除禁止期間
    *//*
    @Basic(optional = false)
    @Column(name = "TIME_STOP_DELETE")
    public int timeStopDelete;
    
    *//**
    * 抽出キー区分1
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_1")
    public String clsKeyQuery1;
    
    *//**
    * 抽出キー区分2
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_2")
    public String clsKeyQuery2;
    
    *//**
    * 抽出キー区分3
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_3")
    public String clsKeyQuery3;
    
    *//**
    * 抽出キー区分4
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_4")
    public String clsKeyQuery4;
    
    *//**
    * 抽出キー区分5
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_5")
    public String clsKeyQuery5;
    
    *//**
    * 抽出キー区分6
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_6")
    public String clsKeyQuery6;
    
    *//**
    * 抽出キー区分7
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_7")
    public String clsKeyQuery7;
    
    *//**
    * 抽出キー区分8
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_8")
    public String clsKeyQuery8;
    
    *//**
    * 抽出キー区分9
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_9")
    public String clsKeyQuery9;
    
    *//**
    * 抽出キー区分10
    *//*
    @Basic(optional = false)
    @Column(name = "CLS_KEY_QUERY_10")
    public String clsKeyQuery10;
    
    *//**
    * 抽出キー条件固定
    *//*
    @Basic(optional = false)
    @Column(name = "DEFAULT_COND_KEY_QUERY")
    public String defaultCondKeyQuery;
    
    *//**
    * 抽出キー項目1
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_1")
    public String fieldKeyQuery1;
    
    *//**
    * 抽出キー項目2
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_2")
    public String fieldKeyQuery2;
    
    *//**
    * 抽出キー項目3
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_3")
    public String fieldKeyQuery3;
    
    *//**
    * 抽出キー項目4
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_4")
    public String fieldKeyQuery4;
    
    *//**
    * 抽出キー項目5
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_5")
    public String fieldKeyQuery5;
    
    *//**
    * 抽出キー項目6
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_6")
    public String fieldKeyQuery6;
    
    *//**
    * 抽出キー項目7
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_7")
    public String fieldKeyQuery7;
    
    *//**
    * 抽出キー項目8
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_8")
    public String fieldKeyQuery8;
    
    *//**
    * 抽出キー項目9
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_9")
    public String fieldKeyQuery9;
    
    *//**
    * 抽出キー項目10
    *//*
    @Basic(optional = false)
    @Column(name = "FIELD_KEY_QUERY_10")
    public String fieldKeyQuery10;
    
    *//**
    * 日付項目1
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_1")
    public String fieldDate1;
    
    *//**
    * 日付項目2
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_2")
    public String fieldDate2;
    
    *//**
    * 日付項目3
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_3")
    public String fieldDate3;
    
    *//**
    * 日付項目4
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_4")
    public String fieldDate4;
    
    *//**
    * 日付項目5
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_5")
    public String fieldDate5;
    
    *//**
    * 日付項目6
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_6")
    public String fieldDate6;
    
    *//**
    * 日付項目7
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_7")
    public String fieldDate7;
    
    *//**
    * 日付項目8
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_8")
    public String fieldDate8;
    
    *//**
    * 日付項目9
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_9")
    public String fieldDate9;
    
    *//**
    * 日付項目10
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_10")
    public String fieldDate10;
    
    *//**
    * 日付項目11
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_11")
    public String fieldDate11;
    
    *//**
    * 日付項目12
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_12")
    public String fieldDate12;
    
    *//**
    * 日付項目13
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_13")
    public String fieldDate13;
    
    *//**
    * 日付項目14
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_14")
    public String fieldDate14;
    
    *//**
    * 日付項目15
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_15")
    public String fieldDate15;
    
    *//**
    * 日付項目16
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_16")
    public String fieldDate16;
    
    *//**
    * 日付項目17
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_17")
    public String fieldDate17;
    
    *//**
    * 日付項目18
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_18")
    public String fieldDate18;
    
    *//**
    * 日付項目19
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_19")
    public String fieldDate19;
    
    *//**
    * 日付項目20
    *//*
    @Basic(optional = false)
    @Column(name = " FIELD_DATE_20")
    public String fieldDate20;
    
    *//**
    * 更新キー項目1
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_1")
    public String filedKeyUpdate1;
    
    *//**
    * 更新キー項目2
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_2")
    public String filedKeyUpdate2;
    
    *//**
    * 更新キー項目3
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_3")
    public String filedKeyUpdate3;
    
    *//**
    * 更新キー項目4
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_4")
    public String filedKeyUpdate4;
    
    *//**
    * 更新キー項目5
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_5")
    public String filedKeyUpdate5;
    
    *//**
    * 更新キー項目6
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_6")
    public String filedKeyUpdate6;
    
    *//**
    * 更新キー項目7
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_7")
    public String filedKeyUpdate7;
    
    *//**
    * 更新キー項目8
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_8")
    public String filedKeyUpdate8;
    
    *//**
    * 更新キー項目9
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_9")
    public String filedKeyUpdate9;
    
    *//**
    * 更新キー項目10
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_10")
    public String filedKeyUpdate10;
    
    *//**
    * 更新キー項目11
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_11")
    public String filedKeyUpdate11;
    
    *//**
    * 更新キー項目12
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_12")
    public String filedKeyUpdate12;
    
    *//**
    * 更新キー項目13
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_13")
    public String filedKeyUpdate13;
    
    *//**
    * 更新キー項目14
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_14")
    public String filedKeyUpdate14;
    
    *//**
    * 更新キー項目15
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_15")
    public String filedKeyUpdate15;
    
    *//**
    * 更新キー項目16
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_16")
    public String filedKeyUpdate16;
    
    *//**
    * 更新キー項目17
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_17")
    public String filedKeyUpdate17;
    
    *//**
    * 更新キー項目18
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_18")
    public String filedKeyUpdate18;
    
    *//**
    * 更新キー項目19
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_19")
    public String filedKeyUpdate19;
    
    *//**
    * 更新キー項目20
    *//*
    @Basic(optional = false)
    @Column(name = " FILED_KEY_UPDATE_20")
    public String filedKeyUpdate20;
    
    *//**
    * 履歴区分
    *//*
    @Basic(optional = true)
    @Column(name = " HISTORY_DIVISION")
    public int historyDivision;
    
    @Override
    protected Object getKey()
    {
        return categoryFieldMtPk;
    }

    

}
*/