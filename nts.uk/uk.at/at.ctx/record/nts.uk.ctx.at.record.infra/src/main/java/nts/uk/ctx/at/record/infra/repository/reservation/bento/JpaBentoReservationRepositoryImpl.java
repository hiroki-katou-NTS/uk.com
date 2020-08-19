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
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.infra.entity.reservation.bento.KrcdtReservation;
import nts.uk.ctx.at.record.infra.entity.reservation.bento.KrcdtReservationDetail;
import nts.uk.ctx.at.record.infra.entity.reservation.bento.KrcdtReservationDetailPK;
import nts.uk.ctx.at.record.infra.entity.reservation.bento.KrcdtReservationPK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBentoReservationRepositoryImpl extends JpaRepository implements BentoReservationRepository {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final String SELECT;
	
	private static final String FIND_BY_ID_DATE;
	
	private static final String FIND_ALL_BY_DATE;
	
	private static final String FIND_BY_ORDER_PERIOD_EMP;

	private static final String GET_RESERVATION_DETAIL_FROM_ORDER;

	private static final String FIND_ALL_RESERVATION_DETAIL;

	private static final String ACQUIRED_RESERVATION_DETAIL;

	private static final String GET_EMPLOYEE_NOT_ORDER;

	private static final String FIND_RESERVATION_INFOMATION;

	private static final String FIND_ALL_RESERVATION_OF_A_BENTO;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a.CID, a.RESERVATION_ID, a.CONTRACT_CD, a.RESERVATION_YMD, a.RESERVATION_FRAME, a.CARD_NO, a.ORDERED,");
		builderString.append("b.MANU_FRAME, b.REGIST_DATETIME, b.QUANTITY, b.AUTO_RESERVATION_ATR, a.WORK_LOCATION_CD ");
		builderString.append("FROM KRCDT_RESERVATION a LEFT JOIN KRCDT_RESERVATION_DETAIL b ON a.CID = b.CID AND a.RESERVATION_ID = b.RESERVATION_ID ");
		SELECT = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CARD_NO = 'cardNo' AND a.RESERVATION_YMD = 'date' AND a.RESERVATION_FRAME = frameAtr");
		FIND_BY_ID_DATE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CARD_NO = 'cardNo' AND a.RESERVATION_YMD = 'date'");
		FIND_ALL_BY_DATE = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE a.CARD_NO IN (cardLst) AND a.RESERVATION_YMD >= 'startDate' AND a.RESERVATION_YMD <= 'endDate' AND a.ORDERED IN (ordered)");
        FIND_BY_ORDER_PERIOD_EMP = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CARD_NO IN (cardLst) AND a.RESERVATION_YMD >= 'startDate' AND a.RESERVATION_YMD <= 'endDate'" +
				" AND a.ORDERED IN (ordered) AND a.RESERVATION_FRAME = 'closingTimeFrame' ");
        GET_RESERVATION_DETAIL_FROM_ORDER = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CARD_NO IN (cardLst) AND a.RESERVATION_YMD >= 'startDate' AND a.RESERVATION_YMD <= 'endDate'" +
				" AND a.RESERVATION_FRAME = 'closingTimeFrame' ");
		FIND_ALL_RESERVATION_DETAIL = builderString.toString();

        builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CARD_NO IN (cardLst) AND a.RESERVATION_YMD >= 'startDate' AND a.RESERVATION_YMD <= 'endDate'" +
				" AND a.RESERVATION_FRAME = 'closingTimeFrame' AND b.QUANTITY >= 2 ");
        ACQUIRED_RESERVATION_DETAIL = builderString.toString();

        builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CARD_NO IN (cardLst) AND a.RESERVATION_YMD = 'date' ");
		GET_EMPLOYEE_NOT_ORDER = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE a.CARD_NO IN (cardLst) AND a.RESERVATION_YMD = 'date' ");
        FIND_RESERVATION_INFOMATION = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append("WHERE a.CARD_NO IN (cardLst) AND a.RESERVATION_YMD >= 'startDate' AND a.RESERVATION_YMD <= 'endDate'" +
				" AND a.RESERVATION_FRAME = 'closingTimeFrame' AND b.pk.frameNo = frameNo ");
		FIND_ALL_RESERVATION_OF_A_BENTO = builderString.toString();
	}
	
	@AllArgsConstructor
	@Getter
	private class FullJoinBentoReservation {
		public String companyID;
		public String reservationID;
		public String contractCD;
		public GeneralDate date;
		public int frameAtr;
		public String cardNo;
		public boolean ordered;
		public String workLocationCode;
		public int frameNo;
		public GeneralDateTime registerDate;
		public int quantity;
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
					Integer.valueOf(rs.getString("ORDERED")) == 1 ? true : false,
					rs.getString("WORK_LOCATION_CD"),
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
					int frameAtr = first.getFrameAtr();
					String cardNo = first.getCardNo();
					boolean ordered= first.isOrdered();
					String workLocationCode = first.getWorkLocationCode();
					List<KrcdtReservationDetail> reservationDetails = x.getValue().stream()
							.collect(Collectors.groupingBy(FullJoinBentoReservation::getFrameNo))
							.entrySet().stream().map(y -> {
								KrcdtReservationDetailPK detailPk = new KrcdtReservationDetailPK(
										y.getValue().get(0).getCompanyID(), 
										y.getValue().get(0).getReservationID(), 
										y.getValue().get(0).getFrameNo(), 
										y.getValue().get(0).getRegisterDate());
								int quantity = y.getValue().get(0).getQuantity();
								boolean autoReservation = y.getValue().get(0).isAutoReservation();
								return new KrcdtReservationDetail(detailPk, contractCD, quantity, autoReservation ? 1 : 0, null);
							}).collect(Collectors.toList());
					return new KrcdtReservation(pk, contractCD, date, frameAtr, cardNo, ordered ? 1 : 0,workLocationCode, reservationDetails);
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
			
			commandProxy().remove(KrcdtReservation.class, beforeKrcdtReservation.pk);
			this.getEntityManager().flush();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<BentoReservation> findList(ReservationRegisterInfo registerInfor, ReservationDate reservationDate) {
		String query = FIND_ALL_BY_DATE;
		query = query.replaceFirst("cardNo", registerInfor.getReservationCardNo());
		query = query.replaceFirst("date", reservationDate.getDate().toString());
        return getBentoReservations(query);
    }

    @Override
    public List<BentoReservation> findByOrderedPeriodEmpLst(List<ReservationRegisterInfo> inforLst, DatePeriod period, boolean ordered) {
        String query = FIND_BY_ORDER_PERIOD_EMP;
        List<String> cardLst = inforLst.stream().map(x -> x.getReservationCardNo()).collect(Collectors.toList());
        String cardLstStr = "";
        if(CollectionUtil.isEmpty(inforLst)) cardLstStr = "''";
        else {
            for(String cardStr : cardLst) {
                cardLstStr += "'" + cardStr + "'";
                if(cardLst.indexOf(cardStr) < cardLst.size() - 1) {
                    cardLstStr += ",";
                }
            }
        }
        String orderedParam;
        if(ordered) orderedParam = "1";
        else orderedParam = "0,1";
        query = query.replaceFirst("cardLst", cardLstStr);
        query = query.replaceFirst("startDate", period.start().toString());
        query = query.replaceFirst("endDate", period.end().toString());
        query = query.replaceFirst("ordered", orderedParam);
        return getBentoReservations(query);
    }

	@Override
	public List<BentoReservation> getReservationDetailFromOrder(List<ReservationRegisterInfo> inforLst, DatePeriod period, ReservationClosingTimeFrame closingTimeFrame, boolean ordered, List<WorkLocationCode> workLocationCode) {
		String query = GET_RESERVATION_DETAIL_FROM_ORDER;
		List<String> cardLst = inforLst.stream().map(x -> x.getReservationCardNo()).collect(Collectors.toList());
		String cardLstStr = getString(inforLst, cardLst);
		String orderedParam;
		if(ordered) orderedParam = "1";
		else orderedParam = "0,1";

		query = handleQueryForWkLocationCD(workLocationCode, query);

		query = query.replaceFirst("cardLst", cardLstStr);
		query = query.replaceFirst("startDate", period.start().toString());
		query = query.replaceFirst("endDate", period.end().toString());
		query = query.replaceFirst("ordered", orderedParam);
		query = query.replaceFirst("closingTimeFrame", String.valueOf(closingTimeFrame));
		return getBentoReservations(query);
	}

	@Override
    public List<BentoReservation> getAllReservationDetail(
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			List<WorkLocationCode> workLocationCode) {
		return handleQuery(inforLst, period, closingTimeFrame, workLocationCode, FIND_ALL_RESERVATION_DETAIL);
    }

	@Override
    public List<BentoReservation> acquireReservationDetails(
			List<ReservationRegisterInfo> inforLst, DatePeriod period,ReservationClosingTimeFrame closingTimeFrame,
			List<WorkLocationCode> workLocationCode) {
		return handleQuery(inforLst, period, closingTimeFrame, workLocationCode, ACQUIRED_RESERVATION_DETAIL);
    }

	private List<BentoReservation> handleQuery(List<ReservationRegisterInfo> inforLst, DatePeriod period, ReservationClosingTimeFrame closingTimeFrame, List<WorkLocationCode> workLocationCode, String query) {
		List<String> cardLst = inforLst.stream().map(x -> x.getReservationCardNo()).collect(Collectors.toList());
		String cardLstStr = getString(inforLst, cardLst);
		query = handleQueryForWkLocationCD(workLocationCode,query);
		query = query.replaceFirst("cardLst", cardLstStr);
		query = query.replaceFirst("startDate", period.start().toString());
		query = query.replaceFirst("endDate", period.end().toString());
		query = query.replaceFirst("closingTimeFrame", String.valueOf(closingTimeFrame));
		return getBentoReservations(query);
	}

	private String handleQueryForWkLocationCD(List<WorkLocationCode> workLocationCodes, String query){
		if (CollectionUtil.isEmpty(workLocationCodes)) query += " AND a.WORK_LOCATION_CD = NULL ";
		else{
			List<String> workLst = workLocationCodes.stream().map(x -> x.v()).collect(Collectors.toList());
			String workLstStr = getStringWork(workLocationCodes, workLst);
			query += " AND a.WORK_LOCATION_CD IN (workLstStr) ";
			query = query.replaceFirst("workLocationCode", workLstStr);
		}
		return query;
	}

    @Override
    public List<BentoReservation> getEmployeeNotOrder(List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate) {
		String query = GET_EMPLOYEE_NOT_ORDER;
		List<String> cardLst = inforLst.stream().map(x -> x.getReservationCardNo()).collect(Collectors.toList());
		String cardLstStr = getString(inforLst, cardLst);

		query = query.replaceFirst("cardLst", cardLstStr);
		query = query.replaceFirst("date", reservationDate.getDate().toString());
		List<BentoReservation> bentoReservations = getBentoReservations(query);

		if (bentoReservations.size() <= 0)
			return inforLst.stream().map(x -> x.convertToBentoReservation(x,reservationDate)).collect(Collectors.toList());

		List<ReservationRegisterInfo> reservationRegisterInfos = inforLst.stream().filter(x -> bentoReservations.contains(x.getReservationCardNo())).collect(Collectors.toList());

        return reservationRegisterInfos.stream().map(x -> x.convertToBentoReservation(x,reservationDate)).collect(Collectors.toList());
    }

    @Override
    public List<BentoReservation> getReservationInformation(List<ReservationRegisterInfo> inforLst, ReservationDate reservationDate) {
		String query = FIND_RESERVATION_INFOMATION;
		List<String> cardLst = inforLst.stream().map(x -> x.getReservationCardNo()).collect(Collectors.toList());
		String cardLstStr = getString(inforLst, cardLst);
		query = query.replaceFirst("cardLst", cardLstStr);
		query = query.replaceFirst("date", reservationDate.getDate().toString());
		return getBentoReservations(query);
    }

    private List<BentoReservation> getBentoReservations(String query) {
        try (PreparedStatement stmt = this.connection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            List<BentoReservation> bentoReservationLst = toEntity(createFullJoinBentoReservation(rs))
                    .stream().map(x -> x.toDomain()).collect(Collectors.toList());
            return bentoReservationLst;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getString(List<ReservationRegisterInfo> inforLst, List<String> cardLst) {
        if(CollectionUtil.isEmpty(inforLst)) return "''";
        else return handleList(cardLst);
	}

	@Override
	public void update(BentoReservation bentoReservation) {
		commandProxy().update(KrcdtReservation.fromDomain(bentoReservation));
	}

	@Override
	@SneakyThrows
	public List<BentoReservation> getAllReservationOfBento(int frameNo,
														   List<ReservationRegisterInfo> inforLst, DatePeriod period, ReservationClosingTimeFrame closingTimeFrame,
														   List<WorkLocationCode> workLocationCode) {
		String query = FIND_ALL_RESERVATION_OF_A_BENTO;
		List<String> cardLst = inforLst.stream().map(x -> x.getReservationCardNo()).collect(Collectors.toList());
		String cardLstStr = getString(inforLst, cardLst);
		query = handleQueryForWkLocationCD(workLocationCode, query);
		query = query.replaceFirst("frameNo", String.valueOf(frameNo));
		query = query.replaceFirst("cardLst", cardLstStr);
		query = query.replaceFirst("startDate", period.start().toString());
		query = query.replaceFirst("endDate", period.end().toString());
		query = query.replaceFirst("closingTimeFrame", String.valueOf(closingTimeFrame));
		return getBentoReservations(query);
	}

	private String getStringWork(List<WorkLocationCode> workLocationCodes, List<String> workLst) {
        if(CollectionUtil.isEmpty(workLocationCodes)) return  "''";
        else return handleList(workLst);
    }

    private String handleList(List<String> list){
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < list.size()-1; ++i) {
			result.append("'");
			result.append(list.get(i));
			result.append("'");
			result.append(",");
		}
		result.append("'");
		result.append(list.get(list.size()-1));
		result.append("'");
		return result.toString();
	}

}
