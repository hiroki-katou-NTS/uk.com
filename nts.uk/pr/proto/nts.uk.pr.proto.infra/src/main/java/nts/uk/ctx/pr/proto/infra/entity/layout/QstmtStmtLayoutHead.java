package nts.uk.ctx.pr.proto.infra.entity.layout;

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
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name="QSTMT_STMT_LAYOUT_HEAD")
@AllArgsConstructor
@NoArgsConstructor
public class QstmtStmtLayoutHead implements Serializable {
	
	public static final long serialVersionUID = 1L;

	@EmbeddedId
	public QstmtStmtLayoutHeadPK qstmtStmtLayoutHeadPK;
	
	@Column(name ="STMT_NAME")
	public String stmtName;

	@Basic(optional = false)
	@Column(name ="STR_YM")
	public int strYm;

	@Basic(optional = false)
	@Column(name ="END_YM")
	public int endYm;
	
	@Basic(optional = false)
	@Column(name ="LAYOUT_ATR")
	public int layoutAtr;
	
//	@OneToMany(cascade=CascadeType.ALL, mappedBy="layoutHead")
//	public List<QstmtStmtLayoutCtg> layoutCategories;
}
