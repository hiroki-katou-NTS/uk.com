package nts.uk.ctx.at.shared.infra.repository.remainingnumber.holidayover60h;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcdtHdpaidTemp;

@Stateless
public class JpaTmpHolidayOver60hMngRepository extends JpaRepository implements TmpHolidayOver60hMngRepository{

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<TmpHolidayOver60hMng> getById(String mngId) {
//		Optional<TmpAnnualHolidayMng> optTmpAnnualHolidayMng = this.queryProxy().find(mngId, KrcdtHdpaidTemp.class)
//				.map(x -> toDomain(x));
		return Optional.empty();
	}

//	private TmpAnnualHolidayMng toDomain(KrcdtHdpaidTemp x) {
//		return new TmpAnnualHolidayMng(x.annualMngId, x.workTypeCode, new UseDay(x.useDays));
//	}

	@Override
	public void deleteById(String mngId) {
		Optional<KrcdtHdpaidTemp> optTmpAnnualHolidayMng = this.queryProxy().find(mngId, KrcdtHdpaidTemp.class);
		optTmpAnnualHolidayMng.ifPresent(x -> {
			this.commandProxy().remove(x);
		});
		
	}

	@Override
	public void persistAndUpdate(TmpHolidayOver60hMng dataMng) {
//		Optional<KrcdtHdpaidTemp> optTmpAnnualHolidayMng = this.queryProxy().find(dataMng.getAnnualId(), KrcdtHdpaidTemp.class);
//		if(optTmpAnnualHolidayMng.isPresent()) {
//			KrcdtHdpaidTemp entity = optTmpAnnualHolidayMng.get();
//			entity.useDays = dataMng.getUseDays().v();
//			entity.workTypeCode = dataMng.getWorkTypeCode();
//			this.commandProxy().update(entity);
//		} else {
//			KrcdtHdpaidTemp entity = new KrcdtHdpaidTemp();
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
//		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KRCDT_HDPAID_TEMP a1"
//				+ " INNER JOIN KRCDT_INTERIM_REMAIN_MNG a2 ON a1.ANNUAL_MNG_ID = a2.REMAIN_MNG_ID"
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

	/**
	 * ドメインモデル「暫定60H超休管理データ」を取得
	 *
	 * @param employeeId 社員ID
	 * @param period 対象日　
	 * @param remainType 60H超休
	 * @return the by employee id and date period and remain type
	 */
	@Override
	public List<TmpHolidayOver60hMng> getByEmployeeIdAndDatePeriodAndRemainType(String employeeId
																				  , DatePeriod period
																				  , int remainType) {
		// TODO change to 60h table
		try (PreparedStatement sql = this.connection().prepareStatement(
			  "SELECT * FROM KRCDT_HDPAID_TEMP a1"
			+ "	INNER JOIN KRCDT_INTERIM_REMAIN_MNG a2"
			+ "		ON a1.ANNUAL_MNG_ID = a2.REMAIN_MNG_ID"
			+ " WHERE a2.SID = ?"
			+ " 	AND a2.REMAIN_TYPE = ?"
			+ " 	AND a2.YMD >= ? and a2.YMD <= ?"
			+ " ORDER BY a2.YMD")) {
			
			sql.setString(1, employeeId);
			sql.setInt(2, remainType);
			sql.setDate(3, Date.valueOf(period.start().localDate()));
			sql.setDate(4, Date.valueOf(period.end().localDate()));

//			return new NtsResultSet(sql.executeQuery()).getList(x -> toDomain(x));
			// TODO fake data for test
			TmpHolidayOver60hMng tmpHolidayOver60hMng = new TmpHolidayOver60hMng(
					"ca294040-910f-4a42-8d90-2bd02772697c"
					, GeneralDate.ymd(2020, 5, 10)
					, "remainTypeId");
			tmpHolidayOver60hMng.setUseTime(Optional.of(new UseTime(40)));
			tmpHolidayOver60hMng.setCreatorAtr(CreateAtr.APPBEFORE);

			TmpHolidayOver60hMng tmpHolidayOver60hMng1 = new TmpHolidayOver60hMng(
					"ca294040-910f-4a42-8d90-2bd02772697c"
					, GeneralDate.ymd(2020, 8, 10)
					, "remainTypeId");
			tmpHolidayOver60hMng1.setUseTime(Optional.of(new UseTime(30)));
			tmpHolidayOver60hMng1.setCreatorAtr(CreateAtr.APPBEFORE);

			TmpHolidayOver60hMng tmpHolidayOver60hMng2 = new TmpHolidayOver60hMng(
					"ca294040-910f-4a42-8d90-2bd02772697c"
					, GeneralDate.ymd(2020, 7, 11)
					, "remainTypeId");
			tmpHolidayOver60hMng2.setUseTime(Optional.of(new UseTime(50)));
			tmpHolidayOver60hMng2.setCreatorAtr(CreateAtr.FLEXCOMPEN);

			TmpHolidayOver60hMng tmpHolidayOver60hMng3 = new TmpHolidayOver60hMng(
					"ca294040-910f-4a42-8d90-2bd02772697c"
					, GeneralDate.ymd(2020, 5, 14)
					, "remainTypeId");
			tmpHolidayOver60hMng3.setUseTime(Optional.of(new UseTime(30)));
			tmpHolidayOver60hMng3.setCreatorAtr(CreateAtr.APPBEFORE);

			TmpHolidayOver60hMng tmpHolidayOver60hMng4 = new TmpHolidayOver60hMng(
					"ca294040-910f-4a42-8d90-2bd02772697c"
					, GeneralDate.ymd(2020, 8, 31)
					, "remainTypeId");
			tmpHolidayOver60hMng4.setUseTime(Optional.of(new UseTime(40)));
			tmpHolidayOver60hMng4.setCreatorAtr(CreateAtr.APPBEFORE);

			TmpHolidayOver60hMng tmpHolidayOver60hMng5 = new TmpHolidayOver60hMng(
					"ca294040-910f-4a42-8d90-2bd02772697c"
					, GeneralDate.ymd(2020, 8, 2)
					, "remainTypeId");
			tmpHolidayOver60hMng5.setUseTime(Optional.of(new UseTime(60)));
			tmpHolidayOver60hMng5.setCreatorAtr(CreateAtr.SCHEDULE);

			TmpHolidayOver60hMng tmpHolidayOver60hMng6 = new TmpHolidayOver60hMng(
					"ca294040-910f-4a42-8d90-2bd02772697c"
					, GeneralDate.ymd(2020, 9, 15)
					, "remainTypeId");
			tmpHolidayOver60hMng6.setUseTime(Optional.of(new UseTime(60)));
			tmpHolidayOver60hMng6.setCreatorAtr(CreateAtr.FLEXCOMPEN);
			
			TmpHolidayOver60hMng tmpHolidayOver60hMng7 = new TmpHolidayOver60hMng(
					"ca294040-910f-4a42-8d90-2bd02772697c"
					, GeneralDate.ymd(2020, 9, 16)
					, "remainTypeId");
			tmpHolidayOver60hMng7.setUseTime(Optional.of(new UseTime(60)));
			tmpHolidayOver60hMng7.setCreatorAtr(CreateAtr.RECORD);

			List<TmpHolidayOver60hMng> result = new ArrayList<TmpHolidayOver60hMng>();
			result.add(tmpHolidayOver60hMng);
			result.add(tmpHolidayOver60hMng1);
			result.add(tmpHolidayOver60hMng2);
			result.add(tmpHolidayOver60hMng3);
			result.add(tmpHolidayOver60hMng4);
			result.add(tmpHolidayOver60hMng5);
			result.add(tmpHolidayOver60hMng6);
			result.add(tmpHolidayOver60hMng7);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		 return new ArrayList<>();
	}

//	private TmpHolidayOver60hMng toDomain(NtsResultRecord x) {		
//		return new TmpAnnualHolidayMng(x.getString("ANNUAL_MNG_ID"),
//				x.getString("WORKTYPE_CODE"),
//				new UseDay(x.getBigDecimal("USE_DAYS") == null ? 0 : x.getBigDecimal("USE_DAYS").doubleValue()));
//	
//	}

}
