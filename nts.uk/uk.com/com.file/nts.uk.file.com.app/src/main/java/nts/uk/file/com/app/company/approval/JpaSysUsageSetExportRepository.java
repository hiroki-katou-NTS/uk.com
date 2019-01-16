package nts.uk.file.com.app.company.approval;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaSysUsageSetExportRepository extends JpaRepository implements SysUsageExportRepository{
	
	private static final String SELECT_NO_WHERE = "SELECT NEW " + SysUsageSetData.class.getName() + "(c.jinji, c.shugyo, c.kyuyo) FROM SacmtSysUsageSet c where c.sacmtSysUsageSetPK.companyId = :companyId";

	@Override
	public Optional<SysUsageSetData> findUsageSet(String companyId) {
		return this.queryProxy().query(SELECT_NO_WHERE, SysUsageSetData.class).setParameter("companyId", companyId).getSingle();
				
	}

	

}
