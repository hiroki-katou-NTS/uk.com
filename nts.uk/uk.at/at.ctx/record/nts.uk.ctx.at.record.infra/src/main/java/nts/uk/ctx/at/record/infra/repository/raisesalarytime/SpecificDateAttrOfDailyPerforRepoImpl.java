package nts.uk.ctx.at.record.infra.repository.raisesalarytime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrSheet;
import nts.uk.ctx.at.record.dom.raisesalarytime.enums.SpecificDateAttr;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.infra.entity.daily.specificdatetttr.KrcdtDaiSpeDayCla;
import nts.uk.ctx.at.record.infra.entity.daily.specificdatetttr.KrcdtDaiSpeDayClaPK;

@Stateless
public class SpecificDateAttrOfDailyPerforRepoImpl extends JpaRepository implements SpecificDateAttrOfDailyPerforRepo{

	@Override
	public Optional<SpecificDateAttrOfDailyPerfor> find(String employeeId, GeneralDate baseDate) {
		List<SpecificDateAttrSheet> shortTimeSheets = findEntities(employeeId, baseDate).getList(
				c -> new SpecificDateAttrSheet(new SpecificDateItemNo(c.krcdtDaiSpeDayClaPK.speDayItemNo),
						EnumAdaptor.valueOf(c.tobeSpeDay, SpecificDateAttr.class)));
		if (!shortTimeSheets.isEmpty()) {
			return Optional.of(new SpecificDateAttrOfDailyPerfor(employeeId, shortTimeSheets, baseDate));
		}
		return Optional.empty();
	}

	@Override
	public void update(SpecificDateAttrOfDailyPerfor domain) {
		List<KrcdtDaiSpeDayCla> entities = findEntities(domain.getEmployeeId(), domain.getYmd()).getList();
		domain.getSpecificDateAttrSheets().stream().forEach(c -> {
			KrcdtDaiSpeDayCla current = entities.stream()
					.filter(x -> x.krcdtDaiSpeDayClaPK.speDayItemNo == c.getSpecificDateItemNo().v())
					.findFirst().orElse(null);
			if(current != null){
				current.tobeSpeDay = c.getSpecificDateAttr().value;
			} else {
				entities.add(newEntities(domain.getEmployeeId(), domain.getYmd(), c));
			}
		});
		commandProxy().updateAll(entities);
	}

	@Override
	public void add(SpecificDateAttrOfDailyPerfor domain) {
		List<KrcdtDaiSpeDayCla> entities = domain.getSpecificDateAttrSheets().stream()
				.map(c -> newEntities(domain.getEmployeeId(), domain.getYmd(), c)).collect(Collectors.toList());
		commandProxy().insertAll(entities);
	}
	
	private KrcdtDaiSpeDayCla newEntities(String employeeId, GeneralDate ymd, SpecificDateAttrSheet c) {
		return new KrcdtDaiSpeDayCla(new KrcdtDaiSpeDayClaPK(employeeId, ymd, c.getSpecificDateItemNo().v()),
				c.getSpecificDateAttr().value);
	}

	private TypedQueryWrapper<KrcdtDaiSpeDayCla> findEntities(String employeeId, GeneralDate ymd) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT s FROM KrcdtDaiSpeDayCla s");
		query.append(" WHERE s.krcdtDaiSpeDayClaPK.sid = :employeeId");
		query.append(" AND s.krcdtDaiSpeDayClaPK.ymd = :ymd");
		query.append(" ORDER BY s.krcdtDaiSpeDayClaPK.speDayItemNo");
		return queryProxy().query(query.toString(), KrcdtDaiSpeDayCla.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd);
	}

}
