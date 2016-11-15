package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.Date;
import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.personalcommute.PersonalCommuteFee;
import nts.uk.ctx.pr.proto.dom.paymentdata.personalcommute.PersonalCommuteValue;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PersonalCommuteFeeRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.personalcommute.PprmtPersonCommute;

public class JpaPersonalCommuteFeeRepository extends JpaRepository implements PersonalCommuteFeeRepository {

	@Override
	public List<PersonalCommuteFee> find(String companyCode, String personId, Date baseYM) {
		// TODO Auto-generated method stub
		return null;
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
		PersonalCommuteFee domain = PersonalCommuteFee.createFromJavaType(entity.commuNotaxLimitPriNo, entity.commuNotaxLimitPriNo,
				commute1, commute2, commute3, commute4, commute5, entity.pprmtPersonCommutePK.pId);
		entity.toDomain(domain);
		return domain;
	}

}
