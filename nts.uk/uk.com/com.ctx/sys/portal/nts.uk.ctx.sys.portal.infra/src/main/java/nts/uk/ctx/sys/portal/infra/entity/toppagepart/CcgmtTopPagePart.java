package nts.uk.ctx.sys.portal.infra.entity.toppagepart;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author LamDT
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGMT_TOPPAGE_PART")
public class CcgmtTopPagePart extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CcgmtTopPagePartPK ccgmtTopPagePartPK;

	@Column(name = "CODE")
	public String code;

	@Column(name = "NAME")
	public String name;

	@Column(name = "TOPPAGE_PART_TYPE")
	public int topPagePartType;

	@Column(name = "WIDTH")
	public int width;

	@Column(name = "HEIGHT")
	public int height;
	
	@Override
	protected Object getKey() {
		return ccgmtTopPagePartPK;
	}

}