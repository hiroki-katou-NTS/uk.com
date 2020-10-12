package nts.uk.ctx.at.record.infra.repository.reservation.bentomenu;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenu;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenuPK;
import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBentoMenuRepositoryImpl extends JpaRepository implements BentoMenuRepository {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static final String SELECT;
	
	private static final String FIND_BENTO_MENU_DATE;
	
	private static final String FIND_BENTO_MENU_PERIOD;

	private static final String FIND_BENTO_MENU_BY_HISTID;

	private static final String FIND_BENTO_MENU_BY_ENDATE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append(" SELECT a.CID, a.HIST_ID, a.CONTRACT_CD, a.RESERVATION_FRAME1_NAME, a.RESERVATION_FRAME1_START_TIME, a.RESERVATION_FRAME1_END_TIME, ");
		builderString.append(" a.RESERVATION_FRAME2_NAME, a.RESERVATION_FRAME2_START_TIME, a.RESERVATION_FRAME2_END_TIME, b.START_YMD, b.END_YMD, ");
		builderString.append(" c.MENU_FRAME, c.BENTO_NAME, c.UNIT_NAME, c.PRICE1, c.PRICE2, c.RESERVATION1_ATR, c.RESERVATION2_ATR, c.WORK_LOCATION_CD ");
		builderString.append(" FROM KRCMT_BENTO_MENU a JOIN KRCMT_BENTO_MENU_HIST b ON a.HIST_ID = b.HIST_ID AND a.CID = b.CID ");
		builderString.append(" LEFT JOIN KRCMT_BENTO c ON a.HIST_ID = c.HIST_ID AND a.CID = c.CID ");
		SELECT = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE a.CID = 'companyID' AND b.START_YMD <= 'date' AND b.END_YMD >= 'date' ");
		FIND_BENTO_MENU_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE a.CID = 'companyID' AND b.START_YMD <= 'startDate' AND b.END_YMD >= 'endDate' ");
		FIND_BENTO_MENU_PERIOD = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE a.CID = 'companyID' AND a.HIST_ID = 'histId' ");
		FIND_BENTO_MENU_BY_HISTID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE a.CID = 'companyID' AND b.END_YMD = 'date' ");
		FIND_BENTO_MENU_BY_ENDATE = builderString.toString();
	}
	
	@AllArgsConstructor
	@Getter
	private class FullJoinBentoMenu {
		public String companyID;
		public String histID;
		public String contractCD;
		public String reservationFrameName1;
		public Integer reservationStartTime1;
		public int reservationEndTime1;
		public String reservationFrameName2;
		public Integer reservationStartTime2;
		public Integer reservationEndTime2;
		public GeneralDate startDate;
		public GeneralDate endDate;
		public Integer frameNo;
		public String bentoName;
		public String unitName;
		public int price1;
		public int price2;
		public boolean reservationAtr1;
		public boolean reservationAtr2;
		public String workLocationCode;

	}

	@SneakyThrows
	private List<FullJoinBentoMenu> createFullJoinBentoMenu(ResultSet rs){
		List<FullJoinBentoMenu> listFullData = new ArrayList<>();
		while (rs.next()) {
			listFullData.add(new FullJoinBentoMenu(
					rs.getString("CID"), 
					rs.getString("HIST_ID"),
					rs.getString("CONTRACT_CD"), 
					rs.getString("RESERVATION_FRAME1_NAME"), 
					Strings.isBlank(rs.getString("RESERVATION_FRAME1_START_TIME")) ? null : Integer.valueOf(rs.getString("RESERVATION_FRAME1_START_TIME")), 
					Integer.valueOf(rs.getString("RESERVATION_FRAME1_END_TIME")), 
					rs.getString("RESERVATION_FRAME2_NAME"), 
					Strings.isBlank(rs.getString("RESERVATION_FRAME2_START_TIME")) ? null :  Integer.valueOf(rs.getString("RESERVATION_FRAME2_START_TIME")),
					Strings.isBlank(rs.getString("RESERVATION_FRAME2_END_TIME")) ? null :  Integer.valueOf(rs.getString("RESERVATION_FRAME2_END_TIME")),
					GeneralDate.fromString(rs.getString("START_YMD"), DATE_FORMAT),
					GeneralDate.fromString(rs.getString("END_YMD"), DATE_FORMAT),
					Strings.isBlank(rs.getString("MENU_FRAME")) ? null : Integer.valueOf(rs.getString("MENU_FRAME")),
					rs.getString("BENTO_NAME"),
					rs.getString("UNIT_NAME"),
					Integer.valueOf(rs.getString("PRICE1")),
					Integer.valueOf(rs.getString("PRICE2")),
					"0".equals(rs.getString("RESERVATION1_ATR")) ? false : true,
				    "0".equals(rs.getString("RESERVATION2_ATR")) ? false : true,
					rs.getString("WORK_LOCATION_CD")));
		}
		return listFullData;
	}
	
	private List<BentoMenu> toDomain(List<FullJoinBentoMenu> listFullJoin) {
		return listFullJoin.stream().collect(Collectors.groupingBy(FullJoinBentoMenu::getHistID))
				.entrySet().stream().map(x -> {
					FullJoinBentoMenu first = x.getValue().get(0);
					String historyID = first.getHistID();
					List<Bento> bentos = x.getValue().stream()
							.collect(Collectors.groupingBy(FullJoinBentoMenu::getFrameNo))
							.entrySet().stream().map(y -> {
								int frameNo = y.getValue().get(0).getFrameNo();
								BentoName name = new BentoName(y.getValue().get(0).getBentoName()); 
								BentoAmount amount1 = new BentoAmount(y.getValue().get(0).getPrice1());
								BentoAmount amount2 = new BentoAmount(y.getValue().get(0).getPrice2());
								BentoReservationUnitName unit = new BentoReservationUnitName(y.getValue().get(0).getUnitName());
								boolean reservationTime1Atr = y.getValue().get(0).isReservationAtr1();
								boolean reservationTime2Atr = y.getValue().get(0).isReservationAtr2();
								String workLocationCodeStr = y.getValue().get(0).getWorkLocationCode();
								Optional<WorkLocationCode> workLocationCode = workLocationCodeStr == null ? Optional.empty()
																	: Optional.of(new WorkLocationCode(workLocationCodeStr));
								return new Bento(frameNo, name, amount1, amount2, unit, reservationTime1Atr, reservationTime2Atr, workLocationCode);
							}).collect(Collectors.toList());
					String reservationFrameName1 = first.getReservationFrameName1();
					Integer reservationStartTime1 = first.getReservationStartTime1();
					int reservationEndTime1 = first.getReservationEndTime1();
					String reservationFrameName2 = first.getReservationFrameName2();
					Integer reservationStartTime2 = first.getReservationStartTime2();
					Integer reservationEndTime2 = first.getReservationEndTime2();
					Optional<ReservationClosingTime> closingTime2 = Optional.empty();
					if(reservationEndTime2 != null) {
						closingTime2 = Optional.of(new ReservationClosingTime(
								new BentoReservationTimeName(reservationFrameName2), 
								new BentoReservationTime(reservationEndTime2), 
								reservationStartTime2==null ? Optional.empty() : Optional.of(new BentoReservationTime(reservationStartTime2))));
					}
					return new BentoMenu(
							historyID, 
							bentos, 
							new BentoReservationClosingTime(
									new ReservationClosingTime(
											new BentoReservationTimeName(reservationFrameName1), 
											new BentoReservationTime(reservationEndTime1), 
											reservationStartTime1==null ? Optional.empty() : Optional.of(new BentoReservationTime(reservationStartTime1))), 
									closingTime2));
				}).collect(Collectors.toList());
	}

	@Override
	@SneakyThrows
	public BentoMenu getBentoMenu(String companyID, GeneralDate date) {
		String query = FIND_BENTO_MENU_DATE;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("date", date.toString());
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(rs));
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
	public BentoMenu getBentoMenu(String companyID, GeneralDate date, Optional<WorkLocationCode> workLocationCode) {
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
			ResultSet rs = stmt.executeQuery();
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(rs));
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
			ResultSet rs = stmt.executeQuery();
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(rs));
			if (bentoMenuLst.isEmpty()) {
				return new ArrayList<>();
			}
			return bentoMenuLst.get(0).getMenu();
		}
	}

	@Override
	public List<BentoMenu> getBentoMenuPeriod(String companyID, DatePeriod period) {
		String query = FIND_BENTO_MENU_PERIOD;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("startDate", period.end().toString());
		query = query.replaceAll("endDate", period.start().toString());
		return getBentoMenus(query);
	}

	@Override
	public List<BentoMenu> getBentoMenu(String companyID, GeneralDate date, ReservationClosingTimeFrame reservationClosingTimeFrame) {
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
	public BentoMenu getBentoMenuByHistId(String companyID, String histId) {
		String query = FIND_BENTO_MENU_BY_HISTID;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceFirst("histId", histId);
		return getBentoMenu(query);
	}

	@Override
	public BentoMenu getBentoMenuByEndDate(String companyID, GeneralDate date) {
		String query = FIND_BENTO_MENU_BY_ENDATE;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceFirst("date", date.toString());
		return getBentoMenu(query);
	}

	private BentoMenu getBentoMenu(String query) {
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(rs));
			if(bentoMenuLst.isEmpty()){
				return null;
			}
			return bentoMenuLst.get(0);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	private List<BentoMenu> getBentoMenus(String query) {
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(rs));
			return bentoMenuLst;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void add(BentoMenu bentoMenu) {
		commandProxy().insert(KrcmtBentoMenu.fromDomain(bentoMenu));
	}

	@Override
	public void update(BentoMenu bentoMenu) {
		commandProxy().update(KrcmtBentoMenu.fromDomain(bentoMenu));
	}

	@Override
	public void delete(String companyId, String historyId) {
		this.commandProxy().remove(KrcmtBentoMenu.class, new KrcmtBentoMenuPK(companyId, historyId));
	}
}
