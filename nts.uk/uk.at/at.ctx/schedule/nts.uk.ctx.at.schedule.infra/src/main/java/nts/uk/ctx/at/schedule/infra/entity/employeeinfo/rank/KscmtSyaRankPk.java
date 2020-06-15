package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.rank;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtSyaRankPk implements Serializable {
private static final long serialVersionUID = 1L;

/** 会社ID */
@Column(name = "CID")
public String CID;

/** 社員ID */
@Column(name = "SID")
public String SID;
}
