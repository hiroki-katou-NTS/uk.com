package nts.uk.ctx.at.shared.infra.repository.calculation.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOT;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOTRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.setting.KrcmtCalcDDef;

@Stateless
public class JpaDeformLaborOTRepository extends JpaRepository implements DeformLaborOTRepository {
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT k");
		builderString.append(" FROM KrcmtCalcDDef k");
		builderString.append(" WHERE k.cid = :companyId");
		SELECT_BY_CID = builderString.toString();
	}
	
	private DeformLaborOT convertToDomain(KrcmtCalcDDef krcmtCalcDDef) {
		DeformLaborOT deformLaborOT = DeformLaborOT.createFromJavaType(krcmtCalcDDef.getCid(), krcmtCalcDDef.getLegalOtCalc());
		
		return deformLaborOT;
	}
	
	private KrcmtCalcDDef convertToDbType(DeformLaborOT deformLaborOT){
		KrcmtCalcDDef krcmtCalcDDef = new KrcmtCalcDDef();
		krcmtCalcDDef.setCid(deformLaborOT.getCid());
		krcmtCalcDDef.setLegalOtCalc(deformLaborOT.getLegalOtCalc().value);
		return krcmtCalcDDef;
	}

	@Override
	public List<DeformLaborOT> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KrcmtCalcDDef.class).setParameter("companyId", companyId).getList(d -> convertToDomain(d));
	}

	@Override
	public void add(DeformLaborOT deformLaborOT) {
		this.commandProxy().insert(convertToDbType(deformLaborOT));
	}

	@Override
	public void update(DeformLaborOT deformLaborOT) {
		KrcmtCalcDDef krcmtCalcDDef = this.queryProxy().find(deformLaborOT.getCid(), KrcmtCalcDDef.class).get();
		krcmtCalcDDef.setLegalOtCalc(deformLaborOT.getLegalOtCalc().value);
		this.commandProxy().update(krcmtCalcDDef);
	}

	@Override
	public Optional<DeformLaborOT> findByCId(String companyId) {
		return this.queryProxy().find(companyId,KrcmtCalcDDef.class)
				.map(c->convertToDomain(c));
	}

}
