package nts.uk.ctx.at.record.infra.repository.stampmanagement.refersToInformation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.task.parallel.ParallelExceptions.Item;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.EmployeeVeinInformation;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.EmployeeVeinInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.FingerType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.FingerVeininformation;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.VeinContent;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.refersToInformation.KrmctFingerVeinInfo;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.refersToInformation.KrmctFingerVeinInfoPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaEmployeeVeinInformationRepository extends JpaRepository implements EmployeeVeinInformationRepository {
	
	private static final String SELECT_LIST_VEIN_INFORMATION = "SELECT r FROM KrmctFingerVeinInfo r WHERE r.pk.employeeId = :employeeId";

	/**
	 * [1] insert(社員の静脈情報)
	 */
	@Override
	public void insert(EmployeeVeinInformation domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	/**
	 * [2] update(社員の静脈情報)
	 */
	@Override
	public void update(EmployeeVeinInformation domain) {
		Optional<KrmctFingerVeinInfo> opt = this.queryProxy().find(domain.getSid(), KrmctFingerVeinInfo.class);

		if (!opt.isPresent()) {
			return;
		}

		KrmctFingerVeinInfo entity = opt.get();
		entity.update(domain);
		this.commandProxy().update(entity);
	}

	/**
	 * [3] delete(社員の静脈情報)
	 */
	@Override
	public void delete(EmployeeVeinInformation domain) {
		toEntity(domain)
			.stream()
			.forEach(entity -> {
				this.getEntityManager().remove(entity);
			});
	}

	/**
	 * [4] get
	 */
	@Override
	public Optional<EmployeeVeinInformation> get(String employeeId) {
		List<KrmctFingerVeinInfo> lstVeinInfo = this.queryProxy().query(SELECT_LIST_VEIN_INFORMATION, KrmctFingerVeinInfo.class)
				.getList();

		if (lstVeinInfo == null) {
			return Optional.empty();
		}

		return this.toDomain(lstVeinInfo);
	}

	/**
	 * [5] get
	 */
	@Override
	public List<EmployeeVeinInformation> get(List<String> employeeIds) {
		return this.queryProxy().query("SELECT fv FROM KrmctFingerVeinInfo fv WHERE fv.pk.employeeId in :employeeIds", KrmctFingerVeinInfo.class)
				.setParameter("employeeIds", employeeIds)
				.getList()
				.stream()
				.collect(Collectors.groupingBy(f -> f.pk.employeeId))
				.values()
				.stream()
				.map(m -> this.toDomain(m).orElse(null))
				.filter(f -> f != null)
				.collect(Collectors.toList());
	}

	private List<KrmctFingerVeinInfo> toEntity(EmployeeVeinInformation domain) {
		List<KrmctFingerVeinInfo> lst = new ArrayList<KrmctFingerVeinInfo>();

		domain.getVeininformations().stream().forEach(finger -> {
			int type = finger.getFingerType().value;
			String[] values = finger.getVeinInformation().getSubString();

			for (int i = 0; i < values.length; i++) {
				KrmctFingerVeinInfoPk key = new KrmctFingerVeinInfoPk(domain.getSid(), type, i + 1);
				KrmctFingerVeinInfo entity = new KrmctFingerVeinInfo(key, values[i]);

				lst.add(entity);
			}
		});

		return lst;
	}

	private Optional<EmployeeVeinInformation> toDomain(List<KrmctFingerVeinInfo> lstEntity) {
		if (lstEntity == null || lstEntity.size() == 0) {
			return Optional.ofNullable(null);
		}
		
		HashMap<Integer, FingerVeininformation> fingers = new HashMap<>();
		
		lstEntity
			.stream()
			.sorted(Comparator.comparing(KrmctFingerVeinInfo::getFingerType).thenComparing(KrmctFingerVeinInfo::getVeinNo))
			.forEach(record -> {
				FingerVeininformation finger = fingers.get(record.getFingerType());
				
				if(finger == null) {			
					finger =new FingerVeininformation(EnumAdaptor.valueOf(record.pk.fingerType, FingerType.class), new VeinContent(record.veinDetail));
					
					fingers.put(record.pk.fingerType, finger);
				} else {
					finger.concatVeinContent(record.veinDetail);
				}
			});
		
		KrmctFingerVeinInfo entity = lstEntity.get(0);

		return Optional.ofNullable(new EmployeeVeinInformation(AppContexts.user().companyCode(), entity.pk.employeeId, fingers.values().stream().collect(Collectors.toList())));
	}

}
