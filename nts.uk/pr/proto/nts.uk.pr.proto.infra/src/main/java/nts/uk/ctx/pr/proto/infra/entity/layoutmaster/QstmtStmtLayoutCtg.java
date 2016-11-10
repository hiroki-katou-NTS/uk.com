package nts.uk.ctx.pr.proto.infra.entity.layoutmaster;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="QSTMT_STMT_LAYOUT_CTG")
public class QstmtStmtLayoutCtg {
	
	@EmbeddedId
	private QstmtStmtLayoutCtgPK qstmtStmtLayoutCtgPk;

	@Basic(optional = false)
	@Column(name ="END_YM")
	private int endYm;

	@Basic(optional = false)
	@Column(name ="CTG_POS")
	private int ctgPos;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="CCD", referencedColumnName="CCD", insertable = false, updatable = false),
        @JoinColumn(name="STMT_CD", referencedColumnName="STMT_CD", insertable = false, updatable = false),
        @JoinColumn(name="STR_YM", referencedColumnName="STR_YM", insertable = false, updatable = false)
    })
	private QstmtStmtLayoutHead layoutHead;
	
	@OneToOne(optional=false)
	@JoinColumns({
        @JoinColumn(name="CCD", referencedColumnName="CCD", insertable = false, updatable = false),
        @JoinColumn(name="STMT_CD", referencedColumnName="STMT_CD", insertable = false, updatable = false),
        @JoinColumn(name="STR_YM", referencedColumnName="STR_YM", insertable = false, updatable = false),
        @JoinColumn(name="CTG_ATR", referencedColumnName="CTG_ATR", insertable = false, updatable = false)
    })
	private QstmtStmtLayoutDetail getQstmtStmtLayoutDetail;

}
