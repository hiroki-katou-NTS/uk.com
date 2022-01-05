package nts.uk.ctx.at.record.infra.repository.reservation.bentomenu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBento;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenuHist;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenuHistPK;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoPK;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBentoMenuHistRepositoryImpl extends JpaRepository implements BentoMenuHistRepository {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static final String SELECT;
	
	private static final String FIND_BENTO_MENU_DATE;
	
	private static final String FIND_BENTO_MENU_PERIOD;

	private static final String FIND_BENTO_MENU_BY_HISTID;

	private static final String FIND_BENTO_MENU_BY_ENDATE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append(" SELECT a.CONTRACT_CD, a.CID, a.HIST_ID, a.START_YMD, a.END_YMD, ");
		builderString.append(" c.MENU_FRAME, c.WORK_LOCATION_CD, c.BENTO_NAME, c.UNIT_NAME, c.PRICE1, c.PRICE2, c.FRAME_NO ");
		builderString.append(" FROM KRCMT_BENTO_MENU_HIST a LEFT JOIN KRCMT_BENTO c ON a.HIST_ID = c.HIST_ID AND a.CID = c.CID");
		SELECT = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE a.CID = 'companyID' AND a.START_YMD <= 'date' AND a.END_YMD >= 'date' ");
		FIND_BENTO_MENU_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE a.CID = 'companyID' AND a.START_YMD <= 'endDate' AND a.END_YMD >= 'startDate' ");
		FIND_BENTO_MENU_PERIOD = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE a.CID = 'companyID' AND a.HIST_ID = 'histId' ");
		FIND_BENTO_MENU_BY_HISTID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE a.CID = 'companyID' AND a.END_YMD = 'date' ");
		FIND_BENTO_MENU_BY_ENDATE = builderString.toString();
	}
	
	@AllArgsConstructor
	@Getter
	private class FullJoinBentoMenu {
		public String companyID;
		public String histID;
		public String contractCD;
		public GeneralDate startDate;
		public GeneralDate endDate;
		public Integer frameNo;
		public String bentoName;
		public String unitName;
		public int price1;
		public int price2;
		public String workLocationCode;
		public int receptionTimezoneNo;
	}

	@SneakyThrows
	private List<FullJoinBentoMenu> createFullJoinBentoMenu(NtsResultSet rs){
		List<FullJoinBentoMenu> listFullData = rs.getList(x -> new FullJoinBentoMenu(
				x.getString("CID"), 
				x.getString("HIST_ID"),
				x.getString("CONTRACT_CD"), 
				x.getGeneralDate("START_YMD"),
				x.getGeneralDate("END_YMD"),
				x.getInt("MENU_FRAME"),
				x.getString("BENTO_NAME"),
				x.getString("UNIT_NAME"),
				x.getInt("PRICE1"),
				x.getInt("PRICE2"),
				x.getString("WORK_LOCATION_CD"),
				x.getInt("FRAME_NO")));
		return listFullData;
	}
	
	@SneakyThrows
	private List<FullJoinBentoMenu> createFullJoinBentoMenuBasic(ResultSet rs){
		List<FullJoinBentoMenu> listFullData = new ArrayList<>();
		while (rs.next()) {
			listFullData.add(new FullJoinBentoMenu(
					rs.getString("CID"), 
					rs.getString("HIST_ID"),
					rs.getString("CONTRACT_CD"), 
					GeneralDate.fromString(rs.getString("START_YMD"), "yyyy/MM/dd"),
					GeneralDate.fromString(rs.getString("END_YMD"), "yyyy/MM/dd"),
					rs.getInt("MENU_FRAME"),
					rs.getString("BENTO_NAME"),
					rs.getString("UNIT_NAME"),
					rs.getInt("PRICE1"),
					rs.getInt("PRICE2"),
					rs.getString("WORK_LOCATION_CD"),
					rs.getInt("FRAME_NO")));
		}
		return listFullData;
	}
	
	private List<BentoMenuHistory> toDomain(List<FullJoinBentoMenu> listFullJoin) {
		return listFullJoin.stream().collect(Collectors.groupingBy(FullJoinBentoMenu::getHistID))
				.entrySet().stream().map(x -> {
					FullJoinBentoMenu first = x.getValue().get(0);
					String historyID = first.getHistID();
					GeneralDate startDate = first.getStartDate();
					GeneralDate endDate = first.getEndDate();
					List<Bento> bentos = x.getValue().stream()
							.collect(Collectors.groupingBy(FullJoinBentoMenu::getFrameNo))
							.entrySet().stream().map(y -> {
								int frameNo = y.getValue().get(0).getFrameNo();
								BentoName name = new BentoName(y.getValue().get(0).getBentoName()); 
								BentoAmount amount1 = new BentoAmount(y.getValue().get(0).getPrice1());
								BentoAmount amount2 = new BentoAmount(y.getValue().get(0).getPrice2());
								BentoReservationUnitName unit = new BentoReservationUnitName(y.getValue().get(0).getUnitName());
								ReservationClosingTimeFrame receptionTimezoneNo = EnumAdaptor.valueOf(y.getValue().get(0).getReceptionTimezoneNo(), ReservationClosingTimeFrame.class);
								String workLocationCodeStr = y.getValue().get(0).getWorkLocationCode();
								Optional<WorkLocationCode> workLocationCode = workLocationCodeStr == null ? Optional.empty()
																	: Optional.of(new WorkLocationCode(workLocationCodeStr));
								return new Bento(frameNo, name, amount1, amount2, unit, receptionTimezoneNo, workLocationCode);
							}).collect(Collectors.toList());
					return new BentoMenuHistory(
							historyID, 
							new DateHistoryItem(historyID, new DatePeriod(startDate, endDate)),
							bentos);
				}).collect(Collectors.toList());
	}

	@Override
	@SneakyThrows
	public BentoMenuHistory getBentoMenu(String companyID, GeneralDate date) {
		String query = FIND_BENTO_MENU_DATE;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("date", date.toString());
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			List<BentoMenuHistory> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
			if (CollectionUtil.isEmpty(bentoMenuLst)){
				return  null;
			}
			return bentoMenuLst.get(0);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	@SneakyThrows
	public BentoMenuHistory getBentoMenu(String companyID, GeneralDate date, Optional<WorkLocationCode> workLocationCode) {
		String query = FIND_BENTO_MENU_DATE;

		if (workLocationCode.isPresent()){
			query += " AND c.WORK_LOCATION_CD = 'workLocationCode' ";
			query = query.replaceFirst("workLocationCode", String.valueOf(workLocationCode.get()));
		}

		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("date", date.toString());

		return getBentoMenu(query);
	}

	@Override
	@SneakyThrows
	public Bento getBento(String companyID, GeneralDate date, int frameNo) {
		String query = FIND_BENTO_MENU_DATE;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("date", date.toString());
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			List<BentoMenuHistory> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
			if (CollectionUtil.isEmpty(bentoMenuLst)) return null;
			return bentoMenuLst.get(0).getMenu().stream()
					.filter(x -> x.getFrameNo()==frameNo).findAny().orElse(null);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	@SneakyThrows
	public List<Bento> getBento(String companyID, GeneralDate date) {
		String query = FIND_BENTO_MENU_DATE;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("date", date.toString());
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			List<BentoMenuHistory> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
			if (bentoMenuLst.isEmpty()) {
				return new ArrayList<>();
			}
			return bentoMenuLst.get(0).getMenu();
		}
	}

	@Override
	public List<BentoMenuHistory> getBentoMenuPeriod(String companyID, DatePeriod period) {
		String query = FIND_BENTO_MENU_PERIOD;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("startDate", period.end().toString());
		query = query.replaceAll("endDate", period.start().toString());
		return getBentoMenus(query);
	}

	@Override
	public List<BentoMenuHistory> getBentoMenu(String companyID, GeneralDate date, ReservationClosingTimeFrame reservationClosingTimeFrame) {
		String query = FIND_BENTO_MENU_DATE;

		if (reservationClosingTimeFrame == ReservationClosingTimeFrame.FRAME1) {
			query += " AND c.RESERVATION1_ATR = 1 ";
		} else {
			query += " AND c.RESERVATION2_ATR = 1 ";
		}

		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("date", date.toString());
		return getBentoMenus(query);
	}

	@Override
	public BentoMenuHistory getBentoMenuByHistId(String companyID, String histId) {
		String query = FIND_BENTO_MENU_BY_HISTID;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceFirst("histId", histId);
		return getBentoMenu(query);
	}

	@Override
	public BentoMenuHistory getBentoMenuByEndDate(String companyID, GeneralDate date) {
		String query = FIND_BENTO_MENU_BY_ENDATE;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceFirst("date", date.toString());
		return getBentoMenu(query);
	}

	private BentoMenuHistory getBentoMenu(String query) {
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<BentoMenuHistory> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
			if(bentoMenuLst.isEmpty()){
				return null;
			}
			return bentoMenuLst.get(0);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private List<BentoMenuHistory> getBentoMenus(String query) {
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<BentoMenuHistory> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
			return bentoMenuLst;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void delete(String companyId, String historyId) {
		commandProxy().remove(KrcmtBentoMenuHist.class, new KrcmtBentoMenuHistPK(companyId, historyId));
	}
	
	@Override
	public Optional<BentoMenuHistory> findByCompanyDate(String companyID, GeneralDate date) {
		String query =
				"SELECT a.CID, a.HIST_ID, a.START_YMD, a.END_YMD, c.MENU_FRAME, c.WORK_LOCATION_CD, c.BENTO_NAME, c.UNIT_NAME, c.PRICE1, c.PRICE2, c.FRAME_NO " + 
				"FROM KRCMT_BENTO_MENU_HIST a LEFT JOIN KRCMT_BENTO c ON a.HIST_ID = c.HIST_ID AND a.CID = c.CID " + 
				"WHERE a.CID = @companyID AND a.START_YMD <= @date AND a.END_YMD >= @date";
		List<Map<String, Object>> mapLst = new NtsStatement(query, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramDate("date", date)
				.getList(rec -> toObject(rec));
		List<KrcmtBentoMenuHist> krcmtBentoMenuHistLst = convertToEntity(mapLst);
		if(CollectionUtil.isEmpty(krcmtBentoMenuHistLst)) {
			return Optional.empty();
		} else {
			return krcmtBentoMenuHistLst.stream().findFirst().map(x -> x.toDomain());
		}
	}
	
	private Map<String, Object> toObject(NtsResultRecord rec) {
		Map<String, Object> map = new HashMap<String, Object>();
		// KRCMT_BENTO_MENU_HIST
		map.put("CID", rec.getString("CID"));
		map.put("HIST_ID", rec.getString("HIST_ID"));
		map.put("START_YMD", rec.getGeneralDate("START_YMD"));
		map.put("END_YMD", rec.getGeneralDate("END_YMD"));
		// KRCMT_BENTO
		map.put("MENU_FRAME", rec.getInt("MENU_FRAME"));
		map.put("WORK_LOCATION_CD", rec.getString("WORK_LOCATION_CD"));
		map.put("BENTO_NAME", rec.getString("BENTO_NAME"));
		map.put("UNIT_NAME", rec.getString("UNIT_NAME"));
		map.put("PRICE1", rec.getInt("PRICE1"));
		map.put("PRICE2", rec.getInt("PRICE2"));
		map.put("FRAME_NO", rec.getInt("FRAME_NO"));
		return map;
	}
	
	private List<KrcmtBentoMenuHist> convertToEntity(List<Map<String, Object>> mapLst) {
		List<KrcmtBentoMenuHist> result = mapLst.stream().collect(Collectors.groupingBy(x -> x.get("HIST_ID"))).entrySet()
			.stream().map(x -> {
				List<KrcmtBento> krcmtBentoLst = Collections.emptyList();
				if(x.getValue().get(0).get("MENU_FRAME")!=null) {
					krcmtBentoLst = x.getValue().stream().collect(Collectors.groupingBy(y -> y.get("MENU_FRAME"))).entrySet()
							.stream().map(y -> {
								return new KrcmtBento(
										new KrcmtBentoPK(
												(String) y.getValue().get(0).get("CID"), 
												(String) y.getValue().get(0).get("HIST_ID"), 
												(int) y.getValue().get(0).get("MENU_FRAME")), 
										(String) y.getValue().get(0).get("BENTO_NAME"), 
										(String) y.getValue().get(0).get("UNIT_NAME"), 
										(int) y.getValue().get(0).get("PRICE1"), 
										(int) y.getValue().get(0).get("PRICE2"), 
										(String) y.getValue().get(0).get("WORK_LOCATION_CD"), 
										(int) y.getValue().get(0).get("FRAME_NO"), 
										null);
							}).collect(Collectors.toList());
				}
				return new KrcmtBentoMenuHist(
						new KrcmtBentoMenuHistPK(
								(String) x.getValue().get(0).get("CID"), 
								(String) x.getValue().get(0).get("HIST_ID")), 
						(GeneralDate) x.getValue().get(0).get("START_YMD"), 
						(GeneralDate) x.getValue().get(0).get("END_YMD"), 
						krcmtBentoLst);
			}).collect(Collectors.toList());
		return result;
	}

	@Override
	public List<Bento> getBento(String companyID, GeneralDate date, Optional<WorkLocationCode> workLocationCode) {
		String query =
				"SELECT c.CID, c.HIST_ID, c.MENU_FRAME, c.WORK_LOCATION_CD, c.BENTO_NAME, c.UNIT_NAME, c.PRICE1, c.PRICE2, c.FRAME_NO " + 
				"FROM KRCMT_BENTO_MENU_HIST a LEFT JOIN KRCMT_BENTO c ON a.HIST_ID = c.HIST_ID AND a.CID = c.CID " + 
				"WHERE c.CID = @companyID AND a.START_YMD <= @date AND a.END_YMD >= @date";
		return new NtsStatement(query, this.jdbcProxy())
        .paramString("companyID", companyID)
        .paramDate("date", date)
        .getList(x -> {
        	return new Bento(
        			x.getInt("MENU_FRAME"), 
        			new BentoName(x.getString("BENTO_NAME")), 
        			new BentoAmount(x.getInt("PRICE1")), 
        			new BentoAmount(x.getInt("PRICE2")), 
        			new BentoReservationUnitName(x.getString("UNIT_NAME")), 
        			EnumAdaptor.valueOf(x.getInt("FRAME_NO"), ReservationClosingTimeFrame.class), 
        			Optional.empty());
        });
	}
	
	@Override
	public void update(BentoMenuHistory bentoMenu) {
		commandProxy().update(KrcmtBentoMenuHist.fromDomain(bentoMenu));
	}
	
	@Override
	public void updateLst(List<BentoMenuHistory> updateBentoMenuHistoryLst) {
		commandProxy().updateAll(updateBentoMenuHistoryLst.stream().map(x -> KrcmtBentoMenuHist.fromDomain(x)).collect(Collectors.toList()));
	}
	
	@Override
	public void add(BentoMenuHistory bentoMenu) {
		commandProxy().insert(KrcmtBentoMenuHist.fromDomain(bentoMenu));
	}
	
	@Override
	public Optional<BentoMenuHistory> findByHistoryID(String historyID) {
		String query =
				"SELECT a.CID, a.HIST_ID, a.START_YMD, a.END_YMD, c.MENU_FRAME, c.WORK_LOCATION_CD, c.BENTO_NAME, c.UNIT_NAME, c.PRICE1, c.PRICE2, c.FRAME_NO " + 
				"FROM KRCMT_BENTO_MENU_HIST a LEFT JOIN KRCMT_BENTO c ON a.HIST_ID = c.HIST_ID AND a.CID = c.CID " + 
				"WHERE a.HIST_ID = @historyID";
		List<Map<String, Object>> mapLst = new NtsStatement(query, this.jdbcProxy())
				.paramString("historyID", historyID)
				.getList(rec -> toObject(rec));
		List<KrcmtBentoMenuHist> krcmtBentoMenuHistLst = convertToEntity(mapLst);
		if(CollectionUtil.isEmpty(krcmtBentoMenuHistLst)) {
			return Optional.empty();
		} else {
			return krcmtBentoMenuHistLst.stream().findFirst().map(x -> x.toDomain());
		}
	}
	
	@Override
	public List<BentoMenuHistory> findByCompanyPeriod(String companyID, DatePeriod period) {
		String query =
				"SELECT a.CID, a.HIST_ID, a.START_YMD, a.END_YMD, c.MENU_FRAME, c.WORK_LOCATION_CD, c.BENTO_NAME, c.UNIT_NAME, c.PRICE1, c.PRICE2, c.FRAME_NO " + 
				"FROM KRCMT_BENTO_MENU_HIST a LEFT JOIN KRCMT_BENTO c ON a.HIST_ID = c.HIST_ID AND a.CID = c.CID " + 
				"WHERE a.CID = @companyID AND a.START_YMD <= @endDate AND a.END_YMD >= @startDate";
		List<Map<String, Object>> mapLst = new NtsStatement(query, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramDate("startDate", period.start())
				.paramDate("endDate", period.end())
				.getList(rec -> toObject(rec));
		List<KrcmtBentoMenuHist> krcmtBentoMenuHistLst = convertToEntity(mapLst);
		return krcmtBentoMenuHistLst.stream().map(x -> x.toDomain()).collect(Collectors.toList());
	}

	@Override
	public List<BentoMenuHistory> findByCompany(String companyID) {
		String query =
				"SELECT a.CID, a.HIST_ID, a.START_YMD, a.END_YMD, c.MENU_FRAME, c.WORK_LOCATION_CD, c.BENTO_NAME, c.UNIT_NAME, c.PRICE1, c.PRICE2, c.FRAME_NO " + 
				"FROM KRCMT_BENTO_MENU_HIST a LEFT JOIN KRCMT_BENTO c ON a.HIST_ID = c.HIST_ID AND a.CID = c.CID " + 
				"WHERE a.CID = @companyID";
		List<Map<String, Object>> mapLst = new NtsStatement(query, this.jdbcProxy())
				.paramString("companyID", companyID)
				.getList(rec -> toObject(rec));
		List<KrcmtBentoMenuHist> krcmtBentoMenuHistLst = convertToEntity(mapLst);
		return krcmtBentoMenuHistLst.stream().map(x -> x.toDomain()).collect(Collectors.toList());
	}

}
