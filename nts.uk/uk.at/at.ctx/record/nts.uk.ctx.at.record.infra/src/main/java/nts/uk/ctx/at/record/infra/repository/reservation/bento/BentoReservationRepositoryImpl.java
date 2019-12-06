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
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.infra.entity.reservation.bento.KrcdtReservation;
import nts.uk.ctx.at.record.infra.entity.reservation.bento.KrcdtReservationDetail;
import nts.uk.ctx.at.record.infra.entity.reservation.bento.KrcdtReservationDetailPK;
import nts.uk.ctx.at.record.infra.entity.reservation.bento.KrcdtReservationPK;

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
	
	private List<KrcdtReservation> toEntity(List<FullJoinBentoReservation> listFullJoin) {
		return listFullJoin.stream().collect(Collectors.groupingBy(FullJoinBentoReservation::getReservationID))
				.entrySet().stream().map(x -> {
					FullJoinBentoReservation first = x.getValue().get(0);
					KrcdtReservationPK pk = new KrcdtReservationPK(
							first.getCompanyID(), 
							first.getReservationID());
					String contractCD = first.getContractCD();
					GeneralDate date = first.getDate();
					Integer frameAtr = first.getFrameAtr();
					String cardNo = first.getCardNo();
					boolean ordered= first.isOrdered();
					List<KrcdtReservationDetail> reservationDetails = x.getValue().stream()
							.collect(Collectors.groupingBy(FullJoinBentoReservation::getFrameNo))
							.entrySet().stream().map(y -> {
								KrcdtReservationDetailPK detailPk = new KrcdtReservationDetailPK(
										y.getValue().get(0).getCompanyID(), 
										y.getValue().get(0).getReservationID(), 
										y.getValue().get(0).getFrameNo(), 
										y.getValue().get(0).getRegisterDate());
								Integer quantity = y.getValue().get(0).getQuantity();
								boolean autoReservation = y.getValue().get(0).isAutoReservation();
								return new KrcdtReservationDetail(detailPk, contractCD, quantity, autoReservation, null);
							}).collect(Collectors.toList());
					return new KrcdtReservation(pk, contractCD, date, frameAtr, cardNo, ordered, reservationDetails);
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
			List<BentoReservation> bentoReservationLst = toEntity(createFullJoinBentoReservation(rs))
					.stream().map(x -> x.toDomain()).collect(Collectors.toList());
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
		commandProxy().insert(KrcdtReservation.fromDomain(bentoReservation));
	}

	@Override
	public void delete(BentoReservation bentoReservation) {
		String query = FIND_BY_ID_DATE;
		query = query.replaceFirst("cardNo", bentoReservation.getRegisterInfor().getReservationCardNo());
		query = query.replaceFirst("date", bentoReservation.getReservationDate().getDate().toString());
		query = query.replaceFirst("frameAtr", String.valueOf(bentoReservation.getReservationDate().getClosingTimeFrame().value));
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			KrcdtReservation beforeKrcdtReservation = toEntity(createFullJoinBentoReservation(rs)).get(0);
			commandProxy().remove(beforeKrcdtReservation);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<BentoReservation> findList(ReservationRegisterInfo registerInfor, ReservationDate reservationDate) {
		String query = FIND_BY_ID_DATE;
		query = query.replaceFirst("cardNo", registerInfor.getReservationCardNo());
		query = query.replaceFirst("date", reservationDate.getDate().toString());
		query = query.replaceFirst("frameAtr", String.valueOf(reservationDate.getClosingTimeFrame().value));
		try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<BentoReservation> bentoReservationLst = toEntity(createFullJoinBentoReservation(rs))
					.stream().map(x -> x.toDomain()).collect(Collectors.toList());
			return bentoReservationLst;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
