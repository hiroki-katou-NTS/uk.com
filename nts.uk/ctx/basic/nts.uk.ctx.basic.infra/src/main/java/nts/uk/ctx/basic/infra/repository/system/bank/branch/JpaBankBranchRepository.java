package nts.uk.ctx.basic.infra.repository.system.bank.branch;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.system.bank.BankCode;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranch;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.ctx.basic.infra.entity.system.bank.branch.CbkmtBranch;
import nts.uk.ctx.basic.infra.entity.system.bank.branch.CbkmtBranchPK;

@RequestScoped
public class JpaBankBranchRepository extends JpaRepository implements BankBranchRepository{
    public static String SEL_2 = "SELECT br FROM CbkmtBranch br WHERE br.ckbmtBranchPK.companyCode = :companyCode AND br.ckbmtBranchPK.bankCode = :bankCode";
    public static String SEL_3 = "SELECT br FROM CbkmtBranch br WHERE br.ckbmtBranchPK.companyCode = :companyCode";
	
    @Override
    public Optional<BankBranch> find(String companyCode, String bankCode, String branchCode) {
    	CbkmtBranchPK key = new CbkmtBranchPK(companyCode, bankCode, branchCode);
    	return this.queryProxy().find(key, CbkmtBranch.class)
    			.map(x -> BankBranch.createFromJavaType(companyCode, bankCode, branchCode, x.branchName, x.branchKnName, x.memo));
    }
    
    @Override
	public List<BankBranch> findAll(String companyCode,BankCode bankCode) {
         return this.queryProxy().query(SEL_2, CbkmtBranch.class)
        		 .setParameter("companyCode", companyCode)
        		 .setParameter("bankCode", bankCode)
        		 .getList(x -> BankBranch.createFromJavaType(
        				 x.ckbmtBranchPK.companyCode,
        				 x.ckbmtBranchPK.bankCode,
        				 x.ckbmtBranchPK.branchCode, 
        				 x.branchName, 
        				 x.branchKnName, 
        				 x.memo));
	}

	@Override
	public void add(BankBranch bank) {
		this.commandProxy().insert(toEntity(bank));
		
	}

	@Override
	public void update(BankBranch bank) {
		this.commandProxy().update(toEntity(bank));
		
	}

	@Override
	public void remove(BankBranch bank) {
		this.commandProxy().remove(toEntity(bank));
		
	}
	
	private static CbkmtBranch toEntity(BankBranch domain){
		CbkmtBranchPK key = new CbkmtBranchPK(domain.getCompanyCode().v(),domain.getBankCode(),domain.getBankBranchCode().v());
		CbkmtBranch entity = new CbkmtBranch(key, domain.getBankBranchName().v(), domain.getBankBranchNameKana().v(), domain.getMemo().v());		
		return entity;
	}

	@Override
	public List<BankBranch> findAll(String companyCode) {
		return this.queryProxy().query(SEL_3, CbkmtBranch.class)
       		 .setParameter("companyCode", companyCode)
       		 .getList(x -> BankBranch.createFromJavaType(
       				 x.ckbmtBranchPK.companyCode,
       				 x.ckbmtBranchPK.bankCode,
       				 x.ckbmtBranchPK.branchCode, 
       				 x.branchName, 
       				 x.branchKnName, 
       				 x.memo));
	}
	
}
