package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingSendReservationRepository;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSendReservation;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSendReservationPK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimeRecordReqSettingSendReservationRepository extends JpaRepository
	   implements TimeRecordReqSettingSendReservationRepository {

	@Override
	public void insert(TimeRecordReqSetting reqSetting) {
		List<KrcmtTrSendReservation> reservationList = reqSetting.getBentoMenuFrameNumbers().stream()
				.map(f -> toEntity(reqSetting, f)).collect(Collectors.toList());

		this.commandProxy().insertAll(reservationList);
	}

	@Override
	public void update(TimeRecordReqSetting reqSetting) {

		List<KrcmtTrSendReservation> reservationList = reqSetting.getBentoMenuFrameNumbers().stream()
				.map(f -> toEntity(reqSetting, f)).collect(Collectors.toList());

		this.commandProxy().updateAll(reservationList);
	}

	@Override
	public void delete(TimeRecordReqSetting reqSetting) {
		String contractCode = reqSetting.getContractCode().v();
		String timeRecordCode = reqSetting.getTerminalCode().v();

		List<KrcmtTrSendReservationPK> reservationList = reqSetting.getBentoMenuFrameNumbers().stream()
				.map(f -> new KrcmtTrSendReservationPK(contractCode, timeRecordCode, f)).collect(Collectors.toList());

		this.commandProxy().removeAll(KrcmtTrSendReservation.class, reservationList);
		this.getEntityManager().flush();
		//	reservationList.forEach(e -> this.commandProxy().remove(KrcmtTrSendReservation.class, e));
	}

	private KrcmtTrSendReservation toEntity(TimeRecordReqSetting setting, int bentoFrameNumber) {
		return new KrcmtTrSendReservation(new KrcmtTrSendReservationPK(setting.getContractCode().v(),
				setting.getTerminalCode().v(), bentoFrameNumber));
	}
}
