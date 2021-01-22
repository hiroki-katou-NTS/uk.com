package nts.uk.ctx.at.function.infra.entity.indexreconstruction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgCat;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The class KfnctIndexReorgCat.<br>
 * Entity インデックス再構成カテゴリ
 */
@Data
@Entity
@Table(name = "KFNCT_INDEX_REORG_CAT")
@EqualsAndHashCode(callSuper = true)
public class KfnctIndexReorgCat extends UkJpaEntity
		implements IndexReorgCat.MementoGetter, IndexReorgCat.MementoSetter, Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The column 排他バージョン
	 */
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;

	/**
	 * The primary key category no.<br>
	 * カテゴリNO
	 */
	@Id
	@Column(name = "CATEGORY_NO", nullable = false)
	private int categoryNo;

	/**
	 * The category name.<br>
	 * カテゴリ名
	 */
	@Column(name = "CATEGORY_NAME", nullable = false)
	private String categoryName;

	/**
	 * Gets primary key.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.categoryNo;
	}

	/**
	 * No args constructor.
	 */
	protected KfnctIndexReorgCat() {
	}

	/**
	 * Creates new entity from domain and memento.
	 *
	 * @param domain       the domain require <code>not null</code>
	 */
	public KfnctIndexReorgCat(@NonNull IndexReorgCat domain) {
		domain.setMemento(this);
	}

}
