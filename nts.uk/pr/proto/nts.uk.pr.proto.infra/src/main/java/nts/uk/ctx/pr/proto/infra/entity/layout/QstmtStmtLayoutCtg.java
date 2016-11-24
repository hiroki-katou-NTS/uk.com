package nts.uk.ctx.pr.proto.infra.entity.layout;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QSTMT_STMT_LAYOUT_CTG")
public class QstmtStmtLayoutCtg implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QstmtStmtLayoutCtgPK qstmtStmtLayoutCtgPk;

	@Basic(optional = false)
	@Column(name = "END_YM")
	public int endYm;

	@Basic(optional = false)
	@Column(name = "CTG_POS")
	public int ctgPos;
	
//	@ManyToOne
//	@JoinColumns({ @JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
//			@JoinColumn(name = "STMT_CD", referencedColumnName = "STMT_CD", insertable = false, updatable = false),
//			@JoinColumn(name = "STR_YM", referencedColumnName = "STR_YM", insertable = false, updatable = false) })
//	public QstmtStmtLayoutHead layoutHead;
//
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "layoutCategory")
//	public List<QstmtStmtLayoutLines> layoutLines;

}
