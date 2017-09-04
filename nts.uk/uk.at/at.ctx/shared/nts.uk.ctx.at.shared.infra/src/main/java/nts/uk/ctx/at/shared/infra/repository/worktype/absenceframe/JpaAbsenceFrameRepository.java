package nts.uk.ctx.at.shared.infra.repository.worktype.absenceframe;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameRepository;
import nts.uk.ctx.at.shared.infra.entity.worktype.absenceframe.KshmtAbsenceFrame;

@Stateless
public class JpaAbsenceFrameRepository extends JpaRepository implements AbsenceFrameRepository {
    
	private final String SEL_1 = "SELECT a FROM KshmtAbsenceFrame a  WHERE a.kshmtAbsenceFramePK.companyId = :companyId AND a.abolishAtr = :abolishAtr ";
	
	private static AbsenceFrame toDomain(KshmtAbsenceFrame entity) {
		AbsenceFrame domain = AbsenceFrame.createSimpleFromJavaType(entity.kshmtAbsenceFramePK.companyId,
				entity.kshmtAbsenceFramePK.absenceFrameNo,
				entity.name, 
				entity.abolishAtr);
		return domain;
	}
	
	@Override
	public List<AbsenceFrame> findAbsenceFrame(String companyId) {			
		return this.queryProxy().query(SEL_1, KshmtAbsenceFrame.class)
				.setParameter("companyId", companyId)
				.setParameter("abolishAtr", 0)
				.getList(a -> toDomain(a));
	}
}
