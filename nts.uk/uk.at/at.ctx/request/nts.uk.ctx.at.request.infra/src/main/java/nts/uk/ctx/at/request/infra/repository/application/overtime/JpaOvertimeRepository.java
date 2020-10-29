package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdpTime36UpLimitPerMonthPK;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertime;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetail;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetailPk;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimePK;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtOvertimeInput;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtOvertimeInputPK;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtTime36UpLimitPerMonth;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtYear36OverMonth;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtYear36OverMonthPk;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
public class JpaOvertimeRepository extends JpaRepository implements OvertimeRepository {

	private static final String FIND_ALL = "SELECT e FROM KrqdtAppOvertime e";

	private static final String FIND_BY_APPID;

	private static final String FIND_BY_ATR;

	static {
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.krqdtAppOvertimePK.cid = :companyID");
		query.append(" AND e.krqdtAppOvertimePK.appId = :appID");
		FIND_BY_APPID = query.toString();

		query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.overtimeAtr = :overtimeAtr");
		FIND_BY_ATR = query.toString();
	}

	@Override
	public Optional<AppOverTime_Old> getAppOvertime(String companyID, String appID) {
		return this.queryProxy().query(FIND_BY_APPID, KrqdtAppOvertime.class).setParameter("companyID", companyID)
				.setParameter("appID", appID).getSingle(e -> convertToDomain(e));
	}

	@Override
	public void Add(AppOverTime_Old domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public Optional<AppOverTime_Old> getFullAppOvertime(String companyID, String appID) {
//		Optional<KrqdtAppOvertime> opKrqdtAppOvertime = this.queryProxy().find(new KrqdtAppOvertimePK(companyID, appID),
//				KrqdtAppOvertime.class);
//		Optional<KrqdtApplication_New> opKafdtApplication = this.queryProxy()
//				.find(new KrqdpApplicationPK_New(companyID, appID), KrqdtApplication_New.class);
//		if (!opKrqdtAppOvertime.isPresent() || !opKafdtApplication.isPresent()) {
//			return Optional.ofNullable(null);
//		}
//		KrqdtAppOvertime krqdtAppOvertime = opKrqdtAppOvertime.get();
//		KrqdtApplication_New kafdtApplication = opKafdtApplication.get();
//		AppOverTime appOverTime = krqdtAppOvertime.toOvertimeAppSetDomain();
//		appOverTime.setApplication(kafdtApplication.toOvertimeAppSetDomain());
//		return Optional.of(appOverTime);
		return Optional.empty();
	}

	@Override
	public void update(AppOverTime_Old appOverTime) {
		String companyID = appOverTime.getCompanyID();
		String appID = appOverTime.getAppID();
		Optional<KrqdtAppOvertime> opKrqdtAppOvertime = this.queryProxy().find(new KrqdtAppOvertimePK(companyID, appID),
				KrqdtAppOvertime.class);
		if (!opKrqdtAppOvertime.isPresent()) {
			throw new RuntimeException("khong ton tai doi tuong de update");
		}
		KrqdtAppOvertime krqdtAppOvertime = opKrqdtAppOvertime.get();
		krqdtAppOvertime.fromDomainValue(appOverTime);
		this.commandProxy().update(krqdtAppOvertime);
	}

	@Override
	public void delete(String companyID, String appID) {
		Optional<KrqdtAppOvertime> opKrqdtAppOvertime = this.queryProxy().find(new KrqdtAppOvertimePK(companyID, appID),
				KrqdtAppOvertime.class);
		if (!opKrqdtAppOvertime.isPresent()) {
			throw new RuntimeException("khong ton tai doi tuong de xoa");
		}
		// Delete application over time
		this.commandProxy().remove(KrqdtAppOvertime.class, new KrqdtAppOvertimePK(companyID, appID));
	}

	private KrqdtAppOvertime toEntity(AppOverTime_Old domain) {
		List<KrqdtOvertimeInput> overtimeInputs = domain.getOverTimeInput().stream().map(item -> {
			KrqdtOvertimeInputPK pk = new KrqdtOvertimeInputPK(item.getCompanyID(), item.getAppID(),
					item.getAttendanceType().value, item.getFrameNo(), item.getTimeItemTypeAtr().value);
			return new KrqdtOvertimeInput(pk, item.getStartTime() == null ? null : item.getStartTime().v(),
					item.getEndTime() == null ? null : item.getEndTime().v(),
					item.getApplicationTime() == null ? null : item.getApplicationTime().v());
		}).collect(Collectors.toList());

		KrqdtAppOvertimeDetail appOvertimeDetail = KrqdtAppOvertimeDetail.toEntity(domain.getAppOvertimeDetail());

		return new KrqdtAppOvertime(new KrqdtAppOvertimePK(domain.getCompanyID(), domain.getAppID()),
				domain.getVersion(), domain.getOverTimeAtr().value,
				domain.getWorkTypeCode() == null ? null : domain.getWorkTypeCode().v(),
				domain.getSiftCode() == null ? null : domain.getSiftCode().v(), domain.getWorkClockFrom1(),
				domain.getWorkClockTo1(), domain.getWorkClockFrom2(), domain.getWorkClockTo2(),
				domain.getDivergenceReason(), domain.getFlexExessTime(), domain.getOverTimeShiftNight(), overtimeInputs,
				appOvertimeDetail);
	}

	private AppOverTime_Old convertToDomain(KrqdtAppOvertime entity) {
		return AppOverTime_Old.createSimpleFromJavaType(entity.getKrqdtAppOvertimePK().getCid(),
				entity.getKrqdtAppOvertimePK().getAppId(), entity.getOvertimeAtr(), entity.getWorkTypeCode(),
				entity.getSiftCode(), entity.getWorkClockFrom1(), entity.getWorkClockTo1(), entity.getWorkClockFrom2(),
				entity.getWorkClockTo2(), entity.getDivergenceReason(), entity.getFlexExcessTime(),
				entity.getOvertimeShiftNight());
	}

	@Override
	public Optional<AppOverTime_Old> getAppOvertimeByDate(GeneralDate appDate, String employeeID, OverTimeAtr overTimeAtr) {
//		List<AppOverTime> appOverTimeList = this.queryProxy().query(FIND_BY_ATR, KrqdtAppOvertime.class)
//				.setParameter("overtimeAtr", overTimeAtr.value).getList(e -> convertToDomain(e));
//		// List<AppOverTime> fullList =
//		appOverTimeList.stream().map(x -> this.getFullAppOvertime(x.getCompanyID(), x.getAppID()).orElse(null))
//				.collect(Collectors.toList());
//		List<AppOverTime> resultList = appOverTimeList.stream().filter(x -> {
//			if (x == null)
//				return false;
//			Application_New app = x.getApplication();
//			if (app == null)
//				return false;
//			return app.getAppDate().equals(appDate) && app.getEmployeeID().equals(employeeID)
//					&& app.getPrePostAtr().equals(PrePostAtr.PREDICT);
//		}).collect(Collectors.toList());
//		if (CollectionUtil.isEmpty(resultList)) {
//			return Optional.empty();
//		}
//		resultList.sort(Comparator.comparing((AppOverTime x) -> {
//			return x.getApplication().getInputDate();
//		}).reversed());
//		return Optional.of(resultList.get(0));
		return Optional.empty();
	}

	/**
	 * get Application Over Time and Frame
	 * 
	 * @author hoatt-new
	 * @param companyID
	 * @param appID
	 * @return
	 */
	@Override
	public Optional<AppOverTime_Old> getAppOvertimeFrame(String companyID, String appID) {
		Optional<KrqdtAppOvertime> opKrqdtAppOvertime = this.queryProxy().find(new KrqdtAppOvertimePK(companyID, appID),
				KrqdtAppOvertime.class);
		if (!opKrqdtAppOvertime.isPresent()) {
			return Optional.ofNullable(null);
		}
		KrqdtAppOvertime krqdtAppOvertime = opKrqdtAppOvertime.get();
		AppOverTime_Old appOverTime = krqdtAppOvertime.toDomain();
		return Optional.of(appOverTime);
	}

	/**
	 * get list Application Over Time and Frame
	 * 
	 * @author hoatt
	 * @param companyID
	 * @param lstAppID
	 * @return map: key - appID, value - AppOverTime
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Map<String, AppOverTime_Old> getListAppOvertimeFrame(String companyID, List<String> lstAppID) {
		Map<String, AppOverTime_Old> lstMap = new HashMap<>();

		if (lstAppID.isEmpty()) {
			return lstMap;
		}

		Set<KrqdtYear36OverMonth> lstYear36OverMonth = new HashSet<KrqdtYear36OverMonth>();
		Set<KrqdtTime36UpLimitPerMonth> lstAverageTimeLst = new HashSet<KrqdtTime36UpLimitPerMonth>();
		Set<KrqdtOvertimeInput> lstKrqdtOvertimeInput = new HashSet<KrqdtOvertimeInput>();
		Set<KrqdtAppOvertimeDetail> lstkrqdtAppOvertimeDetail = new HashSet<KrqdtAppOvertimeDetail>();
		Set<KrqdtAppOvertime> lstKrqdtAppOvertime = new HashSet<KrqdtAppOvertime>();

		CollectionUtil.split(lstAppID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String subAppID = NtsStatement.In.createParamsString(subList);
			String sql = "SELECT ot.CID, ot.APP_ID, ot.OVERTIME_ATR, ot.WORK_TYPE_CODE, ot.SIFT_CODE, ot.WORK_CLOCK_FROM1, ot.WORK_CLOCK_TO1, "
					+ "ot.WORK_CLOCK_FROM2, ot.WORK_CLOCK_TO2, ot.DIVERGENCE_REASON, ot.FLEX_EXCESS_TIME, ot.OVERTIME_SHIFT_NIGHT, "
					+ "input.ATTENDANCE_ID, input.FRAME_NO, input.TIME_ITEM_TYPE_ATR, input.START_TIME, input.END_TIME, input.APPLICATION_TIME as APPLICATION_TIME_INPUT, "
					+ "detail.YEAR_MONTH, detail.APPLICATION_TIME as APPLICATION_TIME_DETAIL, detail.ACTUAL_TIME, detail.LIMIT_ALARM_TIME, detail.LIMIT_ERROR_TIME, detail.NUM_OF_YEAR36_OVER, detail.EXCEPTION_LIMIT_ALARM_TIME, "
					+ "detail.EXCEPTION_LIMIT_ERROR_TIME, detail.ACTUAL_TIME_YEAR, detail.LIMIT_TIME_YEAR, detail.REG_APPLICATION_TIME, detail.REG_ACTUAL_TIME, detail.REG_LIMIT_TIME, detail.REG_LIMIT_TIME_MULTI, "
					+ "om.OVER_MONTH, " + "detm.START_YM, detm.END_YM, detm.AVE_TIME, detm.TOTAL_TIME "
					+ "FROM KRQDT_APP_OVERTIME ot "
					+ "LEFT JOIN KRQDT_OVERTIME_INPUT input ON ot.CID = input.CID and ot.APP_ID = input.APP_ID "
					+ "LEFT JOIN KRQDT_APP_OVERTIME_DETAIL detail ON ot.CID = detail.CID and ot.APP_ID = detail.APP_ID "
					+ "LEFT JOIN KRQDT_YEAR36_OVER_MONTH om ON ot.CID = om.CID and ot.APP_ID = om.APP_ID "
					+ "LEFT JOIN KRQDT_APP_OVERTIME_DET_M detm ON ot.CID = detm.CID and ot.APP_ID = detm.APP_ID "
					+ "WHERE ot.APP_ID IN (" + subAppID + ") AND ot.CID = ?";

			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(i + 1, subList.get(i));
				}
				stmt.setString(subList.size() + 1, companyID);

				new NtsResultSet(stmt.executeQuery()).forEach(rs -> {
					String cid = rs.getString("CID");
					String appId = rs.getString("APP_ID");

					// OVER_MONTH is not null
					if (rs.getInt("OVER_MONTH") != null) {
						lstYear36OverMonth.add(this.createKrqdtYear36OverMonth(cid, appId, rs));
					}
					// START_YM is not null
					if (rs.getInt("START_YM") != null) {
						lstAverageTimeLst.add(this.createKrqdtTime36UpLimitPerMonth(cid, appId, rs));
					}
					// ATTENDANCE_ID is not null
					if (rs.getInt("ATTENDANCE_ID") != null) {
						lstKrqdtOvertimeInput.add(this.createKrqdtOvertimeInput(cid, appId, rs));
					}
					// APPLICATION_TIME is not null
					if (rs.getInt("YEAR_MONTH") != null) {
						lstkrqdtAppOvertimeDetail.add(this.createKrqdtAppOvertimeDetail(cid, appId, rs));
					}

					lstKrqdtAppOvertime.add(this.createKrqdtAppOvertime(cid, appId, rs));
				});

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		// merge data
		lstAppID.forEach(appId -> {
			Optional<KrqdtAppOvertime> optKrqdtAppOvertime = lstKrqdtAppOvertime.stream()
					.filter(x -> x.getKrqdtAppOvertimePK().getAppId().equals(appId)).findFirst();

			if (!optKrqdtAppOvertime.isPresent()) {
				return;
			}

			// get krqdtAppOvertime
			KrqdtAppOvertime krqdtAppOvertime = optKrqdtAppOvertime.get();
			// get list KrqdtOvertimeInput
			List<KrqdtOvertimeInput> lstKrqdtOvertimeInputFilter = lstKrqdtOvertimeInput.stream()
					.filter(x -> x.getKrqdtOvertimeInputPK().getAppId().equals(appId)).collect(Collectors.toList());
			// set KrqdtOvertimeInput for krqdtAppOvertime
			krqdtAppOvertime.setOvertimeInputs(lstKrqdtOvertimeInputFilter);
			// get KrqdtAppOvertimeDetail
			Optional<KrqdtAppOvertimeDetail> optKrqdtAppOvertimeDetail = lstkrqdtAppOvertimeDetail.stream()
					.filter(x -> x.appOvertimeDetailPk.appId.equals(appId)).findFirst();
			KrqdtAppOvertimeDetail krqdtAppOvertimeDetail = null;

			if (optKrqdtAppOvertimeDetail.isPresent()) {
				krqdtAppOvertimeDetail = optKrqdtAppOvertimeDetail.get();
				// get list KrqdtYear36OverMonth
				List<KrqdtYear36OverMonth> lstKrqdtYear36OverMonthFilter = lstYear36OverMonth.stream()
						.filter(x -> x.year36OverMonthPk.appId.equals(appId)).collect(Collectors.toList());
				// get list KrqdtTime36UpLimitPerMonth
				List<KrqdtTime36UpLimitPerMonth> lstKrqdtTime36UpLimitPerMonthFilter = lstAverageTimeLst.stream()
						.filter(x -> x.pk.appID.equals(appId)).collect(Collectors.toList());
				// set KrqdtYear36OverMonth and KrqdtTime36UpLimitPerMonth for
				// krqdtAppOvertimeDetail
				krqdtAppOvertimeDetail.year36OverMonth = lstKrqdtYear36OverMonthFilter;
				krqdtAppOvertimeDetail.averageTimeLst = lstKrqdtTime36UpLimitPerMonthFilter;
			}
			// set krqdtAppOvertimeDetail for krqdtAppOvertime
			krqdtAppOvertime.setAppOvertimeDetail(krqdtAppOvertimeDetail);

			lstMap.put(appId, this.toDomainPlus(krqdtAppOvertime));
		});

		return lstMap;
	}

	@SneakyThrows
	private AppOverTime_Old toDomainPlus(KrqdtAppOvertime entity) {
		return new AppOverTime_Old(null, entity.getKrqdtAppOvertimePK().getCid(), entity.getKrqdtAppOvertimePK().getAppId(),
				EnumAdaptor.valueOf(entity.getOvertimeAtr(), OverTimeAtr.class),
				entity.overtimeInputs.stream().map(x -> x.toDomain()).collect(Collectors.toList()),
				new WorkTypeCode(entity.getWorkTypeCode()), new WorkTimeCode(entity.getSiftCode()),
				entity.getWorkClockFrom1(), entity.getWorkClockTo1(), entity.getWorkClockFrom2(),
				entity.getWorkClockTo2(), entity.getDivergenceReason(), entity.getFlexExcessTime(),
				entity.getOvertimeShiftNight(),
				entity.appOvertimeDetail == null ? Optional.empty() : Optional.of(entity.appOvertimeDetail.toDomain()));
	}

	private KrqdtYear36OverMonth createKrqdtYear36OverMonth(String cid, String appId, NtsResultRecord rs) {
		return new KrqdtYear36OverMonth(new KrqdtYear36OverMonthPk(cid, appId, rs.getInt("OVER_MONTH").intValue()));
	}

	private KrqdtTime36UpLimitPerMonth createKrqdtTime36UpLimitPerMonth(String cid, String appId, NtsResultRecord rs) {
		return new KrqdtTime36UpLimitPerMonth(
				new KrqdpTime36UpLimitPerMonthPK(cid, appId, rs.getInt("START_YM"), rs.getInt("END_YM")),
				rs.getInt("AVE_TIME"), rs.getInt("TOTAL_TIME"));
	}

	private KrqdtOvertimeInput createKrqdtOvertimeInput(String cid, String appId, NtsResultRecord rs) {
		return new KrqdtOvertimeInput(
				new KrqdtOvertimeInputPK(cid, appId, rs.getInt("ATTENDANCE_ID"), rs.getInt("FRAME_NO"),
						rs.getInt("TIME_ITEM_TYPE_ATR")),
				rs.getInt("START_TIME"), rs.getInt("END_TIME"), rs.getInt("APPLICATION_TIME_INPUT"));
	}

	private KrqdtAppOvertimeDetail createKrqdtAppOvertimeDetail(String cid, String appId, NtsResultRecord rs) {
		return new KrqdtAppOvertimeDetail(new KrqdtAppOvertimeDetailPk(cid, appId), rs.getInt("YEAR_MONTH"),
				rs.getInt("APPLICATION_TIME_DETAIL"), rs.getInt("ACTUAL_TIME"), rs.getInt("LIMIT_ALARM_TIME"),
				rs.getInt("LIMIT_ERROR_TIME"), rs.getInt("NUM_OF_YEAR36_OVER"), rs.getInt("EXCEPTION_LIMIT_ALARM_TIME"),
				rs.getInt("EXCEPTION_LIMIT_ERROR_TIME"), rs.getInt("ACTUAL_TIME_YEAR"), rs.getInt("LIMIT_TIME_YEAR"),
				rs.getInt("REG_APPLICATION_TIME"), rs.getInt("REG_ACTUAL_TIME"), rs.getInt("REG_LIMIT_TIME"),
				rs.getInt("REG_LIMIT_TIME_MULTI"));
	}

	private KrqdtAppOvertime createKrqdtAppOvertime(String cid, String appId, NtsResultRecord rs) {
		return new KrqdtAppOvertime(new KrqdtAppOvertimePK(cid, appId), rs.getInt("OVERTIME_ATR"),
				rs.getString("WORK_TYPE_CODE"), rs.getString("SIFT_CODE"), rs.getInt("WORK_CLOCK_FROM1"),
				rs.getInt("WORK_CLOCK_TO1"), rs.getInt("WORK_CLOCK_FROM2"), rs.getInt("WORK_CLOCK_TO2"),
				rs.getString("DIVERGENCE_REASON"), rs.getInt("FLEX_EXCESS_TIME"), rs.getInt("OVERTIME_SHIFT_NIGHT"));
	}
}
