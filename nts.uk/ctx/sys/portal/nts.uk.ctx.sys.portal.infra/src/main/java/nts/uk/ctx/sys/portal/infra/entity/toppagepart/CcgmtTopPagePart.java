package nts.uk.ctx.sys.portal.infra.entity.toppagepart;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author LamDT
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGMT_TOPPAGE_PART")
public class CcgmtTopPagePart extends UkJpaEntity {

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
