package nts.uk.cnv.infra.entity.categorypriority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * データコンバートカテゴリ優先順
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_CATEGORY_PRIORITY")
public class ScvmtCategoryPriority extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@Column(name = "SEQ_NO")
	private int sequenceNo;

	@Override
	protected Object getKey() {
		return categoryName;
	}
}
