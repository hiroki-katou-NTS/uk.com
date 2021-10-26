package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class CopyForm9LayoutServiceTest {
	
	@Injectable
	private CopyForm9LayoutService.Require require;
	
	/**
	 * target: copy
	 * pattern:
	 * 		複製先のコードが登録された )重複 )
	 * 		出力レイアウ = システム固定
	 * expceted: Msg_2252
	 */
	@Test
	public void testCopy_oldCopyDestination_system_layout(
			@Injectable Form9Cover cover
		,	@Injectable Form9NursingTable nursingTable
		,	@Injectable Form9NursingAideTable nursingAideTable ) {
		
		val destinationCode = new Form9Code( "02" );
		val destinationName = new Form9Name( "destinationName" );
		val tempalteFileId = "tempalteFileId";
		
		val copyResource = Form9Layout.create(
					new Form9Code( "01" )
				,	new Form9Name( "name" )
				,	false, true
				,	cover, nursingTable, nursingAideTable
				,	Optional.of( tempalteFileId ));
		
		val oldCopyDestination =  Form9Layout.create(
					new Form9Code( "02" )
				,	new Form9Name( "name" )
				,	true //システム固定
				,	true, cover, nursingTable, nursingAideTable, Optional.empty() );
		
		new Expectations() {
			{
				//複製先のコードが登録された(重複)
				require.getForm9Layout( destinationCode );
				result = Optional.of( oldCopyDestination );
			}
		};
		
		NtsAssert.businessException("Msg_2252", () ->{
			CopyForm9LayoutService.copy( require, copyResource, destinationCode, destinationName, false );
		});
		
	}
	
	/**
	 * target: copy
	 * pattern: 
	 * 		複製先のコードが登録された(重複)
	 * 		出力レイアウ = ユーザー定義
	 * 		上書きするか = false (上書きしない)
	 * expceted: Msg_2239
	 */
	@Test
	public void testCopy_isOverwrite_false(
			@Injectable Form9Cover cover
		,	@Injectable Form9NursingTable nursingTable
		,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		val destinationCode = new Form9Code( "02" );
		val destinationName = new Form9Name( "destinationName" );
		val tempalteFileId = "tempalteFileId";
		val isOverwrite = false;//上書きしない
		
		val copyResource = Form9Layout.create( new Form9Code( "01" ) , new Form9Name( "name" ), false, true
				,	cover, nursingTable, nursingAideTable, Optional.of( tempalteFileId ) );
		
		val oldCopyDestination =  Form9Layout.create( new Form9Code( "02" ) , new Form9Name( "name" )
				,	false //ユーザー定義
				,	true, cover, nursingTable, nursingAideTable, Optional.of( tempalteFileId ) );
		
		new Expectations() {
			{
				require.getForm9Layout( destinationCode );
				result = Optional.of( oldCopyDestination );
			}
		};
		
		NtsAssert.businessException("Msg_2239", () ->{
			CopyForm9LayoutService.copy( require, copyResource, destinationCode, destinationName, isOverwrite );
		});
		
	}
	
	/**
	 * target: copy
	 * pattern: 
	 * 			複製先のコードが登録された(重複)
	 * 			出力レイアウ = ユーザー定義
	 * 			上書きするか = true (上書きする)
	 * expceted: 複製されたレイアウ登録(insert)される
	 */
	@Test
	public void testCopy_isOverwrite_true(
			@Injectable Form9Cover cover
		,	@Injectable Form9NursingTable nursingTable
		,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		val destinationCode = new Form9Code( "02" );
		val destinationName = new Form9Name( "destinationName" );
		val tempalteFileId = "tempalteFileId";
		val isOverwrite = true;//上書きする
		
		val copyResource = Form9Layout.create( new Form9Code( "01" ) , new Form9Name( "name" ), false, true
				,	cover, nursingTable, nursingAideTable, Optional.of( tempalteFileId ) );
		
		val oldCopyDestination =  Form9Layout.create( new Form9Code( "02" ) , new Form9Name( "name" )
				,	false //ユーザー定義
				,	true, cover, nursingTable, nursingAideTable, Optional.of( tempalteFileId ) );
		
		val newLayout = copyResource.copy( require, destinationCode, destinationName );
		
		new Expectations() {
			{
				require.getForm9Layout( destinationCode );
				result = Optional.of( oldCopyDestination );
				
				copyResource.copy( require, destinationCode, destinationName );
				times = 1;
			}
		};

		NtsAssert.atomTask(
					() -> CopyForm9LayoutService.copy( require, copyResource, destinationCode, destinationName, isOverwrite )
				,	any -> require.deleteForm9Layout( destinationCode )
				,	any -> require.insertForm9Layout( newLayout ));
	}
	
	/**
	 * target: copy
	 * pattern: 
	 * 			複製先のコードが未登録(重複なし)
	 * expceted: 複製されたレイアウ登録(insert)される
	 */
	@Test
	public void testCopy_destinationCodeIsNew(
			@Injectable Form9Cover cover
		,	@Injectable Form9NursingTable nursingTable
		,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		val destinationCode = new Form9Code( "02" );
		val destinationName = new Form9Name( "destinationName" );
		val tempalteFileId = "tempalteFileId";
		val isOverwrite = true;
		
		val copyResource = Form9Layout.create( new Form9Code("01" ) , new Form9Name("name" ), false, true
				,	cover, nursingTable, nursingAideTable, Optional.of( tempalteFileId ) );
		
		val newLayout = copyResource.copy( require, destinationCode, destinationName );
		
		new Expectations() {
			{
				require.getForm9Layout( destinationCode );
				
				copyResource.copy( require, destinationCode, destinationName );
				times = 1;
			}
		};

		NtsAssert.atomTask( 
					() -> CopyForm9LayoutService.copy( require, copyResource, destinationCode, destinationName, isOverwrite )
				,	any -> require.insertForm9Layout( newLayout ));
		
	}
}
