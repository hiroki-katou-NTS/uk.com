package nts.uk.ctx.at.shared.infra.repository.employmentrule.hourlate.overtime.overtimeframe;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe.OvertimeFrame;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe.OvertimeFrameRepository;
import nts.uk.ctx.at.shared.infra.entity.employmentrule.hourlate.overtime.overtimeframe.KrqdtOvertimeFrame;

@Stateless
public class OvertimeFrameImpl extends JpaRepository implements OvertimeFrameRepository {
	private static final String FIND_ALL = "SELECT e FROM KrqdtOvertimeFrame e";
	private static final String FIND_BY_FRAMENO;
	private static final String FIND_BY_COMPANYID;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" Where e.krqdtOvertimeFramePK.cid = :companyID");
		query.append(" AND e.krqdtOvertimeFramePK.otFrameNo IN :frameNo");
		FIND_BY_FRAMENO = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" Where e.krqdtOvertimeFramePK.cid = :companyID");
		query.append(" AND e.useAtr = :useAtr");
		FIND_BY_COMPANYID = query.toString();
	}
	@Override
	public List<OvertimeFrame> getOvertimeFrameByFrameNos(String companyID,List<Integer> frameNo) {
		
		return this.queryProxy().query(FIND_BY_FRAMENO, KrqdtOvertimeFrame.class)
				.setParameter("companyID", companyID)
				.setParameter("frameNo", frameNo)
				.getList(e -> convertToDomain(e));
	}
	
	
	

	@Override
	public List<OvertimeFrame> getOvertimeFrameByCID(String companyID,int useAtr) {
		
		return this.queryProxy().query(FIND_BY_COMPANYID,KrqdtOvertimeFrame.class)
				.setParameter("companyID", companyID)
				.setParameter("useAtr", useAtr)
				.getList(e -> convertToDomain(e));
		
	}
	private OvertimeFrame convertToDomain(KrqdtOvertimeFrame entity){
		return OvertimeFrame.createSimpleFromJavaType(entity.getKrqdtOvertimeFramePK().getCid(),
				entity.getKrqdtOvertimeFramePK().getOtFrameNo(),
				entity.getUseAtr(), entity.getTransferFrameName(),
				entity.getOvertimeFrameName());
	}
}
