package nts.uk.ctx.basic.infra.repository.system.bank.linebank;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.system.bank.linebank.LineBank;
import nts.uk.ctx.basic.dom.system.bank.linebank.LineBankRepository;
import nts.uk.ctx.basic.infra.entity.system.bank.linebank.CbkmtLineBank;
import nts.uk.ctx.basic.infra.entity.system.bank.linebank.CbkmtLineBankPK;

@Stateless
@Transactional
public class JpaLineBankRepository extends JpaRepository implements LineBankRepository {
	
	private final String SEL_1 = "SELECT c FROM CbkmtLineBank c " 
	+ "WHERE c.cbkmtLineBankPK.companyCode = :CCD";
	
	private CbkmtLineBank toEntity(LineBank domain) {
		val entity = new CbkmtLineBank();

		entity.cbkmtLineBankPK = new CbkmtLineBankPK();
		entity.cbkmtLineBankPK.companyCode = domain.getCompanyCode().v();
		entity.cbkmtLineBankPK.lineBankCode = domain.getLineBankCode().v();
		entity.accountAtr = domain.getAccountAtr().value;
		entity.accountNo = domain.getAccountNo().v();
		entity.bankCode = domain.getBankCode();
		entity.branchCode = domain.getBranchCode();
		entity.lineBankName = domain.getLineBankName().v();
		entity.memo = domain.getMemo().v();
		entity.requesterName = domain.getRequesterName().v();
		for (int i = 0; i <= domain.getConsignor().size(); i++) {
			if (i == 0) {
				entity.consignorCode1 = domain.getConsignor().get(i).getConsignorCode().v();
				entity.consignorMemo1 = domain.getConsignor().get(i).getConsignorMemo().v();
			}

			if (i == 1) {
				entity.consignorCode2 = domain.getConsignor().get(i).getConsignorCode().v();
				entity.consignorMemo2 = domain.getConsignor().get(i).getConsignorMemo().v();
			}

			if (i == 2) {
				entity.consignorCode3 = domain.getConsignor().get(i).getConsignorCode().v();
				entity.consignorMemo3 = domain.getConsignor().get(i).getConsignorMemo().v();
			}

			if (i == 3) {
				entity.consignorCode4 = domain.getConsignor().get(i).getConsignorCode().v();
				entity.consignorMemo4 = domain.getConsignor().get(i).getConsignorMemo().v();
			}

			if (i == 4) {
				entity.consignorCode5 = domain.getConsignor().get(i).getConsignorCode().v();
				entity.consignorMemo5 = domain.getConsignor().get(i).getConsignorMemo().v();
			}
		}
		return entity;
	}
	
	public static LineBank toDomain(CbkmtLineBank entity){
		LineBank domain = LineBank.createFromJavaType(
				entity.cbkmtLineBankPK.companyCode, entity.accountAtr, 
				entity.accountNo, entity.bankCode, 
				entity.branchCode, entity.cbkmtLineBankPK.lineBankCode, 
				entity.lineBankName, entity.memo, entity.requesterName);
		domain.createConsignorFromJavaType(
				entity.consignorCode1, entity.consignorMemo1,
				entity.consignorCode2, entity.consignorMemo2,
				entity.consignorCode3, entity.consignorMemo3,
				entity.consignorCode4, entity.consignorMemo4,
				entity.consignorCode5, entity.consignorMemo5);
		return domain;
	}
	
	@Override
	public List<LineBank> findAll(String companyCode) {
		return this.queryProxy()
				.query(SEL_1, CbkmtLineBank.class)
				.setParameter("CCD", companyCode)
				.getList(c -> toDomain(c));
	}

	@Override
	public void add(LineBank lineBank) {
		this.commandProxy().insert(toEntity(lineBank));
	}

	@Override
	public void remove(String companyCode, String lineBankCode) {
		CbkmtLineBankPK cbkmtLineBankPK = new CbkmtLineBankPK(companyCode, lineBankCode); 
		this.commandProxy().remove(CbkmtLineBank.class, cbkmtLineBankPK);;
	}

	@Override
	public void update(LineBank lineBank) {
		this.commandProxy().update(toEntity(lineBank));

	}

	@Override
	public Optional<LineBank> find(String companyCode, String lineBankCode) {
		CbkmtLineBankPK cbkmtLineBankPK = new CbkmtLineBankPK(companyCode, lineBankCode); 
		return this.queryProxy().find(cbkmtLineBankPK, CbkmtLineBank.class).map(x->toDomain(x));
	}

}
