package nts.uk.ctx.at.record.infra.repository.reservation.reservationsetting;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.ctx.at.record.infra.entity.reservation.reservationsetting.KrcmtReservationSetting;

@Stateless
public class JpaReservationSettingRepositoryImpl extends JpaRepository implements ReservationSettingRepository {

    @Override
    public Optional<ReservationSetting> findByCId(String companyId) {
        String QUERY_BY_ID = "SELECT s FROM KrcmtReservationSetting s WHERE s.companyID = :companyId";
        return this.queryProxy()
                .query(QUERY_BY_ID, KrcmtReservationSetting.class)
                .setParameter("companyId", companyId).getSingle(x -> KrcmtReservationSetting.toDomain(x));
    }

    @Override
    public void add(ReservationSetting bentoReservationSetting) {
        commandProxy().insert(KrcmtReservationSetting.fromDomain(bentoReservationSetting));

    }

    @Override
    public void update(ReservationSetting bentoReservationSetting) {
        commandProxy().update(KrcmtReservationSetting.fromDomain(bentoReservationSetting));
    }

	@Override
	public List<ReservationRecTimeZone> getReservationRecTimeZoneByOpDist(String companyID, int operationDistinction) {
		String QUERY_BY_ID = "SELECT s FROM KrcmtReservationSetting s WHERE s.companyID = :companyId AND s.operationDistinction = :operationDistinction";
		Optional<ReservationSetting> opReservationSetting = this.queryProxy()
                .query(QUERY_BY_ID, KrcmtReservationSetting.class)
                .setParameter("companyId", companyID)
                .setParameter("operationDistinction", operationDistinction)
                .getSingle(x -> KrcmtReservationSetting.toDomain(x));
		if(opReservationSetting.isPresent()) {
			return opReservationSetting.get().getReservationRecTimeZoneLst();
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public ReservationSetting getReservationSettingByOpDist(String companyID, int operationDistinction) {
		String QUERY_BY_ID = "SELECT s FROM KrcmtReservationSetting s WHERE s.companyID = :companyId AND s.operationDistinction = :operationDistinction";
		Optional<ReservationSetting> opReservationSetting = this.queryProxy()
                .query(QUERY_BY_ID, KrcmtReservationSetting.class)
                .setParameter("companyId", companyID)
                .setParameter("operationDistinction", operationDistinction)
                .getSingle(x -> KrcmtReservationSetting.toDomain(x));
		if(opReservationSetting.isPresent()) {
			return opReservationSetting.get();
		} else {
			return null;
		}
	}

	@Override
	public ReservationRecTimeZone getReservationSetByOpDistAndFrameNo(String companyID, int frameNo, int operationDistinction) {
		String QUERY_BY_ID = "SELECT s FROM KrcmtReservationSetting s WHERE s.companyID = :companyId AND s.operationDistinction = :operationDistinction";
		Optional<ReservationSetting> opReservationSetting = this.queryProxy()
                .query(QUERY_BY_ID, KrcmtReservationSetting.class)
                .setParameter("companyId", companyID)
                .setParameter("operationDistinction", operationDistinction)
                .getSingle(x -> KrcmtReservationSetting.toDomain(x));
		if(opReservationSetting.isPresent()) {
			return opReservationSetting.get().getReservationRecTimeZoneLst().stream().filter(x -> x.getFrameNo().value==frameNo).findAny().orElse(null);
		} else {
			return null;
		}
	}

}
