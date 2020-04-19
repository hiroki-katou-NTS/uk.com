package entity.workplacegroup;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 職場グループ所属情報
 * @author phongtq
 *
 */
@Entity
@NoArgsConstructor
@Table(name="BSYMT_AFF_WORKPLACE_GROUP")
public class BsymtAffWorkPlaceGroup  extends ContractUkJpaEntity{
	@EmbeddedId
    public BsymtAffWorkPlaceGroupPk pk;

	public BsymtAffWorkPlaceGroup(BsymtAffWorkPlaceGroupPk pk) {
		super();
		this.pk = pk;
	}
	
	public AffWorkplaceGroup toDomain(){
		return new AffWorkplaceGroup(
				pk.CID,
				pk.WKPGRPID,
				pk.WKPID);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
