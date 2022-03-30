package nts.uk.ctx.bs.employee.dom.workplace.group;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupGettingService.Require;

@RunWith(JMockit.class)
public class WorkplaceGroupGettingServiceTest {

	@Injectable private Require require;



	/**
	 * Target	: get
	 */
	@Test
	public void test_get() {

		/* 【マスタ状態】 */
		/* ========================================
		 *  職場グループ	| 職場		| 社員
		 * ========================================
		 *  wkp-grp-id-1	| wkp-id-1	| emp-id-5
		 * 					|----------------------
		 * 					| wkp-id-4	| emp-id-6
		 * ----------------------------------------
		 *  wkp-grp-id-2	| wkp-id-3	| emp-id-2
		 * ----------------------------------------
		 * 	--				| wkp-id-2	| emp-id-1
		 * 					|			|----------
		 * 					|			| emp-id-4
		 * 					|----------------------
		 * 					| wkp-id-5	| emp-id-7
		 * 					|----------------------
		 * 					| --		| emp-id-3
		 * ========================================
		 */
		// 社員 - 職場
		@SuppressWarnings("serial")
		val data_emp_wkp = new HashMap<String, Optional<String>>() {{

			put( "emp-id-1", Optional.of( "wkp-id-2" ) );
			put( "emp-id-2", Optional.of( "wkp-id-3" ) );
			put( "emp-id-3", Optional.empty() );
			put( "emp-id-4", Optional.of( "wkp-id-2" ) );
			put( "emp-id-5", Optional.of( "wkp-id-1" ) );
			put( "emp-id-6", Optional.of( "wkp-id-4" ) );
			put( "emp-id-7", Optional.of( "wkp-id-5" ) );

		}};
		data_emp_wkp.entrySet().forEach( entry -> {

			new Expectations() {{
				// require.社員が所属している職場を取得する
				require.getAffWkpHistItemByEmpDate( entry.getKey(), (GeneralDate)any );
				result = entry.getValue();
			}};

		} );

		// 職場 - 職場グループ
		@SuppressWarnings("serial")
		val data_wkp_wkpGrp = new HashMap<String, Optional<String>>() {{

			put( "wkp-id-1", Optional.of( "wkp-grp-id-1" ) );
			put( "wkp-id-2", Optional.empty() );
			put( "wkp-id-3", Optional.of( "wkp-grp-id-2" ) );
			put( "wkp-id-4", Optional.of( "wkp-grp-id-1" ) );
			put( "wkp-id-5", Optional.empty() );

		}};
		new Expectations() {{
			// require.職場グループ所属情報を取得する
			require.getWGInfo( data_emp_wkp.values().stream().flatMap(OptionalUtil::stream).distinct().collect(Collectors.toList()) );
			result = data_wkp_wkpGrp.entrySet().stream()
					.filter( entry -> entry.getValue().isPresent() )
					.map( entry -> new AffWorkplaceGroup( entry.getValue().get(), entry.getKey() ) )
					.collect(Collectors.toList());
		}};


		/* 実行 */
		val result = WorkplaceGroupGettingService.get(
						require, GeneralDate.today()
					,	data_emp_wkp.keySet().stream().collect(Collectors.toList())
				);

		/* 期待値 */
		/* ========================================
		 * 社員		| 職場		| 職場グループ
		 * ========================================
		 * emp-id-1	| wkp-id-2	| --
		 * emp-id-2	| wkp-id-3	| wkp-grp-id-2
		 * emp-id-4	| wkp-id-2	| --
		 * emp-id-5	| wkp-id-1	| wkp-grp-id-1
		 * emp-id-6	| wkp-id-4	| wkp-grp-id-1
		 * emp-id-7	| wkp-id-5	| --
		 * ======================================== */
		// 表示情報 -> 常にempty
		assertThat( result ).extracting( EmployeeAffiliation::getEmployeeCode ).containsOnly( Optional.empty() );
		assertThat( result ).extracting( EmployeeAffiliation::getBusinessName ).containsOnly( Optional.empty() );

		// 所属職場のない社員を含まない
		assertThat( result ).extracting( EmployeeAffiliation::getEmployeeID ).doesNotContain( "emp-id-3" );

		assertThat( result )
			.extracting(
					EmployeeAffiliation::getEmployeeID			// 社員ID
				,	EmployeeAffiliation::getWorkplaceID			// 職場ID
				,	EmployeeAffiliation::getWorkplaceGroupID	// 職場グループID
			).containsExactlyInAnyOrder(
					tuple( "emp-id-1", "wkp-id-2", Optional.empty() )
				,	tuple( "emp-id-2", "wkp-id-3", Optional.of( "wkp-grp-id-2" ) )
				,	tuple( "emp-id-4", "wkp-id-2", Optional.empty() )
				,	tuple( "emp-id-5", "wkp-id-1", Optional.of( "wkp-grp-id-1" ) )
				,	tuple( "emp-id-6", "wkp-id-4", Optional.of( "wkp-grp-id-1" ) )
				,	tuple( "emp-id-7", "wkp-id-5", Optional.empty() )
			);

	}

}
