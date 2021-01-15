package nts.uk.ctx.at.shared.infra.repository.calculation.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOT;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOTRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.setting.KshstDefLaborOtCalc;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaDeformLaborOTRepository extends JpaRepository implements DeformLaborOTRepository {
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT k");
		builderString.append(" FROM KshstDefLaborOtCalc k");
		builderString.append(" WHERE k.cid = :companyId");
		SELECT_BY_CID = builderString.toString();
	}
	
	private DeformLaborOT convertToDomain(KshstDefLaborOtCalc kshstDefLaborOtCalc) {
		DeformLaborOT deformLaborOT = DeformLaborOT.createFromJavaType(kshstDefLaborOtCalc.getCid(), kshstDefLaborOtCalc.getLegalOtCalc());
		
		return deformLaborOT;
	}
	
	private KshstDefLaborOtCalc convertToDbType(DeformLaborOT deformLaborOT){
		KshstDefLaborOtCalc kshstDefLaborOtCalc = new KshstDefLaborOtCalc();
		kshstDefLaborOtCalc.setCid(deformLaborOT.getCid());
		kshstDefLaborOtCalc.setLegalOtCalc(deformLaborOT.getLegalOtCalc().value);
		return kshstDefLaborOtCalc;
	}

	@Override
	public List<DeformLaborOT> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstDefLaborOtCalc.class).setParameter("companyId", companyId).getList(d -> convertToDomain(d));
	}

	@Override
	public void add(DeformLaborOT deformLaborOT) {
		this.commandProxy().insert(convertToDbType(deformLaborOT));
	}

	@Override
	public void update(DeformLaborOT deformLaborOT) {
		KshstDefLaborOtCalc kshstDefLaborOtCalc = this.queryProxy().find(deformLaborOT.getCid(), KshstDefLaborOtCalc.class).get();
		kshstDefLaborOtCalc.setLegalOtCalc(deformLaborOT.getLegalOtCalc().value);
		this.commandProxy().update(kshstDefLaborOtCalc);
	}

	@Override
	public Optional<DeformLaborOT> findByCId(String companyId) {
		return this.queryProxy().find(companyId,KshstDefLaborOtCalc.class)
				.map(c->convertToDomain(c));
	}

}
