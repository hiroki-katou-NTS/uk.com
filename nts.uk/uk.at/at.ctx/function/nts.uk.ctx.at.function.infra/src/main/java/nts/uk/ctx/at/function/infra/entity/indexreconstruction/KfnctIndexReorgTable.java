package nts.uk.ctx.at.function.infra.entity.indexreconstruction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgTable;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity インデックス再構成テーブル<br>
 * The Class KfnctIndexReorgTable.
 */
@Data
@Entity
@Table(name = "KFNCT_INDEX_REORG_TABLE")
@EqualsAndHashCode(callSuper = true)
public class KfnctIndexReorgTable extends UkJpaEntity implements IndexReorgTable.MementoGetter, IndexReorgTable.MementoSetter, Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The primary key.
	 */
	@EmbeddedId
	private KfnctIndexReorgTablePk pk;

	/**
	 * Column 排他バージョン
	 */
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;

	/**
	 * The Contract Code.
	 */
	@Column(name = "CONTRACT_CD")
	private String contractCode;

	/**
	 * The company id.
	 * 会社ID
	 */
	@Column(name = "CID")
	private String companyId;

	/**
	 * The table jp name.
	 */
	@Column(name = "TABLE_JP_NAME")
	private String tableJpName;

	/**
	 * Gets primary key.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.pk;
	}

	/**
	 * Sets category no.
	 *
	 * @param categoryNo the category no
	 */
	@Override
	public void setCategoryNo(int categoryNo) {
		if (this.pk == null) {
			this.pk = new KfnctIndexReorgTablePk();
		}
		this.pk.categoryNo = categoryNo;
	}

	/**
	 * Sets table phys name.
	 *
	 * @param tablePhysicalName the table physical name
	 */
	@Override
	public void setTablePhysName(String tablePhysicalName) {
		if (this.pk == null) {
			this.pk = new KfnctIndexReorgTablePk();
		}
		this.pk.tablePhysName = tablePhysicalName;
	}

	/**
	 * Gets table phys name.
	 *
	 * @return the table phys name
	 */
	@Override
	public String getTablePhysName() {
		return this.pk.tablePhysName;
	}

	/**
	 * Gets category no.
	 *
	 * @return the category no
	 */
	@Override
	public int getCategoryNo() {
		return this.pk.categoryNo;
	}

}
