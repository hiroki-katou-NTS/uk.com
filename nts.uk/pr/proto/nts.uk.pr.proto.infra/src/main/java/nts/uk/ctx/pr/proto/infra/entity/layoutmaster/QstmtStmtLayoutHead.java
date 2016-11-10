package nts.uk.ctx.pr.proto.infra.entity.layoutmaster;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name="QSTMT_STMT_LAYOUT_HEAD")
public class QstmtStmtLayoutHead extends AggregateTableEntity {
	
	@EmbeddedId
	public QstmtStmtLayoutHeadPK qstmtStmtLayoutHeadPK;
	
	@Column(name ="STMT_NAME")
	public String stmtName;
		
	@Column(name ="END_YM")
	public int endYm;
	
	@Column(name ="LAYOUT_ATR")
	public int layoutAtr;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="layoutHead")
	public List<QstmtStmtLayoutCtg> layoutCategories;
}
