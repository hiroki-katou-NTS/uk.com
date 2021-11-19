package nts.uk.ctx.at.record.infra.repository.reservation.bentomenu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoAmount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoReservationUnitName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenu;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenuPK;

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
								ReservationClosingTimeFrame receptionTimezoneNo = EnumAdaptor.valueOf(y.getValue().get(0).getReceptionTimezoneNo(), ReservationClosingTimeFrame.class);
								String workLocationCodeStr = y.getValue().get(0).getWorkLocationCode();
								Optional<WorkLocationCode> workLocationCode = workLocationCodeStr == null ? Optional.empty()
																	: Optional.of(new WorkLocationCode(workLocationCodeStr));
								return new Bento(frameNo, name, amount1, amount2, unit, receptionTimezoneNo, workLocationCode);
							}).collect(Collectors.toList());
					return new BentoMenu(
							historyID, 
							bentos);
				}).collect(Collectors.toList());
	}

	@Override
	@SneakyThrows
	public BentoMenu getBentoMenu(String companyID, GeneralDate date) {
		String query = FIND_BENTO_MENU_DATE;
		query = query.replaceFirst("companyID", companyID);
		query = query.replaceAll("date", date.toString());
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
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
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
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
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
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
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
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
			List<BentoMenu> bentoMenuLst = toDomain(createFullJoinBentoMenu(new NtsResultSet(stmt.executeQuery())));
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
