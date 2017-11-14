package nts.uk.ctx.at.shared.infra.repository.employmentrule.hourlate.breaktime.breaktimeframe;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.breaktime.breaktimeframe.BreaktimeFrame;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.breaktime.breaktimeframe.BreaktimeFrameRepository;
import nts.uk.ctx.at.shared.infra.entity.employmentrule.hourlate.breaktime.breaktimeframe.KrqdtBreaktimeFrame;

@Stateless
public class BreaktimeFrameImpl extends JpaRepository implements BreaktimeFrameRepository {
	
	private static final String FIND_ALL = "SELECT e FROM KrqdtBreaktimeFrame e";
	private static final String FIND_BY_FRAMENO;
	private static final String FIND_BY_COMPANYID;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.krqdtBreaktimeFramePK.breaktimeFrameNo IN :breaktimeFrameNo");
		FIND_BY_FRAMENO = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" Where e.krqdtBreaktimeFramePK.cid = :companyID");
		query.append(" AND e.useAtr = :useAtr");
		FIND_BY_COMPANYID = query.toString();
	}

	@Override
	public List<BreaktimeFrame> getBreaktimeFrameByFrameNo(List<Integer> breaktimeFrameNo) {
		return this.queryProxy().query(FIND_BY_FRAMENO, KrqdtBreaktimeFrame.class)
				.setParameter("breaktimeFrameNo", breaktimeFrameNo)
				.getList(e -> convertToDomain(e));
	}

	@Override
	public List<BreaktimeFrame> getBreaktimeFrameByCID(String companyID, int useAtr) {
		return this.queryProxy().query(FIND_BY_COMPANYID,KrqdtBreaktimeFrame.class)
				.setParameter("companyID", companyID)
				.setParameter("useAtr", useAtr)
				.getList(e -> convertToDomain(e));
	}
	
	private BreaktimeFrame convertToDomain(KrqdtBreaktimeFrame entity){
		return BreaktimeFrame.createSimpleFromJavaType(entity.getKrqdtBreaktimeFramePK().getCid(),
				entity.getKrqdtBreaktimeFramePK().getBreaktimeFrameNo(),
				entity.getUseAtr(), entity.getTransferFrameName(),
				entity.getBreaktimeFrameName());
	}

}
