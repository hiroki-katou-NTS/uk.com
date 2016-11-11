package nts.uk.ctx.pr.proto.infra.entity.layout;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.AggregateTableEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="QSTMT_STMT_LAYOUT_LINES")
public class QstmtStmtLayoutLines extends AggregateTableEntity implements Serializable {

	public static final long serialVersionUID = 1L;

	@EmbeddedId
	public QstmtStmtLayoutLinesPK qstmtStmtLayoutLinesPk;

	@Basic(optional = false)
	@Column(name ="END_YM")
	public int endYm;

	@Basic(optional = false)
	@Column(name ="LINE_POS")
	public int linePos;

	@Basic(optional = false)
	@Column(name ="LINE_DISP_ATR")
	public int lineDispAtr;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="CCD", referencedColumnName="CCD", insertable = false, updatable = false),
        @JoinColumn(name="STMT_CD", referencedColumnName="STMT_CD", insertable = false, updatable = false),
        @JoinColumn(name="STR_YM", referencedColumnName="STR_YM", insertable = false, updatable = false),
        @JoinColumn(name="CTG_ATR", referencedColumnName="CTG_ATR", insertable = false, updatable = false)
    })
	public QstmtStmtLayoutDetail layoutDetail;
}
