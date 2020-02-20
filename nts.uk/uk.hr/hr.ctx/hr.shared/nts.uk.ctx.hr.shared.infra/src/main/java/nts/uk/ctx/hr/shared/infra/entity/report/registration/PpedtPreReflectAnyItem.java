package nts.uk.ctx.hr.shared.infra.entity.report.registration;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEDT_PRE_REFLECT_ANY_ITEM")
public class PpedtPreReflectAnyItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public PpedtPreReflectAnyItemPk pk;
	
	@Column(name = "CID")
	public String cid; // 会社ID
	
	@Column(name = "RPTID")
	public int reportId; // 届出ID
	
	@Column(name = "DSP_ORDER")
	public int dispOrder; // 表示順
	
	@Column(name = "CATEGORY_ID")
	public String categoryId; // カテゴリID
	
	@Column(name = "CATEGORY_CD")
	public String categoryCode; // カテゴリコード
	
	@Column(name = "ITEM_ID")
	public String itemId; // 項目ID
	
	@Column(name = "ITEM_CD")
	public String itemCode; // 項目コード
	
	@Column(name = "DST_HIST_ID")
	public String dstHistId; // ???
	
	@Column(name = "SAVE_DATA_ATR")
	public int saveDataAtr; // 保存データ型
	
	@Column(name = "STRING_VAL")
	public String stringVal; // 文字列
	
	@Column(name = "INT_VAL")
	public BigDecimal intVal; // 数値
	
	@Column(name = "DATE_VAL")
	public GeneralDate dateVal;// 日付
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
