package nts.uk.ctx.at.shared.infra.repository.worktype.specialholidayframe;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.ctx.at.shared.infra.entity.worktype.specialholidayframe.KshmtSpecialHolidayFrame;

@Stateless
public class JpaSpecialHolidayFrameRepository extends JpaRepository implements SpecialHolidayFrameRepository  {
	
	private final String SEL_1 = "SELECT a FROM KshmtSpecialHolidayFrame a  WHERE a.kshmtSpecialHolidayFramePK.companyId = :companyId AND a.abolishAtr = :abolishAtr ";
	
	private static SpecialHolidayFrame toDomain(KshmtSpecialHolidayFrame entity) {
		SpecialHolidayFrame domain = SpecialHolidayFrame.createSimpleFromJavaType(entity.kshmtSpecialHolidayFramePK.companyId,
				entity.kshmtSpecialHolidayFramePK.specialHdFrameNo,
				entity.name,
				entity.abolishAtr);
		return domain;
	}		

	@Override
	public List<SpecialHolidayFrame> findSpecialHolidayFrame(String companyId) {
		return this.queryProxy().query(SEL_1, KshmtSpecialHolidayFrame.class)
				.setParameter("companyId", companyId)
				.setParameter("abolishAtr", 0)
				.getList(a -> toDomain(a));
	}
}
