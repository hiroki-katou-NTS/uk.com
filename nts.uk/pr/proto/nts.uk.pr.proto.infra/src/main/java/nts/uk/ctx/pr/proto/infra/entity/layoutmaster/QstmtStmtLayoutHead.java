package nts.uk.ctx.pr.proto.infra.entity.layoutmaster;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name="QSTMT_STMT_LAYOUT_HEAD")
@NoArgsConstructor
public class QstmtStmtLayoutHead extends AggregateTableEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private QstmtStmtLayoutHeadPk qstmtStmtLayoutHeadPK;
	
	@Column(name ="STMT_NAME")
	private String stmtName;
		
	@Basic(optional = false)
	@Column(name ="END_YM")
	private int endYm;
	
	@Basic(optional = false)
	@Column(name ="LAYOUT_ATR")
	private int layoutAtr;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="layoutHead")
	private List<QstmtStmtLayoutCtg> layoutCategories;
}
