package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.fixedverticalsetting;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalTime;
@Stateless
public class JpaFixedVerticalSetting extends JpaRepository implements FixedVerticalSettingRepository{

	private static final String SELECT_BY_CID;
	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFixedVerticalSet e");
		builderString.append(" WHERE e.kscstFixedVerticalSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}
	
	@Override
	public List<FixedVertical> findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFixedVertical(FixedVertical fixedVertical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFixedVertical(FixedVertical fixedVertical) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<VerticalTime> findAllVerticalTime(String companyId, int fixedVerticalNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addVerticalTime(VerticalTime verticalTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVerticalTime(VerticalTime verticalTime) {
		// TODO Auto-generated method stub
		
	}

}
