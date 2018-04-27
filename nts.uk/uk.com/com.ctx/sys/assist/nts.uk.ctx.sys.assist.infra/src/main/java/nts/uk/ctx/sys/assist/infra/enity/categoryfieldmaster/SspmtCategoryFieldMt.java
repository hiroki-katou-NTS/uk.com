package nts.uk.ctx.sys.assist.infra.enity.categoryfieldmaster;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.assist.dom.categoryFieldMaster.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryFieldMaster.HistoryDiviSion;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* カテゴリ項目マスタ
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_CATEGORY_FIELD_MT")
public class SspmtCategoryFieldMt extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public SspmtCategoryFieldMtPk categoryFieldMtPk;
    
    /**
    * テーブル日本語名
    */
    @Basic(optional = false)
    @Column(name = "TABLE_JAPAN_NAME")
    public String tableJapanName;
    
    /**
    * テーブル物理名
    */
    @Basic(optional = false)
    @Column(name = "TABLE_ENGLISH_NAME")
    public String tableEnglishName;
    
    /**
    * 削除禁止期間
    */
    @Basic(optional = true)
    @Column(name = "TIME_STOP_DELETE")
    public String timeStopDelete;
    
    /**
    * 抽出キー区分1
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_1")
    public String clsKeyQuery1;
    
    /**
    * 抽出キー区分2
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_2")
    public String clsKeyQuery2;
    
    /**
    * 抽出キー区分3
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_3")
    public String clsKeyQuery3;
    
    /**
    * 抽出キー区分4
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_4")
    public String clsKeyQuery4;
    
    /**
    * 抽出キー区分5
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_5")
    public String clsKeyQuery5;
    
    /**
    * 抽出キー区分6
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_6")
    public String clsKeyQuery6;
    
    /**
    * 抽出キー区分7
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_7")
    public String clsKeyQuery7;
    
    /**
    * 抽出キー区分8
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_8")
    public String clsKeyQuery8;
    
    /**
    * 抽出キー区分9
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_9")
    public String clsKeyQuery9;
    
    /**
    * 抽出キー区分10
    */
    @Basic(optional = true)
    @Column(name = "CLS_KEY_QUERY_10")
    public String clsKeyQuery10;
    
    /**
    * 抽出キー条件固定
    */
    @Basic(optional = true)
    @Column(name = "DEFAULT_COND_KEY_QUERY")
    public String defaultCondKeyQuery;
    
    /**
    * 抽出キー項目1
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_1")
    public String fieldKeyQuery1;
    
    /**
    * 抽出キー項目2
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_2")
    public String fieldKeyQuery2;
    
    /**
    * 抽出キー項目3
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_3")
    public String fieldKeyQuery3;
    
    /**
    * 抽出キー項目4
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_4")
    public String fieldKeyQuery4;
    
    /**
    * 抽出キー項目5
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_5")
    public String fieldKeyQuery5;
    
    /**
    * 抽出キー項目6
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_6")
    public String fieldKeyQuery6;
    
    /**
    * 抽出キー項目7
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_7")
    public String fieldKeyQuery7;
    
    /**
    * 抽出キー項目8
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_8")
    public String fieldKeyQuery8;
    
    /**
    * 抽出キー項目9
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_9")
    public String fieldKeyQuery9;
    
    /**
    * 抽出キー項目10
    */
    @Basic(optional = true)
    @Column(name = "FIELD_KEY_QUERY_10")
    public String fieldKeyQuery10;
    
    /**
    * 日付項目1
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_1")
    public String fieldDate1;
    
    /**
    * 日付項目2
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_2")
    public String fieldDate2;
    
    /**
    * 日付項目3
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_3")
    public String fieldDate3;
    
    /**
    * 日付項目4
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_4")
    public String fieldDate4;
    
    /**
    * 日付項目5
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_5")
    public String fieldDate5;
    
    /**
    * 日付項目6
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_6")
    public String fieldDate6;
    
    /**
    * 日付項目7
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_7")
    public String fieldDate7;
    
    /**
    * 日付項目8
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_8")
    public String fieldDate8;
    
    /**
    * 日付項目9
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_9")
    public String fieldDate9;
    
    /**
    * 日付項目10
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_10")
    public String fieldDate10;
    
    /**
    * 日付項目11
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_11")
    public String fieldDate11;
    
    /**
    * 日付項目12
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_12")
    public String fieldDate12;
    
    /**
    * 日付項目13
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_13")
    public String fieldDate13;
    
    /**
    * 日付項目14
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_14")
    public String fieldDate14;
    
    /**
    * 日付項目15
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_15")
    public String fieldDate15;
    
    /**
    * 日付項目16
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_16")
    public String fieldDate16;
    
    /**
    * 日付項目17
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_17")
    public String fieldDate17;
    
    /**
    * 日付項目18
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_18")
    public String fieldDate18;
    
    /**
    * 日付項目19
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_19")
    public String fieldDate19;
    
    /**
    * 日付項目20
    */
    @Basic(optional = true)
    @Column(name = " FIELD_DATE_20")
    public String fieldDate20;
    
    /**
    * 更新キー項目1
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_1")
    public String filedKeyUpdate1;
    
    /**
    * 更新キー項目2
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_2")
    public String filedKeyUpdate2;
    
    /**
    * 更新キー項目3
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_3")
    public String filedKeyUpdate3;
    
    /**
    * 更新キー項目4
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_4")
    public String filedKeyUpdate4;
    
    /**
    * 更新キー項目5
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_5")
    public String filedKeyUpdate5;
    
    /**
    * 更新キー項目6
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_6")
    public String filedKeyUpdate6;
    
    /**
    * 更新キー項目7
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_7")
    public String filedKeyUpdate7;
    
    /**
    * 更新キー項目8
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_8")
    public String filedKeyUpdate8;
    
    /**
    * 更新キー項目9
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_9")
    public String filedKeyUpdate9;
    
    /**
    * 更新キー項目10
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_10")
    public String filedKeyUpdate10;
    
    /**
    * 更新キー項目11
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_11")
    public String filedKeyUpdate11;
    
    /**
    * 更新キー項目12
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_12")
    public String filedKeyUpdate12;
    
    /**
    * 更新キー項目13
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_13")
    public String filedKeyUpdate13;
    
    /**
    * 更新キー項目14
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_14")
    public String filedKeyUpdate14;
    
    /**
    * 更新キー項目15
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_15")
    public String filedKeyUpdate15;
    
    /**
    * 更新キー項目16
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_16")
    public String filedKeyUpdate16;
    
    /**
    * 更新キー項目17
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_17")
    public String filedKeyUpdate17;
    
    /**
    * 更新キー項目18
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_18")
    public String filedKeyUpdate18;
    
    /**
    * 更新キー項目19
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_19")
    public String filedKeyUpdate19;
    
    /**
    * 更新キー項目20
    */
    @Basic(optional = true)
    @Column(name = " FILED_KEY_UPDATE_20")
    public String filedKeyUpdate20;
    
    /**
    * 履歴区分
    */
    @Basic(optional = false)
    @Column(name = "HISTORY_CLS")
    public int historyCls;
    
    @Override
    protected Object getKey()
    {
        return categoryFieldMtPk;
    }

    public CategoryFieldMt toDomain() {
        return new CategoryFieldMt(this.categoryFieldMtPk.categoryId, this.categoryFieldMtPk.tableNo, this.tableJapanName, this.tableEnglishName, this.timeStopDelete, this.clsKeyQuery1, this.clsKeyQuery2, this.clsKeyQuery3, this.clsKeyQuery4, this.clsKeyQuery5, this.clsKeyQuery6, this.clsKeyQuery7, this.clsKeyQuery8, this.clsKeyQuery9, this.clsKeyQuery10, this.defaultCondKeyQuery, this.fieldKeyQuery1, this.fieldKeyQuery2, this.fieldKeyQuery3, this.fieldKeyQuery4, this.fieldKeyQuery5, this.fieldKeyQuery6, this.fieldKeyQuery7, this.fieldKeyQuery8, this.fieldKeyQuery9, this.fieldKeyQuery10, this.fieldDate1, this.fieldDate2, this.fieldDate3, this.fieldDate4, this.fieldDate5, this.fieldDate6, this.fieldDate7, this.fieldDate8, this.fieldDate9, this.fieldDate10, this.fieldDate11, this.fieldDate12, this.fieldDate13, this.fieldDate14, this.fieldDate15, this.fieldDate16, this.fieldDate17, this.fieldDate18, this.fieldDate19, this.fieldDate20, this.filedKeyUpdate1, this.filedKeyUpdate2, this.filedKeyUpdate3, this.filedKeyUpdate4, this.filedKeyUpdate5, this.filedKeyUpdate6, this.filedKeyUpdate7, this.filedKeyUpdate8, this.filedKeyUpdate9, this.filedKeyUpdate10, this.filedKeyUpdate11, this.filedKeyUpdate12, this.filedKeyUpdate13, this.filedKeyUpdate14, this.filedKeyUpdate15, this.filedKeyUpdate16, this.filedKeyUpdate17, this.filedKeyUpdate18, this.filedKeyUpdate19, this.filedKeyUpdate20, EnumAdaptor.valueOf(this.historyCls, HistoryDiviSion.class));
    }
    public static SspmtCategoryFieldMt toEntity(CategoryFieldMt domain) {
        return new SspmtCategoryFieldMt(new SspmtCategoryFieldMtPk(domain.getCategoryId(), domain.getTableNo()), domain.getTableJapanName(), domain.getTableEnglishName(), domain.getTimeStopDelete(), domain.getClsKeyQuery1(), domain.getClsKeyQuery2(), domain.getClsKeyQuery3(), domain.getClsKeyQuery4(), domain.getClsKeyQuery5(), domain.getClsKeyQuery6(), domain.getClsKeyQuery7(), domain.getClsKeyQuery8(), domain.getClsKeyQuery9(), domain.getClsKeyQuery10(), domain.getDefaultCondKeyQuery(), domain.getFieldKeyQuery1(), domain.getFieldKeyQuery2(), domain.getFieldKeyQuery3(), domain.getFieldKeyQuery4(), domain.getFieldKeyQuery5(), domain.getFieldKeyQuery6(), domain.getFieldKeyQuery7(), domain.getFieldKeyQuery8(), domain.getFieldKeyQuery9(), domain.getFieldKeyQuery10(), domain.getFieldDate1(), domain.getFieldDate2(), domain.getFieldDate3(), domain.getFieldDate4(), domain.getFieldDate5(), domain.getFieldDate6(), domain.getFieldDate7(), domain.getFieldDate8(), domain.getFieldDate9(), domain.getFieldDate10(), domain.getFieldDate11(), domain.getFieldDate12(), domain.getFieldDate13(), domain.getFieldDate14(), domain.getFieldDate15(), domain.getFieldDate16(), domain.getFieldDate17(), domain.getFieldDate18(), domain.getFieldDate19(), domain.getFieldDate20(), domain.getFiledKeyUpdate1(), domain.getFiledKeyUpdate2(), domain.getFiledKeyUpdate3(), domain.getFiledKeyUpdate4(), domain.getFiledKeyUpdate5(), domain.getFiledKeyUpdate6(), domain.getFiledKeyUpdate7(), domain.getFiledKeyUpdate8(), domain.getFiledKeyUpdate9(), domain.getFiledKeyUpdate10(), domain.getFiledKeyUpdate11(), domain.getFiledKeyUpdate12(), domain.getFiledKeyUpdate13(), domain.getFiledKeyUpdate14(), domain.getFiledKeyUpdate15(), domain.getFiledKeyUpdate16(), domain.getFiledKeyUpdate17(), domain.getFiledKeyUpdate18(), domain.getFiledKeyUpdate19(), domain.getFiledKeyUpdate20(), domain.getHistoryCls().value);
    }

}
