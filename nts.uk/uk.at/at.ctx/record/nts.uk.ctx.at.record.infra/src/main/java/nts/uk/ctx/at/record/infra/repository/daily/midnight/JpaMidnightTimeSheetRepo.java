package nts.uk.ctx.at.record.infra.repository.daily.midnight;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.midnight.MidnightTimeSheetRepo;
import nts.uk.ctx.at.record.infra.entity.daily.midnight.KrcstNightTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author yennh
 *
 */
@Stateless
public class JpaMidnightTimeSheetRepo extends JpaRepository implements MidnightTimeSheetRepo {
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT k");
		builderString.append(" FROM KrcstNightTimeSheet k");
		builderString.append(" WHERE k.cid = :companyId");
		SELECT_BY_CID = builderString.toString();
	}
	
	private MidNightTimeSheet convertToDomain(KrcstNightTimeSheet krcstNightTimeSheet) {
		MidNightTimeSheet midNightTimeSheet = new MidNightTimeSheet(krcstNightTimeSheet.getCid(), new TimeWithDayAttr(krcstNightTimeSheet.getStartTime()), new TimeWithDayAttr(krcstNightTimeSheet.getEndTime()));
		
		return midNightTimeSheet;
	}
	
	private KrcstNightTimeSheet convertToDbType(MidNightTimeSheet midNightTimeSheet){
		KrcstNightTimeSheet krcstNightTimeSheet = new KrcstNightTimeSheet();
		krcstNightTimeSheet.setCid(midNightTimeSheet.getCompanyId());
		krcstNightTimeSheet.setStartTime(midNightTimeSheet.getStart().v());
		krcstNightTimeSheet.setEndTime(midNightTimeSheet.getEnd().v());
		return krcstNightTimeSheet;
	}

	@Override
	public List<MidNightTimeSheet> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KrcstNightTimeSheet.class).setParameter("companyId", companyId).getList(d -> convertToDomain(d));
	}

	@Override
	public void add(MidNightTimeSheet midNightTimeSheet) {
		this.commandProxy().insert(convertToDbType(midNightTimeSheet));
	}

	@Override
	public void update(MidNightTimeSheet midNightTimeSheet) {
		KrcstNightTimeSheet krcstNightTimeSheet = this.queryProxy().find(midNightTimeSheet.getCompanyId(), KrcstNightTimeSheet.class).get();
		krcstNightTimeSheet.setStartTime(midNightTimeSheet.getStart().v());
		krcstNightTimeSheet.setEndTime(midNightTimeSheet.getEnd().v());
		this.commandProxy().update(krcstNightTimeSheet);
	}

	@Override
	public Optional<MidNightTimeSheet> findByCId(String companyId) {
		return this.queryProxy().find(companyId,KrcstNightTimeSheet.class)
				.map(c->convertToDomain(c));
	}

}
