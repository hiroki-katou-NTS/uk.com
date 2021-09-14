package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class Form9LayoutTest {
	
	@Injectable
	private Form9Layout.Require require;
	
	@Test
	public void testGetter(
				@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable
			,	@Injectable Form9Cover cover) {
		
		val result = new Form9Layout(new Form9Code("01"), new Form9Name("name")
				,	true, false
				,	cover, nursingTable
				,	nursingAideTable, Optional.of("tempalteFileId"));
		
		NtsAssert.invokeGetters(result);
		
	}
	
	/**
	 * target: getFileName
	 * pattern: 出力レイアウはシステム固定 
	 * except: ファイル名 = コード＋ 名称 + ".xlsx"
	 * 
	 */
	@Test
	public void testGetFileName_layout_is_systemFixed(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		val code = new Form9Code("01");
		val name = new Form9Name("name");
		val exceptResult = code.v() + name.v() + ".xlsx";
		
		val layout = new Form9Layout(code , name
				,	true //システム固定
				,	false
				,	cover, nursingTable
				,	nursingAideTable, Optional.of("tempalteFileId"));
		//Act
		val result = layout.getFileName(require);
		
		
		//Assert
		assertThat( result.get()).isEqualTo( exceptResult );
		
	}
	
	/**
	 * target: getFileName
	 * pattern: 出力レイアウはユーザー定義
	 * except: empty
	 * 
	 */
	@Test
	public void testGetFileName_layout_is_user_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		val code = new Form9Code("01");
		val name = new Form9Name("name");
		val templateId = "templateId";
		
		
		val layout = new Form9Layout(code , name
				,	false //ユーザー定義
				,	false
				,	cover, nursingTable
				,	nursingAideTable, Optional.of(templateId));
		
		new Expectations() {
			{
				require.getInfo(templateId);
			
			}
		};
		
		//Act
		val result = layout.getFileName(require);
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	/**
	 * target: getFileName
	 * pattern: 出力レイアウはユーザー定義
	 * except: ファイルIDの名称
	 * 
	 */
	@Test
	public void testGetFileName_layout_is_user_not_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		val code = new Form9Code( "01" );
		val name = new Form9Name( "name" );
		val templateId = "templateId";
		val templateName =  "template.xlsx";
		val storeFileInfo = StoredFileInfo.createNewTemporaryWithId(
					templateId, templateName
				,	"mimeType"//DUMMY
				,	2300);//DUMMY
				
		
		val layout = new Form9Layout( code , name
				,	false //ユーザー定義
				,	false//DUMMY
				,	cover, nursingTable//DUMMY
				,	nursingAideTable//DUMMY
				,	Optional.of( templateId ));
		
		new Expectations() {
			{
				require.getInfo(templateId);
				result = Optional.of( storeFileInfo );
			}
		};
		
		//Act
		val result = layout.getFileName(require);
		
		//Assert
		assertThat( result.get() ).isEqualTo( templateName );
		
	}
	
	/**
	 * target: create
	 * pattern: 出力レイアウはシステム固定, テンプレートIDがある
	 * except: Msg_2279
	 * 
	 */
	@Test
	public void testCreate_layout_is_system_templateId_not_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		NtsAssert.businessException("Msg_2279", () ->{
			Form9Layout.create(
						new Form9Code( "01" )
					,	new Form9Name( "name" )
					,	true //システム固定
					,	false//DUMMY
					,	cover, nursingTable//DUMMY
					,	nursingAideTable//DUMMY
					,	Optional.of( "templateId" ));
		});
		
	}
	
	/**
	 * target: create
	 * pattern: 出力レイアウはユーザー定義, テンプレートIDはempty
	 * except: Msg_2280
	 * 
	 */
	@Test
	public void testCreate_layout_is_user_templateId_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		NtsAssert.businessException("Msg_2280", () ->{
			Form9Layout.create(
						new Form9Code( "01" )
					,	new Form9Name( "name" )
					,	false //ユーザー定義
					,	false//DUMMY
					,	cover, nursingTable//DUMMY
					,	nursingAideTable//DUMMY
					,	Optional.empty());
		});
	}
		
	/**
	 * target: create
	 * pattern: 出力レイアウはユーザー定義, テンプレートID not empty
	 * except: success
	 * 
	 */
	@Test
	public void testCreate_layout_is_user_templateId_not_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		val code = new Form9Code( "01" );
		val name = new Form9Name( "name" );
		val templateId = "templateId";
		
		val layout = Form9Layout.create( code
					,	name
					,	false //ユーザー定義
					,	true
					,	cover, nursingTable
					,	nursingAideTable
					,	Optional.of(templateId));
		
		assertThat( layout.getCode()).isEqualTo( code );
		assertThat( layout.getName() ).isEqualTo( name );
		assertThat( layout.isSystemFixed() ).isFalse();
		assertThat( layout.isUse() ).isTrue();
		assertThat( layout.getCover() ).isEqualTo( cover );
		assertThat( layout.getNursingAideTable() ).isEqualTo( nursingAideTable );
		assertThat( layout.getNursingTable() ).isEqualTo( nursingTable );
		assertThat( layout.getTempalteFileId().get()).isEqualTo( templateId );

	}
	
	/**
	 * target: create
	 * pattern: 出力レイアウはシステム固定, テンプレートIDはempty
	 * except: success
	 * 
	 */
	@Test
	public void testCreate_layout_is_system_templateId_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {

		val code = new Form9Code( "01" );
		val name = new Form9Name( "name" );
		
		val layout = Form9Layout.create( code
					,	name
					,	true //ユーザー定義
					,	true
					,	cover, nursingTable
					,	nursingAideTable
					,	Optional.empty());
		
		assertThat( layout.getCode()).isEqualTo( code );
		assertThat( layout.getName() ).isEqualTo( name );
		assertThat( layout.isSystemFixed() ).isTrue();
		assertThat( layout.isUse() ).isTrue();
		assertThat( layout.getCover() ).isEqualTo( cover );
		assertThat( layout.getNursingAideTable() ).isEqualTo( nursingAideTable );
		assertThat( layout.getNursingTable() ).isEqualTo( nursingTable );
		assertThat( layout.getTempalteFileId()).isEmpty();
		
	}
	
	/**
	 * target: copy
	 * pattern: 出力レイアウはユーザー定義
	 */
	@Test
	public void testCopy_user(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		val destinationCode = new Form9Code("02");
		val destinationName = new Form9Name("destinationName");
		val tempalteFileId = "tempalteFileId";
		val layout = Form9Layout.create(new Form9Code("01") , new Form9Name("name"), false, true
					,	cover, nursingTable, nursingAideTable, Optional.of(tempalteFileId));
		
		val destinationLayout = layout.copy(require, destinationCode, destinationName);
		
		assertThat( destinationLayout.getCode()).isEqualTo( destinationCode );
		assertThat( destinationLayout.getName() ).isEqualTo( destinationName );
		assertThat( destinationLayout.isSystemFixed() ).isFalse();
		assertThat( destinationLayout.isUse() ).isTrue();
		assertThat( layout.getCover() ).isEqualTo( cover );
		assertThat( layout.getNursingAideTable() ).isEqualTo( nursingAideTable );
		assertThat( layout.getNursingTable() ).isEqualTo( nursingTable );
		assertThat( layout.getTempalteFileId().get()).isEqualTo( tempalteFileId );
		
	}
	
}
