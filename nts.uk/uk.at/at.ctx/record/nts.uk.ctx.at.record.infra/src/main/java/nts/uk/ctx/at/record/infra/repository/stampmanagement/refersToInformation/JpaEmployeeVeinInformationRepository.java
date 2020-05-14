package nts.uk.ctx.at.record.infra.repository.stampmanagement.refersToInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation.EmployeeVeinInformation;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation.EmployeeVeinInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation.FingerType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation.FingerVeininformation;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation.VeinContent;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.refersToInformation.KrmctFingerVeinInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaEmployeeVeinInformationRepository extends JpaRepository implements EmployeeVeinInformationRepository {

	/**
	 * 	[1] insert(社員の静脈情報)
	 */
	@Override
	public void insert(EmployeeVeinInformation domain) {
		this.commandProxy().insert(toEntity(domain));
	}
	
	/**
	 * 	[2] update(社員の静脈情報)
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
	 * 	[3] delete(社員の静脈情報)
	 */
	@Override
	public void delete(EmployeeVeinInformation domain) {
		this.commandProxy().remove(domain);
	}
	
	/**
	 * 	[4] get
	 */
	@Override
	public Optional<EmployeeVeinInformation> get(String employeeId) {
		Optional<KrmctFingerVeinInfo> entityOpt = this.queryProxy().find(employeeId,
				KrmctFingerVeinInfo.class);
		
		if (!entityOpt.isPresent()) {
			return Optional.empty();
		}
		
		EmployeeVeinInformation doamin = this.toDomain(entityOpt.get()); 
		
		return Optional.of(doamin);
	}

	/**
	 * 	[5] get
	 */
	@Override
	public Optional<EmployeeVeinInformation> get(List<String> employeeIds) {
		return null;
	}
	
	private KrmctFingerVeinInfo toEntity(EmployeeVeinInformation domain) {
		KrmctFingerVeinInfo krmctFingerVeinInfo = new KrmctFingerVeinInfo();
		
		krmctFingerVeinInfo.employeeId = domain.getSid();
		krmctFingerVeinInfo.fingerType = domain.getVeininformations().get(0).getFingerType().value;
//		krmctFingerVeinInfo.veinNo = domain.getVeininformations().get(0).get
		krmctFingerVeinInfo.veinDetail = domain.getVeininformations().get(0).getVeinInformation().v();
		
		return krmctFingerVeinInfo;
	}
	
	private EmployeeVeinInformation toDomain( KrmctFingerVeinInfo entity ) {
		List<FingerVeininformation> list = new ArrayList<>();
		
		list.add(new FingerVeininformation(EnumAdaptor.valueOf(entity.fingerType, FingerType.class), new VeinContent(entity.veinDetail)));
		
		return new EmployeeVeinInformation(AppContexts.user().companyId(), entity.employeeId, list);
	}

}
