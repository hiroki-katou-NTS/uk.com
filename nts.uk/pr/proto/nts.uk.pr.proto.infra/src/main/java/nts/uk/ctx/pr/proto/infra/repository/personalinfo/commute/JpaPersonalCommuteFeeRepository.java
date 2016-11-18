package nts.uk.ctx.pr.proto.infra.repository.personalinfo.commute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFee;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFeeRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteValue;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.commute.PprmtPersonCommute;

@RequestScoped
public class JpaPersonalCommuteFeeRepository extends JpaRepository implements PersonalCommuteFeeRepository {

	private final String SELECT_BY_CCD_PID_STRYM_ENDYM = "SELECT c FROM PPRMT_PERSON_COMMUTE c WHERE c.CCD = :CCD and c.PID = :PID and c.STR_YM <= :BASEYM and c.END_YM >= :BASEYM";

	@Override
	public List<PersonalCommuteFee> findAll(String companyCode, List<String> personIdList, Date baseYM) {
		List<PersonalCommuteFee> lstPersonalCommuteFee = new ArrayList<>();
		for (int i = 0; i < personIdList.size(); i++) {
			Optional<PersonalCommuteFee> tmpPersonalCommuteFee = this
					.queryProxy().query(SELECT_BY_CCD_PID_STRYM_ENDYM, PprmtPersonCommute.class)
					.setParameter("CCD", companyCode).setParameter("PID", personIdList.get(i))
					.setParameter("BASEYM", baseYM).getSingle(c -> toDomain(c));
			if (tmpPersonalCommuteFee.isPresent()) {
				lstPersonalCommuteFee.add(tmpPersonalCommuteFee.get());
			}
		}
		return lstPersonalCommuteFee;
	}

	private static PersonalCommuteFee toDomain(PprmtPersonCommute entity) {
		PersonalCommuteValue commute1 = PersonalCommuteValue.createFromJavaType(entity.commuteCycle1,
				entity.commuAllowance1, entity.commuMeansAtr1, entity.payStartYm1, entity.useOrNot1);
		PersonalCommuteValue commute2 = PersonalCommuteValue.createFromJavaType(entity.commuteCycle2,
				entity.commuAllowance2, entity.commuMeansAtr2, entity.payStartYm2, entity.useOrNot2);
		PersonalCommuteValue commute3 = PersonalCommuteValue.createFromJavaType(entity.commuteCycle3,
				entity.commuAllowance3, entity.commuMeansAtr3, entity.payStartYm3, entity.useOrNot3);
		PersonalCommuteValue commute4 = PersonalCommuteValue.createFromJavaType(entity.commuteCycle4,
				entity.commuAllowance4, entity.commuMeansAtr4, entity.payStartYm4, entity.useOrNot4);
		PersonalCommuteValue commute5 = PersonalCommuteValue.createFromJavaType(entity.commuteCycle5,
				entity.commuAllowance5, entity.commuMeansAtr5, entity.payStartYm5, entity.useOrNot5);
		PersonalCommuteFee domain = PersonalCommuteFee.createFromJavaType(entity.commuNotaxLimitPriNo,
				entity.commuNotaxLimitPriNo, commute1, commute2, commute3, commute4, commute5,
				entity.pprmtPersonCommutePK.pId);
		entity.toDomain(domain);
		return domain;
	}

	@Override
	public Optional<PersonalCommuteFee> find(String companyCode, String personId, int startYearMonth) {
		// TODO Auto-generated method stub
		return null;
	}

}
