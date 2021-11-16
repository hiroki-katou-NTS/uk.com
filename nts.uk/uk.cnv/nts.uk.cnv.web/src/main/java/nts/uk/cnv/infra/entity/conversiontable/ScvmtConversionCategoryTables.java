package nts.uk.cnv.infra.entity.conversiontable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.core.dom.TableIdentity;
import nts.uk.cnv.core.dom.conversiontable.ConversionCategoryTable;

/**
 * コンバートカテゴリテーブル
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CONVERSION_CATEGORY_TABLES")
public class ScvmtConversionCategoryTables extends JpaEntity implements Serializable  {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public ScvmtConversionCategoryTablesPk pk;

	@Column(name = "SEQ_NO")
	private int sequenceNo;

	@Override
	protected Object getKey() {
		return pk;
	}

	public ConversionCategoryTable toDomain() {
		return new ConversionCategoryTable(
				this.pk.categoryName,
				new TableIdentity(
						this.pk.targetTableName,
						this.pk.targetTableName),
				sequenceNo);
	}

	public static ScvmtConversionCategoryTables fromDomain(ConversionCategoryTable domain) {
		return new ScvmtConversionCategoryTables(
				new ScvmtConversionCategoryTablesPk(domain.getCategoryName(), domain.getTable().getName()),
				domain.getSequenceNo()
			);
	}

}
