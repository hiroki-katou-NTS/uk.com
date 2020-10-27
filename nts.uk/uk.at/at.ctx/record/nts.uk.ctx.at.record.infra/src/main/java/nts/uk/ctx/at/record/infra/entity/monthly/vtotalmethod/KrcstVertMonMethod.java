package nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

//import java.math.BigDecimal;


/**
 * @author HoangNDH
 * The persistent class for the KRCST_VERT_MON_METHOD database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KRCST_VERT_MON_METHOD")
public class KrcstVertMonMethod extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 会社ID
	@Id
	@Column(name="CID")
	private String cid;

	// 振出日数カウント条件
	@Column(name="TRANS_ATTEND_DAY")
	private int transAttendDay;

	@Override
	protected Object getKey() {
		return cid;
	}

}