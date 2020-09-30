package nts.uk.ctx.at.shared.infra.repository.remainingnumber.holidayover60h;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMngRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcmtInterimAnnualMng;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class JpaTmpHolidayOver60hMngRepository extends JpaRepository implements TmpHolidayOver60hMngRepository{

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<TmpHolidayOver60hMng> getById(String mngId) {
//		Optional<TmpAnnualHolidayMng> optTmpAnnualHolidayMng = this.queryProxy().find(mngId, KrcmtInterimAnnualMng.class)
//				.map(x -> toDomain(x));
		return Optional.empty();
	}

//	private TmpAnnualHolidayMng toDomain(KrcmtInterimAnnualMng x) {
//		return new TmpAnnualHolidayMng(x.annualMngId, x.workTypeCode, new UseDay(x.useDays));
//	}

	@Override
	public void deleteById(String mngId) {
		Optional<KrcmtInterimAnnualMng> optTmpAnnualHolidayMng = this.queryProxy().find(mngId, KrcmtInterimAnnualMng.class);
		optTmpAnnualHolidayMng.ifPresent(x -> {
			this.commandProxy().remove(x);
		});
		
	}

	@Override
	public void persistAndUpdate(TmpHolidayOver60hMng dataMng) {
//		Optional<KrcmtInterimAnnualMng> optTmpAnnualHolidayMng = this.queryProxy().find(dataMng.getAnnualId(), KrcmtInterimAnnualMng.class);
//		if(optTmpAnnualHolidayMng.isPresent()) {
//			KrcmtInterimAnnualMng entity = optTmpAnnualHolidayMng.get();
//			entity.useDays = dataMng.getUseDays().v();
//			entity.workTypeCode = dataMng.getWorkTypeCode();
//			this.commandProxy().update(entity);
//		} else {
//			KrcmtInterimAnnualMng entity = new KrcmtInterimAnnualMng();
//			entity.annualMngId = dataMng.getAnnualId();
//			entity.useDays = dataMng.getUseDays().v();
//			entity.workTypeCode = dataMng.getWorkTypeCode();
//			this.getEntityManager().persist(entity);
//		}
//		this.getEntityManager().flush();
	}
	@SneakyThrows
	@Override
	public List<TmpHolidayOver60hMng> getBySidPeriod(String sid, DatePeriod period) {
//		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KRCMT_INTERIM_ANNUAL_MNG a1"
//				+ " INNER JOIN KRCMT_INTERIM_REMAIN_MNG a2 ON a1.ANNUAL_MNG_ID = a2.REMAIN_MNG_ID"
//				+ " WHERE a2.SID = ?"
//				+ " AND  a2.REMAIN_TYPE = 0"
//				+ " AND a2.YMD >= ? and a2.YMD <= ?"
//				+ " ORDER BY a2.YMD");
//		)
//		{
//			sql.setString(1, sid);
//			sql.setDate(2, Date.valueOf(period.start().localDate()));
//			sql.setDate(3, Date.valueOf(period.end().localDate()));
//			List<TmpAnnualHolidayMng> lstOutput = new NtsResultSet(sql.executeQuery())
//					.getList(x -> toDomain(x));
//			return lstOutput;
			return new ArrayList<TmpHolidayOver60hMng>();
	}

//	private TmpHolidayOver60hMng toDomain(NtsResultRecord x) {		
//		return new TmpAnnualHolidayMng(x.getString("ANNUAL_MNG_ID"),
//				x.getString("WORKTYPE_CODE"),
//				new UseDay(x.getBigDecimal("USE_DAYS") == null ? 0 : x.getBigDecimal("USE_DAYS").doubleValue()));
//	
//	}

}
