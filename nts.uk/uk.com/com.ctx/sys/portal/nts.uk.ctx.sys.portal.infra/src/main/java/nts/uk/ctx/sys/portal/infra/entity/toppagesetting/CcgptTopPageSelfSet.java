package nts.uk.ctx.sys.portal.infra.entity.toppagesetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGPT_TOPPAGE_SELF_SET")
public class CcgptTopPageSelfSet extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CcgptTopPageSelfSetPK ccgptTopPageSelfSetPK;	

	/** The code. */
	@Column(name = "CODE")
	public String code;
	
	/** The division. */
	@Column(name = "DIVISION")
	public int division;
	
	@Override
	protected Object getKey() {
		return ccgptTopPageSelfSetPK;
	}
}
