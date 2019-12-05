package nts.uk.ctx.at.record.infra.repository.reservation.bento;

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
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReservationRepositoryImpl extends JpaRepository implements BentoReservationRepository {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final String SELECT;
	
	private static final String FIND_BY_ID_DATE;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a.CID, a.RESERVATION_ID, a.CONTRACT_CD, a.RESERVATION_YMD, a.RESERVATION_FRAME, a.CARD_NO, a.ORDERED,");
		builderString.append("b.MANU_FRAME, b.REGIST_DATETIME, b.QUANTITY, b.AUTO_RESERVATION_ATR");
		builderString.append("FROM KRCDT_RESERVATION a LEFT JOIN KRCDT_RESERVATION_DETAIL b ON a.CID = b.CID AND a.RESERVATION_ID = b.RESERVATION_ID ");
		SELECT = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CARD_NO = 'cardNo' AND a.RESERVATION_YMD = 'date' AND a.RESERVATION_FRAME = frameAtr");
		FIND_BY_ID_DATE = builderString.toString();
	}
	
	@AllArgsConstructor
	@Getter
	private class FullJoinBentoReservation {
		public String companyID;
		public String reservationID;
		public String contractCD;
		public GeneralDate date;
		public Integer frameAtr;
		public String cardNo;
		public boolean ordered;
		public Integer frameNo;
		public GeneralDateTime registerDate;
		public Integer quantity;
		public boolean autoReservation;
	}
	
	@SneakyThrows
	private List<FullJoinBentoReservation> createFullJoinBentoReservation(ResultSet rs){
		List<FullJoinBentoReservation> listFullData = new ArrayList<>();
		while (rs.next()) {
			listFullData.add(new FullJoinBentoReservation(
					rs.getString("CID"), 
					rs.getString("RESERVATION_ID"), 
					rs.getString("CONTRACT_CD"), 
					GeneralDate.fromString(rs.getString("RESERVATION_YMD"), DATE_FORMAT), 
					Integer.valueOf(rs.getString("RESERVATION_FRAME")), 
					rs.getString("CARD_NO"), 
					Boolean.valueOf(rs.getString("ORDERED")), 
					Integer.valueOf(rs.getString("MANU_FRAME")), 
					GeneralDateTime.fromString(rs.getString("REGIST_DATETIME"), DATE_TIME_FORMAT), 
					Integer.valueOf(rs.getString("QUANTITY")), 
					Boolean.valueOf(rs.getString("AUTO_RESERVATION_ATR"))));
		}
		return listFullData;
	}
	
	private List<BentoReservation> toDomain(List<FullJoinBentoReservation> listFullJoin) {
		return listFullJoin.stream().collect(Collectors.groupingBy(FullJoinBentoReservation::getReservationID))
				.entrySet().stream().map(x -> {
					FullJoinBentoReservation first = x.getValue().get(0);
					String cardNo = first.getCardNo();
					GeneralDate date = first.getDate();
					ReservationClosingTimeFrame closingTimeFrame = EnumAdaptor.valueOf(first.getFrameAtr(), ReservationClosingTimeFrame.class);
					boolean ordered = first.isOrdered();
					List<BentoReservationDetail> bentoReservationDetails = x.getValue().stream()
							.collect(Collectors.groupingBy(FullJoinBentoReservation::getFrameNo))
							.entrySet().stream().map(y -> {
								Integer frameNo = y.getValue().get(0).getFrameNo();
								GeneralDateTime dateTime = y.getValue().get(0).getRegisterDate();
								boolean autoReservation = y.getValue().get(0).isAutoReservation();
								BentoReservationCount bentoCount = new BentoReservationCount(y.getValue().get(0).getQuantity());
								return new BentoReservationDetail(frameNo, dateTime, autoReservation, bentoCount);
							}).collect(Collectors.toList());
					return new BentoReservation(
							new ReservationRegisterInfo(cardNo), 
							new ReservationDate(date, closingTimeFrame), 
							ordered, 
							bentoReservationDetails);
				}).collect(Collectors.toList());
	}
	
	@Override
	@SneakyThrows
	public Optional<BentoReservation> find(ReservationRegisterInfo registerInfor, ReservationDate reservationDate) {	
		String query = FIND_BY_ID_DATE;
		query = query.replaceFirst("cardNo", registerInfor.getReservationCardNo());
		query = query.replaceFirst("date", reservationDate.getDate().toString());
		query = query.replaceFirst("frameAtr", String.valueOf(reservationDate.getClosingTimeFrame().value));
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<BentoReservation> bentoReservationLst = toDomain(createFullJoinBentoReservation(rs));
			if(CollectionUtil.isEmpty(bentoReservationLst)) {
				return Optional.empty();
			} else {
				return Optional.of(bentoReservationLst.get(0));
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void add(BentoReservation bentoReservation) {
		commandProxy().insert(bentoReservation);
	}

	@Override
	public void delete(BentoReservation bentoReservation) {
		commandProxy().remove(bentoReservation);
	}
}
