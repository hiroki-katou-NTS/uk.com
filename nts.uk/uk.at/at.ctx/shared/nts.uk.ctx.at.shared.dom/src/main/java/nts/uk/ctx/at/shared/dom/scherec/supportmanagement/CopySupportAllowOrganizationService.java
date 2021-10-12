package nts.uk.ctx.at.shared.dom.scherec.supportmanagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * 応援許可する組織を複写する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).応援管理.応援許可する組織を複写する
 * @author lan_lt
 * 
 */
public class CopySupportAllowOrganizationService {

	/**
	 * 複写する
	 * @param require
	 * @param copySources 複写元
	 * @param destinationOrg 複写先組織
	 * @param isOverwrite 上書きするか
	 * @return
	 */
	public static Optional<AtomTask> copy( Require require
			,	List<SupportAllowOrganization> copySources
			,	TargetOrgIdenInfor destinationOrg
			,	boolean isOverwrite ) {
		val isExist = require.existsSupportAllowOrganization( destinationOrg );
		
		if( isExist && !isOverwrite ) return Optional.empty();
		
		val copyResults = copySources.stream()
				.filter( source -> source.getTargetOrg().getTargetId() != destinationOrg.getTargetId())
				.map( source -> source.copy(destinationOrg) )
				.collect( Collectors.toList() );
		
		if(copyResults.isEmpty()) return Optional.empty();
		
		return Optional.of( AtomTask.of(() ->{
			if( isOverwrite ) 
				require.deleteSupportAllowOrganization( destinationOrg );
			
			require.registerSupportAllowOrganization( copyResults );
			
		}));
	}
	
	public static interface Require {
		
		/**
		 * 応援許可する組織が存在するか
		 * @param targetOrg 対象組織情報
		 * @return
		 */
		boolean existsSupportAllowOrganization( TargetOrgIdenInfor targetOrg );
		
		/**
		 * 応援許可する組織を削除する
		 * @param targetOrg 対象組織情報
		 */
		void deleteSupportAllowOrganization( TargetOrgIdenInfor targetOrg );
		
		/**
		 * 応援許可する組織を登録する
		 * @param supportAllowOrgs 応援許可する組織リスト
		 */
		void registerSupportAllowOrganization( List<SupportAllowOrganization> supportAllowOrgs );
	
	}
}
