package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNDT_RPT_ITEM")
public class JhndtReportItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhndtReportItemPK pk;
	
	@Column(name = "WORK_ID")
	public int workId; // 業務ID
	
	@Column(name = "RPT_LAYOUT_ID")
	public int reportLayoutID; // 個別届出種類ID
	
	@Column(name = "RPT_NAME")
	public String reportName; // 項目区分
	
	@Column(name = "LAYOUT_ITEM_TYPE")
	public int layoutItemType; // 項目区分
	
	@Column(name = "CATEGORY_ID")
	public String categoryId; // 反映ID
	
	@Column(name = "CATEGORY_NAME")
	public String ctgName; // カテゴリ名
	
	@Column(name = "FIXED_ATR")
	public boolean fixedAtr; // 既定区分
	
	@Column(name = "ITEM_ID")
	public String itemId; // 反映ID
	
	@Column(name = "ITEM_NAME")
	public String itemName; // 項目名
	
	@Column(name = "DSP_ORDER")
	public int dspOrder; // 表示順
	
	@Column(name = "SAVE_DATA_ATR")
	public int saveDataAtr; // 保存データ型
	
	@Column(name = "STRING_VAL")
	public String stringVal; // 文字列
	
	@Column(name = "INT_VAL")
	public BigDecimal intVal; // 数値
	
	@Column(name = "DATE_VAL")
	public GeneralDate dateVal;// 日付
	
	@Column(name = "LAYOUT_DISORDER")
	public int layoutDisOrder;  // レイアウト項目区分
	
	@Column(name = "CONTRACT_CD")
	public String contractCode; // 帳票差し込みの項目名称
	
	@Column(name = "REFLECT_ID")
	public int reflectID; // 反映ID
	
	@Override
	public Object getKey() {
		return pk;
	}

	public ReportItem toDomain() {
		return ReportItem.createFromJavaType(
						this.pk.cid ,
						this.workId ,
						this.pk.reportID ,
						this.reportLayoutID ,
						this.reportName ,
						this.layoutItemType ,
						this.categoryId ,
						this.pk.ctgCode ,
						this.ctgName ,
						this.fixedAtr ,
						this.itemId,
						this.pk.itemCd ,
						this.itemName ,
						this.dspOrder ,
						this.saveDataAtr ,
						this.stringVal ,
						this.intVal ,
						this.dateVal ,
						this.layoutDisOrder,
						this.contractCode,
						this.reflectID
						);
	}
}
